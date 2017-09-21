package sample.DAO;

import sample.Feld;

import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public interface FeldDAO {
    public boolean createFeld(Feld feld);
    public boolean updateFeld(Feld feld);
    public boolean deleteFeld(Feld feld);

}
