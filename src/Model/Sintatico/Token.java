package Model.Sintatico;

import java.util.*;

import static Model.Sintatico.TipoToken.*;

public class Token {

    private List<String> mes = Arrays.asList("janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro");

    private List<String> lugar = Arrays.asList( "natal", "paris", "bahia", "salvador", "sergipe", "alagoas", "inglaterra");

    private List<String> produto = Arrays.asList( "pacote", "hospedagem", "voo", "passagem", "passeio", "restaurante");

    private List<String> veiculo = Arrays.asList("carro", "moto", "onibus", "avião", "helicóptero", "a pe");

    private List<String> periodo = Arrays.asList("noite", "dia", "tarde", "chovendo", "ensolarado", "ventando", "frio", "doente");

    private List<String> local = Arrays.asList("bar", "supermercado", "padaria", "lanchonete", "farmacia");

    private List<String> evento = Arrays.asList("voo", "passeio", "show", "trilha", "barco", "treno", "bike");

    private List<String> pagamento = Arrays.asList( "debito", "credito", "boleto", "a vista", "cheque", "crediario");

    private List<String> situacao = Arrays.asList( "crian", "cadeirante", "cego", "amigos");

    private List<String> feriado = Arrays.asList( "natal", "carnaval", "pascoa");

    private Map<TipoToken, List<String>> listOfTokens;

    public Token() {
        listOfTokens = new HashMap<>();
        listOfTokens.put(TipoToken.SITUACAO, situacao);
        listOfTokens.put(MES, mes);
        listOfTokens.put(LUGAR, lugar);
        listOfTokens.put(PRODUTO, produto);
        listOfTokens.put(VEICULO, veiculo);
        listOfTokens.put(PERIODO, periodo);
        listOfTokens.put(LOCAL, local);
        listOfTokens.put(EVENTO, evento);
        listOfTokens.put(PAGAMENTO, pagamento);
        listOfTokens.put(SITUACAO, situacao);
        listOfTokens.put(FERIADO, feriado);
    }

    public Map<TipoToken, List<String>> getListOfTokens() {
        return this.listOfTokens;
    }

}
