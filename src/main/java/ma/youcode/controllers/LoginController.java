package ma.youcode.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.youcode.dao.AdminDaoImpl;
import ma.youcode.main.App;
import ma.youcode.models.Apprenant;
import ma.youcode.models.Formateur;
import ma.youcode.models.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button loginBtn;

    private AdminDaoImpl admin = new AdminDaoImpl();
    Preferences userPreference = Preferences.userRoot();

    public void handleButtonAction(ActionEvent actionEvent) throws IOException {

        Utilisateur utilisateur = admin.afficherUtilisateurParEmail(tfEmail.getText());
        if (utilisateur != null){
            if (BCrypt.checkpw(tfPassword.getText(), utilisateur.getPassword())){
                System.out.println("login successful, "+ utilisateur.getNom()+" "+utilisateur.getPrenom()+" "+utilisateur.getRole());
                userPreference.putInt("id", utilisateur.getId());
                userPreference.put("nom",utilisateur.getNom());
                userPreference.put("prenom",utilisateur.getPrenom());
                userPreference.put("role",utilisateur.getRole());

                if (utilisateur.getRole().equals("Formateur")){
                    Formateur formateur = admin.afficherFormateur(utilisateur.getId());
                    userPreference.put("classe", formateur.getClasse());
                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    App.setRoot("listeAbsence");
                    stage.sizeToScene();
                }else if (utilisateur.getRole().equals("Apprenant")){
                    Apprenant apprenant = admin.afficherApprenant(utilisateur.getId());
                    userPreference.put("classe", apprenant.getClasse());
                    userPreference.put("promo", apprenant.getPromo());
                }else if (utilisateur.getRole().equals("Admin")){
                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    App.setRoot("admin");
                    stage.sizeToScene();
                }
            }else{
                errorLabel.setVisible(true);
                errorLabel.setText("Password incorrect");
            }
        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("Email incorrect");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
    }
}
