package sample.DAO;

import sample.Spielklasse;
import sample.Turnier;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by jens on 05.08.2017.
 */
public interface SpielklasseDAO {
    boolean create(Spielklasse spielklasse);

    boolean delete(Spielklasse spielklasse);

    boolean stoppeSpielsystem (Spielklasse spielklasse);

    boolean update(Spielklasse spielklasse);

    //List<Spielklasse> getAllSpielklassen();

    /*    @Override
        public Spielklasse read(int spielklasseID) {
            String sql = "Select * from spielklasse Where spielklasseID=" + spielklasseID;
            Spielklasse temp = null;
            try {
                SQLConnection con = new SQLConnection();
                Connection connection = con.SQLConnection();
                Statement st = connection.createStatement();
                ResultSet spielklasseResult = st.executeQuery(sql);
                spielklasseResult.next();
                temp = new Spielklasse(spielklasseID, spielklasseResult.getString(2), spielklasseResult.getString(3), spielklasseResult.getInt("turnierid"));
                System.out.println(spielklasseResult.getString(2));


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lesen Klappt nicht");
            }
            return temp;
        }*/


    /*    @Override
        public Spielklasse read(int spielklasseID) {
            String sql = "Select * from spielklasse Where spielklasseID=" + spielklasseID;
            Spielklasse temp = null;
            try {
                SQLConnection con = new SQLConnection();
                Connection connection = con.SQLConnection();
                Statement st = connection.createStatement();
                ResultSet spielklasseResult = st.executeQuery(sql);
                spielklasseResult.next();
                temp = new Spielklasse(spielklasseID, spielklasseResult.getString(2), spielklasseResult.getString(3), spielklasseResult.getInt("turnierid"));
                System.out.println(spielklasseResult.getString(2));


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lesen Klappt nicht");
            }
            return temp;
        }*/
   // Dictionary<Integer, Spielklasse> getSpielklassenDict(Turnier turniereingabe);

    //Dictionary<Integer, Spielklasse> getAllSpielklassenDict();
}
