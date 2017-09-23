package sample.FXML.Visualisierung;

import com.sun.javafx.scene.control.skin.Utils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.*;
import sample.DAO.auswahlklasse;
import sample.Spielsysteme.Spielsystem;

import java.lang.reflect.Array;
import java.util.*;

public class GruppenTabelle implements Visualisierung {
    private Spielsystem spielsystem;
    private Tab tab;
    private int xObenLinksLeereZelle = 20;
    private int yObenLinksLeereZelle = 20;
    private int zellenHoehePlatzierungsTabelle = 20;
    private int zellenBreite = 150;
    private int zellenHoehe = 50;
    private int xObenLinks = xObenLinksLeereZelle+zellenBreite; //Startpunkt
    private int yObenLinks = 20;

    public GruppenTabelle(Spielsystem spielsystem, Tab tab) {
        this.spielsystem = spielsystem;
        this.tab = tab;
        spielsystem.setVisualisierung(this);
    }

    public void update(){
        erstelleGruppenTabelle();
    }

    public void drucken(){

    }

    public void erstelleGruppenTabelle() {

        ArrayList<Team> teams = (ArrayList<Team>) spielsystem.getSetzliste().clone();
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            if (team.isFreilos()) {
                teams.remove(team);
            }
        }
        Font schriftart = new Font("Calibri",12);

        //int anzahlSpiele = spielklasse.getSpiele().size();
        //double anzahlTeilnehmerDouble = (((Math.sqrt(1 + anzahlSpiele * 2 * 4)) / 2 * 2) + 1) / 2;
        int anzahlTeilnehmer = teams.size();



        Canvas spieluebersicht = new Canvas(2000, 2000);
        GraphicsContext gc = spieluebersicht.getGraphicsContext2D();
        ScrollPane scrollPane = new ScrollPane();
        tab.setContent(scrollPane);
        scrollPane.setContent(spieluebersicht);

        //Leere Zelle oben links erstellen
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.beginPath();
        gc.moveTo(xObenLinksLeereZelle, yObenLinksLeereZelle);
        gc.lineTo(xObenLinksLeereZelle + zellenBreite, yObenLinksLeereZelle);
        gc.lineTo(xObenLinksLeereZelle + zellenBreite, yObenLinksLeereZelle + zellenHoehe);
        gc.lineTo(xObenLinksLeereZelle, yObenLinksLeereZelle + zellenHoehe);
        gc.lineTo(xObenLinksLeereZelle, yObenLinksLeereZelle);
        gc.stroke();
        gc.closePath();


        //erste Zeile Tabelle


        for (int i = 0; i < teams.size(); i++) {

            Team aktuellesTeam = teams.get(i);
            if (!aktuellesTeam.isFreilos()) {
                gc.beginPath();
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(1);
                gc.moveTo(xObenLinks + i * zellenBreite, yObenLinks - i * zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite + zellenBreite, yObenLinks - i * zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite + zellenBreite, yObenLinks - i * zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks + i * zellenBreite, yObenLinks - i * zellenHoehe + zellenHoehe);
                //gc.lineTo(xObenLinks + i * zellenBreite, yObenLinks - i * zellenHoehe);
                if (aktuellesTeam.isEinzel()) {
                    Font gepruefteSchriftart = textPruefen(aktuellesTeam.toString(), schriftart);
                    Text text = new Text(aktuellesTeam.toString());
                    text.setFont(gepruefteSchriftart);
                    double textbreite = text.getBoundsInLocal().getWidth();
                    double xstart = xObenLinks + i * zellenBreite + (zellenBreite - textbreite) / 2;
                    gc.setFont(gepruefteSchriftart);
                    //gc.fillText(aktuellesTeam.toString(), xstart, yObenLinks + 42);
                    gc.fillText(aktuellesTeam.toString(), xstart, yObenLinks - i * zellenHoehe + 30);
                }
                else {
                    String string = aktuellesTeam.getSpielerEins().toString();
                    Font gepruefteSchriftart = textPruefen(string,schriftart);
                    Text text = new Text(string);
                    text.setFont(gepruefteSchriftart);
                    double textbreite = text.getBoundsInLocal().getWidth();
                    double xstart = xObenLinks + i * zellenBreite + (zellenBreite - textbreite) / 2;
                    gc.setFont(gepruefteSchriftart);
                    gc.fillText(string,xstart,yObenLinks - i * zellenHoehe + 20);

                    string = aktuellesTeam.getSpielerZwei().toString();
                    gepruefteSchriftart = textPruefen(string,schriftart);
                    text = new Text(string);
                    text.setFont(gepruefteSchriftart);
                    textbreite = text.getBoundsInLocal().getWidth();
                    xstart = xObenLinks + i * zellenBreite + (zellenBreite - textbreite) / 2;
                    gc.setFont(gepruefteSchriftart);
                    gc.fillText(string,xstart,yObenLinks - i * zellenHoehe + 40);
                }


                gc.stroke();
                gc.closePath();


                gc.beginPath();
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(1);
                //gc.moveTo(xObenLinks - zellenBreite, yObenLinks + zellenHoehe);
                gc.moveTo(xObenLinks, yObenLinks + zellenHoehe);
                gc.lineTo(xObenLinks, yObenLinks + zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks - zellenBreite, yObenLinks + zellenHoehe + zellenHoehe);
                gc.lineTo(xObenLinks - zellenBreite, yObenLinks + zellenHoehe);
                if(aktuellesTeam.isEinzel()) {
                    String string = aktuellesTeam.toString();
                    Font gepruefteSchriftart = textPruefen(string,schriftart);
                    Text text = new Text(string);
                    text.setFont(gepruefteSchriftart);
                    double textbreite = text.getBoundsInLocal().getWidth();
                    double xstart = xObenLinks - zellenBreite + (zellenBreite-textbreite)/2;
                    gc.setFont(gepruefteSchriftart);
                    //gc.fillText(aktuellesTeam.toString(), xstart, yObenLinks + 42);
                    gc.fillText(string, xstart, yObenLinks + zellenHoehe + 30);
                }
                else{
                    String string = aktuellesTeam.getSpielerEins().toString();
                    Font gepruefteSchriftart = textPruefen(string,schriftart);
                    Text text = new Text(string);
                    text.setFont(gepruefteSchriftart);
                    double textbreite = text.getBoundsInLocal().getWidth();
                    double xstart = xObenLinks - zellenBreite + (zellenBreite-textbreite)/2;
                    gc.setFont(gepruefteSchriftart);
                    gc.fillText(string,xstart,yObenLinks+zellenHoehe+20);

                    string = aktuellesTeam.getSpielerZwei().toString();
                    gepruefteSchriftart = textPruefen(string,schriftart);
                    text = new Text(string);
                    text.setFont(gepruefteSchriftart);
                    textbreite = text.getBoundsInLocal().getWidth();
                    xstart = xObenLinks - zellenBreite + (zellenBreite-textbreite)/2;
                    gc.setFont(gepruefteSchriftart);
                    gc.fillText(string,xstart,yObenLinks+zellenHoehe+40);
                }
                //gc.fillText(aktuellesTeam.toString(), xObenLinks - zellenBreite + 40, yObenLinks + zellenHoehe + 30);
                gc.stroke();
                gc.closePath();


                //Titelreihe und Titelspalte erstellen

                yObenLinks += zellenHoehe;
            }

        }

        yObenLinks -= (anzahlTeilnehmer-1)*zellenHoehe;
        for (int zeile = 0; zeile < anzahlTeilnehmer; zeile++) {
            for (int spalte = 0; spalte < anzahlTeilnehmer; spalte++) {
                //Hier die Zellen der Tabelle erstellen
                Team aktuellesTeamEins = teams.get(zeile);
                Team aktuellesTeamZwei = teams.get(spalte);
                if (!aktuellesTeamEins.isFreilos() && !aktuellesTeamZwei.isFreilos()) {
                    gc.beginPath();
                    gc.setStroke(Color.GREEN);
                    gc.setLineWidth(1);
                    gc.moveTo(xObenLinks + spalte * zellenBreite, yObenLinks + zellenHoehe + zeile * zellenHoehe);
                    gc.lineTo(xObenLinks + spalte * zellenBreite + zellenBreite, yObenLinks + zellenHoehe + zeile * zellenHoehe);
                    gc.lineTo(xObenLinks + spalte * zellenBreite + zellenBreite, yObenLinks + zeile * zellenHoehe);
                    if (zeile!=spalte) {
                        String ergebnisString = ergebnisStringGenerieren(aktuellesTeamEins, aktuellesTeamZwei);

                        Font gepruefteSchriftart = textPruefen(ergebnisString,schriftart);
                        Text text = new Text(ergebnisString);
                        text.setFont(gepruefteSchriftart);
                        double textbreite = text.getBoundsInLocal().getWidth();
                        double xstart = xObenLinks + spalte * zellenBreite + (zellenBreite-textbreite)/2;
                        gc.setFont(gepruefteSchriftart);

                        gc.fillText(ergebnisString, xstart, yObenLinks + 30 + zeile * zellenHoehe);
                    }
                    else{
                        gc.moveTo(xObenLinks + spalte * zellenBreite, yObenLinks + zeile * zellenHoehe);
                        gc.lineTo(xObenLinks + spalte * zellenBreite + zellenBreite, yObenLinks + zellenHoehe + zeile * zellenHoehe);
                        gc.moveTo(xObenLinks + 50 + spalte * zellenBreite, yObenLinks + zeile * zellenHoehe);
                        gc.lineTo(xObenLinks + spalte * zellenBreite + zellenBreite, yObenLinks - 17 + zellenHoehe + zeile * zellenHoehe);
                        gc.moveTo(xObenLinks + spalte * zellenBreite, yObenLinks + zeile * zellenHoehe + 18);
                        gc.lineTo(xObenLinks + 100 + spalte * zellenBreite , yObenLinks + zellenHoehe + zeile * zellenHoehe);

                        gc.moveTo(xObenLinks + 100 + spalte * zellenBreite, yObenLinks + zeile * zellenHoehe);
                        gc.lineTo(xObenLinks + spalte * zellenBreite + zellenBreite, yObenLinks - 34 + zellenHoehe + zeile * zellenHoehe);
                        gc.moveTo(xObenLinks + spalte * zellenBreite, yObenLinks + zeile * zellenHoehe + 34);
                        gc.lineTo(xObenLinks + 50 + spalte * zellenBreite , yObenLinks + zellenHoehe + zeile * zellenHoehe);

                    }
                    gc.stroke();
                    gc.closePath();


                }
            }
        }
        int xObenLinksPlatzierungsTabelle = xObenLinksLeereZelle;
        int yObenLinksPlatzierungsTabelle = yObenLinksLeereZelle+zellenHoehe*2+anzahlTeilnehmer*zellenHoehe;
        int xPlatzBreite = 20;
        int xTeamSpielerBreite = 200;
        int xSpieleBreite = 45;
        int xSaetzeBreite = 45;
        int xPunkteBreite=45;

        PlatzierungsTabelle platzierungsTabelle = new PlatzierungsTabelle(xObenLinksPlatzierungsTabelle,
                yObenLinksPlatzierungsTabelle,
                zellenHoehePlatzierungsTabelle,
                xPlatzBreite,
                xTeamSpielerBreite,
                xSpieleBreite,
                xSaetzeBreite,
                xPunkteBreite,
                spielsystem,
                gc);
        yObenLinks-=zellenHoehe;
    }


    private String ergebnisStringGenerieren(Team team1, Team team2) {
        String ergebnisString = "";
        ArrayList<ZeitplanRunde> runden = spielsystem.getRunden();
        for (int i=0;i<runden.size();i++){
            for (int j=0;j<runden.get(i).size();j++){
                Spiel spiel = runden.get(i).get(j);
                if (spiel.istTeamInSpiel(team1)&&spiel.istTeamInSpiel(team2)){
                    if(spiel.getErgebnis()!=null) {
                        return spiel.getErgebnisString(team1); //HeimTeam angeben
                    }
                    else{
                        return "Runde "+(i+1);
                    }
                }
            }
        }


        return ergebnisString;
    }
    private Font textPruefen(String string, Font schriftart){

        Text text = new Text(string);
        text.setFont(schriftart);
        double textbreite = text.getBoundsInLocal().getWidth();
        while(textbreite>zellenBreite){
            schriftart = new Font(schriftart.getName(),schriftart.getSize()-1);
            text.setFont(schriftart);
            textbreite = text.getBoundsInLocal().getWidth();
        }
        return schriftart;
    }






}
