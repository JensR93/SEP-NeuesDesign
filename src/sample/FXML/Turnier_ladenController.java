package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.DAO.*;
import sample.Main;
import sample.Spiel;
import sample.Team;
import sample.Turnier;

import javax.naming.ldap.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.DAO.auswahlklasse.getTurnierzumupdaten;

/**
 * Created by jens on 17.09.2017.
 */
public class Turnier_ladenController extends Application implements Initializable, ControlledScreen {
    String baseName = "resources.Main";
    String titel ="";
    DashboardController dashboardController = new DashboardController();
    @FXML
    private JFXTextField t_turniersuche;
    ContextMenu contextMenu=new ContextMenu();
    @FXML
    public TableView TurnierlisteTabelle;
    @FXML
    public TableColumn TurnierDatumSpalte;
    @FXML
    public TableColumn TurnierNameSpalte;
    @FXML
    public TableColumn TurnierIDSpalte;
    ObservableList<Turnier> turniere = FXCollections.observableArrayList();
    @FXML
    private StackPane holderPane;
    @FXML
    private JFXButton btn_neuesTurnier;
    private JFXTabPane NeuerSpieler;
    private StackPane TurnierLaden;
    TurnierDAO t = new TurnierDAOimpl();
    Dictionary<Integer,Turnier> turnierliste = t.getAllTurniere();
    private static Stage primaryStage;
    public Turnier_ladenController()
    {
        System.out.println();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("t");
    }

    public Turnier_ladenController(String refresh)
    {
        primaryStage.setTitle(refresh);
    }



    private void createPages() {
        try {

            NeuerSpieler = FXMLLoader.load(getClass().getResource("Spieler_hinzufuegen.fxml"));

            //set up default node on page load
            //setNode(NeuerSpieler);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    @FXML
    private void zeigeTabelle() {
        //System.out.println("Print table");



        Enumeration enumKeys = auswahlklasse.getTurnierliste().keys();

        while(enumKeys.hasMoreElements()){
            int key = (int) enumKeys.nextElement();
            //index = 0-->Altes Turnier oben!
            turniere.add(0,auswahlklasse.getTurnierliste().get(key));

        }
        TurnierlisteTabelle.setItems(turniere);
        TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Integer>("turnierid"));

        //TurnierIDSpalte.setCellFactory(integerCellFactory);
        TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,Date>("datum"));
        TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier,String>("name"));

        //TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Date>("datum"));
        //TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, String>("name"));

        //TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Integer>("turnierid"));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

            createPages();

            Properties saveProps = new Properties();
            saveProps.setProperty("path1", "/somethingpath1");
            saveProps.setProperty("path2", "/somethingpath2");
            try {
                saveProps.storeToXML(new FileOutputStream("settings.xml"), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Load Settings
            Properties loadProps = new Properties();
            try {
                loadProps.loadFromXML(new FileInputStream("settings.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path1 = loadProps.getProperty("path1");
            String path2 = loadProps.getProperty("path2");
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                titel = bundle.getString("btn_neuesTurnier");
            } catch (MissingResourceException e) {
                System.err.println(e);
            }
            btn_neuesTurnier.setText(titel);

            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                titel = bundle.getString("t_turniersuche");
            } catch (MissingResourceException e) {
                System.err.println(e);
            }
            t_turniersuche.setText(titel);

            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                titel = bundle.getString("TurnierDatumSpalte");
            } catch (MissingResourceException e) {
                System.err.println(e);
            }
            TurnierDatumSpalte.setText(titel);

            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                titel = bundle.getString("TurnierNameSpalte");
            } catch (MissingResourceException e) {
                System.err.println(e);
            }
            TurnierNameSpalte.setText(titel);

            auswahlklasse a = new auswahlklasse();
            a.readTurnierListe();
            if (auswahlklasse.getAktuelleTurnierAuswahl() != null) {
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().clear();
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().clear();
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_gespielteSpiele().clear();
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_zukuenftigeSpiele().clear();
            }
            zeigeTabelle();

            TurnierlisteTabelle.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                            && event.getClickCount() == 2) {
                        Turnier clickedRow = (Turnier) row.getItem();
                        auswahlSpeichern(clickedRow);
                        //   a.getStagesdict().get("")
                    }
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        contextMenu.hide();
                    } else if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                        Turnier clickedRow = (Turnier) row.getItem();

                        //System.out.println("R-KLICK");
                        MenuItem item1 = new MenuItem("Turnier auswählen");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                Turnier clickedRow = (Turnier) row.getItem();
                                auswahlSpeichern(clickedRow);

                                //tabpane_spieler.getSelectionModel().select(tab_sphin);
                            }
                        });
                        MenuItem item2 = new MenuItem("Turnier bearbeiten");
                        item2.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                auswahlklasse.setTurnierzumupdaten(clickedRow);
                                try {
                                    pressBtn_neuesTurnier(event);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        MenuItem item3 = new MenuItem("Turnier löschen");
                        item3.setOnAction(new EventHandler<ActionEvent>() {


                            @Override
                            public void handle(ActionEvent event) {
                                auswahlklasse.setTurnierzumupdaten(clickedRow);
                                //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                // t.readFelder_Neu(auswahlklasse.getTurnierzumupdaten());
                                t.read(getTurnierzumupdaten());
                                int anzahlturnierfelderalt = getTurnierzumupdaten().getFelder().size();
                                boolean erfolg = false;
                                FeldDAO feldDAO = new FeldDAOimpl();
                                for (int i = 0; i < anzahlturnierfelderalt; i++) {

                                    //System.out.println("Lösche Feld");
                                    erfolg = feldDAO.deleteFeld(getTurnierzumupdaten().getFelder().get(i));

                                    if (!erfolg)
                                        break;
                                }
                                if (!erfolg)
                                    auswahlklasse.WarnungBenachrichtigung("fehler", "fehler");


                                //Spielklasse löschen
                                int anzahlspielklassen = getTurnierzumupdaten().getSpielklassen().size();
                                erfolg = false;
                                SpielklasseDAO spielklasseDAO = new SpielklasseDAOimpl();
                                ;


                                Enumeration eSpielklassekeys = getTurnierzumupdaten().getSpielklassen().keys();
                                boolean setzlisteloeschen = false;
                                SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
                                TeamDAO teamDAO = new TeamDAOimpl();
                                while (eSpielklassekeys.hasMoreElements()) {

                                    int key = (int) eSpielklassekeys.nextElement();

                                    for (int i = 0; i < getTurnierzumupdaten().getSpielklassen().get(key).getSetzliste().size(); i++) {
                                        if (getTurnierzumupdaten().getSpielklassen().get(key).getSetzliste().size() > 0) {
                                            int id = getTurnierzumupdaten().getSpielklassen().get(key).getSetzliste().get(i).getTeamid();
                                            Team team = auswahlklasse.getTurnierzumupdaten().getTeams().get(id);
                                            //System.out.println(team);
                                            setzlisteloeschen = setzlisteDAO.delete(
                                                    getTurnierzumupdaten().getSpielklassen().get(key).getSpielklasseID());


                                            Enumeration eSpiele = getTurnierzumupdaten().getSpiele().keys();
                                            while (eSpiele.hasMoreElements()) {

                                                int key2 = (int) eSpiele.nextElement();
                                                Spiel spiel = getTurnierzumupdaten().getSpiele().get(key2);
                                                spiel.getSpielDAO().delete(spiel);
                                            }
                                            teamDAO.delete(team);
                                        }

                                    }

                                    if (!setzlisteloeschen)
                                        auswahlklasse.WarnungBenachrichtigung("Setzliste fehler", "fehler");
                                    //System.out.println("Lösche Spielklasse");

                                    erfolg = spielklasseDAO.delete(getTurnierzumupdaten().getSpielklassen().get(key));
                                    if (!erfolg && getTurnierzumupdaten().getSpielklassen().get(key).getSetzliste().size() > 0) {
                                        auswahlklasse.WarnungBenachrichtigung(" Spielklassenfehler", "fehler");

                                    }
                                    if (!erfolg)
                                        break;

                                }


                                //Turnier löschen
                                boolean erfolg2 = t.delete(clickedRow);
                                if (erfolg2) {
                                    auswahlklasse.InfoBenachrichtigung("Turnier löschen erfolgreich", clickedRow.getName() + " wurde gelöscht.");
                                    auswahlklasse.getTurnierliste().remove(getTurnierzumupdaten().getTurnierid());

                                } else {
                                    auswahlklasse.WarnungBenachrichtigung("Turnier Löschen nicht erfolgreich", clickedRow.getName() + " konnte nicht gelöscht werden!");
                                }
                                auswahlklasse.setTurnierzumupdaten(null);
                                turniere.clear();
                                zeigeTabelle();

                            }
                        });
                        if (!contextMenu.isShowing()) {
                            contextMenu.getItems().clear();
                            contextMenu.getItems().addAll(item1, item2, item3);
                        }
                        TurnierlisteTabelle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                if (!contextMenu.isShowing()) {
                                    contextMenu.show(TurnierlisteTabelle, event.getScreenX(), event.getScreenY());
                                }

                            }
                        });
                    }
                });

                return row;
            });
            t_turniersuche.textProperty().addListener((observable, oldValue, newValue) -> {
                // System.out.println("textfield changed from " + oldValue + " to " + newValue);
                //obs_spieler.clear();

                turniere.clear();

                TurnierlisteTabelle.refresh();
                Enumeration e = auswahlklasse.getTurnierliste().keys();
                while (e.hasMoreElements()) {
                    int key = (int) e.nextElement();
                    if (auswahlklasse.getTurnierliste().get(key).getName().toUpperCase().contains(t_turniersuche.getText().toUpperCase())) {
                        turniere.add(0, auswahlklasse.getTurnierliste().get(key));
                    }
                    ;
                }


            });


            TurnierlisteTabelle.getSelectionModel().select(0);


    }


    private void auswahlSpeichern(Turnier item) {

        if (TurnierlisteTabelle.getSelectionModel().getSelectedItem() != null && (Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem() != auswahlklasse.getAktuelleTurnierAuswahl()) {
            auswahlklasse.turnierAuswahlSpeichern((Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem());
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spiele().clear();

            t.read(auswahlklasse.getAktuelleTurnierAuswahl());
            //System.out.println(a.getAktuelleTurnierAuswahl().getName());
            auswahlklasse.turnierAuswahlSpeichern(auswahlklasse.getAktuelleTurnierAuswahl());
            //System.out.println("Das aktuelle Turnier lautet"+a.getAktuelleTurnierAuswahl().toString());
            //Main.getInstance().updateTitle("Badminton Turnierverwaltung - Turnier: "+a.getAktuelleTurnierAuswahl().getName());
            //a.setAktuelleTurnierAuswahl
            //System.out.println("Turnierauswahl durch Doppelklick: = "+item.getName());
            //auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
            //System.out.println("t");


            //((Node)(event.getSource())).getScene().getWindow().hide();
            try {

                setNode(NeuerSpieler);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/*    private void MainLaden() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        primaryStage = new Stage();

        auswahlklasse.addStagesdict(primaryStage,"Main");
        primaryStage.setScene(new Scene(root1));
*//**//*
        primaryStage.show();
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Badminton Turnierverwaltung - Turnier: "+auswahlklasse.getAktuelleTurnierAuswahl().getName());
    }*/

    public void pressBtn_neuesTurnier(ActionEvent event) throws Exception {
        try {
            //((Node)(event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();


            auswahlklasse.addStagesdict(stage, "NeuesTurnier");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("neuesTurnier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage.setScene(new Scene(root1));
            stage.show();
            if (getTurnierzumupdaten() != null) {
                stage.setTitle(getTurnierzumupdaten().getName() + " bearbeiten");
            } else {
                stage.setTitle("Neues Turnier");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    ScreensController myController;
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

/*    @FXML
    private void goToScreen2(ActionEvent event){
        myController.setScreen(Main.screen2ID);
    }

    @FXML
    private void goToScreen3(ActionEvent event){
        myController.setScreen(Main.screen3ID);
    }*/
}

