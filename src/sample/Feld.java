package sample;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.time.LocalTime;

public class Feld {
	private FeldDAO feldDAO = new FeldDAOimpl();
	private int feldID;
	private Spiel inVorbereitung;
	private Spiel aktivesSpiel;
	private Turnier turnier;
	private int feldnummer;
	private ImageView imageView;
	private StackPane feldImageStackPane;
	private Tooltip tooltip;

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
		Image image = new Image("/sample/images/Badmintonfeld.jpg");
		this.imageView.setImage(image);
		this.aktivesSpiel = this.inVorbereitung;
		this.inVorbereitung = null;
		tooltip.setText("freies Feld");
	}

	public void setImage(Image image) {
		this.imageView.setImage(image);
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public StackPane getFeldImageStackPane() {
		return feldImageStackPane;
	}

	public void setFeldImageStackPane(StackPane feldImageStackPane) {
		this.feldImageStackPane = feldImageStackPane;
	}

	public void setInVorbereitung(Spiel inVorbereitung) {
		this.inVorbereitung = inVorbereitung;
	}

	public void setAktivesSpiel(Spiel aktivesSpiel) {
		this.aktivesSpiel = aktivesSpiel;
		if(this.imageView!=null) {
			Image image = new Image("/sample/images/BadmintonfeldBesetzt.jpg");
			this.imageView.setImage(image);
			String spiel = "Spiel: "+ aktivesSpiel.getHeimStringKomplett()+" vs. "+aktivesSpiel.getGastStringKomplett();
			LocalTime aufrufZeit = aktivesSpiel.getAufrufZeit();
			String aufrufzeit = "Aufrufzeit: "+ String.format("%02d:%02d", aufrufZeit.getHour(), aufrufZeit.getMinute());
			String spielklasse = "Spielklasse: " + aktivesSpiel.getSpielklasseString();
			tooltip.setText(spiel + "\n" + aufrufzeit +"\n"+spielklasse);
		}
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
}