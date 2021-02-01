package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;
import ma.youcode.models.Absence;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ApprenantDoaImpl implements ApprenantDao {
    @Override
    public ObservableList<Absence> getAllAbsencesByDate(int apprenantId, String classeText, String promoText, Date startDate, Date endDate) {
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

    @Override
    public ObservableList<Absence> getAllAbsences(int apprenantId, String classeText, String promoText) {
        ObservableList<Absence> absences = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT absences.date, absences.justification FROM apprenant " +
                    "INNER JOIN absences ON apprenant.id_apprenant = absences.id_apprenant " +
                    "WHERE apprenant.id_apprenant = ? AND apprenant.classe = ? AND apprenant.promo = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, apprenantId);
            preparedStatement.setString(2, classeText);
            preparedStatement.setString(3, promoText);
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

    @Override
    public ArrayList<String> getUserInfos(int apprenantId, String classeText, String promoText) {
        ArrayList<String> infos = new ArrayList<>();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT utilisateurs.nom, utilisateurs.prenom, utilisateurs.email, apprenant.promo, apprenant.classe, count(absences.date) AS 'n' FROM absences " +
                    "INNER JOIN apprenant on absences.id_apprenant = apprenant .id_apprenant " +
                    "INNER JOIN utilisateurs on utilisateurs.id = apprenant.id_apprenant and apprenant.classe = ? and apprenant.promo = ? " +
                    "WHERE apprenant.id_apprenant = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, classeText);
            preparedStatement.setString(2, promoText);
            preparedStatement.setInt(3, apprenantId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                infos.add(resultSet.getString("prenom"));
                infos.add(resultSet.getString("nom"));
                infos.add(resultSet.getString("promo"));
                infos.add(resultSet.getString("classe"));
                infos.add(resultSet.getString("email"));
                infos.add(resultSet.getInt("n") + "");
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
        return infos;
    }

}
