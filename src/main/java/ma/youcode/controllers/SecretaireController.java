package ma.youcode.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SecretaireController {

    @FXML
    private AnchorPane comboBoxListBtnSearch;
    @FXML
    private AnchorPane comboBoxJustificatifSearch;
    @FXML
    private TableView tableViewList;
    @FXML
    private TableView tableViewJustificatif;
    @FXML
    private ComboBox justificationChoiceComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private Button saveBtn;

    @FXML
    private void showComboxBoxSearch () {
        comboBoxJustificatifSearch.setVisible(false);
        tableViewJustificatif.setVisible(false);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
        comboBoxListBtnSearch.setVisible(true);
    }

    @FXML
    private void showTableViewList () {
        tableViewList.setVisible(true);
        justificationChoiceComboBox.setVisible(false);
        saveBtn.setVisible(false);
    }

    @FXML
    private void showComboxBoxJustificatif () {
        comboBoxListBtnSearch.setVisible(false);
        tableViewList.setVisible(false);
        comboBoxJustificatifSearch.setVisible(true);
    }

    @FXML
    private void showTableViewJustificatif () {
        tableViewJustificatif.setVisible(true);
        justificationChoiceComboBox.setVisible(true);
        saveBtn.setVisible(true);
    }
}

