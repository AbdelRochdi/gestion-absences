package ma.youcode.dao;

import javafx.collections.ObservableList;
import ma.youcode.models.Absence;
import ma.youcode.models.Utilisateur;

public interface FormateurDao {

    public ObservableList<Utilisateur> afficherApprenantsParClasse(String classe, String date);
    public void marquerAbsence(int id, String type, String date);
    public Absence afficherAbsences(int id, String date);
    public void deleteAbsence(int id, String date);
    public void updateAbsence(int id, String type, String date);

}
