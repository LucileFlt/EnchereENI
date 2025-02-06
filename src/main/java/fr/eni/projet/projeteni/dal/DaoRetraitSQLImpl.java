package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Retrait;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoRetraitSQLImpl implements DaoRetrait {


    static final String SELECT_ALL = "select * from RETRAITS";
    static final String SELECT_BY_ID = "select * from RETRAITS where no_article = ?";
    static final String INSERT = "INSERT  INTO RETRAITS ([no_article],[rue],[code_postal],[ville]) " +
                                 "VALUES (:no_article,:rue,:code_postal,:ville)";
    static final String DELETE = "DELETE FROM RETRAITS where no_article=?";
    static final String UPDATE = "UPDATE RETRAITS set no_article=?, rue=?,code_postal=?,ville=? where no_article=?";


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DaoRetraitSQLImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
//    @Override
//    public Retrait read(int noArticle) {
//        return jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, noArticle);
//    }


    @Override
    public List<Retrait> read() {
        return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(Retrait.class));
    }

    @Override
    public Retrait read(int idArticle) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Retrait.class), idArticle);
    }

    @Override
    public int create(Retrait retrait) {
        var namedparameters = new MapSqlParameterSource();
        namedparameters.addValue("no_article", retrait.getId_article());
        namedparameters.addValue("rue", retrait.getRue());
        namedparameters.addValue("code_postal", retrait.getCode_postal());
        namedparameters.addValue("ville", retrait.getVille());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, namedparameters, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Retrait retrait) {
        jdbcTemplate.update(UPDATE,retrait.getId_article() , retrait.getRue(), retrait.getCode_postal(), retrait.getVille(), retrait.getId_article());
    }

    @Override
    public void delete(Retrait retrait) {
        jdbcTemplate.update(DELETE, retrait.getId_article());
    }



}
