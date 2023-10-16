import Model.Lexico.AnalisadorLexico;
import Model.Response;
import Model.Sintatico.AnalisadorSintatico;
import Model.Translator.Translator;
import Util.FileHandler;

import java.util.Scanner;

import static Model.QueryClassification.*;

public class Main {
    public static void main(String[] args) {

        Chat chat = new Chat();
        chat.init();

    }

    public static class Chat {

        private AnalisadorLexico analisadorLexico;
        private AnalisadorSintatico analisadorSintatico;
        private final Scanner input;

        public Chat() {
            input = new Scanner(System.in);
        }
        public void init() {
            System.out.println("Olá! Como posso ajudar?");

            while (true) {
                this.analisadorLexico = new AnalisadorLexico();
                String query;

                query = input.nextLine();

                while (query.isBlank() || query.isEmpty())
                    query =  input.nextLine();

                analisadorLexico.start(query);

                analisadorSintatico = new AnalisadorSintatico(analisadorLexico.getFilaPalavra(), analisadorLexico.getTabela());
                Response response = analisadorSintatico.checkRegras();

                if (response.getResponseType() == ERROR) {
                    System.out.println(response.getMessage());
                } else if (response.getResponseType() == LESS) {
                    response = handleMissingTokens(response);
                }

                if (response.getResponseType() == OK) {
                    Translator translator = new Translator(analisadorLexico.getFilaPalavra());

                    Response answer = translator.gerarResposta();

                    System.out.println(answer.getMessage());
                }

                System.out.println("Como posso ajudar?");
            }
        }

        private Response handleMissingTokens(Response response) {
            do {
                String query;
                System.out.println(response.getMessage());
                query = input.nextLine();
                while (query.isEmpty() || query.isBlank() || (!FileHandler.readExpectedWordsFile().contains(query.toLowerCase()))) {
                    System.out.println("Não entendi.");
                    System.out.println(response.getMessage());
                    query = input.nextLine();
                }
                analisadorLexico.removeStopWords(query);
                analisadorLexico.addInQueue();

                response = analisadorSintatico.checkRegras();
            } while (response.getResponseType() != OK);

            return response;
        }

    }
}