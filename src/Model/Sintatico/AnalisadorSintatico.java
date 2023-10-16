package Model.Sintatico;

import Model.Response;

import java.util.*;

import static Model.QueryClassification.*;

public class AnalisadorSintatico {

    private Queue<String> tokens;
    private Token token;

    private List<String> symbolTable;

    public AnalisadorSintatico() {
        token = new Token();
    }

    public AnalisadorSintatico(Queue<String> tokens, List<String> symbolTable) {
        this.tokens = tokens;
        token = new Token();
        this.symbolTable = symbolTable;
    }

    public Response checkRegras() {
        String regra = this.tokens.peek().toLowerCase();
        Response response = null;

        switch (regra) {
            case "quanto":
                 return processQuanto();
            case "como":
                return processComo();
            case "onde":
                return processOnde();
            case "quando":
                return processQuando();
            case "qual":
                return processQual();
            default:
                response = new Response(ERROR, "ERRO - Não foi possivel entender sua pergunta.");
        }

        return response;
    }

    private Response processQuanto(){
        List<String> copy = copyTokenList();
        String token = copy.get(0);

        if (tokenExistente(TipoToken.PAGAMENTO, token)) {
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.PAGAMENTO)));
        }
        if (tokenExistente(TipoToken.PRODUTO,token)){
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.PRODUTO)));
        }

        return new Response(ERROR, "ERRO - Não foi possivel entender sua pergunta.");
    }

    private Response processComo() {
        List<String> copy = copyTokenList();
        String token = copy.get(0);

        if (tokenExistente(TipoToken.LUGAR,token)){
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.LUGAR)));

        } else if (tokenExistente(TipoToken.VEICULO,token)){
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.VEICULO)));
        }

        return new Response(ERROR, "ERRO - Não foi possivel entender sua pergunta.");
    }

    private Response processQuando() {

        List<String> copy = copyTokenList();
        String token = copy.get(0);

        if (tokenExistente(TipoToken.EVENTO,token)){
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.EVENTO)));
        }

        return new Response(ERROR, "ERRO - Não foi possivel entender sua pergunta.");
    }

    private Response processOnde(){
        List<String> copy = copyTokenList();
        String token = copy.get(0);

        if (tokenExistente(TipoToken.LOCAL,token)) {

            if (tokenExistente(TipoToken.LOCAL, token)) {
                return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.LOCAL)));
            } else if (tokenExistente(TipoToken.LUGAR, token)) {
                return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.LUGAR)));
            }

        } else if (tokenExistente(TipoToken.SITUACAO,token)) {
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.SITUACAO)));
        }

        return new Response(ERROR, "ERRO - Não foi possivel entender sua pergunta.");
    }

    private Response processQual() {
        List<String> copy = copyTokenList();
        String token = copy.get(0);

        if (tokenExistente(TipoToken.PRODUTO, token)) {
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.PRODUTO)));
        } else if (tokenExistente(TipoToken.FERIADO, token)) {
            return tokensInQuery(new LinkedList<>(Arrays.asList(TipoToken.FERIADO)));
        }

        return new Response(ERROR, "Não entendi.");
    }

    private Response tokensInQuery(List<TipoToken> expectedTokens) {
        List<String> tokens = copyTokenList();
        List<String> filteredTokens = new LinkedList<>();
        List<TipoToken> filteredExpectedTokens = new LinkedList<>();

        for (TipoToken type : expectedTokens) {
            for (String token : tokens) {
                if (tokenExistente(type, token)) {
                    filteredTokens.add(token);
                    filteredExpectedTokens.add(type);
                }
            }
        }

        tokens.removeAll(filteredTokens);
        expectedTokens.removeAll(filteredExpectedTokens);

        if (filteredTokens.size() == 0 && tokens.size() > 0)
            return new Response(ERROR, "Não entendi.");

        return resolveTokens(tokens, expectedTokens);
    }
    private Response resolveTokens(List<String> tokens, List<TipoToken> expectedTokens) {
        String defaultMessage = "Para qual %s você deseja saber?";
        Response response = new Response();

        if (tokens.size() > expectedTokens.size()) {
            this.symbolTable.addAll(tokens);
        } else if (tokens.size() < expectedTokens.size()) {
            String expected = "";
            for (int i = 0; i < expectedTokens.size(); i++) {
                if (i < expectedTokens.size() - 1)
                    expected += (expectedTokens.get(i).toString() + ", ");
                else if (i == expectedTokens.size() - 1)
                    expected += expectedTokens.get(i).toString();
            }
            response.setResponseType(LESS);
            response.setMessage(String.format(defaultMessage, expected));
        } else {
            response.setResponseType(OK);
        }

        return response;
    }

    private boolean tokenExistente(TipoToken type, String token) {
        Map listOfTokens = this.token.getListOfTokens();
        if (listOfTokens.containsKey(type)) {
            List<String> values = (List<String>) listOfTokens.get(type);
            if (values.contains(token))
                return true;
        }
        return false;
    }

    private List<String> copyTokenList() {
        Queue<String> copyTokens = new LinkedList<>();
        copyTokens.addAll(tokens);
        copyTokens.poll();

        List<String> tokens = new LinkedList<>();

        for (String token : copyTokens)
            tokens.add(token);

        return tokens;
    }

}