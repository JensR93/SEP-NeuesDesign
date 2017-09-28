/**
 * Sample Skeleton for 'Dashboard.fxml' Controller Class
 */

package sample.FXML;


import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.DAO.auswahlklasse;
import sample.Spiel;

public class DashboardController implements Initializable{

    String baseName = "resources.Main";
    String titel ="";

    private JFXTabPane Spieler,Spielsystem,Visualisierung,Vereinsuebersicht;
    private GridPane Einstellungen,NeuesTurnier,Spieler_vorhanden,Klassenuebersicht,Zeitplan,SpielErgebnisEintragen,NeuerVerein,Spielereigenschaften;

    private GridPane Turnier,home;
    private VBox Spieluebersicht;
    @FXML
    void Vereinsuebersichtauswahl(ActionEvent event) {

    }
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="stackPane"
    private GridPane stackPane; // Value injected by FXMLLoader

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
    @FXML
    private JFXButton btnVerein;
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

    @FXML
    private Text t_Btv;

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
    void teamsoeffnen(ActionEvent event) {

    }

    @FXML
    void turnierübersichtoeffnen(ActionEvent event) {

    }

    @FXML
    void zeitplanoeffnen(ActionEvent event) {

    }

    public void SpracheLaden()
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btnTurnierübersicht");
            btnTurnierübersicht.setText(titel);

            titel = bundle.getString("btnKlassen");
            btnKlassen.setText(titel);

            titel = bundle.getString("btnSpieler");
            btnSpieler.setText(titel);

            titel = bundle.getString("btnTurnierbaum");
            btnTurnierbaum.setText(titel);

            titel = bundle.getString("btnSpieluebersicht");
            btnSpieluebersicht.setText(titel);

            titel = bundle.getString("btnzeitplan");
            btnzeitplan.setText(titel);

            titel = bundle.getString("btnEinstellungen");
            btnEinstellungen.setText(titel);

            titel = bundle.getString("btnBeenden");
            btnBeenden.setText(titel);

            titel = bundle.getString("t_Btv");
            t_Btv.setText(titel);

            titel = bundle.getString("btnVerein");
            btnVerein.setText(titel);



        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
    }

    public void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("Turnier_laden.fxml"));
            Zeitplan = FXMLLoader.load(getClass().getResource("Zeitplan.fxml"));
            NeuesTurnier =  FXMLLoader.load(getClass().getResource("NeuesTurnier.fxml"));
            Visualisierung = FXMLLoader.load(getClass().getResource("Visualisierung.fxml"));
            Spieluebersicht=FXMLLoader.load(getClass().getResource("Spieluebersicht.fxml"));
            SpielErgebnisEintragen =FXMLLoader.load(getClass().getResource("SpielerErgebnisEintragen.fxml"));
            Klassenuebersicht =  FXMLLoader.load(getClass().getResource("Klasse.fxml"));
            Vereinsuebersicht = FXMLLoader.load(getClass().getResource("Vereinsuebersicht.fxml"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void meldeformularImport()
    {
        //Listen löschen nicht vollständig
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


    public void setNodeNeuerVerein()
    {
        try {
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("Neuer_Verein.fxml"));
            NeuerVerein = fxmlLoader.load();



        } catch (IOException e) {
            e.printStackTrace();
        }
        setNode(NeuerVerein);
    }

     public void setNodeSpieluebersicht()
    {
        try {
            Spieluebersicht=FXMLLoader.load(getClass().getResource("Spieluebersicht.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setNode(Spieluebersicht);
    }
    @FXML public void setNodeSpieluebersicht(ActionEvent event)
    {

        setNode(Spieluebersicht);
    }
    @FXML public void setNodeSpieler(ActionEvent event)
    {
        setNode(Spieler);
        if(auswahlklasse.getSpieler_hinzufuegenController()!=null){
            auswahlklasse.getSpieler_hinzufuegenController().setzeBearbeitenNachVorne();
        }

    }
    public void setNodeSpieler()
    {
        setNode(Spieler);
    }
    public void setNodeSpielergebnis()
    {
        auswahlklasse.getSpielErgebnisEintragenController().setSp(auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielID()));
        auswahlklasse.getSpielErgebnisEintragenController().allesZuruecksetzen();
        setNode(SpielErgebnisEintragen);
    }

    @FXML public void setNodeVereinsuebersicht(ActionEvent event)
    {
        try {
            if(auswahlklasse.getVereinsuebersichtController()!=null)
            {
                auswahlklasse.getVereinsuebersichtController().fulleObsVereine();
                auswahlklasse.getVereinsuebersichtController().tab1Auswahl();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        setNode(Vereinsuebersicht);
    }
  public void setNodeVereinsuebersicht()
    {
        try {
            if(auswahlklasse.getVereinsuebersichtController()!=null)
            {
                auswahlklasse.getVereinsuebersichtController().fulleObsVereine();
                auswahlklasse.getVereinsuebersichtController().tab1Auswahl();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        setNode(Vereinsuebersicht);
    }
    @FXML public void setNodeEinstellungen(ActionEvent event)
    {
        setNode(Einstellungen);
    }
    @FXML public void setNodeVisualisierung(ActionEvent event)
    {
        setNode(Visualisierung);
    }
    public void setNodeVisualisierung() {
        setNode(Visualisierung);
    }
    @FXML public void setNodeZeitplan(ActionEvent event)
    {
        setNode(Zeitplan);
    }
    public void setNodeEinstellungen()
    {
        setNode(Einstellungen);
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
    public void setNodeSpielerEigenschaften() {
        try {
            Spielereigenschaften= FXMLLoader.load(getClass().getResource("SpielerEigenschaften.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setNode(Spielereigenschaften);
    }
    public void setNodeSpielervorhanden()
    {

        try {
            if(Spieler_vorhanden==null)
            {
                Spieler_vorhanden =  FXMLLoader.load(getClass().getResource("Spieler_vorhanden.fxml"));
            }
            else
            {
                auswahlklasse.setSpielererfolgreich(new Hashtable<>());
                auswahlklasse.setSpielerupdate(new Hashtable<>());
                auswahlklasse.getObs_vereine_erfolgreich().clear();
               // auswahlklasse.getSpieler_vorhandenController().resetteDaten();
                System.out.println(auswahlklasse.getSpielererfolgreich());
                auswahlklasse.getSpieler_vorhandenController().Tabellefuelle();
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
       FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void allesFreigeben(){
        btnKlassen.setDisable(false);
        btnHome.setDisable(false);
        btnzeitplan.setDisable(false);
        btnSpieluebersicht.setDisable(false);
        turnierbaumFreigeben();
    }

    public void turnierbaumFreigeben(){
        for(int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size();i++){
            if(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(i).getSpielsystem()!=null){
                btnTurnierbaum.setDisable(false);
                return;
            }
        }
        btnTurnierbaum.setDisable(true);
    }

    public void turnierbaumAktivieren(){
        btnTurnierbaum.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpracheLaden();
        auswahlklasse.setDashboardController(this);
        System.out.println(auswahlklasse.getDashboardController());
        createTurnierLadenPage();
        setNodeTurnier();
    }

    private void createTurnierLadenPage() {
        try {
            Einstellungen =  FXMLLoader.load(getClass().getResource("Einstellungen.fxml"));
            FXMLLoader fxmlLoaderTurnier = new FXMLLoader(getClass().getResource("Turnier_laden.fxml"));
            Turnier = fxmlLoaderTurnier.load();
            Spieler = FXMLLoader.load(getClass().getResource("Spieler_hinzufuegen.fxml"));
            NeuerVerein = FXMLLoader.load(getClass().getResource("Neuer_Verein.fxml"));

            Spiel.SpracheLaden();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
