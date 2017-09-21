package sample.DAO;

import sample.Ergebnis;
import sample.Spiel;

/**
 * Created by Florian-PC on 26.07.2017.
 */
public interface ErgebnisDAO {
    public boolean create (Spiel ergebnis);
    public boolean delete (Spiel ergebnis);
}
