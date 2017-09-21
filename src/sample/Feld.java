package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

public class Feld {
	private FeldDAO feldDAO = new FeldDAOimpl();
	private int feldID;
	private Spiel inVorbereitung;
	private Spiel aktivesSpiel;
	private Turnier turnier;
	private int feldnummer;

	public Feld(Turnier turnier) {
		this.turnier = turnier;
		feldDAO.createFeld(this); //feldDAO ruft setFeldID() auf!!
	}

	public void setFeldID(int feldID) {
		this.feldID = feldID;
		this.turnier.getFelder().add(this);
		this.feldnummer = this.turnier.getFelder().indexOf(this)+1;
	}

	public Feld(int feldID, Spiel inVorbereitung, Spiel aktivesSpiel, Turnier turnier) {		//Constructor zum einlesen
		this.feldID = feldID;
		this.inVorbereitung = inVorbereitung;
		this.aktivesSpiel = aktivesSpiel;
		this.turnier = turnier;
	}

	public String toString(){
		return "Feld "+feldnummer;
	}


	public void setFeldnummer(int feldnummer) {
		this.feldnummer = feldnummer;
	}

	public int getFeldID() {
		return feldID;
	}

	public Spiel getInVorbereitung() {
		return inVorbereitung;
	}

	public Spiel getAktivesSpiel() {
		return aktivesSpiel;
	}

	public void spielBeenden(){
		this.aktivesSpiel = this.inVorbereitung;
		this.inVorbereitung = null;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public void setInVorbereitung(Spiel inVorbereitung) {
		this.inVorbereitung = inVorbereitung;
	}

	public void setAktivesSpiel(Spiel aktivesSpiel) {
		this.aktivesSpiel = aktivesSpiel;
	}
}