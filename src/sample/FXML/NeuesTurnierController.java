package sample.FXML;

import com.jfoenix.controls.*;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.DAO.*;
import sample.ExcelImport;
import sample.Feld;
import sample.Turnier;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private JFXDatePicker date_doppel;

    @FXML
    private JFXTimePicker time_doppel;

    @FXML
    private JFXDatePicker date_einzel;

    @FXML
    private JFXTimePicker time_einzel;

    @FXML
    private JFXDatePicker date_mixed;

    @FXML
    private JFXTimePicker time_mixed;
    @FXML
    private BigDecimalField AnzahlFelder;
    @FXML
    private Button btn_abbrechen;
    @FXML
    private Button btn_starten;

    //---Sprache---
    @FXML
    private Label Label_Turniername;
    @FXML
    private Label Label_AnzahlFelder;
    @FXML
    private Label Label_StartEinzel;
    @FXML
    private JFXRadioButton Radio_DatumUhrEinzel;
    @FXML
    private JFXRadioButton Radio_AnschlussDisziEinzel;
    @FXML
    private Label Label_Disziplin_Einzel;
    @FXML
    private ChoiceBox Choicebox_Einzel;
    @FXML
    private Label Label_StartDoppel;
    @FXML
    private JFXRadioButton Radio_DatumUhrDoppel;
    @FXML
    private JFXRadioButton Radio_AnschlussDisziDoppel;
    @FXML
    private Label Label_Disziplin_Doppel;
    @FXML
    private ChoiceBox Choicebox_Doppel;
    @FXML
    private Label Label_StartMixed;
    @FXML
    private JFXRadioButton Radio_DatumUhrMixed;
    @FXML
    private JFXRadioButton Radio_AnschlussDisziMixed;
    @FXML
    private Label Label_Disziplin_Mixed;
    @FXML
    private ChoiceBox Choicebox_Mixed;
    @FXML
    private JFXTextField meldegebuehr_einzel;

    @FXML
    private JFXTextField meldegebuehr_doppel;

    public void SpracheLaden() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);

            titel = bundle.getString("Label_Turniername");
            Label_Turniername.setText(titel);

            titel = bundle.getString("Label_AnzahlFelder");
            Label_AnzahlFelder.setText(titel);

            titel = bundle.getString("Label_StartEinzel");
            Label_StartEinzel.setText(titel);

            titel = bundle.getString("Radio_DatumUhrEinzel");
            Radio_DatumUhrEinzel.setText(titel);

            titel = bundle.getString("Radio_AnschlussDisziEinzel");
            Radio_AnschlussDisziEinzel.setText(titel);

            titel = bundle.getString("Label_Disziplin_Einzel");
            Label_Disziplin_Einzel.setText(titel);

            titel = bundle.getString("Label_StartDoppel");
            Label_StartDoppel.setText(titel);

            titel = bundle.getString("Radio_DatumUhrDoppel");
            Radio_DatumUhrDoppel.setText(titel);

            titel = bundle.getString("Radio_AnschlussDisziDoppel");
            Radio_AnschlussDisziDoppel.setText(titel);

            titel = bundle.getString("Label_Disziplin_Doppel");
            Label_Disziplin_Doppel.setText(titel);

            titel = bundle.getString("Label_StartMixed");
            Label_StartMixed.setText(titel);

            titel = bundle.getString("Radio_DatumUhrMixed");
            Radio_DatumUhrMixed.setText(titel);

            titel = bundle.getString("Radio_AnschlussDisziMixed");
            Radio_AnschlussDisziMixed.setText(titel);

            titel = bundle.getString("Label_Disziplin_Mixed");
            Label_Disziplin_Mixed.setText(titel);

        } catch (MissingResourceException e) {
            System.err.println(e);
        }
    }




    LocalDateTime startzeiteinzel;
    LocalDateTime startzeidoppel;
    LocalDateTime startzeitmixed;

    @FXML
    public void Abbrechen(ActionEvent event) throws Exception
    {
        auswahlklasse.getDashboardController().setNodeTurnier();
    }

    @FXML
    public void erstelleTurnier(ActionEvent event) throws Exception {

        LocalDate dateeinzel= date_einzel.getValue();
        LocalDate datedoppel= date_doppel.getValue();
        LocalDate datemixed= date_mixed.getValue();

        LocalTime timeeinzel= time_einzel.getValue();
        LocalTime timedoppel= time_doppel.getValue();
        LocalTime timemixed= time_mixed.getValue();


        try{
            meldegebuehr_einzel.setText(meldegebuehr_einzel.getText().replace(',', '.'));
            meldegebuehr_doppel.setText(meldegebuehr_doppel.getText().replace(',', '.'));
            Float meldegebuehreinzel= Float.parseFloat(meldegebuehr_einzel.getText());
            Float meldegebuehrdoppel= Float.parseFloat(meldegebuehr_doppel.getText());
            TurnierDAO t = new TurnierDAOimpl();
            FeldDAO feldDAO = new FeldDAOimpl();
            int anzahlfelder = Integer.parseInt(AnzahlFelder.getText());
            if (auswahlklasse.getTurnierzumupdaten() == null) {
                Turnier turnier = new Turnier(Turniername.getText(),LocalDateTime.of(dateeinzel,timeeinzel),LocalDateTime.of(datedoppel,timedoppel),LocalDateTime.of(datemixed,timemixed),meldegebuehreinzel,meldegebuehrdoppel);
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
                auswahlklasse.getTurnierzumupdaten().setStartzeitEinzel(LocalDateTime.of(dateeinzel,timeeinzel));
                auswahlklasse.getTurnierzumupdaten().setStartzeitDoppel(LocalDateTime.of(datedoppel,timedoppel));
                auswahlklasse.getTurnierzumupdaten().setStartzeitMixed(LocalDateTime.of(datemixed,timemixed));
                auswahlklasse.getTurnierzumupdaten().setMeldegebuehrEinzel(meldegebuehreinzel);
                auswahlklasse.getTurnierzumupdaten().setMeldegebuehrDoppel(meldegebuehrdoppel);
                erfolg=turnierDao.update(auswahlklasse.getTurnierzumupdaten());
                if(erfolg)
                    auswahlklasse.InfoBenachrichtigung("Turnier Update",auswahlklasse.getTurnierzumupdaten().getName()+" wurde geupdatet");
                if (!erfolg)
                    auswahlklasse.WarnungBenachrichtigung("Feld fehler", "nicht löschbar");



                auswahlklasse.setTurnierzumupdaten(null);

                ladeTurnierladen();


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            auswahlklasse.WarnungBenachrichtigung("Gebühr hat falsches format","f");
        }

}

    private void ladeTurnierladen() {
        auswahlklasse.getTurnier_ladenController().tabelleReload();
        auswahlklasse.getDashboardController().setNodeTurnier();
    }

    @FXML
    private void radioAuswahl(){
        if(Radio_AnschlussDisziDoppel.isSelected()){
            Label_Disziplin_Doppel.setVisible(true);
            Choicebox_Doppel.setVisible(true);
            date_doppel.setVisible(false);
            time_doppel.setVisible(false);
        }
        if(Radio_DatumUhrDoppel.isSelected()){
            Label_Disziplin_Doppel.setVisible(false);
            Choicebox_Doppel.setVisible(false);
            date_doppel.setVisible(true);
            time_doppel.setVisible(true);
        }

        if(Radio_AnschlussDisziEinzel.isSelected()){
            Label_Disziplin_Einzel.setVisible(true);
            Choicebox_Einzel.setVisible(true);
            date_einzel.setVisible(false);
            time_einzel.setVisible(false);
        }

        if(Radio_DatumUhrEinzel.isSelected()){
            Label_Disziplin_Einzel.setVisible(false);
            Choicebox_Einzel.setVisible(false);
            date_einzel.setVisible(true);
            time_einzel.setVisible(true);
        }

        if(Radio_AnschlussDisziMixed.isSelected()){
            Label_Disziplin_Mixed.setVisible(true);
            Choicebox_Mixed.setVisible(true);
            date_mixed.setVisible(false);
            time_mixed.setVisible(false);
        }

        if(Radio_DatumUhrMixed.isSelected()){
            Label_Disziplin_Mixed.setVisible(false);
            Choicebox_Mixed.setVisible(false);
            date_mixed.setVisible(true);
            time_mixed.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpracheLaden();

        time_einzel.setIs24HourView(true);
        time_doppel.setIs24HourView(true);
        time_mixed.setIs24HourView(true);


        StringConverter <LocalTime> uhrzeit =new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime object) {
                if(object!=null)
                return object.toString();
                else
                    return null;
            }

            @Override
            public LocalTime fromString(String string) {
                return LocalTime.parse(string);
            }
        };
        time_einzel.setConverter(uhrzeit);
        time_doppel.setConverter(uhrzeit);
        time_mixed.setConverter(uhrzeit);

    auswahlklasse.setNeuesTurnierController(this);


        BigDecimal v = new BigDecimal(1);
        AnzahlFelder.setMinValue(v);
        v=new BigDecimal(30);
        AnzahlFelder.setMaxValue(v);
        AnzahlFelder.setText(String.valueOf(1));
        //turnierDatum.setValue(LocalDate.now());
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

             startzeiteinzel = auswahlklasse.getTurnierzumupdaten().getStartzeitEinzel();
             startzeidoppel = auswahlklasse.getTurnierzumupdaten().getStartzeitDoppel();
             startzeitmixed = auswahlklasse.getTurnierzumupdaten().getStartzeitMixed();
             meldegebuehr_einzel.setText(String.valueOf(auswahlklasse.getTurnierzumupdaten().getMeldegebuehrEinzel()));
            meldegebuehr_doppel.setText(String.valueOf(auswahlklasse.getTurnierzumupdaten().getMeldegebuehrDoppel()));
            date_einzel.setValue(startzeiteinzel.toLocalDate());
            time_einzel.setValue(startzeiteinzel.toLocalTime());

            date_doppel.setValue(startzeidoppel.toLocalDate());
            time_doppel.setValue(startzeidoppel.toLocalTime());

            date_mixed.setValue(startzeitmixed.toLocalDate());
            time_mixed.setValue(startzeitmixed.toLocalTime());
            Turniername.setText(auswahlklasse.getTurnierzumupdaten().getName());

        }
    }


}
