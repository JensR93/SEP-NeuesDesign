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
/*        File f = new File("Einstellungen.xml");
        if(f.exists() && !f.isDirectory()) {
            Properties loadProps = new Properties();
            try {
                loadProps.loadFromXML(new FileInputStream("Einstellungen.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }


            String  Sprache = loadProps.getProperty("Sprache");
            if(Sprache.equals("de"))
            {
                Locale.setDefault( new Locale("de", "DE") );
            }
            if(Sprache.equals("en"))
            {
                Locale.setDefault( new Locale("en", "UK") );
            }
            if(Sprache.equals("es"))
            {
                Locale.setDefault( new Locale("es", "ES") );
            }


        }*/

        launch(args);
    }

    /*public void prgHelp() {
        JHelp helpViewer = null;
        try {
            //Hauptfenster in der nächsten Zeile ersetzen durch aktuellen Klassennamen
            ClassLoader cl = Main.class.getClassLoader();
            URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
            helpViewer = new JHelp(new HelpSet(cl, url));
            // Darzustellendes Kapitel festlegen, ID muss im XML existieren!
            helpViewer.setCurrentID("Simple.Introduction");
        } catch (Exception e) {
            System.err.println("Help-Datei nicht gefunden.");
        }

        JFrame frame = new JFrame();
        frame.setTitle("Infos zur Turnierverwaltung");
        frame.setSize(800, 600);
        frame.getContentPane().add(helpViewer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }*/

}
