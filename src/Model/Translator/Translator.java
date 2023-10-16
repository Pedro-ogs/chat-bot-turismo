package Model.Translator;

import Model.Document;
import Model.Response;
import Model.Word;
import Util.FileHandler;
import Util.Index;
import Util.TfIdf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static Model.QueryClassification.*;

public class Translator {

    private Queue<String> tokens;

    public Translator() {
    }

    public Translator(Queue<String> tokens) {
        this.tokens = tokens;
    }

    public Response gerarResposta() {
        List<Word> wordsFromTokensList = wordsFromTokensList();

        for (Word word : wordsFromTokensList) {
            String doc = word.getDocumento().get(0);
            Document document = new Document(doc, FileHandler.readResponseDocuments(doc));
            word.setFrequencia(TfIdf.tfIdf(word, document));
        }

        Word maxFrequencyWord = findMaxFrequencyWord(wordsFromTokensList);

        List<String> docs = findCommonDocuments(wordsFromTokensList, maxFrequencyWord);

        wordsFromTokensList.remove(maxFrequencyWord);

        for (String doc : docs) {
            List<String> responseDoc = FileHandler.readResponseDocuments(doc);
            String answer = findAnswer(responseDoc, maxFrequencyWord, wordsFromTokensList);

            if (answer != null)
                return new Response(OK, answer);
        }

        return new Response(ERROR, "Não entendi.");
    }

    private List<Word> wordsFromTokensList() {
        List<Word> invertedFile = Index.loadInvertedFile();
        List<Word> words = new LinkedList<>();

        for (String token : tokens) {
            for (Word word : invertedFile) {
                if (token.equalsIgnoreCase(word.getPalavra())) {
                    words.add(word);
                    break;
                }
            }
        }

        return words;
    }

    /**
     * Encontra a palavra que posui o maior valor de frequência.
     * Palavras com valor de frequência maiores ocorrem em menos documentos.
     * @param words
     * @return Retorna a word que possui o maior valor de frequência.
     */
    private Word findMaxFrequencyWord(List<Word> words) {
        Word maxFrequencyWord = null;
        double maxFrequency = 0;

        for (Word word : words) {
            if (word.getFrequencia() > maxFrequency) {
                maxFrequency = word.getFrequencia();
                maxFrequencyWord = word;
            }
        }

        return maxFrequencyWord;
    }

    private List<String> findCommonDocuments(List<Word> wordList, Word maxFrequencyWord) {
        List<String> commonDocuments = new ArrayList<>(maxFrequencyWord.getDocumento());

        for (Word word : wordList) {
            if (word != maxFrequencyWord) {
                commonDocuments.retainAll(word.getDocumento());
            }
        }

        return commonDocuments;
    }

    private String findAnswer(List<String> doc, Word maxFrequencyWord, List<Word> words) {

        for (String line : doc) {
            boolean allWordsPresent = true;
            if (line.toLowerCase().contains(maxFrequencyWord.getPalavra())) {
                if (words.size() == 0)
                    allWordsPresent = true;

                for (Word word : words) {
                    if (!line.toLowerCase().contains(word.getPalavra())) {
                        allWordsPresent = false;
                        break;
                    }
                }
            } else
                allWordsPresent = false;

            if (allWordsPresent)
                return line;
        }

        return null;
    }
}
