package ma.youcode.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.youcode.dao.*;
import ma.youcode.main.App;
import ma.youcode.models.Absence;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;


public class SecretaireController {

    @FXML
    private ComboBox<String> comboBoxClasseList;
    @FXML
    private ComboBox<String> comboBoxPromoList;
    @FXML
    private ComboBox<String> comboBoxClasseBilan;
    @FXML
    private ComboBox<String> comboBoxPromoBilan;
    @FXML
    private AnchorPane comboBoxListSearch;
    @FXML
    private AnchorPane comboBoxBilanSearch;
    @FXML
    private DatePicker startDateBilan;
    @FXML
    private DatePicker endDateBilan;
    @FXML
    private TableView<Absence> tableViewBilan;
    @FXML
    private TableColumn<Absence, String> colBilanNom;
    @FXML
    private TableColumn<Absence, String> colBilanPrenom;
    @FXML
    private TableColumn<Absence, String> colBilanTel;
    @FXML
    private TableColumn<Absence, Integer> colBilanNumAbsences;
    @FXML
    private TableView<Absence> tableViewList;
    @FXML
    private TableColumn<Absence, String> colListNom;
    @FXML
    private TableColumn<Absence, String> colListPrenom;
    @FXML
    private TableColumn<Absence, String> colListTel;
    @FXML
    private TableColumn<Absence, String> colListJutificatif;
    @FXML
    private DatePicker dateAbsence;
    @FXML
    private HBox hBoxList;
    @FXML
    private HBox hBoxBilan;
    @FXML
    private Button listBtn;
    @FXML
    private Button bilanBtn;

    @FXML
    private void initialize() {
        dateAbsence.setValue(LocalDate.now());
        SecretaireDao secretaireDao = new SecretaireDaoImpl();
        ArrayList<String> classeList = secretaireDao.getClasseValues();
        ArrayList<String> promoList = secretaireDao.getPromoValues();
        for (String classe : classeList) {
            comboBoxClasseBilan.getItems().add(classe);
            comboBoxClasseList.getItems().add(classe);
        }
        for (String promo : promoList) {
            comboBoxPromoBilan.getItems().add(promo);
            comboBoxPromoList.getItems().add(promo);
        }
    }

    @FXML
    private void showAbsences() {
        String classe = comboBoxClasseList.getValue();
        String promo = comboBoxPromoList.getValue();
        SecretaireDao secretaire = new SecretaireDaoImpl();
            if (classe != null && promo != null) {
                boolean isValidDate = dateAbsence.getValue().compareTo(LocalDate.now()) <= 0;
                if (isValidDate) {
                    ObservableList<Absence> absences = secretaire.getAllAbsencesByClasse(classe, promo, Date.valueOf(dateAbsence.getValue()));
                    if (!absences.isEmpty()) {
                        tableViewList.setEditable(true);
                        removeScrollBar(tableViewList);
                        ObservableList<String> justificationState = FXCollections.observableArrayList();
                        justificationState.add("Approuvé");
                        justificationState.add("Non approuvé");
                        colListNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                        colListPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                        colListTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
                        colListJutificatif.setCellValueFactory(new PropertyValueFactory<>("justification"));
                        colListJutificatif.setCellFactory(ComboBoxTableCell.forTableColumn(justificationState));
                        colListJutificatif.setOnEditCommit(absencesStringCellEditEvent -> {
                            Absence absence = absencesStringCellEditEvent.getRowValue();
                            absence.setJustification(absencesStringCellEditEvent.getNewValue());
                            secretaire.updateJustificationUsers(absence.getId_absence(), absence.getJustification());
                            alertBoxDisplay("Justification modifié");
                        });
                        tableViewList.setItems(absences);
                    } else {
                        tableViewList.getItems().clear();
                        alertBoxDisplay("Aucun résultats obtenus!");
                    }
                } else {
                    tableViewList.getItems().clear();
                    alertBoxDisplay("Impossible de dépasser la date d'aujourd'hui");
                }

            } else {
                alertBoxDisplay("S'il vous plait, entrez tous les champs nécessaires\n\t\t     pour obtenir le résultat!");
            }
    }

    @FXML
    private void showAbsencesBilan() {
        String classe = comboBoxClasseBilan.getValue();
        String promo = comboBoxPromoBilan.getValue();
        SecretaireDao secretaire = new SecretaireDaoImpl();
        if (classe != null && promo != null && startDateBilan.getValue() != null && endDateBilan.getValue() != null) {
            boolean areValidDates = startDateBilan.getValue().compareTo(LocalDate.now()) <= 0 && endDateBilan.getValue().compareTo(LocalDate.now()) <= 0;
            if (areValidDates) {
                ObservableList<Absence> absences = secretaire.getAllAbsencesStateByClasse(classe, promo, Date.valueOf(startDateBilan.getValue()), Date.valueOf(endDateBilan.getValue()));
                if (!absences.isEmpty()) {
                    colBilanNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    colBilanPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                    colBilanTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
                    colBilanNumAbsences.setCellValueFactory(new PropertyValueFactory<>("absencesTotal"));
                    tableViewBilan.setItems(absences);
                    removeScrollBar(tableViewBilan);
                } else {
                    tableViewBilan.getItems().clear();
                    alertBoxDisplay("Aucun résultats obtenus!");
                }
            } else {
                tableViewBilan.getItems().clear();
                alertBoxDisplay("Impossible de dépasser la date d'aujourd'hui");
            }
        } else {
            alertBoxDisplay("S'il vous plait, entrez tous les champs nécessaires\n\t\t     pour obtenir le résultat!");
        }


    }

    @FXML
    private void returnToAbsencesBilan() {
        comboBoxListSearch.setVisible(false);
        tableViewList.setVisible(false);
        comboBoxBilanSearch.setVisible(true);
        tableViewBilan.setVisible(true);
        bilanBtn.setStyle("-fx-background-color:  #0099ff;  -fx-text-fill: #fdffff; -fx-font-weight: bold; -fx-font-size: 13;");
        hBoxBilan.setStyle("-fx-background-color:  #0099ff; -fx-background-radius: 0 4 4 0; -fx-alignment: center;");
        listBtn.setStyle("-fx-background-color:  #fdffff;  -fx-text-fill: #0099ff; -fx-font-weight: bold; -fx-font-size: 13;");
        hBoxList.setStyle("-fx-background-color:  #fdffff; -fx-background-radius: 4 0 0 4; -fx-alignment: center;");
    }

    @FXML
    private void showAbsencesList() {
        comboBoxBilanSearch.setVisible(false);
        tableViewBilan.setVisible(false);
        comboBoxListSearch.setVisible(true);
        tableViewList.setVisible(true);
        listBtn.setStyle("-fx-background-color:  #0099ff;  -fx-text-fill: #fdffff; -fx-font-weight: bold; -fx-font-size: 13;");
        hBoxList.setStyle("-fx-background-color:  #0099ff; -fx-background-radius: 4 0 0 4; -fx-alignment: center;");
        bilanBtn.setStyle("-fx-background-color:  #fdffff;  -fx-text-fill: #0099ff; -fx-font-weight: bold; -fx-font-size: 13;");
        hBoxBilan.setStyle("-fx-background-color:  #fdffff; -fx-background-radius: 0 4 4 0; -fx-alignment: center;");

    }

    @FXML
    private void logoutSecretaire() throws IOException {
        Stage stage = (Stage) listBtn.getScene().getWindow();
        App.setRoot("login");
        stage.sizeToScene();
        stage.centerOnScreen();

    }

    public static <T extends Control> void removeScrollBar(T table) {
        ScrollBar scrollBar = (ScrollBar) table.queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);
        if (scrollBar != null) {
            scrollBar.setPrefHeight(0);
            scrollBar.setMaxHeight(0);
            scrollBar.setOpacity(0);
            scrollBar.setVisible(false);
        }
    }

    public static void alertBoxDisplay(String message) {
        Stage dialogWindow = new Stage();
        // prevent any user interaction outside of Dialog Box until Dialog Box closed
        dialogWindow.initModality(Modality.APPLICATION_MODAL);
        dialogWindow.setTitle("Alerte");
        dialogWindow.setWidth(500);
        dialogWindow.setHeight(140);
        dialogWindow.setResizable(false);
        dialogWindow.getIcons().add(new Image("file:src/main/resources/ma/youcode/main/warning.png"));
        Label label = new Label(message);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill:  #252525");
        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setStyle("-fx-padding: 10");
        wrapper.getChildren().add(label);
        Scene scene = new Scene(wrapper);
        dialogWindow.setScene(scene);
        dialogWindow.showAndWait();
    }

}

