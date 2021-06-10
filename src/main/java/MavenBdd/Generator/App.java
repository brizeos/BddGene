package MavenBdd.Generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JTextField;

import model.Column;
import model.Constraint;
import model.DaoAccess;
import model.Database;
import model.Table;
import vue.Btn;
import vue.ComboSelecter;
import vue.Lab;
import vue.Pane;
import vue.ViewPrincipal;

/***
 * 
 * @author Brizeos
 * @version 0.01
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 *
 */
public class App extends JFrame
{
	public static int width = 800, height = 640;
	public static JTextField jt1, jt2, jt3;
	public static ComboSelecter cs;
	public static DaoAccess dao = null;
	public static Database db = new Database();
	public static App frame; 
	
	
	public App(String title) throws HeadlessException {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBackground(new Color(240, 240, 240));
		setResizable(false);
		setForeground(Color.WHITE);
		setBounds(0,0,width,height);
		setLocationRelativeTo(null);
		setVisible(true);
	}


	
	
	
    public static void main( String[] args )
    {
        
    	frame = new App("BddGenerator");
    	
    	Pane pane = new Pane();
    	Pane pConnect = new Pane(width/10, height/5, width/10*8, height/2);
    	
    	
    	Lab welcome = new Lab("Bienvenue sur mon générateur.");
    	welcome.setBounds(10,10,450,100);
    	welcome.setForeground(Color.white);
    	welcome.setFont(new Font("TimesRoman", Font.PLAIN, 25));
    	

    	Lab l1 = new Lab("Id Bdd : ");
        Lab l2 = new Lab("Mdp Bdd : ");
        Lab l4 = new Lab("Port de MySql : ");
        Lab l3 = new Lab("Vous vous connecterez à la base de donnée :  ");
        
        l1.setBounds(10, 10, 140, 50);
        l2.setBounds(10, 75, 140, 50);
        l4.setBounds(10, 140, 400, 50);
        l3.setBounds(10, 250, 400, 50);
        
        jt1 = new JTextField();
        jt2 = new JTextField();
        jt3 = new JTextField();
        jt1.setBounds(150, 20, 200, 30);
        jt2.setBounds(150, 85, 200, 30);
        jt3.setBounds(150, 150, 200, 30);
        
        
        
        Btn b1 = new Btn("Charger les tables", 120, 200, 150, 25);
        Btn b2 = new Btn("Charger la BDD", 450, 200, 150, 25);
        
        
        //DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
        cs = new ComboSelecter(new ArrayList<String>());
        cs.setBounds(450, 150, 150, 25);

    	pConnect.add(l1);
    	pConnect.add(l2);
    	pConnect.add(l3);
    	pConnect.add(l4);
    	pConnect.add(jt1);
    	pConnect.add(jt2);
    	pConnect.add(jt3);
    	pConnect.add(b1);
    	pConnect.add(b2);
    	pConnect.add(cs);
    	
    	
    	
    	pane.add(pConnect);
    	pane.add(welcome);
    	
    	
    	
    	
    	
    	frame.getContentPane().add(pane);
    	frame.repaint();
    	frame.revalidate();
    	
    	
    	
    	
    	/*
    	 * Action Chargement des tables
    	 */
    
    	
    	
    	b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cs.setModel( loadTables() );
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			
		});
    	
    	
    	/*
    	 * Action Chargement de la BDD.table
    	 */
    	
    	b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loadDB();
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
    	
    	
    	
    	
    }
    
    

	public static JTextField getJt1() {
		return jt1;
	}
	public static JTextField getJt2() {
		return jt2;
	}
	public static JTextField getJt3() {
		return jt3;
	}
	
	public static String getDBName() {
		return cs.getModel().getSelectedItem().toString();
	}
	
	public static DefaultComboBoxModel<String> loadTables() throws SQLException {
		
		String sql = "SELECT DISTINCT `TABLE_SCHEMA` FROM `TABLES` WHERE `TABLE_TYPE`='BASE TABLE' AND `TABLE_SCHEMA` NOT IN ('mysql', 'performance_schema', 'sys', 'syscom');";
		ResultSet rs = null;
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
    	
    	String login = getJt1().getText();
    	String mdp = getJt2().getText();
    	String url = "localhost:"+ getJt3().getText();
    	
    	dao = new DaoAccess(url, "information_schema", login, mdp, null);
    	
    	dao.connect();
    	
    	dao.setPreparedStatement(sql);
    	rs = dao.getPreparedStatement().executeQuery();
    	while(rs.next()) {
    		str.addElement(rs.getString(1));
    
    	}
    	dao.disconnect();
 
    	return str;
    	
    }
	
	protected static void loadDB() throws SQLException, IOException {
		
			int i = 0, j = 0;
//			ArrayList<String> lsTables = new ArrayList<>();
			String sql = "SELECT DISTINCT `TABLE_NAME` FROM `TABLES` WHERE `TABLE_SCHEMA` = ?";
			
			ResultSet rs = null;
			db.setName(cs.getModel().getSelectedItem().toString());
			
			dao.connect();
	    	
	    	dao.setPreparedStatement(sql);
	    	dao.getPreparedStatement().setString(1, cs.getModel().getSelectedItem().toString());
	    	
	    	rs = dao.getPreparedStatement().executeQuery();
	    	db.setLstTable(new ArrayList<Table>());
	    	
	    	//Récupere les tables
	    	while(rs.next()) {
	    		
	    		ResultSet rsBis = null;
	    		
	    		db.getLstTable().add(new Table());	   
	    		db.getLstTable().get(i).setTableName(rs.getString("TABLE_NAME"));
	    		db.getLstTable().get(i).setLstColumn(new ArrayList<Column>());
	    		

	    		
	    		String sql2 = "SELECT * FROM `COLUMNS` WHERE `TABLE_SCHEMA` = ? AND `TABLE_NAME` = ?;";
	    		dao.setPreparedStatement(sql2);
	    		dao.getPreparedStatement().setString(1, cs.getModel().getSelectedItem().toString());
	    		dao.getPreparedStatement().setString(2, rs.getString("TABLE_NAME"));
	    		
	    		rsBis = dao.getPreparedStatement().executeQuery();
	    		while(rsBis.next()) {
	    			//Récupere les colonnes
	    			j = 0;
	    			db.getLstTable().get(i).getLstColumn().add( new Column(	rsBis.getString("COLUMN_NAME"),
													    					rsBis.getString("DATA_TYPE"),
													    					rsBis.getInt("CHARACTER_MAXIMUM_LENGTH"),
													    					(rsBis.getString("COLUMN_KEY").equals("PRI") && rsBis.getString("COLUMN_KEY") != null? true : false),
													    					((rsBis.getString("IS_NULLABLE").equals("YES") ? true : false)),
													    					db.getLstTable().get(i) ) );


		    		
	    			db.getLstTable().get(i).getLstColumn().get(j).setLstCons(new ArrayList<Constraint>());
	    			ResultSet rsCons = null;
	    			
	    			String sql3 = "SELECT * FROM `KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = ? AND `TABLE_NAME`= ? AND `COLUMN_NAME`= ? AND `REFERENCED_COLUMN_NAME` IS NOT null;";
	    			dao.setPreparedStatement(sql3);
	    			dao.getPreparedStatement().setString(1, cs.getModel().getSelectedItem().toString());
	    			dao.getPreparedStatement().setString(2, rs.getString("TABLE_NAME"));
	    			dao.getPreparedStatement().setString(3, rsBis.getString("COLUMN_NAME"));
		    		
		    		rsCons = dao.getPreparedStatement().executeQuery();
		    		
		    		while(rsCons.next()) {
		    			
		    			db.getLstTable().get(i).getLstColumn().get(j).getLstCons().add(  new Constraint( 
		    					rsCons.getString("TABLE_NAME")+"."+rsCons.getString("COLUMN_NAME") , 
		    					rsCons.getString("REFERENCED_TABLE_NAME")+"."+rsCons.getString("REFERENCED_COLUMN_NAME"),
		    					db.getLstTable().get(i).getLstColumn().get(j))  );
		    			db.getLstTable().get(i).getLstColumn().get(j).setIsConstrained(true);
		    			db.getLstTable().get(i).setConstrained(true);
		    		}
		    		
	    			j++;
	    		}
	    		
	    		i++;
	    	}
	    	dao.disconnect();
	    	
	    	frame.getContentPane().removeAll();
	    	frame.getContentPane().add(new ViewPrincipal());
	    	
	    	frame.repaint();
	    	frame.revalidate();
	}
    
}
