package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface SpielDAO {
     boolean create(Spiel spiel);
     boolean delete(Spiel spiel);
     boolean update(Spiel spiel);
}
