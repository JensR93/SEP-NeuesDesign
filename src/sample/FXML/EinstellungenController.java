package sample.FXML;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sample.DAO.auswahlklasse;
import sample.Spiel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EinstellungenController implements Initializable {

    String baseName = "resources.Main";
    String titel ="";

    @FXML
    private ChoiceBox<String> Choice_Sprache;

    @FXML
    private JFXColorPicker color_aktiv;

    @FXML
    private JFXColorPicker color_ausstehend;

    @FXML
    private JFXColorPicker color_zukunft;

    @FXML
    private JFXColorPicker color_gespielt;

    @FXML
    private Label Label_Spieleinstellungen;

    @FXML
    private Label lab_Sprache;

    @FXML
    private Label Label_SchiriStandVerw;

    @FXML
    private Label lab_VormSchiri;

    @FXML
    private Label lab_ausstehSpiel;

    @FXML
    private Label lab_aktiveSpiele;

    @FXML
    private Label lab_gespielSpiele;

    @FXML
    private Label Label_ZukSpiele;

    @FXML
    private JFXButton btn_StandEinstell;

    @FXML
    private Label Label_Drucker;

    @FXML
    private JFXButton btn_DruckerAendern;

    @FXML
    private JFXButton btn_TestseiteDrucken;

    @FXML
    private GridPane gridPane;

    @FXML
    private ToggleGroup Language;

    @FXML
    private JFXRadioButton rdVerlierer;

    @FXML
    private ToggleGroup level;

    @FXML
    private JFXRadioButton rdGewinner;

    @FXML
    private JFXRadioButton rdAus;

    @FXML
    private JFXButton btnClose21;

    @FXML
    private JFXButton btnClose2111;

    @FXML
    private JFXButton btnClose21111;

    @FXML
    private JFXToggleButton toggle_schiri;

    @FXML
    private JFXTextField tf_aktuellerDrucker;

    @FXML
    void exit(ActionEvent event) {

    }

    public String getSprache() {
        return Sprache;
    }

    public void SpracheLaden()
    {
        if(auswahlklasse.getSpieler_hinzufuegenController() != null) {
            auswahlklasse.getSpieler_hinzufuegenController().SpracheLaden();
        }
        if(auswahlklasse.getDashboardController() != null) {
            auswahlklasse.getDashboardController().SpracheLaden();
        }
        if (auswahlklasse.getTurnier_ladenController() !=null) {
            auswahlklasse.getTurnier_ladenController().SpracheLaden();
        }
        if (auswahlklasse.getKlassenuebersichtController() !=null) {
            auswahlklasse.getKlassenuebersichtController().SpracheLaden();
        }
        if (auswahlklasse.getSpielErgebnisEintragenController() !=null) {
            auswahlklasse.getSpielErgebnisEintragenController().SpracheLaden();
        }
        if(auswahlklasse.getNeuesTurnierController() !=null) {
            auswahlklasse.getNeuesTurnierController().SpracheLaden();
        }
        if(auswahlklasse.getVereinsuebersichtController() != null) {
            auswahlklasse.getVereinsuebersichtController().SpracheLaden();
        }
        Spiel.SpracheLaden();

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );

            titel = bundle.getString("Label_Spieleinstellungen");
            Label_Spieleinstellungen.setText(titel);

            titel = bundle.getString("lab_Sprache");
            lab_Sprache.setText(titel);

            titel = bundle.getString("Label_SchiriStandVerw");
            Label_SchiriStandVerw.setText(titel);

           /* titel = bundle.getString("lab_VormSchiri");
            lab_VormSchiri.setText(titel);*/

            titel = bundle.getString("lab_ausstehSpiel");
            lab_ausstehSpiel.setText(titel);

            titel = bundle.getString("lab_aktiveSpiele");
            lab_aktiveSpiele.setText(titel);

            titel = bundle.getString("lab_gespielSpiele");
            lab_gespielSpiele.setText(titel);

            titel = bundle.getString("Label_ZukSpiele");
            Label_ZukSpiele.setText(titel);

            titel = bundle.getString("btn_StandEinstell");
            btn_StandEinstell.setText(titel);

            Choice_Sprache.getItems().clear();
            Choice_Sprache.getItems().add(bundle.getString("spracheDeutsch"));
            if (Sprache.equals("de")){
                Choice_Sprache.getSelectionModel().select(0);
            }
            Choice_Sprache.getItems().add(bundle.getString("spracheEnglisch"));
            if (Sprache.equals("en")){
                Choice_Sprache.getSelectionModel().select(1);
            }
            Choice_Sprache.getItems().add(bundle.getString("spracheSpanisch"));
            if (Sprache.equals("es")){
                Choice_Sprache.getSelectionModel().select(2);
            }
            Choice_Sprache.getItems().add(bundle.getString("spracheTuerkisch"));
            if (Sprache.equals("tk")){
                Choice_Sprache.getSelectionModel().select(3);
            }
            Choice_Sprache.getItems().add(bundle.getString("spracheFranzoesisch"));
            if (Sprache.equals("fr")){
                Choice_Sprache.getSelectionModel().select(4);
            }


            /*titel = bundle.getString("rdGewinner");
            rdGewinner.setText(titel);
            rdGewinner.setSelectedColor(Color.AQUAMARINE);

            titel = bundle.getString("rdVerlierer");
            rdVerlierer.setText(titel);
            rdVerlierer.setSelectedColor(Color.AQUAMARINE);

            titel = bundle.getString("rdAus");
            rdAus.setText(titel);
            rdAus.setSelectedColor(Color.AQUAMARINE);*/

          /*  titel = bundle.getString("Label_Drucker");
            Label_Drucker.setText(titel);

            titel = bundle.getString("btn_DruckerAendern");
            btn_DruckerAendern.setText(titel);

            titel = bundle.getString("btn_TestseiteDrucken");
            btn_TestseiteDrucken.setText(titel);

            titel = bundle.getString("tf_aktuellerDrucker");
            tf_aktuellerDrucker.setPromptText(titel);
            tf_aktuellerDrucker.setLabelFloat(true);
*/

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }


    }

    @FXML
    public void pressbtn_Standardeinstellungen(ActionEvent event)
    {
        ZukuenftigeSpieleFarbe="#000000" ;
        AusstehendeSpieleFarbe="#006400" ;
        AktiveSpieleFarbe="#00008b" ;
        GespielteSpieleFarbe = "#ff0000";

        Sprache = "de";
        SchiedsrichterStandard=true;
        VormerkungSchiedsrichter="Gewinner";
        Einstellungen_schreiben();
        SetzeEinstellungen();
        try {
            auswahlklasse.getSpieluebersichtController().printSpielTable();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    String ZukuenftigeSpieleFarbe="#000000" ;
    String AusstehendeSpieleFarbe="#006400" ;
    String AktiveSpieleFarbe="#00008b" ;
    String GespielteSpieleFarbe = "#ff0000";

    String Sprache = "de";
    Boolean SchiedsrichterStandard=true;
    String VormerkungSchiedsrichter="Gewinner";

    private void variablenLaden() {
        File f = new File("Einstellungen.xml");
        if(f.exists() && !f.isDirectory()) {
            Properties loadProps = new Properties();
            try {
                loadProps.loadFromXML(new FileInputStream("Einstellungen.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ZukuenftigeSpieleFarbe = loadProps.getProperty("ZukuenftigeSpieleFarbe");
            AusstehendeSpieleFarbe = loadProps.getProperty("AusstehendeSpieleFarbe");
            AktiveSpieleFarbe = loadProps.getProperty("AktiveSpieleFarbe");
            GespielteSpieleFarbe = loadProps.getProperty("GespielteSpieleFarbe");

            Sprache = loadProps.getProperty("Sprache");
            SchiedsrichterStandard = Boolean.valueOf(loadProps.getProperty("SchiedsrichterStandard"));
            VormerkungSchiedsrichter = loadProps.getProperty("VormerkungSchiedsrichter");

        }
        else
        {
            Einstellungen_schreiben();
        }
    }
    public void SetzeEinstellungen()
    {
        color_ausstehend.setValue(Color.valueOf(AusstehendeSpieleFarbe));
        color_aktiv.setValue(Color.valueOf(AktiveSpieleFarbe));
        color_gespielt.setValue(Color.valueOf(GespielteSpieleFarbe));
        color_zukunft.setValue(Color.valueOf(ZukuenftigeSpieleFarbe));
        if (auswahlklasse.getSpieluebersichtController()!=null) {
            try {
                auswahlklasse.getSpieluebersichtController().printSpielTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(SchiedsrichterStandard)
        {
            toggle_schiri.setSelected(true);
        }
        else
        {
            toggle_schiri.setSelected(false);
        }
       /* if(VormerkungSchiedsrichter.equals("Gewinner"))
        {
            rdGewinner.setSelected(true);
        }
        if(VormerkungSchiedsrichter.equals("Verlierer"))
        {
            rdVerlierer.setSelected(true);
        }
        if(VormerkungSchiedsrichter.equals("Aus"))
        {
            rdAus.setSelected(true);
        }*/

    }

    public void Einstellungen_schreiben() {
        Properties saveProps = new Properties();

        saveProps.setProperty("dbHost", "localhost");
        saveProps.setProperty("dbPort", "3306");
        saveProps.setProperty("dbName", "turnierverwaltung_neu");
        saveProps.setProperty("dbUser", "root");
        saveProps.setProperty("dbPass", "");



        saveProps.setProperty("Sprache", Sprache);
        saveProps.setProperty("SchiedsrichterStandard", SchiedsrichterStandard.toString());
        saveProps.setProperty("VormerkungSchiedsrichter", VormerkungSchiedsrichter);
        //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
        saveProps.setProperty("ZukuenftigeSpieleFarbe", ZukuenftigeSpieleFarbe);
        saveProps.setProperty("AusstehendeSpieleFarbe", AusstehendeSpieleFarbe);
        saveProps.setProperty("AktiveSpieleFarbe", AktiveSpieleFarbe);
        saveProps.setProperty("GespielteSpieleFarbe", GespielteSpieleFarbe);



        try {
            saveProps.storeToXML(new FileOutputStream("Einstellungen.xml"), "Badminton Turnierverwaltung Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        auswahlklasse.setEinstellungenController(this);


        variablenLaden();
        // Load Settings


        changeListener();

        SetzeEinstellungen();
        if(getSprache().equals("de"))
        {
            Locale.setDefault( new Locale("de", "de") );
            System.out.println("Sprache=Deutsch");
        }
        if(getSprache().equals("en"))
        {
            Locale.setDefault( new Locale("en", "en") );
            System.out.println("Sprache=Englisch");
        }
        if(getSprache().equals("es"))
        {
            Locale.setDefault( new Locale("es", "es") );
            System.out.println("Sprache=Spanisch");
        }
        if(getSprache().equals("tk"))
        {
            Locale.setDefault( new Locale("tk", "tk") );
            System.out.println("Sprache=Türkisch");
        }

        SpracheLaden();

    }

    private void changeListener() {
        color_zukunft.setOnAction(e ->
        {
            //System.out.println(color_ausstehend.getValue());

            if(!color_zukunft.getValue().equals(Color.valueOf(ZukuenftigeSpieleFarbe)))
            {
                System.out.println("Neue Farbe");
                ZukuenftigeSpieleFarbe=color_zukunft.getValue().toString();
                Einstellungen_schreiben();
                try {
                    auswahlklasse.getSpieluebersichtController().printSpielTable();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        color_ausstehend.setOnAction(e ->
        {
            //System.out.println(color_ausstehend.getValue());

            if(!color_ausstehend.getValue().equals(Color.valueOf(AusstehendeSpieleFarbe)))
            {
                System.out.println("Neue Farbe");
                AusstehendeSpieleFarbe=color_ausstehend.getValue().toString();
                Einstellungen_schreiben();
                try {
                    auswahlklasse.getSpieluebersichtController().printSpielTable();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        color_aktiv.setOnAction(e ->
        {
            //System.out.println(color_ausstehend.getValue());

            if(!color_aktiv.getValue().equals(Color.valueOf(AktiveSpieleFarbe)))
            {
                System.out.println("Neue Farbe");
                AktiveSpieleFarbe=color_aktiv.getValue().toString();
                Einstellungen_schreiben();
                try {
                    auswahlklasse.getSpieluebersichtController().printSpielTable();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        color_gespielt.setOnAction(e ->
        {
            //System.out.println(color_ausstehend.getValue());

            if(!color_gespielt.getValue().equals(Color.valueOf(GespielteSpieleFarbe)))
            {
                System.out.println("Neue Farbe");
                GespielteSpieleFarbe=color_gespielt.getValue().toString();
                Einstellungen_schreiben();
                try {
                    auswahlklasse.getSpieluebersichtController().printSpielTable();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        Choice_Sprache.setOnAction(e ->
        {
            String selection = Choice_Sprache.getSelectionModel().getSelectedItem();
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            if(selection.equals(bundle.getString("spracheDeutsch"))){
                if(!Sprache.equals("de"))
                {
                    Locale.setDefault( new Locale("de", "de") );
                    System.out.println("Sprache=Deutsch");
                    Sprache="de";
                    Einstellungen_schreiben();
                    SpracheLaden();
                }
            }

            if(selection.equals(bundle.getString("spracheEnglisch"))){
                if(!Sprache.equals("en"))
                {
                    Locale.setDefault( new Locale("en", "en") );
                    System.out.println("Sprache=Englisch");
                    Sprache="en";
                    Einstellungen_schreiben();
                    SpracheLaden();
                }
            }
            if(selection.equals(bundle.getString("spracheSpanisch"))){
                if(!Sprache.equals("es"))
                {
                    Locale.setDefault( new Locale("es", "es") );
                    System.out.println("Sprache=Spanisch");
                    Sprache="es";
                    Einstellungen_schreiben();
                    SpracheLaden();
                }
            }
            if(selection.equals(bundle.getString("spracheTuerkisch"))){
                if(!Sprache.equals("tk"))
                {
                    Locale.setDefault( new Locale("tk", "tk") );
                    System.out.println("Sprache=Türkisch");
                    Sprache="tk";
                    Einstellungen_schreiben();
                    SpracheLaden();
                }
            }
        });
        toggle_schiri.setOnAction(e ->
        {
            //System.out.println(color_ausstehend.getValue());

            SchiedsrichterStandard= Boolean.valueOf(toggle_schiri.isSelected());
            Einstellungen_schreiben();

        });
    }


    public String getGespielteSpieleFarbe() {
        return GespielteSpieleFarbe;
    }

    public void setGespielteSpieleFarbe(String gespielteSpieleFarbe) {
        GespielteSpieleFarbe = gespielteSpieleFarbe;
    }

    public String getZukuenftigeSpieleFarbe() {
        return ZukuenftigeSpieleFarbe;
    }

    public void setZukuenftigeSpieleFarbe(String zukuenftigeSpieleFarbe) {
        ZukuenftigeSpieleFarbe = zukuenftigeSpieleFarbe;
    }

    public String getAusstehendeSpieleFarbe() {
        return AusstehendeSpieleFarbe;
    }

    public void setAusstehendeSpieleFarbe(String ausstehendeSpieleFarbe) {
        AusstehendeSpieleFarbe = ausstehendeSpieleFarbe;
    }

    public String getAktiveSpieleFarbe() {
        return AktiveSpieleFarbe;
    }

    public void setAktiveSpieleFarbe(String aktiveSpieleFarbe) {
        AktiveSpieleFarbe = aktiveSpieleFarbe;
    }


}
