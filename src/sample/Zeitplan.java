package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

/**
 * Created by Florian-PC on 11.08.2017.
 */
public class Zeitplan {
    private static ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
    private static ArrayList<ArrayList<ZeitplanRunde>> spielsystemRunden = new ArrayList<>();
    private static ArrayList<ZeitplanRunde> alleRundenSortiert = new ArrayList<>();


    public static void zeitplanErstellen(Turnier turnier){
        resetteAlles();
        alleSpielsystemeEinlesen(turnier);
        if (spielsystemRunden.size()>0){
            spielSystemeSortieren();
            alleRundenSortieren();
        }
        listenVereinen();
    }
    public static ArrayList<ZeitplanRunde> getAlleRunden(Turnier turnier){
        alleSpielsystemeEinlesen(turnier);
        if (spielsystemRunden.size()>0){
            spielSystemeSortieren();
            alleRundenSortieren();
            return alleRundenSortiert;
        }
        return null;
    }

    private static void resetteAlles(){
        zeitplan.clear();
        alleRundenSortiert.clear();
        spielsystemRunden.clear();
    }

    private static void alleSpielsystemeEinlesen(Turnier turnier){
        if (turnier.getSpielklassen()!=null){

            Enumeration e = turnier.getSpielklassen().keys();
            while (e.hasMoreElements()){
                int key =(int) e.nextElement();
                if (turnier.getSpielklassen().get(key).getSpielsystem()!=null){
                    ArrayList<ZeitplanRunde> getRunden = turnier.getSpielklassen().get(key).getSpielsystem().getRunden();
                    spielsystemRunden.add(getRunden);
                }
            }
        }
    }


    private static void spielSystemeSortieren(){
        Collections.sort(spielsystemRunden, new Comparator<ArrayList<ZeitplanRunde>>() {
            @Override
            public int compare(ArrayList<ZeitplanRunde> list1, ArrayList<ZeitplanRunde> list2) {
                return list2.size()-list1.size();
            }
        });
    }


    private static void alleRundenSortieren(){
        while (spielsystemRunden.size()>0){
            for (int i=0;i<spielsystemRunden.size();i++){
                int verbleibendeRunden = spielsystemRunden.get(i).size();
                if (verbleibendeRunden!=0){
                    ZeitplanRunde runde = spielsystemRunden.get(i).get(verbleibendeRunden-1);
                    alleRundenSortiert.add(runde);
                    spielsystemRunden.get(i).remove(runde);
                }
                else {
                    spielsystemRunden.remove(i);
                }
            }
        }
    }
    private static void listenVereinen(){
        int spielnummer = 1;
        for(int i=alleRundenSortiert.size()-1;i>=0;i--){
            for(int j=0;j<alleRundenSortiert.get(i).size();j++){
                Spiel spiel = alleRundenSortiert.get(i).get(j);
                if(spiel.getHeim()==null || spiel.getGast()==null){
                    zeitplan.add(spiel);
                    spiel.setZeitplanNummer(spielnummer);
                    spiel.setRundenZeitplanNummer(alleRundenSortiert.size()-i);
                    spielnummer++;
                }
                else if (!spiel.getHeim().isFreilos() && !spiel.getGast().isFreilos()){
                    zeitplan.add(spiel);
                    spiel.setZeitplanNummer(spielnummer);
                    spiel.setRundenZeitplanNummer(alleRundenSortiert.size()-i);
                    spielnummer++;
                }
                else{
                    spiel.setRundenZeitplanNummer(alleRundenSortiert.size()-i);
                }
            }
        }
    }
}
