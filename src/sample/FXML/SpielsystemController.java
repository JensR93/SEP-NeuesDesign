package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.omg.CORBA.INITIALIZE;
import sample.DAO.SetzlisteDAO;
import sample.DAO.SetzlisteDAOimpl;
import sample.DAO.auswahlklasse;
import sample.Spieler;
import sample.Spielklasse;
import sample.Spielsysteme.*;
import sample.Team;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by jens on 20.09.2017.
 */
public class SpielsystemController implements Initializable {

    String baseName = "resources.Main";
    String titel ="";

    //region Deklaration
    TableColumn<Team,Integer> setzplatz = new TableColumn("Setzplatz");
    //Dictionary<Integer, Team> dicttest = new Hashtable<>();
    @FXML
    private Button btn_stoppen;
    @FXML
    private Label label_spiele;
    @FXML
    private Label label_spieler;
    @FXML
    private Label anzahlsetzlistespieler;
    @FXML
    private VBox vbox_allespieler;
    @FXML
    private Tab tabsperst;

    @FXML
    private Menu menu_rlp;
    //Tab1
    @FXML
    private VBox vbox_info;
    @FXML
    private TextField t_suchleistespieler;
    @FXML
    private TextField t_suchleistesetzliste;
    @FXML
    private Label l_meldungsetzliste1;

    @FXML
    private TextField textField_anzahlWeiterkommender;
    @FXML
    private TextField textField_gruppenGroesse;

    @FXML
    private TableView spielsystem_spielerliste_alleSpieler;
    @FXML
    private TableView spielsystem_setzliste;

    //Tab2
    @FXML
    private JFXButton btn_spielklasseStarten;
    @FXML
    private JFXRadioButton radio_gruppe;

    @FXML
    private JFXRadioButton radio_gruppeMitE;

    @FXML
    private JFXRadioButton radio_ko;

    @FXML
    private JFXRadioButton radio_schweizer;

    @FXML
    private JFXRadioButton radio_trostJa;

    @FXML
    private FontAwesomeIconView pfeil_links;

    @FXML
    private FontAwesomeIconView pfeil_rechts;

    @FXML
    private JFXRadioButton radio_trostNein;

    @FXML
    private AnchorPane gruppe;

    @FXML
    private AnchorPane gruppeMitEndrunde;

    @FXML
    private AnchorPane koSystem;

    @FXML
    private AnchorPane schweizerSystem;

    @FXML
    private AnchorPane koTrostRundeJa;

    @FXML
    private AnchorPane koTrostRundeNein;

    @FXML
    private JFXRadioButton radio_platzDreiAusspielen;

    @FXML
    private Text t_spielsystem;

    @FXML
    private Text t_Gruppengröße;

    @FXML
    private Text t_AnzahlWeiterkom;

    @FXML
    private RadioButton rb_Gruppe;

    @FXML
    private RadioButton rb_ko;

    @FXML
    private Text t_Trostrunde;

    @FXML
    private Tab tab_Setzliste;

    @FXML
    private Text t_Endrunde;

    @FXML
    private JFXRadioButton radio_platzDreiAusspielenNein;

    @FXML
    private Text t_Platz3;

    @FXML
    private Text t_AnzahlRunden;

    @FXML
    private JFXTextField textField_anzahlRundenSchweizer;



    @FXML
    private ArrayList<Team> team_setzliste = new ArrayList<>();

    Dictionary<Integer,Spielklasse> turnierauswahlspielklassendict = null;
    Spielklasse ausgewaehlte_spielklasse=  auswahlklasse.getAktuelleSpielklassenAuswahl();
    Spieler spieler_m1=null;
    Spieler spieler_m2=null;
    private static ObservableList<Spieler> obs_spieler = FXCollections.observableArrayList();
    private static ObservableList<Team> obs_setzliste =  FXCollections.observableArrayList();
    private static boolean befuellem1=true;
    private static Team teams = null;
    private SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
    Team team = new Team();

    //endregion
    //Linke Tabelle füllen beim Laden

    private void fuelleobs_setzliste() {
        obs_setzliste.clear();
        Enumeration enumTeams = auswahlklasse.getAktuelleTurnierAuswahl().getTeams().keys();

        while (enumTeams.hasMoreElements()) {
            int key = (int) enumTeams.nextElement();
            Team team = auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key);

            if (team.getSpielklasse().getSpielklasseID()==ausgewaehlte_spielklasse.getSpielklasseID()&&!team.isFreilos()) {
                if (team.toString().toUpperCase().contains(t_suchleistesetzliste.getText().toUpperCase())) {
                    obs_setzliste.add(team);
                }

            }
        }
        pruefeAnzahlRLPItems();
    }

    private void printSpielerSpielklasseHinzuTable() throws Exception {
        boolean weiblich=false;
        boolean maennlich=false;
        boolean alle=false;
        System.out.println(auswahlklasse.getAktuelleTurnierAuswahl());
        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            obs_spieler.clear();
            System.out.println("Anzahl spielklassen = "+auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
            Enumeration enumSpielerIDs = auswahlklasse.getSpieler().keys();
            while (enumSpielerIDs.hasMoreElements()){
                int key = (int)enumSpielerIDs.nextElement();
                Spieler spieler = auswahlklasse.getSpieler().get(key);

                if (!istInSetzListe(spieler)&&spieler.toString()!=""){
                    if(auswahlklasse.getAktuelleSpielklassenAuswahl().toString().toUpperCase().contains("DAMEN"))
                    {

                        if(!spieler.getGeschlecht())
                        {
                            obs_spieler.add(spieler);
                            weiblich=false;
                        }
                        if(obs_spieler.size()==0)
                        {
                            weiblich=true;
                            //auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine weiblichen Spieler gefunden!");
                        }
                    }
                    if(auswahlklasse.getAktuelleSpielklassenAuswahl().toString().toUpperCase().contains("HERREN"))
                    {

                        if(spieler.getGeschlecht())
                        {
                            obs_spieler.add(spieler);
                            maennlich=false;
                        }
                        if(obs_spieler.size()==0)
                        {
                            maennlich=true;
                            //auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine weiblichen Spieler gefunden!");
                        }

                    }
                    if(auswahlklasse.getAktuelleSpielklassenAuswahl().toString().toUpperCase().contains("MIXED"))
                    {
                        if(spieler!=null) {
                            obs_spieler.add(spieler);
                            alle = false;
                        }
                        if(obs_spieler.size()==0)
                        {
                            alle=true;
                            //auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine Spieler gefunden!");
                        }

                    }

                    //sp.getDisziplin().contains("einzel")



                }
            }
            if(weiblich)
            {
                auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine weiblichen Spieler gefunden!");
            }
            if(maennlich)
            {
                auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine männlichen Spieler gefunden!");
            }
            if(alle)
            {
                auswahlklasse.WarnungBenachrichtigung("Keine Spieler", "Es wurden keine Spieler gefunden!");
            }
            TableColumn<Spieler,String> spielerVornameSpalte = new TableColumn("Vorname");
            spielerVornameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("vName"));
            TableColumn<Spieler,String> spielerNachnameSpalte = new TableColumn("Nachname");
            spielerNachnameSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("nName"));
            TableColumn<ImageView,String> spielerGeschlechtSpalte = new TableColumn("Geschlecht");
            spielerGeschlechtSpalte.setCellValueFactory(new PropertyValueFactory<ImageView,String>("iGeschlecht"));
            TableColumn<Spieler,String> spielerVereinSpalte = new TableColumn("Verein");
            spielerVereinSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,String>("verein"));
            TableColumn<Spieler,Date> spielerGeburtsdatumSpalte = new TableColumn("Geburtsdatum");
            spielerGeburtsdatumSpalte.setCellValueFactory(new PropertyValueFactory<Spieler,Date>("gDatum"));
            TableColumn<Spieler,Integer> spielerRLP = new TableColumn("Ranglistenpunkte");
            spielerRLP.setCellValueFactory(new PropertyValueFactory<Spieler,Integer>("RLPanzeigen"));
            spielsystem_spielerliste_alleSpieler.setItems(obs_spieler);
            spielsystem_spielerliste_alleSpieler.getColumns().addAll(spielerVornameSpalte,spielerNachnameSpalte,spielerGeschlechtSpalte,spielerRLP,spielerVereinSpalte,spielerGeburtsdatumSpalte);
        }
        else{
            System.out.println("kann Spielerliste nicht laden");
        }
    }
    private boolean istInSetzListe(Spieler spieler){
        for (int i=0;i<obs_setzliste.size();i++){
            if (obs_setzliste.get(i).istImTeam(spieler)){
                return true;
            }
        }
        return false;
    }
    @FXML
    private void pressbtn_spielklasseStarten(ActionEvent event){
        if(ausgewaehlte_spielklasse.getSetzlistedict().size()==0)
        {
            System.out.println("Spielklasse_Setzliste wird erstellt");
            for(int i=0;i<obs_setzliste.size();i++)
            {
                setzlisteDAO.create(i+1,obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
                ausgewaehlte_spielklasse.getSetzlistedict().put(obs_setzliste.get(i),i+1);
                ausgewaehlte_spielklasse.getSetzliste().add(obs_setzliste.get(i));
            }
        }
        if(ausgewaehlte_spielklasse.getSetzliste().size()>0&&ausgewaehlte_spielklasse.getSpiele().size()<=0)
        {


            if(radio_gruppe.isSelected()){
                gruppenSystemStarten();
            }
            if(radio_ko.isSelected()){
                koSystemStarten();
            }
            if (radio_gruppeMitE.isSelected()){
                gruppeMitEndrundeStarten();
            }
            if(radio_schweizer.isSelected()){
                schweizerSystemStarten();
            }
        }
        auswahlklasse.getSpieluebersichtController().CheckeSpielsuche();
        auswahlklasse.getDashboardController().setNodeSpieluebersicht();
        auswahlklasse.getVisualisierungController().klassenTabsErstellen();
    }

    private void schweizerSystemStarten() {
        try{
            int anzahlRunden = Integer.valueOf(textField_anzahlRundenSchweizer.getText());
            SchweizerSystem schweizerSystem = new SchweizerSystem(anzahlRunden,ausgewaehlte_spielklasse.getSetzliste(),ausgewaehlte_spielklasse);
            ausgewaehlte_spielklasse.setSpielsystem(schweizerSystem);
            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
            //TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());
        }
        catch (NumberFormatException e){
            System.out.println("Bitte nur zahlen eintragen");
        }
        catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    private void gruppeMitEndrundeStarten() {
        try{
            int gruppenGroesse = Integer.valueOf(textField_gruppenGroesse.getText());
            int anzahlGruppen = (int) Math.ceil((double)ausgewaehlte_spielklasse.getSetzliste().size()/gruppenGroesse);
            System.out.println("Math.ceil: " + (int) Math.ceil(ausgewaehlte_spielklasse.getSetzliste().size()/gruppenGroesse));
            int anzahlWeiterkommender = Integer.valueOf(textField_anzahlWeiterkommender.getText());
            if(rb_Gruppe.isSelected()) {
                GruppeMitEndrunde gruppeMitEndrunde = new GruppeMitEndrunde(ausgewaehlte_spielklasse, anzahlGruppen, anzahlWeiterkommender,false);
                ausgewaehlte_spielklasse.setSpielsystem(gruppeMitEndrunde);
            }
            else{
                GruppeMitEndrunde gruppeMitEndrunde = new GruppeMitEndrunde(ausgewaehlte_spielklasse, anzahlGruppen, anzahlWeiterkommender,true);
                ausgewaehlte_spielklasse.setSpielsystem(gruppeMitEndrunde);
            }
            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
            //TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());
        }
        catch (NumberFormatException e){
            System.out.println("Bitte nur zahlen eintragen");
        }
        catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    private void gruppenSystemStarten() {
        Gruppe gruppe = new Gruppe (ausgewaehlte_spielklasse.getSetzliste(),ausgewaehlte_spielklasse);
        try {
            ausgewaehlte_spielklasse.setSpielsystem( gruppe);

            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
            //TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());


            //a.getStages().get(0).close();

            //a.getStages().get(2).close();

        } catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    private void koSystemStarten() {
        boolean platzDreiAusspielen = radio_platzDreiAusspielen.isSelected() ;
        Spielsystem ko = new KO(ausgewaehlte_spielklasse.getSetzliste(),ausgewaehlte_spielklasse, platzDreiAusspielen);
        try {

            ausgewaehlte_spielklasse.setSpielsystem(ko);

            l_meldungsetzliste1.setText("ERFOLG");
            auswahlklasse.InfoBenachrichtigung("Spielsystem start","Das Spielsystem wurde erfolgreich gestartet");
           // TurnierladenController t = new TurnierladenController("Badminton Turnierverwaltung - "+auswahlklasse.getAktuelleTurnierAuswahl().getName());


            //a.getStages().get(0).close();

            //a.getStages().get(2).close();

        } catch (Exception e) {
            l_meldungsetzliste1.setText("Fehlschlag");
            auswahlklasse.InfoBenachrichtigung("Fehler","Das Spielsystem konnte nicht erfolgreich gestartet werden");
            e.printStackTrace();
        }
    }

    @FXML
    private void trostSwitch(ActionEvent event) throws IOException {
        if(radio_trostNein.isSelected()){
            koTrostRundeNein.setVisible(true);
            koTrostRundeJa.setVisible(false);
        }
        else{
            koTrostRundeNein.setVisible(false);
            koTrostRundeJa.setVisible(true);
        }
    }

    @FXML
    void erstelleSetzlisteNormal(ActionEvent event) {
        t_suchleistesetzliste.setText("");
        //System.out.println(obs_setzliste);
        SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
        boolean erfolg=false;
        for(int i=0;i<obs_setzliste.size();i++)
        {

            if(obs_setzliste.get(i).getSetzplatz()>0)
            {


/*                    auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));
                    auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i),obs_setzliste.get(i).getSetzplatz());
                    erfolg= setzlisteDAO.create(obs_setzliste.get(i).getSetzplatz(),obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());*/


            }
            if(obs_setzliste.get(i).getSetzplatz()==0)
            {
/*                erfolg= setzlisteDAO.create(i+1,obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i),i+1);
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));*/
                obs_setzliste.get(i).setSetzplatz(i+1);
                obs_setzliste.get(i).getTeamDAO().update(obs_setzliste.get(i));
            }
        }
/*        if(erfolg)
        {
            auswahlklasse.InfoBenachrichtigung("Erfolg","Erfolg");
            auswahlklasse.getAktuelleSpielklassenAuswahl().setSetzliste_gesperrt(true);
            //pruefeSperrungSetzliste();
        }
        if(!erfolg)
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler","Fehler");
        }*/
        fuelleobs_setzliste();
        sortiereTabelleSetzliste();
        pruefeSperrungSetzliste();
    }

    @FXML
    void erstelleSetzlisteZufall(ActionEvent event) {
        t_suchleistesetzliste.setText("");

        SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
        boolean erfolg=false;
        ArrayList <Team> teamsohnesetzplatz=new ArrayList<>();
        ArrayList <Team> teamsohnesetzplatz_random=new ArrayList<>();
        int index=1;

        for(int i=0;i<obs_setzliste.size();i++)
        {
            System.out.println(i);

            if (obs_setzliste.get(i).getSetzplatz() > 0) {
/*                erfolg = setzlisteDAO.create(obs_setzliste.get(i).getSetzplatz(), obs_setzliste.get(i), obs_setzliste.get(i).getSpielklasse());

                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i), i + 1);
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));*/
                index++;
            }
            if (obs_setzliste.get(i).getSetzplatz() == 0) {
                teamsohnesetzplatz.add(obs_setzliste.get(i));

                //erfolg= setzlisteDAO.create(i+1,obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
            }
        }


        teamsohnesetzplatz.sort(new Comparator<Team>() {
            @Override
            public int compare(Team z1, Team z2) {
                if (z1.getRLPanzeigen() > z2.getRLPanzeigen())
                    return -1;
                if (z1.getRLPanzeigen() < z2.getRLPanzeigen())
                    return 1;
                return 0;
            }
        });

        Collections.shuffle(teamsohnesetzplatz);
        for(int i=0;i<teamsohnesetzplatz.size();i++)
        {
/*            erfolg=setzlisteDAO.create(index,teamsohnesetzplatz.get(i),teamsohnesetzplatz.get(i).getSpielklasse());

            auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(teamsohnesetzplatz.get(i),index);
            auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(teamsohnesetzplatz.get(i));*/
            teamsohnesetzplatz.get(i).setSetzplatz(index);
            teamsohnesetzplatz.get(i).getTeamDAO().update(teamsohnesetzplatz.get(i));
            index++;
        }


/*        if(erfolg)
        {
            auswahlklasse.InfoBenachrichtigung("Erfolg","Erfolg");
            auswahlklasse.getAktuelleSpielklassenAuswahl().setSetzliste_gesperrt(true);
            // pruefeSperrungSetzliste();
        }
        if(!erfolg)
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler","Fehler");
        }*/
        fuelleobs_setzliste();
        sortiereTabelleSetzliste();
        pruefeSperrungSetzliste();
    }

    void erstelleSetzlisteRLPAnzahlSpieler(int anzahlspieler) {
        t_suchleistesetzliste.setText("");

        SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
        boolean erfolg=false;
        ArrayList <Team> teamsohnesetzplatz=new ArrayList<>();
        ArrayList <Team> teamsohnesetzplatz_random=new ArrayList<>();
        int index=1;

        for(int i=0;i<obs_setzliste.size();i++)
        {
            System.out.println(i);

            if (obs_setzliste.get(i).getSetzplatz() > 0) {
/*                erfolg = setzlisteDAO.create(obs_setzliste.get(i).getSetzplatz(), obs_setzliste.get(i), obs_setzliste.get(i).getSpielklasse());

                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(obs_setzliste.get(i), i + 1);
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(obs_setzliste.get(i));*/
                index++;
            }
            if (obs_setzliste.get(i).getSetzplatz() == 0) {
                teamsohnesetzplatz.add(obs_setzliste.get(i));

                //erfolg= setzlisteDAO.create(i+1,obs_setzliste.get(i),obs_setzliste.get(i).getSpielklasse());
            }
        }


        teamsohnesetzplatz.sort(new Comparator<Team>() {
            @Override
            public int compare(Team z1, Team z2) {
                if (z1.getRLPanzeigen() > z2.getRLPanzeigen())
                    return -1;
                if (z1.getRLPanzeigen() < z2.getRLPanzeigen())
                    return 1;
                return 0;
            }
        });


        for(int i=0;i<teamsohnesetzplatz.size();i++)
        {
            if(i<anzahlspieler&&teamsohnesetzplatz.get(i)!=null)
            {
/*                erfolg=setzlisteDAO.create(index,teamsohnesetzplatz.get(i),teamsohnesetzplatz.get(i).getSpielklasse());

                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(teamsohnesetzplatz.get(i),index);
                auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(teamsohnesetzplatz.get(i));*/
                teamsohnesetzplatz.get(i).setSetzplatz(index);
                teamsohnesetzplatz.get(i).getTeamDAO().update(teamsohnesetzplatz.get(i));
                index++;
            }
            else
            {
                if(teamsohnesetzplatz.get(i)!=null)
                {
                    teamsohnesetzplatz_random.add(teamsohnesetzplatz.get(i));
                }

            }
        }

        Collections.shuffle(teamsohnesetzplatz_random);
        for (int i=0;i<teamsohnesetzplatz_random.size();i++)
        {
/*            erfolg=setzlisteDAO.create(index,teamsohnesetzplatz_random.get(i),teamsohnesetzplatz_random.get(i).getSpielklasse());

            auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().put(teamsohnesetzplatz_random.get(i),index);
            auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().add(teamsohnesetzplatz_random.get(i));*/
            index++;
        }
/*        if(erfolg)
        {
            auswahlklasse.InfoBenachrichtigung("Erfolg","Erfolg");
            auswahlklasse.getAktuelleSpielklassenAuswahl().setSetzliste_gesperrt(true);
            //pruefeSperrungSetzliste();
        }
        if(!erfolg)
        {
            auswahlklasse.WarnungBenachrichtigung("Fehler","Fehler");
        }*/
        fuelleobs_setzliste();
        sortiereTabelleSetzliste();
        pruefeSperrungSetzliste();
    }
    @FXML
    private void klassenSwitch(ActionEvent event) throws IOException, InterruptedException {

        if(radio_gruppe.isSelected()) {
            gruppe.toFront();
            gruppe.setVisible(true);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(false);
        }

        else if(radio_gruppeMitE.isSelected()) {
            gruppeMitEndrunde.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(true);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(false);
        }

        else if(radio_ko.isSelected()){
            koSystem.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(true);
            schweizerSystem.setVisible(false);
        }

        else if(radio_schweizer.isSelected()){
            schweizerSystem.toFront();
            gruppe.setVisible(false);
            gruppeMitEndrunde.setVisible(false);
            koSystem.setVisible(false);
            schweizerSystem.setVisible(true);
        }
        else{
            //label1.setText("");
            //label2.setText("");
            //label3.setText("");
            //hbox_1.getChildren().clear();
            //hbox_2.getChildren().clear();
        }
    }



    private void printSpielerSpielklasseVorhandenTable() throws Exception {

        if(auswahlklasse.getAktuelleTurnierAuswahl()!=null) {
            turnierauswahlspielklassendict = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen();

            obs_setzliste.clear();
            //for (int i=0;i<turnierauswahlspielklassendict.size();i++){ //Warum?
            //System.out.println(ausgewaehlte_spielklasse.getSpiele().get(i));
            //obsvorhanden_spieler.add(ausgewaehlte_spielklasse.getSetzliste().get(i-1));

            System.out.println("ausgewählte Spielklasse = "+ausgewaehlte_spielklasse.getSpielklasseID()+" "+ausgewaehlte_spielklasse.getDisziplin());
            fuelleobs_setzliste();
            //}

            setzplatz.setCellValueFactory(new PropertyValueFactory<Team,Integer>("Setzplatz"));
            setzplatz.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

            if(!ausgewaehlte_spielklasse.isSetzliste_gesperrt())
            {
                spielsystem_setzliste.setEditable(true);
            }

            setzplatz.setEditable(true);
            setzplatz.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Team, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Team, Integer> event) {

                }
            });
            setzplatz.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Team, Integer>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Team, Integer> event) {
                    Team t = (Team) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    try
                    {
                        event.getNewValue();
                    }
                    catch (Exception e)
                    {
                        event.getTableView().getItems().set(event.getTablePosition().getRow(), t);
                        return;
                    }
                    if (!event.getNewValue().equals(event.getOldValue())&&event.getNewValue() > 0 && event.getNewValue() <= obs_setzliste.size())
                    {
                        boolean neuerwertleer=true;
                        int freiersetzplatz=-1;
                        boolean erfolg=false;
                        SetzlisteDAO setzlisteDAO=new SetzlisteDAOimpl();
                        boolean doppelt = false;
                        List<Team> list = FXCollections.observableList(obs_setzliste);
                        for(int i=0;i<list.size();i++) {
                            if (list.get(i) != null) {
                                boolean loeschen = false;


                                if (!doppelt && list.get(i).getSetzplatz() == event.getNewValue()) {
                                    doppelt = true;
                                    System.out.println("Doppelter Sezuplatz gefunden");
                                    i = 0;
                                }


                                Team to = event.getTableView().getItems().get(event.getTablePosition().getRow());

                                if (!doppelt&&list.get(i).getSetzplatz() < event.getNewValue() && list.get(i).getSetzplatz() != i + 1) {
                                    System.out.println("Kleinerer Setzplatz gefunden");
                                    if(freiersetzplatz==-1||freiersetzplatz>i)
                                    {
                                        freiersetzplatz = i;
                                        i=0;
                                    }

                                }
                                if(list.get(i).getSetzplatz()==freiersetzplatz+1&&event.getOldValue()!=0)
                                {
                                    freiersetzplatz=-2;
                                }
                                if(list.get(i).getSetzplatz()==event.getNewValue())
                                {
                                    neuerwertleer=false;
                                }


                                if (doppelt && list.get(i).getSetzplatz() >= event.getNewValue()&&event.getOldValue()==0) {
                                    System.out.println("Setzplatz wird verschoben, alt = 0 "+list.get(i));
                                    list.get(i).setSetzplatz(list.get(i).getSetzplatz() + 1);
                                    list.get(i).getTeamDAO().update(list.get(i));
                                    freiersetzplatz=-1;
                                }
                                if (doppelt && list.get(i).getSetzplatz()>event.getOldValue()&&list.get(i).getSetzplatz()<=event.getNewValue()&&event.getOldValue()!=0) {
                                    System.out.println("Setzplatz größer als alter, aber kleiner als neuer "+list.get(i));
                                    list.get(i).setSetzplatz(list.get(i).getSetzplatz() - 1);
                                    list.get(i).getTeamDAO().update(list.get(i));
                                    freiersetzplatz=-1;
                                }
                                if (doppelt && list.get(i).getSetzplatz()>=event.getNewValue()&&list.get(i).getSetzplatz()<event.getOldValue()&&event.getOldValue()!=0)
                                {
                                    System.out.println("Setzplatz kleiner als alter, aber größer als neuer "+list.get(i));
                                    list.get(i).setSetzplatz(list.get(i).getSetzplatz() + 1);
                                    list.get(i).getTeamDAO().update(list.get(i));
                                    freiersetzplatz=-1;
                                }

                            }
                        }
                        if(freiersetzplatz>-1&&event.getOldValue()==0)
                        {
                            System.out.println("freiersetzplatz"+freiersetzplatz);
                            t.setSetzplatz(freiersetzplatz+1);
                        }

                        else if(neuerwertleer)
                        {

                            int altersetzplatz=t.getSetzplatz();
                            boolean keinsetzplatz=false;
                            if(t.getSetzplatz()==0)
                            {
                                keinsetzplatz=true;
                            }

                            if(!keinsetzplatz) {
                                for (int i = altersetzplatz - 1; i < obs_setzliste.size(); i++) {
                                    if (obs_setzliste.get(i).getSetzplatz() > 0) {
                                        //System.out.println("Senke Setzplatz um 1");
                                        //System.out.println(obs_setzliste.get(i).getSetzplatz() + "          -1");
                                        obs_setzliste.get(i).setSetzplatz(obs_setzliste.get(i).getSetzplatz() - 1);
                                        obs_setzliste.get(i).getTeamDAO().update(obs_setzliste.get(i));
                                    }
                                }
                            }

                            if(freiersetzplatz>-1)
                            {
                                t.setSetzplatz(freiersetzplatz);
                            }
                            else {
                                t.setSetzplatz(event.getNewValue());
                            }

                        }
                        else if(!erfolg)
                        {
                            t.setSetzplatz(event.getNewValue());
                        }


                        t.getTeamDAO().update(t);

/*                        if(erfolg)
                        {
                            t.setSetzplatz(event.getNewValue());
                            obs_setzliste.add(t);
                            t.getTeamDAO().update(t);
                            System.out.println(obs_setzliste);
                            sortiereTabelleSetzliste();

                        }*/




                       /* if(!event.getOldValue().equals(""))
                        {
                            int alterplatz = Integer.parseInt(event.getOldValue());
                            ausgewaehlte_spielklasse.removeSetzlistedict(Integer.parseInt(event.getOldValue()), t);
                            System.out.println("tttt");
                            ausgewaehlte_spielklasse.addSetzlistedict(Integer.parseInt(event.getNewValue()), alterplatz, t);
                            System.out.println(event.getNewValue());
                            TurnierDAO turnierDAO = new TurnierDAOimpl();

                            System.out.println(t + "wurde bearbeitet");
                            fuelleobs_setzliste();
                            sortiereTabelleSetzliste();
                            spielsystem_setzliste.refresh();
                            spielsystem_setzliste.getSelectionModel().select(t);
                        }
                        else
                        {

                        }*/

                    }
                    if(event.getNewValue()==0)
                    {
                        setzplatzZuruecksetzen(t);
                    }
                    else
                    {

                        Team to = event.getTableView().getItems().get(event.getTablePosition().getRow());


                        event.getTableView().getItems().set(event.getTablePosition().getRow(), to);





                        auswahlklasse.WarnungBenachrichtigung("Wertebereich","Bitte nur Zahlen zwischen 1-"+obs_setzliste.size()+" eingeben");
                    }
                    sortiereTabelleSetzliste();
                }
            });





            TableColumn<Team,String> spielerEinsSpalte = new TableColumn("Spieler");
            TableColumn<Team,String> spielerZweiSpalte = new TableColumn("Partner");
            if(ausgewaehlte_spielklasse.toString().toUpperCase().contains("MIX"))
            {
                spielerEinsSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerEins_MIX"));
                spielerZweiSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerZwei_MIX"));
            }
            else
            {
                spielerEinsSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerEins"));
                spielerZweiSpalte.setCellValueFactory(new PropertyValueFactory<Team,String>("SpielerZwei"));
            }

            TableColumn<Team,Integer> RLPSpalte = new TableColumn("RLP");
            RLPSpalte.setCellValueFactory(new PropertyValueFactory<Team,Integer>("RLPanzeigen"));


            spielsystem_setzliste.setItems(obs_setzliste);
            System.out.println("einzel = "+ausgewaehlte_spielklasse.isEinzel());
            System.out.println("Spielklasse = "+ausgewaehlte_spielklasse.getDisziplin());
            if (ausgewaehlte_spielklasse.isEinzel()){
                spielsystem_setzliste.getColumns().addAll(setzplatz,spielerEinsSpalte,RLPSpalte);
            }
            else{
                spielsystem_setzliste.getColumns().addAll(setzplatz,spielerEinsSpalte,spielerZweiSpalte,RLPSpalte);
            }

        }
        else{
            System.out.println("kann Setzliste nicht laden");
            l_meldungsetzliste1.setText("kann Setzliste nicht laden");
        }

    }


    @FXML
    void verschiebevonSetzliste(MouseEvent event) {

        markierteTeamszurSpielerliste();
    }
    @FXML
    void verschiebevonSpielerliste(MouseEvent event) {

        markierteSpielerzurSetzliste();
    }
    private void removeTeam(Team team)
    {
        int altersetzplatz=team.getSetzplatz();

        //System.out.println(team.toStringKomplett());
        obs_setzliste.remove(team);
        if(ausgewaehlte_spielklasse.isEinzel()) {
            obs_spieler.addAll(team.getSpielerEins());
        }
        else {
            obs_spieler.addAll(team.getSpielerEins(),team.getSpielerZwei());
        }


        boolean keinsetzplatz=false;
        if(team.getSetzplatz()==0)
        {
            keinsetzplatz=true;
        }

        setzlisteDAO.deleteSetzplatz(ausgewaehlte_spielklasse.getSpielklasseID(),team.getTeamid());
        team.getTeamDAO().delete(team);
        obs_setzliste.remove(team);
        auswahlklasse.getAktuelleTurnierAuswahl().getTeams().remove(team.getTeamid());
        if(!keinsetzplatz) {
            for (int i = altersetzplatz - 1; i < obs_setzliste.size(); i++) {
                if (obs_setzliste.get(i).getSetzplatz() > 0) {
                    //System.out.println("Senke Setzplatz um 1");
                    //System.out.println(obs_setzliste.get(i).getSetzplatz() + "          -1");
                    obs_setzliste.get(i).setSetzplatz(obs_setzliste.get(i).getSetzplatz() - 1);
                    obs_setzliste.get(i).getTeamDAO().update(obs_setzliste.get(i));
                }
            }
        }


    }




    private void sortiereTabelleSetzliste() {
        obs_setzliste.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if(o1.getSetzplatzString2().equals("")&&o2.getSetzplatzString2().equals(""))
                {
                    return 0;
                }
                else if(o1.getSetzplatzString2().equals(""))
                {
                    return 1000000 - Integer.parseInt(o2.getSetzplatzString2());
                }
                else if(o2.getSetzplatzString2().equals(""))
                {
                    return Integer.parseInt(o1.getSetzplatzString2())-1000000;
                }
                else
                {
                    return Integer.parseInt(o1.getSetzplatzString2())- Integer.parseInt(o2.getSetzplatzString2());
                }

            }
        });
        spielsystem_setzliste.setItems(obs_setzliste);
        /*setzplatz.setSortType(TableColumn.SortType.ASCENDING);
        spielsystem_setzliste.getSortOrder().add(setzplatz);*/
        spielsystem_setzliste.refresh();
    }
    private void
    addSpieler(Spieler spielerneu)
    {

//        System.out.println(spielerneu.getNName());
        obs_spieler.remove(spielerneu);
        if(befuellem1) {
            if(ausgewaehlte_spielklasse.isEinzel()){
                team = new Team(spielerneu,ausgewaehlte_spielklasse);
                obs_setzliste.add(team);
                //dicttest.put(dicttest.size(),team);
                //ausgewaehlte_spielklasse.addSetzliste(team);

/*  Setzliste DAO wird benötigt
               boolean erfolg = setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size(),team,ausgewaehlte_spielklasse);
                if(erfolg) {
                    //dicttest.put(team.getTeamid(),team)
                    l_meldungsetzliste1.setText(team.getSpielerEins().getVName() + " " + team.getSpielerEins().getNName() + " wurde der Setzliste hinzugefügt!");
                }
                else
                {
                    l_meldungsetzliste1.setText("fehler");
                }*/
            }

            else{
                team = new Team(spielerneu,ausgewaehlte_spielklasse,false);
                System.out.println("doppelklasse");
                befuellem1=false;
                obs_setzliste.add(team);
                //dicttest.put(dicttest.size(),team);


            }

        }
        else
        {

            // team.setTeamid(ausgewaehlte_spielklasse.getSetzliste());
            team.addSpieler(spielerneu);

            team.getTeamDAO().create(team);


            befuellem1=true;
            //System.out.println(ausgewaehlte_spielklasse.getSetzliste().size()+1+"-------------");

            //ausgewaehlte_spielklasse.addSetzliste(team);

            //team.getTeamDAO().addSpieler(team, false);
            //setzlisteDAO.create(ausgewaehlte_spielklasse.getSetzliste().size(),team,ausgewaehlte_spielklasse);

            team=new Team();
            //l_meldungsetzliste1.setText(team.getSpielerEins().getVName()+" "+team.getSpielerEins().getNName()+" und "+team.getSpielerZwei().getVName()+" "+team.getSpielerZwei().getNName()+" wurden der Setzliste hinzugefügt!");

        }
        spielsystem_setzliste.refresh();
        pruefeAnzahlRLPItems();
    }

    private TableColumn getTableColumn(
            final TableColumn  column, int offset) {
        int columnIndex = spielsystem_setzliste.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return spielsystem_setzliste.getVisibleLeafColumn(newColumnIndex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );

            titel = bundle.getString("t_spielsystem");
            t_spielsystem.setText(titel);

            titel = bundle.getString("radio_gruppe");
            radio_gruppe.setText(titel);

            titel = bundle.getString("radio_gruppeMitE");
            radio_gruppeMitE.setText(titel);

            titel = bundle.getString("radio_ko");
            radio_ko.setText(titel);

            titel = bundle.getString("radio_schweizer");
            radio_schweizer.setText(titel);

            titel = bundle.getString("t_Gruppengröße");
            t_Gruppengröße.setText(titel);

            titel = bundle.getString("t_AnzahlWeiterkom");
            t_AnzahlWeiterkom.setText(titel);

            titel = bundle.getString("rb_Gruppe");
            rb_Gruppe.setText(titel);

            titel = bundle.getString("rb_ko");
            rb_ko.setText(titel);

            titel = bundle.getString("t_Trostrunde");
            t_Trostrunde.setText(titel);

            titel = bundle.getString("tab_Setzliste");
            tab_Setzliste.setText(titel);

            titel = bundle.getString("tabsperst");
            tabsperst.setText(titel);

            titel = bundle.getString("t_Endrunde");
            t_Endrunde.setText(titel);

            titel = bundle.getString("radio_trostJa");
            radio_trostJa.setText(titel);

            titel = bundle.getString("radio_trostNein");
            radio_trostNein.setText(titel);

            titel = bundle.getString("radio_platzDreiAusspielen");
            radio_platzDreiAusspielen.setText(titel);

            titel = bundle.getString("radio_platzDreiAusspielenNein");
            radio_platzDreiAusspielenNein.setText(titel);

            titel = bundle.getString("t_Platz3");
            t_Platz3.setText(titel);

            titel = bundle.getString("t_AnzahlRunden");
            t_AnzahlRunden.setText(titel);

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }


        auswahlklasse.setSpielsystemController(this);
        obs_setzliste.addListener((ListChangeListener)(c -> {
            anzahlsetzlistespieler.setText(obs_setzliste.size()+" Spieler");
        }));
        tabsperst.setDisable(false);

        if(ausgewaehlte_spielklasse.toString().toUpperCase().contains("MIX"))
        {
            spielsystem_spielerliste_alleSpieler.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
        else
        {
            spielsystem_spielerliste_alleSpieler.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }

        spielsystem_setzliste.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        System.out.println("------------------"+ausgewaehlte_spielklasse.isSetzliste_gesperrt());

/*        for (int i = 0; i < ausgewaehlte_spielklasse.getSetzliste().size(); i++) {
            dicttest.put(i, ausgewaehlte_spielklasse.getSetzliste().get(i));
            *//*System.out.println("Dict: " + dicttest.size());
            System.out.println(ausgewaehlte_spielklasse.getSetzliste().size());*//*
        }*/
        try {
            printSpielerSpielklasseVorhandenTable();
            printSpielerSpielklasseHinzuTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ausgewaehlte_spielklasse.isSetzliste_gesperrt())
        {
            pruefeSperrungSetzliste();
        }
        else {
            btn_stoppen.setVisible(false);
            label_spieler.setVisible(false);
            label_spiele.setVisible(false);
            vbox_info.setVisible(false);
            vbox_info.setDisable(true);
            ContextMenu contextMenu = new ContextMenu();


            spielsystem_setzliste.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        contextMenu.hide();
                    }

                    if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                        Team clickedRow = (Team) row.getItem();
                        //(((Node)(event.getSource())).getScene().getWindow().hide();
                        MenuItem item1 = new MenuItem("Setzplatz bearbeiten");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                row.setEditable(true);
                                //row.getTableView().getEditingCell().
                                TablePosition<Team, ?> pos = spielsystem_setzliste.getFocusModel().getFocusedCell() ;
                                spielsystem_setzliste.setEditable(true);
                                spielsystem_setzliste.edit(pos.getRow(),setzplatz);

                            }
                        });
                        MenuItem item2 = new MenuItem("Aus Setzliste entfernen");
                        item2.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                                //FuelleFelder(clickedRow);

                                removeTeam(clickedRow);

                            }
                        });
                        MenuItem item4 = new MenuItem("Setzplatz zurücksetzen");
                        item4.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                                //FuelleFelder(clickedRow);

                                setzplatzZuruecksetzen(clickedRow);



                            }
                        });

                        // Add MenuItem to ContextMenu
                        contextMenu.getItems().clear();
                        contextMenu.getItems().addAll(item1, item2,item4);

                        // When user right-click on Circle
                        spielsystem_setzliste.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                if(!ausgewaehlte_spielklasse.isSetzliste_gesperrt())
                                {
                                    contextMenu.show(spielsystem_setzliste, event.getScreenX(), event.getScreenY());
                                }

                            }
                        });
                    }
                });
                return row;
            });


            spielsystem_spielerliste_alleSpieler.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        contextMenu.hide();
                    }
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                            && event.getClickCount() == 2) {
                        Spieler clickedRow = (Spieler) row.getItem();
                        //(((Node)(event.getSource())).getScene().getWindow().hide();
                        addSpielerMixCheck(clickedRow);

                    }
                    if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                        Spieler clickedRow = (Spieler) row.getItem();
                        //(((Node)(event.getSource())).getScene().getWindow().hide();
                        MenuItem item1 = new MenuItem("Spieler hinzufügen");
                        item1.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();


                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                    stage.setTitle("Spieler");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Fehler beim laden");
                                }
                                //tabpane_spieler.getSelectionModel().select(tab_sphin);
                            }
                        });
                        MenuItem item2 = new MenuItem("Spieler bearbeiten");
                        item2.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                                //FuelleFelder(clickedRow);

                                try {
                                    auswahlklasse.setUpdateSpieler(clickedRow);
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spielerHinzu.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();


                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                    stage.setTitle("Spieler");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Fehler beim laden");
                                }


                            }
                        });
                        MenuItem item3 = new MenuItem("Spieler löschen");
                        item3.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {

                                boolean loeschespieler = clickedRow.getSpielerDAO().delete(clickedRow);
                                if (loeschespieler) {
                                    obs_spieler.remove(clickedRow);
                                    auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzliste().remove(clickedRow);
                                    auswahlklasse.getSpieler().remove(clickedRow);
                                    //tabelle_spielerliste.refresh();
                                    System.out.println("Lösche   " + clickedRow.getNName());
                                    l_meldungsetzliste1.setText(clickedRow.getVName() + " " + clickedRow.getNName() + " wurde erfolgreich gelöscht!");
                                } else {

                                    //l_meldungsetzliste.setTextFill(Color.web("#048d46"));
                                    l_meldungsetzliste1.setText(clickedRow.getVName() + " " + clickedRow.getNName() + " kann nicht gelöscht werden!");
                                }

                            }
                        });
                        MenuItem item4 = null;
                        if(spielsystem_spielerliste_alleSpieler.getSelectionModel().getSelectedItems().size()>1)
                        {
                            item4 = new MenuItem("Alle markierten Spieler zur Setzliste hinzufügen");
                        }
                        else
                        {
                            if(team!=null&&team.toString()==null)
                            {
                                item4 = new MenuItem("Team erstellen");
                            }
                            else if(team.getSpielerEins().getGeschlecht()&&!clickedRow.getGeschlecht()||!team.getSpielerEins().getGeschlecht()&&clickedRow.getGeschlecht())
                            {
                                item4 = new MenuItem("Als Doppelpartner von "+team.getSpielerEins());
                            }

                        }


                        // Add MenuItem to ContextMenu
                        contextMenu.getItems().clear();

                        if(item4!=null)
                        {
                            item4.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {

                                //System.out.println(spielsystem_spielerliste_alleSpieler.getSelectionModel().getSelectedItems());
                                markierteSpielerzurSetzliste();
                                //addSpieler(clickedRow);


                            }
                        });
                            contextMenu.getItems().add(item4);
                        }
                        contextMenu.getItems().addAll(item1, item2, item3);

                        // When user right-click on Circle
                        spielsystem_spielerliste_alleSpieler.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                            @Override
                            public void handle(ContextMenuEvent event) {

                                contextMenu.show(spielsystem_spielerliste_alleSpieler, event.getScreenX(), event.getScreenY());
                            }
                        });
                    }
                });
                return row;
            });
            t_suchleistespieler.textProperty().addListener((observable, oldValue, newValue) -> {
                // System.out.println("textfield changed from " + oldValue + " to " + newValue);
                //obs_spieler.clear();

                obs_spieler.clear();


                spielsystem_spielerliste_alleSpieler.refresh();
                Enumeration e = auswahlklasse.getSpieler().keys();
                while (e.hasMoreElements()) {
                    int key = (int) e.nextElement();
                    if (auswahlklasse.getSpieler().get(key).toString().toUpperCase().contains(t_suchleistespieler.getText().toUpperCase())) {
                        if (!istInSetzListe(auswahlklasse.getSpieler().get(key))) {
                            obs_spieler.add(auswahlklasse.getSpieler().get(key));
                        }
                    }
                    ;
                }


            });





        }
        t_suchleistesetzliste.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            obs_setzliste.clear();
            fuelleobs_setzliste();

            spielsystem_setzliste.refresh();
            setzplatz.setSortType(TableColumn.SortType.ASCENDING);
            spielsystem_setzliste.getSortOrder().add(setzplatz);

        });





        sortiereTabelleSetzliste();



    }//Ende Initialize

    private void addSpielerMixCheck(Spieler clickedRow) {
        if(ausgewaehlte_spielklasse.toString().toUpperCase().contains("MIX"))
        {
            if(team.toString()!=null)
            {
                if(team.getSpielerEins().getGeschlecht()&&!clickedRow.getGeschlecht()||!team.getSpielerEins().getGeschlecht()&&clickedRow.getGeschlecht())
                {
                    addSpieler(clickedRow);
                }
                else
                {
                    auswahlklasse.InfoBenachrichtigung("Gleiches Geschlecht","Bei Mixed muss Geschlecht verschieden sein");
                }
            }
            else
            {
                addSpieler(clickedRow);
            }
        }
        else
        {
            addSpieler(clickedRow);
        }
    }

    private void setzplatzZuruecksetzen(Team clickedRow) {
        removeTeam(clickedRow);
        if(ausgewaehlte_spielklasse.isEinzel())
        {
            addSpieler(clickedRow.getSpielerEins());
        }
        else
        {
            addSpieler(clickedRow.getSpielerEins());
            addSpieler(clickedRow.getSpielerZwei());
        }
    }

    private void markierteSpielerzurSetzliste() {
        ObservableList markierteTeams= FXCollections.observableArrayList(spielsystem_spielerliste_alleSpieler.getSelectionModel().getSelectedItems());

        for(int i=0;i<markierteTeams.size();i++)
        {
            Spieler spieler = (Spieler) markierteTeams.get(i);
            addSpielerMixCheck(spieler);
        }
    }
    private void markierteTeamszurSpielerliste() {
        ObservableList markierteTeams= FXCollections.observableArrayList(spielsystem_setzliste.getSelectionModel().getSelectedItems());

        if(!ausgewaehlte_spielklasse.isSetzliste_gesperrt())
        {


            for(int i=0;i<markierteTeams.size();i++)
            {
                Team team = (Team) markierteTeams.get(i);
                removeTeam(team);
            }
        }
    }

    private void pruefeAnzahlRLPItems()
    {
        menu_rlp.getItems().clear();
        MenuItem alle=new MenuItem("Alle");
        alle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                erstelleSetzlisteRLPAnzahlSpieler(obs_setzliste.size());
            }
        });
        menu_rlp.getItems().add(alle);
        if(obs_setzliste.size()>2)
        {


            int anzahl = (int) (Math.log( obs_setzliste.size() ) / Math.log( 2.0 ));
            System.out.println(anzahl);
            MenuItem[] item = new MenuItem[anzahl];

            for(int i=0;i<anzahl;i++)
            {
                item[i]=new MenuItem(String.valueOf(Math.round(Math.pow(2,anzahl)))+" Setzplätze erstellen");
                //item[anzahl].setText(String.valueOf(Math.round(Math.pow(2,anzahl)))+" Setzplätze erstellen");
                int finalI = i;
                item[finalI].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int anzahl = (int) Math.pow(2,finalI+1);
                        System.out.println(anzahl);
                        erstelleSetzlisteRLPAnzahlSpieler(anzahl);
                    }
                });
            }

            while(anzahl>0)
            {


                item[anzahl-1].setText(String.valueOf(Math.round(Math.pow(2,anzahl)))+" Setzplätze erstellen");
                menu_rlp.getItems().add(item[anzahl-1]);
                anzahl--;
            }

        }
    }
    @FXML
    private void stoppeSpielsystem(ActionEvent event)
    {
        boolean erfolg=ausgewaehlte_spielklasse.getSpielklasseDAO().stoppeSpielsystem(ausgewaehlte_spielklasse);

        if(erfolg)
        {
            auswahlklasse.InfoBenachrichtigung("Stop","stop erfolgreich");
        }
    }
    private void pruefeSperrungSetzliste() {
        if(ausgewaehlte_spielklasse.getSpiele().size()>0)
        {
            tabsperst.setDisable(true);
            ausgewaehlte_spielklasse.setSetzliste_gesperrt(true);
            pfeil_links.setVisible(false);
            pfeil_rechts.setVisible(false);
            l_meldungsetzliste1.setText("Setzliste gesperrt!!!");
            spielsystem_spielerliste_alleSpieler.setVisible(false);

            t_suchleistespieler.setVisible(false);


            spielsystem_setzliste.getSortOrder().add(setzplatz);
            btn_stoppen.setVisible(true);
            label_spiele.setVisible(true);
            label_spiele.setText("Anzahl Spiele: "+ausgewaehlte_spielklasse.getSpiele().size());
            label_spieler.setVisible(true);
            label_spieler.setText("Anzahl Spieler in der Setzliste: "+ausgewaehlte_spielklasse.getSetzlistedict().size());
            vbox_info.setDisable(false);
            vbox_info.setVisible(true);
            //btnentf.setVisible(false);
        }
        else
        {
            btn_stoppen.setVisible(false);
            label_spieler.setVisible(false);
            label_spiele.setVisible(false);
            vbox_info.setDisable(true);
            vbox_info.setVisible(false);

        }

    }
}
