package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import user.FinancialAdviser;

public class FinancialAdviserMenu extends CustomerMenu{

    static FinancialAdviser employee;

    @FXML
    public void viewFPRequestsOnClick(ActionEvent actionEvent) {
        ATMInterface.playMusic("click.wav");
        SwitchScene.getSwitchScene().switchScene("ViewFinancialProductRequests.fxml");
    }
}
