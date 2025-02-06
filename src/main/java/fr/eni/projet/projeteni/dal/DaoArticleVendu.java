package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.ArticleVendu;

import java.util.List;

public interface DaoArticleVendu {
    List<ArticleVendu> read();

    ArticleVendu read(int id);

    int create(ArticleVendu articleVendu);

    void update(ArticleVendu articleVendu);

    void delete(ArticleVendu articleVendu);

    void delete(int id);

    // FILTER ARTICLES
    List<ArticleVendu> findByCategory(Long categoryId);

    List<ArticleVendu> findByName(String nomArticle);

    List<ArticleVendu> findByCategoryAndName(Long categoryId, String nomArticle);

    //LIST ARTICLES BY USER
    List<ArticleVendu> findByUser(int no_utilisateur);
}
