package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import machine.Users;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class ViewAccountRequests implements Initializable {
    @FXML
    public TextFlow requestsDisplay = new TextFlow();
    @FXML
    public ChoiceBox<Integer> requestNumberInput;
    private List<String> requests;

    static void invalidChoose(){
        String msg = "Please select a request number";
        String header = "Invalid Input";
        ATMInterface.produceErrorAlert(msg, header);
    }

    @FXML
    public void acceptOnClick(ActionEvent actionEvent) {
        try{
        int requestNumber = requestNumberInput.getValue();
        String msg;
        String[] info = Users.getUsers().getManager().approveAccountRequest(requestNumber - 1);
        String name = info[0];
        String type = info[1];
        ATMInterface.playMusic("confirmation.wav");
        msg = "You have successfully add " + type + " account to " + name;
        ATMInterface.produceConfirmationAlert(msg);
        SwitchScene.getSwitchScene().switchScene("ViewAccountRequests.fxml");
        } catch(Exception e) {
            ATMInterface.playMusic("oops.wav");
            invalidChoose();
        }
    }

    @FXML
    public void rejectOnClick(ActionEvent actionEvent) {
        try {
            int requestNumber = requestNumberInput.getValue();
            ATMInterface.playMusic("confirmation.wav");
            String name = Users.getUsers().getManager().rejectAccountRequest(requestNumber - 1);
            String msg = "You have rejected " + name + "'s request";
            ATMInterface.produceConfirmationAlert(msg);
            SwitchScene.getSwitchScene().switchScene("ViewAccountRequests.fxml");
        } catch (Exception e) {
            ATMInterface.playMusic("oops.wav");
            invalidChoose();
        }
    }

    @FXML
    public void backOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().switchScene("Manager.fxml");
    }

    private void loadRequests(){
        for (int i = 1; i <= requests.size(); i++){
            requestNumberInput.getItems().add(i);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requests = Users.getUsers().getManager().requests;
        loadRequests();
        if (requests.size() > 0){
        displayRequests();}
    }

    private void displayRequests(){
        StringBuilder request = new StringBuilder();
        for (int i = 0; i < requests.size(); i++) {
            String[] info = requests.get(i).split(",");
            String name = info[0];
            String type = info[1];
            if (type.equals("jointChequing")){
                String secondOwner = info[2];
                request.append(i + 1).append(".").append(name).append(" and ").append(secondOwner).append(" requests a ").append(type).append(" account.\n\n");
            }else{
                request.append(i + 1).append(". ").append(name).append(" requests a ").append(type).append(" account.\n\n");}}
        Text requestText = new Text(request.toString());
        requestText.setFont(Font.font("System", 13));
        requestsDisplay.getChildren().add(requestText);
    }

}
