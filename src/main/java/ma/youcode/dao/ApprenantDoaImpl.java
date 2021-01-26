package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;

import javax.sql.DataSource;
import java.sql.*;

public class ApprenantDoaImpl implements ApprenantDao {
    @Override
    public ObservableList<Absence> getAllAbsencesDate(int apprenantId, String classeText, String promoText, Date startDate, Date endDate) {
        ObservableList<Absence> absences = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT absences.date, absences.justification FROM apprenant " +
                    "INNER JOIN absences ON apprenant.id_apprenant = absences.id_apprenant " +
                    "WHERE apprenant.id_apprenant = ? AND apprenant.classe = ? AND apprenant.promo = ? " +
                    "AND absences.date between ? and ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, apprenantId);
            preparedStatement.setString(2, classeText);
            preparedStatement.setString(3, promoText);
            preparedStatement.setDate(4, startDate);
            preparedStatement.setDate(5, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            Absence absence;
            while(resultSet.next()) {
                absence = new Absence(resultSet.getDate("date"), resultSet.getString("justification"));
                absences.add(absence);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return absences;

    }
}
