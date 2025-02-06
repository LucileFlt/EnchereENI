package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.Categorie;

import java.util.List;

public interface CategorieService {
    int addCategories(Categorie categori);

    void removeCategories(int id);

    List<Categorie> getCategories();
    Categorie getCategorieById (int id);

    Categorie getCategorieByName(String libelle);

    void update (Categorie categorie);

}
