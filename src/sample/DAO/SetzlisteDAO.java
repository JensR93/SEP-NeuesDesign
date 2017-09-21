package sample.DAO;

import sample.*;

import java.util.List;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface SetzlisteDAO {
    public boolean create(int setzplatz, Team team,  Spielklasse spielklasse);

    boolean deleteSetzplatz(int spielklasseid, int teamid);

    public boolean delete(int spielklasseid);
    public boolean update(int setzplatz, Team team,  Spielklasse spielklasse);
}
