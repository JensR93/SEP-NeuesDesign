package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

import java.util.*;

public class KO extends Spielsystem {
	private boolean platzDreiAusspielen;
	static int teilnehmerzahl;
	private SpielTree finale;
	private Spielsystem spielsystem;
	private ArrayList<Team> setzliste;
	private int anzahlFreilose = 0;
	private Spiel spielUm3;

	public KO(ArrayList<Team> setzliste, Spielklasse spielklasse,boolean platzDreiAusspielen){
		this.setSpielklasse(spielklasse);
		this.setzliste=setzliste;
		this.platzDreiAusspielen = platzDreiAusspielen;
		this.teilnehmerzahl=setzliste.size();
		this.spielsystem=this;
		setSpielSystemArt(3);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenAufbauen(teilnehmerzahl);
		if(platzDreiAusspielen) {
			spielUmDreiErstellen();
		}
		ersteRundeFuellen(setzliste);
		alleSpieleSchreiben();
	}

	/*public KO(ArrayList<Team> setzliste, Spielsystem spielsystem, Spielklasse spielklasse,boolean platzDreiAusspielen){
		this.spielsystem = spielsystem;//Constructor für Endrunde bei Gruppe mit Endrunde
		this.setzliste=setzliste;
		this.platzDreiAusspielen = platzDreiAusspielen;
		this.setSpielklasse(spielklasse);
		this.teilnehmerzahl=setzliste.size();
		setSpielSystemArt(2);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenAufbauen(teilnehmerzahl);
		if(platzDreiAusspielen) {
			spielUmDreiErstellen();
		}
		ersteRundeFuellen(setzliste);
		alleSpieleSchreiben();
	}*/

	public KO(int anzahlSpieler, Spielsystem gruppeMitEndrunde, Spielklasse spielklasse, boolean platzDreiAusspielen) {
		this.spielsystem = gruppeMitEndrunde;//Constructor für Endrunde bei Gruppe mit Endrunde
		this.platzDreiAusspielen = platzDreiAusspielen;
		this.setSpielklasse(spielklasse);
		this.teilnehmerzahl=anzahlSpieler;
		setSpielSystemArt(2);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(anzahlSpieler);
		knotenAufbauen(teilnehmerzahl);
		freiloseZuKnotenHinzufuegen();
		if(platzDreiAusspielen){
			spielUmDreiErstellen();
		}
		alleSpieleSchreiben();
	}


	public KO(int anzahlSpieler, Spielsystem gruppeMitEndrunde, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.spielsystem = gruppeMitEndrunde;//Constructor für Endrunde bei Gruppe mit Endrunde einlesen
		this.platzDreiAusspielen = platzDreiAusspielen;
		this.setSpielklasse(spielklasse);
		this.teilnehmerzahl=anzahlSpieler;
		setSpielSystemArt(2);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(anzahlSpieler);
		knotenEinlesen(spiele);
		alleErgebnisseEinlesen(ergebnisse);
	}


	public KO(ArrayList<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {
		this.setSpielklasse(spielklasse);		//Constructor nur für Einlesen aus der Datenbank
		this.teilnehmerzahl=setzliste.size();
		this.spielsystem = this;
		setSpielSystemArt(3);
		finale = new SpielTree(spielSystemIDberechnen(), 1, 2);
		freiloseHinzufuegen(setzliste);
		knotenEinlesen(spiele);
		alleErgebnisseEinlesen(ergebnisse);
	}


	private void alleSpieleSchreiben() {
		for (int i=getRunden().size()-1; i>=0;i--){
			for(int j=0;j<getRunden().get(i).size();j++){
				Spiel spiel = getRundenArray().get(i).get(j);
				if(spiel.getVorrundenNummer()==0){
					int justforbreakpoint;
				}
				spiel.getSpielDAO().create(spiel);
				spiel.setFreilosErgebnis();
			}
		}
	}


	private void alleErgebnisseEinlesen(Dictionary<Integer, Ergebnis> ergebnisse){
		Enumeration e = ergebnisse.keys();
		int key;
		while(e.hasMoreElements()){
			key = (int) e.nextElement();
			getSpielklasse().getSpiele().get(key).setErgebnis(ergebnisse.get(key),"einlesen");
		}
	}

	private void knotenEinlesen(ArrayList<Spiel> spiele) {
		Dictionary<Integer,Spiel> dicSpiele = new Hashtable<>();
		for (int i=0;i<spiele.size();i++){
			Spiel spiel = spiele.get(i);
			spiel.setSpielsystem(this.spielsystem);
			int key = spiel.getSystemSpielID();
			if (key ==getSpielSystemArt()*10000000 +1){
				spielUm3 = spiel;
				platzDreiAusspielen = true;
			}
			else{
				dicSpiele.put(key,spiel);
			}

		}
		teilnehmerzahl=dicSpiele.size()+1;
		int anzahlRunden = rundenBerechnen();
		int hoechsterSetzplatz;
		SpielTree aktuellerKnoten = finale;
		finale.setSpiel(dicSpiele.get(getSpielSystemArt()*10000000));
		this.getRundenArray().add(new ZeitplanRunde());
		this.getRundenArray().get(0).add(finale.getSpiel());
		if (spielUm3!=null){
			this.getRundenArray().get(0).add(spielUm3);
		}

		for (int i=0; i<anzahlRunden-1; i++){ //erstelle für jeder Runde Spiele
			aktuellerKnoten = finale.getSpielTree(spielSystemIDberechnen(),finale);
			hoechsterSetzplatz = (int) Math.pow(2,i+2);
			this.getRundenArray().add(0,new ZeitplanRunde());
			for (int j=1; j<=Math.pow(2,i); j++)
			{
				int leftKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1000;
				int rightKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1001;
				int leftKnotenSetzPlatzHeim = aktuellerKnoten.getSetzplatzHeim();
				dicSpiele.get(leftKnotenSpielID).setSetzPlatzHeim(leftKnotenSetzPlatzHeim);
				int leftKnotenSetzPlatzGast = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1;
				dicSpiele.get(leftKnotenSpielID).setSetzPlatzGast(leftKnotenSetzPlatzGast);
				int rightKnotenSetzPlatzHeim = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1;
				dicSpiele.get(rightKnotenSpielID).setSetzPlatzHeim(rightKnotenSetzPlatzHeim);
				int rightKnotenSetzPlatzGast = aktuellerKnoten.getSetzplatzGast();
				dicSpiele.get(rightKnotenSpielID).setSetzPlatzGast(rightKnotenSetzPlatzGast);
				aktuellerKnoten.addLeft(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast );
				aktuellerKnoten.getLeft().setSpiel(dicSpiele.get(leftKnotenSpielID));
				aktuellerKnoten.addRight(rightKnotenSpielID, rightKnotenSetzPlatzHeim ,rightKnotenSetzPlatzGast );
				aktuellerKnoten.getRight().setSpiel(dicSpiele.get(rightKnotenSpielID));
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
				this.getRundenArray().get(0).add(dicSpiele.get(leftKnotenSpielID));
				this.getRundenArray().get(0).add(dicSpiele.get(rightKnotenSpielID));
			}
			erhoeheAktuelleRunde();
		}
	}


	public SpielTree knotenAufbauen (int teilnehmerzahl){
		int anzahlRunden = rundenBerechnen();
		int hoechsterSetzplatz;
		SpielTree aktuellerKnoten = finale;
		finale.setSpiel(new Spiel(spielSystemIDberechnen(),1,2,this.spielsystem));
		this.getRundenArray().add(new ZeitplanRunde());
		this.getRundenArray().get(0).add(finale.getSpiel());

		for (int i=0; i<anzahlRunden-1; i++){ //erstelle für jeder Runde Spiele
			aktuellerKnoten = finale.getSpielTree(spielSystemIDberechnen(),finale);
			this.getRundenArray().add(0,new ZeitplanRunde());
			hoechsterSetzplatz = (int) Math.pow(2,i+2);
			for (int j=1; j<=Math.pow(2,i); j++)
			{
				int leftKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1000;
				int rightKnotenSpielID = ((aktuellerKnoten.getSpielID()-getSpielSystemArt()*10000000-getAktuelleRunde()*1000)*2+getSpielSystemArt()*10000000+getAktuelleRunde()*1000)+1001;
				int leftKnotenSetzPlatzHeim = aktuellerKnoten.getSetzplatzHeim();
				int leftKnotenSetzPlatzGast = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzHeim() + 1;
				int rightKnotenSetzPlatzHeim = hoechsterSetzplatz - aktuellerKnoten.getSetzplatzGast() + 1;
				int rightKnotenSetzPlatzGast = aktuellerKnoten.getSetzplatzGast();
				Spiel leftSpiel = new Spiel(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast,this.spielsystem);
				Spiel rightSpiel = new Spiel(rightKnotenSpielID, rightKnotenSetzPlatzHeim , rightKnotenSetzPlatzGast, this.spielsystem);
				aktuellerKnoten.addLeft(leftKnotenSpielID, leftKnotenSetzPlatzHeim, leftKnotenSetzPlatzGast );
				aktuellerKnoten.getLeft().setSpiel(leftSpiel);
				aktuellerKnoten.addRight(rightKnotenSpielID, rightKnotenSetzPlatzHeim ,rightKnotenSetzPlatzGast );
				aktuellerKnoten.getRight().setSpiel(rightSpiel);
				aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale);
				this.getRundenArray().get(0).add(leftSpiel);
				this.getRundenArray().get(0).add(rightSpiel);
			}
			erhoeheAktuelleRunde();
		}
	return finale;
	}

	public int rundenBerechnen(){
		setAnzahlRunden((int) Math.ceil(Math.log(teilnehmerzahl) / Math.log(2.0)));
		return getAnzahlRunden();
	}

	public void ersteRundeFuellen(List<Team> setzliste){
		//Zu erstem Knoten in erster Runde gehen:
		SpielTree aktuellerKnoten = finale;
		while (aktuellerKnoten.getLeft() != null){
			aktuellerKnoten = aktuellerKnoten.getLeft();
		}
		for (int i=0; i<Math.pow(2,getAnzahlRunden()-1);i++){
			for (int j=0; j<setzliste.size();j++){
				if(j+1 == aktuellerKnoten.getSetzplatzHeim()){
					aktuellerKnoten.getSpiel().setHeim(setzliste.get(j));
				}
				if(j+1 == aktuellerKnoten.getSetzplatzGast()){
					aktuellerKnoten.getSpiel().setGast(setzliste.get(j));
				}
			}
			aktuellerKnoten = aktuellerKnoten.getSpielTree(aktuellerKnoten.getSpielID()+1, finale); //zum Spiel mit nächster ID gehen (im Turnierbaum 1 Spiel nach unten)
		}
	}

	private void freiloseZuKnotenHinzufuegen() { //nur für Endrunde, wenn keine Setzliste vorhanden

		for (int setzplatz = anzahlFreilose+teilnehmerzahl;setzplatz>teilnehmerzahl;setzplatz--){
			for (int i=0;i<this.getRundenArray().get(0).size();i++){
				Spiel spiel =this.getRundenArray().get(0).get(i);
				if(spiel.getSetzPlatzHeim()==setzplatz){
					Team freilos = new Team("Freilos", this.getSpielklasse());
					spiel.setHeim(freilos);
					//spiel.getSpielDAO().update(spiel);
				}
				else if(spiel.getSetzPlatzGast()==setzplatz){
					Team freilos = new Team("Freilos", this.getSpielklasse());
					spiel.setGast(freilos);
					//spiel.getSpielDAO().update(spiel);
				}
			}
		}
	}

	private void freiloseHinzufuegen (List<Team> setzliste){
		for (int i=setzliste.size(); i<Math.pow(2,rundenBerechnen());i++){
			setzliste.add(new Team("Freilos",this.getSpielklasse()));
			super.setzlisteDAO.create(setzliste.size(),setzliste.get(setzliste.size()-1),this.getSpielklasse());
		}
	}

	private void freiloseHinzufuegen (int anzahlTeilnehmer){
		for (int i=anzahlTeilnehmer; i<Math.pow(2,rundenBerechnen());i++){
			anzahlFreilose++;
		}
	}

	@Override
	public List<Team> getPlatzierungsliste() {
		return null;
	}

	@Override
	public boolean beendeMatch(Spiel spiel) {

		if (spiel==spielUm3){
			spiel.getSieger().setPlatzierung(3);
			spiel.getVerlierer().setPlatzierung(4);
			return true;
		}
		if(spiel==finale.getSpiel()){
			finale.getSpiel().getSieger().setPlatzierung(1);
			finale.getSpiel().getVerlierer().setPlatzierung(2);
			return true;
		}
		if(spiel.getRundenNummer()>0) {
			if (spiel.getRundenNummer()==1&&platzDreiAusspielen){
				verliererZuSpielUmDreiHinzufuegen(spiel);
			}
			if(spiel.getRundenNummer()>1) {
				spiel.getVerlierer().setPlatzierung(((int) Math.pow(spiel.getRundenNummer() , 2)) + 1);
			}
			SpielTree parent = finale.getSpielTree(spiel.getSystemSpielID(), finale).getParent();
			if (parent.getLeft().getSpielID() == spiel.getSystemSpielID()) {
				parent.getSpiel().setHeim(spiel.getSieger());
			} else {
				parent.getSpiel().setGast(spiel.getSieger());
			}
			parent.getSpiel().getSpielDAO().update(parent.getSpiel());
			return false;
		}
		else{
			return true;
		}
	}

	private void verliererZuSpielUmDreiHinzufuegen(Spiel spiel) {
		Spiel spielUmDrei = getSpielklasse().getSpiele().get(getSpielSystemArt()*10000000 + 1);
		Team verlierer;
		if(spiel.getSieger()!=null){
			if (spiel.getSieger()==spiel.getHeim()){
				verlierer=spiel.getGast();
			}
			else{
				verlierer=spiel.getHeim();
			}
			if (spiel.getSpielNummerInRunde()==0){
				spielUmDrei.setHeim(verlierer);
			}
			else{
				spielUmDrei.setGast(verlierer);
			}
			spielUmDrei.setFreilosErgebnis();
			spielUmDrei.getSpielDAO().update(spielUmDrei);
		}
	}

	public Spiel getSpielUm3() {
		return spielUm3;
	}

	private void spielUmDreiErstellen() {
		int spielUmDreiSystemSpielID =getSpielSystemArt()*10000000+1;
		spielUm3 = new Spiel(spielUmDreiSystemSpielID,this.spielsystem);
		platzDreiAusspielen = true;
		spielsystem.getSpielklasse().getSpiele().put(spielUmDreiSystemSpielID,spielUm3);
		getRundenArray().get(getRundenArray().size()-1).add(spielUm3);
	}

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		return false;
	}

	public ArrayList<Team> getSetzliste(){
		return setzliste;
	}
}