package sample.FXML;

import com.jfoenix.controls.*;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import sample.DAO.*;
import sample.Feld;
import sample.Turnier;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import jfxtras.labs.scene.control.BigDecimalField;

public class NeuesTurnierController implements Initializable{

    String baseName = "resources.Main";
    String titel ="";

    String keineAuswahl = "Noch keine Startzeit festgelegt";
    String einzel = "Einzel";
    String doppel = "Doppel";
    String mixed = "Mixed";

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
    private Button btn_abbrechen_NeuesTurnier;
    @FXML
    private Button btn_Speichern_NeuesTurnier;

    //---Sprache---
    @FXML
    private Label Label_Turniername_NeuesTurnier;
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
    @FXML
    private Label Label_Meldegebuehr;
    @FXML
    private AnchorPane Ap_mixed_datetime;
    @FXML
    private AnchorPane Ap_doppel_datetime;
    @FXML
    private AnchorPane Ap_einzel_datetime;
    @FXML
    private AnchorPane Ap_mixed_Choice;
    @FXML
    private AnchorPane Ap_doppel_Choice;
    @FXML
    private AnchorPane Ap_einzel_Choice;

    public void SpracheLaden() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);

            titel = bundle.getString("Label_Turniername_NeuesTurnier");
            Label_Turniername_NeuesTurnier.setText(titel);

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

            titel = bundle.getString("Label_Meldegebuehr");
            Label_Meldegebuehr.setText(titel);

            titel = bundle.getString("meldegebuehr_einzel");
            meldegebuehr_einzel.setPromptText(titel);
            meldegebuehr_einzel.setLabelFloat(true);

            titel = bundle.getString("meldegebuehr_doppel");
            meldegebuehr_doppel.setPromptText(titel);
            meldegebuehr_doppel.setLabelFloat(true);

            titel = bundle.getString("btn_abbrechen_NeuesTurnier");
            btn_abbrechen_NeuesTurnier.setText(titel);

            titel = bundle.getString("btn_Speichern_NeuesTurnier");
            btn_Speichern_NeuesTurnier.setText(titel);

            einzel = bundle.getString("einzel");
            doppel = bundle.getString("doppel");
            mixed = bundle.getString("mixed");
            keineAuswahl = bundle.getString("keineStartZeitFestgelegt");

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

        LocalDate dateeinzel = LocalDate.now();
        LocalTime timeeinzel = LocalTime.now();
        LocalDate datedoppel = LocalDate.now();
        LocalTime timedoppel = LocalTime.now();
        LocalDate datemixed = LocalDate.now();
        LocalTime timemixed = LocalTime.now();
        if (Radio_DatumUhrEinzel.isSelected()) {
            dateeinzel = date_einzel.getValue();
            timeeinzel= time_einzel.getValue();
        }
        if(Radio_DatumUhrDoppel.isSelected()) {
            datedoppel = date_doppel.getValue();
            timedoppel = time_doppel.getValue();
        }
        if(Radio_DatumUhrMixed.isSelected()) {
            datemixed = date_mixed.getValue();
            timemixed= time_mixed.getValue();
        }
        if(!Radio_DatumUhrEinzel.isSelected()){
            if (Choicebox_Einzel.getSelectionModel().getSelectedItem()==keineAuswahl){
                auswahlklasse.WarnungBenachrichtigung("Disziplin auswählen","Bei Startzeit nach anderer Disziplin muss eine Disziplin ausgewählt werden!");
            }
            else if(Choicebox_Einzel.getSelectionModel().getSelectedItem()==doppel){
                dateeinzel = datedoppel;
                timeeinzel = timedoppel.plusMinutes(1);
            }
            else if(Choicebox_Einzel.getSelectionModel().getSelectedItem()==mixed){
                dateeinzel = datemixed;
                timeeinzel = timemixed.plusMinutes(1);
            }
        }
        if(!Radio_DatumUhrDoppel.isSelected()){
            if (Choicebox_Doppel.getSelectionModel().getSelectedItem()==keineAuswahl){
                auswahlklasse.WarnungBenachrichtigung("Disziplin auswählen","Bei Startzeit nach anderer Disziplin muss eine Disziplin ausgewählt werden!");
            }
            else if(Choicebox_Doppel.getSelectionModel().getSelectedItem()==einzel){
                datedoppel = dateeinzel;
                timedoppel = timeeinzel.plusMinutes(1);
            }
            else if(Choicebox_Doppel.getSelectionModel().getSelectedItem()==mixed){
                datedoppel = datemixed;
                timedoppel = timemixed.plusMinutes(1);
            }
        }
        if(!Radio_DatumUhrMixed.isSelected()){
            if (Choicebox_Mixed.getSelectionModel().getSelectedItem()==keineAuswahl){
                auswahlklasse.WarnungBenachrichtigung("Disziplin auswählen","Bei Startzeit nach anderer Disziplin muss eine Disziplin ausgewählt werden!");
            }
            else if(Choicebox_Mixed.getSelectionModel().getSelectedItem()==doppel){
                datemixed = datedoppel;
                timemixed = timedoppel.plusMinutes(1);
            }
            else if(Choicebox_Mixed.getSelectionModel().getSelectedItem()==einzel){
                datemixed = dateeinzel;
                timemixed = timeeinzel.plusMinutes(1);
            }
        }


        try{
            meldegebuehr_einzel.setText(meldegebuehr_einzel.getText().replace(',', '.'));
            meldegebuehr_doppel.setText(meldegebuehr_doppel.getText().replace(',', '.'));
            Float meldegebuehreinzel= Float.parseFloat(meldegebuehr_einzel.getText());
            Float meldegebuehrdoppel= Float.parseFloat(meldegebuehr_doppel.getText());
            TurnierDAO t = new TurnierDAOimpl();
            FeldDAO feldDAO = new FeldDAOimpl();
            int anzahlfelder = Integer.parseInt(AnzahlFelder.getText());
            if (auswahlklasse.getTurnierzumupdaten() == null) {
                startzeiteinzel = LocalDateTime.of(dateeinzel,timeeinzel);
                startzeidoppel = LocalDateTime.of(datedoppel,timedoppel);
                startzeiteinzel = LocalDateTime.of(datemixed,timemixed);
                Turnier turnier = new Turnier(Turniername.getText(),startzeiteinzel,startzeidoppel,LocalDateTime.of(datemixed,timemixed),meldegebuehreinzel,meldegebuehrdoppel);
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
        if(Radio_AnschlussDisziMixed.isSelected()&&Radio_AnschlussDisziDoppel.isSelected()&&Radio_AnschlussDisziEinzel.isSelected()){
            auswahlklasse.WarnungBenachrichtigung("Fehler","Mindestens eine Klasse muss ein Startdatum haben!");
            Radio_DatumUhrEinzel.setSelected(true);
        }
        if(Radio_AnschlussDisziDoppel.isSelected()){
            Label_Disziplin_Doppel.setVisible(true);
            Ap_doppel_Choice.setVisible(true);
            Ap_doppel_Choice.toFront();
            Ap_doppel_datetime.setVisible(false);
        }
        if(Radio_DatumUhrDoppel.isSelected()){
            Label_Disziplin_Doppel.setVisible(false);
            Ap_doppel_Choice.setVisible(false);
            Ap_doppel_datetime.toFront();
            Ap_doppel_datetime.setVisible(true);
        }

        if(Radio_AnschlussDisziEinzel.isSelected()){
            Label_Disziplin_Einzel.setVisible(true);
            Ap_einzel_Choice.setVisible(true);
            Ap_einzel_Choice.toFront();
            Ap_einzel_datetime.setVisible(false);
        }

        if(Radio_DatumUhrEinzel.isSelected()){
            Label_Disziplin_Einzel.setVisible(false);
            Ap_einzel_Choice.setVisible(false);
            Ap_einzel_datetime.toFront();
            Ap_einzel_datetime.setVisible(true);
        }

        if(Radio_AnschlussDisziMixed.isSelected()){
            Label_Disziplin_Mixed.setVisible(true);
            Ap_mixed_Choice.setVisible(true);
            Ap_mixed_Choice.toFront();
            Ap_mixed_datetime.setVisible(false);
        }

        if(Radio_DatumUhrMixed.isSelected()){
            Label_Disziplin_Mixed.setVisible(false);
            Ap_mixed_Choice.setVisible(false);
            Ap_mixed_datetime.toFront();
            Ap_mixed_datetime.setVisible(true);
        }
    }

    @FXML
    private void choiceBoxFuellen(){
        String selectionEinzel = (String) Choicebox_Einzel.getSelectionModel().getSelectedItem();
        if(!Choicebox_Einzel.getItems().contains(keineAuswahl)) {
            Choicebox_Einzel.getItems().add(keineAuswahl);
        }
        String selectionDoppel = (String) Choicebox_Doppel.getSelectionModel().getSelectedItem();
        if(!Choicebox_Doppel.getItems().contains(keineAuswahl)) {
            Choicebox_Doppel.getItems().add(keineAuswahl);
        }
        String selectionMixed = (String) Choicebox_Mixed.getSelectionModel().getSelectedItem();
        if(!Choicebox_Mixed.getItems().contains(keineAuswahl)) {
            Choicebox_Mixed.getItems().add(keineAuswahl);
        }


        //adden der Strings in die ChoiceBox

        if((time_einzel.getValue()!=null&&date_einzel.getValue()!=null)||(Radio_AnschlussDisziEinzel.isSelected()&&(selectionEinzel!=null&&selectionEinzel.length()>0&&!selectionEinzel.equals(keineAuswahl)))){
            Choicebox_Doppel.getItems().remove(keineAuswahl);
            if(!Choicebox_Doppel.getItems().contains(einzel)) {
                Choicebox_Doppel.getItems().add(einzel);
            }
            Choicebox_Mixed.getItems().remove(keineAuswahl);
            if(!Choicebox_Mixed.getItems().contains(einzel)) {
                Choicebox_Mixed.getItems().add(einzel);
            }
        }
        if(time_doppel.getValue()!=null&&date_doppel.getValue()!=null||(Radio_AnschlussDisziDoppel.isSelected()&&(selectionDoppel!=null&&selectionDoppel.length()>0&&!selectionDoppel.equals(keineAuswahl)))){
            Choicebox_Einzel.getItems().remove(keineAuswahl);
            if(!Choicebox_Einzel.getItems().contains(doppel)) {
                Choicebox_Einzel.getItems().add(doppel);
            }
            Choicebox_Mixed.getItems().remove(keineAuswahl);
            if(!Choicebox_Mixed.getItems().contains(doppel)) {
                Choicebox_Mixed.getItems().add(doppel);
            }
        }
        if(time_mixed.getValue()!=null&&date_mixed.getValue()!=null||(Radio_AnschlussDisziMixed.isSelected()&&(selectionMixed!=null&&selectionMixed.length()>0&&!selectionMixed.equals(keineAuswahl)))){
            Choicebox_Einzel.getItems().remove(keineAuswahl);
            if(!Choicebox_Einzel.getItems().contains(mixed)) {
                Choicebox_Einzel.getItems().add(mixed);
            }
            Choicebox_Doppel.getItems().remove(keineAuswahl);
            if(!Choicebox_Doppel.getItems().contains(mixed)) {
                Choicebox_Doppel.getItems().add(mixed);
            }
        }

        //Auswahl von vorher wieder setzen:
        if (selectionEinzel!=null && !selectionEinzel.equals("")) {
            Choicebox_Einzel.getSelectionModel().select(selectionEinzel);
        }
        else{
            Choicebox_Einzel.getSelectionModel().select(keineAuswahl);
        }
        if (selectionDoppel!=null && !selectionDoppel.equals("")) {
            Choicebox_Doppel.getSelectionModel().select(selectionDoppel);
        }
        else{
            Choicebox_Doppel.getSelectionModel().select(keineAuswahl);
        }
        if (selectionMixed!=null && !selectionMixed.equals("")) {
            Choicebox_Mixed.getSelectionModel().select(selectionMixed);
        }
        else{
            Choicebox_Mixed.getSelectionModel().select(keineAuswahl);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Choicebox_Einzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                choiceBoxFuellen();
            }
        });
        Choicebox_Doppel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                choiceBoxFuellen();
            }
        });
        Choicebox_Mixed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                choiceBoxFuellen();
            }
        });
        SpracheLaden();
        time_einzel.setIs24HourView(true);
        time_doppel.setIs24HourView(true);
        time_mixed.setIs24HourView(true);
        choiceBoxFuellen();
        listenerFuerTimePickerHinzufuegen(time_einzel);
        listenerFuerTimePickerHinzufuegen(time_doppel);
        listenerFuerTimePickerHinzufuegen(time_mixed);
        listenerFuerDatePickerHinzufuegen(date_einzel);
        listenerFuerDatePickerHinzufuegen(date_doppel);
        listenerFuerDatePickerHinzufuegen(date_mixed);

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
        AnzahlFelder.setText(String.valueOf(9));
        AnzahlFelder.setPrefHeight(0);
        AnzahlFelder.setMaxHeight(10);
        AnzahlFelder.setPrefWidth(0);
        AnzahlFelder.setMaxWidth(80);
        //turnierDatum.setValue(LocalDate.now());
        if(auswahlklasse.getTurnierzumupdaten()!=null) {
            turnierDao.readFelder_Neu(auswahlklasse.getTurnierzumupdaten());
            btn_Speichern_NeuesTurnier.setText("Update");
            if (auswahlklasse.getTurnierzumupdaten().getFelder().size() > 30) {
                AnzahlFelder.setText(String.valueOf(30));
            }
            if (auswahlklasse.getTurnierzumupdaten().getFelder().size() < 1) {
                AnzahlFelder.setText(String.valueOf(1));
            } else {
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

    private void listenerFuerTimePickerHinzufuegen(JFXTimePicker jfxTimePicker) {
        jfxTimePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(jfxTimePicker.getValue()!=null) {
                try {
                    choiceBoxFuellen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void listenerFuerDatePickerHinzufuegen(JFXDatePicker jfxDatePicker) {
        jfxDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(jfxDatePicker.getValue()!=null) {
                try {
                    choiceBoxFuellen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
