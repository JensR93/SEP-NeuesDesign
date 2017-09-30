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
import java.util.ArrayList;


public class SQLConnection 
{
		//auswahlklasse a = new auswahlklasse();
        private static Connection con = null;
        private Statement stmt = null;
        private static int versucheCounter =0;
        private static boolean connectionBekommen = false;

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




	public static boolean SQLConnection()
        {
            try 
            {
				versucheCounter++;
                Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.

                // Verbindung zur JDBC-Datenbank herstellen.
				/*if (versucheCounter==0 && !connectionBekommen) {*/
					con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "user=" + dbUser + "&" + "password=" + dbPass); //con muss unbedingt irgendwo geschlossen werden

				/*else{
					return ;
				}*/
				return true;
				//stmt = con.createStatement();
                //stmt.executeUpdate(db_erstellung);


            } catch (ClassNotFoundException e) 
            {
                System.out.println("Treiber nicht gefunden");
            } catch (SQLException e) 
            {
				ladeDatenbank();
				System.out.println("Verbindung nicht moglich");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());

            }
			return connectionBekommen;
        }

	private static boolean ladeDatenbank() {
		System.out.println("Lade Datenbank neu");
		versucheCounter++;
		ArrayList<String> sqlBefehle = new ArrayList<>();
		sqlBefehle.add("CREATE DATABASE IF NOT EXISTS Turnierverwaltung_neu");
		sqlBefehle.add("USE Turnierverwaltung_neu");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Spiel (SpielID int(10) NOT NULL AUTO_INCREMENT, AufrufZeit time, Schiedsrichter int(10), Feld int(10), status int(1) Default '0',Heim int(10), Gast int(10), SiegerID int(10), SpielklasseID int(10), ZeitplanNummer int(10), RundenZeitplanNummer int(5), PRIMARY KEY (SpielID))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Feld (FeldID int(10) NOT NULL AUTO_INCREMENT, aktivesSpiel int(10) Default null, ProfiMatte binary Default '0', turnierid int(10) not null, InVorbereitung int(10) Default null, PRIMARY KEY (FeldID))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Verein (VereinsID int(10) NOT NULL AUTO_INCREMENT, Name varchar(50), Verband varchar(30), ExtVereinsID varchar(20), PRIMARY KEY (VereinsID))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Spieler (SpielerID int(10) NOT NULL AUTO_INCREMENT, VName varchar(50), NName varchar(50), GDatum date, Geschlecht binary Default '0', extVereinsid Varchar(10), RLP_Einzel int(4) Default '0', RLP_Doppel int(4) Default '0', RLP_Mixed int(4) Default '0', MeldeGebuehren float Default '0.0', Nationalitaet int (10) Default '0', MattenSpiele int(10) Default '0', ExtSpielerID varchar(20), AktuellesSpiel int(10),OffenerBetrag binary ,Notiz Varchar(255), PRIMARY KEY (SpielerID))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Turnier (TurnierID int(10) NOT NULL AUTO_INCREMENT, MatchDauer int(3) Default '30',  Name varchar(50) NOT NULL, SpielerPausenZeit int(3) Default '5', Zaehlweise int(1) Default '0', startzeitEinzel timestamp, startzeitDoppel timestamp, startzeitMixed timestamp, MeldegebuehrEinzel float(4) Default '5', MeldegebuehrDoppel float(4) Default '5',  PRIMARY KEY (Turnierid))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Spielklasse (SpielklasseID int(10) NOT NULL AUTO_INCREMENT, Disziplin varchar(20) not null, Niveau varchar(20) not null, turnierid int(10) not null, spielsystemCode int(50), MeldeKosten float Default '0.0', Aktiv binary Default '0', PRIMARY KEY (SpielklasseID))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS spielklasse_setzliste (setzplatz int(10) Not NULL, spielklasseID int(10) Not NULL, TeamID int(10) Not NULL)");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Team (TeamID int(10) not null auto_increment, SpielklasseID int(10) not null, GewonneneSpiele int(10) Default '0', GewonneneSaetze int(10) Default '0', VerloreneSaetze int(10) Default '0', GewonnnenePunkte int(10) Default '0', VerlorenePunkte int(10) Default '0', FesterSetzplatz int(3), PRIMARY KEY (TeamID))");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Team_Spieler (TeamID int(10) not null, SpielerID int(10) not null)");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Spiel_Satzergebnis (spielID int(10) NOT NULL, HeimPunkte int(2) Not null, GastPunkte int(2) Not null)");
		sqlBefehle.add("CREATE TABLE IF NOT EXISTS Spielklasse_SpielID (SpielID int(10) NOT NULL, SpielklasseID int(10) NOT NULL, SpielklasseSpielID int(10) NOT NULL)");
		sqlBefehle.add("ALTER TABLE spielklasse ADD Constraint FOREIGN KEY (turnierid) REFERENCES Turnier(TurnierID)");
		sqlBefehle.add("ALTER TABLE feld ADD Constraint FOREIGN KEY (turnierid) REFERENCES Turnier(TurnierID)");
		sqlBefehle.add("ALTER TABLE team ADD Constraint FOREIGN KEY (SpielklasseID) REFERENCES Spielklasse(SpielklasseID)");
		sqlBefehle.add("ALTER TABLE Spielklasse_setzliste ADD CONSTRAINT FOREIGN KEY (SpielklasseID) REFERENCES Spielklasse (SpielklasseID)");
		sqlBefehle.add("ALTER TABLE Spielklasse_SpielID ADD Constraint FOREIGN KEY (SpielklasseID) REFERENCES Spielklasse(SpielklasseID)");
		sqlBefehle.add("ALTER TABLE Spiel ADD CONSTRAINT FOREIGN KEY (SpielklasseID) REFERENCES Spielklasse (SpielklasseID)");
		sqlBefehle.add("ALTER TABLE Spiel ADD CONSTRAINT FOREIGN KEY (Feld) REFERENCES Feld (FeldID)");
		sqlBefehle.add("ALTER TABLE Team_Spieler ADD Constraint FOREIGN KEY (SpielerID) REFERENCES Spieler(SpielerID)");
		sqlBefehle.add("ALTER TABLE Team_Spieler ADD Constraint FOREIGN KEY (TeamID) REFERENCES Team(TeamID)");
		sqlBefehle.add("ALTER TABLE Spielklasse_setzliste ADD CONSTRAINT FOREIGN KEY (TeamID) REFERENCES Team (TeamID)");
		sqlBefehle.add("ALTER TABLE Spiel ADD CONSTRAINT FOREIGN KEY (heim) REFERENCES Team (TeamID)");
		sqlBefehle.add("ALTER TABLE Spiel ADD CONSTRAINT FOREIGN KEY (Gast) REFERENCES Team (TeamID)");
		sqlBefehle.add("ALTER TABLE Spiel ADD CONSTRAINT FOREIGN KEY (Schiedsrichter) REFERENCES Spieler (SpielerID)");
		sqlBefehle.add("ALTER TABLE feld ADD Constraint FOREIGN KEY (aktivesSpiel) REFERENCES Spiel(SpielID)");
		sqlBefehle.add("ALTER TABLE feld ADD Constraint FOREIGN KEY (inVorbereitung) REFERENCES Spiel(SpielID)");
		sqlBefehle.add("ALTER TABLE Spielklasse_SpielID ADD Constraint FOREIGN KEY (SpielID) REFERENCES Spiel(SpielID)");
		sqlBefehle.add("ALTER TABLE Spiel_Satzergebnis ADD CONSTRAINT FOREIGN KEY (spielID) REFERENCES spiel (spielID)");
		sqlBefehle.add("ALTER TABLE Spieler ADD CONSTRAINT FOREIGN KEY (AktuellesSpiel) REFERENCES Spiel (SpielID)");
		sqlBefehle.add("ALTER TABLE `turnier` ADD UNIQUE(`Name`)");


		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/","root","");
			for (int i=0;i<sqlBefehle.size();i++){
				String sqlBefehl = sqlBefehle.get(i);
				if (connection.isClosed()){
					connection = DriverManager.getConnection("jdbc:mysql://localhost/","root","");
				}
				Statement smt = connection.createStatement();
				System.out.println(sqlBefehl);
				smt.execute(sqlBefehl);
				smt.close();
			}
			connectionBekommen=true;
			return true;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
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
