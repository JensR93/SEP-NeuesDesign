package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.sql.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public class SpielDAOimpl implements SpielDAO {
    @Override
    public boolean create(Spiel spiel) {
        String idAbfrage = "Select AUTO_INCREMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'turnierverwaltung_neu' " +
                "AND TABLE_NAME = 'Spiel'";

        String sql = "INSERT INTO spiel("
                + "Heim,"
                + "Gast, "
                + "Schiedsrichter, "
                + "Status, "
                + "SpielklasseID) "
                + "VALUES(?,?,?,?,?)";

        String sqlzwei = "INSERT INTO spielklasse_spielid("
                + "SpielID,"
                + "SpielklasseID, "
                + "SpielklasseSpielID) "
                + "VALUES(?,?,?)";


        try {
            Connection con = SQLConnection.getCon();
            Statement smtID = con.createStatement();
            ResultSet count = smtID.executeQuery(idAbfrage);
            count.next();
            int spielID = count.getInt(1);
            spiel.setSpielID(spielID);
            smtID.close();
            PreparedStatement smt = con.prepareStatement(sql);
            if(spiel.getHeim()!=null) {
                smt.setInt(1, spiel.getHeim().getTeamid());
            }
            else{
                smt.setNull(1, Types.INTEGER);
            }
            if(spiel.getGast()!=null){
                smt.setInt(2, spiel.getGast().getTeamid());
            }
            else{
                smt.setNull(2, Types.INTEGER);
            }
            if (spiel.getSchiedsrichter()!=null){
                smt.setInt(3, spiel.getSchiedsrichter().getSpielerID());
            }
            else{
                smt.setNull(3,Types.INTEGER);
            }
            smt.setInt(4,spiel.getStatus());
            smt.setInt(5, spiel.getSpielsystem().getSpielklasse().getSpielklasseID());


            smt.executeUpdate();
            smt.close();
            PreparedStatement smtzwei = con.prepareStatement(sqlzwei);
            smtzwei.setInt(1, spiel.getSpielID());
            smtzwei.setInt(2, spiel.getSpielsystem().getSpielklasse().getSpielklasseID());
            smtzwei.setInt(3, spiel.getSystemSpielID());
            smtzwei.executeUpdate();
            smtzwei.close();
            //con.close();



            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Spiel spiel) {
        String sql1 = "DELETE FROM spielklasse_spielid WHERE spielID = ?";
        String sql2 = "DELETE FROM spiel_satzergebnis WHERE spielID = ?";
        String sql3 = "DELETE FROM spiel WHERE SpielID= ?";
        try {
            Connection con =  SQLConnection.getCon();
            PreparedStatement smt1 = con.prepareStatement(sql1);
            smt1.setInt(1, spiel.getSpielID());
            PreparedStatement smt2 = con.prepareStatement(sql2);
            smt2.setInt(1, spiel.getSpielID());
            PreparedStatement smt3 = con.prepareStatement(sql3);
            smt3.setInt(1, spiel.getSpielID());
            smt1.executeUpdate();
            smt1.close();
            smt2.executeUpdate();
            smt2.close();
            smt3.executeUpdate();
            smt3.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spiel spiel) {
        String sql = "UPDATE spiel "
                + "SET AufrufZeit = ?, "
                + "Schiedsrichter = ?, "
                + "Feld = ?, "
                + "Status = ?, "
                + "Heim = ?, "
                + "Gast = ?, "
                + "SiegerID = ?, "
                + "SpielklasseID = ?, "
                + "ZeitplanNummer = ?, "
                + "RundenZeitplanNummer = ? "
                + "WHERE SpielID = ? "
                ;
        try {
            Connection con =  SQLConnection.getCon();
            //System.out.println(spiel.getAufrufZeit()+"-------");
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1, spiel.getAufrufZeit());
            if (spiel.getSchiedsrichter()!=null){
                smt.setInt(2, spiel.getSchiedsrichter().getSpielerID());
            }
            else{
                smt.setNull(2,Types.INTEGER);
            }
            if (spiel.getFeld()!=null){
                smt.setInt(3, spiel.getFeld().getFeldID());
            }
            else{
                smt.setNull(3,Types.INTEGER);
            }
            smt.setInt(4, spiel.getStatus());
            if (spiel.getHeim()!=null){
                smt.setInt(5, spiel.getHeim().getTeamid());
            }
            else{
                smt.setNull(5,Types.INTEGER);
            }
            if (spiel.getGast()!=null){
                smt.setInt(6, spiel.getGast().getTeamid());
            }
            else{
                smt.setNull(6,Types.INTEGER);
            }
            if (spiel.getSieger()!=null){
                smt.setInt(7, spiel.getSieger().getTeamid());
            }
            else{
                smt.setNull(7,Types.INTEGER);
            }
            smt.setInt(8, spiel.getSpielsystem().getSpielklasse().getSpielklasseID());
            smt.setInt(9,spiel.getZeitplanNummer());
            smt.setInt(10,spiel.getRundenZeitplanNummer());

            smt.setInt(11, spiel.getSpielID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Update Klappt nicht");
        }


        return false;
    }
}
