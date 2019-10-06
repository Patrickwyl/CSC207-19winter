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
public class CustomerCreation implements Initializable {
    @FXML
    private ChoiceBox<String> levelInput;

    @FXML
    public TextField customerUsernameInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelInput.setValue("regular");
        levelInput.setItems(FXCollections.observableArrayList("regular", "silver", "gold", "platinum"));
    }

    @FXML
    public void backOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().switchScene("Manager.fxml");
    }

    @FXML
    public void createCustomerOnClick(ActionEvent actionEvent) {
        String username = customerUsernameInput.getText();
        String level = levelInput.getValue();
        if (Users.getUsers().customerFinder(username) != null ||
                Users.getUsers().getManager().getUsername().equals(username) ||
                Users.getUsers().financialAdviserFinder(username) != null){
            String msg = "The username already exists. Please type a different username";
            String header = "Username Duplicated";
            ATMInterface.playMusic("oops.wav");
            ATMInterface.produceErrorAlert(msg, header);

        }else{
            String msg = "Successfully created new customer. Username: " + username
                    + " Password: 123456." + "userLevel: " + level;
            Users.getUsers().getManager().addCustomer(username, level);
            ATMInterface.playMusic("confirmation.wav");
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().switchScene("Manager.fxml");
        }
    }
}
