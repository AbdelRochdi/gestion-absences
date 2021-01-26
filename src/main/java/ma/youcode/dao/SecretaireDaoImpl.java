package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;

import javax.sql.DataSource;
import java.sql.*;

public class SecretaireDaoImpl implements SecretaireDao {
    @Override
    public ObservableList<Absence> getAllAbsencesByClasse(String classeText, String promoText, Date date) {
        ObservableList<Absence> absences = FXCollections.observableArrayList();
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
            Absence absence;
            while(resultSet.next()) {
                absence = new Absence(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("tel") ,resultSet.getString("justification"));
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
    public ObservableList<Absence> getAllAbsencesStateByClasse(String classeText, String promoText, Date startDate, Date endDate) {
        ObservableList<Absence> absences = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT utilisateurs.nom, utilisateurs.prenom, utilisateurs.tel, count(absences.date) AS 'n' FROM absences " +
                    "RIGHT OUTER JOIN apprenant on absences.id_apprenant = apprenant .id_apprenant and (absences.date BETWEEN ? AND ?) " +
                    "INNER JOIN utilisateurs on utilisateurs.id = apprenant.id_apprenant and apprenant.classe = ? and apprenant.promo = ? " +
                    "GROUP BY utilisateurs.nom";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setString(3, classeText);
            preparedStatement.setString(4, promoText);
            ResultSet resultSet = preparedStatement.executeQuery();
            Absence absence;
            while(resultSet.next()) {
                absence = new Absence(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("tel"), resultSet.getInt("n"));
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
