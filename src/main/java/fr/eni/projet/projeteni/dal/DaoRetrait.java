package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Retrait;

import java.util.List;

public interface DaoRetrait {
    List<Retrait> read();

    Retrait read(int idArticle);

    int create(Retrait retrait);

    void update(Retrait retrait);

    void delete(Retrait retrait);
}
