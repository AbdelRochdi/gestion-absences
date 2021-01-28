package ma.youcode.models;

import java.sql.Date;

public class Absence extends Utilisateur {

    private int id_absence;
    private int id_apprenant;
    private Date date;
    private String justification;
    private int absencesTotal;
    private String type_absence;

    public Absence(int id_absence, int id_apprenant, String type_absence) {
        this.id_absence = id_absence;
        this.id_apprenant = id_apprenant;
        this.type_absence = type_absence;
    }

    public Absence(Date date, String justification) {
        this.date = date;
        this.justification = justification;
    }

    public Absence(String nom, String prenom, String tel, int absencesTotal) {
        super(nom, prenom, tel);
        this.absencesTotal = absencesTotal;
    }

    public Absence(String nom, String prenom, String tel, String justification) {
        super(nom, prenom, tel);
        this.justification = justification;
    }


    public int getId_absence() {
        return id_absence;
    }

    public void setId_absence(int id_absence) {
        this.id_absence = id_absence;
    }

    public int getId_apprenant() {
        return id_apprenant;
    }

    public void setId_apprenant(int id_apprenant) {
        this.id_apprenant = id_apprenant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public int getAbsencesTotal() {
        return absencesTotal;
    }

    public void setAbsencesTotal(int absencesTotal) {
        this.absencesTotal = absencesTotal;
    }

    public String getType_absence() {
        return type_absence;
    }

    public void setType_absence(String type_absence) {
        this.type_absence = type_absence;
    }
}
