package visualization;

import account.AccountType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputDialog;
import user.Customer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ApplyAccount implements Initializable {
    private Customer user;
    @FXML
    public ChoiceBox<String> accountType = new ChoiceBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (Customer)ATMInterface.user;
        setAccountType();
    }

    private  void setAccountType(){
        for (AccountType type: AccountType.values()){
            accountType.getItems().add(type.getName());
        }
    }

    @FXML
    public void applyAccount(ActionEvent actionEvent) {
        AccountType type = AccountType.getType(accountType.getValue());
        try {
            if (type == AccountType.jointChequing) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Apply for joint chequing account");
                dialog.setHeaderText("Please enter the other owner of this joint account.");
                dialog.setContentText("Enter the other owner's username:");
                Optional<String> username = dialog.showAndWait();
                if (username.isPresent()) {
                    user.requestJointAccount(username.get());
                } else {
                    return;
                }
            } else {
                user.requestAccount(type.name());
            }
            ATMInterface.playMusic("confirmation.wav");
            String msg = "Successfully applied for a " + type.getName() + ".";
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().backToMenu();
        } catch (Exception e) {
            ATMInterface.playMusic("oops.wav");
            String msg = e.getMessage();
            String header = "Request failed";
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();

    }
}
