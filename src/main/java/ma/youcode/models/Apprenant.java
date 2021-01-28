package ma.youcode.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import ma.youcode.dao.FormateurDaoImpl;

public class Apprenant extends Utilisateur {

    private String classe;
    private String promo;
    private ComboBox type;
    private FormateurDaoImpl formateur = new FormateurDaoImpl();

    private ObservableList<String> typesAbsence = FXCollections.observableArrayList("Journée","Demi-Journée", null);

    public Apprenant(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password, String classe, String promo, String date) {
        super(id, nom, prenom, date_naissance, tel, email, role, password);
        this.classe = classe;
        this.promo = promo;
        this.type = new ComboBox(typesAbsence);
        Absence absence = formateur.afficherAbsences(this.getId(), date);
        if (absence != null) {
            this.type.setValue(absence.getType_absence());
        }
    }

    public Apprenant(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password, String classe, String promo) {
        super(id, nom, prenom, date_naissance, tel, email, role, password);
        this.classe = classe;
        this.promo = promo;
        this.type = new ComboBox(typesAbsence);
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

    public ComboBox getType() {
        return type;
    }

    public void setType(ComboBox type) {
        this.type = type;
    }
}
