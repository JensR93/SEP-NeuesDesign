package sample.FXML;

import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DAO.*;
import sample.Feld;
import sample.Turnier;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import jfxtras.labs.scene.control.BigDecimalField;

public class NeuesTurnierController implements Initializable{
    String baseName = "resources.Main";
    String titel ="";
    boolean erfolg = false;
    TurnierDAO turnierDao = new TurnierDAOimpl();


    @FXML
    private JFXTextField Turniername;
    @FXML
    private JFXDatePicker turnierDatum;
    @FXML
    private BigDecimalField AnzahlFelder;
    @FXML
    private Button btn_abbrechen;
    @FXML
    private Button btn_starten;



    @FXML
    public void erstelleTurnier(ActionEvent event) throws Exception {

        String date = turnierDatum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate Datum = turnierDatum.getValue();
        System.out.println("Felder:  " + AnzahlFelder.getText());
        System.out.println("Name " + Turniername.getText());
        System.out.println("Datum " + date);
        TurnierDAO t = new TurnierDAOimpl();
        FeldDAO feldDAO = new FeldDAOimpl();
        int anzahlfelder = Integer.parseInt(AnzahlFelder.getText());
        if (auswahlklasse.getTurnierzumupdaten() == null) {
            Turnier turnier = new Turnier(Turniername.getText(), Datum);
            erfolg=t.create(turnier);
            for (int i = 0; i < anzahlfelder; i++) {
                new Feld(turnier);
            }


            if(erfolg) {
                auswahlklasse.InfoBenachrichtigung("Turnier erstellt", turnier.getName() + " wurde erstellt.");
                auswahlklasse.getTurniere().add(turnier);
            }

            else
                auswahlklasse.WarnungBenachrichtigung("Turnier nicht erstellt", turnier.getName() + " wurde nicht erstellt.");
            System.out.println("Erfolg");

            try {
                auswahlklasse.getDashboardController().setNodeTurnier();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            int anzahlturnierfelderalt = auswahlklasse.getTurnierzumupdaten().getFelder().size();
            if (anzahlfelder > anzahlturnierfelderalt) {
                for (int i = anzahlturnierfelderalt; i < anzahlfelder; i++) {
                    new Feld(auswahlklasse.getTurnierzumupdaten());

                }
                auswahlklasse.InfoBenachrichtigung("Felder erstellt", String.valueOf(anzahlfelder - anzahlturnierfelderalt)+" Felder wurden erstellt");
            }
            if (anzahlfelder < anzahlturnierfelderalt)
            {
                boolean erfolg = false;
                for (int i = anzahlfelder; i < anzahlturnierfelderalt; i++) {

                    System.out.println("Lösche Feld");
                    erfolg = feldDAO.deleteFeld(auswahlklasse.getTurnierzumupdaten().getFelder().get(i));

                    if(!erfolg)
                        break;
                }
                if(erfolg)
                    auswahlklasse.InfoBenachrichtigung("Felder gelöscht",String.valueOf(anzahlturnierfelderalt-anzahlfelder)+" Felder wurden gelöscht");
                if (!erfolg)
                    auswahlklasse.WarnungBenachrichtigung("Feld fehler", "nicht löschbar");

            }
            auswahlklasse.getTurnierzumupdaten().setName(Turniername.getText());
            auswahlklasse.getTurnierzumupdaten().setDatum(turnierDatum.getValue());
            erfolg=turnierDao.update(auswahlklasse.getTurnierzumupdaten());
            if(erfolg)
                auswahlklasse.InfoBenachrichtigung("Turnier Update",auswahlklasse.getTurnierzumupdaten().getName()+" wurde geupdatet");
            if (!erfolg)
                auswahlklasse.WarnungBenachrichtigung("Feld fehler", "nicht löschbar");



            auswahlklasse.setTurnierzumupdaten(null);
            if(auswahlklasse.getStagesdict().get("TurnierLaden")!=null) {
                auswahlklasse.getStagesdict().get("TurnierLaden").close();
                auswahlklasse.getStagesdict().remove("TurnierLaden");
            }
            if(auswahlklasse.getStagesdict().get("NeuesTurnier")!=null) {
                auswahlklasse.getStagesdict().get("NeuesTurnier").close();
                auswahlklasse.getStagesdict().remove("NeuesTurnier");
            }

            ladeTurnierladen();


        }
    }

    private void ladeTurnierladen() {
        auswahlklasse.getTurnier_ladenController().tabelleReload();
        auswahlklasse.getDashboardController().setNodeTurnier();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    auswahlklasse.setNeuesTurnierController(this);
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_turniername");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        Turniername.setPromptText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_datum");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        //turnierDatum.setPromptText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_anzahlFelder");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        AnzahlFelder.setPromptText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_abbrechen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_abbrechen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_starten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_starten.setText(titel);

        BigDecimal v = new BigDecimal(1);
        AnzahlFelder.setMinValue(v);
        v=new BigDecimal(30);
        AnzahlFelder.setMaxValue(v);
        AnzahlFelder.setText(String.valueOf(1));
        turnierDatum.setValue(LocalDate.now());
        if(auswahlklasse.getTurnierzumupdaten()!=null)
        {
            turnierDao.readFelder_Neu(auswahlklasse.getTurnierzumupdaten());
            btn_starten.setText("Update");

            //System.out.println(turnierzumupdaten.getFelder());

            if(auswahlklasse.getTurnierzumupdaten().getFelder().size()>30)
            {
                AnzahlFelder.setText(String.valueOf(30));
            }
            if(auswahlklasse.getTurnierzumupdaten().getFelder().size()<1)
            {
                AnzahlFelder.setText(String.valueOf(1));
            }
            else
            {
                AnzahlFelder.setText(String.valueOf(auswahlklasse.getTurnierzumupdaten().getFelder().size()));
            }
            turnierDatum.setValue(auswahlklasse.getTurnierzumupdaten().getDatum());
            Turniername.setText(auswahlklasse.getTurnierzumupdaten().getName());

        }
    }


}
