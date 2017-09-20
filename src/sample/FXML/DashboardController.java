/**
 * Sample Skeleton for 'Dashboard.fxml' Controller Class
 */

package sample.FXML;


import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
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
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sample.DAO.auswahlklasse;

public class DashboardController implements Initializable{

    private JFXTabPane Spieler,Klassehinzufuegen,Spielsystem;
    private GridPane Einstellungen,NeuesTurnier,Spieler_vorhanden;
    private GridPane Klassenuebersicht,Zeitplan;
    private StackPane Turnier,home;

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

    @FXML
    private JFXButton btnSpieluebersicht;

    @FXML
    private JFXButton btnTurnierbaum;

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

    public void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("Turnier_laden.fxml"));
            Spieler = FXMLLoader.load(getClass().getResource("Spieler_hinzufuegen.fxml"));
            Zeitplan = FXMLLoader.load(getClass().getResource("Zeitplan.fxml"));

            NeuesTurnier =  FXMLLoader.load(getClass().getResource("NeuesTurnier.fxml"));

            Klassehinzufuegen =  FXMLLoader.load(getClass().getResource("Klasse_hinzufügen.fxml"));






            Einstellungen =  FXMLLoader.load(getClass().getResource("Einstellungen.fxml"));


            Klassenuebersicht =  FXMLLoader.load(getClass().getResource("Klassenuebersicht.fxml"));


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void meldeformularImport()
    {
        setNodeSpieler();
        if(auswahlklasse.getSpielererfolgreich().size()>0) {
            String s ="";
            Enumeration e = auswahlklasse.getSpielererfolgreich().keys();
            while(e.hasMoreElements())
            {
                s+=e.nextElement().toString();
                if(e.hasMoreElements())
                {
                    s+=" --- ";
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spielerimport - Neue Spieler");
            alert.setHeaderText("Spieler erfolgreich eingelesen! ");
            alert.setContentText(s);
            alert.showAndWait();
            //ExcelImport.setSpielererfolgreich(null);
        }

        if(auswahlklasse.getSpielerupdate().size()>0) {
            Enumeration eu = auswahlklasse.getSpielerupdate().keys();
            String s ="";
            while(eu.hasMoreElements())
            {
                s+=eu.nextElement().toString();
                if(eu.hasMoreElements())
                {
                    s+=" --- ";
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spielerimport - Update");
            alert.setHeaderText("Spieler erfolgreich aktualisiert! ");
            alert.setContentText(String.valueOf(s));
            alert.showAndWait();
            //ExcelImport.setSpielerupdate(null);
        }
        if(auswahlklasse.getObs_vereine_erfolgreich().size()>0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vereinimport - Neue Vereine");
            alert.setHeaderText("Vereine erfolgreich hinzugefügt ");
            alert.setContentText(String.valueOf(auswahlklasse.getObs_vereine_erfolgreich()));
            alert.showAndWait();
            //ExcelImport.getObs_vereine_erfolgreich().clear();
        }
    }

    @FXML public void setNodeSpieler(ActionEvent event)
    {
        setNode(Spieler);
    }
    public void setNodeSpieler()
    {
        setNode(Spieler);
    }
    @FXML public void setNodeEinstellungen(ActionEvent event)
    {
        setNode(Einstellungen);
    }
    @FXML public void setNodeZeitplan(ActionEvent event)
    {
        setNode(Zeitplan);
    }
    public void setNodeEinstellungen()
    {
        setNode(Einstellungen);
    }
    public void setNodeKlassehinzufuegen()
    {
        setNode(Klassehinzufuegen);
    }
    @FXML public void setNodeTurnier(ActionEvent event)
    {
        setNode(Turnier);
    }
    public void setNodeTurnier()
    {
        setNode(Turnier);
    }
    @FXML public void setNodeHome(ActionEvent event){
        setNode(home);
    }
    @FXML public void setNodeKlassenuebersicht(ActionEvent event)
    {
        setNode(Klassenuebersicht);
    }
    public void setNodeKlassenuebersicht()
    {
        setNode(Klassenuebersicht);
    }
    public void setNodeSpielervorhanden()
    {

        try {
            if(Spieler_vorhanden==null)
            {
                Spieler_vorhanden =  FXMLLoader.load(getClass().getResource("Spieler_vorhanden.fxml"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        setNode(Spieler_vorhanden);
    }
    public void setNodeSpielsystem()
    {
        try {
            Spielsystem =  FXMLLoader.load(getClass().getResource("SpielSystem.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setNode(Spielsystem);
    }
    public void setNodeNeuesTurnier()
    {
        try {
            NeuesTurnier =  FXMLLoader.load(getClass().getResource("NeuesTurnier.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setNode(NeuesTurnier);
    }
    private static void test(){

    }

    private static void buttonHighlight(JFXButton button)
    {

    }

    private void setNode(Node node)
    {
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void allesFreigeben(){
        btnKlassen.setDisable(false);
        btnSpieler.setDisable(false);
        btnHome.setDisable(false);
        btnStatistik.setDisable(false);
        btnzeitplan.setDisable(false);
        btnTurnierbaum.setDisable(false);
        btnSpieluebersicht.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        auswahlklasse.setDashboardController(this);
        System.out.println(auswahlklasse.getDashboardController());
        createTurnierLadenPage();
        setNodeTurnier();
    }

    private void createTurnierLadenPage() {
        try {
            FXMLLoader fxmlLoaderTurnier = new FXMLLoader(getClass().getResource("Turnier_laden.fxml"));
            Turnier=fxmlLoaderTurnier.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
