package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ma.youcode.dao.AdminDaoImpl;
import ma.youcode.main.App;
import ma.youcode.models.Classe;
import ma.youcode.models.Promo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassePromoController implements Initializable {

    @FXML
    private Button userBtn;
    @FXML
    private Label classeError;
    @FXML
    private Label promoError;
    @FXML
    private TableView tvClasse;
    @FXML
    private TableColumn colClasseId;
    @FXML
    private TableColumn colClasseName;
    @FXML
    private Button addClasseBtn;
    @FXML
    private Button updateClasseBtn;
    @FXML
    private Button deleteClasseBtn;
    @FXML
    private Button addPromoBtn;
    @FXML
    private Button updatePromoBtn;
    @FXML
    private Button deletePromoBtn;
    @FXML
    private TextField tfClasseId;
    @FXML
    private TextField tfClasseName;
    @FXML
    private TextField tfPromoId;
    @FXML
    private TextField tfPromoName;
    @FXML
    private TableView tvPromo;
    @FXML
    private TableColumn colPromoId;
    @FXML
    private TableColumn colPromoName;

    AdminDaoImpl admin = new AdminDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showClasses();
        showPromos();
    }

    public void handleButtonAction(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == addClasseBtn) {
            if (tfClasseName.getText().isEmpty()) {
                classeError.setText("Veuillez ajouter le titre de la classe");
                return;
            } else {
                admin.ajouterClasse(tfClasseName.getText());
                showClasses();
                classeError.setText("Classe Ajoutée");
            }

        } else if (actionEvent.getSource() == updateClasseBtn) {
            if (tfClasseId.getText().isEmpty()) {
                classeError.setText("Veuillez ajouter le titre de la classe");
                return;
            } else if (tfClasseName.getText().isEmpty()) {
                classeError.setText("Veuillez ajouter le titre de la classe");
                return;
            } else {
                admin.modifierClasse(Integer.parseInt(tfClasseId.getText()), tfClasseName.getText());
                showClasses();
                classeError.setText("Classe Modifiée");
            }
        } else if (actionEvent.getSource() == deleteClasseBtn) {
            if (tfClasseId.getText().isEmpty()) {
                classeError.setText("Veuillez entrer un id valide");
                return;
            }else{
                admin.supprimerClasse(Integer.parseInt(tfClasseId.getText()));
                showClasses();
                classeError.setText("Classe Supprimée");
            }
        } else if (actionEvent.getSource() == addPromoBtn) {
            if (tfPromoName.getText().isEmpty()) {
                promoError.setText("Veuillez ajouter le titre de la promo");
                return;
            }else {
                admin.ajouterPromo(tfPromoName.getText());
                showPromos();
                promoError.setText("Promo Ajoutée");
            }
        } else if (actionEvent.getSource() == updatePromoBtn) {
            if (tfPromoId.getText().isEmpty()) {
                promoError.setText("Veuillez entrer un id valide");
                return;
            } else if (tfPromoName.getText().isEmpty()) {
                promoError.setText("Veuillez ajouter le titre de la promo");
                return;
            } else {
                admin.modifierPromo(Integer.parseInt(tfPromoId.getText()), tfPromoName.getText());
                showPromos();
                promoError.setText("Promo Modifiée");
            }
        } else if (actionEvent.getSource() == deletePromoBtn) {
            if (tfPromoId.getText().isEmpty()) {
                promoError.setText("Veuillez entrer un id valide");
                return;
            }else{
                admin.supprimerPromo(Integer.parseInt(tfPromoId.getText()));
                showPromos();
                promoError.setText("Promo Supprimée");
            }
        }else if (actionEvent.getSource() == userBtn){
            Stage stage = (Stage) userBtn.getScene().getWindow();
            App.setRoot("admin");
            stage.sizeToScene();
        }
    }

    public void showClasses() {
        ObservableList<Classe> classeList = admin.afficherClasses();

        colClasseId.setCellValueFactory(new PropertyValueFactory<Classe, Integer>("id"));
        colClasseName.setCellValueFactory(new PropertyValueFactory<Classe, String>("classe"));

        tvClasse.setItems(classeList);
    }

    public void showPromos() {
        ObservableList<Promo> promoList = admin.afficherPromos();

        colPromoId.setCellValueFactory(new PropertyValueFactory<Promo, Integer>("id"));
        colPromoName.setCellValueFactory(new PropertyValueFactory<Promo, String>("promo"));

        tvPromo.setItems(promoList);
    }

    public void handleMouseAction(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() == tvClasse){

            Classe classe = (Classe) tvClasse.getSelectionModel().getSelectedItem();

            if (classe != null) {
                tfClasseId.setText("" + classe.getId());
                tfClasseName.setText("" + classe.getClasse());
            }
        }else if (mouseEvent.getSource() == tvPromo){
            Promo promo = (Promo) tvPromo.getSelectionModel().getSelectedItem();
            if (promo != null) {
                tfPromoId.setText("" + promo.getId());
                tfPromoName.setText("" + promo.getPromo());
            }
        }

    }
}
