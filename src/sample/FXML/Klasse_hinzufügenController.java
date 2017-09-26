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


    public void SpracheLaden()
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );



        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }
    }











    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpracheLaden();

        //auswahlklasse.setKlasse_hinzufügenController(this);


    }


}
