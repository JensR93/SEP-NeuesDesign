package sample;

import sample.DAO.ErgebnisDAO;
import sample.DAO.ErgebnisDAOimpl;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class Ergebnis {
    private ErgebnisDAO ergebnisDAO = new ErgebnisDAOimpl();
    private int ergebnisID;
    private int[] heim;
    private int[] gast;
    private boolean gueltig=false;

    public Ergebnis(int satz1heim, int satz1gast, int satz2heim, int satz2gast) {
        heim=new int[2];
        gast=new int[2];
        this.heim[0] = satz1heim;
        this.gast[0] = satz1gast;
        this.heim[1] = satz2heim;
        this.gast[1] = satz2gast;

    }

    public Ergebnis(int satz1heim, int satz1gast, int satz2heim, int satz2gast, int satz3heim, int satz3gast) {
        heim=new int[3];
        gast=new int[3];
        this.heim[0] = satz1heim;
        this.gast[0] = satz1gast;
        this.heim[1] = satz2heim;
        this.gast[1] = satz2gast;
        this.heim[2] = satz3heim;
        this.gast[2] = satz3gast;

    }

    public Ergebnis(int satz1heim, int satz1gast, int satz2heim, int satz2gast, int satz3heim, int satz3gast, int satz4heim, int satz4gast) {
        heim=new int[4];
        gast=new int[4];
        this.heim[0] = satz1heim;
        this.gast[0] = satz1gast;
        this.heim[1] = satz2heim;
        this.gast[1] = satz2gast;
        this.heim[2] = satz3heim;
        this.gast[2] = satz3gast;
        this.heim[3] = satz4heim;
        this.gast[3] = satz4gast;

    }

    public Ergebnis(int satz1heim, int satz1gast, int satz2heim, int satz2gast, int satz3heim, int satz3gast, int satz4heim, int satz4gast, int satz5heim, int satz5gast) {
        heim=new int[5];
        gast=new int[5];
        this.heim[0] = satz1heim;
        this.gast[0] = satz1gast;
        this.heim[1] = satz2heim;
        this.gast[1] = satz2gast;
        this.heim[2] = satz3heim;
        this.gast[2] = satz3gast;
        this.heim[3] = satz4heim;
        this.gast[3] = satz4gast;
        this.heim[4] = satz5heim;
        this.gast[4] = satz5gast;

    }

    public int getErgebnisID() {
        return ergebnisID;
    }

    public void setErgebnisID(int ergebnisID) {
        this.ergebnisID = ergebnisID;
    }

    public String toString(){
        String ergebnis = heim[0]+"-"+gast[0];
        for (int i=1; i<heim.length;i++){
            ergebnis=ergebnis+", "+heim[i]+"-"+gast[i];
        }
        return ergebnis;
    }

    public Team getSieger(Spiel spiel){
        if(heim != null){
            if (heim[heim.length-1]>gast[heim.length-1]) {
                return spiel.getHeim();
            }
            else{
                return spiel.getGast();
            }
        }
        else return null;
    }

    public int[] getErgebnisArray(){
        int[] ergebnis = new int[heim.length*2];
        for(int i=0; i<heim.length;i++){
            ergebnis[i*2]=heim[i];
            ergebnis[i*2+1]=gast[i];
        }
        return ergebnis;
    }
    public String toStringUmgedreht(){
        String ergebnis = gast[0]+"-"+heim[0];
        for (int i=1; i<gast.length;i++){
            ergebnis=ergebnis+", "+gast[i]+"-"+heim[i];
        }
        return ergebnis;
    }


    public ErgebnisDAO getErgebnisDAO() {
        return ergebnisDAO;
    }

    public int[] getHeim() {
        return heim;
    }

    public int[] getGast() {
        return gast;
    }
}
