package fr.info.orleans.ws.motusws.modele.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.info.orleans.ws.motusws.modele.dataencryption.JsonDateDeserializer;
import fr.info.orleans.ws.motusws.modele.dataencryption.JsonDateSerializer;

import java.time.LocalDateTime;

public class Joueur {

    private String nom;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime dateInscription;

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Joueur() {
    }

    public Joueur(String nom) {
        this();
        this.nom = nom;
        this.dateInscription = LocalDateTime.now();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
