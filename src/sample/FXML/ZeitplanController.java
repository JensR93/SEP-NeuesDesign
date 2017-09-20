package sample.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import sample.DAO.auswahlklasse;
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
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    private ObservableList<ZeitplanRunde> rundenListe = FXCollections.observableArrayList();

    @FXML
    private GridPane grid_zeitplan;

    @FXML
    private TableView<ZeitplanRunde> tableview_runden;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<ZeitplanRunde> alleRunden =Zeitplan.getAlleRunden(auswahlklasse.getAktuelleTurnierAuswahl());
        if(alleRunden!=null){
            rundenListe.addAll(alleRunden);
        }
        Zeitplan.zeitplanErstellen(auswahlklasse.getAktuelleTurnierAuswahl());
        sortiereRundenListe();
        tableColumnsErstellen();


        tableview_runden.setRowFactory(tv -> {
            TableRow<ZeitplanRunde> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);

                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    ZeitplanRunde draggedPerson = tableview_runden.getItems().remove(draggedIndex);

                    int dropIndex ;

                    if (row.isEmpty()) {
                        dropIndex = tableview_runden.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableview_runden.getItems().add(dropIndex, draggedPerson);

                    event.setDropCompleted(true);
                    tableview_runden.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row ;
        });

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