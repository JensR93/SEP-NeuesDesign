package sample.FXML;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.ExcelImport;
import sample.Spieler;

import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jens on 20.09.2017.
 */
public class Spieler_vorhandenController implements Initializable
{

    String baseName = "resources.Main";
    String titel ="";

    @FXML
    private Label lab_neuerSpieler;
    @FXML
    private Label lab_VorhandSpieler;
    @FXML
    private JFXButton btn_zurück;
    @FXML
    private JFXButton btn_SpielerSpeichern;
    @FXML
    private JFXButton btn_SpielerUpdaten;


    //region Deklaration Fxml
    @FXML
    TableView popup_tabelle;
    @FXML TableView popup_tabelle2;

    @FXML
    TableColumn popup_vorname;
    @FXML TableColumn popup_vorname2;

    @FXML TableColumn popup_nachname;
    @FXML TableColumn popup_nachname2;

    @FXML TableColumn popup_nationalitaet;
    @FXML TableColumn popup_nationalitaet2;

    @FXML TableColumn popup_gdatum;
    @FXML TableColumn popup_gdatum2;

    @FXML TableColumn popup_geschlecht;
    @FXML TableColumn popup_geschlecht2;

    @FXML TableColumn popup_verein;
    @FXML TableColumn popup_verein2;

    @FXML TableColumn popup_spielerid;
    @FXML TableColumn popup_spielerid2;

    //endregion
    ExcelImport excelImport = new ExcelImport();
    Spieler updateSpieler;
    Spieler getAktuellerSpieler;

    //ArrayList<Spieler> vorhandeneSpieler;
    ObservableList<Spieler> obs_vorhandeneSpieler = FXCollections.observableArrayList();
    ObservableList<Spieler> obs_neuerSpieler = FXCollections.observableArrayList();
public void resetteDaten()
{
    obs_vorhandeneSpieler.clear();
    obs_neuerSpieler.clear();
    updateSpieler=null;
    getAktuellerSpieler=null;
}

    @FXML
    public  void btn_UpdateSpielerPopup (ActionEvent event)
    {
        if(updateSpieler==null)
        {
            updateSpieler=obs_vorhandeneSpieler.get(0);
        }
        if(updateSpieler!=null)
        {


            updateSpieler.setvName(getAktuellerSpieler.getVName());
            updateSpieler.setnName(getAktuellerSpieler.getNName());
            updateSpieler.setGeschlecht(getAktuellerSpieler.getGeschlecht());
            updateSpieler.setgDatum(getAktuellerSpieler.getGDatum());
            updateSpieler.setVerein(getAktuellerSpieler.getVerein());
            updateSpieler.setNationalitaet(getAktuellerSpieler.getNationalitaet());



            updateSpieler.getSpielerDAO().update(updateSpieler);

            if(auswahlklasse.getDict_doppelte_spieler().size()>0)
            {
                System.out.println("Neuer Frame");
                obs_neuerSpieler.clear();
                obs_vorhandeneSpieler.clear();
                auswahlklasse.InfoBenachrichtigung("Spieler erfolreich aktualisiert",getAktuellerSpieler.toString()+" wurde aktualisiert.");
                auswahlklasse.getSpielerupdate().put(getAktuellerSpieler.toString(),getAktuellerSpieler);

                Tabellefuelle();

            }
            else {
                try {
                    pressBtn_LadeSpielerHinzu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
        else
        {
            auswahlklasse.WarnungBenachrichtigung("Update fehlgeschlagen","Bitte einen Spieler der unteren Tabelle auswählen");
        }
    }
    @FXML
    public void btn_SpeicherSpielerPopup(ActionEvent event)
    {
if(getAktuellerSpieler!=null) {
    System.out.println(getAktuellerSpieler.getNName());
    getAktuellerSpieler.getSpielerDAO().create(getAktuellerSpieler);
    if (auswahlklasse.getSpieler().get(getAktuellerSpieler) == null) {
        auswahlklasse.addSpieler(getAktuellerSpieler);
        auswahlklasse.getSpieler().put(getAktuellerSpieler.getSpielerID(), getAktuellerSpieler);
        System.out.println("Erfolg");
    }
    if (auswahlklasse.getSpielererfolgreich() != null) {
        if (auswahlklasse.getSpielererfolgreich().get(getAktuellerSpieler.toString()) == null) {
            auswahlklasse.getSpielererfolgreich().put(getAktuellerSpieler.toString(), getAktuellerSpieler);
        }
    }
    auswahlklasse.getDict_doppelte_spieler().remove(getAktuellerSpieler);
    auswahlklasse.InfoBenachrichtigung("Spieler erfolreich hinzugefügt",getAktuellerSpieler.toString()+" wurde hinzugefügt.");
}
else
{
    System.out.println();
}

        try {
            if(auswahlklasse.getDict_doppelte_spieler().size()>0)
            {

                obs_neuerSpieler.clear();
                obs_vorhandeneSpieler.clear();
                getAktuellerSpieler=null;
                updateSpieler=null;

                Tabellefuelle();


            }
            else {

                pressBtn_LadeSpielerHinzu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //region Button
    public void pressBtn_LadeSpielerHinzu () throws Exception {
        auswahlklasse.getDashboardController().meldeformularImport();


        //ExcelImport ex = new ExcelImport();
        //ex.pressBtn_Popup();

    }

    @FXML
    public void selectrow(MouseEvent event)
    {
        System.out.println(popup_tabelle2.getSelectionModel().getSelectedItem());
        updateSpieler= (Spieler) popup_tabelle2.getSelectionModel().getSelectedItem();
    }

    //Endregion
    public void Tabellefuelle()
    {

        Boolean elementevorhanden=true;




        Enumeration enumeration = auswahlklasse.getDict_doppelte_spieler().keys();

        if(enumeration.hasMoreElements())
        {
            Spieler key = (Spieler) enumeration.nextElement();


            if(auswahlklasse.getSpielererfolgreich().size()>0) {
                if (auswahlklasse.getSpielererfolgreich().get(key.toString()) != null) {


                    int[] rpunktealt = key.getrPunkte();
                    for (int i = 0; i < auswahlklasse.getSpielererfolgreich().get(key.toString()).getrPunkte().length; i++) {

                        if (rpunktealt[i] != 0) {
                            System.out.println(rpunktealt[i]);
                            if (auswahlklasse.getSpielererfolgreich().get(key.toString()).getrPunkte()[i] == 0) {
                                auswahlklasse.getSpielererfolgreich().get(key.toString()).getrPunkte()[i] = Integer.valueOf(rpunktealt[i]);
                            }

                        }

                    }
                    //spielererfolgreich.get(aktuellerSpieler.toString()).setrPunkte(rpunktekomplett);
                    // aktuellerSpieler.setrPunkte(rpunktekomplett);
                    // auswahlklasse.getSpieler().get( spielererfolgreich.get(aktuellerSpieler.getSpielerID())).setrPunkte(rpunktekomplett);
                    key.getSpielerDAO().update(auswahlklasse.getSpielererfolgreich().get(key.toString()));
                    System.out.println("Spieler wurde schon hinzugefügt, RLP aktualisiert       "+key);

                    elementevorhanden=false;

                }
            }
            //System.out.println(aktuellerSpieler.toString()+String.valueOf(aktuellerSpieler.getrPunkte()));

            obs_vorhandeneSpieler=auswahlklasse.getDict_doppelte_spieler().get(key);

            if(obs_vorhandeneSpieler==null)
            {
                elementevorhanden=false;
            }
            else if(obs_vorhandeneSpieler.size()==0)
            {
                elementevorhanden=false;
            }

            if(elementevorhanden)
            {




            obs_neuerSpieler.add(key);
            getAktuellerSpieler=key;
                setTable();
            }
            else
            {
                System.out.println("llere tabelle");
                if(auswahlklasse.getDict_doppelte_spieler().get(key)!=null)
                {
                    auswahlklasse.getDict_doppelte_spieler().remove(key);
                }

                if(auswahlklasse.getDict_doppelte_spieler().size()>0)
                {
                    Tabellefuelle();
                }
                else
                {
                    try {
                        pressBtn_LadeSpielerHinzu();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }






/*        else
        {
            obs_vorhandeneSpieler=auswahlklasse.getObs_vorhandeneSpielerSpielerhinzu();
            obs_neuerSpieler.add(auswahlklasse.getNeuerSpielerSpielerhinzu());
        }*/



        //ExcelImport.getDict_doppelte_spieler().remove(ExcelImport.getAktuellerSpieler());


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auswahlklasse.setSpieler_vorhandenController(this);
        Tabellefuelle();
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_neuerSpieler");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_neuerSpieler.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_spielerid");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_spielerid.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_vorname");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_vorname.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_nachname");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_nachname.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_geschlecht");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_geschlecht.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_gdatum");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_gdatum.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_verein");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_verein.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_nationalitaet");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_nationalitaet.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_VorhandSpieler");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_VorhandSpieler.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_spielerid2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_spielerid2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_vorname2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_vorname2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_nachname2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_nachname2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_geschlecht2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_geschlecht2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_gdatum2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_gdatum2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_verein2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_verein2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("popup_nationalitaet2");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        popup_nationalitaet2.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_zurück");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_zurück.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_SpielerSpeichern");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_SpielerSpeichern.setText(titel);


        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_SpielerUpdaten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_SpielerUpdaten.setText(titel);


        //obs_vorhandeneSpieler=auswahlklasse.getDict_doppelte_spieler().get(getAktuellerSpieler);

        setTable();





      /*  popup_tabelle2.requestFocus();
        popup_tabelle2.getSelectionModel().selectFirst();
        popup_tabelle2.getFocusModel().focus(0);*/
        //popup_tabelle2.refresh();
    }


    public void setTable()
    {
        popup_tabelle2.setItems(obs_vorhandeneSpieler);
        popup_vorname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
        popup_nachname2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
        popup_geschlecht2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
        popup_verein2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
        popup_gdatum2.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
        popup_nationalitaet2.setCellValueFactory(new PropertyValueFactory<Spieler,String>("Nationalitaet"));
        popup_spielerid.setCellValueFactory(new PropertyValueFactory<Spieler,Integer>("ExtSpielerID"));

        popup_tabelle.setItems(obs_neuerSpieler);
        popup_vorname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
        popup_nachname.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
        popup_geschlecht.setCellValueFactory(new PropertyValueFactory<Spieler,String>("sGeschlecht"));
        popup_verein.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
        popup_gdatum.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
        popup_nationalitaet.setCellValueFactory(new PropertyValueFactory<Spieler,String>("Nationalitaet"));
        popup_spielerid2.setCellValueFactory(new PropertyValueFactory<Spieler,Integer>("ExtSpielerID"));
        popup_tabelle2.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY) {
                    updateSpieler = (Spieler) row.getItem();
                    System.out.println("Spieler ausgewählt "+updateSpieler.getSpielerID()
                            +" "+updateSpieler.getNName());
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                }
            });
            return row ;
        });

        if (obs_vorhandeneSpieler.size() > 0)
        {
            popup_tabelle2.getSelectionModel().select(obs_vorhandeneSpieler.get(0));
        }


        popup_tabelle2.refresh();
    }

    public void initialize() {
    }
}
