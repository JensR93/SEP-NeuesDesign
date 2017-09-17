package sample.DAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLConnection 
{
		//auswahlklasse a = new auswahlklasse();
        private static Connection con = null;
        private Statement stmt = null;

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPass() {
		return dbPass;
	}

	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}

	private static String dbHost = "localhost"; // Hostname
        private static String dbPort = "3306";      // Port -- Standard: 3306
        private static String dbName = "turnierverwaltung_neu";   // Datenbankname
        private static String dbUser = "root";     // Datenbankuser
        private static String dbPass = "";      // Datenbankpasswort
        private static String db_erstellung = "create table if not exists turnierverwaltung";
        private static String db_nutzung = "USE turnierverwaltung";




	public static void SQLConnection()
        {
            try 
            {
                Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.

                // Verbindung zur JDBC-Datenbank herstellen.
                con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "user=" + dbUser + "&" + "password=" + dbPass); //con muss unbedingt irgendwo geschlossen werden
                //stmt = con.createStatement();
                //stmt.executeUpdate(db_erstellung);


            } catch (ClassNotFoundException e) 
            {
                System.out.println("Treiber nicht gefunden");
            } catch (SQLException e) 
            {

				try {
					//pressBtn_Einstellungenneu();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println("Verbindung nicht moglich");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
	public void pressBtn_Einstellungenneu () throws Exception {
		System.out.println("test");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/DBEinstellungen.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		//a.addStage(stage);
		stage.setScene(new Scene(root1));
		stage.show();
		stage.setTitle("Einstellungen");
		//((Node)(event.getSource())).getScene().getWindow().hide();
	}
        //Funktioniert. Einfach select abfragen
        public ResultSet executeSQL(String sql)
        {
        	try
        	{
        		Statement smt = con.createStatement();
        		
        		ResultSet res = stmt.executeQuery(sql);

        		return res;
        		
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("Klappt nicht");
        	}
        	return null;
        }

	public static Connection getCon() throws SQLException {
		if (con==null||con.isClosed()){
        	SQLConnection();
		}

		return con;
	}

	//Ergebnis ausgeben (komplette Tabelle z.b.)
        public void PrintResult(ResultSet r) //als boolean machen, um zu pr�fen ob erfolgreich (gilt f�r alle void sql klassen!) Booleans immer weiterleiten und ganz am ende ausgeben ob erfolgreich 
        {
        	try {
				while(r.next())
				{
					int maxColoums = r.getMetaData().getColumnCount();
					String print = "";
					for(int i =1; i<=maxColoums; i++) //eventuell bei 1 starten
					{
						print += " ";
						print += r.getMetaData().getColumnName(i);
						print += " = ";
						print += r.getString(i);
					}
					System.out.println(print);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Fehler");
			}
        }

        //funktioniert
        public int getSpielerID(String firstname, String lastname)
        {
        	String sql = "SELECT SpielerID,VNAME, NNAME FROM spieler";
        	ResultSet r = executeSQL(sql);
        	try {
        		while(r.next())
        		{
        			if(firstname.equals(r.getString(2))&&lastname.equals(r.getString(3))) //Spalte 1 = firstname
        			{
        				return r.getInt(1);
        			}
        		}
        		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return -1; //�berpr�fung in main, ob nicht -1 return
        }
        public String getSpielerName(int id)
        {
        	String sql = "SELECT SpielerID,VNAME, NNAME FROM spieler";
        	ResultSet r = executeSQL(sql);
        	try {
        		while(r.next())
        		{ 
        			if(id==Integer.parseInt(r.getString(1))) //Spalte 1 = firstname
        			{
        				return r.getString(2)+" "+r.getString(3);
        			}
        		}
        		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return " Nicht gefunden"; //�berpr�fung in main, ob nicht -1 return
        }
        public ResultSet getSpielerr(int id)
        {
        	String sql = "SELECT SpielerID,VNAME, NNAME, GDatum FROM spieler WHERE SpielerID = "+id;
        	try
        	{
        		Statement smt = con.createStatement();
        		
        		ResultSet res = stmt.executeQuery(sql);

        		return res;
        		
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("Klappt nicht");
        	}
        	return null;
        }
        public Boolean insertSpieler(String vorname, String nachname)
        {
        	String sql = "INSERT INTO spieler("
			        + "VName,"
			        + "NName) "
			        +  "VALUES(?,?)";
        	try
        	{
        		PreparedStatement smt = con.prepareStatement(sql);
        		smt.setString(1, vorname);
        		smt.setString(2, nachname);
        		smt.executeUpdate();
        		smt.close();
        		return true;
        		
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("Klappt nicht");
        	}
        	return false;
        	
        	
        }

        
}
