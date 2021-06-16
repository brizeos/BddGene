package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/***
 * 
 * @author Anthony Plays
 * {@link} https://www.linkedin.com/in/anthony-plays-b61987206/
 *
 */
public class DaoAccess{

	private String				strClassName = "com.mysql.cj.jdbc.Driver";
	private String				ur;
	private String				local;
	private String				login;
	private String				mdp;
	@SuppressWarnings("unused")
	private String				driver;
	
	private PreparedStatement	prs, prs2;
	private Connection			conn;
	
	/***
	 * 
	 * @param url 		Url to MySql. by default this is "localhost"
	 * @param nomBDD	Database to use. In first time this is "information_schema". We can change this with a method. 
	 *		 @see model.DaoAccess.changeBdd
	 * @param login		Login with permission to the DB to fill (Write) and "information_schema" (Read-only)
	 * @param mdp		Password
	 * @param driver	Not used.
	 */
	public DaoAccess(String url, String nomBDD,String login,String mdp, String driver) {
		this.login=login;
		this.mdp=mdp;
		this.driver=driver;
		this.local = url;
		this.ur = "jdbc:mysql://" + url + "/" + nomBDD + "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	}
	
	/**
	 * Method to connected DaoAccess Object to MySql
	 */
	public void connect()
	{
		try { // try for connexion to database
			Class.forName(strClassName);	
			this.conn = DriverManager.getConnection(this.ur,this.login,this.mdp);
		} 
		catch(ClassNotFoundException e)
		{  
			System.err.println("Driver non charg� !");  e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/***
	 * 	Method to disconnected DaoAccess Object from MySql
	 */
	public void disconnect()
	{
		try 
		{
			this.conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}
		
	/**
	 * Set the query to the DaoAccess object.
	 * @param quary String containing the Sql Query.
	 */
	public void setPreparedStatement(String quary)
	{
		try {
			this.prs = conn.prepareStatement(quary);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return The Sql Query actually set in the DaoAccess object.
	 */
	public PreparedStatement getPreparedStatement()
	{
		return prs;
	}
	
	
	/***
	 * Set a second query to the DaoAccess object.
	 * @param query String containing the Sql Query.
	 */
	public void setSecond(String query) {
		try {
			this.prs2 = conn.prepareStatement(query);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/***
	 * 
	 * @return The Sql Query actually set in the second DaoAccess object.
	 */
	public PreparedStatement getSecond()
	{
		return prs2;
	}
	
	
	/***
	 * 
	 * @param str Change database used in the DaoAccess object.
	 */
	public void changeBdd(String str) {
		try {
			this.ur = "jdbc:mysql://" + this.local + "/" + str + "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
			this.conn = DriverManager.getConnection(this.ur,this.login,this.mdp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
