package visualization;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import machine.Users;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class FACreation implements Initializable {
    @FXML
    public TextField usernameInput;

    @FXML
    public ChoiceBox<String> levelInput;

    @FXML
    public void FACreationConfirmOnClick(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String level = levelInput.getValue();
        if (Users.getUsers().customerFinder(username) != null ||
                Users.getUsers().getManager().getUsername().equals(username) ||
                Users.getUsers().financialAdviserFinder(username) != null){
            ATMInterface.playMusic("oops.wav");
            String msg = "The username already exists. Please type a different username";
            String header = "Username Duplicated";
            ATMInterface.produceErrorAlert(msg, header);
        } else{
            Users.getUsers().getManager().addFinancialAdviser(username, level);
            ATMInterface.playMusic("confirmation.wav");
            String msg = "Successfully created new financial adviser. Username: " + username
                + " Password: 123456." + "userLevel: " + level;
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().switchScene("Manager.fxml");
        }
    }

    @FXML
    public void FACreationBackOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().switchScene("Manager.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelInput.setValue("regular");
        levelInput.setItems(FXCollections.observableArrayList("regular", "silver", "gold", "platinum"));
    }
}
