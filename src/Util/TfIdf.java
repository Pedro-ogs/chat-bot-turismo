package Util;

import Model.Document;
import Model.Word;

import java.util.List;

public class TfIdf {

    public static double tf(Word term, Document d) {
        double result = 0;

        List<String> lines = d.getLinhas();

        for (String line : lines) {
            String[] aux = line.split(" ");
            for (String word : aux)
                if (word.toLowerCase().contains(term.getPalavra()))
                    result++;
        }

        return result / d.getSize();
    }

    public static double idf(Word term) {
        return Math.log(Index.numberOfResponseFiles() / term.getDocumento().size());
    }

    public static double tfIdf(Word term, Document doc) {
        return tf(term, doc) * idf(term);
    }

}
