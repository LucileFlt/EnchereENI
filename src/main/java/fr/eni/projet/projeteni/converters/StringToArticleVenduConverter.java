package fr.eni.projet.projeteni.converters;

import fr.eni.projet.projeteni.bll.ArticleVenduService;
import fr.eni.projet.projeteni.bo.ArticleVendu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToArticleVenduConverter implements Converter<String, ArticleVendu> {

    @Autowired
    private ArticleVenduService articleVenduService;

    @Override
    public ArticleVendu convert(String source) {
        return articleVenduService.getArticleVenduById(Integer.parseInt(source));
    }

    @Override
    public <U> Converter<String, U> andThen(Converter<? super ArticleVendu, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
