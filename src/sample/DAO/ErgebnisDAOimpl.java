package sample.DAO;

import sample.Feld;
import sample.Spiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Florian-PC on 26.07.2017.
 */
public class ErgebnisDAOimpl implements ErgebnisDAO {
    @Override
    public boolean create(Spiel ergebnis) {
        String sql = "INSERT INTO Spiel_Satzergebnis( " +
                "SpielID, " +
                "HeimPunkte, " +
                "Gastpunkte) " +
                "VALUES(?,?,?)";
        for(int i=1; i< ergebnis.getErgebnis().getErgebnisArray().length/2;i++){
            sql += ", (?,?,?)";
        }
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1,ergebnis.getSpielID());
            smt.setInt(2,ergebnis.getErgebnis().getErgebnisArray()[0]);
            smt.setInt(3,ergebnis.getErgebnis().getErgebnisArray()[1]);
            for(int j=1; j< ergebnis.getErgebnis().getErgebnisArray().length/2;j++){
                smt.setInt(j*3+1,ergebnis.getSpielID());
                smt.setInt(j*3+2,ergebnis.getErgebnis().getErgebnisArray()[j*2]);
                smt.setInt(j*3+3,ergebnis.getErgebnis().getErgebnisArray()[j*2+1]);
            }
            smt.executeUpdate();
            smt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ergebnis Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean delete(Spiel ergebnis) {
        String sql = "Delete from Spiel_Satzergebnis Where spielid = ?";
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, ergebnis.getSpielID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ergebnis Loeschen Klappt nicht");
        }
        return false;
    }
}
