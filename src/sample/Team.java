package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.DAO.*;
import sample.Spielsysteme.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class Team {
    private TeamDAO teamDAO = new TeamDAOimpl();
    private int teamid;
    private int platzierung;
    private Spieler spielerEins;
    private Spieler spielerZwei;
    private Spielklasse spielklasse;
    private int setzplatznummer=42;
    private boolean einzel = true;
    private int gewonneneSpiele;
    private int gewonneneSaetze;
    private int verloreneSaetze;
    private int gewonnnenePunkte;
    private int verlorenePunkte;
    private boolean freilos = false;
    private List<Team> bisherigeGegner = new ArrayList<Team>();
    private int setzplatz;
    private ArrayList<Spiel> gespielteSpiele = new ArrayList<>();




    public Team(Spielklasse spielklasse) {
        this.spielklasse = spielklasse;
    }

    public Team(Spieler spielerEins, Spieler spielerZwei, Spielklasse spielklasse) {
        this.spielerEins = spielerEins;
        this.spielerZwei = spielerZwei;
        this.spielklasse = spielklasse;
        this.einzel = false;
        teamDAO.create(this);
    }

    public Team(Spieler spielerEins, Spielklasse spielklasse) {
        this.spielerEins = spielerEins;   //teamID
        this.einzel = true;
        this.spielklasse = spielklasse;
        teamDAO.create(this);
    }
    public Team(Spieler spielerEins, Spielklasse spielklasse, Boolean b) {
        this.spielerEins = spielerEins;   //teamID
        this.einzel = true;
        this.spielklasse = spielklasse;
        //teamDAO.update(this);

    }
    public void addSpieler(Spieler spieler){
        this.freilos=false;
        if (spielerEins==null){
            this.spielerEins = spieler;
        }
        else{
            this.spielerZwei = spieler;
            this.einzel=false;
        }

    }

    public void setTeamid(int teamid){
        this.teamid = teamid;
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }
    public void setTeamid(int teamid, Spielklasse sp){
        this.teamid = teamid;
        this.spielklasse=sp;
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }
    public Team(int teamid, Spielklasse spielklasse) { //nur für bestehendes Turnier einlesen
        this.teamid = teamid;
        this.spielklasse = spielklasse;
        this.freilos=true;
    }

    public Team(String freilos, Spielklasse spielklasse) {
        this.spielklasse = spielklasse;
        this.freilos = true;
        teamid = getSpielklasse().getTurnier().getTeams().size()+1;
        teamDAO.createFreilos(this);
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }

    public Team(String freilos, Spielsystem spielsystem) {
        this.spielklasse = spielsystem.getSpielklasse();
        this.freilos = true;
        teamid = getSpielklasse().getTurnier().getTeams().size()+1;
        teamDAO.createFreilos(this);
        this.spielklasse.getTurnier().getTeams().put(teamid,this);
    }

    public void setSetzplatz(int setzplatz) {
        this.setzplatz = setzplatz;
    }

    public boolean istImTeam(Spieler spieler){
        if (spielerEins==spieler){
            return true;
        }
        if(spielerZwei==spieler){
            return true;
        }
        return false;
    }

    public boolean isFreilos() {
        return freilos;
    }

    public boolean isEinzel() {
        return einzel;
    }

    public Spielklasse getSpielklasse() {
        return spielklasse;
    }

    public Spieler getSpielerEins_MIX() {

        if(spielklasse.toString().toUpperCase().contains("MIX"))
        {
            if(spielerEins.getGeschlecht())
            {
                return spielerEins;
            }
            if(spielerZwei!=null)
            {
                if(spielerZwei.getGeschlecht())
                {
                    return spielerZwei;
                }}
        }
        return spielerEins;
    }

    public int getRLPanzeigen()
    {
        int rlp=-0;
        int index=-1;

        if(this!=null) {
            if (spielklasse.getDisziplin().contains("doppel")) {
                index = 1;
            }
            if (spielklasse.getDisziplin().contains("einzel")) {
                index = 0;
            }
            if (spielklasse.getDisziplin().contains("Mix")) {
                index = 2;
            }

            if (spielklasse.isEinzel()) {
                if(spielerEins!=null) {
                    if (spielerEins.getrPunkte() != null) {
                        rlp = getSpielerEins().getrPunkte()[index];
                    }}

            } else {
                if(spielerEins!=null) {
                    if (spielerEins.getrPunkte() != null) {
                        rlp = getSpielerEins().getrPunkte()[index];
                    }
                }
                if(spielerZwei!=null) {
                    if (spielerEins.getrPunkte() != null) {
                        rlp += getSpielerZwei().getrPunkte()[index];
                    }}

            }
        }
        return rlp;


    }

    public String getSetzplatzString()
    {
        if(auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzplatzanzeigen(spielerEins,spielerZwei)!="")
        {
            setzplatznummer= Integer.parseInt(auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzplatzanzeigen(spielerEins,spielerZwei));
            return String.valueOf(setzplatznummer);
        }
        else
        {
            return "-1";
        }
    }
    public String getSetzplatzString2()
    {

        if(setzplatz==0)
        {
            return "";
        }
        else
        {
            return String.valueOf(setzplatz);
        }

    }

    public int getSetzplatz() {

/* wenn 0 -->dann spielklasse_setzplatz
  if(setzplatz==0)
        {
            if(auswahlklasse.getAktuelleSpielklassenAuswahl()!=null) {
                if (auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().get(this) != null) {
                    if (auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().get(this) > 0) {
                        setzplatz = auswahlklasse.getAktuelleSpielklassenAuswahl().getSetzlistedict().get(this);
                    }
                }
            }
        }*/
        return setzplatz;
    }

    public ArrayList<Spiel> getGespielteSpiele() {
        return gespielteSpiele;
    }

    public Spieler getSpielerEins() {
        return spielerEins;
    }

    public void setSpielerEins(Spieler spielerEins) {
        this.spielerEins = spielerEins;
    }

    public Spieler getSpielerZwei() {
        return spielerZwei;
    }

    public void setSpielerZwei(Spieler spielerZwei) {
        this.spielerZwei = spielerZwei;
    }

    public Spieler getSpielerZwei_MIX() {

        if(spielklasse.toString().toUpperCase().contains("MIX")&&spielerZwei!=null)
        {

            if(!spielerEins.getGeschlecht()&&!getSpielerEins_MIX().equals(spielerEins))
            {
                return spielerEins;
            }


        }
        return spielerZwei;
    }

    public void addBisherigenGegner(Team team){
        bisherigeGegner.add(team);
    }

    public TeamDAO getTeamDAO() {
        return teamDAO;
    }

    public List<Team> getBisherigeGegner() {
        return bisherigeGegner;
    }
    public boolean warNochKeinGegner(Team team){
        for(int i=0; i<bisherigeGegner.size();i++){
            if (bisherigeGegner.get(i)==team){
                return false;
            }
        }
        return true;
    }

    public Team()
    {

    }
    public void addGewonnenesSpiel() {
        this.gewonneneSpiele ++;
        //teamDAO.update(this);
    }

    public void addGewonnenenSatz() {
        this.gewonneneSaetze ++;
        //teamDAO.update(this);
    }


    public void addVerlorenenSatz() {
        this.verloreneSaetze ++;
        //teamDAO.update(this);
    }

    public void addGespieltePunkte(int gewonnnenePunkte, int verlorenePunkte) {
        this.gewonnnenePunkte = this.gewonnnenePunkte + gewonnnenePunkte;
        this.verlorenePunkte = this.verlorenePunkte + verlorenePunkte;
        //teamDAO.update(this);
    }

    public int getPlatzierung() {
        return platzierung;
    }

    public void setPlatzierung(int platzierung) {
        this.platzierung = platzierung;
    }

    public int getTeamid() {
        return teamid;
    }

    public int getGewonneneSpiele() {
        return gewonneneSpiele;
    }

    public int getGewonneneSaetze() {
        return gewonneneSaetze;
    }

    public int getVerloreneSaetze() {
        return verloreneSaetze;
    }

    public int getGewonnnenePunkte() {
        return gewonnnenePunkte;
    }

    public int getVerlorenePunkte() {
        return verlorenePunkte;
    }

    public String toStringKomplett(){

        if(this.freilos==true){
            return "Freilos";
        }
        else {
            if(this.einzel==true){
                if(this.spielerEins!=null)
                {
                    return this.spielerEins.toString();
                }
                else
                {
                    return null;
                }
            }
            else if(this.einzel == false){
                return this.spielerEins.getVName()+" "+ this.spielerEins.getNName()+" / "+this.spielerZwei.getVName()+" "+ this.spielerZwei.getNName();
            }
        }
        return "Fehler";
    }
    public String toString(){

        if(this.freilos==true){
            return "Freilos";
        }
        else {
            if(this.einzel==true){
                if(this.spielerEins!=null)
                {
                    return this.spielerEins.toString();
                }
                else
                {
                    return null;
                }
            }
            else if(this.einzel == false){
                return this.spielerEins.getNName()+" "+this.spielerEins.getVName().charAt(0)+"." + " / " + this.spielerZwei.getNName()+" "+this.spielerZwei.getVName().charAt(0)+".";
            }
        }
        return "Fehler";
    }
    public List<Team> getVerbleibendeGegner(ArrayList<Team> teams){
        List<Team> verbleibendeGegner = new ArrayList<>();
        for (int j=0;j<teams.size();j++){
            verbleibendeGegner.add(teams.get(j));
        }
        for(int i=0; i<bisherigeGegner.size();i++){
            verbleibendeGegner.remove(bisherigeGegner.get(i));
        }
        verbleibendeGegner.remove(this);
        return verbleibendeGegner;
    }

    public int compareTo(Team team){
        int satzdifferenzThis = this.gewonneneSaetze-this.verloreneSaetze;
        int punktdifferenzThis = this.gewonnnenePunkte-this.verlorenePunkte;
        int satzdifferenzTeam = team.getGewonneneSaetze()-team.getVerloreneSaetze();
        int punktdifferenzTeam = team.getGewonnnenePunkte()-team.getVerlorenePunkte();
        if (this.gewonneneSpiele==team.getGewonneneSpiele()){
            if(spielklasse.getSpielsystem() != null && spielklasse.getSpielsystem().getSpielSystemArt()==5){
                if(team.getBHZ()==this.getBHZ()){
                    if (satzdifferenzTeam == satzdifferenzThis) {
                        if (punktdifferenzTeam == punktdifferenzThis) {
                            return 0;
                        } else {
                            return punktdifferenzTeam - punktdifferenzThis;
                        }
                    } else {
                        return satzdifferenzTeam - satzdifferenzThis;
                    }
                }
                else{
                    return team.getBHZ()-this.getBHZ();
                }
            }
            else {
                if (satzdifferenzTeam == satzdifferenzThis) {
                    if (punktdifferenzTeam == punktdifferenzThis) {
                        return 0;
                    } else {
                        return punktdifferenzTeam - punktdifferenzThis;
                    }
                } else {
                    return satzdifferenzTeam - satzdifferenzThis;
                }
            }


        }
        else{
            return team.getGewonneneSpiele()-this.gewonneneSpiele;
        }
    }


    public int getBHZ() {
        int BHZ =0;
        for (int i=0;i< bisherigeGegner.size();i++){
            BHZ+=bisherigeGegner.get(i).getGewonneneSpiele();
        }
        return BHZ;
    }
    public boolean contains(Spieler spieler){
        if(spielerEins==spieler){
            return true;
        }
        if(spielerZwei==spieler){
            return true;
        }
        return false;
    }

    public boolean isVerfuegbar() {
        if(spielerEins==null){
            return false;
        }
        else{
            if(!spielerEins.isVerfuegbar()){
                return false;
            }
        }
        if(spielerZwei==null){
            return false;
        }
        else{
            if(!spielerZwei.isVerfuegbar()){
                return false;
            }
        }
        return true;
    }
}
