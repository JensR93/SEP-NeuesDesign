package sample.DAO;

import sample.*;

import java.util.List;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface SetzlisteDAO {
     boolean create(int setzplatz, Team team, Spielklasse spielklasse);

    boolean deleteSetzplatz(int spielklasseid, int teamid);

     boolean delete(int spielklasseid);
     boolean update(int setzplatz, Team team, Spielklasse spielklasse);
}
