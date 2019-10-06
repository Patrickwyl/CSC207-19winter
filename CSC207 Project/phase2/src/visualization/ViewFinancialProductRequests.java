package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import machine.Users;
import user.Employee;
import user.Manager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class ViewFinancialProductRequests implements Initializable {
    @FXML
    public ChoiceBox<Integer> requestNumberInput;

    @FXML
    public TextFlow requestsDisplay = new TextFlow();
    private List<String> requests;

    @FXML
    public void approveOnClick(ActionEvent actionEvent) {
        try{
        int requestNumber = requestNumberInput.getValue();
        String[] info = new String[0];
        info = Users.getUsers().getManager().approveProductRequest(requestNumber - 1);
        String name = info[0];
        String principle = info[1];
        String period = info[2];
        String type = info[3];
        ATMInterface.playMusic("confirmation.wav");
        String msg = "You have successfully approve " + name + "'s request (" + type + " with principle " + principle +
                " for " + period + " year(s)" ;
        ATMInterface.produceConfirmationAlert(msg);
        SwitchScene.getSwitchScene().switchScene("ViewFinancialProductRequests.fxml");}
        catch (NullPointerException e){
            ATMInterface.playMusic("oops.wav");
            ViewAccountRequests.invalidChoose();
        }
        catch(Exception e){
            ATMInterface.playMusic("oops.wav");
            String msg = e.getMessage();
            String header = "Add Financial Product Failed";
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    @FXML
    public void rejectOnClick(ActionEvent actionEvent) {
        try{
        int requestNumber = requestNumberInput.getValue();
        ATMInterface.playMusic("confirmation.wav");
        String[] info = Users.getUsers().getManager().rejectProductRequest(requestNumber - 1);
        String name = info[0];
        String msg = "You have rejected" + name + "'s request";
        ATMInterface.produceConfirmationAlert(msg);
        SwitchScene.getSwitchScene().switchScene("ViewFinancialProductRequests.fxml");
        } catch (Exception e){
            ATMInterface.playMusic("oops.wav");
            ViewAccountRequests.invalidChoose();
        }
    }

    @FXML
    public void backOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        if (ATMInterface.user instanceof Manager) {
            SwitchScene.getSwitchScene().switchScene("Manager.fxml");
        } else {
            SwitchScene.getSwitchScene().switchScene("FinancialAdviserMenu.fxml");
        }
    }

    private void loadRequests(){
        for (int i = 1; i <= requests.size(); i++){
            requestNumberInput.getItems().add(i);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requests = Employee.productRequests;
        loadRequests();
        if (requests.size() > 0){
        displayRequests();}
    }

    private void displayRequests(){
        StringBuilder request = new StringBuilder();
        for (int i = 0; i < requests.size(); i++) {
            String[] info = requests.get(i).split(",");
            String name = info[0];
            String principle = info[1];
            String period = info[2];
            String type = info[3];
            request.append(i + 1).append(".").append(name).append(" requested a ").append(type).append(" with principle ").append(principle).append(" for ").append(period).append(" year(s)\n\n");
        }
        Text requestText = new Text(request.toString());
        requestText.setFont(Font.font("System", 13));
        requestsDisplay.getChildren().add(requestText);
    }
}
