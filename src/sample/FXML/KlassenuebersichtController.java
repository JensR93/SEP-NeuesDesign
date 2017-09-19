package sample.FXML;

import com.sun.org.apache.bcel.internal.generic.IndexedInstruction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 19.09.2017.
 */
public class KlassenuebersichtController implements Initializable {
    DashboardController controller;
    public void setController(DashboardController controller)
    {
        this.controller = controller;
    }

    @FXML
    void setNodeNeueKlasse(ActionEvent event) {
controller.setNodeKlassehinzufuegen();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
