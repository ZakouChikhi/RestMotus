package fr.info.orleans.ws.motusws.modele.facade;

import fr.info.orleans.ws.motusws.modele.exceptions.IdentiteInvalide;
import fr.info.orleans.ws.motusws.modele.exceptions.JoueurNonValideException;
import fr.info.orleans.ws.motusws.modele.exceptions.PseudoDejaPrisException;
import fr.info.orleans.ws.motusws.modele.modele.Joueur;

public interface FacadeJoueurs {

    String inscription(String pseudo) throws PseudoDejaPrisException;

    Joueur isJoueurValide(String identiteeChiffree) throws IdentiteInvalide, JoueurNonValideException;

}
