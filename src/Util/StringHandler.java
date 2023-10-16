package Util;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringHandler {

    public static String verificaAlfabeto(String s) {
        String regex = "[^a-zA-Z0-9 ]";
        String output = s.replaceAll(regex, " $0 ");
        return output;
    }
    
    public static boolean similar(String str1, String str2, int error) {
        int distance = LevenshteinDistance.getDefaultInstance().apply(str1, str2);

        if (distance <= error)
            return true;

        return false;
    }

    public static ArrayList<String> splitQuery(String query) {
        ArrayList<String> words =
                Stream.of(query.toLowerCase().split(" "))
                        .collect(Collectors.toCollection(ArrayList<String>::new));

        return words;
    }

    public static String concatenaPalavras(List<String> words) {
        String result = words.stream().collect(Collectors.joining(" "));
        return result;
    }

}
