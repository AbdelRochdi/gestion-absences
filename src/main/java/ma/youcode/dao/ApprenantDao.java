package ma.youcode.dao;

import javafx.collections.ObservableList;
import ma.youcode.models.Absence;

import java.sql.Date;

public interface ApprenantDao {
    ObservableList<Absence> getAllAbsencesDate(int apprenantId, String classeText, String promoText, Date startDate, Date endDate);

}
