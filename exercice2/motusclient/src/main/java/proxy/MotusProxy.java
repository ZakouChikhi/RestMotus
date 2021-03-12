package proxy;

import exceptions.*;
import proxy.dto.EtatPartie;

import java.util.List;

public interface MotusProxy {

    String creerUnCompte(String pseudo) throws PseudoDejaPrisException, ConnexionServeurException;
    String creerUnePartie(String tokenAuthentification) throws IdentiteInvalide, JoueurNonValideException, ConnexionServeurException, CodeErreurInnatendu;
    EtatPartie proposerMot(String tokenPartie, String proposition) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException, CodeErreurInnatendu, ConnexionServeurException;
    List<String> getPropositions(String tokenPartie) throws TicketInvalideException, PartieInexistanteException, CodeErreurInnatendu, ConnexionServeurException;
}
