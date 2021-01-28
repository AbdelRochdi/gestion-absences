package ma.youcode.dao;


import javafx.collections.ObservableList;
import ma.youcode.models.Absence;

import java.sql.Date;
import java.util.ArrayList;

public interface SecretaireDao {
    ObservableList<Absence> getAllAbsencesByClasse(String classeText, String promoText, Date date);
    void updateJustificationUsers(int id, String justification);
    ObservableList<Absence> getAllAbsencesStateByClasse(String classeText, String promoText, Date startDate, Date endDate);
    ArrayList<String> getPromoValues();
    ArrayList<String> getClasseValues();
}
