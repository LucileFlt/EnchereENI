package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.Retrait;

import java.util.List;

public interface RetraitService {
    int addRetrait(Retrait retrait);

    Retrait getRetrait(int id);

    List<Retrait> getAllRetrait();

    void updateRetrait(Retrait retrait);

    void deleteRetrait(Retrait retrait);
}
