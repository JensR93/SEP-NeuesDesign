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

public class GruppenTabelle {
    private Spielsystem spielsystem;
    private Tab tab;
    private int xObenLinksLeereZelle = 20;
    private int yObenLinksLeereZelle = 20;

    private int zellenBreite = 150;
    private int zellenHoehe = 50;
    private int xObenLinks = xObenLinksLeereZelle+zellenBreite; //Startpunkt
    private int yObenLinks = 20;

    public GruppenTabelle(Spielsystem spielsystem, Tab tab) {
        this.spielsystem = spielsystem;
        this.tab = tab;
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
        int zellenBreitePlatzierungsTabelle = 50;
        int zellenHoehePlatzierungsTabelle = 20;
        platzierungsTabelleErstellen(xObenLinksPlatzierungsTabelle,yObenLinksPlatzierungsTabelle,zellenBreitePlatzierungsTabelle,zellenHoehePlatzierungsTabelle,anzahlTeilnehmer,gc);
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



    private void platzierungsTabelleErstellen(int xObenLinks, int yObenLinks, int zellenBreite, int zellenHoehe,int anzahlTeilnehmer, GraphicsContext gc){
        int xPlatz = xObenLinks;
        int xPlatzBreite = 50;
        int xTeamSpieler = xObenLinks+xPlatzBreite;
        int xTeamSpielerBreite = 150;
        int xSpiele = xTeamSpieler+xTeamSpielerBreite;
        int xSpieleBreite = 100;
        int xSaetze = xSpiele+xSpieleBreite;
        int xSaetzeBreite = 100;
        int xPunkte = xSaetze+xSaetzeBreite;
        int xPunkteBreite = 100;

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.beginPath();
        gc.moveTo(xPlatz, yObenLinks);
        gc.lineTo(xPlatz + xPlatzBreite, yObenLinks);
        gc.lineTo(xPlatz + xPlatzBreite,  yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks);
        gc.fillText("Platz", xPlatz+5,yObenLinks+14);

        gc.moveTo(xTeamSpieler, yObenLinks);
        gc.lineTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks);
        gc.lineTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks + zellenHoehe);
        gc.lineTo(xTeamSpieler, yObenLinks + zellenHoehe);
        gc.fillText("Team / Spieler", xTeamSpieler + 5,yObenLinks+14);

        gc.moveTo(xSpiele, yObenLinks);
        gc.lineTo(xSpiele + xSpieleBreite, yObenLinks);
        gc.lineTo(xSpiele + xSpieleBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSpiele , yObenLinks+zellenHoehe);
        gc.fillText("Spiele G/V", xSpiele + 5,yObenLinks+14);

        gc.moveTo(xSaetze, yObenLinks);
        gc.lineTo(xSaetze +xSaetzeBreite, yObenLinks);
        gc.lineTo(xSaetze +xSaetzeBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSaetze, yObenLinks+zellenHoehe);
        gc.fillText("Sätze", xSaetze + 5,yObenLinks+14);

        gc.moveTo(xPunkte, yObenLinks);
        gc.lineTo(xPunkte + xPunkteBreite, yObenLinks);
        gc.lineTo(xPunkte + xPunkteBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xPunkte, yObenLinks+zellenHoehe);
        gc.fillText("Punkte", xPunkte + 5,yObenLinks+14);

        gc.stroke();
        gc.closePath();



        ArrayList<Team> platzierungsliste = spielsystem.getSetzliste();
        for (int i=0; i<platzierungsliste.size();i++){
            Team team = platzierungsliste.get(i);
            if(team.isFreilos()){
                platzierungsliste.remove(team);
            }
        }
        platzierungsliste.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i=0;i<platzierungsliste.size();i++){
            teamZeileErstellen(xObenLinks,yObenLinks+(i+1)*zellenHoehe,zellenHoehe,zellenBreite,platzierungsliste.get(i),i+1,gc);
        }


    }
    private void teamZeileErstellen(int xObenLinks, int yObenLinks,int zellenHoehe,int zellenBreite, Team team,int platzierung, GraphicsContext gc){
        int gespielteSpiele = team.getBisherigeGegner().size();
        int gewonneneSpiele = team.getGewonneneSpiele();
        int gewonnenePunkte = team.getGewonnnenePunkte();
        int verlorenePunkte = team.getVerlorenePunkte();

        int xPlatz = xObenLinks;
        int xPlatzBreite = 50;
        int xTeamSpieler = xObenLinks+xPlatzBreite;
        int xTeamSpielerBreite = 150;
        int xSpiele = xTeamSpieler+xTeamSpielerBreite;
        int xSpieleBreite = 100;
        int xSaetze = xSpiele+xSpieleBreite;
        int xSaetzeBreite = 100;
        int xPunkte = xSaetze+xSaetzeBreite;
        int xPunkteBreite = 100;

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        gc.moveTo(xPlatz + xPlatzBreite, yObenLinks);
        gc.lineTo(xPlatz + xPlatzBreite,  yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks + zellenHoehe);
        gc.lineTo(xPlatz, yObenLinks);
        gc.fillText(platzierung+".", xPlatz+5,yObenLinks+14);

        gc.moveTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks);
        gc.lineTo(xTeamSpieler + xTeamSpielerBreite, yObenLinks + zellenHoehe);
        gc.lineTo(xTeamSpieler, yObenLinks + zellenHoehe);
        gc.fillText(team.toString(), xTeamSpieler + 5,yObenLinks+14);

        gc.moveTo(xSpiele + xSpieleBreite, yObenLinks);
        gc.lineTo(xSpiele + xSpieleBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSpiele , yObenLinks+zellenHoehe);
        gc.fillText(gewonneneSpiele+":"+(gespielteSpiele-gewonneneSpiele), xSpiele + xSpieleBreite/2 - 10,yObenLinks+14);


        gc.moveTo(xSaetze +xSaetzeBreite, yObenLinks);
        gc.lineTo(xSaetze +xSaetzeBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xSaetze, yObenLinks+zellenHoehe);
        gc.fillText("gewonneneSaetze:verloreneSaetze", xSaetze + xSaetzeBreite/2 - 10,yObenLinks+14);


        gc.moveTo(xPunkte + xPunkteBreite, yObenLinks);
        gc.lineTo(xPunkte + xPunkteBreite, yObenLinks+zellenHoehe);
        gc.lineTo(xPunkte, yObenLinks+zellenHoehe);
        gc.fillText(gewonnenePunkte+":"+verlorenePunkte, xPunkte + xPunkteBreite/2 - 10,yObenLinks+14);

        gc.stroke();
        gc.closePath();

    }


}
