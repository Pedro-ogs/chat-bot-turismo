package Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Word {

    private String palavra;
    private List<String> documento;
    private double frequencia;

    public Word() {

    }

    public Word(String palavra) {
        this.palavra = palavra;
        documento = new LinkedList<>();
    }

    public Word(String palavra, List<String> documento) {
        this.palavra = palavra;
        this.documento = documento;
    }

    public String getPalavra() {
        return this.palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<String> getDocumento() {
        return documento;
    }

    public void setDocumento(List<String> documents) {
        this.documento = documents;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequency) {
        this.frequencia = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(getPalavra(), word1.getPalavra()) && Objects.equals(getDocumento(), word1.getDocumento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPalavra(), getDocumento());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(palavra + " | ");
        for (String document : documento)
            sb.append(document + " ");

        return sb.toString();
    }
}

