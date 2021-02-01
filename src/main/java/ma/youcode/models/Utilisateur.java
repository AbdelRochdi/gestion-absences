package ma.youcode.models;

import java.io.InputStream;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String date_naissance;
    private String tel;
    private String email;
    private String role;
    private String password;
    public String classe;
    public String promo;
    public InputStream image;

    public Utilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password, InputStream image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.email = email;
        this.role = role;
        this.password = password;
        this.image = image;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public Utilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public Utilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.email = email;

    }

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
