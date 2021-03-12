package proxy;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import exceptions.*;
import proxy.dto.EtatPartie;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;


public class MotusProxyImpl implements MotusProxy {


    public static final String CONTENT_TYPE = "Content_Type";
    public static final String PSEUDO = "pseudo";
    public static final String TOKEN = "token";
    public static final String TOKEN_PARTIE = "tokenPartie";
    public static final String PROPOSITION = "proposition";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();


    private static final String URI_WS = "http://localhost:8080/motus";
    private static final String URI_JOUEUR = URI_WS + "/joueur";
    private static final String URI_PARTIE = URI_WS + "/partie";

    private final static int CONFLICT = 409;
    private final static int CREATED = 201;
    private final static int UNAUTHORIZED = 401;
    private final static int BAD_REQUEST = 400;
    private final static int OK = 200;
    private final static int NOT_ACCEPTABLE = 406;
    private final static int NOT_FOUND = 404;

    @Override
    public String creerUnCompte(String pseudo) throws PseudoDejaPrisException, ConnexionServeurException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI_JOUEUR))
                .header(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED)
                .POST(HttpRequest.BodyPublishers.ofString(PSEUDO + "=" + pseudo)).build();


        try {
            HttpResponse response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()){
                case OK: {
                    return response.headers().firstValue("token").get();
                }
                case BAD_REQUEST:{
                    throw new PseudoDejaPrisException();
                }
                default:
                    throw new CodeErreurInnatendu();
            }

        } catch (IOException | InterruptedException | CodeErreurInnatendu e) {
            throw new ConnexionServeurException();
        }


    }

    @Override
    public String creerUnePartie(String tokenAuthentification) throws IdentiteInvalide, JoueurNonValideException, ConnexionServeurException, CodeErreurInnatendu {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI_PARTIE))
                .header(TOKEN, tokenAuthentification)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();


        try {
            HttpResponse response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {

                case CREATED: {
                    String token = response.headers().firstValue(TOKEN_PARTIE).get();
                    return token;
                }
                case BAD_REQUEST: {
                    throw new IdentiteInvalide();
                }
                case UNAUTHORIZED: {
                    throw new JoueurNonValideException();
                }
                default:
                    throw new CodeErreurInnatendu();
            }


        } catch (IOException | InterruptedException e) {
            throw new ConnexionServeurException();
        }


    }




    @Override
    public EtatPartie proposerMot(String tokenPartie, String proposition) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException, CodeErreurInnatendu, ConnexionServeurException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI_PARTIE))
                .header(CONTENT_TYPE,APPLICATION_X_WWW_FORM_URLENCODED)
                .header(TOKEN_PARTIE,tokenPartie)
                .PUT(HttpRequest.BodyPublishers.ofString(PROPOSITION+"="+proposition))
                .build();


        try {

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {
                case OK: {
                    EtatPartie resultat = objectMapper.readerFor(EtatPartie.class)
                            .readValue(response.body());
                    return resultat;
                }
                case NOT_FOUND: {
                    throw new MotInexistantException();
                }
                case NOT_ACCEPTABLE: {
                    throw new MaxNbCoupsException();
                }

                case BAD_REQUEST: {
                    throw new TicketInvalideException();
                }
                default: {
                    System.out.println(response.statusCode());
                    throw new CodeErreurInnatendu();
                }
            }
        }catch (IOException| InterruptedException e){
            throw new ConnexionServeurException();
        }


        }

    @Override
    public List<String> getPropositions(String tokenPartie) throws TicketInvalideException, PartieInexistanteException, CodeErreurInnatendu, ConnexionServeurException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI_PARTIE))
                .GET()
                .header(TOKEN_PARTIE, tokenPartie)
                .build();


        try {

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {
                case OK: {
                    CollectionType collectionType =
                            objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);

                    return objectMapper.readValue(response.body(), collectionType);
                }


                case NOT_FOUND: {
                    throw new PartieInexistanteException();

                }
                case BAD_REQUEST: {
                    throw new TicketInvalideException();
                }
                default: {
                    System.out.println(response.statusCode());
                    throw new CodeErreurInnatendu();

                }
            }


        } catch (InterruptedException |IOException e) {
            throw new ConnexionServeurException();
        }


    }
}
