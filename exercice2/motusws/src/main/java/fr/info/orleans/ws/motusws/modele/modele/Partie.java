package fr.info.orleans.ws.motusws.modele.modele;


import fr.info.orleans.ws.motusws.modele.dto.EtatPartie;
import fr.info.orleans.ws.motusws.modele.exceptions.MaxNbCoupsException;
import fr.info.orleans.ws.motusws.modele.exceptions.MotInexistantException;

import java.util.ArrayList;
import java.util.List;

/**
 * La partie d'un joueur, avec ses essais successifs et le dictionnaire utilisé
 */
public class Partie {
    public static final int MAX_NB_COUPS = 8;
    private String motRecherche;
    private Dico leDico;
    private List<String> essais;
    private long identifiant;



    public Partie(long id, Dico dico) {
        essais = new ArrayList<>();
        leDico = dico;
        motRecherche = dico.getRandomMot();
        identifiant = id;
    }

    public long getIdentifiant() {
        return identifiant;
    }

    public EtatPartie jouer(String mot) throws MotInexistantException, MaxNbCoupsException {
        if (essais.size()>= MAX_NB_COUPS) throw new MaxNbCoupsException();
        String motMaj = mot.toUpperCase();
        essais.add(motMaj);
        // le mot n'existe pas
        if (!leDico.isMot(motMaj)) throw new MotInexistantException(mot);

        String res = correspondance(motMaj);
        EtatPartie etatPartie = new EtatPartie(res,MAX_NB_COUPS-getNbEssais());
        return etatPartie;
    }

    public List<String> getEssais() {
        return essais;
    }

    public int getNbEssais() {
        return essais.size();
    }

    /*
    fonction qui recherche la correspondance des lettres entre le mot proposé et le mot recherché

    elle renvoie une chaine avec le résultat pour chaque lettre du mot proposé,
    avec un X pour une lettre à la bonne place
         un m pour une lettre mal placée
         un * pour les mauvaises lettres

    Exemples : si le mot recherché est CITRON, on a :
            le mot proposé  CASTOR
            renvoie         X**mXm
            le mot proposé  CINEMA
            renvoie         XXm***
            le mot proposé  CYPRES
            renvoie         X**X**
            le mot proposé  CITRON
            renvoie         XXXXXX
     */
    public String correspondance(String mot) {
        char[] resultat = new char[mot.length()];
        boolean[] match = new boolean[motRecherche.length()];
        // marque les lettres à la bonne place
        for(int i = 0; i<mot.length();i++) {
            char c = mot.charAt(i);
            if (c==motRecherche.charAt(i)) {
                resultat[i]='X';
                match[i] = true;
            } else {
                resultat[i]='*';
                match[i] = false;
            }
        }
        // les lettres à la mauvaise place
        for(int i = 0; i<mot.length();i++) {
            if (resultat[i]!='X') {
                char c = mot.charAt(i);
                int start = 0;
                while (start>=0&&start<motRecherche.length()) {
                    int j = motRecherche.indexOf(c,start);
                    if (j>=0 && !match[j]) {
                        resultat[i] = 'm';
                        match[j]=true;
                        start = -1;
                    } else {
                        start = (j>=0?j+1:j);
                    }
                }
            }
        }
        return new String(resultat);
    }

    public String getMotRecherche() {
        return motRecherche;
    }

    public void setMotRecherche(String motRecherche) {
        this.motRecherche = motRecherche;
    }

    public Dico getDico() {
        return leDico;
    }
}
