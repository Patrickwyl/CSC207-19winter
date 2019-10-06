package visualization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import user.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerInfoDisplay implements Initializable {
    @FXML
    public TextFlow information = new TextFlow();
    @FXML
    public TextFlow productInfo = new TextFlow();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Customer user = (Customer) ATMInterface.user;
        Text info = new Text(user.userInfo());
        Text products = new Text(user.getProductInfo());
        information.getChildren().add(info);
        productInfo.getChildren().add(products);
    }
    public void back(ActionEvent actionEvent) {
        ATMInterface.playMusic("back.wav");
        SwitchScene.getSwitchScene().backToMenu();
    }
}
