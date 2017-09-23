package sample.FXML;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import sample.DAO.auswahlklasse;
import sample.Ergebnis;
import sample.Spiel;
import sample.Turnier;

import java.net.URL;
import java.util.Dictionary;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.UP;

/**
 * Created by jens on 21.09.2017.
 */
public class SpielErgebnisEintragenController implements Initializable{
    String baseName = "resources.Main";
    String titel ="";


    @FXML
    private HBox hbox_5;
    @FXML
    private HBox hbox_4;
    @FXML
    private Button btn_OK;


    @FXML
    private TextField ts1_1;

    @FXML
    private TextField ts1_2;

    @FXML
    private TextField ts2_1;

    @FXML
    private TextField ts2_2;

    @FXML
    private TextField ts3_1;

    @FXML
    private TextField ts3_2;
    @FXML
    private Label l_heim;
    @FXML
    private Label l_gast;

    @FXML
    private ImageView green_check_1;
    @FXML
    private ImageView green_check_2;
    @FXML
    private ImageView green_check_3;
    @FXML
    private ImageView red_cross_1;
    @FXML
    private ImageView red_cross_2;
    @FXML
    private ImageView red_cross_3;

    Dictionary<Integer, Spiel> dictspiele = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele();

    Spiel sp = dictspiele.get(auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielID());


    Ergebnis erg;
    int s11=-1;
    int s12=-1;
    int s21=-1;
    int s22=-1;
    int s31=-1;
    int s32=-1;
    int s41=-1;
    int s42=-1;
    int s51=-1;
    int s52=-1;

    @FXML
    void pressbtn_Abbbruch(ActionEvent event) {
        leereSatzfelder();
        auswahlklasse.getDashboardController().setNodeSpieluebersicht();

    }
    public boolean gueltigesErgebnis(int s11, int s12){

        if(s11<0||s12<0)
        {
           // l_meldungergebnis.setText("Negative Ergebnisse sind ungültig");
            return false;
        }
        if (Math.abs(s11-s12)<2)
        {
            if (!((s11==29 && s12==30)||s11==30 && s12==29)){
                //l_meldungergebnis.setText("Ein Satz muss mit 2 Punkten Differenz gewonnen werden");

                System.out.println("Fehler in Satz ");
                return false;
            }
        }
        if(s11<21&&s12<21)
        {
            //l_meldungergebnis.setText("Ein Satz muss mindestens 21 Punkte haben");
            return false;
        }
        if (s11>30 || s12>30){
            // l_meldungergebnis.setText("Ein Satz kann maximal bis 30 Punkte gehen");
            return false;
        }
        if((s11>18 && s12>18) && Math.abs(s11-s12)>2 ){
            //l_meldungergebnis.setText("Ungültiges Satzergebnis ");
            return false;
        }

        return true;
    }


    @FXML
    void pressbtn_OK(ActionEvent event) {


        //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
        if(sp!=null && sp.getStatus()==0)
        {
            System.out.println("unvollständiges Spiel");
        }

        if(sp!=null && sp.getStatus()==2)
        {


            System.out.println("aktives Spiel");
            if(erg!=null) {
                try {

                    auswahlklasse.getSpielAuswahlErgebniseintragen().setErgebnis(erg);
                    auswahlklasse.getSpielAuswahlErgebniseintragen().setStatus(3);
                    auswahlklasse.InfoBenachrichtigung("Erfolg","Ergebnis eingetragen");
                    auswahlklasse.getDashboardController().setNodeSpieluebersicht();
                    auswahlklasse.getSpieluebersichtController().CheckeSpielsuche();
                    auswahlklasse.getSpieluebersichtController().spielInTabelleAuswaehlen(sp);
                    auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielsystem().updateVisualisierung();
                    // a.getAktuelleTurnierAuswahl().addobsGespielteSpiele(a.getSpielAuswahlErgebniseintragen());
                    // l_meldungergebnis.setText("Ergebnis erfolgreich eingetragen");


                } catch (Exception e) {
                    e.printStackTrace();
                    // l_meldungergebnis.setText("Satz nicht ausgefüllt");
                }

            }

        }
        if(sp!=null && sp.getStatus()==1)
        {
            System.out.println("ausstehendes Spiel");
        }
        if(sp!=null && sp.getStatus()==3)
        {
            System.out.println("gespieltes Spiel");
            ts1_1.setEditable(false);
        }

    }

    private void leereSatzfelder()
    {
        ts1_1.setText("");
        ts1_2.setText("");
        ts2_1.setText("");
        ts2_2.setText("");
        ts3_1.setText("");
        ts3_2.setText("");
    }




    private void setzeErgebnis()
    {
        if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1&&s41!=-1&&s42!=-1&&s51!=-1&&s52!=-1)
        {

            erg = new Ergebnis(s11,s12,s21,s22,s31,s32,s41,s42,s51,s52);

            //  l_meldungergebnis.setText("Ergebnis ist ausgefüllt!");

            //a.getAktuelleTurnierAuswahl().
        }
        else if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1&&s41!=-1&&s42!=-1)
        {

            erg = new Ergebnis(s11,s12,s21,s22,s31,s32,s41,s42);

            //  l_meldungergebnis.setText("Ergebnis ist ausgefüllt!");

            //a.getAktuelleTurnierAuswahl().
        }
        else if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1)
        {

            erg = new Ergebnis(s11,s12,s21,s22,s31,s32);

            //  l_meldungergebnis.setText("Ergebnis ist ausgefüllt!");

            //a.getAktuelleTurnierAuswahl().
        }
    }
    @FXML
    void pressbtn_Verlegen(ActionEvent event) {

    }

    @FXML
    void pressbtn_Zurueckziehen(ActionEvent event) {

    }

    @FXML
    void pressbtn_schitzzettel(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        s11=-1;
        s12=-1;
        s21=-1;
        s22=-1;
        s31=-1;
        s32=-1;
        s41=-1;
        s42=-1;
        s51=-1;
        s52=-1;

        ts1_1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if((event.getCode() ==KeyCode.TAB))
                {
                    pruefekleinesergebnis1();
                }
            }
        });
        ts1_2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if((event.getCode() ==KeyCode.TAB))
                {
                    pruefekleinesergebnis1();
                }
            }
        });
        ts2_1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if((event.getCode() ==KeyCode.TAB))
                {
                    pruefekleinesergebnis2();
                }
            }
        });
        ts2_2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if((event.getCode() ==KeyCode.TAB))
                {
                    pruefekleinesergebnis2();
                }
            }
        });
        ts3_1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if((event.getCode() ==KeyCode.TAB))
                {
                    pruefekleinesergebnis3();
                }
            }
        });
        ts3_2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if((event.getCode() ==KeyCode.TAB))
                {
                    pruefekleinesergebnis3();
                }
            }
        });

//
      /*  try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_OK");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_OK.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Abbbruch");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        //btn_Abbbruch.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Zurueckziehen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        // btn_Zurueckziehen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_Verlegen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        //btn_Verlegen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_schitzzettel");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        //btn_schitzzettel.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("eins_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        eins_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("zwei_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        zwei_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("drei_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        drei_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("vier_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        vier_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("fuenf_satz");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        fuenf_s.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_heim");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        l_heim.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_gast");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        l_gast.setText(titel);*/


        if(sp!=null && sp.getStatus()==1)
        {
            System.out.println("ausstehendes Spiel");
        }
        if(sp!=null && sp.getStatus()==2)
        {
            System.out.println("aktives Spiel");
        }
        if(sp!=null && sp.getStatus()==3)
        {
            System.out.println("gespieltes Spiel");

            int[] ergebnisArray =auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis().getErgebnisArray() ;
            ts1_1.setText(String.valueOf(ergebnisArray[0]));
            ts1_2.setText(String.valueOf(ergebnisArray[1]));
            ts2_1.setText(String.valueOf(ergebnisArray[2]));
            ts2_2.setText(String.valueOf(ergebnisArray[3]));
            ts3_1.setText(String.valueOf(ergebnisArray[4]));
            ts3_2.setText(String.valueOf(ergebnisArray[5]));
        }




        //Listener für Textboxen
        if(auswahlklasse.getSpielAuswahlErgebniseintragen()!=null)
        {
            l_gast.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getGast().toString());
            l_heim.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getHeim().toString());

            if(auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis()!=null)
            {
                erg = auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis();
                System.out.println(erg);
            }

        }

        ts1_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts1_1.equals("")&&ts1_1.getText().length()>0) {
                try {
                    s11 = Integer.parseInt(ts1_1.getText());
                    setzeErgebnis();


                    satz1Gueltigkeit();

                } catch (Exception e) {
                    e.printStackTrace();
                    zeigeRedCross1();
                }
            }

        });
        ts1_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts1_2.equals("")&&ts1_2.getText().length()>0) {

                try {
                    s12 = Integer.parseInt(ts1_2.getText());
                    setzeErgebnis();

                    satz1Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross1();
                    e.printStackTrace();
                }}
        });
        ts2_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts2_1.equals("")&&ts2_1.getText().length()>0) {
                try {
                    s21 = Integer.parseInt(ts2_1.getText());
                    setzeErgebnis();

                    satz2Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross2();
                    e.printStackTrace();
                }
            }

        });
        ts2_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts2_2.equals("")&&ts2_2.getText().length()>0) {
                try {
                    s22 = Integer.parseInt(ts2_2.getText());
                    setzeErgebnis();

                    satz2Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross2();
                    e.printStackTrace();
                }
            }
        });
        ts3_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts3_1.equals("")&&ts3_1.getText().length()>0) {
                try {
                    s31 = Integer.parseInt(ts3_1.getText());
                    setzeErgebnis();

                    satz3Gueltigkeit();
                } catch (Exception e) {
                    zeigeRedCross3();
                    e.printStackTrace();
                }
            }
        });
        ts3_2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts3_2.equals("")&&ts3_2.getText().length()>0) {
                try {
                    s32 = Integer.parseInt(ts3_2.getText());
                    setzeErgebnis();

                    satz3Gueltigkeit();

                } catch (Exception e) {
                    zeigeRedCross3();
                    e.printStackTrace();
                }
            }
        });

    }

    private void satz3Gueltigkeit() {
        if(s32>-1&&s31>-1)
        {
            if(!gueltigesErgebnis(s31,s32))
            {
                //l_meldungergebnis.setText("Satz 3 prüfen");
                zeigeRedCross3();

            }
            else
            {
                // l_meldungergebnis.setText("Satz 3 OK");
                zeigeGreenCheck3();
            }
        }

    }
    void pruefekleinesergebnis1() {

        if (s11 < 18&&s11>-1&&ts1_2.getText().equals("")) {
            ts1_2.setText("21");
        }
        if (s12 < 18&&s12>-1&&ts1_1.getText().equals("")) {
            ts1_1.setText("21");
        }
        if (s11 > 21&&s11<29&&ts1_2.getText().equals("")) {
            ts1_2.setText(String.valueOf(Integer.parseInt(ts1_1.getText())+2));
        }
        if (s12 > 21&&s12<29&&ts1_1.getText().equals("")) {
            ts1_1.setText(String.valueOf(Integer.parseInt(ts1_2.getText())+2));
        }
    }
    @FXML
    void pruefekleinesergebnis1(MouseEvent event) {

        if (s11 < 18&&s11>-1&&ts1_2.getText().equals("")) {
            ts1_2.setText("21");
        }
        if (s12 < 18&&s12>-1&&ts1_1.getText().equals("")) {
            ts1_1.setText("21");
        }
        if (s11 > 21&&s11<29&&ts1_2.getText().equals("")) {
            ts1_2.setText(String.valueOf(Integer.parseInt(ts1_1.getText())+2));
        }
        if (s12 > 21&&s12<29&&ts1_1.getText().equals("")) {
            ts1_1.setText(String.valueOf(Integer.parseInt(ts1_2.getText())+2));
        }
    }
    @FXML
    void pruefekleinesergebnis2(MouseEvent event) {
        if (s21 < 18&&s21>-1&&ts2_2.getText().equals("")) {
            ts2_2.setText("21");
        }
        if (s22 < 18&&s22>-1&&ts2_2.getText().equals("")) {
            ts2_1.setText("21");
        }
    }
    void pruefekleinesergebnis2() {
        if (s21 < 18&&s21>-1&&ts2_2.getText().equals("")) {
            ts2_2.setText("21");
        }
        if (s22 < 18&&s22>-1&&ts2_2.getText().equals("")) {
            ts2_1.setText("21");
        }
    }
    @FXML
    void pruefekleinesergebnis3(MouseEvent event) {
        if (s31 < 18&&s31>-1&&ts3_2.getText().equals("")) {
            ts3_2.setText("21");
        }
        if (s32 < 18&&s32>-1&&ts3_1.getText().equals("")) {
            ts3_1.setText("21");
        }
    }
    void pruefekleinesergebnis3() {
        if (s31 < 18&&s31>-1&&ts3_2.getText().equals("")) {
            ts3_2.setText("21");
        }
        if (s32 < 18&&s32>-1&&ts3_1.getText().equals("")) {
            ts3_1.setText("21");
        }
    }
    private void satz1Gueltigkeit() {
        if(s11>-1&&s12>-1)
        {
            if(!gueltigesErgebnis(s11,s12))
            {
                //    l_meldungergebnis.setText("Satz 1 prüfen");
                zeigeRedCross1();

            }
            else
            {
                //   l_meldungergebnis.setText("Satz 1 OK");
                zeigeGreenCheck1();
            }
        }
    }

    private void satz2Gueltigkeit() {
        if(s22>-1&&s21>-1)
        {
            if(!gueltigesErgebnis(s21,s22))
            {
                //   l_meldungergebnis.setText("Satz 2 prüfen");
                zeigeRedCross2();

            }
            else
            {
                //   l_meldungergebnis.setText("Satz 2 OK");
                zeigeGreenCheck2();
            }
        }
    }

    private void zeigeGreenCheck1() {
        red_cross_1.setVisible(false);
        green_check_1.setVisible(true);
    }

    private void zeigeRedCross1() {
        red_cross_1.setVisible(true);
        green_check_1.setVisible(false);
    }
    private void zeigeGreenCheck2() {
        red_cross_2.setVisible(false);
        green_check_2.setVisible(true);
    }

    private void zeigeRedCross2() {
        red_cross_2.setVisible(true);
        green_check_2.setVisible(false);
    }
    private void zeigeGreenCheck3() {
        red_cross_3.setVisible(false);
        green_check_3.setVisible(true);
    }

    private void zeigeRedCross3() {
        red_cross_3.setVisible(true);
        green_check_3.setVisible(false);
    }
}
