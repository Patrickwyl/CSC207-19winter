package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import machine.ATM;
import machine.Simulator;
import manageFile.WriteFile;
import user.Person;

import java.time.ZoneId;
import java.util.Date;

@SuppressWarnings("ALL")
public class InitialInterface{
    @FXML
    public DatePicker dateInput;

    @FXML
    public TextField usernameInput;

    @FXML
    public PasswordField passwordInput;

    @FXML
    public void RegisterOnClick(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        try {
            ATM.getATM().date = Date.from(dateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            String msg = "Please select a date.";
            String header = "Initialize failed";
            ATMInterface.playMusic("oops.wav");
            ATMInterface.produceErrorAlert(msg, header);
            return;
        }
        if (username.equals("") || !Person.IsValidPassword(password)) {
            ATMInterface.playMusic("oops.wav");
            String msg = "Please enter a valid username and password. \n" +
                    "Your password need to be at least 6 digits long and contain a digit, a lowercase letter, " +
                    "and an upper case letter.";
            String header = "Initialize failed";
            ATMInterface.produceErrorAlert(msg, header);
            return;
        }
        WriteFile.write(ATM.format.format(ATM.getATM().date), "ATM/time.txt");
        Simulator.getSimulator().constructManager(username, password);
        Simulator.getSimulator().initialize();
        SwitchScene.getSwitchScene().switchScene("Login.fxml");
    }

}
