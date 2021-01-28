package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;
import ma.youcode.models.Absence;
import ma.youcode.models.Apprenant;
import ma.youcode.models.Utilisateur;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormateurDaoImpl implements FormateurDao {


    @Override
    public ObservableList<Utilisateur> afficherApprenantsParClasse(String classe, String date) {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM utilisateurs, apprenant WHERE id = id_apprenant AND apprenant.classe = ? ");
            preparedStatement.setString(1, classe);
            ResultSet rs = preparedStatement.executeQuery();
            Apprenant apprenant;
            while (rs.next()) {
                apprenant = new Apprenant(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("date_naissance"), rs.getString("tel"), rs.getString("email"), rs.getString("role"), rs.getString("password"), rs.getString("classe"), rs.getString("promo"), date);
                utilisateurs.add(apprenant);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return utilisateurs;

    }

    @Override
    public void marquerAbsence(int id, String type, String date) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO absences (id_apprenant,date,type_absence) VALUES(?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, type);
            preparedStatement.executeUpdate();
            System.out.println("Absence Marquée");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Absence afficherAbsences(int id, String date) {
        Connection conn = null;

        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM absences WHERE id_apprenant = ? AND date = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, date);
            ResultSet rs = preparedStatement.executeQuery();
            Absence absence;
            if (rs.next()) {
                absence = new Absence(rs.getInt("id_absence"), rs.getInt("id_apprenant"), rs.getString("type_absence"));
                return absence;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void deleteAbsence(int id, String date) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM absences WHERE id_apprenant = ? AND date = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, date);
            preparedStatement.executeUpdate();
            System.out.println("Absence Supprimée");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateAbsence(int id, String type, String date) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE absences SET type_absence = ? WHERE id_apprenant = ? AND date = ?");
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();
            System.out.println("Absence Modifiée");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
