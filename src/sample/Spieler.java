package sample;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static java.time.temporal.ChronoUnit.MINUTES;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.DAO.*;

public class Spieler {
	SpielerDAO spielerDAO = new SpielerDAOimpl();
	private String vName;
	private boolean gebuehrenbezahlt;
	private String Notiz;
	public String getNotiz() {
		return Notiz;
	}
	public void setNotiz(String notiz) {
		Notiz = notiz;
	}
	private String nName;
	private LocalDate gDatum;
	private int spielerID;
	private boolean geschlecht;
	private int[] rPunkte = new int[3]; //[0]=Einzel-, [1]=Doppel-, [2]=Mixed-Ranglistenpunkte
	private Verein verein;
	private int Nationalitaet = 0;
	private LocalTime verfuegbar = LocalTime.now();
	private int mattenSpiele = 0;
	private String extSpielerID;
	private Spiel aktuellesSpiel;

	public void setSpielerDAO(SpielerDAO spielerDAO) {
		this.spielerDAO = spielerDAO;
	}

	public String getvName() {
		return vName;
	}

	public boolean isGebuehrenbezahlt() {
		return gebuehrenbezahlt;
	}

	public void setGebuehrenbezahlt(boolean gebuehrenbezahlt) {
		this.gebuehrenbezahlt = gebuehrenbezahlt;
	}

	public String getnName() {
		return nName;
	}

	public LocalDate getgDatum() {
		return gDatum;
	}

	public boolean isGeschlecht() {
		return geschlecht;
	}

	//Einlesen neu
	public Spieler(String vName, String nName, LocalDate gdatum, int spielerID, boolean geschlecht, int[] rPunkte, Verein verein, float meldegebuehren, int nationalitaet, LocalDate verfuegbar, int mattenSpiele, String extSpielerID) {
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.spielerID = spielerID;
		this.geschlecht = geschlecht;
		this.rPunkte = rPunkte;
		this.verein = verein;
		this.Nationalitaet = nationalitaet;
		this.verfuegbar = LocalTime.now();
		this.mattenSpiele = mattenSpiele;
		this.extSpielerID = extSpielerID;
		this.aktuellesSpiel = aktuellesSpiel;

	}

	public Spieler() {

	}
	public Spieler(String notiz, LocalDate gDatum, String vName, String nName, LocalDate gdatum, int spielerID, boolean geschlecht, int[] rPunkte, Verein verein, float meldegebuehren, int nationalitaet, LocalDate verfuegbar, int mattenSpiele, String extSpielerID, boolean offenerBetrag) {
		this.Notiz=notiz;
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.spielerID = spielerID;
		this.geschlecht = geschlecht;
		this.rPunkte = rPunkte;
		this.verein = verein;

		this.Nationalitaet = nationalitaet;
		this.verfuegbar = LocalTime.now();
		this.mattenSpiele = mattenSpiele;
		this.extSpielerID = extSpielerID;
		this.aktuellesSpiel = aktuellesSpiel;
		this.gebuehrenbezahlt =offenerBetrag;
	}


	public void setvName(String vName) {
		this.vName = vName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public void setSpielerID(int spielerID) {
		this.spielerID = spielerID;
	}

	public Spieler(String vName, String nName){
		this.vName = vName;
		this.nName = nName;
		spielerDAO.create(this);
	}
	public Spieler(String vName, String nName, LocalDate gDatum, int spielerID){
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.spielerID=spielerID;
		spielerDAO.create(this);
	}

	public Spieler(String vName, String nName, LocalDate gDatum, boolean geschlecht, int[] rPunkte, Verein verein,String extSpielerID)
	{
		this.vName = vName;
		this.nName = nName;
		this.gDatum = gDatum;
		this.geschlecht = geschlecht;
		this.rPunkte = rPunkte;
		this.verein = verein;
		this.extSpielerID = extSpielerID;
		spielerDAO.create(this);

	}
	//Spieler erstellen (Prüfung)
	public Spieler(String vName, String nName, LocalDate gDatum, boolean geschlecht, int[] rPunkte, Verein verein,String extSpielerID, String s)
	{


	}
	public Spieler(String text, String t_nnText, LocalDate value, boolean geschlecht, int[] rpunkte, Verein verein, String t_spidText, Object selectedItem, String s){
		this.vName = text;
		this.nName = t_nnText;
		this.gDatum = value;
		this.geschlecht = geschlecht;
		this.rPunkte = rpunkte;
		this.verein = verein;
		this.extSpielerID = t_spidText;
		this.Nationalitaet= (int) selectedItem;
	}
	public void einzelPunkteUpdate(int einzelPunkte){
		rPunkte[0] = einzelPunkte;
	}
	public void doppelPunkteUpdate(int doppelPunkte){
		rPunkte[1] = doppelPunkte;
	}
	public void mixedPunkteUpdate(int mixedPunkte){
		rPunkte[2] = mixedPunkte;
	}

	public boolean deleteSpieler(Spieler spieler){
		try {
			SpielerDAOimpl dao = new SpielerDAOimpl();
			dao.delete(this);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Loeschen klappt nicht");
			return false;
		}

	}

	public String toString(){
		if (nName != null) {
			return vName + " " + nName;
		}
		else
		{
			return vName;
		}
	}

	public void setgDatum(LocalDate gDatum) {
		this.gDatum = gDatum;
		//spielerDAO.update(this);
	}

	public void setrPunkte(int[] rPunkte) {
		this.rPunkte = rPunkte;
		//spielerDAO.update(this);
	}

	public void setVerein(Verein verein) {
		this.verein = verein;
		//spielerDAO.update(this);
	}



	public SpielerDAO getSpielerDAO() {
		return spielerDAO;
	}

	public void setNationalitaet(int nationalitaet) {
		Nationalitaet = nationalitaet;
		//spielerDAO.update(this);
	}

	public void setVerfuegbar(LocalTime verfuegbar) {
		this.verfuegbar = verfuegbar;
		//spielerDAO.update(this);
	}

	public void setMattenSpiele(int mattenSpiele) {
		this.mattenSpiele = mattenSpiele;
		//spielerDAO.update(this);
	}

	public void setExtSpielerID(String extSpielerID) {
		this.extSpielerID = extSpielerID;
		//spielerDAO.update(this);
	}

	public void setAktuellesSpiel(Spiel aktuellesSpiel) {
		this.aktuellesSpiel = aktuellesSpiel;
		//spielerDAO.update(this);
	}
	public ArrayList<Spielklasse> checkeSetzlisteMitglied(Spieler spieler)
	{

		ArrayList <Spielklasse> vorhandenspielklassen = new ArrayList<>();

		Enumeration enumeration = auswahlklasse.getAktuelleTurnierAuswahl().getTeams().keys();

		while (enumeration.hasMoreElements())
		{
			int key=(int) enumeration.nextElement();
			Spielklasse spielklasse = auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielklasse();

			if(!vorhandenspielklassen.contains(spielklasse)) {

				if (auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielerEins() != null) {
					if (auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielerEins().toString().equals(spieler.toString())) {

						vorhandenspielklassen.add(spielklasse);
					}
				}
				if (auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielerZwei() != null) {
					if (auswahlklasse.getAktuelleTurnierAuswahl().getTeams().get(key).getSpielerZwei().toString().equals(spieler.toString())) {
						vorhandenspielklassen.add(spielklasse);
					}
				}

				if (spielklasse.isSetzliste_gesperrt()) {
					vorhandenspielklassen.add(spielklasse);
				}
			}
		}
		return vorhandenspielklassen;
	}

	public boolean isVerfuegbar(){
		if (LocalTime.now().isBefore(verfuegbar)){
			int differenz = (int)MINUTES.between(LocalTime.now(),verfuegbar);
			auswahlklasse.WarnungBenachrichtigung("Spieler nicht verfügbar",this.toString()+" hat noch "+differenz+ " Minuten Pause");
			return false;
		}
		for (int i=0;i<auswahlklasse.getAktuelleTurnierAuswahl().getFelder().size();i++){
			Feld feld = auswahlklasse.getAktuelleTurnierAuswahl().getFelder().get(i);
			if(feld.getAktivesSpiel()!=null){
				Spiel spiel = feld.getAktivesSpiel();
				if (spiel.contains(this)){
					String feldnr = spiel.getFeldNr();
					auswahlklasse.WarnungBenachrichtigung("Spieler nicht verfügbar",this.toString()+" spiel aktuell auf "+feldnr);
					return false;
				}
			}
		}
		return true;
	}

	public int getRLPanzeigen()
	{
		int rlp=-0;
		int index=-1;

		if(this!=null) {
			if (auswahlklasse.getAktuelleSpielklassenAuswahl().getDisziplin().contains("doppel")) {
				index = 1;
			}
			if (auswahlklasse.getAktuelleSpielklassenAuswahl().getDisziplin().contains("einzel")) {
				index = 0;
			}
			if (auswahlklasse.getAktuelleSpielklassenAuswahl().getDisziplin().contains("Mix")) {
				index = 2;
			}
			rlp=rPunkte[index];

		}
		return rlp;


	}
	public int getNationalitaet() {
		return Nationalitaet;
	}

	public LocalDate getGDatum() {
		return gDatum;
	}

	public int[] getrPunkte() {
		return rPunkte;
	}

	public boolean getGeschlecht() {
		return geschlecht;
	}
	public String getSGeschlecht() {
		if(geschlecht){
			return "m";
		}
		else {
			return "w";
		}
	}
	public ImageView getIGeschlecht() {
		if(geschlecht){
			Image imgmale = new Image("sample/Images/icon/user_male.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else {
			Image imgfemale = new Image("sample/Images/icon/user_female.png",24,24,true,true);
			ImageView imageView2 = new ImageView(imgfemale);
			return imageView2;
		}
	}
	public ImageView getINationalitaet() {
		if(Nationalitaet==0){
			Image imgmale = new Image("sample/Images/Flaggen/deutschland.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==1){
			Image imgmale = new Image("sample/Images/Flaggen/frankreich.jpg",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==2){
			Image imgmale = new Image("sample/Images/Flaggen/daenemark.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==3){
			Image imgmale = new Image("sample/Images/Flaggen/luxemburg.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==4){
			Image imgmale = new Image("sample/Images/Flaggen/niederlande.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==5){
			Image imgmale = new Image("sample/Images/Flaggen/schweiz.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==6){
			Image imgmale = new Image("sample/Images/Flaggen/spanien.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else if(Nationalitaet==7){
			Image imgmale = new Image("sample/Images/Flaggen/finnland.png",24,24,true,true);
			ImageView imageView = new ImageView(imgmale);
			return imageView;
		}
		else {
			Image imgfemale = new Image("sample/Images/Flaggen/keinLand.png",24,24,true,true);
			ImageView imageView2 = new ImageView(imgfemale);
			return imageView2;
		}
	}
	public Verein getVerein() {
		return verein;
	}



	public LocalTime getVerfuegbar() {
		return verfuegbar;
	}

	public int getMattenSpiele() {
		return mattenSpiele;
	}

	public String getExtSpielerID() {
		return extSpielerID;
	}

	public Spiel getAktuellesSpiel() {
		return aktuellesSpiel;
	}

	public String getVName() {
		return vName;
	}

	public void setGeschlecht(boolean geschlecht) {
		this.geschlecht = geschlecht;
		//spielerDAO.update(this);
	}

	public String getNName() {
		return nName;
	}


	public void meldeFormularimportieren() {
		throw new UnsupportedOperationException();
	}

	public int getSpielerID() {
		return spielerID;
	}
}