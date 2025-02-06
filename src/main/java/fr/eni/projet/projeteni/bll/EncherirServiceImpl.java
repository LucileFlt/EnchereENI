package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.ArticleVendu;
import fr.eni.projet.projeteni.bo.Enchere;
import fr.eni.projet.projeteni.bo.Utilisateur;
import fr.eni.projet.projeteni.dal.DaoArticleVendu;
import fr.eni.projet.projeteni.dal.DaoEncheres;
import fr.eni.projet.projeteni.dal.UtilisateurDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EncherirServiceImpl implements EncherirService {

    private DaoArticleVendu daoArticleVendu;
    private UtilisateurDao utilisateurDao;
    private DaoEncheres daoEncheres;

    public EncherirServiceImpl(DaoArticleVendu daoArticleVendu, UtilisateurDao utilisateurDao, DaoEncheres daoEncheres) {
        this.daoArticleVendu = daoArticleVendu;
        this.utilisateurDao = utilisateurDao;
        this.daoEncheres = daoEncheres;
    }

    @Override
    public void encherir(int valeurEnchere, int id, Utilisateur utilisateur) {
        Enchere enchere = daoEncheres.read(id);
        System.out.println(enchere);
        System.out.println(utilisateur);
        if (enchere.getArticleVendu().getVendeur().getPseudo() == utilisateur.getPseudo()) {
            throw new IllegalArgumentException("Vous ne pouvez pas surenchérir sur votre propre enchère");
        }
        if (enchere.getArticleVendu().getDateDebutEncheres().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("L'enchère n'a pas encore commencé.");
        }
        if (enchere.getArticleVendu().getDateFinEncheres().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'enchère est terminée.");
        }

        if (utilisateur.getCredit() < valeurEnchere) {
            throw new IllegalArgumentException("Vous n'avez pas assez de crédit");
        }

        int prixActuel = enchere.getArticleVendu().getMiseAPrix();
        if (prixActuel > valeurEnchere) {
            throw new IllegalArgumentException("L'enchère doit etre superieur au prix actuel");
        }

        int oldMontant = enchere.getMontant_enchere();
        Utilisateur lastBidder = utilisateurDao.read(enchere.getLastBidder().getEmail());

        ArticleVendu articleVendu = daoArticleVendu.read(id);
        articleVendu.setPrixVente(valeurEnchere);

        utilisateur.setCredit(utilisateur.getCredit() - valeurEnchere);
        utilisateurDao.update(utilisateur);

        System.out.println("actualBidder : " + utilisateur);
        System.out.println("lastBidder : " + enchere.getLastBidder());

        enchere.setLastBidder(utilisateur);
        enchere.setMontant_enchere(valeurEnchere);
        daoEncheres.update(enchere);

        System.out.println(enchere);

        if (enchere.getLastBidder() != null){
            lastBidder.setCredit(lastBidder.getCredit() + oldMontant);
            utilisateurDao.update(lastBidder);
        }

    }
}