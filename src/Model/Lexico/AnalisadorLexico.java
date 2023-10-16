package Model.Lexico;

import Util.FileHandler;
import Util.StringHandler;

import java.util.*;

public class AnalisadorLexico {

    private String fraseCorreta;
    private List<String> tabela;
    private Queue<String> filaPalavra;
    private List<String> identificador;

    public AnalisadorLexico() { //Construtor
        tabela = new ArrayList<>();
        filaPalavra = new LinkedList<>();
        identificador = new ArrayList<>(Arrays.asList("quanto", "para", "como", "onde", "quando", "qual"));
    }

    public void start(String frase) {
        removeStopWords(frase);
        addInQueue();
        removeExpWords();
        addInSymbolTable();
    }

    public void alfabeto(String frase) {
        frase = StringHandler.verificaAlfabeto(frase);

        ArrayList<String> words = StringHandler.splitQuery(frase);

        for(int i = 0; i < words.size(); i++) {
            if (!verificaAlfabeto(words.get(i)))
                words.remove(i);
        }

        this.fraseCorreta = StringHandler.concatenaPalavras(words);
    }

    public String removeStopWords(String frase) {
        alfabeto(frase);

        List<String> listaStopWords = FileHandler.readStopWordsFile();

        List<String> allWords = StringHandler.splitQuery(this.fraseCorreta);

        allWords.removeAll(listaStopWords);
        this.fraseCorreta = StringHandler.concatenaPalavras(allWords);

        return this.fraseCorreta;
    }

    public String removeExpWords() {
        List<String> listaExpWords = FileHandler.readExpectedWordsFile();

        List<String> allWords = StringHandler.splitQuery(this.fraseCorreta);

        allWords.removeAll(listaExpWords);

        this.fraseCorreta = StringHandler.concatenaPalavras(allWords);

        return this.fraseCorreta;
    }

    public void addInSymbolTable() {
        List<String> words = StringHandler.splitQuery(this.fraseCorreta);
        words.removeAll(identificador);
        tabela.addAll(words);
    }

    public void addInQueue() {
        filaPalavra.addAll(StringHandler.splitQuery(this.fraseCorreta));
    }

    public void addInQueue(List<String> tokens) {
        filaPalavra.addAll(tokens);
    }

    private boolean verificaAlfabeto(String palavra) {
        String regex = "[\\p{L}\\p{N}\\p{P}\\p{Zs}@]+";

        if (palavra.matches(regex))
            return true;

        return false;
    }

    public List<String> getTabela() {
        return tabela;
    }

    public Queue<String> getFilaPalavra() {
        return filaPalavra;
    }
}