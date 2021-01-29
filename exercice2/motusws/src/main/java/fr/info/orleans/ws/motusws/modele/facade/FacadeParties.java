package fr.info.orleans.ws.motusws.modele.facade;

import fr.info.orleans.ws.motusws.modele.dataencryption.TicketPartie;
import fr.info.orleans.ws.motusws.modele.dto.EtatPartie;
import fr.info.orleans.ws.motusws.modele.exceptions.MaxNbCoupsException;
import fr.info.orleans.ws.motusws.modele.exceptions.MotInexistantException;
import fr.info.orleans.ws.motusws.modele.exceptions.PartieInexistanteException;
import fr.info.orleans.ws.motusws.modele.exceptions.TicketInvalideException;

import java.util.List;

public interface FacadeParties {
    String nouvellePartie(String pseudo);

    EtatPartie jouer(String ticketChiffre, String mot) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException;

    List<String> getMotsJoues(String ticket) throws TicketInvalideException, PartieInexistanteException;

    int getNbEssais(String ticket) throws TicketInvalideException, PartieInexistanteException;

    void clorePartie(String ticket) throws TicketInvalideException;

    void resetAll();
}
