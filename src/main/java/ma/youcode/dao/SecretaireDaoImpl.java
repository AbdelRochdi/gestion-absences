package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;
import ma.youcode.models.Absences;

import javax.sql.DataSource;
import java.sql.*;

public class SecretaireDaoImpl implements SecretaireDao {
    @Override
    public ObservableList<Absences> getAllAbsencesByClasse(String classeText, String promoText, Date date) {
        ObservableList<Absences> absences = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT utilisateurs.nom, utilisateurs.prenom, utilisateurs.tel, absences.date, apprenant.promo, apprenant.classe, absences.id_absence, absences.justification " +
                            "FROM utilisateurs INNER JOIN apprenant ON utilisateurs.id = apprenant.id_apprenant " +
                            "INNER JOIN absences ON apprenant.id_apprenant = absences.id_apprenant where apprenant.classe = ? AND apprenant.promo = ? AND absences.date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, classeText);
            preparedStatement.setString(2, promoText);
            preparedStatement.setDate(3, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            Absences absence;
            while(resultSet.next()) {
                absence = new Absences(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getDate("date"), resultSet.getString("tel"), resultSet.getInt("id_absence"),resultSet.getString("justification"));
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
    public void updateJustificationUsers(int id, String justification) {
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "UPDATE absences SET justification = ? WHERE id_absence = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, justification);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ObservableList<Absences> getAllAbsencesByYear(String classeText, String promoText) {
        ObservableList<Absences> absences = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, classeText);
            preparedStatement.setString(2, promoText);
            ResultSet resultSet = preparedStatement.executeQuery();
            Absences absence;
            while(resultSet.next()) {
                absence = new Absences(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getDate("date"), resultSet.getString("tel"), resultSet.getInt("id_absence"),resultSet.getString("justification"));
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
