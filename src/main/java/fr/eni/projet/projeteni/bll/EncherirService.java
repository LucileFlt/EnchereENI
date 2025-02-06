package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.Utilisateur;

public interface EncherirService {
    void encherir(int valeurEnchere, int id, Utilisateur utilisateur);
}
