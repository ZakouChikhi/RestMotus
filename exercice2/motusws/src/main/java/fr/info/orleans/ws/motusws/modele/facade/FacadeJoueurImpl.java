package fr.info.orleans.ws.motusws.modele.facade;

import fr.info.orleans.ws.motusws.modele.dataencryption.IdentiteChiffrement;
import fr.info.orleans.ws.motusws.modele.exceptions.IdentiteInvalide;
import fr.info.orleans.ws.motusws.modele.exceptions.JoueurNonValideException;
import fr.info.orleans.ws.motusws.modele.exceptions.PseudoDejaPrisException;
import fr.info.orleans.ws.motusws.modele.modele.Joueur;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component("facadeJoueurs")
public class FacadeJoueurImpl implements FacadeJoueurs{

    private Map<String,Joueur> joueursInscrits;
    private IdentiteChiffrement identiteChiffrement;


    public FacadeJoueurImpl() {
        joueursInscrits = new HashMap<>();
        identiteChiffrement = new IdentiteChiffrement("Superclésecrètepourlesutilisateurs");
    }

    @Override
    public String inscription(String pseudo) throws PseudoDejaPrisException {
        if (joueursInscrits.containsKey(pseudo)) {
            throw new PseudoDejaPrisException();
        }
        Joueur joueur = new Joueur(pseudo);
        joueursInscrits.put(pseudo,joueur);
        return identiteChiffrement.chiffrement(joueur);
    }

    @Override
    public Joueur isJoueurValide(String identiteeChiffree) throws IdentiteInvalide, JoueurNonValideException {
        Joueur resultat = identiteChiffrement.dechiffrement(identiteeChiffree);
        if (joueursInscrits.containsKey(resultat.getNom())) {
            return resultat;
        }
        else {
            throw new JoueurNonValideException();
        }
    }
}
