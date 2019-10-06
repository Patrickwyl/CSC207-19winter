package visualization;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import user.*;

import java.io.IOException;

class SwitchScene {
    private static SwitchScene switchScene;

    private SwitchScene() {
    }

    static SwitchScene getSwitchScene(){
        if (switchScene == null){
            switchScene = new SwitchScene();
        }
        return switchScene;
    }

    void switchScene(String fxmlFile){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            Scene scene = new Scene(loader.load(), 600, 400);
            scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            ATMInterface.window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void backToMenu(){
        if (ATMInterface.user instanceof Manager) {
            SwitchScene.getSwitchScene().switchScene("Manager.fxml");
        } else if (ATMInterface.user instanceof FinancialAdviser.FinancialUser){
            SwitchScene.getSwitchScene().switchScene("FinancialAdviserMenu.fxml");
        } else {
            SwitchScene.getSwitchScene().switchScene("CustomerMenu.fxml");
        }
    }
}
