package fr.eni.projet.projeteni.bll;

import fr.eni.projet.projeteni.bo.Retrait;
import fr.eni.projet.projeteni.dal.DaoRetrait;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetraitServiceImpl implements RetraitService {

    private DaoRetrait daoRetrait;

    public RetraitServiceImpl(DaoRetrait daoRetrait) {
        this.daoRetrait = daoRetrait;
    }

    @Override
    public int addRetrait(Retrait retrait) {
        return daoRetrait.create(retrait);
    }

    @Override
    public Retrait getRetrait(int id) {
        return daoRetrait.read(id);
    }

    @Override
    public List<Retrait> getAllRetrait() {
        return daoRetrait.read();
    }

    @Override
    public void updateRetrait(Retrait retrait) {
         daoRetrait.update(retrait);
    }

    @Override
    public void deleteRetrait(Retrait retrait) {
        daoRetrait.delete(retrait);
    }


}
