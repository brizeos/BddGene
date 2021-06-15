package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoAccess{

	private String				strClassName = "com.mysql.cj.jdbc.Driver";
	private String				ur;
	private String				local;
	private String				login;
	private String				mdp;
	
	private String				driver;
	
	private PreparedStatement	prs, prs2;
	private Connection			conn;
	
	// Set the constructor of DAOAccess
	public DaoAccess(String url, String nomBDD,String login,String mdp, String driver) {
		this.login=login;
		this.mdp=mdp;
		this.driver=driver;
		this.local = url;
		this.ur = "jdbc:mysql://" + url + "/" + nomBDD + "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	}
	
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
		
	public void setPreparedStatement(String quary)
	{
		try {
			this.prs = conn.prepareStatement(quary);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public PreparedStatement getPreparedStatement()
	{
		return prs;
	}
	
	public void setSecond(String query) {
		try {
			this.prs2 = conn.prepareStatement(query);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public PreparedStatement getSecond()
	{
		return prs2;
	}
	
	public void changeBdd(String str) {
		try {
			this.ur = "jdbc:mysql://" + this.local + "/" + str + "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
			this.conn = this.conn = DriverManager.getConnection(this.ur,this.login,this.mdp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
