package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ma.youcode.dao.AdminDaoImpl;
import ma.youcode.dao.ApprenantDao;
import ma.youcode.dao.ApprenantDoaImpl;
import ma.youcode.main.App;
import ma.youcode.models.Absence;
import ma.youcode.models.Utilisateur;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.prefs.Preferences;


public class ApprenantController {

    @FXML
    private ImageView userImage;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView<Absence> apprenantAbsenceTable;
    @FXML
    private TableColumn<Absence, String> colDateAbsenceApprenant;
    @FXML
    private TableColumn<Absence, String> colJustificationApprenant;
    @FXML
    private Label nomLabel;
    @FXML
    private Label promoLabel;
    @FXML
    private Label classeLabel;
    @FXML
    private Label totalAbsencesLabel;

    private AdminDaoImpl admin = new AdminDaoImpl();
    private Preferences userPreferences = Preferences.userRoot();
    private Utilisateur utilisateur = admin.afficherUtilisateur(userPreferences.getInt("id", 1));


    @FXML
    private void initialize() throws IOException {
        displayUserInfos();
        displayAbsencesList();
        getUserImage();
    }


    @FXML
    private void displayAbsencesListByDate() {
        if (startDate.getValue() != null && endDate.getValue() != null) {
            ApprenantDao apprenantDao = new ApprenantDoaImpl();
            boolean areValidDates = startDate.getValue().compareTo(LocalDate.now()) <= 0 && endDate.getValue().compareTo(LocalDate.now()) <= 0;
            if (areValidDates) {
                ObservableList<Absence> absences = apprenantDao.getAllAbsencesByDate(userPreferences.getInt("id", 1), userPreferences.get("classe", ""), userPreferences.get("promo", ""), Date.valueOf(startDate.getValue()), Date.valueOf(endDate.getValue()));
                if (!absences.isEmpty()) {
                    colDateAbsenceApprenant.setCellValueFactory(new PropertyValueFactory<>("date"));
                    colJustificationApprenant.setCellValueFactory(new PropertyValueFactory<>("justification"));
                    apprenantAbsenceTable.setItems(absences);
                } else {
                    apprenantAbsenceTable.getItems().clear();
                    SecretaireController.alertBoxDisplay("Aucun résultats obtenus!");
                    SecretaireController.removeScrollBar(apprenantAbsenceTable);
                }
            } else {
                apprenantAbsenceTable.getItems().clear();
                SecretaireController.alertBoxDisplay("Impossible de dépasser la date d'aujourd'hui");
            }
        } else {
            SecretaireController.alertBoxDisplay("S'il vous plait, entrez tous les champs nécessaires\n\t\t     pour obtenir le résultat!");
        }

    }

    private void displayUserInfos() {
        ApprenantDao apprenantDao = new ApprenantDoaImpl();
        // your session
        ArrayList<String> infos = apprenantDao.getUserInfos(userPreferences.getInt("id", 1), userPreferences.get("classe", ""), userPreferences.get("promo", ""));
        nomLabel.setText(infos.get(0) + " " + infos.get(1));
        promoLabel.setText(infos.get(2));
        classeLabel.setText(infos.get(3));
        totalAbsencesLabel.setText(infos.get(5));
    }

    private void displayAbsencesList() {
        ApprenantDao apprenantDao = new ApprenantDoaImpl();
        // your session
        ObservableList<Absence> absences = apprenantDao.getAllAbsences(userPreferences.getInt("id", 1), userPreferences.get("classe", ""), userPreferences.get("promo", ""));
        colDateAbsenceApprenant.setCellValueFactory(new PropertyValueFactory<>("date"));
        colJustificationApprenant.setCellValueFactory(new PropertyValueFactory<>("justification"));
        apprenantAbsenceTable.setItems(absences);

    }


    @FXML
    private void logoutApprenant() throws IOException {
        Stage stage = (Stage) endDate.getScene().getWindow();
        App.setRoot("login");
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    public void getUserImage() throws IOException {
        InputStream is = utilisateur.getImage();
        if (is != null) {

            OutputStream os = new FileOutputStream(new File("photo.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            is.close();

            Image image = new Image("file:photo.jpg");
            userImage.setImage(image);
        }
    }


}
