package fr.eni.projet.projeteni.bo;

public class Retrait {
    private int id_article;
    private String rue;
    private int code_postal;
    private String ville;

    public Retrait() {
    }

    public Retrait(int id_article, String rue, int code_postal, String ville) {
        this.id_article = id_article;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return rue + " rue" +
                ", code_postal=" + code_postal +
                ", ville='" + ville;
    }
}
