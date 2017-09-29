package sample.FXML;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import sample.*;
import sample.DAO.auswahlklasse;

import java.net.URL;
import java.util.*;

/**
 * Created by Manuel Hüttermann on 20.09.2017.
 */
public class SpieluebersichtController implements Initializable {

    DashboardController controller;

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    String baseName = "resources.Main";
    String titel = "";

    final ObservableList<Spielklasse> strings = FXCollections.observableArrayList();
    final CheckComboBox<Spielklasse> checkComboBox = new CheckComboBox<Spielklasse>();
    private Label lspielklassen;
    ArrayList<Integer> index_neu = new ArrayList<Integer>();

    //region FXML Deklaration

    @FXML
    private GridPane gridPane_main;
    @FXML
    private JFXCheckBox check_gespielteSpiele = new JFXCheckBox();
    @FXML
    private JFXCheckBox check_aktiveSpiele = new JFXCheckBox();
    @FXML
    private JFXCheckBox check_ausstehendeSpiele = new JFXCheckBox();
    @FXML
    private JFXCheckBox check_zukuenftigeSpiele = new JFXCheckBox();

    @FXML
    public TableView tabelle_spiele;
    @FXML
    private Tab tab_spieluebersicht = new Tab();
    @FXML
    JFXTabPane tabPane_spielklassen = new JFXTabPane();
    @FXML
    private JFXTextField tspielsuche;

    private HBox hBox =new HBox();

    private VBox vbox_main = new VBox();

    private GridPane grid_pane1 = new GridPane();

    private GridPane grid_pane2 = new GridPane();

    public void SpracheLaden()
    {

    }

    //endregion

    ObservableList<Spiel> sortListe = FXCollections.observableArrayList(auswahlklasse.getAktuelleTurnierAuswahl().getObs_angezeigteSpiele());
//wieso integer?
    //wenn integer dann die ids abgehen


    private void sortiereTabelleSpiele() {
        sortListe.sort(new Comparator<Spiel>() {
            @Override
            public int compare(Spiel o1, Spiel o2) {
                int a=0,b=0;
                if(o1.getStatus()==0)
                {
                    a-=100000;
                }
                if(o2.getStatus()==0)
                {
                    b-=100000;
                }
                return o1.getZeitplanNummer()-a - o2.getZeitplanNummer()-b;
            }
        });
        tabelle_spiele.setItems(sortListe);
    }


    public void printSpielTable() throws Exception {
        tabelle_spiele.getColumns().clear();
        if (auswahlklasse.getAktuelleTurnierAuswahl() != null) {

            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                String feld = bundle.getString("feld");
                String heim = bundle.getString("heim");
                String gast = bundle.getString("gast");
                String ergebnis = bundle.getString("ergebnis");
                String spielklasse = bundle.getString("spielklasse");
                String runde = bundle.getString("runde");


                check_aktiveSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getAktiveSpieleFarbe()));
                check_ausstehendeSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getAusstehendeSpieleFarbe()));
                check_gespielteSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getGespielteSpieleFarbe()));
                check_zukuenftigeSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getZukuenftigeSpieleFarbe()));
                toggleCheckbox(check_aktiveSpiele);
                toggleCheckbox(check_ausstehendeSpiele);
                toggleCheckbox(check_gespielteSpiele);
                toggleCheckbox(check_zukuenftigeSpiele);

                TableColumn<Spiel, String> spielNummerSpalte = tableColoumnsetCellFactory("#", "SpielNummer");
                TableColumn<Spiel, String> spielFeldSpalte = tableColoumnsetCellFactory(feld, "FeldNr");
                TableColumn<Spiel, String> spielHeimSpalte = tableColoumnsetCellFactory(heim, "HeimStringKomplett");
                TableColumn<Spiel, String> spielGastSpalte = tableColoumnsetCellFactory(gast, "GastStringKomplett");
                TableColumn<Spiel, String> spielErgebnisSpalte = tableColoumnsetCellFactory(ergebnis, "ErgebnisString");
                TableColumn<Spiel, String> spielSpielklasseSpalte = tableColoumnsetCellFactory(spielklasse, "SpielklasseString");
                TableColumn<Spiel, String> spielRundeSpalte = tableColoumnsetCellFactory(runde, "RundenName");

                tabelle_spiele.getColumns().addAll(spielNummerSpalte, spielFeldSpalte, spielHeimSpalte, spielGastSpalte, spielErgebnisSpalte, spielSpielklasseSpalte, spielRundeSpalte);

                spielNummerSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.0400));
                spielNummerSpalte.getStyleClass().add("table-viewRightAlignColumn");
                spielNummerSpalte.setSortable(false);
                spielFeldSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.0528));
                spielFeldSpalte.getStyleClass().add("table-viewLeftAlignColumn");
                spielFeldSpalte.setSortable(false);
                spielHeimSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.2528));
                spielHeimSpalte.getStyleClass().add("table-viewRightAlignColumn");
                spielHeimSpalte.setSortable(false);
                spielGastSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.2528));
                spielGastSpalte.getStyleClass().add("table-viewLeftAlignColumn");
                spielGastSpalte.setSortable(false);
                spielErgebnisSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.1128));
                spielErgebnisSpalte.getStyleClass().add("table-viewLeftAlignColumn");
                spielErgebnisSpalte.setSortable(false);
                spielSpielklasseSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.1428));
                spielSpielklasseSpalte.getStyleClass().add("table-viewLeftAlignColumn");
                spielSpielklasseSpalte.setSortable(false);
                spielRundeSpalte.prefWidthProperty().bind(tabelle_spiele.widthProperty().multiply(0.1428));
                spielRundeSpalte.getStyleClass().add("table-viewLeftAlignColumn");
                spielRundeSpalte.setSortable(false);

            }
            catch ( MissingResourceException e ) {
                System.err.println( e );
            }
        } else {
            System.out.println("kann Turnier nicht laden");
        }
    }

    private void toggleCheckbox(JFXCheckBox checkbox) {
        if(checkbox.isSelected()){
            checkbox.setSelected(false);
            checkbox.setSelected(true);
        }
        else{
            checkbox.setSelected(true);
            checkbox.setSelected(false);
        }
    }


    private TableColumn<Spiel, String> tableColoumnsetCellFactory(String tabellenspaltentext, String getFunktion) {
        TableColumn<Spiel, String> spalte = new TableColumn(tabellenspaltentext);

        spalte.setCellValueFactory(new PropertyValueFactory<Spiel, String>(getFunktion));
        spalte.setCellFactory(column -> {
            return new TableCell<Spiel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        ResourceBundle bundle = ResourceBundle.getBundle(baseName);
                        setText(item);
                        Spiel spiel = getTableView().getItems().get(getIndex());
                        String heim = bundle.getString("heim");
                        if (tabellenspaltentext.equals(heim) || tabellenspaltentext.equals("#")) {
                            setAlignment(Pos.CENTER_RIGHT);
                        }
                        if (spiel.getStatus() == 3) {
                            setTextFill(Color.valueOf(auswahlklasse.getEinstellungenController().getGespielteSpieleFarbe()));
                        } else if (spiel.getStatus() == 2) {
                            setTextFill(Color.valueOf(auswahlklasse.getEinstellungenController().getAktiveSpieleFarbe()));
                        } else if (spiel.getStatus() == 1) {
                            setTextFill(Color.valueOf(auswahlklasse.getEinstellungenController().getAusstehendeSpieleFarbe()));
                        } else {
                            setTextFill(Color.valueOf(auswahlklasse.getEinstellungenController().getZukuenftigeSpieleFarbe()));
                        }
                    }
                }
            };
        });
        return spalte;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auswahlklasse.setSpieluebersichtController(this);
        //region Sprache
        /*try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_turnierLaden");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_turnierLaden.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_klassen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_klassen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_spieler");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_spieler.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_zeitplan");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_zeitplan.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("btn_statistik");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        btn_statistik.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_turnierbaum");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_turnierbaum.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("tab_spieluebersicht");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        tab_spieluebersicht.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("m_datei");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        m_datei.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("m_turnier");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        m_turnier.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("m_ansicht");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        m_ansicht.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("m_sonstiges");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        m_sonstiges.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("m_neuesFenster");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        m_neuesFenster.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_turnierLaden");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_turnierLaden.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Meldeform_import");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Meldeform_import.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Prog_beenden");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Prog_beenden.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Turnierdaten_bearbeiten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Turnierdaten_bearbeiten.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Klassendaten_bearbeiten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Klassendaten_bearbeiten.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Spielerdaten_bearbeiten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Spielerdaten_bearbeiten.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Vereine_bearbeiten");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Vereine_bearbeiten.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_statistiken");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_statistiken.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Feldübersicht");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Feldübersicht.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Klassen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Klassen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_spielübersicht");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_spielübersicht.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Zeitplan");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Zeitplan.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Platzierung");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Platzierung.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_klassen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_klassen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Spielübersicht");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Spielübersicht.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_zeitplan");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_zeitplan.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_x");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_x.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Teilnehmerl_drucken");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Teilnehmerl_drucken.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Startgeldl_drucken");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Startgeldl_drucken.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Ergebnisf_erstellen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Ergebnisf_erstellen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("menu_Optionen");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        menu_Optionen.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("lab_Badmin_Turnierverw");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        lab_Badmin_Turnierverw.setText(titel);
*/
        //endregion


        //auswahlklasse.getAktuelleTurnierAuswahl().getObs_alleSpiele().clear();

        klassenTabsErstellen();
        tabelleSpieleContextMenu();
        checkComboBoxListener();
        checkComboBox.getStyleClass().add("check-combo-box");
        layoutErstellen();
        suchleisteListener();
        checkboxListener(check_aktiveSpiele);
        check_aktiveSpiele.setId("check-box");
        checkboxListener(check_ausstehendeSpiele);
        checkboxListener(check_gespielteSpiele);
        checkboxListener(check_zukuenftigeSpiele);
        check_zukuenftigeSpiele.getStyleClass().add("check");



        checkComboBoxFuellen();
        CheckeSpielsuche();

        //tabelle_spiele.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }


    private void checkComboBoxFuellen() {
        try {
            printSpielTable();
            for (int i = 0; i < auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size(); i++) {
                checkComboBox.getCheckModel().check(i);
            }
            //obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedItems();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
            for (int i = 0; i < checkComboBox.getCheckModel().getCheckedItems().size(); i++) {
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().add(checkComboBox.getCheckModel().getCheckedItems().get(i).getSpielklasseID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkboxListener(CheckBox checkBox) {
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    CheckeSpielsuche();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void layoutErstellen() {
        hBox.getChildren().clear();
        checkComboBox.setConverter(new StringConverter<Spielklasse>() {
            @Override
            public String toString(Spielklasse object) {
                return object.toString();
            }

            @Override
            public Spielklasse fromString(String string) {
                return null;
            }
        });

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);

            String aktiveSpiele = bundle.getString("aktivSpiel");
            String ausstehendeSpiele = bundle.getString("ausstehSpiel");
            String gespielteSpiele = bundle.getString("gespielSpiel");
            String zukuenftigeSpiele = bundle.getString("zukuenftSpiel");
            String Spielsuche = bundle.getString("Spielsuch");


            lspielklassen = new Label("Spielklassen");
            tspielsuche = new JFXTextField("");
            tspielsuche.setLabelFloat(true);
            tspielsuche.setPromptText(Spielsuche);
            tspielsuche.getStyleClass().add("text-field");
            gridPane_main.getChildren().add(grid_pane1);
            grid_pane1.getChildren().add(tspielsuche);
            hBox.getChildren().addAll(lspielklassen,checkComboBox);
            hBox.setSpacing(150);
            GridPane.setColumnIndex(tspielsuche, 0);
            GridPane.setRowIndex(tspielsuche, 0);


            gridPane_main.getChildren().add(grid_pane2);
            GridPane.setColumnIndex(grid_pane2, 2);
            GridPane.setRowIndex(grid_pane2, 0);
            gridPane_main.getChildren().add(vbox_main);
            GridPane.setColumnIndex(vbox_main, 1);
            GridPane.setRowIndex(vbox_main, 0);
//            gridPane_main.getChildren().add(check_ausstehendeSpiele);
//            GridPane.setColumnIndex(check_ausstehendeSpiele, 1);
//            GridPane.setRowIndex(check_ausstehendeSpiele, 0);
//            gridPane_main.getChildren().add(check_gespielteSpiele);
//            GridPane.setColumnIndex(check_gespielteSpiele, 1);
//            GridPane.setRowIndex(check_gespielteSpiele, 0);
//            gridPane_main.getChildren().add(check_zukuenftigeSpiele);
//            GridPane.setColumnIndex(check_zukuenftigeSpiele, 1);
//            GridPane.setRowIndex(check_zukuenftigeSpiele, 0);
            grid_pane2.getChildren().add(checkComboBox);
            vbox_main.getChildren().add(check_aktiveSpiele);
            vbox_main.getChildren().add(check_zukuenftigeSpiele);
            vbox_main.getChildren().add(check_gespielteSpiele);
            vbox_main.getChildren().add(check_ausstehendeSpiele);
            check_aktiveSpiele.setText(aktiveSpiele);
            check_aktiveSpiele.setSelected(true);
            check_aktiveSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getAktiveSpieleFarbe()));
            check_ausstehendeSpiele.setText(ausstehendeSpiele);
            check_ausstehendeSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getAusstehendeSpieleFarbe()));
            check_ausstehendeSpiele.setSelected(true);
            check_gespielteSpiele.setText(gespielteSpiele);
            check_gespielteSpiele.setSelected(true);
            check_gespielteSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getGespielteSpieleFarbe()));
            check_zukuenftigeSpiele.setText(zukuenftigeSpiele);
            check_zukuenftigeSpiele.setSelected(true);
            check_zukuenftigeSpiele.setCheckedColor(Color.valueOf(auswahlklasse.getEinstellungenController().getZukuenftigeSpieleFarbe()));
            checkComboBox.setMaxWidth(800);
            checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
    }

    private void suchleisteListener() {
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        tspielsuche.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            //obs_spieler.clear();

            pause.setOnFinished(event -> CheckeSpielsuche());
            pause.playFromStart();

        });
    }

    private void checkComboBoxListener() {
        checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Spielklasse>() {
            public void onChanged(Change<? extends Spielklasse> c) {
                //System.out.println(checkComboBox.getCheckModel().getCheckedIndices());
                //obs_spielklassen_auswahl=checkComboBox.getCheckModel().getCheckedIndices();
                auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().clear();
                for (int i = 0; i < checkComboBox.getCheckModel().getCheckedItems().size(); i++) {
                    auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().add(checkComboBox.getCheckModel().getCheckedItems().get(i).getSpielklasseID());

                }
                //System.out.println(obs_spielklassen_auswahl);
                try {
                    //System.out.println("Auswahl geändert"+ checkComboBox.getCheckModel().getCheckedIndices());
                    CheckeSpielsuche();
//                    tabelle_spiele.getItems().clear();
//                    tabelle_spiele.setItems(obs_spiele);
//                    tabelle_spiele.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tabelleSpieleContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        tabelle_spiele.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    contextMenu.hide();
                }
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Spiel clickedRow = (Spiel) row.getItem();
                    //UpdateSpieler(clickedRow);
                    //(((Node)(event.getSource())).getScene().getWindow().hide();
                }
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    Spiel clickedRow = (Spiel) row.getItem();
                    /*MenuItem item1 = new MenuItem("Spieldetails anzeigen");
                    item1.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            //tabpane_spieler.getSelectionModel().select(tab_sphin);
                        }
                    });*/
                    MenuItem item2 = new MenuItem("Ergebnisse eintragen");
                    item2.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            //tabpane_spieler.getSelectionModel().select(tab_spupdate);
                            //FuelleFelder(clickedRow);
                            auswahlklasse.setSpielAuswahlErgebniseintragen(clickedRow);
                            auswahlklasse.getDashboardController().setNodeSpielergebnis();

                        }
                    });
                    Menu item3 = new Menu("Felder zuweisen");
                    item3.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    Menu item4 = new Menu("in Vorbereitung setzen");
                    item4.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item5 = new MenuItem("Spiel zurückziehen");
                    item5.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item6 = new MenuItem("Spielzettel drucken");
                    item6.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item7 = new MenuItem("Spiel auf anderes Feld verlegen");
                    item7.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {


                        }
                    });
                    MenuItem item8 = new MenuItem("Ergebnis korrigieren");
                    item8.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {

                            auswahlklasse.setSpielAuswahlErgebniseintragen(clickedRow);
                            auswahlklasse.getSpielErgebnisEintragenController().setSpiel_update(clickedRow);
                            auswahlklasse.getSpielErgebnisEintragenController().fuelleUpdateSpiel();
                            auswahlklasse.getDashboardController().setNodeSpielergebnis();
                        }
                    });

                    contextMenu.getItems().clear();
                    //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt


                    if (clickedRow.getStatus() == 1) {
                        //ausstehend
                        ArrayList<Feld> feld = new ArrayList<>();
                        ArrayList<Feld> feld2 = new ArrayList<>();

                        Spiel spiel;
                        for (int i = 0; i < auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size(); i++) {

                            spiel = auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i).getAktivesSpiel();

                            if (spiel == null) {
                                feld.add(auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i));
                            } else {
                                spiel = auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i).getInVorbereitung();
                                if (spiel == null) {
                                    feld2.add(auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i));
                                }
                            }


                        }
                        MenuItem[] childMenu1 = new MenuItem[feld.size()];
                        MenuItem[] childMenu2 = new MenuItem[feld2.size()];
                        if (feld.size() > 0) {
                            for (int i = 0; i < feld.size(); i++) {
                                final int ii = i;

                                if (feld.get(i) != null) {
                                    childMenu1[i] = new MenuItem(feld.get(i).toString());

                                    childMenu1[i].setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            //System.out.println("Feld = " + feld.get(ii));
                                            clickedRow.setFeld(feld.get(ii));
                                            clickedRow.setStatus(2);
                                            CheckeSpielsuche();
                                       /*     auswahlklasse.getAktuelleTurnierAuswahl().getObs_ausstehendeSpiele().remove(clickedRow);
                                            auswahlklasse.getAktuelleTurnierAuswahl().getObs_aktiveSpiele().add(clickedRow);*/
                                        }
                                    });


                                    item3.getItems().add(childMenu1[i]);
                                }
                            }
                        }
                        if (feld2.size() > 0) {
                            for (int i = 0; i < feld2.size(); i++) {
                                if (feld2.get(i) != null) {
                                    childMenu2[i] = new MenuItem(feld2.get(i).toString());
                                    item4.getItems().add(childMenu2[i]);
                                }
                            }
                        }
                        contextMenu.getItems().addAll(item3);
                    }
                    if (clickedRow.getStatus() == 2) {   //aktiv
                        contextMenu.getItems().addAll(item2, item5, item6, item7);
                    }
                    if (clickedRow.getStatus() == 3) {
                        //gespielt
                        contextMenu.getItems().addAll(item8);
                    }

                    // Add MenuItem to ContextMenu


                    // When user right-click on Circle
                    tabelle_spiele.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                        @Override
                        public void handle(ContextMenuEvent event) {

                            contextMenu.show(tabelle_spiele, event.getScreenX(), event.getScreenY());
                        }
                    });
                }
            });
            return row;
        });
    }

    public void spielInTabelleAuswaehlen(Spiel spiel)
    {
        tabelle_spiele.getSelectionModel().select(spiel);
    }
    private void klassenTabsErstellen() {
        auswahlklasse.getDashboardController().setNodeVisualisierung();
    }


    public void zoomIn() {
        ScrollPane aktuellesScrollPane = (ScrollPane) tabPane_spielklassen.getSelectionModel().getSelectedItem().getContent();
        aktuellesScrollPane.setScaleX(2);
        aktuellesScrollPane.setScaleY(2);
    }

    public void CheckeSpielsuche() {


        boolean check0=check_zukuenftigeSpiele.isSelected(),check1=check_ausstehendeSpiele.isSelected(),
                check2=check_aktiveSpiele.isSelected(),check3=check_gespielteSpiele.isSelected();

        int status0=0;
        int status1=1;
        int status2=2;
        int status3=3;
        //System.out.println(tspielsuche.getText());

        tabelle_spiele.refresh();
        Enumeration e = auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().keys();
        sortListe.clear();
        while (e.hasMoreElements()) {
            int key = (int) e.nextElement();

            if (auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).toString().toUpperCase().contains(tspielsuche.getText().toUpperCase())) {

                if((auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).getStatus()==status0&&check0||
                        auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).getStatus()==status1&&check1||
                        auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).getStatus()==status2&&check2||
                        auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).getStatus()==status3&&check3)
                        && auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen_auswahl().
                        contains(auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key).getSpielklasseid()
                        ))
                {
                    sortListe.add(auswahlklasse.getAktuelleTurnierAuswahl().getSpiele().get(key));
                }

            }
        }
        sortiereTabelleSpiele();

    }
/*

    @FXML
    public void reloadcheckbox() {

        //auswahlklasse.getAktuelleTurnierAuswahl().getObs_alleSpiele().clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());
        Enumeration enumKeys = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();

        while (enumKeys.hasMoreElements()) {
            int key = (int) enumKeys.nextElement();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().add(auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().get(key));
            //System.out.println("größe = "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
            //checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
//        hbox_main.getChildren().remove(checkComboBox);
//        hbox_main.getChildren().add(checkComboBox);
        checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());


        System.out.println("Lade CheckCombobox ohne Button");

    }

    @FXML
    public void reloadcheckbox(ActionEvent event) {
        auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().clear();
        checkComboBox.getItems().clear();

        //System.out.println(a.getAktuelleTurnierAuswahl().getSpielklassen().size());

        Enumeration enumKeys = auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().keys();
        while (enumKeys.hasMoreElements()) {
            int key = (int) enumKeys.nextElement();
            auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().add(auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().get(key));
            //System.out.println("größe = "+a.getAktuelleTurnierAuswahl().getObs_spielklassen().size());
//            checkComboBox.getItems().add(obs_spielklassen.get(i-1));

        }
        checkComboBox.getItems().setAll(auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen());


        //System.out.println("test");

    }*/
}
