package Model;

import java.util.List;

public class Document {

    private String nome;
    private List<String> linhas;
    private int size;

    public Document() {

    }

    public Document(String nome, List<String> linhas) {
        this.nome = nome;
        this.linhas = linhas;
        calculateSize();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<String> linhas) {
        this.linhas = linhas;
    }

    public int getSize() {
        if (this.size == 0) calculateSize();

        return this.size;
    }

    private void calculateSize() {
        this.size = 0;

        for (String line : this.linhas)
            this.size += line.split(" ").length;
    }
}
