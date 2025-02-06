package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Categorie;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoCategoriSQLImpl implements DaoCategories {

    static final String SELECT_ALL = "select * from CATEGORIES";
    static final String SELECT_BY_ID = "select * from CATEGORIES where no_categorie=?";
    static final String SELECT_BY_LIBELLE = "select * from CATEGORIES where libelle=?";
    static final String INSERT = "INSERT  INTO CATEGORIES ([libelle]) VALUES (:libelle)";
    static final String DELETE = "DELETE FROM CATEGORIES where no_categorie=?";
    static final String UPDATE = "UPDATE CATEGORIES set libelle=? where no_categorie=?";

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DaoCategoriSQLImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Categorie> read() {
        return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(Categorie.class));
    }

    @Override
    public Categorie read(int noCategorie) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Categorie.class),noCategorie);
    }

    @Override
    public Categorie read(String libelle) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Categorie.class),libelle);
    }

    @Override
    public int create(Categorie categorie) {
        var namedparameters = new MapSqlParameterSource();
        namedparameters.addValue("libelle", categorie.getLibelle());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, namedparameters, keyHolder);
        return keyHolder.getKey().intValue();
    }


    @Override
    public void update(Categorie  categorie) {
        jdbcTemplate.update(UPDATE, categorie.getLibelle(), categorie.getNoCategorie());
    }

    @Override
    public void delete(Categorie categorie) {
        delete(categorie.getNoCategorie());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }
}
