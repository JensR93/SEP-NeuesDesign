package sample.FXML.Visualisierung;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.print.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.*;
import sample.DAO.auswahlklasse;
import sample.Spielsysteme.KO;
import sample.Spielsysteme.Spielsystem;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Optional;

public class Turnierbaum implements Visualisierung {

    private int xObenLinks = 20; //Startpunkt
    private int yObenLinks = 20;
    private int breite = 200;
    private int hoehe = 50;
    private int xAbstand = 100;
    private int yAbstand = 20;
    private Spielsystem spielsystem;
    private Canvas canvas;
    private Tab tab;
    private Spiel spielUmPlatz3;

    public Turnierbaum(int xObenLinks, int yObenLinks, int breite, int hoehe, int xAbstand, int yAbstand, Tab tab) {
        this.xObenLinks = xObenLinks;
        this.yObenLinks = yObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.xAbstand = xAbstand;
        this.yAbstand = yAbstand;
        this.tab = tab;
    }

    public void erstelleTurnierbaum(Spielsystem spielsystem) {
        this.spielsystem=spielsystem;
        KO kosystem = (KO) spielsystem;
        spielsystem.setVisualisierung(this);
        ArrayList<ZeitplanRunde> runden = spielsystem.getRunden();
        if (kosystem.getSpielUm3()!=null){
            spielUmPlatz3 = kosystem.getSpielUm3();
            runden.get(runden.size()-1).remove(spielUmPlatz3);
        }
        int gesamtHoehe =runden.get(0).size()*(hoehe+yAbstand)+yObenLinks+2-yAbstand;
        int gesamtBreite = runden.size()*(breite+xAbstand)+xObenLinks+2-xAbstand;
        if(runden.size()<3){
            gesamtHoehe +=hoehe;
        }
        int yObenLinks = this.yObenLinks;
        this.canvas = new Canvas(gesamtBreite, gesamtHoehe);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        Button drucken = new Button("drucken");
        drucken.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drucken();
            }
        });
        VBox vBox = new VBox();
        scrollPane.setContent(vBox);
        vBox.getChildren().add(drucken);
        vBox.getChildren().add(canvas);


        ArrayList<TurnierbaumSpiel> letzteRunde = new ArrayList<>();
        for(int j=0; j<runden.get(0).size();j++){
            Spiel aktuellesSpiel = runden.get(0).get(j);
            TurnierbaumSpiel turnierbaumSpiel = new TurnierbaumSpiel(xObenLinks,yObenLinks,breite,hoehe,aktuellesSpiel,xAbstand, yAbstand);
            turnierbaumSpiel.draw(gc);
            yObenLinks += hoehe + yAbstand;
            letzteRunde.add(turnierbaumSpiel);
        }

        for(int i=0;i<letzteRunde.size();i++){
            if(i%2==0){
                TurnierbaumSpiel neuesSpiel = letzteRunde.get(i).neuesSpielerstellen(gc);
                if (neuesSpiel!=null) {
                    neuesSpiel.draw(gc);
                    letzteRunde.add(neuesSpiel);
                }
            }
            else{
                letzteRunde.get(i).linieZuNaechstemSpiel(letzteRunde.get(i),letzteRunde.get(letzteRunde.size()-1),gc);
            }
        }
        if(spielUmPlatz3!=null){
            int rundenAnzahl = runden.size()-1;
            int xObenLinksSU3 = xObenLinks+rundenAnzahl*breite+rundenAnzahl*xAbstand;
            int yObenLinksSU3 = (gesamtHoehe/4)*3;
            if(rundenAnzahl<2){
                yObenLinksSU3 = gesamtHoehe-hoehe-2;
            }
            TurnierbaumSpiel spielUmDrei = new TurnierbaumSpiel(xObenLinksSU3,yObenLinksSU3,breite,hoehe,spielUmPlatz3,xAbstand,yAbstand);
            spielUmDrei.draw(gc);
            gc.setTextBaseline(VPos.BOTTOM);
            Font schriftArt = new Font("Calibri",12);
            Text text = new Text("Spiel um Platz 3");
            text.setFont(schriftArt);
            double textbreite = text.getLayoutBounds().getWidth();
            gc.fillText("Spiel um Platz 3",xObenLinksSU3+(breite-textbreite)/2,yObenLinksSU3-5);
        }
    }

    public ArrayList<Canvas> erstelleTurnierbaum(Spielsystem spielsystem, double papierBreite, double papierHoehe) {
        ArrayList<Canvas> alleSeiten = new ArrayList<>();
        ArrayList<ZeitplanRunde> runden = spielsystem.getRunden();
        //int gesamtHoehe =runden.get(0).size()*(hoehe+yAbstand)+yObenLinks+2-yAbstand;
        //int gesamtBreite = runden.size()*(breite+xAbstand)+xObenLinks+2-xAbstand;
        Canvas canvas = new Canvas(papierBreite, papierHoehe);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //papierBreite = 200;
        //papierHoehe = 50;
        int papierbreite = (int) papierBreite;
        int papierhöhe = (int) papierHoehe;

        int xObenLinks = papierbreite/20;
        int yObenLinks = papierhöhe/20;
        int breite = (papierbreite-xObenLinks)/(runden.size()*2);
        int hoehe = (papierhöhe-yObenLinks)/(runden.get(0).size()*2);
        int xAbstand = (papierbreite-xObenLinks)/(runden.size()*2);
        int yAbstand = (papierhöhe-yObenLinks)/(runden.get(0).size()*2);

        /*int xObenLinks = 20;
        int yObenLinks = 20;
        int breite = 200;
        int hoehe = 50;
        int xAbstand = 100;
        int yAbstand = 20;*/


        ArrayList<TurnierbaumSpiel> letzteRunde = new ArrayList<>();
        for(int j=0; j<runden.get(0).size();j++){
            Spiel aktuellesSpiel = runden.get(0).get(j);
            TurnierbaumSpiel turnierbaumSpiel = new TurnierbaumSpiel(xObenLinks,yObenLinks,breite,hoehe,aktuellesSpiel,xAbstand, yAbstand);
            turnierbaumSpiel.draw(gc);
            yObenLinks += hoehe + yAbstand;
            letzteRunde.add(turnierbaumSpiel);
        }
        for(int i=0;i<letzteRunde.size();i++){
            if(i%2==0){
                TurnierbaumSpiel neuesSpiel = letzteRunde.get(i).neuesSpielerstellen(gc);
                if (neuesSpiel!=null) {
                    neuesSpiel.draw(gc);
                    letzteRunde.add(neuesSpiel);
                }
            }
            else{
                letzteRunde.get(i).linieZuNaechstemSpiel(letzteRunde.get(i),letzteRunde.get(letzteRunde.size()-1),gc);
            }
        }
        alleSeiten.add(canvas);
        return alleSeiten;
    }

    @Override
    public void update() {
        erstelleTurnierbaum(spielsystem);
    }

    @Override
    public void drucken() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0,0,0,0 );
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if(printerJob!=null && (auswahlklasse.isDruckerGesetzt()||printerJob.showPrintDialog(auswahlklasse.getPrimaryStage()))){
            if (!auswahlklasse.isDruckerGesetzt()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Drucker speichern?");
                alert.setContentText("Soll der Drucker gespeichert werden?");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().add(ButtonType.YES);
                alert.getButtonTypes().add(ButtonType.NO);
                Optional<ButtonType> auswahl = alert.showAndWait();
                if(auswahl.get()==ButtonType.YES){
                    auswahlklasse.setDruckerGesetzt(true);
                }
            }
            ArrayList<Canvas> alleSeiten = erstelleTurnierbaum(spielsystem,pageLayout.getPrintableWidth(),pageLayout.getPrintableHeight());
            boolean success = true;

            for (int i=0;i<alleSeiten.size();i++){
                if (success) {
                    success = printerJob.printPage(pageLayout,alleSeiten.get(i));
                }
            }
            if (success) {
                printerJob.endJob();
            }
        }

    }
}


