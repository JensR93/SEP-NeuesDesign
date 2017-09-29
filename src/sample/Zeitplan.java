package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DAO.auswahlklasse;
import sample.Enums.Disziplin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

/**
 * Created by Florian-PC on 11.08.2017.
 */
public class Zeitplan {
    private static ObservableList<Spiel> zeitplanGesamt = FXCollections.observableArrayList();
    private static ObservableList<Spiel> zeitplanEinzel = FXCollections.observableArrayList();
    private static ObservableList<Spiel> zeitplanDoppel = FXCollections.observableArrayList();
    private static ObservableList<Spiel> zeitplanMixed = FXCollections.observableArrayList();
    private static ArrayList<ArrayList<ZeitplanRunde>> spielsystemRundenEinzel = new ArrayList<>();
    private static ArrayList<ArrayList<ZeitplanRunde>> spielsystemRundenDoppel = new ArrayList<>();
    private static ArrayList<ArrayList<ZeitplanRunde>> spielsystemRundenMixed = new ArrayList<>();
    private static ArrayList<ZeitplanRunde> alleRundenSortiertEinzel = new ArrayList<>();
    private static ArrayList<ZeitplanRunde> alleRundenSortiertDoppel = new ArrayList<>();
    private static ArrayList<ZeitplanRunde> alleRundenSortiertMixed = new ArrayList<>();


    public static void optimalenZeitplanErstellen(Turnier turnier, String disziplin1, String disziplin2, String disziplin3){
        alleSpielsystemeEinlesen(turnier,disziplin1);
        alleSpielsystemeEinlesen(turnier,disziplin2);
        alleSpielsystemeEinlesen(turnier,disziplin3);
        if (spielsystemRundenEinzel.size()>0){
            spielSystemeSortieren(spielsystemRundenEinzel);
            alleRundenSortiertEinzel = alleRundenSortieren(spielsystemRundenEinzel);
            zeitplanErstellen(alleRundenSortiertEinzel,true);
        }
        if (spielsystemRundenDoppel.size()>0){
            spielSystemeSortieren(spielsystemRundenDoppel);
            alleRundenSortiertDoppel = alleRundenSortieren(spielsystemRundenDoppel);
            zeitplanErstellen(alleRundenSortiertDoppel,true);
        }
        if (spielsystemRundenDoppel.size()>0){
            spielSystemeSortieren(spielsystemRundenDoppel);
            alleRundenSortiertMixed = alleRundenSortieren(spielsystemRundenDoppel);
            zeitplanErstellen(alleRundenSortiertMixed,true);
        }
    }

    public static ArrayList<ZeitplanRunde> getAlleRunden(Turnier turnier,String disziplin){
        if(disziplin.toUpperCase().contains("EINZEL")){
            return alleRundenSortiertEinzel;
        }
        else if(disziplin.toUpperCase().contains("DOPPEL")){
            return alleRundenSortiertDoppel;
        }
        else if(disziplin.toUpperCase().contains("MIXED")){
            return alleRundenSortiertMixed;
        }
        return null;
    }

    public static ArrayList<ZeitplanRunde> getAlleRundenSortiert(String disziplin){
        ArrayList<ArrayList<ZeitplanRunde>> spielSystemRunden = alleSpielsystemeEinlesen(auswahlklasse.getAktuelleTurnierAuswahl(),disziplin);
        ArrayList<ZeitplanRunde> alleRundenSortiert = null;
        if(spielSystemRunden!=null) {
            spielSystemeSortieren(spielSystemRunden);
            alleRundenSortiert = alleRundenSortieren(spielSystemRunden);
        }
        return alleRundenSortiert;
    }
    public static ArrayList<ZeitplanRunde> getAlleRundenEinlesen(){
        ArrayList<ZeitplanRunde> alleRunden = new ArrayList<>();
        ArrayList<ZeitplanRunde> einzelRunden = getAlleRundenSortiert("EINZEL");
        if(einzelRunden!=null) {
            alleRunden.addAll(einzelRunden);
        }
        ArrayList<ZeitplanRunde> doppelRunden = getAlleRundenSortiert("DOPPEL");
        if(doppelRunden!=null) {
            alleRunden.addAll(doppelRunden);
        }
        ArrayList<ZeitplanRunde> mixedRunden = getAlleRundenSortiert("MIXED");
        if(mixedRunden!=null) {
            alleRunden.addAll(mixedRunden);
        }
        alleRunden.sort(ZeitplanRunde.comparator);
        return alleRunden;
    }

    private static ArrayList<ArrayList<ZeitplanRunde>> alleSpielsystemeEinlesen(Turnier turnier, String disziplin){
        ArrayList<ArrayList<ZeitplanRunde>> returnListe = null;
        if (turnier.getSpielklassen()!=null){
            Enumeration e = turnier.getSpielklassen().keys();
            while (e.hasMoreElements()){
                int key =(int) e.nextElement();
                Spielklasse spielklasse = turnier.getSpielklassen().get(key);
                if (spielklasse.getSpielsystem()!=null && spielklasse.getDisziplin().toUpperCase().contains(disziplin)){
                    ArrayList<ZeitplanRunde> getRunden = new ArrayList<>();
                    for(int i=0;i<turnier.getSpielklassen().get(key).getSpielsystem().getRunden().size();i++){
                        getRunden.add((ZeitplanRunde)turnier.getSpielklassen().get(key).getSpielsystem().getRunden().get(i).clone());
                    }
                    if(disziplin.toUpperCase().contains("EINZEL")){
                        spielsystemRundenEinzel.add(getRunden);
                        returnListe = spielsystemRundenEinzel;
                    }
                    else if(disziplin.toUpperCase().contains("DOPPEL")){
                        spielsystemRundenDoppel.add(getRunden);
                        returnListe = spielsystemRundenDoppel;
                    }
                    else if(disziplin.toUpperCase().contains("MIXED")){
                        spielsystemRundenMixed.add(getRunden);
                        returnListe = spielsystemRundenMixed;
                    }
                }
            }
        }
        return returnListe;

    }


    private static void spielSystemeSortieren(ArrayList<ArrayList<ZeitplanRunde>> spielsystemRunden){
        if (spielsystemRunden.size()>1) {
            Collections.sort(spielsystemRunden, new Comparator<ArrayList<ZeitplanRunde>>() {
                @Override
                public int compare(ArrayList<ZeitplanRunde> list1, ArrayList<ZeitplanRunde> list2) {
                    return list2.size() - list1.size();
                }
            });
        }
    }


    private static ArrayList<ZeitplanRunde> alleRundenSortieren(ArrayList<ArrayList<ZeitplanRunde>> spielsystemRunden){
        ArrayList<ZeitplanRunde> alleRundenSortiert = new ArrayList<>();
        while (spielsystemRunden.size()>0){
            for (int i=0;i<spielsystemRunden.size();i++){
                int verbleibendeRunden = spielsystemRunden.get(i).size();
                if (verbleibendeRunden!=0){
                    ZeitplanRunde runde = spielsystemRunden.get(i).get(verbleibendeRunden-1);
                    alleRundenSortiert.add(0,runde);
                    spielsystemRunden.get(i).remove(runde);
                }
                else {
                    spielsystemRunden.remove(i);
                }
            }
        }
        return alleRundenSortiert;
    }

    public static ObservableList<Spiel> zeitplanErstellen(ArrayList<ZeitplanRunde> alleRunden,ObservableList<Spiel> alterZeitplan ,boolean nurLesen){
        int spielnummer = alterZeitplan.size()+1;
        int rundenZeitPlanNummer = 1;
        if (alterZeitplan.size()>0){
            rundenZeitPlanNummer = alterZeitplan.get(alterZeitplan.size()-1).getRundenZeitplanNummer()+1;
        }
        ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
        listenVereinen(alleRunden, rundenZeitPlanNummer, zeitplan, spielnummer,nurLesen);
        return zeitplan;
    }
    public static ObservableList<Spiel> zeitplanErstellen(ArrayList<ZeitplanRunde> alleRunden, boolean nurLesen){
        int spielnummer = 1;
        int rundenZeitPlanNummer = 1;
        ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
        listenVereinen(alleRunden, rundenZeitPlanNummer, zeitplan, spielnummer,nurLesen);
        return zeitplan;
    }

    private static void listenVereinen(ArrayList<ZeitplanRunde> alleRunden, int rundenZeitPlanNummer, ObservableList<Spiel> zeitplan, int spielnummer, boolean nurLesen) {
        for(int i=0;i<alleRunden.size();i++){
            for(int j=0;j<alleRunden.get(i).size();j++){
                Spiel spiel = alleRunden.get(i).get(j);
                if(spiel!=null) {
                    if (spiel.getHeim() == null || spiel.getGast() == null) {
                        zeitplan.add(spiel);
                        if(!nurLesen) {
                            spiel.setZeitplanNummer(spielnummer);
                            spiel.setRundenZeitplanNummer(rundenZeitPlanNummer);
                        }
                        spielnummer++;
                    } else if (!spiel.getHeim().isFreilos() && !spiel.getGast().isFreilos()) {
                        zeitplan.add(spiel);
                        if(!nurLesen) {
                            spiel.setZeitplanNummer(spielnummer);
                            spiel.setRundenZeitplanNummer(rundenZeitPlanNummer);
                        }
                        spielnummer++;
                    } else {
                        spiel.setRundenZeitplanNummer(rundenZeitPlanNummer);
                    }
                }
            }
            rundenZeitPlanNummer++;
        }
    }
/*

    public static ArrayList<Spiel> listenVereinen(ArrayList<ZeitplanRunde> alleRundenSortiert){
        int spielnummer = 1;
        ArrayList<Spiel> zeitplan = new ArrayList<>();
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
        return zeitplan;
    }
*/

    public static ObservableList<Spiel> getZeitplan(String disziplin) {
        if(disziplin.toUpperCase().contains("EINZEL")){
            return zeitplanEinzel;
        }
        else if(disziplin.toUpperCase().contains("DOPPEL")){
            return zeitplanDoppel;
        }
        else if(disziplin.toUpperCase().contains("MIXED")){
            return zeitplanMixed;
        }
        return null;
    }

    public static void zeitplanEinlesen(Turnier turnierEingabe) {
        for(int i=0;i<turnierEingabe.getObs_alleSpiele().size();i++){
            Spiel spiel = turnierEingabe.getObs_alleSpiele().get(i);
            if (spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("EINZEL")){
                zeitplanEinzel.add(spiel);
            }
            else if (spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("DOPPEL")){
                zeitplanDoppel.add(spiel);
            }
            else if (spiel.getSpielsystem().getSpielklasse().getDisziplin().toUpperCase().contains("MIXED")){
                zeitplanMixed.add(spiel);
            }
        }
        Comparator<Spiel> zeitplanComparator = new Comparator<Spiel>() {
            @Override
            public int compare(Spiel o1, Spiel o2) {
                return o1.getZeitplanNummer()-o2.getZeitplanNummer();
            }
        };
        zeitplanEinzel.sort(zeitplanComparator);
        zeitplanDoppel.sort(zeitplanComparator);
        zeitplanMixed.sort(zeitplanComparator);
    }



}
