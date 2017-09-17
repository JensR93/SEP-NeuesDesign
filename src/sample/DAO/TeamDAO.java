package sample.DAO;

import sample.Team;

import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public interface TeamDAO {
     boolean create(Team team);
     boolean createFreilos(Team team);
     boolean update(Team team);
     boolean addSpieler(Team team, boolean ersterSpieler);
     boolean delete(Team team);
    //public List<Team> getAllTeams();
}
