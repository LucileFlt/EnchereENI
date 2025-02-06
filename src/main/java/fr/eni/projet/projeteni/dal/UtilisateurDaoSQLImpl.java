package fr.eni.projet.projeteni.dal;

import fr.eni.projet.projeteni.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UtilisateurDaoSQLImpl implements UtilisateurDao {


    static final String SELECT_ALL = "select * from UTILISATEURS";
    static final String SELECT_BY_PSEUDO = "select * from UTILISATEURS where email=?";
    static final String SELECT_BY_ID = "select * from UTILISATEURS where no_utilisateur=?";
    static final String INSERT = "INSERT  INTO UTILISATEURS ([pseudo],[nom],[prenom],[email],[telephone],[rue],[code_postal],[ville],[mot_de_passe],[credit]) " +
                                 "VALUES (:pseudo,:nom,:prenom,:email,:telephone,:rue,:code_postal,:ville,:mot_de_passe,:credit)";
    static final String DELETE = "DELETE FROM UTILISATEURS where email=?";
    static final String UPDATE = "UPDATE UTILISATEURS set pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=? where email=?";


    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UtilisateurDaoSQLImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Utilisateur> read() {
        return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(Utilisateur.class));
    }


    @Override
    public Utilisateur read(String email) {
        return jdbcTemplate.queryForObject(SELECT_BY_PSEUDO, BeanPropertyRowMapper.newInstance(Utilisateur.class), email);
    }

    @Override
    public Utilisateur read(int no_utilisateur) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Utilisateur.class), no_utilisateur);
    }

    @Override
    public int create(Utilisateur utilisateur) {
        var namedparameters = new MapSqlParameterSource();
        namedparameters.addValue("pseudo", utilisateur.getPseudo());
        namedparameters.addValue("nom", utilisateur.getNom());
        namedparameters.addValue("prenom", utilisateur.getPrenom());
        namedparameters.addValue("email", utilisateur.getEmail());
        namedparameters.addValue("telephone", utilisateur.getTelephone());
        namedparameters.addValue("rue", utilisateur.getRue());
        namedparameters.addValue("code_postal", utilisateur.getCodePostal());
        namedparameters.addValue("ville", utilisateur.getVille());
        namedparameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
        namedparameters.addValue("credit", utilisateur.getCredit());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, namedparameters, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Utilisateur utilisateur) {
        jdbcTemplate.update(UPDATE,
                utilisateur.getPseudo(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone(),
                utilisateur.getRue(),
                utilisateur.getCodePostal(),
                utilisateur.getVille(),
                utilisateur.getMotDePasse(),
                utilisateur.getCredit(),
                utilisateur.getEmail());
    }


    @Override
    public void delete(Utilisateur utilisateur) {
        jdbcTemplate.update(DELETE, utilisateur.getEmail());
    }

}
