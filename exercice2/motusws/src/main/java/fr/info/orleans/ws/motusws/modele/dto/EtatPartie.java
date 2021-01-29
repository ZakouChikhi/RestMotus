package fr.info.orleans.ws.motusws.modele.dto;

public class EtatPartie {

    private String verdict;
    private int nbTentativesRestantes;

    public EtatPartie() {
    }

    public EtatPartie(String verdict, int nbTentativesRestantes) {
        this.verdict = verdict;
        this.nbTentativesRestantes = nbTentativesRestantes;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public int getNbTentativesRestantes() {
        return nbTentativesRestantes;
    }

    public void setNbTentativesRestantes(int nbTentativesRestantes) {
        this.nbTentativesRestantes = nbTentativesRestantes;
    }
}
