package ma.youcode.models;

public class Classe {

    private int id;
    private String classe;

    public Classe(int id, String classe) {
        this.id = id;
        this.classe = classe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
