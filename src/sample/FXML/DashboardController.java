/**
 * Sample Skeleton for 'Dashboard.fxml' Controller Class
 */

package sample.FXML;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToolbar;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController implements Initializable{

    private AnchorPane home, add;
    private JFXTabPane Spieler;
    private GridPane Turnier,Einstellungen;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="stackPane"
    private StackPane stackPane; // Value injected by FXMLLoader

    @FXML // fx:id="toolBar"
    private JFXToolbar toolBar; // Value injected by FXMLLoader

    @FXML // fx:id="toolBarRight"
    private HBox toolBarRight; // Value injected by FXMLLoader

    @FXML // fx:id="sideAnchor"
    private AnchorPane sideAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="btnHome"
    private JFXButton btnHome; // Value injected by FXMLLoader

    @FXML // fx:id="btnTurnierübersicht"
    private JFXButton btnTurnierübersicht; // Value injected by FXMLLoader

    @FXML // fx:id="btnKlassen"
    private JFXButton btnKlassen; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpieler"
    private JFXButton btnSpieler; // Value injected by FXMLLoader

    @FXML // fx:id="btnTeams"
    private JFXButton btnTeams; // Value injected by FXMLLoader

    @FXML // fx:id="btnzeitplan"
    private JFXButton btnzeitplan; // Value injected by FXMLLoader

    @FXML // fx:id="btnStatistik"
    private JFXButton btnStatistik; // Value injected by FXMLLoader

    @FXML // fx:id="btnEinstellungen"
    private JFXButton btnEinstellungen; // Value injected by FXMLLoader

    @FXML // fx:id="btnBeenden"
    private JFXButton btnBeenden; // Value injected by FXMLLoader

    @FXML // fx:id="holderPane"
    private StackPane holderPane; // Value injected by FXMLLoader

    @FXML
    void Beenden(ActionEvent event)
    {
        Platform.exit();
    }

    @FXML
    void einstellungenoeffnen(ActionEvent event) {

    }

    @FXML
    void homeoeffnen(ActionEvent event) {

    }

    @FXML
    void klassenoeffnen(ActionEvent event) {

    }

    @FXML
    void spieleroeffnen(ActionEvent event) {

    }

    @FXML
    void statistikoeffnen(ActionEvent event) {

    }

    @FXML
    void teamsoeffnen(ActionEvent event) {

    }

    @FXML
    void turnierübersichtoeffnen(ActionEvent event) {

    }

    @FXML
    void zeitplanoeffnen(ActionEvent event) {

    }

    private void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("test.fxml"));
            Spieler = FXMLLoader.load(getClass().getResource("Spieler_hinzufuegen.fxml"));
            add = FXMLLoader.load(getClass().getResource("test.fxml"));
            Turnier = FXMLLoader.load(getClass().getResource("Turnier_laden.fxml"));
            Einstellungen = FXMLLoader.load(getClass().getResource("Einstellungen.fxml"));
            //set up default node on page load
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML public void setNodeSpieler(ActionEvent event)
    {
        setNode(Spieler);
    }
    @FXML public void setNodeEinstellungen(ActionEvent event)
    {
        setNode(Einstellungen);
    }
    @FXML public void setNodeTurnier(ActionEvent event)
    {
        setNode(Turnier);
    }

    private static void test(){

    }

    private void setNode(Node node)
    {


        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(3000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createPages();


    }
}
