package fr.eni.projet.projeteni;

import fr.eni.projet.projeteni.bll.*;
import fr.eni.projet.projeteni.bo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ProjetEniApplicationTests {

    @Autowired
    private EnchereService enchereService;
    @Autowired
    private ArticleVenduService articleVenduService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private RetraitService retraitService;


    @Test
    void testDaoEnchere() {
        Utilisateur user = new Utilisateur();
        user.setNoUtilisateur(1);
        user.setPseudo("user1");
        user.setNom(("nom"));
        user.setPrenom(("prenom"));
        user.setEmail(("jean.dupont@email.com"));
        user.setTelephone(("telephone"));
        user.setRue(("utilisateur_rue"));
        user.setCodePostal(35200);
        user.setVille(("utilisateur_ville"));

        Categorie categorie = new Categorie();
        categorie.setNoCategorie(1);
        categorie.setLibelle(("categorie_libelle"));

        Retrait retrait = new Retrait();
        retrait.setRue(("retrait_rue"));
        retrait.setVille(("Rennes"));
        retrait.setCode_postal(35200);

        ArticleVendu article = new ArticleVendu();
        article.setNoArticle(1);
        article.setNomArticle(("nom_article"));
        article.setDescription(("description"));
        article.setDateDebutEncheres(LocalDate.now());
        article.setDateFinEncheres(LocalDate.now().plusDays(4));
        article.setMiseAPrix(100);
        article.setPrixVente(100);
        article.setCategorie(categorie);
        article.setLieuRetrait(retrait);
        article.setVendeur(user);

        Utilisateur lastBidder = new Utilisateur();
        lastBidder.setNoUtilisateur(2);
        lastBidder.setPseudo("user2");
        lastBidder.setNom(("last_bidder"));
        lastBidder.setPrenom(("last_bidder"));
        lastBidder.setEmail(("claire.martin@email.com"));


        Enchere enchere = new Enchere();
        enchere.setArticleVendu(article);
        enchere.setDateEnchere(LocalDate.now());
        enchere.setMontant_enchere(100);
        enchere.setLastBidder(lastBidder);

        System.out.println(enchere.toString());
        enchereService.addEnchere(enchere);

        enchere.setDateEnchere(LocalDate.now().plusDays(4));
        enchere.setMontant_enchere(150);

        System.out.println(enchere.toString());
        enchereService.updateEnchere(enchere);

        Enchere enchere2 = enchereService.getEnchere(2);
        System.out.println(enchere2.toString());

        enchereService.deleteEnchere(enchere);
    }

    @Test
    void testDaoArticleVendu() {
        Utilisateur user = new Utilisateur();
        user.setNoUtilisateur(1);
        user.setPseudo("user1");
        user.setNom(("nom"));
        user.setPrenom(("prenom"));
        user.setEmail(("jean.dupont@email.com"));
        user.setTelephone(("telephone"));
        user.setRue(("utilisateur_rue"));
        user.setCodePostal(35200);
        user.setVille(("utilisateur_ville"));

        Categorie categorie = new Categorie();
        categorie.setNoCategorie(1);
        categorie.setLibelle(("categorie_libelle"));

        Retrait retrait = new Retrait();
        retrait.setRue(("retrait_rue"));
        retrait.setVille(("Rennes"));
        retrait.setCode_postal(35200);

        ArticleVendu article = new ArticleVendu();
        article.setNoArticle(1);
        article.setNomArticle(("nom_article"));
        article.setDescription(("description"));
        article.setDateDebutEncheres(LocalDate.now());
        article.setDateFinEncheres(LocalDate.now().plusDays(4));
        article.setMiseAPrix(100);
        article.setPrixVente(100);
        article.setCategorie(categorie);
        article.setLieuRetrait(retrait);
        article.setVendeur(user);

        int idArticle = articleVenduService.createArticleVendu(article);

        System.out.println(articleVenduService.getArticleVenduById(idArticle));

        article.setPrixVente(150);

        articleVenduService.updateArticleVendu(article);

        System.out.println(articleVenduService.getArticleVenduById(idArticle));

        articleVenduService.deleteArticleVendu(idArticle);

        List<ArticleVendu> articleVendus = articleVenduService.getAllArticleVendu();
        for (ArticleVendu articleVendu : articleVendus) {
            System.out.println(articleVendu.toString());
        }
    }

    @Test
    void testDaoCategorie() {

        Categorie categorie = new Categorie();
        categorie.setLibelle(("Development"));

        int id = categorieService.addCategories(categorie);

        System.out.println(categorieService.getCategorieById(id));

        categorie.setNoCategorie(id);
        categorie.setLibelle(("Production"));
        categorieService.update(categorie);
        System.out.println(categorieService.getCategorieById(id));

        categorieService.removeCategories(id);

        List<Categorie> categories = categorieService.getCategories();
        for (Categorie c : categories) {
            System.out.println(c.toString());
        }


    }

    @Test
    void testDaoUtilisateur() {

        Utilisateur user = new Utilisateur();
        user.setPseudo("user6");
        user.setNom(("nom"));
        user.setPrenom(("prenom"));
        user.setMotDePasse("motDePasse");
        user.setEmail(("jeanine@email.com"));
        user.setTelephone(("telephone"));
        user.setRue(("utilisateur_rue"));
        user.setCodePostal(35200);
        user.setVille(("utilisateur_ville"));
        user.setCredit(100);

        int id = utilisateurService.addUtilisateur(user);

        System.out.println(utilisateurService.getUtilisateur(id));

        user.setNoUtilisateur(id);
        user.setCredit(150);
        user.setTelephone("0000000000");

        utilisateurService.updateUtilisateur(user);
        System.out.println(utilisateurService.getUtilisateur(id));

        utilisateurService.deleteUtilisateur(user);

        List<Utilisateur> users = utilisateurService.getAllUtilisateur();
        for (Utilisateur u : users) {
            System.out.println(u);
        }

    }

//    @Test
//    void testDaoRetrait() {
//
//        Utilisateur user = new Utilisateur();
//        user.setNoUtilisateur(1);
//        user.setPseudo("user1");
//        user.setNom(("nom"));
//        user.setPrenom(("prenom"));
//        user.setEmail(("jean.dupont@email.com"));
//        user.setTelephone(("telephone"));
//        user.setRue(("utilisateur_rue"));
//        user.setCodePostal(35200);
//        user.setVille(("utilisateur_ville"));
//
//        Categorie categorie = new Categorie();
//        categorie.setNoCategorie(1);
//        categorie.setLibelle(("categorie_libelle"));
//
//        Retrait retrait = new Retrait();
//        retrait.setId_article(6);
//        retrait.setRue(("retrait_rue"));
//        retrait.setVille(("retrait_ville"));
//        retrait.setCode_postal(35200);
//
//        ArticleVendu article = new ArticleVendu();
//        article.setNoArticle(6);
//        article.setNomArticle(("nom_article"));
//        article.setDescription(("description"));
//        article.setDateDebutEncheres(LocalDate.now());
//        article.setDateFinEncheres(LocalDate.now().plusDays(4));
//        article.setMiseAPrix(100);
//        article.setPrixVente(100);
//        article.setCategorie(categorie);
//        article.setLieuRetrait(retrait);
//        article.setVendeur(user);
//
//        articleVenduService.createArticleVendu(article);
//        retraitService.addRetrait(retrait);
//
//        System.out.println(retraitService.getRetrait(6));
//
//        retrait.setCode_postal(35700);
//        retrait.setVille(("rennes"));
//        retrait.setRue(("iznogood"));
//        retraitService.updateRetrait(retrait);
//        System.out.println(retraitService.getRetrait(6));
//
//        retraitService.deleteRetrait(retrait);
//        articleVenduService.deleteArticleVendu(6);
//
//    }
}
