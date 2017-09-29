package sample;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Florian-PC on 20.09.2017.
 */
public class ZeitplanRunde extends ArrayList<Spiel>{
    public static Comparator<ZeitplanRunde> comparator = new Comparator<ZeitplanRunde>() {
        @Override
        public int compare(ZeitplanRunde o1, ZeitplanRunde o2) {
            return o1.getRundenNummer()-o2.getRundenNummer();
        }
    };
    public Integer getAnzahlSpiele(){
        return this.size();
    }
    public String getRundenName(){
        String rundenName ="";
        if(this.get(0) != null){
            Spiel spiel = this.get(0);
            rundenName =spiel.getRundenNameKurz();
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
}
