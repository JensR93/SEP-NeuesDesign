package sample.Spielsysteme;
import sample.*;

import java.lang.reflect.Array;
import java.util.*;

public class GruppeMitEndrunde extends Spielsystem{
	private int anzahlGruppen;
	private int anzahlWeiterkommender;
	private ArrayList<Team> endrundenSetzliste = new ArrayList<>();
	private ArrayList<Team> setzliste;
	private ArrayList<Team> templist = new ArrayList<>();
	private ArrayList<ArrayList<Team>> alleSetzListen = new ArrayList<>();
	private ArrayList<Gruppe> alleGruppen = new ArrayList<>();
	private ArrayList<ArrayList<Team>> allePlatzierungslisten = new ArrayList<>();
	private Spielsystem endrunde;
	private Dictionary<Integer,Integer[]> setzPlatze = new Hashtable<>();

	public GruppeMitEndrunde(Spielklasse spielklasse, int anzahlGruppen, int anzahlWeiterkommender,boolean ko) {
		this.setSpielSystemArt(2);
		this.setzliste = spielklasse.getSetzliste();
		this.anzahlGruppen = anzahlGruppen;
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		gruppenErstellen();
		endRundeErstellen(ko);
		rundenArrayErstellen();
	}


	public GruppeMitEndrunde(ArrayList<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setSpielSystemArt(2);
		this.setzliste = setzliste;		//Constructor nur für Einlesen aus der Datenbank
		this.anzahlGruppen = ermittleAnzahlGruppen(spiele);
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		spieleEinlesen(spiele, ergebnisse);
		rundenArrayErstellen();
	}

	private int ermittleAnzahlGruppen(ArrayList<Spiel> spiele) {
		int anzahlGruppen = 0;
		for (int i=0; i<spiele.size();i++){
			Spiel spiel = spiele.get(i);
			int systemSpielID = spiel.getSystemSpielID();
			int gruppenNummer = systemSpielID/100000 - (spiel.getSystemSpielID()/10000000)*100;
			if (gruppenNummer>anzahlGruppen){
				anzahlGruppen = gruppenNummer;
			}
		}
		return anzahlGruppen;
	}

	private void spieleEinlesen(ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		ArrayList<Spiel> endrundenSpiele = new ArrayList<>();
		Dictionary<Integer,Ergebnis> endrundenErgebnisse = new Hashtable<>();
		for(int i=0; i<alleSetzListen.size();i++){
			ArrayList<Spiel> gruppenSpiele = new ArrayList<>();
			Dictionary<Integer,Ergebnis> gruppenErgebnisse = new Hashtable<>();

			for (int j=0;j<spiele.size();j++){
				Spiel spiel = spiele.get(j);
				int gruppenNummer = (spiel.getSystemSpielID()-spiel.getSystemSpielID()/10000000 * 10000000)/100000;
				if (i+1 == gruppenNummer){
					gruppenSpiele.add(spiel);
					if (ergebnisse.get(spiel.getSystemSpielID())!=null){
						gruppenErgebnisse.put(spiel.getSystemSpielID(),ergebnisse.get(spiel.getSystemSpielID()));
					}
				}
				else if(gruppenNummer==0 && !endrundenSpiele.contains(spiel)){
					endrundenSpiele.add(spiel);
					if (ergebnisse.get(spiel.getSystemSpielID())!=null){
						endrundenErgebnisse.put(spiel.getSystemSpielID(),ergebnisse.get(spiel.getSystemSpielID()));
					}
				}
			}
			if(i==0){
				if(endrundeAufKOpruefen(endrundenSpiele)) {
					endrunde = new KO(anzahlWeiterkommender, this, this.getSpielklasse(), false, endrundenSpiele, endrundenErgebnisse);
				}
				else {
					Gruppe endrundenGruppe = new Gruppe(anzahlWeiterkommender,this,getSpielklasse(),endrundenSpiele,endrundenErgebnisse);
					endrunde = endrundenGruppe;
					endrundenGruppe.allesEinlesen(endrundenSpiele,endrundenErgebnisse);
				}
			}
			//this.anzahlWeiterkommender = endrundenSpiele.size()+1;
			Gruppe neueGruppe = new Gruppe(alleSetzListen.get(i),this,this.getSpielklasse(),i+1, gruppenSpiele, gruppenErgebnisse);
			alleGruppen.add(neueGruppe);
			neueGruppe.allesEinlesen(gruppenSpiele, gruppenErgebnisse);
			platzierungslisteChecken(gruppenSpiele, gruppenErgebnisse);

		}
	}

	private void platzierungslisteChecken(ArrayList<Spiel> gruppenSpiele, Dictionary<Integer, Ergebnis> gruppenErgebnisse) {
		for(int i=0;i<gruppenSpiele.size();i++){
			Spiel spiel = gruppenSpiele.get(i);
			if (spiel.getStatus()!=3){
				return;
			}
		}
		ArrayList<Team> platzierungsliste = new ArrayList<>();
		for(int i=0;i<gruppenSpiele.size();i++){
			Spiel spiel = gruppenSpiele.get(i);
			if (spiel.getHeim()!=null && !platzierungsliste.contains(spiel.getHeim())){
				platzierungsliste.add(spiel.getHeim());
			}
			if (spiel.getGast()!=null && !platzierungsliste.contains(spiel.getGast())){
				platzierungsliste.add(spiel.getGast());
			}
		}
		sortList(platzierungsliste);
		allePlatzierungslisten.add(platzierungsliste);
	}

	private boolean endrundeAufKOpruefen(ArrayList<Spiel> endrundenSpiele) {
		ArrayList<ZeitplanRunde> spieleProRunde = new ArrayList<>();
		for (int i=0;i<endrundenSpiele.size();i++){
			Spiel spiel = endrundenSpiele.get(i);
			int rundenNummer = spiel.getRundenNummer();
			while (spieleProRunde.size()-1<rundenNummer){
				spieleProRunde.add(new ZeitplanRunde());
			}
			spieleProRunde.get(rundenNummer).add(spiel);

		}
		for (int i=1;i<spieleProRunde.size();i++){
			if (spieleProRunde.get(i).size()!=spieleProRunde.get(i-1).size()){
				this.anzahlWeiterkommender=endrundenSpiele.size()-1-anzahlSpieleMitFreilosen(endrundenSpiele);
				return true;
			}
		}
		this.anzahlWeiterkommender=spieleProRunde.get(0).size()*2-freiloseInGruppeBerechnen(spieleProRunde.get(0));
		return false;
	}

	private int freiloseInGruppeBerechnen(ZeitplanRunde spiele) {
		int freilosInGruppe = 0;
		for (int i=0;i<spiele.size();i++){
			Spiel spiel = spiele.get(i);
			if((spiel.getHeim()!=null && spiel.getHeim().isFreilos())||(spiel.getGast()!=null && spiel.getGast().isFreilos())){
				freilosInGruppe++;
			}
		}
		return freilosInGruppe;
	}

	private int anzahlSpieleMitFreilosen(ArrayList<Spiel> endrundenSpiele) {
		int anzahlSpieleMitFreilosen=0;
		for (int i=0; i<endrundenSpiele.size();i++){
			Spiel spiel = endrundenSpiele.get(i);
			if (spiel.getHeim()!= null && spiel.getHeim().isFreilos()){
				anzahlSpieleMitFreilosen++;
			}
			else if(spiel.getGast()!= null && spiel.getGast().isFreilos()){
				anzahlSpieleMitFreilosen++;
			}
		}
		return anzahlSpieleMitFreilosen;
	}

	private void setzListeAufteilen(){
		for (int k=0; k<setzliste.size();k++){
			templist.add(setzliste.get(k));
		}
		for (int j=0; j<anzahlGruppen;j++){
			alleSetzListen.add(new ArrayList<>());
		}
		freiloseHinzufuegen();
		boolean hochzaehlen = false;
		boolean wurdeschongetauscht=false;
		int zaehler = 0;
		while(templist.size()>0)
		{
			if ((zaehler==anzahlGruppen-1||zaehler==0)&&!wurdeschongetauscht){
				hochzaehlen = !hochzaehlen;
				wurdeschongetauscht=true;
			}
			else if(hochzaehlen){
				zaehler++;
				wurdeschongetauscht=false;
				System.out.println(zaehler);
			}
			else{
				wurdeschongetauscht=false;
				zaehler--;
			}
			Team tempTeam = templist.get(0);
			templist.remove(tempTeam);
			alleSetzListen.get(zaehler).add(tempTeam);
		}
	}

	private void gruppenErstellen(){
		for(int i=0; i<alleSetzListen.size();i++){
			alleGruppen.add(new Gruppe(alleSetzListen.get(i),this,this.getSpielklasse(),i+1));
		}
	}


	private void freiloseHinzufuegen (){
		while ((double)templist.size()%(anzahlGruppen*2)>0){
			templist.add(new Team("Freilos",this));
			super.setzlisteDAO.create(templist.size(),templist.get(templist.size()-1),this.getSpielklasse());
		}
	}


	private void endRundeErstellen(boolean ko){
		if(ko) {
			endrunde = new KO(anzahlWeiterkommender, this, this.getSpielklasse(), false);
		}
		else{
			endrunde = new Gruppe(anzahlWeiterkommender,this,this.getSpielklasse());
		}

	}


	public void addPlatzierungsliste(ArrayList<Team> platzierungsliste, int extraRundenID){
		allePlatzierungslisten.add(platzierungsliste);
		if(!(endrunde instanceof Gruppe)) {
			for (int i = 0; i < endrunde.getRunden().get(0).size(); i++) {
				Spiel spiel = endrunde.getRunden().get(0).get(i);
				int setzplatzheim = spiel.getSetzPlatzHeim();
				int setzplatzgast = spiel.getSetzPlatzGast();
				if (setzPlatze.get(setzplatzheim) != null && setzPlatze.get(setzplatzheim)[0] == extraRundenID) {
					spiel.setHeim(platzierungsliste.get(setzPlatze.get(setzplatzheim)[1] - 1));
					spiel.setFreilosErgebnis();
					spiel.getSpielDAO().update(spiel);
				}
				if (setzPlatze.get(setzplatzgast) != null && setzPlatze.get(setzplatzgast)[0] == extraRundenID) {
					spiel.setGast(platzierungsliste.get(setzPlatze.get(setzplatzgast)[1] - 1));
					spiel.setFreilosErgebnis();
					spiel.getSpielDAO().update(spiel);
				}
			}
		}
		if (allePlatzierungslisten.size()==alleGruppen.size()){
			if(!(endrunde instanceof Gruppe)) {
				int wildcards = anzahlWeiterkommender % anzahlGruppen;
				int platzierungFuerWildcard = (int) Math.ceil((double) anzahlWeiterkommender / anzahlGruppen);
				ArrayList<Team> platzierteTeams = new ArrayList<>();
				Dictionary<Integer, Team> wildCardTeams = new Hashtable<>(); //Dictionary mit Setzplatz;
				for (int i = 0; i < allePlatzierungslisten.size(); i++) {
					platzierteTeams.add(allePlatzierungslisten.get(i).get(platzierungFuerWildcard - 1)); //füge alle Teams mit der gesuchten Platzierung hinzu
				}
				sortList(platzierteTeams);
				int setzplatz = (platzierungFuerWildcard - 1) * anzahlGruppen + 1;
				for (int i = 0; i < wildcards; i++) {
					wildCardTeams.put(setzplatz, platzierteTeams.get(i));
					setzplatz++;
				}
				for (int i = 0; i < endrunde.getRunden().get(0).size(); i++) {
					Spiel spiel = endrunde.getRunden().get(0).get(i);
					int setzplatzheim = spiel.getSetzPlatzHeim();
					int setzplatzgast = spiel.getSetzPlatzGast();
					if (wildCardTeams.get(setzplatzheim) != null) {
						spiel.setHeim(wildCardTeams.get(setzplatzheim));
						spiel.setFreilosErgebnis();
						spiel.getSpielDAO().update(spiel);
					}
					if (wildCardTeams.get(setzplatzgast) != null) {
						spiel.setGast(wildCardTeams.get(setzplatzgast));
						spiel.setFreilosErgebnis();
						spiel.getSpielDAO().update(spiel);
					}
				}
			}
			else if(endrunde instanceof Gruppe){
				Gruppe endrunde = (Gruppe)this.endrunde;
				for (int i=0;i<allePlatzierungslisten.size();i++){
					sortList(allePlatzierungslisten.get(i));
				}
				ArrayList<Team> setzliste = new ArrayList<>();
				int anzahlweiterkommenderJeGruppe = anzahlWeiterkommender/anzahlGruppen;
				for(int i=0;i<allePlatzierungslisten.size();i++){
					for (int j=0;j<anzahlweiterkommenderJeGruppe;j++){
						Team team = allePlatzierungslisten.get(i).get(j);
						setzliste.add(team);
					}
				}
				int wildcards = anzahlWeiterkommender % anzahlGruppen;
				int platzierungFuerWildcard = (int) Math.ceil((double) anzahlWeiterkommender / anzahlGruppen);
				ArrayList<Team> platzierteTeams = new ArrayList<>();
				for (int i = 0; i < allePlatzierungslisten.size(); i++) {
					platzierteTeams.add(allePlatzierungslisten.get(i).get(platzierungFuerWildcard - 1)); //füge alle Teams mit der gesuchten Platzierung hinzu
				}
				sortList(platzierteTeams);
				for(int i=0;i<wildcards;i++){
					Team team = platzierteTeams.get(i);
					setzliste.add(team);
				}
				endrunde.endrundeStarten(setzliste);
			}
		}
	}

	private void rundenArrayErstellen() {
		for (int i=0;i< alleGruppen.size();i++){
			ArrayList<ZeitplanRunde> gruppenArrayList = alleGruppen.get(i).getRundenArray();
			arrayListIntegrieren(gruppenArrayList);
		}
		ArrayList<ZeitplanRunde> endrundenArrayList = endrunde.getRunden();
		for (int i=0;i<endrundenArrayList.size();i++){
			this.getRundenArray().add(new ZeitplanRunde());
			for (int j=0;j<endrundenArrayList.get(i).size();j++){
				this.getRundenArray().get(getRundenArray().size()-1).add(endrundenArrayList.get(i).get(j));
			}
		}
	}

	private void arrayListIntegrieren(ArrayList<ZeitplanRunde> gruppenArrayList) {
		ArrayList<ZeitplanRunde> gesamtArrayList = this.getRundenArray();
		while(gruppenArrayList.size()>gesamtArrayList.size()){
			gesamtArrayList.add(new ZeitplanRunde());
		}
		for(int i=0;i<gruppenArrayList.size();i++){
			for (int j=0;j<gruppenArrayList.get(i).size();j++){
				Spiel spiel = gruppenArrayList.get(i).get(j);
				gesamtArrayList.get(i).add(spiel);
			}
		}
	}


	public ArrayList<Gruppe> getAlleGruppen() {
		return alleGruppen;
	}

	public Spielsystem getEndrunde() {
		return endrunde;
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		int systemSpielID = spiel.getSystemSpielID();
		int extraRundenNummer = systemSpielID-spiel.getSystemSpielID()/10000000 * 10000000;
		extraRundenNummer = extraRundenNummer / 100000;
		if (extraRundenNummer==0){
			endrunde.beendeMatch(spiel);
		}
		else {
			for (int i = 0; i < alleGruppen.size(); i++) {
				if (alleGruppen.get(i).getExtraRunde() == extraRundenNummer) {
					alleGruppen.get(i).beendeMatch(spiel);
				}
			}
		}
		return false;
	}

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		int systemSpielID = spiel.getSystemSpielID();
		int extraRundenNummer = systemSpielID-spiel.getSystemSpielID()/10000000 * 10000000;
		extraRundenNummer = extraRundenNummer / 100000;
		if (extraRundenNummer==0){
			endrunde.beendeMatch(spiel,"einlesen");
		}
		else {
			for (int i = 0; i < alleGruppen.size(); i++) {
				if (alleGruppen.get(i).getExtraRunde() == extraRundenNummer) {
					alleGruppen.get(i).beendeMatch(spiel, "einlesen");
				}
			}
		}
		return false;
	}
	@Override
	public void updateVisualisierung(){
		for (int i=0;i<alleGruppen.size();i++){
			alleGruppen.get(i).updateVisualisierung();
		}
		endrunde.updateVisualisierung();
	}

	public int getAnzahlWeiterkommender() {
		return anzahlWeiterkommender;
	}

	public Dictionary<Integer, Integer[]> getSetzPlatze() {
		return setzPlatze;
	}

	public int getAnzahlGruppen() {
		return anzahlGruppen;
	}

	public ArrayList<Team> getSetzliste(){
		return setzliste;
	}
}

/*

	private void endRundeErstellen(){
		int qualiPlaetzeJeGruppe = anzahlWeiterkommender/anzahlGruppen;
		int wildCards = anzahlWeiterkommender-qualiPlaetzeJeGruppe*anzahlGruppen;
		Team tempTeam;
		for(int i=0; i<qualiPlaetzeJeGruppe;i++){
			if (i%2==0){
				for (int j=0;j<allePlatzierungslisten.size();j++){
					tempTeam=allePlatzierungslisten.get(j).get(i);
					endrundenSetzliste.add(tempTeam);
				}
			}
			else{
				for (int k=allePlatzierungslisten.size()-1;k>=0;k--){
					tempTeam=allePlatzierungslisten.get(k).get(i);
					endrundenSetzliste.add(tempTeam);
				}
				for (int l=i*anzahlGruppen;l<(i+1)*anzahlGruppen;l++){
					tempTeam=endrundenSetzliste.get(l);
					endrundenSetzliste.remove(tempTeam);
					l++;
					endrundenSetzliste.add(l,tempTeam);
				}
			}
		}
		endrunde= new KO(endrundenSetzliste,this, this.getSpielklasse(),true);
	}*/
