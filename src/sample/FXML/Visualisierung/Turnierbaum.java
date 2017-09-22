package sample.FXML.Visualisierung;

import javafx.print.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.DAO.auswahlklasse;
import sample.Spiel;
import sample.Spielklasse;
import sample.Spielsysteme.Spielsystem;
import sample.ZeitplanRunde;

import java.util.ArrayList;
import java.util.Dictionary;

public class Turnierbaum {

    private int xObenLinks = 20; //Startpunkt
    private int yObenLinks = 20;
    private int breite = 200;
    private int hoehe = 50;
    private int xAbstand = 100;
    private int yAbstand = 20;

    Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getObs_spielklassen().get(0);
    Dictionary<Integer, Spiel> alleSpiele = spielklasse.getSpiele();
    int anzahlSpiele = alleSpiele.size();
    double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele*2*4))/2*2)+1)/2;     //(1/2) + (((1/4) + anzahlSpiele*2)^(1/2))
    int anzahlTeilnehmer = (int) anzahlTeilnehmerDouble;
    Canvas canvas;

    private void druckeTurnierbaum(){
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0,0,0,0 );
        PrinterJob printerJob = PrinterJob.createPrinterJob();
/*        if(printerJob!=null && printerJob.showPrintDialog(auswahlklasse.getStagesdict().get("Main"))){
            boolean success = printerJob.printPage(pageLayout, canvas);
            if (success) {
                printerJob.endJob();
            }
        }*/

    }

    public Turnierbaum(int xObenLinks, int yObenLinks, int breite, int hoehe, int xAbstand, int yAbstand) {
        this.xObenLinks = xObenLinks;
        this.yObenLinks = yObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.xAbstand = xAbstand;
        this.yAbstand = yAbstand;
    }

    public void erstelleTurnierbaum(Spielsystem spielsystem, Canvas canvas) {
        ArrayList<ZeitplanRunde> runden = spielsystem.getRunden();
        int gesamtHoehe =runden.get(0).size()*(hoehe+yAbstand)+yObenLinks+2-yAbstand;
        int gesamtBreite = runden.size()*(breite+xAbstand)+xObenLinks+2-xAbstand;

        this.canvas = canvas;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setHeight(gesamtHoehe);
        canvas.setWidth(gesamtBreite);

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
}


