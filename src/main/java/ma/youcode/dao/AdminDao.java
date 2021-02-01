package ma.youcode.dao;

import javafx.collections.ObservableList;
import ma.youcode.models.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public interface AdminDao {

    public int creerUtilisateur(String nom, String prenom, String date_naissance, String tel, String email, String role, String password, InputStream image, int fileLength);

    public void ajouterApprenant(int id, String classe, String promo);

    public void ajouterFormateur(int id, String classe);

    public void ajouterClasse(String classe);

    public void ajouterPromo(String promo);

    public void modifierClasse(int id, String classe);

    public void modifierPromo(int id, String promo);

    public void supprimerClasse(int id);

    public void supprimerPromo(int id);


    public void supprimerUtilisateur(int id);

    public void supprimerApprenant(int id);

    public void supprimerFormateur(int id);

    public void modifierUtilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, InputStream image, int fileLength);

    public void modifierApprenant(int id, String classe, String promo);

    public void modifierFormateur(int id, String promo);

    public Utilisateur afficherUtilisateur(int id);

    public Utilisateur afficherUtilisateurParEmail(String Email);

    public Apprenant afficherApprenant(int id);

    public Formateur afficherFormateur(int id);

    public ObservableList<Utilisateur> afficherTousLesUtilisateur();

    public ObservableList<Utilisateur> rechercherUtilisateur(String keyword);

    public ArrayList<String> getRoles();

    public ArrayList<String> getClasses();

    public ArrayList<String> getPromos();

    public ObservableList<Classe> afficherClasses();

    public ObservableList<Promo> afficherPromos();

    public void modifierPassword(int id, String password);

}
