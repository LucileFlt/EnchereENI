package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bll.ArticleVenduService;
import fr.eni.projet.projeteni.bll.CategorieService;
import fr.eni.projet.projeteni.bll.UtilisateurService;
import fr.eni.projet.projeteni.bo.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EnchereRowMapper implements RowMapper<Enchere> {

    private UtilisateurService utilisateurService;
    private ArticleVenduService articleVenduService;
    private CategorieService categorieService;

    public EnchereRowMapper(UtilisateurService utilisateurService, ArticleVenduService articleVenduService) {
        this.utilisateurService = utilisateurService;
        this.articleVenduService = articleVenduService;
    }

    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enchere enchere = new Enchere();
        enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime().toLocalDate());
        enchere.setMontant_enchere(rs.getInt("montant_enchere"));



        Utilisateur lastBidder = new Utilisateur();
        lastBidder.setNoUtilisateur(rs.getInt("no_utilisateur"));
        lastBidder.setPseudo(rs.getString("enchere_pseudo"));
        lastBidder.setNom(rs.getString("enchere_nom"));
        lastBidder.setPrenom(rs.getString("enchere_prenom"));
        lastBidder.setEmail(rs.getString("enchere_email"));
        lastBidder.setTelephone(rs.getString("enchere_telephone"));
        lastBidder.setRue(rs.getString("enchere_utilisateur_rue"));
        lastBidder.setCodePostal(rs.getInt("enchere_utilisateur_code_postal"));
        lastBidder.setVille(rs.getString("enchere_utilisateur_ville"));
        lastBidder.setCredit(rs.getInt("enchere_credit"));

        Utilisateur vendeur = new Utilisateur();
        vendeur.setNoUtilisateur(rs.getInt("no_utilisateur_vendeur"));
        vendeur.setPseudo(rs.getString("article_pseudo"));
        vendeur.setNom(rs.getString("article_nom"));
        vendeur.setPrenom(rs.getString("article_prenom"));
        vendeur.setEmail(rs.getString("article_email"));
        vendeur.setTelephone(rs.getString("article_telephone"));
        vendeur.setRue(rs.getString("article_utilisateur_rue"));
        vendeur.setCodePostal(rs.getInt("article_utilisateur_code_postal"));
        vendeur.setVille(rs.getString("article_utilisateur_ville"));
        vendeur.setCredit(rs.getInt("article_credit"));

        Categorie categorie = new Categorie();
        categorie.setLibelle(rs.getString("categorie_libelle"));

        Retrait retrait = new Retrait();
        retrait.setRue(rs.getString("retrait_rue"));
        retrait.setVille(rs.getString("retrait_ville"));
        retrait.setCode_postal(rs.getInt("retrait_code_postal"));

        ArticleVendu article = new ArticleVendu();
        article.setNoArticle(rs.getInt("no_article"));
        article.setNomArticle(rs.getString("nom_article"));
        article.setDescription(rs.getString("description"));
        article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime().toLocalDate());
        article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime().toLocalDate());
        article.setMiseAPrix(rs.getInt("prix_initial"));
        article.setPrixVente(rs.getInt("prix_vente"));
        article.setCategorie(categorie);
        article.setLieuRetrait(retrait);
        article.setVendeur(vendeur);


        enchere.setArticleVendu(article);
        enchere.setLastBidder(lastBidder);

        return enchere;
    }
}
