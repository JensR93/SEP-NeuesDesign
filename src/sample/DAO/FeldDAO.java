package sample.DAO;

import sample.Feld;

import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public interface FeldDAO {
     boolean createFeld(Feld feld);
     boolean updateFeld(Feld feld);
     boolean deleteFeld(Feld feld);

}
