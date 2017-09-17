package sample.DAO;

import sample.Turnier;

import java.util.Dictionary;

public interface TurnierDAO {
     boolean create(Turnier turnier);
     boolean delete(Turnier turnier);
     boolean update(Turnier turnier);
     Turnier read(Turnier turnierEingabe);
     boolean readFelder_Neu(Turnier turnierEingabe);
   // public List<Turnier> getAllTurniere();

     Dictionary<Integer,Turnier> getAllTurniere();
}
