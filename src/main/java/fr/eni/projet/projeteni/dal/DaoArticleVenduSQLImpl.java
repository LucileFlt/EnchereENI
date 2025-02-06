package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.ArticleVendu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoArticleVenduSQLImpl implements DaoArticleVendu {

    static final String SELECT_ALL = "select * from ARTICLES_VENDUS";
    static final String SELECT_BY_ID = "select * from ARTICLES_VENDUS where no_article=?";
    static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie )";
    static final String DELETE = "DELETE FROM ARTICLES_VENDUS where no_article=?";
    static final String UPDATE = "UPDATE ARTICLES_VENDUS set nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?,prix_initial=?,prix_vente=?,no_utilisateur=?,no_categorie=? where no_article=?";
    // Add new SQL queries for filtering
    static final String SELECT_BY_CATEGORY = "select * from ARTICLES_VENDUS where no_categorie=?";
    static final String SELECT_BY_NAME = "select * from ARTICLES_VENDUS where nom_article like ?";
    static final String SELECT_BY_CATEGORY_AND_NAME = "select * from ARTICLES_VENDUS where no_categorie=? and nom_article like ?";
    //select by user
    static final String SELECT_BY_USER = "select * from ARTICLES_VENDUS where no_utilisateur=?";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<ArticleVendu> articleVenduRowMapper;

    public DaoArticleVenduSQLImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, RowMapper<ArticleVendu> articleVenduRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.articleVenduRowMapper = articleVenduRowMapper;
    }

    @Override
    public List<ArticleVendu> read() {
        return jdbcTemplate.query(SELECT_ALL, articleVenduRowMapper);
    }
    @Override
    public ArticleVendu read(int noArticle) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, articleVenduRowMapper, noArticle);
    }


//    public ArticleVendu read(int id) {
//        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(ArticleVendu.class), id);
//    }

    @Override
    public int create(ArticleVendu articleVendu) {
        var namedparameter = new MapSqlParameterSource();
        namedparameter.addValue("nom_article", articleVendu.getNomArticle());
        namedparameter.addValue("description", articleVendu.getDescription());
        namedparameter.addValue("date_debut_encheres", articleVendu.getDateDebutEncheres());
        namedparameter.addValue("date_fin_encheres", articleVendu.getDateFinEncheres());
        namedparameter.addValue("prix_initial", articleVendu.getMiseAPrix() );
        namedparameter.addValue("prix_vente", articleVendu.getPrixVente());
        namedparameter.addValue("no_utilisateur", articleVendu.getVendeur().getNoUtilisateur());
        namedparameter.addValue("no_categorie",articleVendu.getCategorie().getNoCategorie());

        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, namedparameter, keyHolder);
        return keyHolder.getKey().intValue();
    }


    @Override
    public void update(ArticleVendu articleVendu) {
        jdbcTemplate.update(UPDATE, articleVendu.getNomArticle(), articleVendu.getDescription(), articleVendu.getDateDebutEncheres(), articleVendu.getDateFinEncheres(), articleVendu.getMiseAPrix(), articleVendu.getPrixVente(), articleVendu.getVendeur().getNoUtilisateur(), articleVendu.getCategorie().getNoCategorie(), articleVendu.getNoArticle());
    }



    @Override
    public void delete(ArticleVendu articleVendu) {
        jdbcTemplate.update(DELETE, articleVendu.getNoArticle());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);

    }

    // FILTER ARTICLES
    @Override
    public List<ArticleVendu> findByCategory(Long categoryId) {
        return jdbcTemplate.query(SELECT_BY_CATEGORY, new Object[]{categoryId}, articleVenduRowMapper);
    }
    @Override
    public List<ArticleVendu> findByName(String nomArticle) {
        return jdbcTemplate.query(SELECT_BY_NAME, new Object[]{"%" + nomArticle + "%"}, articleVenduRowMapper);
    }
    @Override
    public List<ArticleVendu> findByCategoryAndName(Long categoryId, String nomArticle) {
        return jdbcTemplate.query(SELECT_BY_CATEGORY_AND_NAME, new Object[]{categoryId, "%" + nomArticle + "%"}, articleVenduRowMapper);
    }
    //LIST ARTICLES BY USER
    @Override
    public List<ArticleVendu> findByUser(int no_utilisateur){return jdbcTemplate.query(SELECT_BY_USER, new Object[]{no_utilisateur}, articleVenduRowMapper);}
}
