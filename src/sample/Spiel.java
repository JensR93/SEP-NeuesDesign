package sample;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalTime;
import java.util.Dictionary;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import sample.DAO.*;
import sample.Spielsysteme.*;

public class Spiel {
	//ErgebnisDAO ergebnisDAO = new ErgebnisDAOimpl();
	SpielDAO spielDAO = new SpielDAOimpl();
	private int spielID;
	private Team heim;
	private Team gast;
	private Ergebnis ergebnis;
	private LocalTime aufrufZeit;
	private Spieler schiedsrichter;
	private Spielsystem spielsystem;
	private Turnier turnier;
	private Feld feld;
	private int status = 0; //0= unvollständig 1 = ausstehend, 2=aktiv, 3=gespielt
	private int systemSpielID;
	private int setzPlatzHeim;
	private int setzPlatzGast;
	private int zeitplanNummer;
	private int rundenZeitplanNummer;

	private static String baseName = "resources.Main";
	private static String rundeString;
	private static String gruppeString;
	private static String endrundeString;
	private static String schweizerString;
	private static String finaleString;
	private static String spielUm3String;
	private static String halbfinaleString;
	private static String viertelfinaleString;
	private static String achtelfinaleString;
	private static String deKurzform;
	private static String heKurzform;
	private static String hdKurzform;
	private static String ddKurzform;
	private static String mxKurzform;
	private static String ausstehendString;

	public static void SpracheLaden(){
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle( baseName );

			ausstehendString = bundle.getString("ausstehendString");
			gruppeString = bundle.getString("gruppeString");
			rundeString = bundle.getString("rundeString");
			schweizerString = bundle.getString("schweizerString");
			endrundeString = bundle.getString("endrundeString");
			finaleString = bundle.getString("finaleString");
			spielUm3String = bundle.getString("spielUm3String");
			halbfinaleString = bundle.getString("halbfinaleString");
			viertelfinaleString = bundle.getString("viertelfinaleString");
			achtelfinaleString = bundle.getString("achtelfinaleString");
			deKurzform = bundle.getString("deKurzform");
			heKurzform = bundle.getString("heKurzform");
			hdKurzform = bundle.getString("hdKurzform");
			ddKurzform = bundle.getString("ddKurzform");
			mxKurzform = bundle.getString("mxKurzform");



		}
		catch ( MissingResourceException e ) {
			System.err.println( e );
		}
	}

	public void setFeld(Feld feld) {
		this.feld = feld;
		feld.setAktivesSpiel(this);
		spielDAO.update(this);
	}


	public Team getHeim() {
		return heim;
	}

	@Override
	public String toString() {

		String s="";
		if(heim!=null)
		{
			if(heim.getSpielerEins()!=null)
			{
				s+=heim.getSpielerEins().toString();
			}
			if(heim.getSpielerZwei()!=null)
			{
				s+=heim.getSpielerZwei().toString();
			}
		}
		if(gast!=null)
		{
			if(gast.getSpielerEins()!=null)
			{
				s+=gast.getSpielerEins().toString();
			}
			if(gast.getSpielerZwei()!=null)
			{
				s+=gast.getSpielerZwei().toString();
			}

		}

		return s;
	}

	public String getHeimString() {
		if(heim != null){
			return heim.toString();
		}
		else{
			if (spielsystem instanceof sample.Spielsysteme.GruppeMitEndrunde && getVorrundenNummer()==0){
				GruppeMitEndrunde hauptsystem = (GruppeMitEndrunde) spielsystem;
				Dictionary<Integer, Integer[]> setzplatze = hauptsystem.getSetzPlatze();
				if (setzplatze.get(setzPlatzHeim)!=null && hauptsystem.getEndrunde().getRunden().get(0).contains(this)){
					Integer[] gruppePlatzierung = setzplatze.get(setzPlatzHeim);
					return (gruppeString+gruppePlatzierung[0]+" #"+gruppePlatzierung[1]);
				}
			}
			if(getSystemSpielArt()==5){
				return schweizerString+" "+getRundenName();
			}
			return ausstehendString;
		}

	}
	public String getGastString() {
		if(gast!=null){
			return gast.toString();
		}
		else{
			if (spielsystem instanceof sample.Spielsysteme.GruppeMitEndrunde && getVorrundenNummer()==0){
				GruppeMitEndrunde hauptsystem = (GruppeMitEndrunde) spielsystem;
				Dictionary<Integer, Integer[]> setzplatze = hauptsystem.getSetzPlatze();
				if (setzplatze.get(setzPlatzGast)!=null && hauptsystem.getEndrunde().getRunden().get(0).contains(this)){
					Integer[] gruppePlatzierung = setzplatze.get(setzPlatzGast);
					return (gruppeString+gruppePlatzierung[0]+" #"+gruppePlatzierung[1]);
				}
			}
			if(getSystemSpielArt()==5){
				return schweizerString+" "+getRundenName();
			}
			return ausstehendString;
		}
	}
	public String getHeimStringKomplett() {
		if(heim != null){
			return heim.toStringKomplett();
		}
		else{
			if (spielsystem instanceof sample.Spielsysteme.GruppeMitEndrunde && getVorrundenNummer()==0){
				GruppeMitEndrunde hauptsystem = (GruppeMitEndrunde) spielsystem;
				Dictionary<Integer, Integer[]> setzplatze = hauptsystem.getSetzPlatze();
				if (setzplatze.get(setzPlatzHeim)!=null && hauptsystem.getEndrunde().getRunden().get(0).contains(this)){
					Integer[] gruppePlatzierung = setzplatze.get(setzPlatzHeim);
					return (gruppeString+gruppePlatzierung[0]+" #"+gruppePlatzierung[1]);
				}
			}
			if(getSystemSpielArt()==5){
				return schweizerString+" "+getRundenName();
			}
			return ausstehendString;
		}

	}
	public String getGastStringKomplett() {
		if(gast!=null){
			return gast.toStringKomplett();
		}
		else{
			if (spielsystem instanceof sample.Spielsysteme.GruppeMitEndrunde && getVorrundenNummer()==0){
				GruppeMitEndrunde hauptsystem = (GruppeMitEndrunde) spielsystem;
				Dictionary<Integer, Integer[]> setzplatze = hauptsystem.getSetzPlatze();
				if (setzplatze.get(setzPlatzGast)!=null && hauptsystem.getEndrunde().getRunden().get(0).contains(this)){
					Integer[] gruppePlatzierung = setzplatze.get(setzPlatzGast);
					return (gruppeString+gruppePlatzierung[0]+" #"+gruppePlatzierung[1]);
				}
			}
			if(getSystemSpielArt()==5){
				return schweizerString+" "+getRundenName();
			}
			return ausstehendString;
		}
	}
	public Team getGast() {
		return gast;
	}

	public boolean isGast() {


		return false;
	}
	public void setHeim(Team heim) {
		this.heim = heim;
		if(this.gast != null){
			this.status = 1;

		}
		//spielDAO.update(this);
	}

	public void setGast(Team gast) {
		this.gast = gast;
		if(this.heim != null){
			this.status = 1;
		/*	if (this.heim.isFreilos()){
				this.setErgebnis(new Ergebnis(0,21,0,21));
			}
			else if(this.gast.isFreilos()){
				this.setErgebnis(new Ergebnis(21,0,21,0));
			}*/
		}
		//spielDAO.update(this);
	}

	public SpielDAO getSpielDAO() {
		return spielDAO;
	}

	public Spieler getSchiedsrichter() {
		return schiedsrichter;
	}

	public LocalTime getAufrufZeit() {
		return aufrufZeit;
	}

	public Feld getFeld() {
		return feld;
	}

	public int getStatus() {
		return status;
	}

	public int getSystemSpielID() {
		return systemSpielID;
	}
	public int getSystemSpielArt() {
		return systemSpielID/10000000;
	}
	public int getVorrundenNummer(){ //0 für Endrunde / einzige Runde
		return (systemSpielID - getSystemSpielArt()*10000000)/100000;
	}
	public int getRundenNummer(){
		return (systemSpielID - getSystemSpielArt()*10000000-getVorrundenNummer()*100000)/1000;
	}
	public int getSpielNummerInRunde(){
		return (systemSpielID - getSystemSpielArt()*10000000-getVorrundenNummer()*100000 - getRundenNummer()*1000);
	}

	public int getSpielID() {
		return spielID;
	}

	public Spiel(Team heim, Team gast, int systemSpielID, Spielsystem spielsystem) {
		this.heim = heim;
		this.gast = gast;
		this.spielsystem=spielsystem;
		this.systemSpielID = systemSpielID;
		this.turnier = this.spielsystem.getSpielklasse().getTurnier();
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		//turnier.getObs_ausstehendeSpiele().add(this);
		turnier.getObs_alleSpiele().add(this);
		//auswahlklasse.getAktuelleTurnierAuswahl().getObs_alleSpiele().add(this);
		//spielDAO.create(this);
		this.status = 1;
		/*if(heim.isFreilos()){
			setErgebnis(new Ergebnis(21,0,21,0));
		}*/
	}


	public Spiel(int systemSpielID, Spielsystem spielsystem) { //Constructor für Extrarunden (Gruppe mit Endrunde)
		this.spielsystem = spielsystem;
		this.systemSpielID = systemSpielID;
		this.turnier = this.spielsystem.getSpielklasse().getTurnier();
		//spielDAO.create(this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		//this.spielsystem.getSpielklasse().getTurnier().getObs_zukuenftigeSpiele().add(this);
		this.spielsystem.getSpielklasse().getTurnier().getObs_alleSpiele().add(this);

	}
	public int getSpielklasseid()
	{
		return  this.spielsystem.getSpielklasse().getSpielklasseID();
	}

	public Spiel(int systemSpielID, int setzPlatzHeim, int setzPlatzGast, Spielsystem spielsystem) {
		this.systemSpielID = systemSpielID; //Constructor für SpielTree in KO-System
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzHeim = setzPlatzHeim;
		this.setzPlatzGast = setzPlatzGast;
		this.spielsystem=spielsystem;
		//this.spielDAO.create(this);
		this.turnier = this.spielsystem.getSpielklasse().getTurnier();
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
		//this.spielsystem.getSpielklasse().getTurnier().getObs_ausstehendeSpiele().add(this);
		this.spielsystem.getSpielklasse().getTurnier().getObs_alleSpiele().add(this);
	}


	public Spiel(int spielID, Team heim, Team gast, LocalTime aufrufZeit, Spieler schiedsrichter, int status, int systemSpielID, Feld feld, int zeitplanNummer, int RundenZeitplanNummer) {
		this.spielID = spielID;
		this.heim = heim;						//Constructor für einlesen. Anschließend MUSS Spielsystem gesettet werden!)
		this.gast = gast;
		this.aufrufZeit = aufrufZeit;
		this.schiedsrichter = schiedsrichter;
		this.status = status;
		this.systemSpielID = systemSpielID;
		this.feld = feld;
		this.zeitplanNummer=zeitplanNummer;
		this.rundenZeitplanNummer = getRundenZeitplanNummer();
	}

	public Team getSieger(){
		if(ergebnis!=null){
			return ergebnis.getSieger(this);
		}
		else{
			return null;
		}
	}
	public boolean istTeamInSpiel(Team team){
		if(team==gast||team==heim){
			return true;
		}
		return false;
	}
	public String getErgebnisString(Team team1){
		if(ergebnis!=null) {
			if (team1 == heim) {
				return getErgebnisString();
			} else {
				return ergebnis.toStringUmgedreht();
			}
		}
		else{
			return "";
		}
	}

	public void setSpielsystem(Spielsystem spielsystem) {
		this.spielsystem = spielsystem;
		this.spielsystem.getSpielklasse().getTurnier().getObs_alleSpiele().add(this);
		this.spielsystem.getSpielklasse().getTurnier().getSpiele().put(spielID,this);
		this.spielsystem.getSpielklasse().getSpiele().put(systemSpielID,this);
	}

	public int getZeitplanNummer() {
		return zeitplanNummer;
	}

	public void setZeitplanNummer(int zeitplanNummer) {
		this.zeitplanNummer = zeitplanNummer;
	}

	public int getRundenZeitplanNummer() {
		return rundenZeitplanNummer;
	}

	public void setRundenZeitplanNummer(int rundenZeitplanNummer) {
		this.rundenZeitplanNummer = rundenZeitplanNummer;
	}

	public Spielsystem getSpielsystem() {
		return spielsystem;
	}

	public void setSchiedsrichter(Spieler schiedsrichter) {
		this.schiedsrichter = schiedsrichter;
	}

	public void spielAufrufen(Feld feld) {
		this.feld = feld;
		this.aufrufZeit = LocalTime.now();
		this.status = 2;
	}
	public String getSpielNummer(){
		Integer nr = zeitplanNummer;
		return nr.toString();
	}
	public Team getVerlierer(){
		if (heim==getSieger()){
			return gast;
		}
		else if(gast==getSieger()){
			return heim;
		}
		return null;
	}
	public String getRundenName(){
		if (systemSpielID<20000000 ||(systemSpielID<30000000&&getVorrundenNummer()!=0)){ //Gruppe und Gruppenphase bei Gruppe mit Endrunde
			return rundeString+" "+(getRundenNummer() + 1);
		}
		/*else if(){ //Gruppe mit Endrunde
			return "Runde "+(getRundenNummer()+1);
		}*/
		else if(systemSpielID<40000000){ //K.O. system
			int rundenNummer = (systemSpielID-systemSpielID/10000000*10000000)/1000;
			if (spielsystem instanceof GruppeMitEndrunde)
			{
				GruppeMitEndrunde gruppeMitEndrunde = (GruppeMitEndrunde) spielsystem;
				if(gruppeMitEndrunde.getEndrunde() instanceof Gruppe){
					return endrundeString + " "+(getRundenNummer() + 1);
				}
			}
			if(rundenNummer==0 && getVorrundenNummer()==0){
				if (getSpielNummerInRunde()==0){
					return finaleString;
				}
				if(getSpielNummerInRunde()==1){
					return spielUm3String;
				}
			}
			else if(rundenNummer==1){
				return halbfinaleString;
			}
			else if(rundenNummer==2){
				return viertelfinaleString;
			}
			else if(rundenNummer==3){
				return achtelfinaleString;
			}
			else if(rundenNummer==4){
				return "1/16-"+finaleString;
			}
			else if(rundenNummer==5){
				return "1/32-"+finaleString;
			}
			else if(rundenNummer==6){
				return "1/64-"+finaleString;
			}
			else{
				rundenNummer = spielsystem.getAnzahlRunden() - rundenNummer;
				return rundeString+" " +rundenNummer;
			}
		}
		else if(systemSpielID<50000000){ //K.O. mit TrostRunde

		}
		else{ // Schweizer System
			int rundenNummer = (systemSpielID-systemSpielID/10000000*10000000)/1000;
			return rundeString+ " " +(rundenNummer + 1);
		}
		return "";
	}
	public void setStatus(int status) {
		this.status = status;
		spielDAO.update(this);
	}

	public Ergebnis getErgebnis() {
		return ergebnis;
	}

	public String getErgebnisString() {
		if (ergebnis!=null){
			return ergebnis.toString();
		}
		else {
			return "";
		}
	}
	public String getSpielklasseString() {
		if (spielsystem.getSpielklasse()!=null){
			return spielsystem.getSpielklasse().getDisziplin()+"-"+spielsystem.getSpielklasse().getNiveau();
		}
		else {
			return "";
		}

	}
	public String getFeldNr() {
		if (feld!=null){
			return feld.toString();
		}
		else{
			return "";
		}
	}
	public String getRundenNameKurz(){
		String rundenName ="";
		rundenName += disziplinKurzform();
		rundenName += "-";
		rundenName += spielsystem.getSpielklasse().getNiveau();
		rundenName += " ";
		rundenName += getRundenName();
		return rundenName;
	}
	private String disziplinKurzform(){
		String disziplin = spielsystem.getSpielklasse().getDisziplin().toUpperCase();
		String kurzform = "";
		if(disziplin.equalsIgnoreCase("DAMENEINZEL")){
			kurzform = deKurzform;
		}
		else if(disziplin.equalsIgnoreCase("HERRENEINZEL")) {
			kurzform = heKurzform;
		}
		else if(disziplin.equalsIgnoreCase("HERRENDOPPEL")) {
			kurzform = hdKurzform;
		}
		else if(disziplin.equalsIgnoreCase("DAMENDOPPEL")) {
			kurzform = ddKurzform;
		}
		else if(disziplin.equalsIgnoreCase("MIXED")) {
			kurzform = mxKurzform;
		}
		return kurzform;
	}
	public void setFreilosErgebnis(){
		if (this.gast != null && this.heim != null && this.heim.isFreilos()){
			this.ergebnis = new Ergebnis(0,21,0,21);
			status=3;
			this.spielsystem.beendeMatch(this);
			spielDAO.update(this);
			/*gast.addGewonnenenSatz();
			gast.addGewonnenenSatz();
			gast.addGewonnenesSpiel();
			gast.addGespieltePunkte(42,0);*/
			gast.getTeamDAO().update(gast);
			ergebnis.getErgebnisDAO().create(this);
/*			this.getSpielsystem().getSpielklasse().getTurnier().getObs_zukuenftigeSpiele().remove(this);
			this.getSpielsystem().getSpielklasse().getTurnier().getObs_gespielteSpiele().add(this);*/
		}
		else if(this.heim != null && this.gast != null && this.gast.isFreilos()){
			this.ergebnis = new Ergebnis(21,0,21,0);
			status=3;
			this.spielsystem.beendeMatch(this);
			spielDAO.update(this);
			/*heim.addGewonnenenSatz();
			heim.addGewonnenenSatz();
			heim.addGewonnenesSpiel();
			heim.addGespieltePunkte(42,0);*/
			heim.getTeamDAO().update(heim);
			ergebnis.getErgebnisDAO().create(this);
/*			this.getSpielsystem().getSpielklasse().getTurnier().getObs_zukuenftigeSpiele().remove(this);
			this.getSpielsystem().getSpielklasse().getTurnier().getObs_gespielteSpiele().add(this);*/
		}
	}

	public void setErgebnis(Ergebnis ergebnis) {
		this.ergebnis = ergebnis;
		statistikAktualisieren();
		status=3;
		this.spielsystem.beendeMatch(this);
		spielDAO.update(this);
		heim.getTeamDAO().update(heim);
		gast.getTeamDAO().update(gast);
		if(!heim.isFreilos()&&!gast.isFreilos()) {
			heim.getGespielteSpiele().add(this);
			gast.getGespielteSpiele().add(this);
		}
		ergebnis.getErgebnisDAO().create(this);
/*		this.getSpielsystem().getSpielklasse().getTurnier().getObs_aktiveSpiele().remove(this);
		this.getSpielsystem().getSpielklasse().getTurnier().getObs_gespielteSpiele().add(this);*/
		if (this.feld != null){
			this.feld.spielBeenden();
		}
	}

	public void setErgebnis(Ergebnis ergebnis, String einlesen) {
		this.ergebnis = ergebnis;
		statistikAktualisieren();
		status=3;
		if(!heim.isFreilos()&&!gast.isFreilos()) {
			heim.getGespielteSpiele().add(this);
			gast.getGespielteSpiele().add(this);
		}
		if (spielsystem.getSpielSystemArt()!=2 && this.getVorrundenNummer()!=0) {
			this.spielsystem.beendeMatch(this, einlesen);
		}
	}

	private void statistikAktualisieren() {
		if ((getSystemSpielArt()==5 || heim != null && gast !=null &&(!heim.isFreilos()&&!gast.isFreilos()))) {
			int satzpunkteHeim = 0;
			int satzpunkteGast = 0;

			heim.addBisherigenGegner(gast);
			gast.addBisherigenGegner(heim);
			getSieger().addGewonnenesSpiel();
			int i = 0;
			while (i < ergebnis.getErgebnisArray().length / 2) {
				satzpunkteHeim = ergebnis.getErgebnisArray()[i * 2];
				satzpunkteGast = ergebnis.getErgebnisArray()[i * 2 + 1];
				heim.addGespieltePunkte(satzpunkteHeim, satzpunkteGast);
				gast.addGespieltePunkte(satzpunkteGast, satzpunkteHeim);
				if (satzpunkteHeim > satzpunkteGast) {
					heim.addGewonnenenSatz();
					gast.addVerlorenenSatz();
				} else {
					gast.addGewonnenenSatz();
					heim.addVerlorenenSatz();
				}
				i++;
			}
		}
	}

	private void punkteHinzufuegen(){

	}

	public void setSpielID(int spielID) {
		this.spielID = spielID;
		this.turnier.getSpiele().put(spielID,this);
	}

	public void setAufrufZeit(LocalTime aufrufZeit) {
		this.aufrufZeit = aufrufZeit;
	}

	public void setSetzPlatzHeim(int setzPlatzHeim) {
		this.setzPlatzHeim = setzPlatzHeim;
	}

	public void setSetzPlatzGast(int setzPlatzGast) {
		this.setzPlatzGast = setzPlatzGast;
	}

	public int getSetzPlatzHeim() {
		return setzPlatzHeim;
	}

	public int getSetzPlatzGast() {
		return setzPlatzGast;
	}

	public void spielzettelDrucken() {
		Spielzettel test = new Spielzettel(this);

		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat querFormat = new PageFormat();
		Paper paper = querFormat.getPaper();
		paper.setImageableArea(45, 45, querFormat.getPaper().getWidth()-90, querFormat.getPaper().getHeight()-90);
		querFormat.setPaper(paper);
		querFormat.setOrientation(PageFormat.LANDSCAPE);
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