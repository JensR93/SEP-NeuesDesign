package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import sample.DAO.auswahlklasse;
import sample.Ergebnis;
import sample.Spiel;
import sample.Team;
import sample.Turnier;

import javax.xml.soap.Text;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Dictionary;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.UP;

/**
 * Created by jens on 21.09.2017.
 */
public class SpielErgebnisEintragenController implements Initializable{
    String baseName = "resources.Main";
    String titel ="";

    private boolean satz1FuerHeim = false;
    private boolean satz1FuerGast = false;
    private boolean satz2FuerHeim = false;
    private boolean satz2FuerGast = false;
    private Boolean satz1RechtsGeaendert = false; //Sagt aus, ob bereits der höhere Wert eingetragen wurde;
    private Boolean satz2RechtsGeaendert = false;
    private Boolean satz3rechtsGeaendert = false;
    private Boolean satz1LinksGeaendert = false;
    private Boolean satz2LinksGeaendert = false;
    private Boolean satz3LinksGeaendert = false;

    @FXML
    private JFXTextField ts1_1;
    @FXML
    private JFXTextField ts1_2;
    @FXML
    private JFXTextField ts2_1;
    @FXML
    private JFXTextField ts2_2;
    @FXML
    private JFXTextField ts3_1;
    @FXML
    private JFXTextField ts3_2;
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
    @FXML
    private JFXButton btn_ErgebnisseSpeichern;
    @FXML
    private JFXButton btn_Abbrechen;

    Dictionary<Integer, Spiel> dictspiele = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele();
    Spiel sp ;

    public Spiel getSpiel_update() {
        return spiel_update;
    }

    public void setSpiel_update(Spiel spiel_update) {
        this.spiel_update = spiel_update;
    }

    Spiel spiel_update;
    public Spiel getSp() {
        return sp;
    }

    public void setSp(Spiel sp) {
        this.sp = sp;
    }


    int s11=-1;
    int s12=-1;
    int s21=-1;
    int s22=-1;
    int s31=-1;
    int s32=-1;


    @FXML
    void pressbtn_Abbbruch(ActionEvent event) {
        leereSatzfelder();
        auswahlklasse.getDashboardController().setNodeSpieluebersicht();

    }
    public boolean gueltigesErgebnis(TextField t11, TextField t12){
        int s11 =Integer.valueOf(t11.getText());
        int s12 =Integer.valueOf(t12.getText());

        if(s11<0||s12<0)
        {
            return false;
        }
        if (Math.abs(s11-s12)<2)
        {
            if (!((s11==29 && s12==30)||s11==30 && s12==29)){
                return false;
            }
        }
        if(s11<21&&s12<21)
        {
            return false;
        }
        if (s11>30 || s12>30){
            return false;
        }
        if((s11>21 || s12>21) && Math.abs(s11-s12)>2 ){
            return false;
        }

        return true;
    }


    public void SpracheLaden()
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );

            titel = bundle.getString("ts1_1");
            ts1_1.setPromptText(titel);
            ts1_1.setLabelFloat(true);

            titel = bundle.getString("ts1_2");
            ts1_2.setPromptText(titel);
            ts1_2.setLabelFloat(true);

            titel = bundle.getString("ts2_1");
            ts2_1.setPromptText(titel);
            ts2_1.setLabelFloat(true);

            titel = bundle.getString("ts2_2");
            ts2_2.setPromptText(titel);
            ts2_2.setLabelFloat(true);

            titel = bundle.getString("ts3_1");
            ts3_1.setPromptText(titel);
            ts3_1.setLabelFloat(true);

            titel = bundle.getString("ts3_2");
            ts3_2.setPromptText(titel);
            ts3_2.setLabelFloat(true);

            titel = bundle.getString("btn_ErgebnisseSpeichern");
            btn_ErgebnisseSpeichern.setText(titel);

            titel = bundle.getString("btn_Abbrechen");
            btn_Abbrechen.setText(titel);

            titel = bundle.getString("l_heim");
            l_heim.setText(titel);

            titel = bundle.getString("l_gast");
            l_gast.setText(titel);

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
    }

    @FXML
    void pressbtn_OK(ActionEvent event) {
        //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
        Ergebnis erg = generiereErgebnis();
        if(keinRedCrossVisible() && erg.pruefeErgebnis()) {

            if (sp != null && sp.getStatus() == 0) {
                System.out.println("unvollständiges Spiel");
            }

            else if (sp != null && sp.getStatus() == 2) {
                System.out.println("aktives Spiel");
                if (erg != null) {
                    try {
                        if(bestaetigungsFrameErstellen(erg)) {
                            auswahlklasse.getSpielAuswahlErgebniseintragen().setErgebnis(erg);
                            auswahlklasse.getSpielAuswahlErgebniseintragen().setStatus(3);
                            auswahlklasse.InfoBenachrichtigung("Erfolg", "Ergebnis eingetragen");
                            auswahlklasse.getDashboardController().setNodeSpieluebersicht();
                            auswahlklasse.getSpieluebersichtController().CheckeSpielsuche();
                            auswahlklasse.getSpieluebersichtController().spielInTabelleAuswaehlen(sp);
                            auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielsystem().updateVisualisierung();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // l_meldungergebnis.setText("Satz nicht ausgefüllt");
                    }
                }
            }
            else if (sp != null && sp.getStatus() == 1) {
                System.out.println("ausstehendes Spiel");
            }
            else if (sp != null && sp.getStatus() == 3) {
                if (erg != null) {
                    try {
                        if(bestaetigungsFrameErstellen(erg)) {
                            Spiel spiel =auswahlklasse.getSpielAuswahlErgebniseintragen();
                            spiel.setErgebnis(erg);
                            auswahlklasse.InfoBenachrichtigung("Erfolg", "Ergebnis akutalisiert");
                            auswahlklasse.getDashboardController().setNodeSpieluebersicht();
                            auswahlklasse.getSpieluebersichtController().CheckeSpielsuche();
                            auswahlklasse.getSpieluebersichtController().spielInTabelleAuswaehlen(sp);
                            auswahlklasse.getSpielAuswahlErgebniseintragen().getSpielsystem().updateVisualisierung();
                            LocalTime verfuegbar = LocalTime.now().plusMinutes(auswahlklasse.getEinstellungenController().getPausenzeit());
                            if(spiel.getHeim().getSpielerEins()!=null) {
                                spiel.getHeim().getSpielerEins().setVerfuegbar(verfuegbar);
                            }
                            if(spiel.getHeim().getSpielerZwei()!=null){
                                spiel.getHeim().getSpielerZwei().setVerfuegbar(verfuegbar);
                            }
                            if(spiel.getGast().getSpielerEins()!=null) {
                                spiel.getGast().getSpielerEins().setVerfuegbar(verfuegbar);
                            }
                            if(spiel.getGast().getSpielerZwei()!=null){
                                spiel.getGast().getSpielerZwei().setVerfuegbar(verfuegbar);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // l_meldungergebnis.setText("Satz nicht ausgefüllt");
                    }
                }

            }
        }
        else{
            auswahlklasse.InfoBenachrichtigung("Ungültiges Ergebnis","Bitte korrigieren");
        }
    }

    private boolean bestaetigungsFrameErstellen(Ergebnis ergebnis) {
        Spiel spiel = auswahlklasse.getSpielAuswahlErgebniseintragen();
        String sieger = ergebnis.getSieger(spiel).toString();
        Alert bestaetigung = new Alert(Alert.AlertType.CONFIRMATION);
        bestaetigung.setTitle("Ergebnis eintragen?");
        if (ergebnis.getSieger(spiel)==spiel.getHeim()) {
            bestaetigung.setContentText(sieger + "gewinnt mit " + ergebnis.toString());
        }
        else{
            bestaetigung.setContentText(sieger + "gewinnt mit " + ergebnis.toStringUmgedreht());
        }
        Optional<ButtonType> auswahl = bestaetigung.showAndWait();
        if(auswahl.get() == ButtonType.OK){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean keinRedCrossVisible() {
        if (red_cross_1.isVisible()||red_cross_2.isVisible()||red_cross_3.isVisible()){
            return false;
        }
        if(!green_check_1.isVisible()||!green_check_2.isVisible()){
            return false;
        }
        return true;
    }

    private Ergebnis generiereErgebnis()
    {
        Ergebnis erg=null;
        int s11 = getIntwert(ts1_1);
        int s12 = getIntwert(ts1_2);
        int s21 = getIntwert(ts2_1);
        int s22 = getIntwert(ts2_2);
        int s31 = getIntwert(ts3_1);
        int s32 = getIntwert(ts3_2);
        if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1&&s31!=-1&&s32!=-1)
        {
            erg = new Ergebnis(s11,s12,s21,s22,s31,s32);
        }
        else if(s11!=-1&&s12!=-1&&s21!=-1&&s22!=-1){
            erg = new Ergebnis(s11,s12,s21,s22);
        }
        return erg;
    }
    private int getIntwert(TextField t1){
        int value =-1;
        if(t1.getText().length()>0){
            value = Integer.valueOf(t1.getText());
        }
        else{
            value=-1;
        }
        return value;
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
        auswahlklasse.setSpielErgebnisEintragenController(this);
        ts3_1.setDisable(true);
        ts3_2.setDisable(true);

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


        //Listener für Textboxen on change
        if(auswahlklasse.getSpielAuswahlErgebniseintragen()!=null)
        {
            l_gast.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getGast().toString());
            l_heim.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getHeim().toString());

            if(auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis()!=null)
            {
                //             erg = auswahlklasse.getSpielAuswahlErgebniseintragen().getErgebnis();
                System.out.println("Ergebnis schon vorhanden");
            }
        }

        ts1_1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!ts1_1.equals("")&&ts1_1.getText().length()>0) {
                try {
                    s11 = Integer.parseInt(ts1_1.getText());
                    if(!satz1LinksGeaendert) {
                        satz1RechtsGeaendert=true;
                        fuelleHoeherePunktzahl(s11,ts1_2);
                    }
                    setzeBoolescheWerteSatz1(ts1_1,ts1_2);
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
                    s12 = Integer.parseInt(ts1_1.getText());
                    if(!satz1RechtsGeaendert) {
                        satz1LinksGeaendert=true;
                        fuelleHoeherePunktzahl(s12, ts1_1);

                    }
                    setzeBoolescheWerteSatz1(ts1_1,ts1_2);
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
                    if(!satz2LinksGeaendert) {
                        satz2RechtsGeaendert = true;
                        fuelleHoeherePunktzahl(s21, ts2_2);

                    }
                    setzeBoolescheWerteSatz2(ts2_1,ts2_2);
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
                    if(!satz2RechtsGeaendert) {
                        satz2LinksGeaendert=true;
                        fuelleHoeherePunktzahl(s22, ts2_1);

                    }
                    setzeBoolescheWerteSatz2(ts2_1,ts2_2);
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
                    if(!satz3LinksGeaendert) {
                        satz3rechtsGeaendert=true;
                        fuelleHoeherePunktzahl(s31, ts3_2);

                    }
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
                    if(!satz3rechtsGeaendert) {
                        satz3LinksGeaendert=true;
                        fuelleHoeherePunktzahl(s32, ts3_1);

                    }
                    satz3Gueltigkeit();

                } catch (Exception e) {
                    zeigeRedCross3();
                    e.printStackTrace();
                }
            }
        });

    }

    private void setzeBoolescheWerteSatz1(TextField t1, TextField t2) {
        int satzHeim = Integer.valueOf(t1.getText());
        int satzGast = Integer.valueOf(t2.getText());
        satz1FuerHeim = satzHeim>satzGast;
        satz1FuerGast = satzGast>satzHeim;
        aktiviereSatz3();

    }
    private void setzeBoolescheWerteSatz2(TextField t1, TextField t2) {
        int satzHeim = Integer.valueOf(t1.getText());
        int satzGast = Integer.valueOf(t2.getText());
        satz2FuerHeim = satzHeim>satzGast;
        satz2FuerGast = satzGast>satzHeim;
        aktiviereSatz3();
    }

    private void aktiviereSatz3() {
        if((satz1FuerGast&&satz2FuerHeim) ||(satz2FuerGast&&satz1FuerHeim)){
            ts3_1.setDisable(false);
            ts3_2.setDisable(false);
        }
    }

    private void satz3Gueltigkeit() {
        if(ts3_2.getText().length()>0&&ts3_1.getText().length()>0)
        {
            if(!gueltigesErgebnis(ts3_2,ts3_1))
            {
                zeigeRedCross3();

            }
            else
            {
                zeigeGreenCheck3();
            }
        }
    }

    private void satz1Gueltigkeit() {
        if(ts1_1.getText().length()>0&&ts1_2.getText().length()>0)
        {
            if(!gueltigesErgebnis(ts1_1,ts1_2))
            {
                zeigeRedCross1();
            }
            else
            {
                zeigeGreenCheck1();
            }
        }
    }

    public void allesZuruecksetzen(){
        ts1_1.setText("");
        ts1_2.setText("");
        ts2_1.setText("");
        ts2_2.setText("");
        ts3_1.setText("");
        ts3_2.setText("");
        ts3_1.setDisable(true);
        ts3_2.setDisable(true);
        green_check_1.setVisible(false);
        green_check_2.setVisible(false);
        green_check_3.setVisible(false);
        red_cross_1.setVisible(false);
        red_cross_2.setVisible(false);
        red_cross_3.setVisible(false);
        l_heim.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getHeim().toString());
        l_gast.setText(auswahlklasse.getSpielAuswahlErgebniseintragen().getGast().toString());
    }

    private void fuelleHoeherePunktzahl(int eingabe, TextField zweitesTextField){
        if (eingabe < 20) {
            zweitesTextField.setText("21");
        } else if (eingabe < 29) {
            zweitesTextField.setText((eingabe + 2) + "");
        } else if (eingabe == 29) {
            zweitesTextField.setText("30");
        }
    }

    private void satz2Gueltigkeit() {
        if(ts2_2.getText().length()>0&&ts2_1.getText().length()>0)
        {
            if(!gueltigesErgebnis(ts2_2,ts2_1))
            {
                zeigeRedCross2();
            }
            else
            {
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

    public void fuelleUpdateSpiel() {
        if(spiel_update!=null)
        {
            spiel_update.getErgebnis().getErgebnisArray();


            if(spiel_update.getErgebnis().getErgebnisArray()[0]>0)
            {
                ts1_1.setText(String.valueOf(spiel_update.getErgebnis().getErgebnisArray()[0]));
            }
            if(spiel_update.getErgebnis().getErgebnisArray()[1]>0)
            {
                ts1_2.setText(String.valueOf(spiel_update.getErgebnis().getErgebnisArray()[1]));
            }
            if(spiel_update.getErgebnis().getErgebnisArray()[2]>0)
            {
                ts2_1.setText(String.valueOf(spiel_update.getErgebnis().getErgebnisArray()[2]));
            }
            if(spiel_update.getErgebnis().getErgebnisArray()[3]>0)
            {
                ts2_2.setText(String.valueOf(spiel_update.getErgebnis().getErgebnisArray()[3]));
            }
            if(spiel_update.getErgebnis().getErgebnisArray().length>4) {
                if (spiel_update.getErgebnis().getErgebnisArray()[4] > 0) {
                    ts3_2.setText(String.valueOf(spiel_update.getErgebnis().getErgebnisArray()[4]));
                }
                if (spiel_update.getErgebnis().getErgebnisArray()[5] > 0) {
                    ts3_2.setText(String.valueOf(spiel_update.getErgebnis().getErgebnisArray()[5]));
                }
            }
            //ts1_1.setText();
        }
    }
}




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

