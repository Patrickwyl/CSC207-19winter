package visualization;

import machine.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import user.Manager;

@SuppressWarnings("ALL")
public class ManagerClass {

    Scene menuScene;

    static Manager manager;

    @FXML
    public void createCustomerOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("CustomerCreation.fxml");
    }

    @FXML
    public void viewAccountRequestsOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("ViewAccountRequests.fxml");
    }

    @FXML
    public void undoTransactionsOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("UndoTransactions.fxml");
    }

    @FXML
    public void logOutOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("logout.wav");
        SwitchScene.getSwitchScene().switchScene("Login.fxml");
    }

    @FXML
    public void loadMoneyOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("LoadMoney.fxml");
    }

    @FXML
    public void viewFPRequestsOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("ViewFinancialProductRequests.fxml");
    }

    @FXML
    public void shutDownOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("logout.wav");
        Simulator.getSimulator().updateDate();
        System.exit(0);
    }

    @FXML
    public void changePasswordOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("ChangePassword.fxml");
    }

    @FXML
    public void createFAOnCLick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("FACreation.fxml");
    }
}
