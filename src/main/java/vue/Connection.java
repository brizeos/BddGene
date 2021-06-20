package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sql.ResearchSql;
import vue.components.Btn;
import vue.components.ComboSelecter;
import vue.components.Lab;
import vue.components.TCustom;

@SuppressWarnings("serial")
public class Connection extends JPanel {

	private JPanel gauche;
	private JPanel droite;
	private static JTextField jt1, jt2, jt3;
	private ComboSelecter cs;
	
	
	public Connection() {
		
		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		

		Lab welcome = new Lab("Bienvenue sur mon générateur.",true);
    	welcome.setForeground(Color.white);
    	welcome.setFont(new Font("TimesRoman", Font.PLAIN, 25));
	
    	this.gauche = new JPanel();
    	
    	gauche.setLayout(new BoxLayout(gauche, BoxLayout.PAGE_AXIS));
    	gauche.setBackground(Color.black);

    	
    	this.droite = new JPanel();
    	droite.setLayout(new BoxLayout(droite, BoxLayout.PAGE_AXIS));
    	droite.setBackground(Color.black);
    	/**
    	 * 
    	 */
    	
    	Lab l1 = new Lab("   Id Bdd : ",false);
	    Lab l2 = new Lab("   Mdp Bdd : ",false);
	    Lab l3 = new Lab("   Port MySql : ",false);
	    
	    jt1 = new TCustom(25);
	    jt2 = new TCustom(25);
	    jt3 = new TCustom(25);
	   
	    Btn b1 = new Btn("Charger les tables");
	    Btn b2 = new Btn("Charger la BDD");
	    
	    this.cs = new ComboSelecter(new ArrayList<String>());
    	
    	b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getCs().setModel( ResearchSql.loadTables() );
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
    	
    	
    	Dimension dim = new Dimension(500,50);
    	Dimension espace = new Dimension(0,20);
    	FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
    	/**
    	 * 
    	 */
    	gauche.add(welcome);
    	gauche.add(Box.createVerticalGlue());
    		JPanel opt1 = new JPanel(flow);
    		setSizeCustom(opt1, dim);
    		opt1.add(l1);
    		opt1.add(jt1);
    	gauche.add(opt1);
    	gauche.add(Box.createRigidArea(espace));
    		JPanel opt2 = new JPanel(flow);
    		setSizeCustom(opt2, dim);
    		opt2.add(l2);
    		opt2.add(jt2);
    	gauche.add(opt2);
    	gauche.add(Box.createRigidArea(espace));
    		JPanel opt3 = new JPanel(flow);
    		setSizeCustom(opt3, dim);
    		opt3.add(l3);
    		opt3.add(jt3);
    	gauche.add(opt3);
    	gauche.add(Box.createRigidArea(espace));
    	gauche.add(b1);
    	gauche.add(Box.createVerticalGlue());
    	
    	
    	droite.add(Box.createVerticalGlue());
    		JPanel opt4 = new JPanel(flow);
    		setSizeCustom(opt4, new Dimension(300,100));
    		opt4.add(cs);
    		opt4.add(b2);
    	droite.add(opt4);
    	droite.add(Box.createVerticalGlue());
    	
    	
    	add(gauche, BorderLayout.WEST);
    	add(droite, BorderLayout.EAST);
    	
    	
    	add(Box.createHorizontalGlue());
    	
    	
    	
	}

	private void setSizeCustom(JPanel opt, Dimension dim) {
		opt.setPreferredSize(dim);
		opt.setMaximumSize(dim);
		opt.setMinimumSize(dim);
		opt.setBackground(Color.black);
		
	}

	public  JTextField getJt1() {
		return jt1;
	}
	public JTextField getJt2() {
		return jt2;
	}
	public  JTextField getJt3() {
		return jt3;
	}

	public ComboSelecter getCs() {
		return cs;
	}

	public static void setJt1(JTextField jt1) {
		Connection.jt1 = jt1;
	}

	public static void setJt2(JTextField jt2) {
		Connection.jt2 = jt2;
	}

	public static void setJt3(JTextField jt3) {
		Connection.jt3 = jt3;
	}

	public void setCs(ComboSelecter cs) {
		this.cs = cs;
	}
	
	
}
