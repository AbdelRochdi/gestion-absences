package ma.youcode.dao;


import javafx.collections.ObservableList;
import ma.youcode.models.Absences;

import java.sql.Date;

public interface SecretaireDao {
    ObservableList<Absences> getAllAbsencesByClasse(String classeText, String promoText, Date date);
    void updateJustificationUsers(int id, String justification);
    ObservableList<Absences> getAllAbsencesByYear(String classeText, String promoText);
}
