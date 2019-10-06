package visualization;

import financialProducts.FinancialProductType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import user.Customer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ApplyProduct implements Initializable {
    private Customer user;
    @FXML
    public ChoiceBox<String> productType = new ChoiceBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (Customer)ATMInterface.user;
        setProductType();
    }

    private void setProductType(){
        for (FinancialProductType type: FinancialProductType.values()){
            productType.getItems().add(type.getName());
        }
    }

    @FXML
    public void applyProduct(ActionEvent actionEvent) {
        FinancialProductType type = FinancialProductType.getType(productType.getValue());
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField principle = new TextField();
        TextField period = new TextField();
        grid.add(principle, 1, 0);
        grid.add(period, 1, 1);
        grid.add(new Label("year(s)"), 2, 1);
        ButtonType loginButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
        dialog.getDialogPane().setContent(grid);
        try{
            if (type == FinancialProductType.bond) {
                dialog.setTitle("Apply for a bond");
                dialog.setHeaderText("Please enter the period and face value of the bond.");
                principle.setPromptText("face value");
                period.setPromptText("period");
                grid.add(new Label("Please enter the face value:"), 0, 0);
                grid.add(new Label("00"), 2, 0);
                grid.add(new Label("Please enter the period:"), 0, 1);
                Optional<Pair<String, String>> result = dialog.showAndWait();
                if (result.isPresent()){
                    user.requestFinancialProduct(type.name(), Integer.valueOf(principle.getText())*100,
                            Integer.valueOf(period.getText()));
                } else {
                    return;
                }
                ATMInterface.playMusic("confirmation.wav");
                String msg = "Successfully applied for a " + type.getName() + ".";
                ATMInterface.produceConfirmationAlert(msg);
                SwitchScene.getSwitchScene().backToMenu();
            } else if (type == FinancialProductType.gic) {
                dialog.setTitle("Apply for a GIC");
                dialog.setHeaderText("Please enter the period and amount of the GIC.");
                principle.setPromptText("GIC amount");
                period.setPromptText("period");
                grid.add(new Label("Please enter the GIC amount:"), 0, 0);
                grid.add(new Label("00"), 2, 0);
                grid.add(new Label("Please enter the period:"), 0, 1);
                Optional<Pair<String, String>> result = dialog.showAndWait();
                if (result.isPresent()){
                    user.requestFinancialProduct(type.name(), Integer.valueOf(principle.getText())*100,
                            Integer.valueOf(period.getText()));
                } else {
                    return;
                }
                ATMInterface.playMusic("confirmation.wav");
                String msg = "Successfully applied for a " + type.getName() + ".";
                ATMInterface.produceConfirmationAlert(msg);
                SwitchScene.getSwitchScene().backToMenu();
            } else {
                dialog.setTitle("Apply for a loan");
                dialog.setHeaderText("Please enter the period and amount of the loan.");
                principle.setPromptText("loan amount");
                period.setPromptText("period");
                grid.add(new Label("Please enter the loan amount:"), 0, 0);
                grid.add(new Label("000"), 2, 0);
                grid.add(new Label("Please enter the period:"), 0, 1);
                Optional<Pair<String, String>> result = dialog.showAndWait();
                if (result.isPresent()){
                    user.requestFinancialProduct(type.name(), Integer.valueOf(principle.getText())*1000,
                            Integer.valueOf(period.getText()));
                } else {
                    return;
                }
                ATMInterface.playMusic("confirmation.wav");
                String msg = "Successfully applied for a " + type.getName() + ".";
                ATMInterface.produceConfirmationAlert(msg);
                SwitchScene.getSwitchScene().backToMenu();
            }
        } catch (Exception e) {
            ATMInterface.playMusic("oops.wav");
            String msg = "Please try again.";
            String header = "Request failed";
            ATMInterface.produceErrorAlert(msg, header);
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
