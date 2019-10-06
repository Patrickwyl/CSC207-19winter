package visualization;

import account.Account;
import account.AccountType;
import account.Accounts;
import account.TransferOutable;
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

public class TransferMoney implements Initializable {
    private Customer user;
    @FXML
    public TextFlow accountsInfo = new TextFlow();
    @FXML
    public ChoiceBox<String> outAccounts = new ChoiceBox<>();
    @FXML
    public TextField inAccount;
    @FXML
    public TextField transferAmount;
    @FXML
    public TextField payeeName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (Customer)ATMInterface.user;
        Text transferOutables = new Text(user.transferOutableAccounts());
        accountsInfo.getChildren().add(transferOutables);
        setOutAccounts();
    }
    private void setOutAccounts(){
        for (ArrayList<Account> accountList: user.accounts) {
            for (Account account: accountList) {
                if (account.getAccountType() != AccountType.credit) {
                    outAccounts.getItems().add(account.getAccountNum());
                }
            }
        }
    }

    @FXML
    public void transfer(ActionEvent actionEvent) {
        TransferOutable outAccount = (TransferOutable) Accounts.getAccounts().accountFinder(outAccounts.getValue());
        Account inAccount = Accounts.getAccounts().accountFinder(this.inAccount.getText());
        if (inAccount == null){
            ATMInterface.playMusic("oops.wav");
            String msg = "The account number you enter is not valid. Please try again.";
            String header = "Transaction failed";
            ATMInterface.produceErrorAlert(msg, header);
        } else {
            try {
                Double money = Double.valueOf(transferAmount.getText());
                outAccount.transferOut(money, inAccount);
                ATMInterface.playMusic("confirmation.wav");
                String msg = "Successfully transferred $" + money + " to " + inAccount.getAccountNum() + ".";
                ATMInterface.produceConfirmationAlert(msg);
                SwitchScene.getSwitchScene().backToMenu();
            } catch (Exception e) {
                e.printStackTrace();
                ATMInterface.playMusic("oops.wav");
                String msg = "The money amount you enter is not valid. Please try again.";
                String header = "Transaction failed";
                ATMInterface.produceErrorAlert(msg, header);
            }
        }
    }

    @FXML
    public void pay(ActionEvent actionEvent) {
        TransferOutable outAccount = (TransferOutable) Accounts.getAccounts().accountFinder(outAccounts.getValue());
        try {
            Double money = Double.valueOf(transferAmount.getText());
            outAccount.payBill(money, payeeName.getText());
            ATMInterface.playMusic("confirmation.wav");
            String msg = "Successfully transferred $" + money + " to " + payeeName.getText() + ".";
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().backToMenu();
        } catch (Exception e) {
            ATMInterface.playMusic("oops.wav");
            String msg = "The money amount you enter is not valid. Please try again.";
            String header = "Transaction failed";
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
