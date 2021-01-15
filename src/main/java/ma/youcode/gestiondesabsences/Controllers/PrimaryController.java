package ma.youcode.gestiondesabsences.Controllers;

import ma.youcode.gestiondesabsences.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
         App.setRoot("secondary");
    }
}
