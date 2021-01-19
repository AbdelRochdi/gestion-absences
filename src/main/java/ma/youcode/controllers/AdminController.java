package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.youcode.dao.AdminDaoImpl;
import ma.youcode.models.Utilisateur;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField tfId;
    @FXML
    private TableColumn colRole;
    @FXML
    private TableColumn colPromo;
    @FXML
    private TableColumn colClasse;
    @FXML
    private TableColumn colPassword;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfDateNaissance;
    @FXML
    private TextField tfTel;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private ComboBox cbRole;
    @FXML
    private ComboBox cbClasse;
    @FXML
    private ComboBox cbPromo;
    @FXML
    private TableView tvUtilisateur;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNom;
    @FXML
    private TableColumn colPrenom;
    @FXML
    private TableColumn colDateNaissance;
    @FXML
    private TableColumn colTel;
    @FXML
    private TableColumn colEmail;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;

    private int gKey;

    private AdminDaoImpl admin = new AdminDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showUsers();
        cbPromo.setVisible(false);
        cbClasse.setVisible(false);
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        if (actionEvent.getSource() == addBtn) {
            gKey = admin.creerUtilisateur(tfNom.getText(),tfPrenom.getText(),tfDateNaissance.getText(),tfTel.getText(),tfEmail.getText(),((String) cbRole.getValue()),tfPassword.getText());
            if (cbRole.getValue().equals("Apprenant")){
                admin.ajouterApprenant(gKey, ((String) cbClasse.getValue()),((String) cbPromo.getValue()) );
            }else if (cbRole.getValue().equals("Formateur")){
                admin.ajouterFormateur(gKey, ((String) cbClasse.getValue()));
            }
            showUsers();
        } else if (actionEvent.getSource() == updateBtn) {
            admin.modifierUtilisateur(Integer.parseInt(tfId.getText()),tfNom.getText(),tfPrenom.getText(),tfDateNaissance.getText(),tfTel.getText(),tfEmail.getText(),((String) cbRole.getValue()),tfPassword.getText());
            showUsers();
        } else if (actionEvent.getSource() == deleteBtn) {
            admin.supprimerUtilisateur(Integer.parseInt(tfId.getText()));
            showUsers();
        }
    }

    public void showUsers() {
        ObservableList<Utilisateur> list = admin.afficherTousLesUtilisateur();

        colId.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("date_naissance"));
        colTel.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));

        tvUtilisateur.setItems(list);
        fillComboBox();
    }

    public void fillComboBox() {
        ArrayList<String> roleList = admin.getRoles();
        ArrayList<String> classeList = admin.getClasses();
        ArrayList<String> promoList = admin.getPromos();
        for (int i = 0; i<roleList.size(); i++ ){
            cbRole.getItems().add(roleList.get(i));
        }for (int i = 0; i<classeList.size(); i++ ){
            cbClasse.getItems().add(classeList.get(i));
        }for (int i = 0; i<promoList.size(); i++ ){
            cbPromo.getItems().add(promoList.get(i));
        }
    }

    public void handleComboBoxAction(ActionEvent actionEvent) {
        if (cbRole.getValue().equals("Admin") || cbRole.getValue().equals("Secrétaire")){
            cbPromo.setVisible(false);
            cbClasse.setVisible(false);
        }else if (cbRole.getValue().equals("Formateur")){
            cbPromo.setVisible(false);
            cbClasse.setVisible(true);
        }else if (cbRole.getValue().equals("Apprenant")){
            cbPromo.setVisible(true);
            cbClasse.setVisible(true);
        }
    }
}
