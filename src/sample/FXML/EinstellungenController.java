package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class EinstellungenController implements Initializable {

    String baseName = "resources.Main";
    String titel ="";

    @FXML
    private Label Label_Spieleinstellungen;
    @FXML
    private Label lab_Sprache;
    @FXML
    private Label Label_SchiriStandVerw;
    @FXML
    private Label lab_VormSchiri;
    @FXML
    private Label lab_ausstehSpiel;
    @FXML
    private Label lab_aktiveSpiele;
    @FXML
    private Label lab_gespielSpiele;
    @FXML
    private JFXButton rdVerlierer1; //Deutsch?
    @FXML
    private JFXButton rdVerlierer2; //Englisch?

    @FXML
    private GridPane gridPane;

    @FXML
    private ToggleGroup Language;

    @FXML
    private JFXRadioButton rdVerlierer;

    @FXML
    private ToggleGroup level;

    @FXML
    private JFXRadioButton rdGewinner;

    @FXML
    private JFXRadioButton rdAus;

    @FXML
    private JFXButton btnClose21;

    @FXML
    private JFXButton btnClose2111;

    @FXML
    private JFXButton btnClose21111;

    @FXML
    void exit(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DesignLaden();

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );

            titel = bundle.getString("Label_Spieleinstellungen");
            Label_Spieleinstellungen.setText(titel);

            titel = bundle.getString("lab_Sprache");
            lab_Sprache.setText(titel);

            titel = bundle.getString("Label_SchiriStandVerw");
            Label_SchiriStandVerw.setText(titel);

            titel = bundle.getString("lab_VormSchiri");
            lab_VormSchiri.setText(titel);

            titel = bundle.getString("lab_ausstehSpiel");
            lab_ausstehSpiel.setText(titel);

            titel = bundle.getString("lab_aktiveSpiele");
            lab_aktiveSpiele.setText(titel);

            titel = bundle.getString("lab_gespielSpiele");
            lab_gespielSpiele.setText(titel);

            titel = bundle.getString("rdVerlierer1");
            rdVerlierer1.setText(titel);

            titel = bundle.getString("rdVerlierer2");
            rdVerlierer2.setText(titel);

            titel = bundle.getString("rdGewinner");
            rdGewinner.setText(titel);

            titel = bundle.getString("rdVerlierer");
            rdVerlierer.setText(titel);

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }





    }

    DashboardController controller;
    public void setController(DashboardController controller)
    {
        this.controller = controller;
    }
    private void DesignLaden() {
       /* rdGewinner.setSelectedColor(Color.color(78, 106, 156, 1));
        rdVerlierer.setSelectedColor(Color.color(78, 106, 156, 1));
        rdAus.setSelectedColor(Color.color(78, 106, 156, 1));*/
    }
}
