package sample;

import java.util.ArrayList;

/**
 * Created by Florian-PC on 20.09.2017.
 */
public class ZeitplanRunde extends ArrayList<Spiel>{
    public Integer getAnzahlSpiele(){
        return this.size();
    }
    public String getRundenName(){
        String rundenName ="";
        if(this.get(0) != null){
            Spiel spiel = this.get(0);
            rundenName += disziplinKurzform(spiel);
            rundenName += "-";
            rundenName += spiel.getSpielsystem().getSpielklasse().getNiveau();
            rundenName += " ";
            rundenName += spiel.getRundenName();
        }
        return rundenName;
    }
    public Integer getRundenNummer(){
        Integer rundenNummer = null;
        if(this.get(0)!=null){
            rundenNummer = this.get(0).getRundenZeitplanNummer();
        }
        return rundenNummer;
    }
    private String disziplinKurzform(Spiel spiel){
        String disziplin = spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase();
        String kurzform = "";
        if(disziplin.contains("DAMENEINZEL")){
            kurzform = "DE";
        }
        else if(disziplin.contains("HERRENEINZEL")) {
            kurzform = "HE";
        }
        else if(disziplin.contains("HERRENDOPPEL")) {
            kurzform = "HD";
        }
        else if(disziplin.contains("DAMENDOPPEL")) {
            kurzform = "DD";
        }
        else if(disziplin.contains("MIXED")) {
            kurzform = "MX";
        }
        return kurzform;
    }
}
