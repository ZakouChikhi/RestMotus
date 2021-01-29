package fr.info.orleans.ws.motusws.modele.exceptions;

/**
 * Created by Fred on 06/06/2016.
 */
public class MaxNbCoupsException extends Exception {
    public MaxNbCoupsException() {
        super("nombre maximum de coups atteint");
    }
}
