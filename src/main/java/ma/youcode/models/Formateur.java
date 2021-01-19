package ma.youcode.models;

public class Formateur extends Utilisateur{

    private String classe;

    public Formateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password, String classe) {
        super(id, nom, prenom, date_naissance, tel, email, role, password);
        this.classe = classe;
    }

    public Formateur() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
