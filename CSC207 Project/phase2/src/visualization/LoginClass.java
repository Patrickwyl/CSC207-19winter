package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import machine.*;
import user.*;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class LoginClass implements Initializable {
    @FXML
    public TextFlow dateDisplay = new TextFlow();

    @FXML
    TextField usernameInput;

    @FXML
    PasswordField passwordInput;

    @FXML
    public void loginButtonOnClick(ActionEvent actionEvent) {
        try {
            String username = usernameInput.getText();
            String password = passwordInput.getText();

            Person user = Simulator.getSimulator().loginPass(username, password);
            String menu;
            ATMInterface.user = user;
            if (user == Users.getUsers().getManager()) {
                menu = "Manager.fxml";
                ManagerClass.manager = (Manager) user;
            } else if (user instanceof Customer){
                menu = "CustomerMenu.fxml";
                CustomerMenu.user = (Customer)user;
            }else {
                menu = "FinancialAdviserMenu.fxml";
                FinancialAdviserMenu.employee = (FinancialAdviser) user;
                CustomerMenu.user = FinancialAdviserMenu.employee.getFinancialUser();
                ATMInterface.user = ((FinancialAdviser) user).getFinancialUser();
            }
            ATMInterface.playMusic("login.wav");
            SwitchScene.getSwitchScene().switchScene(menu);

        } catch (Exception e) {
            ATMInterface.playMusic("oops.wav");
            loginFailedAlert();
        }
    }

    private void loginFailedAlert(){
        String msg = "Wrong username or password";
        String header = "Authentication Failed";
        ATMInterface.produceErrorAlert(msg, header);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ATMInterface.playMusic("welcome.wav");
        try{
            String date = "Today is " + ATM.format.format(ATM.getATM().date);
            Text dateText = new Text(date);
            dateText.setFont(Font.font("System", 13));
            dateDisplay.getChildren().add(dateText);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
