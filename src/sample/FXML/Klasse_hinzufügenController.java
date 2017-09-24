package sample.FXML;
import com.jfoenix.controls.JFXRadioButton;
import sample.Enums.Niveau;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DAO.auswahlklasse;
import sample.Enums.AnzahlRunden;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sample.Spieler;
import sample.Spielklasse;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jens on 19.09.2017.
 */
public class Klasse_hinzufügenController implements Initializable
{


    String baseName = "resources.Main";
    String titel ="";
    @FXML
    private Text t_disziplin;

    @FXML
    private ChoiceBox<Disziplin> combo_disziplin;

    @FXML
    private Text t_niveau;

    @FXML
    private ChoiceBox<Niveau> combo_niveau;


    @FXML
    private JFXButton b_klasseSpeichern;

    @FXML
    public ComboBox<AnzahlRunden> combo_anzahlRunden = new ComboBox<>();


    private static int index_anzahlRunden=0;



    @FXML
    public void comboBoxFill() throws IOException {
        try{
            combo_niveau.setItems(FXCollections.observableArrayList(Niveau.values()));
            combo_disziplin.setItems(FXCollections.observableArrayList(Disziplin.values()));


            //combo_anzahlRunden.getItems().setAll("1", "2", "3");
            combo_niveau.getSelectionModel().select(0);
            combo_disziplin.getSelectionModel().select(0);
            //combo_anzahlRunden.getSelectionModel().select(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    private void pressBtn_KlasseSpeichern(ActionEvent event) throws IOException
    {



        Spielklasse spklasse = new Spielklasse(combo_disziplin.getValue(),Niveau.valueOf(String.valueOf(combo_niveau.getValue())), auswahlklasse.getAktuelleTurnierAuswahl());
        spklasse.getSpielklasseDAO().create(spklasse);
        auswahlklasse.getAktuelleTurnierAuswahl().addObs_spielklassen(spklasse);
        auswahlklasse.getAktuelleTurnierAuswahl().getSpielklassen().put(spklasse.getSpielklasseID(),spklasse);
        //a.getAktuelleTurnierAuswahl().addObs_spielklassen(spklasse);
        System.out.println("------------------Größe = "+auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().size());


        auswahlklasse.InfoBenachrichtigung("erf","klasse erstellt");
        auswahlklasse.getKlassenuebersichtController().SpielklassenHinzufuegen();
        auswahlklasse.getDashboardController().setNodeKlassenuebersicht();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        auswahlklasse.setKlasse_hinzufügenController(this);
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_disziplin");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_disziplin.setText(titel);

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("t_niveau");
        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
        t_niveau.setText(titel);



        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
            titel = bundle.getString("b_klasseSpeichern");
            b_klasseSpeichern.setText(titel);

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }

        try {

            comboBoxFill();
            combo_niveau.getSelectionModel().select(0);
            combo_disziplin.getSelectionModel().select(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
