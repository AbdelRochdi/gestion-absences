package ma.youcode.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import ma.youcode.connexion.Connexion;
import ma.youcode.models.*;

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
            if (rs.next()) {
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
    public void ajouterClasse(String classe) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO classes (titre) VALUES(?)");
            preparedStatement.setString(1, classe);
            preparedStatement.executeUpdate();
            System.out.println("Classe ajoutée");
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
    public void ajouterPromo(String promo) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO promos (titre) VALUES(?)");
            preparedStatement.setString(1, promo);
            preparedStatement.executeUpdate();
            System.out.println("Promo ajoutée");
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
    public void modifierClasse(int id, String classe) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE classes SET titre = ? WHERE id_classe = ?");
            preparedStatement.setString(1, classe);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Classe modifiée");
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
    public void modifierPromo(int id, String promo) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE promos SET titre = ? WHERE id_promo = ?");
            preparedStatement.setString(1, promo);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Promo modifiée");
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
    public void supprimerClasse(int id) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM classes WHERE id_classe = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Classe supprimée");
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
    public void supprimerPromo(int id) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM promos WHERE id_promo = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Promo supprimée");
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
    public void supprimerUtilisateur(int id) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM utilisateurs WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur Supprimé");
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
    public void supprimerApprenant(int id) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM apprenant WHERE id_apprenant = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Apprenant Supprimé");
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
    public void supprimerFormateur(int id) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM formateur WHERE id_formateur = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Formateur Supprimé");
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
    public void modifierUtilisateur(int id, String nom, String prenom, String date_naissance, String tel, String email, String role) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE utilisateurs SET nom = ?, prenom = ?,date_naissance = ?,tel = ?,email = ?,role = ? WHERE id = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, date_naissance);
            preparedStatement.setString(4, tel);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, role);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur Modifié");
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
    public void modifierApprenant(int id, String classe, String promo) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE apprenant SET classe = ?, promo = ? WHERE id_apprenant = ?");
            preparedStatement.setString(1, classe);
            preparedStatement.setString(2, promo);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur Modifié");
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
    public void modifierFormateur(int id, String classe) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE formateur SET classe = ? WHERE id_formateur = ?");
            preparedStatement.setString(1, classe);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur Modifié");
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
    public Utilisateur afficherUtilisateur(int id) {

        Connection conn = null;

        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM utilisateurs WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            if (rs.next()) {
                utilisateur = new Utilisateur(rs.getInt("id"), rs.getString("nom"),rs.getString("prenom"),rs.getString("date_naissance"),rs.getString("tel"),rs.getString("email"),rs.getString("role"),rs.getString("password"));
                return utilisateur;
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
    public Utilisateur afficherUtilisateurParEmail(String email) {
        Connection conn = null;

        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM utilisateurs WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            if (rs.next()) {
                utilisateur = new Utilisateur(rs.getInt("id"), rs.getString("nom"),rs.getString("prenom"),rs.getString("date_naissance"),rs.getString("tel"),rs.getString("email"),rs.getString("role"),rs.getString("password"));
                return utilisateur;
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
    public Apprenant afficherApprenant(int id) {
        Connection conn = null;
        Apprenant apprenant;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `utilisateurs`, apprenant WHERE id = ? AND id_apprenant = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                apprenant = new Apprenant(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("date_naissance"),rs.getString("tel"),rs.getString("email"),rs.getString("role"),rs.getString("password"),rs.getString("classe"),rs.getString("promo"));
                return apprenant;
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
    public Formateur afficherFormateur(int id) {
        Connection conn = null;
        Formateur formateur;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `utilisateurs`, formateur WHERE id = ? AND id_formateur = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                formateur = new Formateur(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("date_naissance"),rs.getString("tel"),rs.getString("email"),rs.getString("role"),rs.getString("password"),rs.getString("classe"));
                return formateur;
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
                if (rs.getString("role").equals("Apprenant")) {
                    utilisateur = afficherApprenant(rs.getInt("id"));
                    utilisateurs.add(utilisateur);
                } else if (rs.getString("role").equals("Formateur")) {
                    utilisateur = afficherFormateur(rs.getInt("id"));
                    utilisateurs.add(utilisateur);
                } else {
                    utilisateur = new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("date_naissance"), rs.getString("tel"), rs.getString("email"), rs.getString("role"), rs.getString("password"));
                    utilisateurs.add(utilisateur);
                }
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
    public ObservableList<Utilisateur> rechercherUtilisateur(String keyword) {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM utilisateurs WHERE email LIKE '%"+keyword+"%' OR prenom LIKE '%"+keyword+"%' OR nom LIKE '%"+keyword+"%'");
            ResultSet rs = preparedStatement.executeQuery();
            Utilisateur utilisateur;
            while (rs.next()) {
                if (rs.getString("role").equals("Apprenant")) {
                    utilisateur = afficherApprenant(rs.getInt("id"));
                    utilisateurs.add(utilisateur);
                } else if (rs.getString("role").equals("Formateur")) {
                    utilisateur = afficherFormateur(rs.getInt("id"));
                    utilisateurs.add(utilisateur);
                } else {
                    utilisateur = new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("date_naissance"), rs.getString("tel"), rs.getString("email"), rs.getString("role"), rs.getString("password"));
                    utilisateurs.add(utilisateur);
                }
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
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
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
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
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
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return promos;
    }

    public ObservableList<Promo> afficherPromos(){
        Connection conn = null;
        ObservableList<Promo> promoList = FXCollections.observableArrayList();
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM promos ");
            ResultSet rs = preparedStatement.executeQuery();
            Promo promo;
            while (rs.next()) {
                promo = new Promo(rs.getInt("id_promo"),rs.getString("titre"));
                promoList.add(promo);
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
        return promoList;
    }

    @Override
    public void modifierPassword(int id, String password) {
        Connection conn = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE utilisateurs SET password = ? WHERE id = ?");
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Password modifié");
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

    public ObservableList<Classe> afficherClasses(){
        Connection conn = null;
        ObservableList<Classe> classeList = FXCollections.observableArrayList();
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM classes ");
            ResultSet rs = preparedStatement.executeQuery();
            Classe classe;
            while (rs.next()) {
                classe = new Classe(rs.getInt("id_classe"),rs.getString("titre"));
                classeList.add(classe);
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
        return classeList;
    }
}
