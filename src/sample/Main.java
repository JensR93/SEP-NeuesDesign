package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/Dashboard.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1360);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
