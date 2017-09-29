package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sample.*;
import sample.DAO.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jens on 26.09.2017.
 */
public class VereinsuebersichtController implements Initializable {
    private Verein updateverein;

    @FXML
    private JFXButton btn_vereinbezahlt;

    private static ObservableList <Spieler> Vereinsspieler;
    Verein clickedRow;
    String baseName = "resources.Main";
    String titel ="";

    ContextMenu contextMenu=new ContextMenu();


    @FXML
    private JFXTextField t_anzahlspieler;
    @FXML
    private TabPane tabpane_verein;
    @FXML
    private JFXTextField t_gesamtgebuehren;

    @FXML
    private ListView<Spieler> list_nichtbezahlt;
    @FXML
    private TableView tabelle_vereine;
    @FXML
    private JFXTextField t_offenegebuehren;
    @FXML
    private JFXTextField vereinsuche;

    @FXML
    public TableColumn Vereinsname;
    @FXML
    public TableColumn Vereinsverband;
    @FXML
    public TableColumn Vereinsextvereinsid;
    @FXML
    private Tab tab_verein;
    @FXML
    private Tab tab_startgeld;
    @FXML
    private JFXTextField tf_Name;

    @FXML
    private JFXTextField tf_Verband;

    @FXML
    private JFXTextField tf_ExtVereinsID;

    @FXML
    private JFXButton btn_Speichern_Verein;

    public void SpracheLaden()
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );

            titel = bundle.getString("Vereinsname");
            Vereinsname.setText(titel);

            titel = bundle.getString("Vereinsverband");
            Vereinsverband.setText(titel);

            titel = bundle.getString("Vereinsextvereinsid");
            Vereinsextvereinsid.setText(titel);


            titel = bundle.getString("vereinsuche");
            vereinsuche.setPromptText(titel);
            vereinsuche.setLabelFloat(true);

            titel = bundle.getString("tab_verein");
            tab_verein.setText(titel);

            titel = bundle.getString("tab_startgeld");
            tab_startgeld.setText(titel);

            titel = bundle.getString("tf_Name");
            tf_Name.setPromptText(titel);
            tf_Name.setLabelFloat(true);

            titel = bundle.getString("tf_Verband");
            tf_Verband.setPromptText(titel);
            tf_Verband.setLabelFloat(true);

            titel = bundle.getString("tf_ExtVereinsID");
            tf_ExtVereinsID.setPromptText(titel);
            tf_ExtVereinsID.setLabelFloat(true);

            if(updateverein==null)
            {
                titel = bundle.getString("btn_Speichern_Verein");
                btn_Speichern_Verein.setText(titel);
            }
            else
            {
                btn_Speichern_Verein.setText("UPDATE");
            }

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
    }

    ObservableList <Verein> obs_Vereine  = FXCollections.observableArrayList();
    @FXML
    public void pressBtn_speichern(){
        if(updateverein==null) {
            String verband = tf_Verband.getText();

            String name = tf_Name.getText();

            String vereinsid = tf_ExtVereinsID.getText();

            Verein verein = new Verein(vereinsid, name, verband);

            auswahlklasse.getSpieler_hinzufuegenController().getVereine().add(verein);
            auswahlklasse.getVereine().put(verein.getExtVereinsID(), verein);
            auswahlklasse.getSpieler_hinzufuegenController().neuenVereinauswaehlen();
            //hier
            //auswahlklasse.getDashboardController().setNodeSpieler();
            updateverein=null;
            obs_Vereine.add(verein);
            tabelle_vereine.refresh();
        }
        else
        {
            updateverein.setName(tf_Name.getText());
            updateverein.setVerband(tf_Verband.getText());
            updateverein.setExtVereinsID(tf_ExtVereinsID.getText());
            updateverein.getVereinDAO().update(updateverein);
            //auswahlklasse.getDashboardController().setNodeVereinsuebersicht();

            try {
                auswahlklasse.getSpieler_hinzufuegenController().printSpielerZuordnenTableNeu();
                auswahlklasse.getSpieler_hinzufuegenController().ladeVereine();
                auswahlklasse.getVereinsuebersichtController().fulleObsVereine();
                tabelle_vereine.refresh();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpracheLaden();
        tabpaneanpassen();
        tableviewanpassen();
        auswahlklasse.setVereinsuebersichtController(this);
        fulleObsVereine();
        zeigeTabelle();
        tabelleListener();


        VereinssucheListener();

        final ContextMenu randomListContextMenu = new ContextMenu();


        ListenerNichtbezahlteSpieler(randomListContextMenu);
    }

    private void tabpaneanpassen()
    {
        tabpane_verein.tabMinWidthProperty().bind(tabpane_verein.widthProperty().multiply(0.49));
        tabpane_verein.tabMaxWidthProperty().bind(tabpane_verein.widthProperty().multiply(0.49));
    }

    private void tableviewanpassen()
    {
        Vereinsname.prefWidthProperty().bind(tabelle_vereine.widthProperty().multiply(0.331));
        Vereinsname.getStyleClass().add("table-viewLeftAlignColumn");
        Vereinsname.setSortable(false);
        Vereinsverband.prefWidthProperty().bind(tabelle_vereine.widthProperty().multiply(0.331));
        Vereinsverband.getStyleClass().add("table-viewLeftAlignColumn");
        Vereinsverband.setSortable(false);
        Vereinsextvereinsid.prefWidthProperty().bind(tabelle_vereine.widthProperty().multiply(0.331));
        Vereinsextvereinsid.getStyleClass().add("table-viewLeftAlignColumn");
        Vereinsextvereinsid.setSortable(false);
    }
    public void setUpdateverein(Verein updateverein) {
        this.updateverein = updateverein;
    }
    private void VereinssucheListener() {
        vereinsuche.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();
            obs_Vereine.clear();
            //auswahlklasse.getTurniere().clear();

            Enumeration enumeration = auswahlklasse.getVereine().keys();
            while (enumeration.hasMoreElements())
            {
                String key = (String) enumeration.nextElement();
                if(auswahlklasse.getVereine().get(key).toString().toUpperCase().contains(vereinsuche.getText().toUpperCase()))
                {
                    obs_Vereine.add((Verein) auswahlklasse.getVereine().get(key));
                }

            }

            tabelle_vereine.refresh();

            tabelle_vereine.setItems(obs_Vereine);
            //auswahlklasse.setTurniere(obs_turniere_anzeige);



        });
    }

    private void ListenerNichtbezahlteSpieler(ContextMenu randomListContextMenu) {
        list_nichtbezahlt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                randomListContextMenu.getItems().clear();
                Spieler clickedrow= list_nichtbezahlt.getSelectionModel().getSelectedItem();
                MenuItem item1 = new MenuItem("Spielereigenschaften anzeigen");
                item1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        auswahlklasse.setSpielerzumHinzufeuegen(clickedrow);
                        auswahlklasse.getDashboardController().setNodeSpielerEigenschaften();
                        //replaceRandomCard();
                    }
                });
                MenuItem item2 = new MenuItem("als bezahlt markieren");
                item2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        clickedrow.setGebuehrenbezahlt(true);
                        list_nichtbezahlt.getItems().remove(clickedrow);

                        berechneVereinGesamtGebuehren();
                        clickedrow.getSpielerDAO().update(clickedrow);
                    }
                });

                randomListContextMenu.getItems().addAll(item1,item2);
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    randomListContextMenu.show(list_nichtbezahlt, event.getScreenX(), event.getScreenY());
                }
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    randomListContextMenu.show(list_nichtbezahlt, event.getScreenX(), event.getScreenY());
                }

            }
        });
    }

    public void updateVerein()
    {
        if(updateverein!=null) {
            tf_Name.setText(updateverein.getName());
            tf_ExtVereinsID.setText(updateverein.getExtVereinsID());
            tf_Verband.setText(updateverein.getVerband());
            btn_Speichern_Verein.setText("Verein aktualisieren");
        }

    }

    @FXML
    private void vereinbezahlt(ActionEvent event) {
        for(int i=0;i<list_nichtbezahlt.getItems().size();i++)
        {
            list_nichtbezahlt.getItems().get(i).setGebuehrenbezahlt(true);
        }
        list_nichtbezahlt.getItems().clear();
        berechneVereinGesamtGebuehren();
    }

    public void tab1Auswahl()
    {
        tab_startgeld.setDisable(true);
        tabpane_verein.getSelectionModel().select(tab_verein);
    }
    private void tabelleListener() {
        tabelle_vereine.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                 clickedRow = (Verein) row.getItem();
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

//                    auswahlklasse.getDashboardController().setNodeNeuerVerein();
                    setUpdateverein(clickedRow);
                    updateVerein();
                    //   a.getStagesdict().get("")
                }
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    contextMenu.hide();
                } else if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {


                    //System.out.println("R-KLICK");
                    MenuItem item1 = new MenuItem("Verein bearbeiten");
                    item1.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


 //                           auswahlklasse.getDashboardController().setNodeNeuerVerein();
                            setUpdateverein(clickedRow);
                            updateVerein();
                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                        }
                    });
                    MenuItem item2 = new MenuItem("Startgeldliste anzeigen");
                    item2.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                        if(Vereinsspieler!=null)
                        {
                            Vereinsspieler.clear();
                        }


                        tabpane_verein.getSelectionModel().select(tab_startgeld);
                            Vereinsspieler= berechneAnzahlSpieler(clickedRow);
                        t_anzahlspieler.setText(String.valueOf(Vereinsspieler.size()));
                        //t_offenegebuehren.setText(String.valueOf(berechneVereinOffeneGebuehren(Vereinsspieler)));
                            berechneVereinGesamtGebuehren();



                        listNichtBezahltFuellen(Vereinsspieler);
                            tab_startgeld.setDisable(false);

                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                        }
                    });
                    MenuItem item3 = new MenuItem("Verein löschen");
                    item3.setOnAction(new EventHandler<ActionEvent>() {


                        @Override
                        public void handle(ActionEvent event) {
                        boolean erfolg = clickedRow.getVereinDAO().delete(clickedRow);


                        if(erfolg)
                        {
                            auswahlklasse.InfoBenachrichtigung("Löschen erf","erf");
                            obs_Vereine.remove(clickedRow);
                            tabelle_vereine.getItems().remove(clickedRow);
                            auswahlklasse.getVereine().remove(clickedRow.getExtVereinsID());
                            for (int i=0;i<auswahlklasse.getObs_spieler().size();i++){
                                Spieler spieler = auswahlklasse.getObs_spieler().get(i);
                                if (spieler.getVerein()==clickedRow){
                                    spieler.setVerein(null);
                                }
                            }
                        }
                        else
                        {
                            auswahlklasse.WarnungBenachrichtigung("Fehler","Verein enthält Spieler");
                        }

                        }
                    });
                    if (!contextMenu.isShowing()) {
                        contextMenu.getItems().clear();
                        contextMenu.getItems().addAll(item1,item2, item3);
                    }
                    tabelle_vereine.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                        @Override
                        public void handle(ContextMenuEvent event) {

                            if (!contextMenu.isShowing()) {
                                contextMenu.show(tabelle_vereine, event.getScreenX(), event.getScreenY());
                            }

                        }
                    });
                }
            });

            return row;
        });
    }



    private void listNichtBezahltFuellen(ObservableList <Spieler> vereinsspieler) {

        list_nichtbezahlt.getItems().clear();
        for(int i=0; i<vereinsspieler.size();i++)
        {
            if(!vereinsspieler.get(i).isGebuehrenbezahlt())
            {
                list_nichtbezahlt.getItems().add(vereinsspieler.get(i));
            }
        }

    }

    private void berechneVereinGesamtGebuehren() {

        float gebuehr=0;
        float bezahlt=0;
        for(int i=0;i<Vereinsspieler.size();i++)
        {


                ArrayList<Spielklasse> a = Vereinsspieler.get(i).checkeSetzlisteMitglied(Vereinsspieler.get(i));
                float einzel=  auswahlklasse.getAktuelleTurnierAuswahl().getMeldegebuehrEinzel();
                float doppel=  auswahlklasse.getAktuelleTurnierAuswahl().getMeldegebuehrDoppel();
                float summe = 0;
                for(int j=0;j<a.size();j++)
                {
                    if(a.get(j).toString().toUpperCase().contains("EINZEL"))
                    {
                        summe+=einzel;
                        if(Vereinsspieler.get(i).isGebuehrenbezahlt())
                        {
                            bezahlt+=einzel;
                        }
                    }
                    else
                    {
                        summe+=doppel;
                        if(Vereinsspieler.get(i).isGebuehrenbezahlt())
                        {
                            bezahlt+=doppel;
                        }
                    }
                }


                gebuehr+=summe;

        }
        t_gesamtgebuehren.setText(String.valueOf(gebuehr));
        t_offenegebuehren.setText(String.valueOf(gebuehr-bezahlt));
    }

    @FXML
    void sperreStartgeldliste(ActionEvent event) {

    }
    private boolean berechneVereinOffeneGebuehren(ObservableList <Spieler> vereinsspieler) {
        boolean offen=false;
        for(int i=0;i<vereinsspieler.size();i++)
        {
           if(vereinsspieler.get(i).isGebuehrenbezahlt())
            {
                offen=true;
            }
        }
        return offen;
    }

    private ObservableList berechneAnzahlSpieler(Verein verein) {
        ObservableList spieler = FXCollections.observableArrayList();
       Enumeration enumeration= auswahlklasse.getSpieler().keys();
       while (enumeration.hasMoreElements())
       {
           int spielerid= (int) enumeration.nextElement();
           if(auswahlklasse.getSpieler().get(spielerid).getVerein().equals(verein))
           {
               spieler.add(auswahlklasse.getSpieler().get(spielerid));
           }
       }
       return spieler;
    }

    public void fulleObsVereine()
    {
        obs_Vereine.clear();
        Enumeration enumeration = auswahlklasse.getVereine().keys();
        while (enumeration.hasMoreElements())
        {
            String key = (String) enumeration.nextElement();
            obs_Vereine.add((Verein) auswahlklasse.getVereine().get(key));
        }
    tabelle_vereine.setItems(obs_Vereine);
    }
    @FXML
    private void zeigeTabelle() {
        //System.out.println("Print table");





        if(vereinsuche.getText().equals(""))
        {

        }
        else
        {

        }
        tabelle_vereine.setItems(obs_Vereine);/**/
        Vereinsname.setCellValueFactory(new PropertyValueFactory<Verein,String>("name"));

        //TurnierIDSpalte.setCellFactory(integerCellFactory);
        Vereinsverband.setCellValueFactory(new PropertyValueFactory<Verein,String>("verband"));
        Vereinsextvereinsid.setCellValueFactory(new PropertyValueFactory<Verein,String>("ExtVereinsID"));

//        tabelle_vereine.getColumns().addAll(Vereinsname,Vereinsverband,Vereinsextvereinsid);

        //TurnierDatumSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Date>("datum"));
        //TurnierNameSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, String>("name"));

        //TurnierIDSpalte.setCellValueFactory(new PropertyValueFactory<Turnier, Integer>("turnierid"));

    }

    @FXML
    public void Vereinsuebersichtauswahl(Event event) {
        if(tab_startgeld!=null)
        {
            tab_startgeld.setDisable(true);
        }

    }
}
