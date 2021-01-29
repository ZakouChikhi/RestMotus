package proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import proxy.dto.EtatPartie;

import java.net.http.HttpClient;
import java.util.List;

public class MotusProxyImpl implements MotusProxy{

    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String creerUnCompte(String pseudo) throws PseudoDejaPrisException {
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
