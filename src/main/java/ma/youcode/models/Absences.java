package ma.youcode.models;

import java.sql.Date;

public class Absences  extends Utilisateur{
    private int id_absence;
    private int id_apprenant;
    private Date date;
    private String justification;


    public Absences(String nom, String prenom, Date date, String tel, int id_absence, String justification) {
        super(nom, prenom, tel);
        this.date = date;
        this.id_absence = id_absence;
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
}
