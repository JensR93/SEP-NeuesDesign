package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import sample.DAO.auswahlklasse;
import sample.Verein;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 25.09.2017.
 */
public class Neuer_VereinController implements Initializable {


    private Verein updateverein;

    public Verein getUpdateverein() {
        return updateverein;
    }

    public void setUpdateverein(Verein updateverein) {
        this.updateverein = updateverein;
    }

    String baseName = "resources.Main";
    String titel ="";

    DashboardController controller;

    @FXML
    private JFXTextField tf_Name;

    @FXML
    private JFXTextField tf_Verband;

    @FXML
    private JFXTextField tf_ExtVereinsID;

    @FXML
    private JFXButton btn_Speichern_Verein;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        auswahlklasse.setNeuer_vereinController(this);

        SpracheLaden();

    }

    @FXML
    public void pressBtn_speichern(){
        String verband = tf_Verband.getText();

        String name = tf_Name.getText();

        String vereinsid = tf_ExtVereinsID.getText();

        Verein verein = new Verein(vereinsid,name,verband);

        auswahlklasse.getSpieler_hinzufuegenController().getVereine().add(verein);
        auswahlklasse.getSpieler_hinzufuegenController().neuenVereinauswaehlen();
        //hier
        auswahlklasse.getDashboardController().setNodeSpieler();
    }

    public void SpracheLaden() {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );

            titel = bundle.getString("tf_Name");
            tf_Name.setPromptText(titel);
            tf_Name.setLabelFloat(true);

            titel = bundle.getString("tf_Verband");
            tf_Verband.setPromptText(titel);
            tf_Verband.setLabelFloat(true);

            titel = bundle.getString("tf_ExtVereinsID");
            tf_ExtVereinsID.setPromptText(titel);
            tf_ExtVereinsID.setLabelFloat(true);

            titel = bundle.getString("btn_Speichern_Verein");
            btn_Speichern_Verein.setText(titel);

        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }

    }




}
