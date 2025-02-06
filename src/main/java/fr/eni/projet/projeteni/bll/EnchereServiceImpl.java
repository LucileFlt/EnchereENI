package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.ArticleVendu;
import fr.eni.projet.projeteni.bo.Enchere;
import fr.eni.projet.projeteni.bo.Utilisateur;
import fr.eni.projet.projeteni.dal.DaoEncheresSQLImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    private DaoEncheresSQLImpl daoEncheresSQL;

    public EnchereServiceImpl(DaoEncheresSQLImpl daoEncheresSQL) {
        this.daoEncheresSQL = daoEncheresSQL;
    }

    @Override
    public Enchere getEnchere(int noArticle) {
        return daoEncheresSQL.read(noArticle);
    }

    @Override
    public List<Enchere> getAllEnchere() {
        return daoEncheresSQL.read();
    }

    @Override
    public int addEnchere(Enchere enchere) {
        return daoEncheresSQL.create(enchere);
    }

    @Override
    public void updateEnchere(Enchere enchere) {
        daoEncheresSQL.update(enchere);
    }

    @Override
    public void deleteEnchere(Enchere enchere) {

        daoEncheresSQL.delete(enchere);
    }

}
