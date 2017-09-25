package sample.Spielsysteme;

import java.util.*;
import java.util.concurrent.TimeUnit;

import sample.*;
import sample.DAO.SpielDAO;
import sample.DAO.SpielDAOimpl;

public class SchweizerSystem extends Spielsystem {
	private int anzahlTeams;
	private boolean beendet;
	private int[][] schema;
	private ArrayList<Team> teamList;
	private ArrayList<Team> nextTeamList = new ArrayList<Team>();
	private ArrayList<ArrayList<Team>> teamListArray =new ArrayList<ArrayList<Team>>();

	public SchweizerSystem(int anzahlRunden, ArrayList<Team> teamList, Spielklasse spielklasse) {
		setSpielklasse(spielklasse);
		this.teamList = teamList;
		freiloseHinzufuegen(this.teamList);
		this.anzahlTeams = teamList.size();
		if(anzahlRunden<anzahlTeams) {
			setAnzahlRunden(anzahlRunden);
		}
		else{
			setAnzahlRunden(anzahlTeams-1);
			System.out.println("Es können bei "+anzahlTeams+" Teilnehmern maximal "+(anzahlTeams-1)+" Runden gespielt werden!");
		}
		setSpielSystemArt(5);
		ersteRundeErstellen();
		alleSpieleErstellen();
		alleSpieleSchreiben();
		freilosErgebnisSetzen();
		teamListReset();
	}

	private void alleSpieleSchreiben() {
		for (int i=0; i<this.getRundenArray().size(); i++){
			for(int j=0;j<this.getRundenArray().get(i).size();j++){
				Spiel spiel = this.getRundenArray().get(i).get(j);
				spiel.getSpielDAO().create(spiel);
			}
		}
	}

	public SchweizerSystem(ArrayList<Team> teamList, Spielklasse spielklasse, ArrayList<Spiel> spielListe, Dictionary<Integer,Ergebnis> ergebnisse) {
		setSpielklasse(spielklasse);				//Constructor nur für Einlesen aus der Datenbank
		this.anzahlTeams = teamList.size();
		setAnzahlRunden(spielListe.size()/(teamList.size()/2));
		this.teamList = teamList;
		setSpielSystemArt(5);
		alleSpieleEinlesen(spielListe);
		setOffeneRundenSpiele(anzahlTeams/2);
		alleErgebnisseEinlesen(ergebnisse);
	}

	private void freilosErgebnisSetzen() {
		if(this.getRundenArray().size()>0){
			for(int i=0;i<getRundenArray().get(0).size();i++){
				Spiel spiel = getRundenArray().get(0).get(i);
				if (spiel.getHeim()!=null&&spiel.getHeim().isFreilos()){
					spiel.setErgebnis(new Ergebnis(0, 21, 0, 21));
				}
				else if(spiel.getGast()!=null&&spiel.getGast().isFreilos()) {
					spiel.setErgebnis(new Ergebnis(21, 0, 21, 0));
				}
			}
		}
	}

	private void alleSpieleEinlesen(ArrayList<Spiel> spiele){
		for (int i=0;i<getAnzahlRunden();i++){
			getRundenArray().add(new ZeitplanRunde());
		}
		for (int i=0;i<spiele.size();i++){
			Spiel spiel =spiele.get(i);
			spiel.setSpielsystem(this);
			int rundennummer= spiel.getRundenNummer();
			//if (getRundenArray().get(rundennummer)!=null){
				getRundenArray().get(rundennummer).add(spiel);
			//}
		}
		setOffeneRundenSpiele(anzahlTeams/2);
	}

	private void alleErgebnisseEinlesen(Dictionary<Integer, Ergebnis> ergebnisse){
		Enumeration e = ergebnisse.keys();
		int key;
		while(e.hasMoreElements()){
			key = (int) e.nextElement();
			getSpielklasse().getSpiele().get(key).setErgebnis(ergebnisse.get(key),"einlesen");
		}
	}

	private void sortList(){
		Collections.sort(nextTeamList, new Comparator<Team>() {
			@Override
			public int compare(Team team1, Team team2) {
				return team1.compareTo(team2);
			}
		});
		teamListArray.add(new ArrayList<>());
		for (int i=0; i<nextTeamList.size();i++){
			teamListArray.get(teamListArray.size()-1).add(nextTeamList.get(i));
			//System.out.println("Size: "+teamListArray.get(aktuelleRunde-1).size());
		}
	}

	public void ersteRundeErstellen() {
		getRundenArray().add(new ZeitplanRunde());
		while (teamList.size()>1){
			Team teamEins = getRandomTeam();
			this.teamList.remove(teamEins);
			this.nextTeamList.add(teamEins);
			Team teamZwei = getRandomTeam();
			this.teamList.remove(teamZwei);
			this.nextTeamList.add(teamZwei);
			Spiel spiel = new Spiel(teamEins,teamZwei,spielSystemIDberechnen(),this);
			this.getRundenArray().get(0).add(spiel);
			//spiele.add(spiel);
			erhoeheOffeneRundenSpiele();
		}
		erhoeheAktuelleRunde();
	}
	private void alleSpieleErstellen(){
		for (int i=2;i<=getAnzahlRunden();i++){
			resetOffeneRundenSpiele();
			ZeitplanRunde zeitplanRunde= new ZeitplanRunde();

			for (int j=0; j<anzahlTeams/2;j++){
				Spiel spiel = new Spiel(spielSystemIDberechnen(), this);
				erhoeheOffeneRundenSpiele();
				zeitplanRunde.add(spiel);
			}
			erhoeheAktuelleRunde();
			getRundenArray().add(zeitplanRunde);
		}
		resetAktuelleRunde();
		erhoeheAktuelleRunde();
	}
	private boolean rundeErstellen(){
		sortList();
		//boolean beendet = (rundenListeErstellen(0, getAktuelleRunde()-1));
		rundenListeErstellenNeu();
		if(getAktuelleRunde()<getAnzahlRunden()) {
			rundeFuellen();
		}
		else{
			sortList();
			setPlatzierungsListe(nextTeamList);
		}
		return beendet;
	}

	private void rundenListeErstellenNeu(){
		teamListReset();
		nextTeamList.clear();
		erstelleSchema();
		for (int i=0; i<anzahlTeams;i++){
			fuelleZeile(i);
		}
		fuelleSummenSpalte();
		//druckeSchema();
		while(teamList.size()>1) {
			int indexTeamEins = sucheHoechsteSumme();
			int indexTeamZwei = sucheGeringstenZeilenWert(indexTeamEins);
			loescheTeamAusSchema(indexTeamEins);
			loescheTeamAusSchema(indexTeamZwei);
			Team teamEins = teamListArray.get(teamListArray.size()-1).get(indexTeamEins); //hole Teams aus der alten Liste, damit Index passt
			this.teamList.remove(teamEins);
			this.nextTeamList.add(teamEins);
			Team teamZwei = teamListArray.get(teamListArray.size()-1).get(indexTeamZwei);
			this.teamList.remove(teamZwei);
			this.nextTeamList.add(teamZwei);
		}
	}

	private void erstelleSchema(){
		schema = new int[anzahlTeams+1][anzahlTeams];
		for(int i=0; i<anzahlTeams;i++){
			fuelleZeile(i);
		}
	}

	private void fuelleZeile(int zeile){
		Team teamEins=teamList.get(zeile);
		for (int i=0;i<anzahlTeams;i++){
			Team teamZwei=teamList.get(i);
			if(teamEins==teamZwei){
				schema[i][zeile] = 0;
			}
			else if(!teamEins.warNochKeinGegner(teamZwei)){
				schema[i][zeile] = 10000;
			}
			else{
				schema[i][zeile] = Math.abs(teamList.indexOf(teamEins)-teamList.indexOf(teamZwei));
			}
		}
	}

	private void fuelleSummenSpalte(){
		for (int zeile=0;zeile<anzahlTeams;zeile++){
			int summe=0;
			for (int spalte=0;spalte<anzahlTeams;spalte++){
				summe+=schema[spalte][zeile];
			}
			schema[anzahlTeams][zeile]=summe;
		}
	}

	private int sucheHoechsteSumme(){
		int hoechsteSummenZeile=0;
		int hoechsteSumme=0;
		for (int zeile = 0; zeile<anzahlTeams;zeile++){
			if (schema[anzahlTeams][zeile]>hoechsteSumme){
				hoechsteSummenZeile = zeile;
				hoechsteSumme = schema[anzahlTeams][zeile];
			}
		}
		return hoechsteSummenZeile;
	}

	private int sucheGeringstenZeilenWert(int zeile){
		int geringsterZeilenWert=10000;
		int spalte=0;

		for(int x=0;x<anzahlTeams;x++){
			if (schema[zeile][x]<geringsterZeilenWert&&schema[zeile][x]!=0){
				geringsterZeilenWert=schema[zeile][x];
				spalte=x;
			}
			else if (schema[zeile][x]==geringsterZeilenWert){ //wenn Abstand in Tabelle gleich, wähle Team mit höherer Summe
				if(schema[anzahlTeams][x]>schema[anzahlTeams][spalte]){
					geringsterZeilenWert=schema[zeile][x];
					spalte=x;
				}
			}
		}

		return spalte;
	}

	private void loescheTeamAusSchema(int team){
		for(int i=0;i<anzahlTeams;i++){
			schema[team][i]=0; //setze Werte auf 100, damit Team nicht mehr ausgewählt wird
			schema[i][team]=0;
		}
		fuelleSummenSpalte(); //setze Summe auf 0, damit Team nicht mehr ausgewählt wird
	}

	private void teamListReset(){
		teamList.clear();
		for (int i=0;i<teamListArray.get(teamListArray.size()-1).size();i++)
		{
			this.teamList.add(teamListArray.get(teamListArray.size()-1).get(i));
		}
	}

	private void rundeFuellen(){
		teamList.clear();
		ArrayList<Team> tempTeamList = (ArrayList<Team>) nextTeamList.clone();
		/*for (int i=0;i<nextTeamList.size();i++)
		{
			this.teamList.add(nextTeamList.get(i));
		}*/
		nextTeamList.clear();
		while (tempTeamList.size()>1){
			Team teamEins = tempTeamList.get(0);
			tempTeamList.remove(teamEins);
			this.nextTeamList.add(teamEins);
			Team teamZwei = tempTeamList.get(0);
			tempTeamList.remove(teamZwei);
			this.nextTeamList.add(teamZwei);
			Spiel spiel = getSpielklasse().getSpiele().get(spielSystemIDberechnen());
			spiel.setHeim(teamEins);
			spiel.setGast(teamZwei);
			if (teamEins.isFreilos()){
				spiel.setErgebnis(new Ergebnis(0,21,0,21));
			}
			else if (teamZwei.isFreilos()){
				spiel.setErgebnis(new Ergebnis(21,0,21,0));
			}
			spiel.getSpielDAO().update(spiel);

			erhoeheOffeneRundenSpiele();
		}
	}

	public Team getRandomTeam(){
		int random = (int) Math.round(Math.random()*(teamList.size()-1));
		Team randomTeam = this.teamList.get(random);
		return randomTeam;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {
		senkeOffeneRundenSpiele();
		if(keineOffenenRundenSpiele()){
			if(getAktuelleRunde()<getAnzahlRunden()){
				rundeErstellen();
				erhoeheAktuelleRunde();
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		senkeOffeneRundenSpiele();
		if(keineOffenenRundenSpiele()){
			erhoeheAktuelleRunde();
			setOffeneRundenSpiele(anzahlTeams/2);
			if(getAktuelleRunde()==getAnzahlRunden()){
				return true;
			}
		}
		return false;
	}
	private void freiloseHinzufuegen(List<Team> teamList){
		if ((teamList.size()/2) * 2 != teamList.size()){ // /2 *2 überprüft, ob Spieleranzahl gerade oder ungerade (int)
			this.teamList.add(new Team("Freilos",this.getSpielklasse()));
			System.out.println("Freilos zu schweizer hinzugefügt");
			super.setzlisteDAO.create(teamList.size(),teamList.get(teamList.size()-1),this.getSpielklasse());
		}
	}
	public ArrayList<Team> getSetzliste(){
		return teamList;
	}
}