package ma.youcode.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class SecretaireController {

    @FXML
    private AnchorPane comboBoxListSearch;
    @FXML
    private AnchorPane comboBoxJustificatifSearch;
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
    private TableView tableViewJustificatif;
    @FXML
    private TableColumn colJustifNom;
    @FXML
    private TableColumn colJustifPrenom;
    @FXML
    private TableColumn colJustifTelephone;
    @FXML
    private TableColumn colJustifDateAbsence;
    @FXML
    private TableColumn colJustificatif;
    @FXML
    private TableView tableViewList;
    @FXML
    private TableColumn colListNom;
    @FXML
    private TableColumn colListPrenom;
    @FXML
    private TableColumn colListPromo;
    @FXML
    private TableColumn colListClasse;
    @FXML
    private ComboBox justificationChoiceComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private Button saveBtn;

    @FXML
    private void showComboxBoxSearch () {
        comboBoxBilanSearch.setVisible(false);
        comboBoxJustificatifSearch.setVisible(false);
        tableViewJustificatif.setVisible(false);
        tableViewBilan.setVisible(false);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
        comboBoxBilanSearch.setVisible(false);
        comboBoxListSearch.setVisible(true);
    }

    @FXML
    private void showComboxBoxJustificatif () {
        comboBoxListSearch.setVisible(false);
        comboBoxBilanSearch.setVisible(false);
        tableViewBilan.setVisible(false);
        tableViewList.setVisible(false);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
        comboBoxJustificatifSearch.setVisible(true);
    }

    @FXML
    private void showComboxBoxBilan() {
        comboBoxJustificatifSearch.setVisible(false);
        comboBoxListSearch.setVisible(false);
        tableViewJustificatif.setVisible(false);
        tableViewList.setVisible(false);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
        comboBoxBilanSearch.setVisible(true);
    }

    @FXML
    private void showTableViewList () {
        tableViewList.setVisible(true);
    }

    @FXML
    private void showTableViewJustificatif () {
        tableViewJustificatif.setVisible(true);
        justificationChoiceComboBox.setVisible(true);
        saveBtn.setVisible(true);
    }

    @FXML
    private void showTableViewBilan() {
        tableViewBilan.setVisible(true);
    }

}

