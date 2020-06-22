package Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Terre extends Application {
    @Override
    public void start(Stage primaryStage) {
        try{
            Parent content = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("Modélisation des changements de température");
            primaryStage.setScene(new Scene(content));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
