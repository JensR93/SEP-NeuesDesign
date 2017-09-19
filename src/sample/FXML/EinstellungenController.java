package sample.FXML;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class EinstellungenController implements Initializable {
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
DashboardController controller;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DesignLaden();
    }
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
