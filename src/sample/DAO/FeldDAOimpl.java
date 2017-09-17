package sample.DAO;

import sample.Feld;
import sample.Spieler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class FeldDAOimpl implements FeldDAO {
    @Override
    public boolean createFeld(Feld feld) {
        String idAbfrage = "Select AUTO_INCREMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'turnierverwaltung_neu' " +
                "AND TABLE_NAME = 'Feld'";

        String sql = "INSERT INTO feld("
                + "turnierid) "
                + "VALUES(?)";
        try {
            Connection con = SQLConnection.getCon();
            Statement smtID = con.createStatement();
            ResultSet count = smtID.executeQuery(idAbfrage);
            count.next();
            int feldID = count.getInt(1);
            feld.setFeldID(feldID);
            smtID.close();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, feld.getTurnier().getTurnierid());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean updateFeld(Feld feld) {
        String sql = "UPDATE feld "
                + "SET aktivesSpiel = ?, "
                + "InVorbereitung = ?, "
                + "turnierid = ? "
                + "WHERE FeldID = ? "
                ;
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, feld.getAktivesSpiel().getSpielID());
            smt.setInt(2, feld.getInVorbereitung().getSpielID());
            smt.setInt(3, feld.getTurnier().getTurnierid());
            smt.setInt(4, feld.getFeldID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Update Klappt nicht");
        }
        return false;
    }

  @Override
    public boolean deleteFeld(Feld feld) {
        String sql = "Delete From feld Where feldid= ?";
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, feld.getFeldID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feld Loeschen Klappt nicht");
        }
        return false;
    }
}
