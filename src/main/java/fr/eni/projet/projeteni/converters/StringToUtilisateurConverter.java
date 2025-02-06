package fr.eni.projet.projeteni.converters;

import fr.eni.projet.projeteni.bll.UtilisateurService;
import fr.eni.projet.projeteni.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUtilisateurConverter implements Converter<String, Utilisateur> {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public Utilisateur convert(String source) {
        return utilisateurService.getUtilisateur(Integer.parseInt(source));
    }

    @Override
    public <U> Converter<String, U> andThen(Converter<? super Utilisateur, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
