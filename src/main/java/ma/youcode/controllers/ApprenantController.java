package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.youcode.dao.ApprenantDao;
import ma.youcode.dao.ApprenantDoaImpl;
import ma.youcode.models.Absence;
import java.sql.Date;
import java.time.LocalDate;
import java.util.prefs.Preferences;


public class ApprenantController {
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

    Preferences userPreferences = Preferences.userRoot();

    @FXML
    private void displayAbsencesList() {
        if (startDate.getValue() != null && endDate.getValue() != null) {
            ApprenantDao apprenantDao = new ApprenantDoaImpl();
            // Here we need id of apprenant, classe and promo
            boolean areValidDates = (startDate.getValue().compareTo(LocalDate.now()) <= 0 && endDate.getValue().compareTo(LocalDate.now()) <= 0)
                    || (startDate.getValue().compareTo(LocalDate.now()) == 0 && endDate.getValue().compareTo(LocalDate.now()) == 0);
            if (areValidDates) {
                ObservableList<Absence> absences = apprenantDao.getAllAbsencesDate(userPreferences.getInt("id", 1), userPreferences.get("classe", ""), userPreferences.get("promo", ""), Date.valueOf(startDate.getValue()), Date.valueOf(endDate.getValue()));
                if (!absences.isEmpty()) {
                    colDateAbsenceApprenant.setCellValueFactory(new PropertyValueFactory<>("date"));
                    colJustificationApprenant.setCellValueFactory(new PropertyValueFactory<>("justification"));
                    SecretaireController.removeScrollBar(apprenantAbsenceTable);
                    apprenantAbsenceTable.setItems(absences);
                } else {
                    apprenantAbsenceTable.getItems().clear();
                    SecretaireController.alertBoxDisplay("Aucun résultats obtenus!");
                }
            } else {
                apprenantAbsenceTable.getItems().clear();
                SecretaireController.alertBoxDisplay("Impossible de passer la date d'aujourd'hui");
            }
        } else {
            SecretaireController.alertBoxDisplay("S'il vous plait, entrez tous les champs nécessaires\n\t\t     pour obtenir le résultat!");
        }
    }


}
