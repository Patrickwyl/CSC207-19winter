package visualization;

import account.Account;
import account.Accounts;
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
public class UndoTransactions implements Initializable {
    @FXML
    public TextField accountNumberInput;

    @FXML
    public ChoiceBox<Integer> numberOfTransactionInput;

    @FXML
    public void backOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().switchScene("Manager.fxml");
    }

    @FXML
    public void confirmOnClick(ActionEvent actionEvent) {
        String accountNum = accountNumberInput.getText();
        int num = numberOfTransactionInput.getValue();
        if (Accounts.getAccounts().accountFinder(accountNum) == null){
            // account number does not exist
            ATMInterface.playMusic("oops.wav");
            String msg = "The account number does not exist.";
            String header = "Invalid Input";
            ATMInterface.produceErrorAlert(msg, header);
            SwitchScene.getSwitchScene().switchScene("Manager.fxml");
        }else{
            Account account = Accounts.getAccounts().accountFinder(accountNum);
            try {
                Users.getUsers().getManager().undoTransaction(account, num);
                String msg = "You have successfully undo " + num + " transaction(s) to account " + accountNum;
                ATMInterface.playMusic("confirmation.wav");
                ATMInterface.produceConfirmationAlert(msg);
            } catch (Exception e) {
                // maximum number of transactions exceeded
                ATMInterface.playMusic("oops.wav");
                String msg = "The account does not have " + num + " transaction(s)";
                String header = "Invalid Input";
                ATMInterface.produceErrorAlert(msg, header);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfTransactionInput.setValue(1);
        numberOfTransactionInput.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
}
