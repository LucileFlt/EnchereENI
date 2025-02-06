package fr.eni.projet.projeteni.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {

    private int noCategorie;
    private String libelle;
    private List<ArticleVendu> articleVendus = new ArrayList<ArticleVendu>();

    public Categorie() {
    }

    public Categorie(int noCategorie, String libelle, List<ArticleVendu> articleVendus) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
        this.articleVendus = articleVendus;
    }

    public  int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public  String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<ArticleVendu> getArticleVendus() {
        return articleVendus;
    }

    public void setArticleVendus(List<ArticleVendu> articleVendus) {
        this.articleVendus = articleVendus;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "noCategorie=" + noCategorie +
                ", libelle='" + libelle + '\'' +
                ", articleVendus=" + articleVendus +
                '}';
    }
}
