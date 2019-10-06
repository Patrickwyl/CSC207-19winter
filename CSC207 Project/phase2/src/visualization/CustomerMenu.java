package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import user.Customer;

@SuppressWarnings("ALL")
public class CustomerMenu {

    static Customer user;

    @FXML
    void viewAccountsInfo(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("CustomerInfo.fxml");
    }

    @FXML
    void payBill(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("PayBill.fxml");
    }

    @FXML
    void makeTransaction(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("MakeTransaction.fxml");
    }

    @FXML
    void requestAccount(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("RequestAccount.fxml");
    }

    @FXML
    void withdraw(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("Withdraw.fxml");
    }

    @FXML
    void requestProduct(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("RequestProduct.fxml");
    }

    @FXML
    void changePassword(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("ChangePassword.fxml");
    }

    @FXML
    void setPrimaryAccount(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("SetPrimary.fxml");
    }

    @FXML
    void deposit(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("Deposit.fxml");
    }

    @FXML
    void logout(ActionEvent actionEvent) throws Exception{
        ATMInterface.playMusic("logout.wav");
        SwitchScene.getSwitchScene().switchScene("Login.fxml");
    }

}
