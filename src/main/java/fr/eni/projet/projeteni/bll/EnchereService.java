package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.Enchere;

import java.util.List;

public interface EnchereService {
    Enchere getEnchere(int noArticle);

    List<Enchere> getAllEnchere();

    int addEnchere(Enchere enchere);

    void updateEnchere(Enchere enchere);

    void deleteEnchere(Enchere enchere);
}
