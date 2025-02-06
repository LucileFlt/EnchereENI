package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.Categorie;
import fr.eni.projet.projeteni.dal.DaoCategories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServicelmp implements CategorieService {
    private DaoCategories daoCategories;

    public CategorieServicelmp(DaoCategories daoCategories) {
        this.daoCategories = daoCategories;
    }

    @Override
    public int addCategories(Categorie categori) {
        return daoCategories.create(categori);
    }

    @Override
    public void removeCategories(int id) {
        daoCategories.delete(id);
    }

    @Override
    public List<Categorie> getCategories() {
        return daoCategories.read();
    }

    @Override
    public Categorie getCategorieById(int id) {
        return daoCategories.read(id) ;
    }

    @Override
    public Categorie getCategorieByName(String libelle) {
        return daoCategories.read(libelle) ;
    }


    @Override
    public void update(Categorie categorie) {
        daoCategories.update(categorie);
    }

}
