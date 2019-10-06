package visualization;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import machine.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user.Person;

import java.io.File;
import java.io.IOException;


public class ATMInterface extends Application {

    static Stage window;

    static Person user;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Loading.fxml"));
        try {
            // load fxml window
            Scene loginScene = new Scene(loginLoader.load(), 600, 400);
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("ATM");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Simulator.getSimulator().setSystemDate();
        } catch (Exception e) {
            SwitchScene.getSwitchScene().switchScene("InitialInterface.fxml");
            return;
        }
        Simulator.getSimulator().initialize();
        SwitchScene.getSwitchScene().switchScene("Login.fxml");
        primaryStage.setResizable(false);
    }

    static void invalidNumberAlert(){
        String msg = "Please enter a valid number";
        String header = "Invalid Input";
        produceErrorAlert(msg, header);
    }

    static void produceErrorAlert(String text, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.setHeaderText(header);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonCancel);
        alert.showAndWait();
    }

    static void produceConfirmationAlert(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(text);
        alert.setHeaderText("Confirmation");
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonOK);
        alert.showAndWait();
    }

    static void produceInformationAlert(String text, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.setHeaderText(header);
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonOK);
        alert.showAndWait();
    }

    static void playMusic(String musicName){
        String musicFile = "phase2/src/visualization/music/" + musicName;
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
