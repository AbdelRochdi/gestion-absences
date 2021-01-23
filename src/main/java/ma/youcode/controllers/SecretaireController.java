package ma.youcode.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.youcode.dao.SecretaireDao;
import ma.youcode.dao.SecretaireDaoImpl;
import ma.youcode.models.Absences;

import java.sql.Date;
import java.time.LocalDate;

public class SecretaireController {

    @FXML
    private ComboBox<String> comboBoxClasseList;
    @FXML
    private ComboBox<String> comboBoxPromoList;
    @FXML
    private AnchorPane comboBoxListSearch;
    @FXML
    private AnchorPane comboBoxBilanSearch;
    @FXML
    private TableView tableViewBilan;
    @FXML
    private TableColumn colListeApprenant;
    @FXML
    private TableColumn colJan;
    @FXML
    private TableColumn colFeb;
    @FXML
    private TableColumn colMar;
    @FXML
    private TableColumn colApr;
    @FXML
    private TableColumn colMay;
    @FXML
    private TableColumn colJun;
    @FXML
    private TableColumn colJul;
    @FXML
    private TableColumn colAug;
    @FXML
    private TableColumn colSep;
    @FXML
    private TableColumn colOct;
    @FXML
    private TableColumn colNov;
    @FXML
    private TableColumn colDec;
    @FXML
    private TableView<Absences> tableViewJustificatif;
    @FXML
    private TableColumn<Absences, String> colJustifNom;
    @FXML
    private TableColumn<Absences, String> colJustifPrenom;
    @FXML
    private TableColumn<Absences, String> colJustifTelephone;
    @FXML
    private TableColumn<Absences, String> colJustifDateAbsence;
    @FXML
    private TableColumn<Absences, String> colJustificatif;
    @FXML
    private TableView<Absences> tableViewList;
    @FXML
    private TableColumn<Absences, String> colListNom;
    @FXML
    private TableColumn<Absences, String> colListPrenom;
    @FXML
    private TableColumn<Absences, String> colListDateAbsence;
    @FXML
    private TableColumn<Absences, String> colListJutificatif;
    @FXML
    private ComboBox justificationChoiceComboBox;
    @FXML
    private DatePicker dateAbsence;
    @FXML
    private Button saveBtn;

    @FXML
    private void initialize() {
        dateAbsence.setValue(LocalDate.now());
    }

    @FXML
    private void showComboxBoxSearch () {
        comboBoxBilanSearch.setVisible(false);
        tableViewJustificatif.setVisible(false);
        tableViewBilan.setVisible(false);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
        comboBoxBilanSearch.setVisible(false);
        tableViewList.setVisible(true);
        comboBoxListSearch.setVisible(true);
    }

    @FXML
    private void showComboxBoxJustificatif () {
        comboBoxListSearch.setVisible(false);
        comboBoxBilanSearch.setVisible(false);
        tableViewBilan.setVisible(false);
        tableViewList.setVisible(false);
        justificationChoiceComboBox.setVisible(true);
        saveBtn.setVisible(true);
        tableViewJustificatif.setVisible(true);
    }

    @FXML
    private void showComboxBoxBilan() {
        comboBoxListSearch.setVisible(false);
        tableViewJustificatif.setVisible(false);
        tableViewList.setVisible(false);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
        comboBoxBilanSearch.setVisible(true);
        tableViewBilan.setVisible(true);
    }

    @FXML
    private void showTableViewList () {
        String classe = comboBoxClasseList.getValue();
        String promo = comboBoxPromoList.getValue();
        SecretaireDao secretaire = new SecretaireDaoImpl();
        ObservableList<Absences> absences = secretaire.getAllAbsencesByClasse(classe, promo, Date.valueOf(dateAbsence.getValue()));

        if (comboBoxPromoList.getValue() != null && comboBoxClasseList.getValue() != null) {
            getAbsencesData(absences);
            tableViewList.setVisible(true);
            tableViewList.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 1) {
                    // check the table's selected item and get selected item
                    if (tableViewList.getSelectionModel().getSelectedItem() != null) {
                        ObservableList<Absences> absencesJustificatif = FXCollections.observableArrayList();
                        Absences absence = tableViewList.getSelectionModel().getSelectedItem();
                        absencesJustificatif.add(absence);
                        colJustifNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                        colJustifPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                        colJustifTelephone.setCellValueFactory(new PropertyValueFactory<>("tel"));
                        colJustifDateAbsence.setCellValueFactory(new PropertyValueFactory<>("date"));
                        colJustificatif.setCellValueFactory(new PropertyValueFactory<>("justification"));
                        tableViewJustificatif.setItems(absencesJustificatif);
                        saveBtn.setOnAction(actionEvent -> {
                            updateAbsencesData(secretaire, absence, absences);
                        });
                    }
                }
            });
        } else {
            alertBoxDisplay("Alert", "S'il vous enter la classe et la promo ansi la date correspondant");
        }
    }

    private void updateAbsencesData(SecretaireDao secretaire, Absences absence, ObservableList<Absences> absences) {
        if (justificationChoiceComboBox.getValue() != null) {
            secretaire.updateJustificationUsers(absence.getId_absence(), justificationChoiceComboBox.getValue().toString());
            tableViewJustificatif.setVisible(false);
            justificationChoiceComboBox.setVisible(false);
            saveBtn.setVisible(false);
            comboBoxListSearch.setVisible(true);
            tableViewList.setVisible(true);
            tableViewJustificatif.getItems().clear();
            getAbsencesData(absences);

        }
    }

    private void getAbsencesData(ObservableList<Absences> absences) {
        colListNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colListPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colListDateAbsence.setCellValueFactory(new PropertyValueFactory<>("date"));
        colListJutificatif.setCellValueFactory(new PropertyValueFactory<>("justification"));
        tableViewList.setItems(absences);
    }


    private void alertBoxDisplay(String title, String message) {
        Stage dialogWindow = new Stage();
        // prevent any user interaction outside of Dialog Box until Dialog Box closed
        dialogWindow.initModality(Modality.APPLICATION_MODAL);
        dialogWindow.setTitle(title);
        dialogWindow.setMinWidth(80);
        dialogWindow.setResizable(false);
        Label label = new Label(message);
        Button closeBtn = new Button("Fermer");
        closeBtn.setOnAction(e -> dialogWindow.close());
        VBox wrapper = new VBox();
        wrapper.getChildren().addAll(label, closeBtn);
        wrapper.setAlignment(Pos.CENTER);
        Scene scene = new Scene(wrapper);
        dialogWindow.setScene(scene);
        dialogWindow.showAndWait();
    }

}

