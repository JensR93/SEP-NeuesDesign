package sample.FXML;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import sample.DAO.auswahlklasse;
import sample.FXML.Visualisierung.GruppenTabelle;
import sample.FXML.Visualisierung.PlatzierungsTabelle;
import sample.FXML.Visualisierung.Turnierbaum;
import sample.Spielklasse;
import sample.Spielsysteme.GruppeMitEndrunde;
import sample.Spielsysteme.Spielsystem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 20.09.2017.
 */
public class VisualisierungController implements Initializable {
    @FXML
    JFXTabPane tabPane_spielklassen;

    public void SpracheLaden()
    {

    }

    public void klassenTabsErstellen() {
        tabPane_spielklassen.getTabs().clear();
        for(int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size();i++){
            Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(i);
            if(spielklasse.getSpielsystem()!=null) {
                Tab tab = new Tab(spielklasse.toString());
                tab.setClosable(false);
                tabPane_spielklassen.getTabs().add(tab);
                klassenVisualisierung(spielklasse.getSpielsystem(),tab);
            }
        }
    }
    private void klassenVisualisierung(Spielsystem spielsystem, Tab tab) {
        if (spielsystem.getSpielSystemArt()==1){
            gruppeVisualisierung(spielsystem, tab);
        }
        else if(spielsystem.getSpielSystemArt()==2){
            gruppeMitEndrundeVisualisierung(spielsystem, tab);
        }
        else if (spielsystem.getSpielSystemArt()==3){
            koVisualisierung(spielsystem, tab);
        }
        else if(spielsystem.getSpielSystemArt()==5){
            schweizerVisualisierung(spielsystem, tab);
        }

    }

    private void schweizerVisualisierung(Spielsystem spielsystem, Tab tab) {
        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        int zellenHoehe = 20;
        Canvas canvas = new Canvas(600,spielsystem.getSetzliste().size()*zellenHoehe+100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PlatzierungsTabelle platzierungsTabelle = new PlatzierungsTabelle(20,20,zellenHoehe,15,200,45,45,45, spielsystem,gc);
    }

    private void gruppeVisualisierung(Spielsystem spielsystem, Tab tab) {
        GruppenTabelle gruppenTabelle = new GruppenTabelle(spielsystem, tab);
        gruppenTabelle.erstelleGruppenTabelle();

    }

    private void gruppeMitEndrundeVisualisierung(Spielsystem spielsystem, Tab tab) {
        GruppeMitEndrunde gruppeMitEndrunde = (GruppeMitEndrunde) spielsystem;
        TabPane tabPane = new TabPane();
        tab.setContent(tabPane);
        for(int i = 0;i<gruppeMitEndrunde.getAlleGruppen().size();i++){
            Tab gruppe = new Tab("Gruppe "+(i+1));
            gruppe.setClosable(false);
            tabPane.getTabs().add(gruppe);
            gruppeVisualisierung(gruppeMitEndrunde.getAlleGruppen().get(i),gruppe);
        }
        Tab endrunde = new Tab("Endrunde");
        endrunde.setClosable(false);
        tabPane.getTabs().add(endrunde);
        koVisualisierung(gruppeMitEndrunde.getEndrunde(),endrunde);
    }

    private void koVisualisierung(Spielsystem spielsystem, Tab tab) {
        if (spielsystem!=null) {
            Turnierbaum turnierbaum = new Turnierbaum(20, 20, 180, 50, 100, 20);
            if (auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size() > 1) {
                Canvas canvas = new Canvas();
                VBox vBox = new VBox();
                ScrollPane scrollPane = new ScrollPane();
                tab.setContent(scrollPane);
                scrollPane.setContent(vBox);
                vBox.getChildren().add(canvas);
                turnierbaum.erstelleTurnierbaum(spielsystem, canvas);
                //vBox.setStyle("-fx-background-color: #d8d8d8");
            }
        }
    }

    public void zoomIn(){
        ScrollPane aktuellesScrollPane = (ScrollPane) tabPane_spielklassen.getSelectionModel().getSelectedItem().getContent();
        aktuellesScrollPane.setScaleX(2);
        aktuellesScrollPane.setScaleY(2);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auswahlklasse.setVisualisierungController(this);
        klassenTabsErstellen();

    }
}
