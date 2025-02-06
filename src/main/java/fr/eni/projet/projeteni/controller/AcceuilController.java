package fr.eni.projet.projeteni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcceuilController {

    @GetMapping()
    public String accueil() {
        return "redirect:/encheres";
    }
}
