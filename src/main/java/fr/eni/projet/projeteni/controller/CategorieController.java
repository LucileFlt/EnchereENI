package fr.eni.projet.projeteni.controller;


import fr.eni.projet.projeteni.bll.CategorieService;
import fr.eni.projet.projeteni.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//EVERYTHING HERE WORKS
@Controller
@RequestMapping("/encheres")
public class CategorieController {


    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/cree-categorie")
    public String creeCategorie(Model model) {
        var categorie = categorieService.getCategories();
        model.addAttribute("categories", categorie);
        return "view-creation-categorie";
    }


    @PostMapping("/cree-categorie")
    public String creeCategorie(@RequestParam (name = "libelle") String libelle) {

        Categorie categorie = new Categorie();
        categorie.setLibelle(libelle);
        categorieService.addCategories(categorie);
        return "redirect:/encheres/cree-categorie";
    }



    @PostMapping("/supprimer-cate")
    public String supprimerCategorie(@RequestParam(name = "id") int id) {
        categorieService.removeCategories(id);
        return "redirect:/encheres/cree-categorie";
    }


    @GetMapping("/modifier-cate")
    public String modifCategorie(@RequestParam(name = "id") int id, Model model) {
        Categorie categorie = categorieService.getCategorieById(id);
        model.addAttribute("categorie", categorie);
        return "view-modif-categorie";
    }


    @PostMapping("/modifier-cate")
    public String modifCategorie(@RequestParam(name = "libelle") String libelle, @RequestParam(name = "id") int id) {
        Categorie categorie = categorieService.getCategorieById(id);
        categorie.setLibelle(libelle);
        categorieService.update(categorie);
        return "redirect:/encheres/cree-categorie";
    }



}
