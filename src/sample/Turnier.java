package sample;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Maxdate;

public class Turnier {

    private int matchDauer;
	private int gesamtSpiele;
	private LocalDate datum = LocalDate.now();
	private String name;
	private int zaehlweise = 0; // 0=bis21 1=bis 11 mit Verlängerung 2=bis 11 ohne Verlängerung
	private int spielerPausenZeit = 10;
	private int turnierid;
	private boolean zeitPlanAktuell;
	private LocalDateTime startzeitEinzel;
	private LocalDateTime startzeitDoppel;
	private LocalDateTime startzeitMixed;
	private float MeldegebuehrEinzel,MeldegebuehrDoppel;

	public Turnier(String text, LocalDateTime LocalDateTime, LocalDateTime localDateTime1, LocalDateTime localDateTime2, Float meldegebuehreinzel, Float meldegebuehrdoppel) {
		this.name=text;
		this.turnierid=turnierid;
		this.startzeitEinzel=LocalDateTime;
		this.startzeitDoppel=localDateTime1;
		this.startzeitMixed=localDateTime2;
		this.MeldegebuehrEinzel=meldegebuehreinzel;
		this.MeldegebuehrDoppel=meldegebuehrdoppel;

		this.datum= LocalDate.from((TemporalAccessor) Maxdate.max(startzeitEinzel,startzeitDoppel,startzeitMixed));
	}

	public float getMeldegebuehrEinzel() {
		return MeldegebuehrEinzel;
	}

	public void setMeldegebuehrEinzel(float meldegebuehrEinzel) {
		MeldegebuehrEinzel = meldegebuehrEinzel;
	}

	public float getMeldegebuehrDoppel() {
		return MeldegebuehrDoppel;
	}

	public void setMeldegebuehrDoppel(float meldegebuehrDoppel) {
		MeldegebuehrDoppel = meldegebuehrDoppel;
	}

	private  Dictionary<Integer, Spielklasse> spielklassen = new Hashtable<Integer,Spielklasse>();
	private  ArrayList<Feld> felder = new ArrayList<>();
	//private Dictionary<Integer, Verein> vereine = new Hashtable<Integer,Verein>();
	//private Dictionary<Integer, Spieler> spieler = new Hashtable<Integer,Spieler>();
	private static Dictionary<Integer, Team> teams = new Hashtable<Integer,Team>();
	private static Dictionary<Integer, Spiel> spiele = new Hashtable<Integer,Spiel>();
	private static ObservableList<Spiel> obs_alleSpiele = FXCollections.observableArrayList();
	private static ObservableList<Spiel> obs_angezeigteSpiele = FXCollections.observableArrayList();
	private static ObservableList<Integer> obs_spielklassen_auswahl = FXCollections.observableArrayList();
	private static ObservableList<Spielklasse> obs_spielklassen = FXCollections.observableArrayList();

	public Turnier(String text, LocalDateTime lde, LocalDateTime ldd, LocalDateTime ldm) {
		this.name=text;
		this.startzeitEinzel=lde;
        this.startzeitDoppel=ldd;
        this.startzeitMixed=ldm;
	}

    public Turnier(String turnierName, int turnierid, LocalDateTime localDateTime, LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
	    this.name=turnierName;
	    this.turnierid=turnierid;
	    this.startzeitEinzel=localDateTime;
        this.startzeitDoppel=localDateTime1;
        this.startzeitMixed=localDateTime2;
        Date d1=new Date();
        Date d2=new Date();
        Date d3=new Date();

        this.datum= LocalDate.from((TemporalAccessor) Maxdate.max(startzeitEinzel,startzeitDoppel,startzeitMixed));
    }


    public ObservableList<Spielklasse> getObs_spielklassen() {
		return obs_spielklassen;
	}

	public ObservableList<Spiel> getObs_alleSpiele() {
		return obs_alleSpiele;
	}

	public static ObservableList<Spiel> getObs_angezeigteSpiele() {
		return obs_angezeigteSpiele;
	}

	public ObservableList<Integer> getObs_spielklassen_auswahl() {
		return obs_spielklassen_auswahl;
	}

	public void setObs_spielklassen(ObservableList<Spielklasse> obs_spielklassen) {
		this.obs_spielklassen = obs_spielklassen;
	}
	public void addObs_spielklassen(Spielklasse spielklasse) {
		this.obs_spielklassen.add(spielklasse);
	}
	public void removeobs_spielklassen(Spielklasse spielklasse) {
		this.obs_spielklassen.remove(spielklasse);
	}
	public void addObs_spiele(Spiel spiel) {
		this.obs_alleSpiele.add(spiel);
	}
	public void removeobs_spielklassen(Spiel spiel) {
		this.obs_alleSpiele.remove(spiel);
	}

	public void deleteSpiel(Spiel spiel){
		this.obs_alleSpiele.remove(spiel);
		this.obs_angezeigteSpiele.remove(spiel);
		this.spiele.remove(spiel.getSpielID());
	}

	/*public Turnier(String name, int turnierid, LocalDate datum) {
		this.datum = datum;
		this.name = name;
		this.turnierid = turnierid;
	}*/

    public void setStartzeitEinzel(LocalDateTime startzeitEinzel) {
        this.startzeitEinzel = startzeitEinzel;
    }

    public void setStartzeitDoppel(LocalDateTime startzeitDoppel) {
        this.startzeitDoppel = startzeitDoppel;
    }

    public void setStartzeitMixed(LocalDateTime startzeitMixed) {
        this.startzeitMixed = startzeitMixed;
    }

    public Turnier(LocalDate datum, String name, int turnierid, LocalDateTime startzeitEinzel, LocalDateTime startzeitDoppel, LocalDateTime startzeitMixed) {
		this.datum = datum;

		this.name = name;
		this.turnierid = turnierid;
		this.startzeitEinzel = startzeitEinzel;
		this.startzeitDoppel = startzeitDoppel;
		this.startzeitMixed = startzeitMixed;
	}

	public Turnier(String name, LocalDate datum) {
		this.datum = datum;
		this.name = name;
	}

	public LocalDateTime getStartzeitEinzel() {
		return startzeitEinzel;
	}

	public LocalDateTime getStartzeitDoppel() {
		return startzeitDoppel;
	}

	public LocalDateTime getStartzeitMixed() {
		return startzeitMixed;
	}

	public void setTurnierid(int turnierid) {
		this.turnierid = turnierid;
	}

	public int getGesamtSpiele() {
		return gesamtSpiele;
	}

	public int getSpielerPausenZeit() {
		return spielerPausenZeit;
	}

	public int getMatchDauer() { return matchDauer; }

	public void setGesamtSpiele(int gesamtSpiele) {
		this.gesamtSpiele = gesamtSpiele;
	}

	public int getZaehlweise() { return zaehlweise; }

	public LocalDate getDatum() {
		return datum;
	}

	public String getName() {
		return name;
	}

	public int getTurnierid() {
		return turnierid;
	}

	public Dictionary<Integer, Spielklasse> getSpielklassen() {
		return spielklassen;
	}
	public Dictionary<Integer, Spielklasse> addSpielklassen(Spielklasse sp) {

		spielklassen.put(sp.getSpielklasseID(),sp);
		return spielklassen;
	}
	public Dictionary<Integer, Spielklasse> removeSpielklassen(Spielklasse sp) {

		spielklassen.remove(sp);
		return spielklassen;
	}


	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Feld> getFelder() {

		return felder;
	}

	public boolean isZeitPlanAktuell() {
		return zeitPlanAktuell;
	}

	public void setZeitPlanAktuell(boolean zeitPlanAktuell) {
		this.zeitPlanAktuell = zeitPlanAktuell;
	}

	public void setFelder(ArrayList<Feld> felder) {
		this.felder = felder;
	}

	public Dictionary<Integer, Team> getTeams() {
		return teams;
	}

	public void setTeams(Dictionary<Integer, Team> team) {
		this.teams = team;
	}

	public Dictionary<Integer, Spiel> getSpiele() {
		return spiele;
	}

	public void setSpiele(Dictionary<Integer, Spiel> spiele) {
		this.spiele = spiele;
	}

	public void setSpielklassen(Dictionary<Integer, Spielklasse> spielklassen) {
		this.spielklassen = spielklassen;
	}

	public void setMatchDauer(int matchDauer) {
		this.matchDauer = matchDauer;
	}

	public void setZaehlweise(int zaehlweise) {
		this.zaehlweise = zaehlweise;
	}

	public void setSpielerPausenZeit(int spielerPausenZeit) {
		this.spielerPausenZeit = spielerPausenZeit;
	}

	public void turnierplanErstellen() {
		throw new UnsupportedOperationException();
	}

	public void turnierplanDrucken() {
		throw new UnsupportedOperationException();
	}

	public void ergebnisFormularerstellen() {
		throw new UnsupportedOperationException();
	}

	public void startgeldlisteDrucken() {
		throw new UnsupportedOperationException();
	}

	public void sechsSpielzettelDrucken(ArrayList<Spiel> spiele){

		Spielzettel test = new Spielzettel(spiele);
        /*//*JFrame window = new JFrame();
		window.setSize(800,600);
		window.setTitle("Spielzettel");
		window.setVisible(true);
		DrawingComponent drawingComponent = new DrawingComponent();
		window.add(test);*/

		PrinterJob job = PrinterJob.getPrinterJob();
		//Book book = new Book();
		PageFormat querFormat = new PageFormat();
		Paper paper = querFormat.getPaper();
		//Remove borders from the paper
		paper.setImageableArea(45, 45, querFormat.getPaper().getWidth()-90, querFormat.getPaper().getHeight()-90);
		querFormat.setPaper(paper);
		querFormat.setOrientation(PageFormat.PORTRAIT);
		//book.append(test,querFormat);
		job.setPrintable(test,querFormat);
		try {
			job.print();
		}
		catch (PrinterException e)
		{
			System.out.println("Drucken fehlgeschlagen");
		}
		System.exit(0);

	}


}