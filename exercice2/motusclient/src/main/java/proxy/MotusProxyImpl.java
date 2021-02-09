package proxy;


import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import proxy.dto.EtatPartie;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class MotusProxyImpl implements MotusProxy{

    private static final String HOST = "http://localhost:8080/motus";
    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String creerUnCompte(String pseudo) throws PseudoDejaPrisException {


        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(HOST+"/joueur?pseudo="+pseudo))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

   
        try {
            HttpResponse response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
         return response.headers().firstValue("token").get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String creerUnePartie(String tokenAuthentification) {
        return null;
    }

    @Override
    public EtatPartie proposerMot(String tokenPartie, String proposition) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException {
        return null;
    }

    @Override
    public List<String> getPropositions(String tokenPartie) throws TicketInvalideException, PartieInexistanteException {
        return null;
    }
}
