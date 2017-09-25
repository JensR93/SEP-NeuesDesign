package sample.FXML.Visualisierung;

import javafx.print.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.*;
import sample.DAO.auswahlklasse;
import sample.Spielsysteme.Spielsystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

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
        spielsystem.setVisualisierung(this);
        ArrayList<ZeitplanRunde> runden = spielsystem.getRunden();
        int gesamtHoehe =runden.get(0).size()*(hoehe+yAbstand)+yObenLinks+2-yAbstand;
        int gesamtBreite = runden.size()*(breite+xAbstand)+xObenLinks+2-xAbstand;
        int yObenLinks = this.yObenLinks;
        this.canvas = new Canvas(gesamtBreite, gesamtHoehe);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        scrollPane.setContent(canvas);

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
        //druckeTurnierbaum();
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
        if(printerJob!=null){
            boolean success = printerJob.printPage(pageLayout, canvas);
            if (success) {
                printerJob.endJob();
            }
        }

    }
}


