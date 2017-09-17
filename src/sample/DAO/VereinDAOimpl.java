package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.sql.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public class VereinDAOimpl implements VereinDAO {
    @Override
    public boolean create(Verein verein) {
        //String sql = "INSERT INTO verein (Name ,Verband, ExtVereinsID, VereinsID) VALUES(?,?,?,?)";
        String idAbfrage = "Select AUTO_INCREMENT " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = 'turnierverwaltung_neu' " +
                "AND TABLE_NAME = 'Verein'";

        String sql = "INSERT INTO verein("
                + "name,"
                + "Verband, "
                + "ExtVereinsID) "
                + "VALUES(?,?,?)";
        try {
            Connection con = SQLConnection.getCon();
            Statement smtID = con.createStatement();
            ResultSet count = smtID.executeQuery(idAbfrage);
            count.next();
            int vereinsid = count.getInt(1);
            smtID.close();
            verein.setVereinsID(vereinsid);
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setString(1, verein.getName());
            smt.setString(2, verein.getVerband());
            smt.setString(3, verein.getExtVereinsID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Verein verein) {
        String sql = "Delete From verein Where vereinsid= ?";
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setInt(1, verein.getVereinsID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Verein verein) {
        String sql = "UPDATE verein SET Name = ?,Verband = ?, ExtVereinsID = ? WHERE vereinsID = ?";
        ;
        try {
            Connection con = SQLConnection.getCon();
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setString(1, verein.getName());
            smt.setString(2, verein.getVerband());
            smt.setString(3, verein.getExtVereinsID());
            smt.setInt(4, verein.getVereinsID());
            smt.executeUpdate();
            smt.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Update Klappt nicht");
        }
        return false;
    }
}
