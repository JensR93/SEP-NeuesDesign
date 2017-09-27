package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DAO.auswahlklasse;
import sample.FXML.DashboardController;

import java.util.Locale;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{




        Parent root = FXMLLoader.load(getClass().getResource("FXML/Dashboard.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Badminton Master v1.0");
        scene.setFill(Color.rgb(216,216,216));
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
/*        Image icon = new Image(getClass().getResourceAsStream("Images/icon/Logo.ico"));
        primaryStage.getIcons().add(icon);*/

        primaryStage.setMinHeight(820);
        primaryStage.setMinWidth(1400);
        primaryStage.show();
        auswahlklasse.setPrimaryStage(primaryStage);

    }



    public static void main(String[] args) {
        launch(args);
    }

}
