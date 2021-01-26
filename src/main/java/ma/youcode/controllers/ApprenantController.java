package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.youcode.dao.ApprenantDao;
import ma.youcode.dao.ApprenantDoaImpl;

import java.sql.Date;

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

    @FXML
    private void displayAbsencesList() {
        if (startDate.getValue() != null && endDate.getValue() != null) {
            ApprenantDao apprenantDao = new ApprenantDoaImpl();
            // Here we need id of apprenant, classe and promo
            ObservableList<Absence> absences = apprenantDao.getAllAbsencesDate(63, "C#", "2019-2021", Date.valueOf(startDate.getValue()), Date.valueOf(endDate.getValue()));
            colDateAbsenceApprenant.setCellValueFactory(new PropertyValueFactory<>("date"));
            colJustificationApprenant.setCellValueFactory(new PropertyValueFactory<>("justification"));
            SecretaireController.removeScrollBar(apprenantAbsenceTable);
            apprenantAbsenceTable.setItems(absences);
        } else {
            SecretaireController.alertBoxDisplay("S'il vous plait, entez tous les champs nécessaires\n\t\t     pour obtenir le résultat!");
        }
    }


}
