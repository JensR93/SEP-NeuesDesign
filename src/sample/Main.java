package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DAO.SQLConnection;
import sample.DAO.auswahlklasse;
import sample.FXML.DashboardController;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.net.URL;
import javax.help.HelpSet;
import javax.help.JHelp;
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{


        if(SQLConnection.SQLConnection()) {

            Parent root = FXMLLoader.load(getClass().getResource("FXML/Dashboard.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Badminton Master v1.0");
            scene.setFill(Color.rgb(216, 216, 216));
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
/*        Image icon = new Image(getClass().getResourceAsStream("Images/icon/Logo.ico"));
        primaryStage.getIcons().add(icon);*/


            primaryStage.setMinHeight(820);
            primaryStage.setMinWidth(1400);
            primaryStage.show();
            auswahlklasse.setPrimaryStage(primaryStage);
        }

    }



    public static void main(String[] args) {

        launch(args);
    }

}
