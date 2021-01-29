import exceptions.*;
import proxy.MotusProxy;
import proxy.MotusProxyImpl;
import proxy.dto.EtatPartie;

import java.util.List;

public class Main {


    public static void main(String[] args) throws PseudoDejaPrisException, TicketInvalideException, PartieInexistanteException, MaxNbCoupsException, MotInexistantException {
        MotusProxy motusProxy = new MotusProxyImpl();
        String tokenAuthentification = motusProxy.creerUnCompte("Yohan");
        String tokenPartie = motusProxy.creerUnePartie(tokenAuthentification);
        List<String> tentatives = motusProxy.getPropositions(tokenPartie);
        EtatPartie etat = motusProxy.proposerMot(tokenPartie,"acheter");
        tentatives = motusProxy.getPropositions(tokenPartie);
    }
}
