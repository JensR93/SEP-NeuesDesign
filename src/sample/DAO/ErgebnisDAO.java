package sample.DAO;

import sample.Ergebnis;
import sample.Spiel;

/**
 * Created by Florian-PC on 26.07.2017.
 */
public interface ErgebnisDAO {
     boolean create(Spiel ergebnis);
     boolean delete(Spiel ergebnis);
}
