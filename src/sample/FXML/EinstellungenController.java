package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenController implements Initializable {
    @FXML
    private AnchorPane parentPane;

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
    }

    private void DesignLaden() {
        rdGewinner.setSelectedColor(Color.AQUA);
        rdVerlierer.setSelectedColor(Color.AQUA);
        rdAus.setSelectedColor(Color.AQUA);
    }
}
