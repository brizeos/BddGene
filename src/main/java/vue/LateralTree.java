package vue;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import MavenBdd.Generator.App;
import MavenBdd.Generator.utils;
import model.Column;
import model.Database;
import model.Table;
import vue.components.Btn;
import vue.components.Lab;
import vue.components.MyCellRenderer;
import vue.components.MyTreeSelectionListener;
import vue.components.SimpleJPanel;

public class LateralTree extends JPanel {

	private static Table tableSelected = new Table();
	private SimpleJPanel paneNav;
	private Component btnDeco;
	private Component btnNext;
	private Lab nbRestant;
	private Database db;

	public LateralTree() {
	}

	public LateralTree(LayoutManager layout) {
		super(layout);
	}

	public LateralTree(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public LateralTree(int x, int y, int wx, int wy, Database db) {
		
		super();
		setBounds(x, y , wx, wy);
		setLayout(null);
		
		this.db = db.sortByLevel();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(db);
		DefaultTreeModel dft = new DefaultTreeModel(root);
		
		JTree orga = new JTree(dft);
		
		for(Table t : db.getLstTable()) {
			DefaultMutableTreeNode tmpTable = new DefaultMutableTreeNode( t );
			root.add( tmpTable );
			for( Column c : t.getLstColumn()) {
				if(  !( c.getIsPrimary() ) && !( c.getIsConstrained() )  ) {
					DefaultMutableTreeNode tmpCol = new DefaultMutableTreeNode( c.getName() );
					tmpTable.add(tmpCol);
				}
			}
//			if(tmpTable.getChildCount() == 0) 
//				root.remove(tmpTable);
		}

		orga.addTreeSelectionListener(new MyTreeSelectionListener());

		orga.setCellRenderer(new MyCellRenderer());
		orga.setBounds(10, 10, wx-20, wy-200);
		orga.setFont(new Font(orga.getFont().getFamily(), Font.BOLD, 15));
		add(orga);

		this.nbRestant = new Lab("Il reste " + this.db.checkNb() + " colonnes à configurer.", false);
		this.nbRestant.setBounds(30, wy-180, wx-50, 50);
		this.nbRestant.setFont(new Font("Arial", getFont().getStyle(), 25));
		add(this.nbRestant);
		
		this.btnDeco = new Btn("Déconnexion", 0, 0, (wx-80)/2, 80);
		this.btnNext = new Btn("Générer", (wx-80)/2, 0, (wx-80)/2, 80);

		this.btnNext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					App.getDao().changeBdd(App.getDBName());
					App.getDao().connect();
					utils.startGeneration();
					App.getDao().disconnect();
					
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI oURL = new URI("http://localhost/phpmyadmin/db_structure.php?server=1&db="+App.getDBName());
					desktop.browse(oURL);
					
				
				} catch (SQLException | URISyntaxException | IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});	

	



		this.paneNav = new SimpleJPanel(30, wy-100, wx-60, 80);
		this.paneNav.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.paneNav.add(this.btnDeco);
		this.paneNav.add(this.btnNext);
		add(this.paneNav);
		
		
		
	}

	public static Table getTableSelected() {
		return tableSelected;
	}

	public static void setTableSelected(Table tableSelected) {
		LateralTree.tableSelected = tableSelected;
	}

	public void updateNbRestant() {
		this.nbRestant.setText("Il reste " + this.db.checkNb() + " colonnes à configurer.");
	}
}
