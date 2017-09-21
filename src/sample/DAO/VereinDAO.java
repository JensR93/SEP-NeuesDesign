package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface VereinDAO {
    public boolean create(Verein verein);
    public boolean delete(Verein verein);
    public boolean update(Verein verein);
}
