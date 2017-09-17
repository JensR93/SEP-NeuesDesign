package sample.Spielsysteme;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import sample.*;
import sample.DAO.*;
import sample.Enums.*;

public class KOmitTrostrunde extends Spielsystem{
	private int tRbisPlatz;
	private int ausspielenBisPlatz;
	private ArrayList<Team> setzliste;


    public KOmitTrostrunde(ArrayList<Team> setzliste, Spielklasse spielklasse, ArrayList<Spiel> spiele, Dictionary<Integer,Ergebnis> ergebnisse) {

		this.setSpielklasse(spielklasse);			//Constructor nur für Einlesen aus der Datenbank
    }

	@Override
	public boolean beendeMatch(Spiel spiel) {
		return false;
	}

	@Override
	public boolean beendeMatch(Spiel spiel, String einlesen) {
		return false;
	}
	public ArrayList<Team> getSetzliste(){
		return setzliste;
	}
}