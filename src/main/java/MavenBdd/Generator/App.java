package MavenBdd.Generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import model.DaoAccess;
import model.Database;
import sql.ResearchSql;
import vue.Pane;
import vue.components.Btn;
import vue.components.ComboSelecter;
import vue.components.Lab;


/***
 * 
 * @author Brizeos
 * @version 0.01
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 *
 */
@SuppressWarnings("serial")
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
        Lab l4 = new Lab("Url:port MySql : ");
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
    	
    	/***
    	 * Action Chargement des tables
    	 */
    	b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cs.setModel( ResearchSql.loadTables() );
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
    	
    	
    	/**
    	 * Action Chargement de la BDD.table
    	 */
    	b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ResearchSql.loadDB();
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
    }
    
    
	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/
	public  JTextField getJt1() {
		return jt1;
	}
	public JTextField getJt2() {
		return jt2;
	}
	public  JTextField getJt3() {
		return jt3;
	}
	
	
	/**
	 * 
	 * @return Name of the Db actually charged.
	 */
	public static String getDBName() {
		return cs.getModel().getSelectedItem().toString();
	}
	
	
	
	
    
}
