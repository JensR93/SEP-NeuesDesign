package sample.Spielsysteme;

import sample.*;
import sample.DAO.*;
import sample.Enums.*;

public class SpielTree {

    private int spielID;
    private SpielTree left;
    private SpielTree right;
    private SpielTree parent=null;
    private int setzplatzHeim;
    private int setzplatzGast;
    private Spiel spiel;

    public SpielTree(int spielID, int setzplatzHeim, int setzplatzGast) {
        this.spielID = spielID;
        this.left = null;
        this.right = null;
        this.setzplatzHeim = setzplatzHeim;
        this.setzplatzGast = setzplatzGast;
    }

    public int getSpielID() {
        return spielID;
    }

    public int getSetzplatzHeim() {
        return setzplatzHeim;
    }

    public int getSetzplatzGast() {
        return setzplatzGast;
    }

    public SpielTree getLeft() {
        return left;
    }

    public SpielTree getRight() {
        return right;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public SpielTree getParent() {
        return parent;
    }

    public void setParent(SpielTree parent) {
        this.parent = parent;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public SpielTree getSpielTree(int suchid, SpielTree spielTree){
        //System.out.println( "Suche nach "+this.spielID );
        if (spielTree != null){


        if(spielTree.getSpielID() == suchid)
        {
            return spielTree;
        }
        else
        {
            SpielTree foundSpiel = getSpielTree(suchid, spielTree.left);
            if(foundSpiel==null){
                foundSpiel = getSpielTree(suchid, spielTree.right);
            }
            return foundSpiel;
        }
        }
        else{
            return null;
        }
    }


    public boolean addLeft(int spielID, int setzplatzHeim, int setzplatzGast) {
        if(this.left == null){
            this.left = new SpielTree(spielID, setzplatzHeim, setzplatzGast);
            this.left.setParent(this);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean addRight(int spielID , int setzplatzHeim, int setzplatzGast) {
        if(this.right == null){
            this.right = new SpielTree(spielID, setzplatzHeim, setzplatzGast);
            this.right.setParent(this);
            return true;
        }
        else
        {
            return false;
        }
    }

}