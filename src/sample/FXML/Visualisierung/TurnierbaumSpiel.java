package sample.FXML.Visualisierung;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Ergebnis;
import sample.Spiel;
import sample.Team;

public class TurnierbaumSpiel {
    private int yObenLinks;
    private int xObenLinks;
    private int breite;
    private int hoehe;
    private Spiel spiel;
    private int xAbstand;
    private int yAbstand;


    /* public TurnierbaumSpiel(int xObenLinks, int yObenLinks, int breite, int hoehe, Spiel spiel) {
         this.yObenLinks = yObenLinks;
         this.xObenLinks = xObenLinks;
         this.breite = breite;
         this.hoehe = hoehe;
         this.spiel = spiel;
     }
 */
    public TurnierbaumSpiel(int xObenLinks, int yObenLinks, int breite, int hoehe, Spiel spiel, int xAbstand, int yAbstand) {
        this.yObenLinks = yObenLinks;
        this.xObenLinks = xObenLinks;
        this.breite = breite;
        this.hoehe = hoehe;
        this.spiel = spiel;
        this.xAbstand=xAbstand;
        this.yAbstand=yAbstand;
    }

    public int getXRechtsMitte(){
        return xObenLinks+breite;
    }
    public int getYRechtsMitte(){
        return yObenLinks+(hoehe/2);
    }
    public int getXLinksMitte(){
        return xObenLinks;
    }
    public int getYLinksMitte(){
        return yObenLinks+hoehe/2;
    }

    public void draw(GraphicsContext gc){
        Font schriftart = new Font("Calibri",12);
        Font fetteschriftart = new Font ("Calibri Bold", 12);
        /*for (int i=0;i<Font.getFontNames().size();i++){
            System.out.println(Font.getFontNames().get(i));
        }*/
        gc.setFill(Color.BLACK);
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.moveTo(xObenLinks, yObenLinks);
        gc.lineTo(xObenLinks+breite, yObenLinks);
        gc.lineTo(xObenLinks+breite, yObenLinks+hoehe);
        gc.lineTo(xObenLinks, yObenLinks+hoehe);
        gc.lineTo(xObenLinks, yObenLinks);
        gc.stroke();
        gc.closePath();
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(xObenLinks,yObenLinks+ hoehe*0.5);
        gc.lineTo(xObenLinks+breite,yObenLinks+hoehe*0.5);
        gc.stroke();
        gc.closePath();

        gc.setFont(schriftart);
        gc.setTextBaseline(VPos.BOTTOM);
        if (spiel.getHeim()==spiel.getSieger()&&spiel.getHeim()!=null) {
            Font gepruefteSchriftart = textPruefen(spiel.getHeimString(),fetteschriftart);
            Text text = new Text(spiel.getHeimString());
            text.setFont(gepruefteSchriftart);
            double textbreite = text.getBoundsInLocal().getWidth();
            double texthoehe = text.getBoundsInLocal().getHeight();
            gc.setFont(gepruefteSchriftart);
            if(texthoehe>(hoehe/2)){
                Font font = new Font(gepruefteSchriftart.getName(),hoehe/2 -1);
                gc.setFont(font);
                text.setFont(font);
                texthoehe = text.getBoundsInLocal().getHeight();
                textbreite = text.getBoundsInLocal().getWidth();
            }
            double xstart = xObenLinks + (breite-textbreite)/2;
            gc.fillText(spiel.getHeimString(), xstart, yObenLinks + ((hoehe/2-texthoehe))/2 + texthoehe);
        }
        else{
            Font gepruefteSchriftart = textPruefen(spiel.getHeimString(),schriftart);
            Text text = new Text(spiel.getHeimString());
            text.setFont(gepruefteSchriftart);
            double textbreite = text.getBoundsInLocal().getWidth();
            double texthoehe = text.getBoundsInLocal().getHeight();

            gc.setFont(gepruefteSchriftart);
            if(texthoehe>(hoehe/2)){
                Font font = new Font(gepruefteSchriftart.getName(),hoehe/2 -1);
                gc.setFont(font);
                text.setFont(font);
                texthoehe = text.getBoundsInLocal().getHeight();
                textbreite = text.getBoundsInLocal().getWidth();
            }
            double xstart = xObenLinks + (breite-textbreite)/2;
            gc.fillText(spiel.getHeimString(), xstart, yObenLinks + ((hoehe/2-texthoehe))/2 + texthoehe);
        }

        if (spiel.getGast()==spiel.getSieger()&&spiel.getGast()!=null) {
            Font gepruefteSchriftart = textPruefen(spiel.getGastString(),fetteschriftart);
            Text text = new Text(spiel.getGastString());
            text.setFont(gepruefteSchriftart);
            double textbreite = text.getBoundsInLocal().getWidth();
            double texthoehe = text.getBoundsInLocal().getHeight();

            gc.setFont(gepruefteSchriftart);
            if(texthoehe>(hoehe/2)){
                Font font = new Font(gepruefteSchriftart.getName(),hoehe/2 -1);
                gc.setFont(font);
                text.setFont(font);
                texthoehe = text.getBoundsInLocal().getHeight();
                textbreite = text.getBoundsInLocal().getWidth();
            }
            double xstart = xObenLinks + (breite-textbreite)/2;
            gc.fillText(spiel.getGastString(), xstart, yObenLinks + (hoehe/2) + ((hoehe/2-texthoehe))/2 + texthoehe);
        }
        else{
            Font gepruefteSchriftart = textPruefen(spiel.getGastString(),schriftart);
            Text text = new Text(spiel.getGastString());
            text.setFont(gepruefteSchriftart);
            double textbreite = text.getBoundsInLocal().getWidth();
            double texthoehe = text.getBoundsInLocal().getHeight();
            gc.setFont(gepruefteSchriftart);
            if(texthoehe>(hoehe/2)){
                Font font = new Font(gepruefteSchriftart.getName(),hoehe/2 -1);
                gc.setFont(font);
                text.setFont(font);
                texthoehe = text.getBoundsInLocal().getHeight();
                textbreite = text.getBoundsInLocal().getWidth();
            }
            double xstart = xObenLinks + (breite-textbreite)/2;
            gc.fillText(spiel.getGastString(), xstart, yObenLinks + (hoehe/2) + ((hoehe/2-texthoehe))/2 + texthoehe);
        }

    }

    private Font textPruefen(String string, Font schriftart){

        Text text = new Text(string);
        text.setFont(schriftart);
        double textbreite = text.getBoundsInLocal().getWidth();
        while(textbreite>breite){
            schriftart = new Font(schriftart.getName(),schriftart.getSize()-1);
            text.setFont(schriftart);
            textbreite = text.getBoundsInLocal().getWidth();
        }
        return schriftart;
    }



    public void linieZuNaechstemSpiel(TurnierbaumSpiel spiel1, TurnierbaumSpiel spiel2, GraphicsContext gc){
        Ergebnis ergebnis = spiel.getErgebnis();
        int xStart = spiel1.getXRechtsMitte();
        int yStart = spiel1.getYRechtsMitte();
        int xEnde = spiel2.getXLinksMitte();
        int yEnde = spiel2.getYLinksMitte();
        int xWendePunkt = (int) (xStart+ 0.8*(xEnde-xStart));
        gc.beginPath();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(xStart, yStart);
        gc.lineTo(xWendePunkt, yStart);
        gc.lineTo(xWendePunkt, yEnde);
        gc.lineTo(xEnde, yEnde);
        gc.stroke();
        gc.closePath();
        gc.setTextBaseline(VPos.BASELINE);
        Font schriftart = new Font("Calibri",12);
        //gc.fillText(ergebnis.getErgebnisArray().toString(),xWendePunkt,yStart);
        if(ergebnis!=null){
            int[] ergebnisarray = ergebnis.getErgebnisArray(); //[0] = satz1Heim [1] = satz1Gast [2] = satz2Heim.....
            for(int i=0;i<ergebnisarray.length;i++){
                int satzpunkte = ergebnisarray[i];
                int xzusatz =0;
                if (satzpunkte+"".length()<2){
                    xzusatz =3;
                }
                if(i%2==0){ //heimsätze
                    gc.setTextBaseline(VPos.BOTTOM);
                    gc.fillText(satzpunkte+"",xStart+xzusatz+5+i*10,yStart);
                }
                else{ //gastsätze
                    gc.setTextBaseline(VPos.TOP);
                    gc.fillText(satzpunkte+"",xStart+xzusatz+5+(i-1)*10 ,yStart+2);
                }
            }
        }


    }

    public TurnierbaumSpiel neuesSpielerstellen(GraphicsContext gc) {
        int neuXObenLinks = xObenLinks+breite+xAbstand;
        int neuYObenLinks = yObenLinks +(((2*hoehe+yAbstand)/2)-hoehe/2);
        int neueSystemSpielID = neueSystemSpielIDberechnen() ;
        int neuerYAbstand = 2*yAbstand+hoehe;
        Spiel neuesSpiel = spiel.getSpielsystem().getSpielklasse().getSpiele().get(neueSystemSpielID);
        if (neuesSpiel!=null) {
            TurnierbaumSpiel turnierbaumSpiel = new TurnierbaumSpiel(neuXObenLinks, neuYObenLinks, breite, hoehe, neuesSpiel, xAbstand, neuerYAbstand);
            linieZuNaechstemSpiel(this,turnierbaumSpiel,gc);
            return turnierbaumSpiel;
        }
        else{
            return null;
        }
    }

    private int neueSystemSpielIDberechnen() {
        int alteSystemSpielID = spiel.getSystemSpielID();
        int neueSystemSpielID = alteSystemSpielID - spiel.getSpielsystem().getSpielSystemArt()*10000000;
        int alteRundenNummer = (neueSystemSpielID/1000);
        neueSystemSpielID = neueSystemSpielID - alteRundenNummer*1000;
        neueSystemSpielID = ((int)neueSystemSpielID/2);
        neueSystemSpielID = neueSystemSpielID+(alteRundenNummer-1)*1000;
        neueSystemSpielID += spiel.getSpielsystem().getSpielSystemArt()*10000000;
        return neueSystemSpielID;
    }
}