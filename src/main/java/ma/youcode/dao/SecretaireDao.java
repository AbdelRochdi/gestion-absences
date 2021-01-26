package ma.youcode.dao;


import javafx.collections.ObservableList;

import java.sql.Date;

public interface SecretaireDao {
    ObservableList<Absence> getAllAbsencesByClasse(String classeText, String promoText, Date date);
    void updateJustificationUsers(int id, String justification);
    ObservableList<Absence> getAllAbsencesStateByClasse(String classeText, String promoText, Date startDate, Date endDate);
}
