package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 23.07.2017.
 */
public class SetzlisteDAOimpl implements SetzlisteDAO {
    @Override
    public boolean create(int setzplatz, Team team,  Spielklasse spielklasse) {
        try {
            Connection con = SQLConnection.getCon();
            String sql = "INSERT INTO spielklasse_setzliste ("
                    + "setzplatz, "
                    + "spielklasseID, "
                    + "TeamID) "
                    + "VALUES (?,?,?)";

            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, setzplatz);
            smt.setInt(2, spielklasse.getSpielklasseID());
            smt.setInt(3, team.getTeamid());
            smt.executeUpdate();
            smt.close();
            System.out.println("Setzliste Einfügen klappt");

            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Einfügen Klappt nicht");
        }
        return false;
    }
    @Override
    public boolean deleteSetzplatz(int spielklasseid,int teamid) {
        String sql = "Delete From spielklasse_setzliste Where SpielklasseID= ? AND TeamID=?";
        try{
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, spielklasseid);
            smt.setInt(2,teamid);
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Loeschen Klappt nicht");
        }

        return false;
    }
    @Override
    public boolean delete(int spielklasseid) {
        String sql = "Delete From spielklasse_setzliste Where SpielklasseID= ?";
        try{
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, spielklasseid);
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Loeschen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean update(int setzplatz, Team team,  Spielklasse spielklasse) {
        String sql = "UPDATE spielklasse_setzliste "
                + "SET teamid = ? "
                + "WHERE spielklasseID = ? "
                + "AND setzplatz = ?"
                ;
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, team.getTeamid());
            smt.setInt(2, spielklasse.getSpielklasseID());
            smt.setInt(3, setzplatz);
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Update Klappt nicht");
        }
        return false;
    }
}
