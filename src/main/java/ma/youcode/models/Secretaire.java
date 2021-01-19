package ma.youcode.models;

public class Secretaire extends Utilisateur {
    public Secretaire(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password) {
        super(id, nom, prenom, date_naissance, tel, email, role, password);
    }

    public Secretaire() {
    }
}
