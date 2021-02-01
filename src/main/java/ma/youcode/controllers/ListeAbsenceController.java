package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.youcode.dao.FormateurDaoImpl;
import ma.youcode.main.App;
import ma.youcode.models.Absence;
import ma.youcode.models.Apprenant;
import ma.youcode.models.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

@SuppressWarnings("unchecked")
public class ListeAbsenceController implements Initializable {

    @FXML
    private Button logoutBtn;
    @FXML
    private Label listeDateLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button validerBtn;
    @FXML
    private TableColumn colId;
    @FXML
    private Label labelFormateur;
    @FXML
    private Label labelClasse;
    @FXML
    private TableView tvApprenant;
    @FXML
    private TableColumn colNom;
    @FXML
    private TableColumn colPrenom;
    @FXML
    private TableColumn colType;

    private FormateurDaoImpl formateur = new FormateurDaoImpl();


    Preferences userPreferences = Preferences.userRoot();

    public void handleButtonAction(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == validerBtn) {
            recupererAbsences();
            SecretaireController.alertBoxDisplay("Absence mise à jour pour le "+listeDateLabel.getText());
        } else if (actionEvent.getSource() == datePicker) {
            if (datePicker.getValue().compareTo(LocalDate.now()) <= 0){
                updateList();
            }else{
                tvApprenant.getItems().clear();
                SecretaireController.alertBoxDisplay("Veuillez choisir une date passée");
            }
        }else if (actionEvent.getSource() == logoutBtn) {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            App.setRoot("login");
            stage.sizeToScene();
            stage.centerOnScreen();
        }

    }

    public void afficherApprenants() {

        ObservableList<Utilisateur> list = formateur.afficherApprenantsParClasse(userPreferences.get("classe", "Classe"), listeDateLabel.getText());

        colId.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        colType.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("type"));
        tvApprenant.setItems(list);

    }

    public void recupererAbsences() {

        ObservableList<Apprenant> listAbsences = tvApprenant.getItems();

        for (int i = 0; i < listAbsences.size(); i++) {
            int idStudent = listAbsences.get(i).getId();
            String typeAbsence = String.valueOf(listAbsences.get(i).getType().getValue());
            if (typeAbsence.equals("Journée")) {
                Absence absence = formateur.afficherAbsences(idStudent, listeDateLabel.getText());
                if (absence == null) {
                    formateur.marquerAbsence(idStudent, typeAbsence, listeDateLabel.getText());
                } else if (absence.getType_absence().equals("Demi-Journée")) {
                    formateur.updateAbsence(idStudent, typeAbsence, listeDateLabel.getText());
                }
            } else if (typeAbsence.equals("Demi-Journée")) {
                Absence absence = formateur.afficherAbsences(idStudent, listeDateLabel.getText());
                if (absence == null) {
                    formateur.marquerAbsence(idStudent, typeAbsence, listeDateLabel.getText());
                } else if (absence.getType_absence().equals("Journée")) {
                    formateur.updateAbsence(idStudent, typeAbsence, listeDateLabel.getText());
                }
            } else if ((typeAbsence == null || typeAbsence.equals("null"))) {
                Absence absence = formateur.afficherAbsences(idStudent, listeDateLabel.getText());
                if (absence != null) {
                    formateur.deleteAbsence(idStudent, listeDateLabel.getText());
                }
            }
        }
    }

    public void updateList(){
        listeDateLabel.setText(String.valueOf(datePicker.getValue()));
        afficherApprenants();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelFormateur.setText(userPreferences.get("prenom", "Prenom") + " " + userPreferences.get("nom", "Nom"));
        labelClasse.setText(String.valueOf(userPreferences.get("classe", "Classe")));
        listeDateLabel.setText(String.valueOf(java.time.LocalDate.now()));
        afficherApprenants();
    }
}
