package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface VereinDAO {
     boolean create(Verein verein);
     boolean delete(Verein verein);
     boolean update(Verein verein);
}
