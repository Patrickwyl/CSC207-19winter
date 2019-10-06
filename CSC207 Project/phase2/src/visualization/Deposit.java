package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import user.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class Deposit implements Initializable {
    @FXML
    public TextField transferAmount;
    @FXML
    public TextField cash5;
    @FXML
    public TextField cash20;
    @FXML
    public TextField cash10;
    @FXML
    public TextField cash50;
    private Customer user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (Customer)ATMInterface.user;
    }

    @FXML
    public void depositCheque(ActionEvent actionEvent) {
        try {
            Double money = Double.valueOf(transferAmount.getText());
            user.getPrimaryChequing().deposit(money);
            String msg = "Successfully deposited $" + money + ".";
            ATMInterface.playMusic("confirmation.wav");
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().backToMenu();
        } catch (Exception e) {
            String msg = "Please try again.";
            String header = "Deposit failed";
            ATMInterface.playMusic("oops.wav");
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    @FXML
    public void depositCash(ActionEvent actionEvent) {
        try{
            Integer cash5Amount = Integer.valueOf(cash5.getText());
            Integer cash10Amount = Integer.valueOf(cash10.getText());
            Integer cash20Amount = Integer.valueOf(cash20.getText());
            Integer cash50Amount = Integer.valueOf(cash50.getText());
            Integer total = cash5Amount*5 + cash10Amount*10 + cash20Amount*20 + cash50Amount*50;
            user.getPrimaryChequing().deposit(cash5Amount, cash10Amount, cash20Amount, cash50Amount);
            String msg = "Successfully deposited $" + total + ".";
            ATMInterface.playMusic("confirmation.wav");
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().backToMenu();
        } catch (Exception e) {
            String msg = "Please try again.";
            String header = "Deposit failed";
            ATMInterface.playMusic("oops.wav");
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
