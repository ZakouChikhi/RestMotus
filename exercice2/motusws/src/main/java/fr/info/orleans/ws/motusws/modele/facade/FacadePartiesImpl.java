package fr.info.orleans.ws.motusws.modele.facade;


import fr.info.orleans.ws.motusws.modele.dataencryption.TicketChiffrement;
import fr.info.orleans.ws.motusws.modele.dataencryption.TicketPartie;
import fr.info.orleans.ws.motusws.modele.dto.EtatPartie;
import fr.info.orleans.ws.motusws.modele.exceptions.MaxNbCoupsException;
import fr.info.orleans.ws.motusws.modele.exceptions.MotInexistantException;
import fr.info.orleans.ws.motusws.modele.exceptions.PartieInexistanteException;
import fr.info.orleans.ws.motusws.modele.exceptions.TicketInvalideException;
import fr.info.orleans.ws.motusws.modele.modele.Dico;
import fr.info.orleans.ws.motusws.modele.modele.Partie;
import org.springframework.stereotype.Component;

import java.util.*;


@Component("facadeParties")
public class FacadePartiesImpl implements FacadeParties {

    private Map<Long, Partie> parties = new HashMap<>();

    private Map<String, Collection<Partie>> partiesParJoueur = new HashMap<>();

    private TicketChiffrement ticketChiffrement = new TicketChiffrement("clesecretepourchiffrerlestickets");

    private static long IDs = 0;


    @Override
    public String nouvellePartie(String pseudo)  {
        Partie nouvellePartie = new Partie(IDs,Dico.getInstance(Dico.DEFAULT_FILENAME));
        this.parties.put(nouvellePartie.getIdentifiant(),nouvellePartie);
        if (partiesParJoueur.containsKey(pseudo)) {
            this.partiesParJoueur.get(pseudo).add(nouvellePartie);
        }
        else {
            Collection<Partie> lesParties = new ArrayList<>();
            lesParties.add(nouvellePartie);
            this.partiesParJoueur.put(pseudo,lesParties);
        }

        TicketPartie ticketPartie = new TicketPartie(pseudo,nouvellePartie.getIdentifiant());
        IDs++;
        return ticketChiffrement.chiffrement(ticketPartie);
    }


    @Override
    public EtatPartie jouer(String ticketChiffre, String mot) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException {
        TicketPartie ticketPartie = ticketChiffrement.dechiffrement(ticketChiffre);
        return parties.get(ticketPartie.getIdPartie()).jouer(mot);
    }



    public Collection<String> getListeDicos(){
        return Arrays.asList("dico7lettres","dicosimple7lettres");
    }


    @Override
    public List<String> getMotsJoues(String ticket) throws TicketInvalideException,
            PartieInexistanteException {
        TicketPartie ticketPartie = ticketChiffrement.dechiffrement(ticket);
        if (parties.containsKey(ticketPartie.getIdPartie())) {
            return parties.get(ticketPartie.getIdPartie()).getEssais();
        }
        else
            throw new PartieInexistanteException();
    }


    @Override
    public int getNbEssais(String ticket) throws TicketInvalideException, PartieInexistanteException {
        TicketPartie ticketPartie = ticketChiffrement.dechiffrement(ticket);
        if (parties.containsKey(ticketPartie.getIdPartie())) {
            return parties.get(ticketPartie.getIdPartie()).getNbEssais();
        }
        else
            throw new PartieInexistanteException();

    }

    @Override
    public void clorePartie(String ticket) throws TicketInvalideException {
        TicketPartie ticketPartie = ticketChiffrement.dechiffrement(ticket);
        Partie p = parties.get(ticketPartie.getIdPartie());
        partiesParJoueur.get(ticketPartie.getJoueurCreateur()).remove(p);
        parties.remove(p.getIdentifiant());
    }


    @Override
    public void resetAll() {
        this.parties.clear();
        this.partiesParJoueur.clear();
    }

}
