package fr.eni.projet.projeteni.controller;



import fr.eni.projet.projeteni.bo.*;
import fr.eni.projet.projeteni.bll.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("activeUser")
@RequestMapping("/encheres")
public class EncheresController {

    private ArticleVenduService articleVenduService;
    private EnchereService enchereService;
    private UtilisateurService utilisateurService;
    private CategorieService categorieService;
    private EncherirService encherirService;


    public EncheresController(ArticleVenduService articleVenduService, EnchereService enchereService, UtilisateurService utilisateurService, CategorieService categorieService, EncherirService encherirService) {
        this.articleVenduService = articleVenduService;
        this.enchereService = enchereService;
        this.utilisateurService = utilisateurService;
        this.categorieService = categorieService;
        this.encherirService = encherirService;
    }

    //DOESNT WORK "MISSING VIEW CODE"
    @GetMapping("/detail")
    public String afficherEnchereDetail(@RequestParam(name = "id") int id , Model model) {
        model.addAttribute("enchere", enchereService.getEnchere(id));

        System.out.println(enchereService.getEnchere(id));
        return "view-detail-vente";
    }

    @PostMapping("/detail")
    public String encherir(@RequestParam(name = "id") int id ,@RequestParam(name = "bid") int bid ,Principal principal, Model model) {
        String userName = principal.getName();
        Utilisateur activeUser = utilisateurService.getUtilisateur(userName);
        encherirService.encherir(bid,id,activeUser);
        return "redirect:/encheres";
    }

    @GetMapping
    public String encheres(@RequestParam(value = "nomArticle", required = false) String nomArticle,
                           @RequestParam(value = "categorie", required = false) Long categorieId,
                           Model model) {
        List<ArticleVendu> articles;


        if (categorieId != null) {
            if (nomArticle != null && !nomArticle.isEmpty()) {
                articles = articleVenduService.getArticlesByCategoryAndName(categorieId, nomArticle);
            } else {
                articles = articleVenduService.getArticlesByCategory(categorieId);
            }
        } else if (nomArticle != null && !nomArticle.isEmpty()) {
            articles = articleVenduService.getArticlesByName(nomArticle);
        } else {
            articles = articleVenduService.getAllArticleVendu();
        }


        model.addAttribute("articles", articles);
        model.addAttribute("categories", categorieService.getCategories());

        return "view-encheres";
    }



    // Get the form to fill
    @GetMapping("/cree")
    public String creeEnchere(Model model) {
        // Add categories to the model for the form
        model.addAttribute("categories", categorieService.getCategories());
        return "view-creation-vente";  // Your view name (HTML file)
    }


    // Post the filled form
    @PostMapping("/cree")
    public String creeEnchere(
            @RequestParam(name = "nom") String nom,
            Principal principal,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "categorie") String categorie,
            @RequestParam(name = "prix") int prix,
            @RequestParam(name = "debut") LocalDate debut,
            @RequestParam(name = "fin") LocalDate fin,
            @RequestParam(name = "rue") String rue,
            @RequestParam(name = "code_postal") int code_postal,
            @RequestParam(name = "ville") String ville) {

        String userName = principal.getName();
        Utilisateur activeUser = utilisateurService.getUtilisateur(userName);

        // Get the active user (the seller) from the session or context
        Utilisateur utilisateur = utilisateurService.getUtilisateur(activeUser.getEmail());
        System.out.println(activeUser);
        utilisateur.setNoUtilisateur(utilisateurService.getUtilisateur(activeUser.getEmail()).getNoUtilisateur());

        // Create and populate the Retrait object (location for withdrawal)
        Retrait retrait = new Retrait();
        retrait.setRue(rue);
        retrait.setCode_postal(code_postal);
        retrait.setVille(ville);

        // Create and populate the ArticleVendu object (auction item)
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setVendeur(utilisateur); // Set the seller
        articleVendu.setNomArticle(nom); // Set the name of the auction item
        articleVendu.setDescription(description); // Set the description
        articleVendu.setCategorie(categorieService.getCategorieByName(categorie)); // Get category by name
        articleVendu.setMiseAPrix(prix); // Set the starting price
        articleVendu.setDateDebutEncheres(debut); // Set the start date
        articleVendu.setDateFinEncheres(fin); // Set the end date
        articleVendu.setLieuRetrait(retrait); // Set the withdrawal location

        // Create and populate the Enchere object (auction itself)
        Enchere enchere = new Enchere();
        enchere.setDateEnchere(debut); // Set the auction date
        enchere.setMontant_enchere(prix); // Set the starting bid amount
        enchere.setArticleVendu(articleVendu); // Set the article being auctioned


        // Save the auction to the database
        enchereService.addEnchere(enchere);



        // Redirect to a confirmation or list page after successful creation
        return "redirect:/encheres";
    }





    @GetMapping("/modifier")
    public String modifierVente(@RequestParam(name = "id") int id, Model model) {
        var enchere = enchereService.getEnchere(id);
        model.addAttribute("enchere", enchere);
        model.addAttribute("categories", categorieService.getCategories());
        return "view-modif-vente"; // Render form
    }




    @PostMapping("/modifier")
    public String modifierEnchere(@RequestParam(name = "id") int id,
                                  @RequestParam(name = "categorie") String categorie,
                                  @RequestParam(name = "nom") String nom,
                                  @RequestParam(name = "description") String description,
                                  @RequestParam(name = "prix") int prix,
                                  @RequestParam(name = "debut")LocalDate debut,
                                  @RequestParam(name = "fin")LocalDate fin,
                                  @RequestParam(name = "rue") String rue,
                                  @RequestParam(name = "code_postal") int codePostal,
                                  @RequestParam(name = "ville") String ville,
                                  Model model) {


        var enchere = enchereService.getEnchere(id);


        var selectedCategorie = categorieService.getCategorieByName(categorie);

        ArticleVendu articleVendu = enchere.getArticleVendu();
        articleVendu.setNomArticle(nom);
        articleVendu.setDescription(description);
        articleVendu.setMiseAPrix(prix);
        articleVendu.setDateDebutEncheres(debut);
        articleVendu.setDateFinEncheres(fin);
        articleVendu.setCategorie(selectedCategorie);

        articleVendu.getLieuRetrait().setRue(rue);
        articleVendu.getLieuRetrait().setCode_postal(codePostal);
        articleVendu.getLieuRetrait().setVille(ville);

        enchere.setArticleVendu(articleVendu);

        System.out.println(enchere);
        System.out.println(articleVendu);

        if (enchere.getArticleVendu().getDateDebutEncheres().isAfter(LocalDate.now())){
            enchereService.updateEnchere(enchere);
            articleVenduService.updateArticleVendu(articleVendu);
        }


        return "redirect:/encheres";
    }

}

