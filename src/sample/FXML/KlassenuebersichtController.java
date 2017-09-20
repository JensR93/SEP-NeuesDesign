package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Spielklasse;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 19.09.2017.
 */
public class KlassenuebersichtController implements Initializable {
    ObservableList<Spielklasse> obs_spielklasse= FXCollections.observableArrayList();
    ContextMenu context_spielklasse = new ContextMenu();
    ContextMenu contextMenu_all = new ContextMenu();




    String baseName = "resources.Main";
    String titel ="";

    @FXML
    private JFXTabPane tabpane_uebersicht;

    @FXML
    private Tab tab_einzel;

    @FXML
    private VBox klassseeinzel_vbox;

    @FXML
    private Tab tab_doppel;

    @FXML
    private VBox klasssedoppel_vbox;

    @FXML
    private VBox klasssemixed_vbox;

    @FXML
    private JFXButton b_neueKlasse;




    @FXML
    void setNodeNeueKlasse(ActionEvent event) {


        try {
            FXMLLoader fxmlLoaderKlasseuebersicht = new FXMLLoader(getClass().getResource("Klasse_hinzufügen.fxml"));
            fxmlLoaderKlasseuebersicht.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        auswahlklasse.getDashboardController().setNodeKlassehinzufuegen();
    }

    public void pressBtn_Spielsystem(Spielklasse spielklasse) throws Exception {
        auswahlklasse.setAktuelleSpielklassenAuswahl(spielklasse);
auswahlklasse.getDashboardController().setNodeSpielsystem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        auswahlklasse.setKlassenuebersichtController(this);

        SpielklassenHinzufuegen();


        //lbs[i]=new Label(s);//initializing labels





        //doing what you want here with labels
        //...








//A label with the text element

//A label with the text element and graphical icon
//GridPane_NeueKlasse.add(label2,1,0);

    }

    public void SpielklassenHinzufuegen() {
        klasssemixed_vbox.getChildren().clear();
        klassseeinzel_vbox.getChildren().clear();
        klasssedoppel_vbox.getChildren().clear();
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null)
        {
            obs_spielklasse=auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen();
        }

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_neueKlasse");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        b_neueKlasse.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_einzel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_einzel.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_doppel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_doppel.setText(titel);


        Spielklasse sp = null;
        Label label[] = new Label[obs_spielklasse.size()];
        //label[1].setText("jens");


        TextFlow[] flow = new TextFlow[obs_spielklasse.size()+1];
        final Spielklasse[] spauswahl = {null};
        Hyperlink hp=null;

        try{
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);
            String einzelklasse = bundle.getString("einzelklasse");
            String doppelklasse = bundle.getString("doppelklasse");
            String mixedklasse = bundle.getString("mixedklasse");


            for(int i=0;i<obs_spielklasse.size();i++)
            {
                sp= obs_spielklasse.get(i);
                if(sp.getSetzliste()!=null&&sp.getSetzliste().size()>0)
                {
                    hp = new Hyperlink(sp.getDisziplin()+"-"+sp.getNiveau()+" Spieler:"+(sp.getSetzliste().size()*2));
                    //System.out.println(hp+"----------1");
                }
                if(sp.getSpiele()!=null&&sp.getSetzliste()!=null&&sp.getSpiele().size()>0)
                {
                    sp.setSetzliste_gesperrt(true);
                    //System.out.println(sp.isSetzliste_gesperrt());
                    hp = new Hyperlink(sp.getDisziplin()+"-"+sp.getNiveau()+" Spieler:"+(sp.getSetzliste().size()*2)+" Spiele:"+sp.getSpiele().size());
                    //System.out.println(hp+"----------2");
                }
                if(sp.getSetzliste().size()==0||sp.getSetzliste()==null)
                {
                    sp.setSetzliste_gesperrt(false);
                    hp = new Hyperlink(sp.getDisziplin() + "-" + sp.getNiveau());
                    //System.out.println(hp+"----------3");
                }

                if(sp.getDisziplin().contains("doppel"))
                {
                    flow[i] = new TextFlow(new Text(doppelklasse),hp);

                    flow[i].setPadding(new Insets(10));
                    klasssedoppel_vbox.getChildren().addAll(flow[i]);

                }
                if(sp.getDisziplin().contains("einzel"))
                {
                    flow[i] = new TextFlow(new Text(einzelklasse),hp);
                    flow[i].setPadding(new Insets(10));
                    klassseeinzel_vbox.getChildren().addAll(flow[i]);
                }
                if(sp.getDisziplin().contains("Mix"))
                {
                    flow[i] = new TextFlow(new Text(mixedklasse),hp);
                    flow[i].setPadding(new Insets(10));
                    klasssemixed_vbox.getChildren().addAll(flow[i]);
                }

                tabpane_uebersicht.setOnMouseClicked(event ->{

                    if(MouseButton.SECONDARY==event.getButton()&&(event.getTarget()==klassseeinzel_vbox||event.getTarget()==klasssedoppel_vbox||event.getTarget()==klasssemixed_vbox)) {


                        MenuItem item1 = new MenuItem("Neue Spielklasse");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    auswahlklasse.getDashboardController().setNodeKlassehinzufuegen();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                        contextMenu_all.getItems().clear();
                        contextMenu_all.getItems().add(item1);
                        tabpane_uebersicht.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                if(!contextMenu_all.isShowing())
                                {
                                    contextMenu_all.show(tabpane_uebersicht, event.getScreenX(), event.getScreenY());
                                }

                            }
                        });
                    }
                    else
                    {
                        contextMenu_all.hide();
                    }
                } );
                Spielklasse[] finalSp = new Spielklasse[Integer.valueOf(obs_spielklasse.size())];
                int finalI = i;
                finalSp[i]= sp;
                Hyperlink finalHp = hp;
                hp.setOnMouseClicked(event -> {

                /*spauswahl[finalI] =a.getSpielklasseDAO().getSpielklassenDict(a.getTurnierDAO().
                        read(a.getAktuelleTurnierAuswahl())).get(finalI);*/
                            if (finalSp[finalI] != null ) {
                                contextMenu_all.hide();
                                finalSp[finalI] = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(finalI);
                                if(MouseButton.PRIMARY==event.getButton()) {

                                    context_spielklasse.hide();


                                    // System.out.println(spauswahl[finalI].getDisziplin());
                                    try {
                                        //((Node)(event.getSource())).getScene().getWindow().hide();
                                        pressBtn_Spielsystem(finalSp[finalI]);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(MouseButton.SECONDARY==event.getButton()) {

                                    if(context_spielklasse.isShowing())
                                    {
                                        context_spielklasse.hide();
                                    }
                                    MenuItem item1 = new MenuItem("Spielklasse bearbeiten");
                                    item1.setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                        }
                                    });
                                    MenuItem item2 = new MenuItem("Spielklasse löschen");
                                    item2.setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println(finalSp[finalI]);
                                            if(finalSp[finalI].getSetzliste().size()>0)
                                            {
                                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                alert.setTitle("Spielklasse löschen");
                                                alert.setHeaderText("Das Spielsystem ist aktiv");
                                                alert.setContentText("Möchten Sie die Spielklasse wirklich löschen?");
                                                ButtonType buttonTypeOne = new ButtonType("Ja");
                                                ButtonType buttonTypeTwo = new ButtonType("Nein");
                                                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
                                                Optional<ButtonType> result = alert.showAndWait();
                                                if (result.get() == buttonTypeOne){


                                                    Spielklassekomplettloeschen(finalSp[finalI]);
                                                    SpielklassenHinzufuegen();

                                                    // ... user chose OK
                                                } else {
                                                    // ... user chose CANCEL or closed the dialog
                                                }
                                            }
                                            else
                                            {
                                                Spielklassekomplettloeschen(finalSp[finalI]);
                                                SpielklassenHinzufuegen();
                                            }

                                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                                        }
                                    });
                                    context_spielklasse.getItems().clear();
                                    context_spielklasse.getItems().addAll(item1,item2);
                                    contextMenu_all.getItems().clear();
                                    finalHp.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                                        @Override
                                        public void handle(ContextMenuEvent event) {

                                            {
                                                context_spielklasse.show(finalHp, event.getScreenX(), event.getScreenY());
                                            }

                                        }
                                    });

                                }


                            }}

                );
            }
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
    }

    private void Spielklassekomplettloeschen(Spielklasse spielklasse) {
        if(spielklasse.getSetzliste().size()>0)
        {
            spielklasse.getSpielklasseDAO().stoppeSpielsystem(spielklasse);
        }
        Enumeration enumeration= auswahlklasse.getAktuelleTurnierAuswahl().getTeams().keys();
        while (enumeration.hasMoreElements())
        {
            int key = (int) enumeration.nextElement();
            if( auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielklasse()==spielklasse)
            {
                auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getTeamDAO().delete(auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key));
            }

        }
        boolean erfolgloeschen = spielklasse.getSpielklasseDAO().delete(spielklasse);
        auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().remove(spielklasse.getSpielklasseID());
        if(erfolgloeschen)
        {
            if(klassseeinzel_vbox.getChildren().contains(spielklasse))
            {
                klassseeinzel_vbox.getChildren().remove(spielklasse);
            }
            if(klasssedoppel_vbox.getChildren().contains(spielklasse))
            {
                klasssedoppel_vbox.getChildren().remove(spielklasse);
            }
            if(klasssemixed_vbox.getChildren().contains(spielklasse))
            {
                klasssemixed_vbox.getChildren().remove(spielklasse);
            }
            auswahlklasse.InfoBenachrichtigung("erfolg", "klasse gelöscht");
            auswahlklasse.getAktuelleTurnierAuswahl().removeobs_spielklassen(spielklasse);
            auswahlklasse.getAktuelleTurnierAuswahl().removeSpielklassen(spielklasse);
        }
        else
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler", "klasse konnte nicht gelöscht werden");
        }
    }
}
