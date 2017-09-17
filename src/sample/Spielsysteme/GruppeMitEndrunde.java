package sample.Spielsysteme;
import sample.*;

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

	public GruppeMitEndrunde(Spielklasse spielklasse, int anzahlGruppen, int anzahlWeiterkommender) {
		this.setSpielSystemArt(2);
		this.setzliste = spielklasse.getSetzliste();
		this.anzahlGruppen = anzahlGruppen;
		this.anzahlWeiterkommender = anzahlWeiterkommender;
		setSpielklasse(spielklasse);
		setzListeAufteilen();
		gruppenErstellen();
		endRundeErstellen();
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
			this.anzahlWeiterkommender = endrundenSpiele.size()+1;
			alleGruppen.add(new Gruppe(alleSetzListen.get(i),this,this.getSpielklasse(),i+1, gruppenSpiele, gruppenErgebnisse));

		}
		this.anzahlWeiterkommender = endrundenSpiele.size()+1;
		endrunde= new KO(anzahlWeiterkommender,this, this.getSpielklasse(),false, endrundenSpiele, endrundenErgebnisse);
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
	private void endRundeErstellen(){
		endrunde= new KO(anzahlWeiterkommender,this, this.getSpielklasse(),false);
	}


	public void addPlatzierungsliste(ArrayList<Team> platzierungsliste, int extraRundenID){
		for(int i=0;i<endrunde.getRunden().get(0).size();i++){
			Spiel spiel = endrunde.getRunden().get(0).get(i);
			int setzplatzheim = spiel.getSetzPlatzHeim();
			int setzplatzgast = spiel.getSetzPlatzGast();
			if(setzPlatze.get(setzplatzheim) != null && setzPlatze.get(setzplatzheim)[0]==extraRundenID){
				spiel.setHeim(platzierungsliste.get(setzPlatze.get(setzplatzheim)[1]-1));
				spiel.getSpielDAO().update(spiel);
			}
			if(setzPlatze.get(setzplatzgast) != null && setzPlatze.get(setzplatzgast)[0]==extraRundenID){
				spiel.setGast(platzierungsliste.get(setzPlatze.get(setzplatzgast)[1]-1));
				spiel.getSpielDAO().update(spiel);
			}
		}
		allePlatzierungslisten.add(platzierungsliste);
		if (allePlatzierungslisten.size()==alleGruppen.size()){
			int wildcards = anzahlWeiterkommender%anzahlGruppen;
			int platzierungFuerWildcard = (int) Math.ceil(anzahlWeiterkommender/anzahlGruppen);
			ArrayList<Team> platzierteTeams = new ArrayList<>();
			Dictionary<Integer,Team> wildCardTeams = new Hashtable<>(); //Dictionary mit Setzplatz;
			for (int i=0;i<allePlatzierungslisten.size();i++){
				platzierteTeams.add(allePlatzierungslisten.get(i).get(platzierungFuerWildcard-1)); //füge alle Teams mit der gesuchten Platzierung hinzu
			}
			sortList(platzierteTeams);
			int setzplatz = (platzierungFuerWildcard-1)*anzahlGruppen +1;
			for (int i=0;i<wildcards;i++){
				wildCardTeams.put(setzplatz,platzierteTeams.get(i));
				setzplatz++;
			}
			for (int i=0;i<endrunde.getRunden().get(0).size();i++){
				Spiel spiel = endrunde.getRunden().get(0).get(i);
				int setzplatzheim = spiel.getSetzPlatzHeim();
				int setzplatzgast = spiel.getSetzPlatzGast();
				if (wildCardTeams.get(setzplatzheim)!=null){
					spiel.setHeim(wildCardTeams.get(setzplatzheim));
				}
				if (wildCardTeams.get(setzplatzgast)!=null){
					spiel.setGast(wildCardTeams.get(setzplatzgast));
				}
			}
		}
	}

	private void rundenArrayErstellen() {
		for (int i=0;i< alleGruppen.size();i++){
			ArrayList<ArrayList<Spiel>> gruppenArrayList = alleGruppen.get(i).getRundenArray();
			arrayListIntegrieren(gruppenArrayList);
		}
		ArrayList<ArrayList<Spiel>> endrundenArrayList = endrunde.getRunden();
		for (int i=0;i<endrundenArrayList.size();i++){
			this.getRundenArray().add(new ArrayList<>());
			for (int j=0;j<endrundenArrayList.get(i).size();j++){
				this.getRundenArray().get(getRundenArray().size()-1).add(endrundenArrayList.get(i).get(j));
			}
		}
	}

	private void arrayListIntegrieren(ArrayList<ArrayList<Spiel>> gruppenArrayList) {
		ArrayList<ArrayList<Spiel>> gesamtArrayList = this.getRundenArray();
		while(gruppenArrayList.size()>gesamtArrayList.size()){
			gesamtArrayList.add(new ArrayList<>());
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
		for (int i=0;i<alleGruppen.size();i++){
			if (alleGruppen.get(i).getExtraRunde()==extraRundenNummer){
				alleGruppen.get(i).beendeMatch(spiel);
			}
		}
		return false;
	}

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		int systemSpielID = spiel.getSystemSpielID();
		int extraRundenNummer = systemSpielID-spiel.getSystemSpielID()/10000000 * 10000000;
		extraRundenNummer = extraRundenNummer / 100000;
		for (int i=0;i<alleGruppen.size();i++){
			if (alleGruppen.get(i).getExtraRunde()==extraRundenNummer){
				alleGruppen.get(i).beendeMatch(spiel,"einlesen");
			}
		}
		return false;
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
