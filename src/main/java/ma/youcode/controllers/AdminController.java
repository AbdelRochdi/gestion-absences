package ma.youcode.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ma.youcode.dao.AdminDaoImpl;
import ma.youcode.main.App;
import ma.youcode.models.Utilisateur;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@SuppressWarnings("unchecked")
public class AdminController implements Initializable {

    @FXML
    private Button imageBtn;
    @FXML
    private Label tfImage;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label labelId;
    @FXML
    private Button editPasswordBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button cpBtn;
    @FXML
    private Label errorLabel;
    @FXML
    private DatePicker tfDateNaissance;
    @FXML
    private TextField tfId;
    @FXML
    private TableColumn colRole;
    @FXML
    private TableColumn colPromo;
    @FXML
    private TableColumn colClasse;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
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

    private FileInputStream fis;

    private int fileLength;

    private AdminDaoImpl admin = new AdminDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelId.setVisible(false);
        tfId.setVisible(false);
        showUsers();
        fillComboBox();
        cbPromo.setVisible(false);
        cbClasse.setVisible(false);
    }



    public void handleButtonAction(ActionEvent actionEvent) throws IOException, MessagingException {

        if (actionEvent.getSource() == imageBtn){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
            Stage mainStage =  (Stage) imageBtn.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(mainStage);
            if (selectedFile != null) {
                tfImage.setText(selectedFile.getName());
                fis = new FileInputStream(selectedFile);
                fileLength = (int) selectedFile.length();
            }
        }

        //validation and action for add button
        if (actionEvent.getSource() == addBtn) {
            Utilisateur utilisateur = admin.afficherUtilisateurParEmail(tfEmail.getText());
            if (tfNom.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer un nom");
                return;
            } else if (tfPrenom.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer un prenom");
                return;
            } else if (tfDateNaissance.getValue() == null) {
                errorLabel.setText("Veuillez entrer une date valide");
                return;
            }else if (LocalDate.now().getYear() - tfDateNaissance.getValue().getYear() < 17) {
                errorLabel.setText("L'utilisateur doit avoir au moins 17 ans");
                return;
            } else if (tfTel.getText().isEmpty() || tfTel.getText().length() != 10) {
                errorLabel.setText("Veuillez entrer un numero valide");
                return;
            }else if (isNumeric(tfTel.getText()) == false) {
                errorLabel.setText("Veuillez entrer un numero valide");
                return;
            } else if (!EmailValidator.getInstance().isValid(tfEmail.getText())) {
                errorLabel.setText("Veuillez entrer un email valide");
                return;
            } else if (utilisateur != null) {
                errorLabel.setText("Cet email existe deja");
                return;
            } else if (tfPassword.getText().length() < 8) {
                errorLabel.setText("Veuillez entrer un mot de passe avec un minimum de 8 caracteres");
                return;
            } else if (cbRole.getValue() == null) {
                errorLabel.setText("Veuillez choisir un role");
                return;
            } else if ((cbClasse.getValue() == null || cbClasse.getValue().equals("null")) && cbClasse.isVisible()) {
                errorLabel.setText("Veuillez choisir une classe");
                return;
            } else if ((cbPromo.getValue() == null || cbPromo.getValue().equals("null")) && cbPromo.isVisible()) {
                errorLabel.setText("Veuillez choisir une Promo");
                return;
            } else {
                String hashed = BCrypt.hashpw(tfPassword.getText(), BCrypt.gensalt(12));
                gKey = admin.creerUtilisateur(tfNom.getText(), tfPrenom.getText(), String.valueOf(tfDateNaissance.getValue()), tfTel.getText(), tfEmail.getText(), ((String) cbRole.getValue()), hashed, fis, fileLength);
                if (cbRole.getValue().equals("Apprenant")) {
                    admin.ajouterApprenant(gKey, ((String) cbClasse.getValue()), ((String) cbPromo.getValue()));
                } else if (cbRole.getValue().equals("Formateur")) {
                    admin.ajouterFormateur(gKey, ((String) cbClasse.getValue()));
                } else if (cbRole.getValue() == null) {
                    return;
                }
                errorLabel.setText("Utilisateur créé");
                showUsers();
                sendEmail();
            }

            //validation and action for update button

        } else if (actionEvent.getSource() == updateBtn) {
            Utilisateur utilisateur = null;
            Utilisateur utilisateur2 = null;
            if (!tfId.getText().isEmpty()) {
                utilisateur = admin.afficherUtilisateur(Integer.parseInt(tfId.getText()));
                utilisateur2 = admin.afficherUtilisateurParEmail(tfEmail.getText());
            }

            if (tfId.getText().isEmpty()) {
                errorLabel.setText("Veuillez selectionner un utilisateur");
                return;
            } else if (tfNom.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer un nom");
                return;
            } else if (tfPrenom.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer un prenom");
                return;
            } else if (tfDateNaissance.getValue() == null) {
                errorLabel.setText("Veuillez entrer une date valide");
                return;
            }else if (LocalDate.now().getYear() - tfDateNaissance.getValue().getYear() < 17) {
                errorLabel.setText("L'utilisateur doit avoir au moins 17 ans");
                return;
            } else if (tfTel.getText().isEmpty() || tfTel.getText().length() != 10) {
                errorLabel.setText("Veuillez entrer un numero valide");
                return;
            } else if (!EmailValidator.getInstance().isValid(tfEmail.getText())) {
                errorLabel.setText("Veuillez entrer un email valide");
                return;
            } else if (utilisateur2 != null && (!utilisateur.getEmail().equals(utilisateur2.getEmail()))) {
                errorLabel.setText("Cet email existe deja");
                return;
            } else if (cbRole.getValue() == null || !cbRole.getValue().equals(utilisateur.getRole())) {
                errorLabel.setText("Veuillez choisir le role correspondant");
                return;
            } else if ((cbClasse.getValue() == null || cbClasse.getValue().equals("null")) && cbClasse.isVisible()) {
                errorLabel.setText("Veuillez choisir une classe");
                return;
            } else if ((cbPromo.getValue() == null || cbPromo.getValue().equals("null")) && cbPromo.isVisible()) {
                errorLabel.setText("Veuillez choisir une Promo");
                return;
            } else {
                admin.modifierUtilisateur(Integer.parseInt(tfId.getText()), tfNom.getText(), tfPrenom.getText(), String.valueOf(tfDateNaissance.getValue()), tfTel.getText(), tfEmail.getText(), ((String) cbRole.getValue()), fis, fileLength);
                if (cbRole.getValue().equals("Apprenant")) {
                    admin.modifierApprenant(Integer.parseInt(tfId.getText()), ((String) cbClasse.getValue()), ((String) cbPromo.getValue()));
                } else if (cbRole.getValue().equals("Formateur")) {
                    admin.modifierFormateur(Integer.parseInt(tfId.getText()), ((String) cbClasse.getValue()));
                }
                errorLabel.setText("Utilisateur modifié");
                showUsers();
            }


            //validation and action for delete button
        } else if (actionEvent.getSource() == deleteBtn) {

            if (tfId.getText().isEmpty()) {
                errorLabel.setText("Veuillez selectionner un utilisateur");
                return;
            } else {
                Utilisateur utilisateur = admin.afficherUtilisateur(Integer.parseInt(tfId.getText()));
                if (utilisateur.getRole().equals("Apprenant")) {
                    admin.supprimerApprenant(Integer.parseInt(tfId.getText()));
                    admin.supprimerUtilisateur(Integer.parseInt(tfId.getText()));
                } else if (utilisateur.getRole().equals("Formateur")) {
                    admin.supprimerFormateur(Integer.parseInt(tfId.getText()));
                    admin.supprimerUtilisateur(Integer.parseInt(tfId.getText()));
                } else if (utilisateur.getRole().equals("Secrétaire") || utilisateur.getRole().equals("Admin")) {
                    admin.supprimerUtilisateur(Integer.parseInt(tfId.getText()));
                }
                errorLabel.setText("Utilisateur supprimé");
                showUsers();
            }
        }else if (actionEvent.getSource() == editPasswordBtn) {
            if (tfId.getText().isEmpty()) {
                errorLabel.setText("Veuillez selectionner un utilisateur");
                return;
            } else if (tfPassword.getText().length() < 8) {
                errorLabel.setText("Veuillez entrer un mot de passe avec un minimum de 8 caracteres");
                return;
            }else {
                String hashed = BCrypt.hashpw(tfPassword.getText(), BCrypt.gensalt(12));
                admin.modifierPassword(Integer.parseInt(tfId.getText()), hashed);
                showSearchResult();
                sendEmail();
            }
        } else if (actionEvent.getSource() == searchBtn) {
            System.out.println("search clicked");
            showSearchResult();
        } else if (actionEvent.getSource() == cpBtn) {
            Stage stage = (Stage) cpBtn.getScene().getWindow();
            App.setRoot("classePromo");
            stage.sizeToScene();
            stage.centerOnScreen();
        }else if (actionEvent.getSource() == logoutBtn) {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            App.setRoot("login");
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    // method that populates the tableView
    public void showUsers() {
        ObservableList<Utilisateur> list = admin.afficherTousLesUtilisateur();

        colId.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("date_naissance"));
        colTel.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("role"));
        colClasse.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("classe"));
        colPromo.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("promo"));
        tvUtilisateur.setItems(list);

    }

    public void showSearchResult() {
        ObservableList<Utilisateur> list = admin.rechercherUtilisateur(tfSearch.getText());

        colId.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("date_naissance"));
        colTel.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("role"));
        colClasse.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("classe"));
        colPromo.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("promo"));
        tvUtilisateur.setItems(list);

    }

    // method that fills the comboBox options
    public void fillComboBox() {
        ArrayList<String> roleList = admin.getRoles();
        ArrayList<String> classeList = admin.getClasses();
        ArrayList<String> promoList = admin.getPromos();
        for (int i = 0; i < roleList.size(); i++) {
            cbRole.getItems().add(roleList.get(i));
        }
        for (int i = 0; i < classeList.size(); i++) {
            cbClasse.getItems().add(classeList.get(i));
        }
        for (int i = 0; i < promoList.size(); i++) {
            cbPromo.getItems().add(promoList.get(i));
        }
    }

    //comboBox event method
    public void handleComboBoxAction(ActionEvent actionEvent) {
        //condition to show comboBoxs according to the selected role
        if (cbRole.getValue().equals("Admin") || cbRole.getValue().equals("Secrétaire")) {
            cbPromo.setVisible(false);
            cbClasse.setVisible(false);
        } else if (cbRole.getValue().equals("Formateur")) {
            cbPromo.setVisible(false);
            cbClasse.setVisible(true);
        } else if (cbRole.getValue().equals("Apprenant")) {
            cbPromo.setVisible(true);
            cbClasse.setVisible(true);
        }
    }

    //method that gets the selection from the tableView and puts them in the fields
    public void handleMouseAction(MouseEvent mouseEvent) {

        Utilisateur user = (Utilisateur) tvUtilisateur.getSelectionModel().getSelectedItem();
        if (user != null){
            tfId.setText("" + user.getId());
            tfNom.setText("" + user.getNom());
            tfPrenom.setText("" + user.getPrenom());
            tfDateNaissance.setValue(LocalDate.parse(user.getDate_naissance()));
            tfTel.setText("" + user.getTel());
            tfEmail.setText("" + user.getEmail());
            cbRole.setValue("" + user.getRole());
            cbPromo.setValue("" + user.getPromo());
            cbClasse.setValue("" + user.getClasse());
            tfPassword.setText("");
        }
    }

    public void sendEmail() throws MessagingException {

        String text = "Bonjour, "+tfNom.getText()+" "+tfPrenom.getText()+"\n \n"
                + "Pour vous connecter et visualiser l'état de vos absences dans l'application de "
                +"gestion d'absences de YouCode,\n" +
                "veuillez utiliser l'identifiant et le mot de passe" +
                " ci-dessous: \n \n"
                +"Identifiant : "+tfEmail.getText()+"\n" +
                "Mot de passe : "+tfPassword.getText()+"\n \n \n" +
                "YouCode 2021";

        String host = "smtp.gmail.com";
        String to = tfEmail.getText();
        String username = "youcode.absence@gmail.com";
        String password = "youcode2021";

        Properties prpos = System.getProperties();
        prpos.put("mail.smtp.auth", true);
        prpos.put("mail.smtp.starttls.enable", true);
        prpos.put("mail.smtp.host", host);
        prpos.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(prpos, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage m = new MimeMessage(session);
        m.setFrom(new InternetAddress(username));
        m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress((to)));
        m.setSubject("Identifiant et mot de passe pour l'application de gestion d'absences");
        m.setText(text);

        Transport.send(m);
        System.out.println("Message sent");
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Long d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
