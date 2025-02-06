package fr.eni.projet.projeteni.bo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Enchere {

    private LocalDate dateEnchere;
    private int montant_enchere;
    private Utilisateur lastBidder;
    private ArticleVendu articleVendu;

    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, int montant_enchere, Utilisateur lastBidder, ArticleVendu articleVendu) {
        this.dateEnchere = dateEnchere;
        this.montant_enchere = montant_enchere;
        this.lastBidder = lastBidder;
        this.articleVendu = articleVendu;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(int montant_enchere) {
        this.montant_enchere = montant_enchere;
    }

    public Utilisateur getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(Utilisateur lastBidder) {
        this.lastBidder = lastBidder;
    }

    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "dateEnchere=" + dateEnchere +
                ", montant_enchere=" + montant_enchere +
                ", lastBidder=" + lastBidder +
                ", articleVendu=" + articleVendu +
                '}';
    }
}
