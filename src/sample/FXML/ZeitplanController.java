package sample.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Zeitplan;
import sample.ZeitplanRunde;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jens on 19.09.2017.
 */
public class ZeitplanController implements Initializable{

    String baseName = "resources.Main";
    String titel = "";

    private ObservableList<ZeitplanRunde> rundenListe = FXCollections.observableArrayList();
    private ObservableList<Spiel> zeitplanEinzel = FXCollections.observableArrayList();
    private ObservableList<Spiel> zeitplanDoppel = FXCollections.observableArrayList();
    private ObservableList<Spiel> zeitplanMixed = FXCollections.observableArrayList();
    private ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
    private LocalDateTime startZeitEinzel = auswahlklasse.getAktuelleTurnierAuswahl().getStartzeitEinzel();
    private LocalDateTime startZeitDoppel = auswahlklasse.getAktuelleTurnierAuswahl().getStartzeitDoppel();
    private LocalDateTime startZeitMixed = auswahlklasse.getAktuelleTurnierAuswahl().getStartzeitMixed();
    @FXML
    private ScrollPane scrollpane_zeitplantabelle;
    ArrayList<ArrayList<Spiel>> zeitplanTabelle = new ArrayList<>();
    @FXML
    private GridPane grid_zeitplan;
    @FXML
    private TableView<ZeitplanRunde> tableview_runden;
    private Canvas canvas_zeitplantabelle;

    @FXML
    public void pressBtn_speichern(){
        ArrayList<ZeitplanRunde> neueRundenListe = new ArrayList<>();
        neueRundenListe.addAll(rundenListe);
        zeitplanEinzel = Zeitplan.zeitplanErstellen(neueRundenListe);
        uebersichtZeichnen();
    }

    private void uebersichtZeichnen() {
        arrayAufteilen();
        tabelleZeichnen();
    }

    private void tabelleZeichnen() {
        GraphicsContext gc;

        int zellenHoehe = 20;
        int zellenBreite = 120;
        int xObenLinks = 100;
        int yObenLinks =20;

        if(zeitplanTabelle.size()>0) {
            //canvas_zeitplantabelle = new Canvas(xObenLinks*2+zellenBreite*zeitplanTabelle.get(0).size(),zeitplanTabelle.size()*zeitplanTabelle.size()*zellenHoehe+yObenLinks*2);
            canvas_zeitplantabelle = new Canvas(5000,5000);
            scrollpane_zeitplantabelle.setContent(canvas_zeitplantabelle);
            gc = canvas_zeitplantabelle.getGraphicsContext2D();
            for (int i = 0; i < zeitplanTabelle.get(0).size(); i++) { //Spaltentitel erstellen
                gc.beginPath();
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(1);
                gc.beginPath();


                gc.moveTo(xObenLinks + i*zellenBreite, yObenLinks);
                gc.lineTo(xObenLinks + (i+1) * zellenBreite, yObenLinks);
                gc.lineTo(xObenLinks + (i+1) * zellenBreite, yObenLinks + zellenHoehe);
                gc.lineTo(xObenLinks + i*zellenBreite, yObenLinks + zellenHoehe);
                if(i==0) {
                    gc.lineTo(xObenLinks + i*zellenBreite, yObenLinks);
                }
                gc.fillText("Feld " + (i + 1), xObenLinks + i * zellenBreite + 10, yObenLinks +15);
                gc.stroke();
                gc.closePath();
            }
            int disziplinZeitNummer =0; //Für Uhrzeit pro Disziplin (==i für erste Disziplin)
            String testString ="";
            boolean neueDisziplin =false;
            for (int i = 0; i < zeitplanTabelle.size(); i++) {
                if(zeitplanTabelle.get(i).size()>0) {
                    if (!disziplinTesten(zeitplanTabelle.get(i).get(0)).equals(testString) && !testString.equals("")) {
                        disziplinZeitNummer = 0;
                        neueDisziplin = true;
                    }
                    if(neueDisziplin||testString.equals("")){
                        String disziplinString = disziplinTesten(zeitplanTabelle.get(i).get(0));
                        Text text = new Text(disziplinString);
                        text.setFont(gc.getFont());
                        double textbreite = text.getLayoutBounds().getWidth();
                        gc.fillText(disziplinString, xObenLinks - textbreite - 5, yObenLinks + (i ) * zellenHoehe + 15);
                    }
                    testString=disziplinTesten(zeitplanTabelle.get(i).get(0));
                    LocalTime startzeit = getStartZeit(zeitplanTabelle.get(i).get(0)).plusMinutes(auswahlklasse.getAktuelleTurnierAuswahl().getMatchDauer() * disziplinZeitNummer);
                    String startzeitString = String.format("%02d:%02d", startzeit.getHour(), startzeit.getMinute());
                    Text text = new Text(startzeitString);
                    text.setFont(gc.getFont());
                    double textbreite = text.getLayoutBounds().getWidth();
                    gc.fillText(startzeitString, xObenLinks - textbreite - 5, yObenLinks + (i + 1) * zellenHoehe + 15);
                    zeileZeichnen(xObenLinks, yObenLinks + (i + 1) * zellenHoehe, zellenBreite, zellenHoehe, gc, zeitplanTabelle.get(i),neueDisziplin, startzeit);
                    disziplinZeitNummer++;
                    neueDisziplin=false;
                }
            }
        }

    }

    private LocalTime getStartZeit(Spiel spiel) {
        if(spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("EINZEL")){
            return startZeitEinzel.toLocalTime();
        }
        else if(spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("DOPPEL")){
            return startZeitDoppel.toLocalTime();
        }
        else{
            return startZeitMixed.toLocalTime();
        }

    }

    private void zeileZeichnen(int xObenLinks, int yObenLinks, int zellenBreite, int zellenHoehe, GraphicsContext gc, ArrayList<Spiel> spiele, boolean neueDisziplin, LocalTime startZeit) {
        for (int i = 0; i < spiele.size(); i++) {
            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(1);
            gc.beginPath();
            if(neueDisziplin){
                gc.setLineWidth(2);
                gc.setStroke(Color.DEEPPINK);
                gc.stroke();
                gc.moveTo(xObenLinks+i*zellenBreite,yObenLinks);
                gc.lineTo(xObenLinks + (i+1) * zellenBreite, yObenLinks);
                gc.stroke();
                gc.setLineWidth(1);
                gc.setStroke(Color.GREEN);
            }
            else{
                gc.moveTo(xObenLinks + (i+1) * zellenBreite, yObenLinks);
            }

            gc.lineTo(xObenLinks + (i+1) * zellenBreite, yObenLinks + zellenHoehe);
            gc.lineTo(xObenLinks + i*zellenBreite, yObenLinks + zellenHoehe);
            if(i==0) {
                gc.lineTo(xObenLinks + i*zellenBreite, yObenLinks);
            }
            spiele.get(i).setAufrufZeit(startZeit);
            gc.fillText(spiele.get(i).getRundenNameKurz(), xObenLinks + i * zellenBreite + 5, yObenLinks +15);
            gc.stroke();
            gc.closePath();
        }
    }

    private ArrayList<ArrayList<ZeitplanRunde>> alleRundenHolen(){
        ArrayList<ZeitplanRunde> einzelRunden;
        ArrayList<ZeitplanRunde> doppelRunden;
        ArrayList<ZeitplanRunde> mixedRunden;
        einzelRunden = Zeitplan.getAlleRundenSortiert("EINZEL");
        doppelRunden = Zeitplan.getAlleRundenSortiert("DOPPEL");
        mixedRunden = Zeitplan.getAlleRundenSortiert("MIXED");
        rundenListe.clear();
        zeitplan.clear();
        ArrayList<ArrayList<ZeitplanRunde>> alleDisziplinRunden = new ArrayList<>();
        ArrayList<LocalDateTime> startZeiten = new ArrayList<>();
        startZeiten.add(startZeitEinzel);
        startZeiten.add(startZeitDoppel);
        startZeiten.add(startZeitMixed);
        startZeiten.sort(LocalDateTime::compareTo);
        for (int i=0;i<startZeiten.size();i++){
            if(startZeiten.get(i)==startZeitEinzel && einzelRunden !=null){
                rundenListe.addAll(einzelRunden);
                alleDisziplinRunden.add(einzelRunden);
                zeitplan.addAll(Zeitplan.zeitplanErstellen(einzelRunden,zeitplan));
            }
            if(startZeiten.get(i)==startZeitDoppel && doppelRunden !=null){
                rundenListe.addAll(doppelRunden);
                alleDisziplinRunden.add(doppelRunden);
                zeitplan.addAll(Zeitplan.zeitplanErstellen(doppelRunden,zeitplan));
            }
            if(startZeiten.get(i)==startZeitMixed && mixedRunden !=null){
                rundenListe.addAll(mixedRunden);
                alleDisziplinRunden.add(mixedRunden);
                zeitplan.addAll(Zeitplan.zeitplanErstellen(mixedRunden,zeitplan));
            }
        }
        return alleDisziplinRunden;
    }

    private void arrayAufteilen() {
        int anzahlFelder = auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size();
        ArrayList<Spiel> zeitplanKopie = new ArrayList<>();
        zeitplanKopie.addAll(zeitplan);
        zeitplanTabelle.clear();
        String disziplinTest = "";
        if(zeitplanKopie.size()>0) {
            while (zeitplanKopie.size()>0) {
                zeitplanTabelle.add(new ArrayList<>());
                for (int i = 0; i < anzahlFelder; i++) {
                    Spiel spiel = zeitplanKopie.get(0);
                    if (!disziplinTesten(spiel).equals(disziplinTest) && !disziplinTest.equals("")) {
                        disziplinTest = disziplinTesten(spiel);
                        if(zeitplanTabelle.get(zeitplanTabelle.size()-1).size()!=0){
                            zeitplanTabelle.add(new ArrayList<>());
                        }
                        break;
                    }
                    else{
                        zeitplanKopie.remove(spiel);
                        disziplinTest = disziplinTesten(spiel);
                        zeitplanTabelle.get(zeitplanTabelle.size() - 1).add(spiel);
                        if (zeitplanKopie.size() == 0) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private String disziplinTesten(Spiel spiel) {
        if(spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("EINZEL")){
            return "EINZEL";
        }
        else if(spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("DOPPEL")){
            return "DOPPEL";
        }
        else{
            return "MIXED";
        }
    }

    public void pressBtn_optimalerZeitplan(){
        alleRundenHolen();
        uebersichtZeichnen();
        //tableColumnsErstellen();
        tabelleZeichnen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Zeitplan.zeitplanEinlesen(auswahlklasse.getAktuelleTurnierAuswahl());
        /*if (Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl(),"EINZEL")!=null) {

            Zeitplan.zeitplanEinlesen(auswahlklasse.getAktuelleTurnierAuswahl(), "EINZEL");
        }
        if (Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl(),"DOPPEL")!=null) {

            Zeitplan.zeitplanEinlesen(auswahlklasse.getAktuelleTurnierAuswahl(), "DOPPEL");
        }
        if (Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl(),"MIXED")!=null) {

            Zeitplan.zeitplanEinlesen(auswahlklasse.getAktuelleTurnierAuswahl(), "MIXED");
        }*/
/*        rundenListe.addAll(Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl(), "EINZEL"));
        rundenListe.addAll(Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl(), "DOPPEL"));
        rundenListe.addAll(Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl(), "MIXED"));
        sortiereRundenListe();
        tableColumnsErstellen();
        zeitplanEinzel=Zeitplan.getZeitplan("EINZEL");
        zeitplanDoppel=Zeitplan.getZeitplan("DOPPEL");
        zeitplanMixed =Zeitplan.getZeitplan("MIXED");*/
        alleRundenHolen();
        uebersichtZeichnen();
        tableColumnsErstellen();
        System.out.println(startZeitEinzel);
        System.out.println(startZeitDoppel);
        System.out.println(startZeitMixed);
    }

    private void sortiereRundenListe() {
        rundenListe.sort(new Comparator<ZeitplanRunde>() {
            @Override
            public int compare(ZeitplanRunde o1, ZeitplanRunde o2) {
                return o1.getRundenNummer()-o2.getRundenNummer();
            }
        });
    }


    private void tableColumnsErstellen() {

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName);

            String RundenName = bundle.getString("RundeName");
            String Spiele = bundle.getString("Spiele");

        TableColumn<ZeitplanRunde,String> rundenName = new TableColumn(RundenName);
        TableColumn index = new TableColumn("#");
        TableColumn<ZeitplanRunde,Integer> anzahlSpiele = new TableColumn(Spiele);
        index.setCellValueFactory(new PropertyValueFactory<ZeitplanRunde,String>("rundenNummer"));
        rundenName.setCellValueFactory(new PropertyValueFactory<ZeitplanRunde,String>("rundenName"));
        anzahlSpiele.setCellValueFactory(new PropertyValueFactory<ZeitplanRunde,Integer>("anzahlSpiele"));
        tableview_runden.getColumns().clear();
        tableview_runden.getColumns().addAll(index,rundenName,anzahlSpiele);
        tableview_runden.setItems(rundenListe);

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }

    }
}




/*@FXML
    void DragvomObjekt(MouseEvent event) {

    }

    @FXML
    void dragaufObjekt(DragEvent event) {

    }

    @FXML
    void label1dragdetected(MouseEvent event) {

            Dragboard db = Objekt1.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(Objekt1.getText());
            db.setContent(content);

            event.consume();
    }

    @FXML
    void label1dragdropped(DragEvent event) {

        String str = event.getDragboard().getString();

        if(str.equals(Objekt2.getText()))
        {


            Objekt2.setText(Objekt1.getText());
            Objekt1.setText(str);
            labelliste.remove(Objekt1);
            labelliste.add(1,Objekt1);
            setztlabelAnzeige();
        }
        if(str.equals(Objekt3.getText()))
        {

            Objekt3.setText(Objekt1.getText());
            Objekt1.setText(str);
            labelliste.remove(Objekt1);
            labelliste.add(2,Objekt1);
            setztlabelAnzeige();
        }


    }
    @FXML
    void label1dragover(DragEvent event) {
        if(event.getDragboard().hasString())
        {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    void label2dragover(DragEvent event) {
        if(event.getDragboard().hasString())
        {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    void label3dragover(DragEvent event) {
        if(event.getDragboard().hasString())
        {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    void label2dragdetected(MouseEvent event) {
        Dragboard db = Objekt2.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(Objekt2.getText());
        db.setContent(content);

        event.consume();
    }

    @FXML
    void label2dragdropped(DragEvent event) {
        String str = event.getDragboard().getString();
        if(str.equals(Objekt1.getText()))
        {


            Objekt1.setText(Objekt2.getText());
            Objekt2.setText(str);

            labelliste.remove(Objekt2);
            labelliste.add(0,Objekt2);
            setztlabelAnzeige();

        }
        if(str.equals(Objekt3.getText()))
        {

            Objekt3.setText(Objekt2.getText());
            Objekt2.setText(str);

            labelliste.remove(Objekt2);
            labelliste.add(2,Objekt2);
            setztlabelAnzeige();
        }
    }

    @FXML
    void label3dragdetected(MouseEvent event) {
        Dragboard db = Objekt3.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(Objekt3.getText());
        db.setContent(content);

        event.consume();
    }

    @FXML
    void label3dragdropped(DragEvent event) {
        String str = event.getDragboard().getString();
        if(str.equals(Objekt1.getText()))
        {


            Objekt1.setText(Objekt3.getText());
            Objekt3.setText(str);
            labelliste.remove(Objekt3);
            labelliste.add(0,Objekt3);
            setztlabelAnzeige();

        }
        if(str.equals(Objekt2.getText()))
        {

            Objekt2.setText(Objekt3.getText());
            Objekt3.setText(str);
            labelliste.remove(Objekt3);
            labelliste.add(1,Objekt3);

            setztlabelAnzeige();
        }
    }*/