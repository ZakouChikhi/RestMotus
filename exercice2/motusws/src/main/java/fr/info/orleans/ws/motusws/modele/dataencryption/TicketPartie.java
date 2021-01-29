package fr.info.orleans.ws.motusws.modele.dataencryption;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class TicketPartie  {


    public TicketPartie() {

    }

    public TicketPartie(String joueurCreateur, long id) {
        this.idPartie = id;
        this.joueurCreateur = joueurCreateur;
        this.dateCreation = LocalDateTime.now();
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    private long idPartie; // Identifiant de la partie concernée par l'invitation
    private String joueurCreateur; // Créateur de la partie concernée par l'invitation

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime dateCreation;

    public long getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(long idPartie) {
        this.idPartie = idPartie;
    }

    public String getJoueurCreateur() {
        return joueurCreateur;
    }

    public void setJoueurCreateur(String joueurCreateur) {
        this.joueurCreateur = joueurCreateur;
    }
}
