package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.ArticleVendu;

import java.util.List;

public interface ArticleVenduService {
    List<ArticleVendu> getAllArticleVendu();

    ArticleVendu getArticleVenduById(int id);

    int createArticleVendu(ArticleVendu articleVendu);

    void updateArticleVendu(ArticleVendu articleVendu);

    void deleteArticleVendu(int id);

    void deleteArticleVendu(ArticleVendu articleVendu);

    List<ArticleVendu> getArticlesByCategory(Long categoryId);

    List<ArticleVendu> getArticlesByCategoryAndName(Long categoryId, String nomArticle);

    List<ArticleVendu> getArticlesByName(String nomArticle);
}
