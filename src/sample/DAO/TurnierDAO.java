package sample.DAO;

import javafx.collections.ObservableList;
import sample.Turnier;

public interface TurnierDAO {
     boolean create(Turnier turnier);
     boolean delete(Turnier turnier);
     boolean update(Turnier turnier);
     Turnier read(Turnier turnierEingabe);
     boolean readFelder_Neu(Turnier turnierEingabe);
   // public List<Turnier> getAllTurniere();

     ObservableList getAllTurniere();
}
