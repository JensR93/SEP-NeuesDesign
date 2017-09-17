package sample;

import sample.DAO.*;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sample.Spielsysteme.Spielsystem;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Spielklasse {
	private SpielklasseDAO spielklasseDAO = new SpielklasseDAOimpl();
	private int spielklasseID;
	private String disziplin;
	private String niveau;
	private ArrayList<Team> setzliste=new ArrayList<>();
	private Spielsystem spielsystem;
	private float meldeKosten = (float) 5;
	private Turnier turnier;
	private Hashtable<Integer, Spiel> spiele = new Hashtable<>();
	private boolean aktiv;
	private boolean einzel = false;
	private boolean setzliste_gesperrt= false;
	private Dictionary<Team,Integer> setzlistedict = new Hashtable<>();

	public Dictionary<Team,Integer> getSetzlistedict()
	{
		if(setzlistedict.size()<1)
		{
			//System.out.println("Leeres Dict.");

			for(int i=0;i<setzliste.size();i++)
			{
				setzlistedict.put(setzliste.get(i),i+1);
			}
		}
		//System.out.println(setzlistedict);
		return setzlistedict;
	}
/*
	public void removeSetzlistedict(int setzplatz, Team team)
	{

		setzlistedict.remove(team);
		SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
		setzlisteDAO.deleteSetzplatz(auswahlklasse.getAktuelleSpielklassenAuswahl().getSpielklasseID(),team.getTeamid());


	}
	public void addSetzlistedict(int setzplatzneu,int setzplatzalt,  Team teamalt)
	{

		if(setzlistedict.size()<1)
		{
			System.out.println("Leeres Dict.");

			for(int i=0;i<setzliste.size();i++)
			{
				setzlistedict.put(setzliste.get(i),i+1);
			}
		}
		else
		{


				System.out.println("Setzplatz wurde erhöht");
				SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
				Enumeration e = setzlistedict.keys();
				while (e.hasMoreElements()) {
					Team key = (Team) e.nextElement();


					if (setzlistedict.get(key) < setzplatzalt && setzlistedict.get(key) < setzplatzneu) {
						System.out.println("1.Übernehmen + Erhöhen");
					}
					if (setzlistedict.get(key) > setzplatzalt && setzlistedict.get(key) <= setzplatzneu) {
						System.out.println("2. Key-1 + Erhöhen");
						int platz = setzlistedict.get(key) - 1;
						setzlistedict.remove(key);

						setzlisteDAO.deleteSetzplatz(auswahlklasse.getAktuelleSpielklassenAuswahl().getSpielklasseID(),key.getTeamid());
						setzlisteDAO.create(platz,key,auswahlklasse.getAktuelleSpielklassenAuswahl());
						setzlistedict.put(key, platz);

					}
					if (setzlistedict.get(key) > setzplatzalt && setzlistedict.get(key) > setzplatzneu) {
						System.out.println("3.Übernehmen + Erhöhen");
					}
					if (setzlistedict.get(key) < setzplatzalt && setzlistedict.get(key) >= setzplatzneu) {
						System.out.println("2. Key+1 + Verringern");
						int platz = setzlistedict.get(key) + 1;
						setzlistedict.remove(key);
						setzlisteDAO.deleteSetzplatz(auswahlklasse.getAktuelleSpielklassenAuswahl().getSpielklasseID(),key.getTeamid());
						setzlisteDAO.create(platz,key,auswahlklasse.getAktuelleSpielklassenAuswahl());
						setzlistedict.put(key, platz);
					}



				}
		}





			System.out.println("test");


			setzlistedict.put(teamalt,setzplatzneu);
			SetzlisteDAO setzlisteDAO = new SetzlisteDAOimpl();
			setzlisteDAO.create(setzplatzneu,teamalt,auswahlklasse.getAktuelleSpielklassenAuswahl());


		//System.out.println(setzlistedict);

	}
	public void setSetzlistedict(Dictionary<Team,Integer> setzlistedict) {
		this.setzlistedict = setzlistedict;
	}
*/

	@Override
	public String toString() {
		//funktioniert nicht, weil freilose auch als Spiele zählen..
//		if(this.getSpiele()!=null&&this.getSpiele().size()>0)
//		{
//			return	 disziplin +
//					" - " + niveau + " - Spiele:"+this.getSpiele().size() ;
//		}
//		else
//		{
			return	 disziplin +
					" - " + niveau  ;


	}

	public Spielklasse(int spielklasseID, String disziplin, String niveau, Turnier turnier) {
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		this.turnier = turnier;
		if (disziplin.toUpperCase().contains("EINZEL")){
			einzel=true;
		}
	}
	public Spielklasse(Disziplin disziplin, Niveau niveau, Turnier turnier)
	{
		System.out.println("neue Spielklasse mit Enums1 und gesetzter SpielklasseID");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin.toString();
		this.niveau = niveau.toString();
		this.turnier = turnier;
		System.out.println("Disziplin = "+disziplin.toString());
		if(disziplin.toString().toUpperCase().contains("EINZEL"))
		{
			System.out.println("Erfolg");
			einzel=true;
		}
	}



	/*public Spielklasse(int spielklasseID, String disziplin, String niveau, int turnierid)
	{
		System.out.println("neue Spielklasse mit gesetzter SpielklasseID ohne Enum");
		this.spielklasseID=spielklasseID;
		this.disziplin = disziplin;
		this.niveau = niveau;
		Turnier turnier = new Turnier("",turnierid, LocalDate.now());
		this.turnier = t.read(turnier);
		System.out.println(this.turnier.getName());
		if (disziplin.toUpperCase().contains("EINZEL")){
			einzel=true;
		}

	}*/

	public boolean isEinzel() {
		return einzel;
	}

	public SpielklasseDAO getSpielklasseDAO() {
		return spielklasseDAO;
	}

	public Hashtable<Integer, Spiel> getSpiele() {
		return spiele;
	}

	public int getSpielklasseID() {
		return spielklasseID;
	}

	public void setSpielklasseID(int spielklasseID) {
		this.spielklasseID = spielklasseID;
	}

	public void setSpielsystem(Spielsystem spielsystem) {
		this.spielsystem = spielsystem;
	}

	public void setMeldeKosten(float meldeKosten) {
		this.meldeKosten = meldeKosten;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public Spielsystem getSpielsystem() {
		return spielsystem;
	}

	public boolean isSetzliste_gesperrt() {

		if(this.getSpiele().size()>0)
		{
			setzliste_gesperrt=true;
		}
		return setzliste_gesperrt;
	}

	public void setSetzliste_gesperrt(boolean setzliste_gesperrt) {
		this.setzliste_gesperrt = setzliste_gesperrt;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public boolean isAktiv() {
		return aktiv;
	}


	public String getDisziplin() {
		return disziplin;
	}

	public String getNiveau() {
		return niveau;
	}

	public ArrayList<Team> getSetzliste() {
		return setzliste;
	}


	public void setSetzliste(ArrayList<Team> setzliste) {
		this.setzliste = setzliste;
	}

	public float getMeldeKosten() {
		return meldeKosten;
	}

	public String getSetzplatzanzeigen(Spieler spielerEins, Spieler spielerZwei)
	{
		String setzplatznummer="";

		Enumeration e = getSetzlistedict().keys();
		while (e.hasMoreElements())
		{
			Team team1= (Team) e.nextElement();
			if(team1!=null) {
				if (spielerEins != null) {
					if (team1.toStringKomplett().toUpperCase().contains(spielerEins.toString().toUpperCase())) {

						setzplatznummer = String.valueOf(setzlistedict.get(team1));
						//System.out.println("Setzplatz gefunden!" + setzplatznummer);
						break;
					} else {
						if (spielerZwei != null) {
							if (team1.toStringKomplett().toUpperCase().contains(spielerZwei.toString().toUpperCase())) {
								setzplatznummer = String.valueOf(setzlistedict.get(team1));
								//System.out.println("Setzplatz gefunden!" + setzplatznummer);
							} else {


							}
						}

					}
				}
			}
		}
		return setzplatznummer;
	}
}