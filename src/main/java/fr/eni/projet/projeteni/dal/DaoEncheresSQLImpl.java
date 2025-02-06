package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Enchere;
import fr.eni.projet.projeteni.bo.Enchere;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DaoEncheresSQLImpl implements DaoEncheres {

    static final String SELECT_ALL = "select * from ENCHERES";
    static final String SELECT_BY_ID = "SELECT\n" +
            "    e.no_utilisateur,\n" +
            "    e.no_article,\n" +
            "    e.date_enchere,\n" +
            "    e.montant_enchere,\n" +
            "    a.no_utilisateur AS no_utilisateur_vendeur,\n" +
            "    a.nom_article,\n" +
            "    a.description,\n" +
            "    a.date_debut_encheres,\n" +
            "    a.date_fin_encheres,\n" +
            "    a.prix_initial,\n" +
            "    a.prix_vente,\n" +
            "    ue.pseudo AS enchere_pseudo,\n" +
            "    ue.nom AS enchere_nom,\n" +
            "    ue.prenom AS enchere_prenom,\n" +
            "    ue.email AS enchere_email,\n" +
            "    ue.telephone AS enchere_telephone,\n" +
            "    ue.rue AS enchere_utilisateur_rue,\n" +
            "    ue.code_postal AS enchere_utilisateur_code_postal,\n" +
            "    ue.ville AS enchere_utilisateur_ville,\n" +
            "    ue.credit AS enchere_credit,\n" +
            "    ua.pseudo AS article_pseudo,\n" +
            "    ua.nom AS article_nom,\n" +
            "    ua.prenom AS article_prenom,\n" +
            "    ua.email AS article_email,\n" +
            "    ua.telephone AS article_telephone,\n" +
            "    ua.rue AS article_utilisateur_rue,\n" +
            "    ua.code_postal AS article_utilisateur_code_postal,\n" +
            "    ua.ville AS article_utilisateur_ville,\n" +
            "    ua.credit AS article_credit,\n" +
            "    r.rue AS retrait_rue,\n" +
            "    r.code_postal AS retrait_code_postal,\n" +
            "    r.ville AS retrait_ville,\n" +
            "    c.libelle AS categorie_libelle\n" +
            "FROM\n" +
            "    ENCHERES e\n" +
            "JOIN\n" +
            "    ARTICLES_VENDUS a ON e.no_article = a.no_article\n" +
            "LEFT JOIN\n" +
            "    UTILISATEURS ue ON e.no_utilisateur = ue.no_utilisateur\n" +
            "JOIN\n" +
            "    UTILISATEURS ua ON a.no_utilisateur = ua.no_utilisateur\n" +
            "LEFT JOIN\n" +
            "    RETRAITS r ON a.no_article = r.no_article\n" +
            "JOIN\n" +
            "    CATEGORIES c ON a.no_categorie = c.no_categorie\n" +
            "WHERE\n" +
            "    e.no_article = ?\n" +
            "    AND ( e.no_utilisateur IS NULL OR e.no_utilisateur = 0 OR ue.no_utilisateur IS NOT NULL);";
//            "select * from ENCHERES where no_article = ?";
    static final String INSERT_ENCHERE = "INSERT  INTO ENCHERES ([no_utilisateur],[no_article],[date_enchere],[montant_enchere]) " +
            "VALUES (:no_utilisateur,:no_article,:date_enchere,:montant_enchere)";
    static final String INSERT_ARTICLE ="INSERT  INTO ARTICLES_VENDUS ([nom_article],[description],[date_debut_encheres],[date_fin_encheres],[prix_initial],[prix_vente],[no_utilisateur],[no_categorie]) " +
            "VALUES (:nom_article,:description,:date_debut_encheres,:date_fin_encheres,:prix_initial,:prix_vente,:no_utilisateur,:no_categorie)";
    static final String INSERT_RETRAIT = "INSERT  INTO RETRAITS ([no_article],[rue],[code_postal],[ville]) VALUES (:no_article,:rue,:code_postal,:ville)";

    static final String DELETE = "DELETE FROM ENCHERES where no_article=?";
    static final String UPDATE = "UPDATE ENCHERES set no_utilisateur=?,no_article=?,date_enchere=?,montant_enchere=? where no_article=?";


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<Enchere> rowMapper;

    public DaoEncheresSQLImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, RowMapper<Enchere> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
    }
    @Override
    public Enchere read(int noArticle) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, noArticle);
    }


    @Override
    public List<Enchere> read() {
        return jdbcTemplate.query(SELECT_ALL, rowMapper);
    }

//    @Override
//    public Enchere read(int idArticle) {
//        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Enchere.class), idArticle);
//    }

    @Transactional
    @Override
    public int create(Enchere enchere) {
        var namedparameters = new MapSqlParameterSource();
        namedparameters.addValue("nom_article", enchere.getArticleVendu().getNomArticle());
        namedparameters.addValue("description", enchere.getArticleVendu().getDescription());
        namedparameters.addValue("date_debut_encheres", java.sql.Date.valueOf(enchere.getArticleVendu().getDateDebutEncheres()));
        namedparameters.addValue("date_fin_encheres", java.sql.Date.valueOf(enchere.getArticleVendu().getDateFinEncheres()));
        namedparameters.addValue("prix_initial", enchere.getArticleVendu().getMiseAPrix());
        namedparameters.addValue("prix_vente", enchere.getArticleVendu().getMiseAPrix());
        namedparameters.addValue("no_utilisateur", enchere.getArticleVendu().getVendeur().getNoUtilisateur());
        namedparameters.addValue("no_categorie", enchere.getArticleVendu().getCategorie().getNoCategorie());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_ARTICLE, namedparameters, keyHolder);

        int noArticle = keyHolder.getKey().intValue();

        var namedparameters2 = new MapSqlParameterSource();
        namedparameters2.addValue("no_article", noArticle);
        namedparameters2.addValue("rue", enchere.getArticleVendu().getLieuRetrait().getRue());
        namedparameters2.addValue("code_postal", enchere.getArticleVendu().getLieuRetrait().getCode_postal());
        namedparameters2.addValue("ville", enchere.getArticleVendu().getLieuRetrait().getVille());
        namedParameterJdbcTemplate.update(INSERT_RETRAIT, namedparameters2);

        var namedparameters1 = new MapSqlParameterSource();
        namedparameters1.addValue("no_utilisateur", 0);
        namedparameters1.addValue("no_article", noArticle);
        namedparameters1.addValue("date_enchere", enchere.getArticleVendu().getDateDebutEncheres());
        namedparameters1.addValue("montant_enchere", enchere.getArticleVendu().getMiseAPrix());
        namedParameterJdbcTemplate.update(INSERT_ENCHERE, namedparameters1);

        return noArticle;
    }

    @Override
    public void update(Enchere enchere) {
        jdbcTemplate.update(UPDATE, enchere.getLastBidder().getNoUtilisateur(), enchere.getArticleVendu().getNoArticle(), enchere.getDateEnchere(), enchere.getMontant_enchere(), enchere.getArticleVendu().getNoArticle());
    }

    @Override
    public void delete(Enchere enchere) {
        jdbcTemplate.update(DELETE, enchere.getArticleVendu().getNoArticle());
    }


}
