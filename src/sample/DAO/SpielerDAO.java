package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.util.List;

/**
 * Created by flori on 30.05.2017.
 */
public interface SpielerDAO {
     boolean create(Spieler spieler);
     boolean delete(Spieler spieler);
     boolean update(Spieler spieler);
    //public Spieler read (int spielerID);
    //public List<Spieler> getAllSpieler();
}
