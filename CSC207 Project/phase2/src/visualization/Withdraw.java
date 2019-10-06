package visualization;

import account.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import user.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Withdraw implements Initializable {
    private Customer user;
    @FXML
    public TextField transferAmount;
    @FXML
    public TextFlow withdrawInfo = new TextFlow();
    @FXML
    public ChoiceBox<String> withdrawAccounts = new ChoiceBox<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (Customer)ATMInterface.user;
        Text withdrawables = new Text(user.withdrawables());
        withdrawInfo.getChildren().add(withdrawables);
        setWithdrawAccounts();
    }
    private void setWithdrawAccounts(){
        for (ArrayList<Account> accountList: user.accounts) {
            for (Account account: accountList) {
                if (account instanceof Withdrawable) {
                    withdrawAccounts.getItems().add(account.getAccountNum());
                }
            }
        }
    }

    public void withdrawMoney(){
        Withdrawable account = (Withdrawable) Accounts.getAccounts().accountFinder(withdrawAccounts.getValue());
        try {
            Integer money = Integer.valueOf(transferAmount.getText());
            account.withdraw(money);
            ATMInterface.playMusic("confirmation.wav");
            String msg = "Successfully withdrew $" + money + " from " + ((Account)account).getAccountNum() + ".";
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().backToMenu();
        } catch (Exception e) {
            ATMInterface.playMusic("oops.wav");
            String msg = e.getMessage();
            String header = "Transaction failed";
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
