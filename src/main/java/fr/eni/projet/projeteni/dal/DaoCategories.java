package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Categorie;

import java.util.List;

public interface DaoCategories {
    List<Categorie> read();
    Categorie read(int noCategorie);

    Categorie read(String libelle);

    int create(Categorie categorie);
    void update(Categorie categorie);
    void delete(Categorie categorie);
    void delete(int noCategorie);
}
