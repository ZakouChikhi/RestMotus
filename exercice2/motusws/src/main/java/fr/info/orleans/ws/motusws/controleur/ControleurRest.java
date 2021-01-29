package fr.info.orleans.ws.motusws.controleur;

import fr.info.orleans.ws.motusws.modele.dto.EtatPartie;
import fr.info.orleans.ws.motusws.modele.exceptions.*;
import fr.info.orleans.ws.motusws.modele.facade.FacadeJoueurs;
import fr.info.orleans.ws.motusws.modele.facade.FacadeParties;
import fr.info.orleans.ws.motusws.modele.modele.Joueur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/motus", produces = MediaType.APPLICATION_JSON_VALUE)

public class ControleurRest {


    @Autowired
    FacadeJoueurs facadeJoueurs;


    @Autowired
    FacadeParties facadeParties;


    @PostMapping("/joueur")
    public ResponseEntity<String> inscription(@RequestParam String pseudo) {
        try {
            String ticketIdentite = facadeJoueurs.inscription(pseudo);
            return ResponseEntity.status(HttpStatus.CREATED).header("token",ticketIdentite).build();
        } catch (PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/partie")
    public ResponseEntity<String> creationPartie(@RequestHeader String token) {
        try {
            Joueur joueur = facadeJoueurs.isJoueurValide(token);
            String tokenPartie = facadeParties.nouvellePartie(joueur.getNom());
            return ResponseEntity.status(HttpStatus.CREATED).header("tokenPartie",tokenPartie).build();
        } catch (IdentiteInvalide identiteInvalide) {
            return ResponseEntity.badRequest().build();
        } catch (JoueurNonValideException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/partie")
    public ResponseEntity<EtatPartie> jouer(@RequestHeader String tokenPartie, @RequestParam String proposition) {
        try {
            EtatPartie resultat = this.facadeParties.jouer(tokenPartie, proposition);
            return ResponseEntity.ok(resultat);
        } catch (MotInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (MaxNbCoupsException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (TicketInvalideException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/partie")
    public ResponseEntity<List<String>> getEssais(@RequestHeader String tokenPartie) {
        try {
            return ResponseEntity.ok(facadeParties.getMotsJoues(tokenPartie));
        }

        catch (TicketInvalideException e) {
            return ResponseEntity.badRequest().build();
        }

        catch (PartieInexistanteException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/partie/nbcoups")
    public ResponseEntity<Integer> getNbEssais(@RequestHeader String tokenPartie) {
        try {
            return ResponseEntity.ok(facadeParties.getNbEssais(tokenPartie));
        }

        catch (TicketInvalideException e) {
            return ResponseEntity.badRequest().build();
        }

        catch (PartieInexistanteException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
