package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;
import ma.youcode.models.Utilisateur;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class AdminDaoImpl implements AdminDao {
    @Override
    public int creerUtilisateur(String nom, String prenom, String date_naissance, String tel, String email, String role, String password) {
        Connection conn = null;
        int gKey = 0;
        try {

            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO utilisateurs (nom,prenom,date_naissance,tel,email,role,password) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, date_naissance);
            preparedStatement.setString(4, tel);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, role);
            preparedStatement.setString(7, password);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            System.out.println("Utilisateur Créé");
            if (rs.next()){
                gKey = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return gKey;
    }

    @Override
    public void ajouterApprenant(int id, String classe, String promo) {
        Connection conn = null;
        try {

            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO apprenant (id_apprenant,classe,promo) VALUES(?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, classe);
            preparedStatement.setString(3, promo);
            preparedStatement.executeUpdate();
            System.out.println("Apprenant ajouté");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void ajouterFormateur(int id, String classe) {
        Connection conn = null;
        try {

            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO formateur (id_formateur,classe) VALUES(?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "Classe 1");
            preparedStatement.executeUpdate();
            System.out.println("Formateur ajouté");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void supprimerUtilisateur(int id) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM utilisateurs WHERE id = ?");
            preparedStatement.setInt(1, 1);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur Supprimé");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void modifierUtilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role, String password) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE utilisateurs SET nom = ?, prenom = ?,date_naissance = ?,tel = ?,email = ?,role = ?,password = ? WHERE id = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, date_naissance);
            preparedStatement.setString(4, tel);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, role);
            preparedStatement.setString(7, password);
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur Modifié");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afficherUtilisateur(int id) {
        Connection conn = null;

        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM utilisateurs WHERE id = ?");
            preparedStatement.setInt(1, 2);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("nom") + " " + rs.getString("prenom"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ObservableList<Utilisateur> afficherTousLesUtilisateur() {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM utilisateurs ");
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            while (rs.next()) {
                utilisateur = new Utilisateur(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("date_naissance"),rs.getString("tel"),rs.getString("email"));
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return utilisateurs;
    }

    @Override
    public ArrayList<String> getRoles() {
        Connection conn = null;
        ArrayList<String> roles = new ArrayList<String>();
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM roles ");
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            while (rs.next()) {
                roles.add(rs.getString("titre"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return roles;
    }

    @Override
    public ArrayList<String> getClasses() {
        Connection conn = null;
        ArrayList<String> classes = new ArrayList<String>();
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM classes ");
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            while (rs.next()) {
                classes.add(rs.getString("titre"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return classes;
    }

    @Override
    public ArrayList<String> getPromos() {
        Connection conn = null;
        ArrayList<String> promos = new ArrayList<String>();
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM promos ");
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            while (rs.next()) {
                promos.add(rs.getString("titre"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return promos;
    }
}
