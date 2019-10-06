package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

@SuppressWarnings("ALL")
public class ChangePassword {
    @FXML
    public PasswordField oldPasswordInput;

    @SuppressWarnings("WeakerAccess")
    @FXML
    public PasswordField newPasswordInput;

    @FXML
    public PasswordField newPasswordAgainInput;

    private void incorrectPasswordAlert(){
        String msg = "Your old password is incorrect";
        String header = "Invalid Password";
        ATMInterface.produceErrorAlert(msg, header);
    }

    private void passwordNotMatchAlert(){
        String msg = "Password does not match";
        String header = "Invalid Password";
        ATMInterface.produceErrorAlert(msg, header);
    }

    private void passwordNotStrongAlert(){
        String msg = "Your password is not strong enough. A strong password should exceed 6 digits and include " +
                "at least an uppercase character, a lowercase character and an integer";
        String header = "Password not strong";
        ATMInterface.produceInformationAlert(msg, header);
    }

    @FXML
    public void confirmOnClick(ActionEvent actionEvent) {
        String oldPassword = oldPasswordInput.getText();
        String newPassword = newPasswordInput.getText();
        String newPasswordAgain = newPasswordAgainInput.getText();
        // if old password do not match
        if (!ATMInterface.user.password.equals(oldPassword)){
            ATMInterface.playMusic("oops.wav");
            incorrectPasswordAlert();
            SwitchScene.getSwitchScene().switchScene("ChangePassword.fxml");
        // if the user type different new password
        } else if (!newPassword.equals(newPasswordAgain)){
            ATMInterface.playMusic("oops.wav");
            passwordNotMatchAlert();
            SwitchScene.getSwitchScene().switchScene("ChangePassword.fxml");
        }else{
            try {
                ATMInterface.user.changePassword(newPassword);
                ATMInterface.playMusic("confirmation.wav");
                String msg = "You have successfully set a new password";
                ATMInterface.produceConfirmationAlert(msg);
                SwitchScene.getSwitchScene().backToMenu();
            } catch (Exception e) {
                // password is not strong enough
                ATMInterface.playMusic("oops.wav");
                passwordNotStrongAlert();
                SwitchScene.getSwitchScene().switchScene("ChangePassword.fxml");
            }
        }

    }

    @FXML
    public void backOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
