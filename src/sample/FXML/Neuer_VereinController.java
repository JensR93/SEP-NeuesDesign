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






    String baseName = "resources.Main";
    String titel ="";

    DashboardController controller;






    @Override
    public void initialize(URL location, ResourceBundle resources) {



        SpracheLaden();


    }

    @FXML
    public void pressBtn_Abbrechen(ActionEvent event)
    {
        auswahlklasse.getDashboardController().setNodeVereinsuebersicht();
}


    public void SpracheLaden() {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( baseName );




        }
        catch ( MissingResourceException e ) {
            System.err.println( e );
        }

    }




}
