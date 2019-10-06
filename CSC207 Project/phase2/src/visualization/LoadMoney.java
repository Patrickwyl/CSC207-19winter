package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import machine.ATM;
import machine.Users;

@SuppressWarnings("ALL")
public class LoadMoney {
    @FXML
    public TextField bill5Input;
    @FXML
    public TextField bill10Input;
    @FXML
    public TextField bill20Input;
    @FXML
    public TextField bill50Input;

    @FXML
    public void loadMoneyOnClick(ActionEvent actionEvent) {
        try{
        boolean isAlert = ATM.getATM().checkATMBalance();
        int cash5 = Integer.parseInt(bill5Input.getText());
        int cash10 = Integer.parseInt(bill10Input.getText());
        int cash20 = Integer.parseInt(bill20Input.getText());
        int cash50 = Integer.parseInt(bill50Input.getText());

        if(cash5 >= 0 && cash10 >= 0 && cash20 >= 0 && cash50 >= 0){
            Users.getUsers().getManager().addMoneyToATM(cash5, cash10, cash20, cash50);
            ATMInterface.playMusic("click.wav");
            if (isAlert && !ATM.getATM().checkATMBalance()){
                String msg = "You have successfully loaded money to ATM and resolved the alert";
                ATMInterface.produceConfirmationAlert(msg);
            } else if(ATM.getATM().checkATMBalance()){
                String msg = "You have successfully loaded money to ATM, but the alert still exists";
                ATMInterface.produceConfirmationAlert(msg);
            }
            else {
                String msg = "You have successfully loaded money to ATM";
                ATMInterface.produceConfirmationAlert(msg);
                SwitchScene.getSwitchScene().switchScene("Manager.fxml");
            }
        }else {
            ATMInterface.playMusic("oops.wav");
            ATMInterface.invalidNumberAlert();
        }
        }
        catch (Exception NumberFormatException){
            ATMInterface.playMusic("oops.wav");
            ATMInterface.invalidNumberAlert();
        }
    }

    @FXML
    public void backOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().switchScene("Manager.fxml");
    }

    @FXML
    public void viewAlertOnCLick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        String msg;
        if (ATM.getATM().checkATMBalance()){
            msg = ATM.getATM().displayAlert();
        } else{
            msg = "There is no alert. The amount of denominations are all above the limit 20.";
        }
        String header = "Alert";
        ATMInterface.produceInformationAlert(msg, header);
    }
}
