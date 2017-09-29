package sample.DAO;

import sample.Team;

import java.sql.*;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class TeamDAOimpl implements TeamDAO {
    @Override
    public boolean create(Team team) {
        String idAbfrage = "Select AUTO_INCREMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'turnierverwaltung_neu' " +
                "AND TABLE_NAME = 'Team'";

        String sql = "INSERT INTO team("
                + "SpielklasseID) "
                + "VALUES(?)";
        String sqlSpielerEins = "INSERT INTO Team_Spieler("
                + "TeamID, "
                + "SpielerID) "
                + "VALUES(?,?)"
                ;
        String sqlSpielerZwei = "INSERT INTO Team_Spieler("
                + "TeamID, "
                + "SpielerID) "
                + "VALUES(?,?)"
                ;
        try {
            Connection con = SQLConnection.getCon();
            Statement smtID = con.createStatement();
            ResultSet count = smtID.executeQuery(idAbfrage);
            count.next();
            int teamID = count.getInt("AUTO_INCREMENT");
            System.out.println(teamID);
            team.setTeamid(count.getInt("AUTO_INCREMENT"),team.getSpielklasse());
            smtID.close();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, team.getSpielklasse().getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            if(team.getSpielerEins()!=null) {
                PreparedStatement smtSpielerEins = con.prepareStatement(sqlSpielerEins);
                smtSpielerEins.setInt(1, team.getTeamid());
                smtSpielerEins.setInt(2, team.getSpielerEins().getSpielerID());
                smtSpielerEins.executeUpdate();
                smtSpielerEins.close();
            }
            if(team.getSpielerZwei()!=null) {
                PreparedStatement smtSpielerZwei = con.prepareStatement(sqlSpielerZwei);
                smtSpielerZwei.setInt(1, team.getTeamid());
                smtSpielerZwei.setInt(2, team.getSpielerZwei().getSpielerID());
                smtSpielerZwei.executeUpdate();
                smtSpielerZwei.close();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean createFreilos(Team team) {
        String idAbfrage = "Select AUTO_INCREMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'turnierverwaltung_neu' " +
                "AND TABLE_NAME = 'Team'";

        String sql = "INSERT INTO team("
                + "SpielklasseID, TeamID) "
                + "VALUES(?,?)";
        try{
            Connection con = SQLConnection.getCon();
            Statement smtID = con.createStatement();
            ResultSet count = smtID.executeQuery(idAbfrage);
            count.next();
            int teamID = count.getInt("AUTO_INCREMENT");
            System.out.println(teamID);
            team.setTeamid(teamID);
            smtID.close();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, team.getSpielklasse().getSpielklasseID());
            smt.setInt(2, team.getTeamid());
            smt.executeUpdate();
            smt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Freilos Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Team team) {
        String sql = "UPDATE team "
                + "SET SpielklasseID = ?, "
                + "GewonneneSpiele = ?, "
                + "GewonneneSaetze = ?, "
                + "VerloreneSaetze = ?, "
                + "GewonnnenePunkte = ?, "
                + "VerlorenePunkte = ?, "
                + "FesterSetzplatz = ? "
                + "WHERE TeamID = ? "
                ;
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, team.getSpielklasse().getSpielklasseID());
            smt.setInt(2, team.getGewonneneSpiele());
            smt.setInt(3, team.getGewonneneSaetze());
            smt.setInt(4, team.getVerloreneSaetze());
            smt.setInt(5, team.getGewonnnenePunkte());
            smt.setInt(6, team.getVerlorenePunkte());
            smt.setInt(7, team.getSetzplatz());
            smt.setInt(8, team.getTeamid());

            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Update Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean addSpieler(Team team, boolean ersterSpieler) {

        String sqlSpielerEins = "INSERT INTO Team_Spieler("
                + "TeamID, "
                + "SpielerID) "
                + "VALUES(?,?)"
                ;
        String sqlSpielerZwei = "INSERT INTO Team_Spieler("
                + "TeamID, "
                + "SpielerID) "
                + "VALUES(?,?)"
                ;
        try {
            Connection con = SQLConnection.getCon();
            if(ersterSpieler) {
                PreparedStatement smtSpielerEins = con.prepareStatement(sqlSpielerEins);
                smtSpielerEins.setInt(1, team.getTeamid());
                smtSpielerEins.setInt(2, team.getSpielerEins().getSpielerID());
                smtSpielerEins.executeUpdate();
                smtSpielerEins.close();
            }
            else{
                PreparedStatement smtSpielerZwei = con.prepareStatement(sqlSpielerZwei);
                smtSpielerZwei.setInt(1, team.getTeamid());
                smtSpielerZwei.setInt(2, team.getSpielerZwei().getSpielerID());
                smtSpielerZwei.executeUpdate();
                smtSpielerZwei.close();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean delete(Team team) {

        deleteteam_spieler(team.getTeamid());
        String sql = "Delete From team Where teamid= ?";
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, team.getTeamid());

            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Loeschen Klappt nicht");
        }
        return false;
    }
    private boolean deleteteam_spieler(int teamid) {


        String sql = "Delete From team_spieler Where teamid= ?";
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, teamid);
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team_spieler Loeschen Klappt nicht");
        }
        return false;
    }

   /* @Override
    public List<Team> getAllTeams() {
        return null;
    }*/
}
