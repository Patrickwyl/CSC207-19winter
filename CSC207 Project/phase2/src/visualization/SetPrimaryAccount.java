package visualization;

import account.Account;
import account.AccountType;
import account.Accounts;
import account.ChequingAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import user.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SetPrimaryAccount implements Initializable {
    private Customer user;
    @FXML
    public TextFlow chequingInfo = new TextFlow();
    @FXML
    public ChoiceBox<String> chequingAccounts = new ChoiceBox<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (Customer) ATMInterface.user;
        Text chequingAccount = new Text(user.chequingInfo());
        chequingInfo.getChildren().add(chequingAccount);
        setChequingAccounts();
    }

    private void setChequingAccounts(){
        for (ArrayList<Account> accountList: user.accounts) {
            for (Account account: accountList) {
                if (account.getAccountType() == AccountType.chequing) {
                    chequingAccounts.getItems().add(account.getAccountNum());
                }
            }
        }
    }

    @FXML
    public void setPrimary(ActionEvent actionEvent) {
        try {
            ChequingAccount account = (ChequingAccount) Accounts.getAccounts().accountFinder(chequingAccounts.getValue());
            user.setPrimary(account);
            ATMInterface.playMusic("confirmation.wav");
            String msg = "Successfully set " + account.getAccountNum() + " as your primary account.";
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().backToMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
