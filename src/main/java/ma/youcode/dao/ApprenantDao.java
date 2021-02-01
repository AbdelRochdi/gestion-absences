package ma.youcode.dao;

import javafx.collections.ObservableList;
import ma.youcode.models.Absence;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface ApprenantDao {
    ObservableList<Absence> getAllAbsencesByDate(int apprenantId, String classeText, String promoText, Date startDate, Date endDate);
    ObservableList<Absence> getAllAbsences(int apprenantId, String classeText, String promoText);
    ArrayList<String> getUserInfos(int apprenantId, String classeText, String promoText);

}
