package ma.youcode.dao;

import javafx.collections.ObservableList;
import ma.youcode.models.Utilisateur;

import java.util.ArrayList;

public interface AdminDao {

    public int creerUtilisateur(String nom, String prenom, String date_naissance, String tel, String email, String role, String password);

    public void ajouterApprenant(int id, String classe, String promo);

    public void ajouterFormateur(int id, String classe);

    public void supprimerUtilisateur(int id);

    public void modifierUtilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password);

    public void afficherUtilisateur(int id);

    public ObservableList<Utilisateur> afficherTousLesUtilisateur();

    public ArrayList<String> getRoles();

    public ArrayList<String> getClasses();

    public ArrayList<String> getPromos();

}
