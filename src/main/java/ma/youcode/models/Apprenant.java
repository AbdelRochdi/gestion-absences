package ma.youcode.models;

public class Apprenant extends Utilisateur {

    private String classe;
    private String promo;

    public Apprenant(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password, String classe, String promo) {
        super(id, nom, prenom, date_naissance, tel, email, role, password);
        this.classe = classe;
        this.promo = promo;
    }

    public Apprenant() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }
}
