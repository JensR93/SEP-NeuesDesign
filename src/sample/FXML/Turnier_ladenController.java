package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.bind.v2.schemagen.episode.Klass;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DAO.*;
import sample.Spiel;
import sample.Team;
import sample.Turnier;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static sample.DAO.auswahlklasse.getTurnierzumupdaten;

/**
 * Created by jens on 17.09.2017.
 */
public class Turnier_ladenController extends Application implements Initializable {
    String baseName = "resources.Main";
    String titel ="";

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
    @FXML
    private Text t_TurnierLaden;
    @FXML
    private StackPane holderPane;
    @FXML
    private JFXButton btn_neuesTurnier;
    private JFXTabPane NeuerSpieler;
    private StackPane TurnierLaden;
    TurnierDAO t = new TurnierDAOimpl();
    ObservableList <Turnier> obs_turniere_anzeige=FXCollections.observableArrayList();
    private static Stage primaryStage;
    public Turnier_ladenController()
    {
        System.out.println();
    }

    public void SpracheLaden()
    {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);

            titel = bundle.getString("TurnierNameSpalte");
            TurnierNameSpalte.setText(titel);

            titel = bundle.getString("t_TurnierLaden");
            t_TurnierLaden.setText(titel);

            titel = bundle.getString("TurnierDatumSpalte");
            TurnierDatumSpalte.setText(titel);

            titel = bundle.getString("t_turniersuche");
            t_turniersuche.setPromptText(titel);
            t_turniersuche.setLabelFloat(true);

            titel = bundle.getString("btn_neuesTurnier");
            btn_neuesTurnier.setText(titel);

        } catch (MissingResourceException e) {
            System.err.println(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("t");
    }

    public Turnier_ladenController(String refresh)
    {
        primaryStage.setTitle(refresh);
    }

public void tabelleReload()
{
    try {
        turnierlisteLaden();
    } catch (Exception e) {
        e.printStackTrace();
    }
    TurnierlisteTabelle.refresh();
}

    @FXML
    private void zeigeTabelle() {
        //System.out.println("Print table");





        if(t_turniersuche.getText().equals(""))
        {
            TurnierlisteTabelle.setItems(auswahlklasse.getTurniere());
        }
        else
        {

        }
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

        SpracheLaden();

        auswahlklasse.setTurnier_ladenController(this);

        try {
            turnierlisteLaden();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                                    ResourceBundle bundle = ResourceBundle.getBundle( baseName );
                                    String titel1 = bundle.getString("Label_Spieleinstellungen");
                                    String titel2 = bundle.getString("Label_Spieleinstellungen");
                                    auswahlklasse.InfoBenachrichtigung("Titel löschen", "Das Turnier "+ clickedRow.getName()+" wurde gelöscht");

                                    auswahlklasse.getTurniere().remove(getTurnierzumupdaten());

                                    if(obs_turniere_anzeige.size()>0)
                                    {
                                        obs_turniere_anzeige.remove(getTurnierzumupdaten());
                                    }

                                } else {
                                    auswahlklasse.WarnungBenachrichtigung("Turnier Löschen nicht erfolgreich", clickedRow.getName() + " konnte nicht gelöscht werden!");
                                }
                                auswahlklasse.setTurnierzumupdaten(null);
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
                obs_turniere_anzeige.clear();
                ObservableList<Turnier> obs_turniere = auswahlklasse.getTurniere();
                //auswahlklasse.getTurniere().clear();

                TurnierlisteTabelle.refresh();
                for(int i=0;i<auswahlklasse.getTurniere().size();i++)
                {
                    if (obs_turniere.get(i).getName().toUpperCase().contains(t_turniersuche.getText().toUpperCase())) {
                        obs_turniere_anzeige.add(obs_turniere.get(i));
                    }

                }
                TurnierlisteTabelle.setItems(obs_turniere_anzeige);
                //auswahlklasse.setTurniere(obs_turniere_anzeige);



            });


            TurnierlisteTabelle.getSelectionModel().select(0);


    }



    private void turnierlisteLaden() throws Exception {
        auswahlklasse.readTurnierListe();
        if (auswahlklasse.getAktuelleTurnierAuswahl() != null) {
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_alleSpiele().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_angezeigteSpiele().clear();
        }
        zeigeTabelle();
    }


    private void auswahlSpeichern(Turnier item) {

        if (TurnierlisteTabelle.getSelectionModel().getSelectedItem() != null && (Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem() != auswahlklasse.getAktuelleTurnierAuswahl()) {
            auswahlklasse.turnierAuswahlSpeichern((Turnier) TurnierlisteTabelle.getSelectionModel().getSelectedItem());
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_alleSpiele().clear();

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

                //setNode(NeuerSpieler);

                FXMLLoader fxmlLoaderKlassenuebersicht = new FXMLLoader(getClass().getResource("Klasse.fxml"));
                fxmlLoaderKlassenuebersicht.load();

                auswahlklasse.getKlassenuebersichtController().SpielklassenHinzufuegen();
                auswahlklasse.getDashboardController().allesFreigeben();
                auswahlklasse.getDashboardController().createPages();
                if(auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().size()>0)
                {
                    auswahlklasse.getDashboardController().setNodeSpieluebersicht();
                }
                else
                {
                    auswahlklasse.getDashboardController().setNodeKlassenuebersicht();
                }


                //holderPane.getParent().
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void pressBtn_neuesTurnier(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoaderNeuesTurnier = new FXMLLoader(getClass().getResource("NeuesTurnier.fxml"));
            fxmlLoaderNeuesTurnier.load();




            auswahlklasse.getDashboardController().setNodeNeuesTurnier();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

