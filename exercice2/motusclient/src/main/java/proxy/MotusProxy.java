package proxy;

import exceptions.*;
import proxy.dto.EtatPartie;

import java.util.List;

public interface MotusProxy {

    String creerUnCompte(String pseudo) throws PseudoDejaPrisException;
    String creerUnePartie(String tokenAuthentification);
    EtatPartie proposerMot(String tokenPartie, String proposition) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException;
    List<String> getPropositions(String tokenPartie) throws TicketInvalideException, PartieInexistanteException;
}
