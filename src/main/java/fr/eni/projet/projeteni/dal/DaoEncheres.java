package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Enchere;

import java.util.List;

public interface DaoEncheres {
    List<Enchere> read();

    Enchere read(int idArticle);

    int create(Enchere enchere);

    void update(Enchere enchere);

    void delete(Enchere enchere);
}
