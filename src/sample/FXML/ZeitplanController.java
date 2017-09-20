package sample.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Zeitplan;
import sample.ZeitplanRunde;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * Created by jens on 19.09.2017.
 */
public class ZeitplanController implements Initializable{

    private ObservableList<ZeitplanRunde> rundenListe = FXCollections.observableArrayList();
    private ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
    ArrayList<ArrayList<Spiel>> zeitplanTabelle = new ArrayList<>();
    @FXML
    private GridPane grid_zeitplan;
    @FXML
    private TableView<ZeitplanRunde> tableview_runden;
    @FXML
    private Canvas canvas_zeitplantabelle;

    @FXML
    public void pressBtn_speichern(){
        ArrayList<ZeitplanRunde> neueRundenListe = new ArrayList<>();
        neueRundenListe.addAll(rundenListe);
        zeitplan = Zeitplan.zeitplanErstellen(neueRundenListe);
        uebersichtZeichnen();
    }

    private void uebersichtZeichnen() {
        arrayAufteilen();
        tabelleZeichnen();
    }

    private void tabelleZeichnen() {
        GraphicsContext gc = canvas_zeitplantabelle.getGraphicsContext2D();
        int zellenHoehe = 40;
        int zellenBreite = 80;
        int xObenLinks = 20;
        int yObenLinks =20;
        if(zeitplanTabelle.size()>0) {
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
                gc.fillText("Feld " + (i + 1), xObenLinks + i * zellenBreite + 10, yObenLinks +20);
                gc.stroke();
                gc.closePath();
            }
            for (int i = 0; i < zeitplanTabelle.size(); i++) {
                zeileZeichnen(xObenLinks, yObenLinks + i*zellenHoehe, zellenBreite, zellenHoehe, gc, zeitplanTabelle.get(i));
            }
        }

    }

    private void zeileZeichnen(int xObenLinks, int yObenLinks, int zellenBreite, int zellenHoehe, GraphicsContext gc, ArrayList<Spiel> spiele) {
        for (int i = 0; i < spiele.size(); i++) { //Spaltentitel erstellen
            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(1);
            gc.beginPath();

            gc.moveTo(xObenLinks + (i+1) * zellenBreite, yObenLinks);
            gc.lineTo(xObenLinks + (i+1) * zellenBreite, yObenLinks + zellenHoehe);
            gc.lineTo(xObenLinks + i*zellenBreite, yObenLinks + zellenHoehe);
            if(i==0) {
                gc.lineTo(xObenLinks + i*zellenBreite, yObenLinks);
            }
            gc.fillText(spiele.get(i).getRundenNameKurz(), xObenLinks + i * zellenBreite + 10, yObenLinks +20);
            gc.stroke();
            gc.closePath();
        }
    }

    private void arrayAufteilen() {
        int anzahlFelder = auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size();
        ArrayList<Spiel> zeitplanKopie = new ArrayList<>();
        zeitplanKopie.addAll(zeitplan);
        while (zeitplanKopie.size()>0){
            zeitplanTabelle.add(new ArrayList<>());
            for (int i=0;i<anzahlFelder;i++){
                Spiel spiel = zeitplanKopie.get(0);
                zeitplanKopie.remove(spiel);
                zeitplanTabelle.get(zeitplanTabelle.size()-1).add(spiel);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rundenListe.addAll(Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl()));
        Zeitplan.optimalenZeitplanErstellen(auswahlklasse.getAktuelleTurnierAuswahl());
        sortiereRundenListe();
        tableColumnsErstellen();
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
        TableColumn<ZeitplanRunde,String> rundenName = new TableColumn("RundenName");
        TableColumn index = new TableColumn("#");
        TableColumn<ZeitplanRunde,Integer> anzahlSpiele = new TableColumn("Spiele");
        index.setCellValueFactory(new PropertyValueFactory<ZeitplanRunde,String>("rundenNummer"));
        rundenName.setCellValueFactory(new PropertyValueFactory<ZeitplanRunde,String>("rundenName"));
        anzahlSpiele.setCellValueFactory(new PropertyValueFactory<ZeitplanRunde,Integer>("anzahlSpiele"));
        tableview_runden.getColumns().addAll(index,rundenName,anzahlSpiele);
        tableview_runden.setItems(rundenListe);
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