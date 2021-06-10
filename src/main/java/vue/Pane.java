package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import MavenBdd.Generator.App;
import MavenBdd.Generator.utils;
import model.Column;
import model.Database;
import model.Table;

public class Pane extends JPanel {

	public static Table tableSelected = new Table();
	private static Column column;
	private JButton  btn;
	private Lab nbRestant;
	private Database db;
	private JButton okay;
	private Pane paneNav;
	private Btn btnDeco;
	private Btn btnNext;
	
	
	
	public Pane() {
		super();
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 800, 620);
		setLayout(null);
		
	}


	public Pane(int x, int y, int wx, int wy) {
		
		super();
		setBounds(x, y , wx, wy);
		setLayout(null);
		
	}


	public Pane(int x, int y, int wx, int wy, Database db) {
		
		super();
		setBounds(x, y , wx, wy);
		setLayout(null);
		
		this.db = db;
		
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
			
			
			
			if(tmpTable.getChildCount() == 0) 
				root.remove(tmpTable);
			
			

		}

		orga.addTreeSelectionListener(new MyTreeSelectionListener());

		orga.setCellRenderer(new MyCellRenderer());
		orga.setBounds(10, 10, wx-20, wy-200);
		orga.setFont(new Font(orga.getFont().getFamily(), Font.BOLD, 15));
		add(orga);


		this.nbRestant = new Lab("Il reste " + this.db.checkNb() + " colonnes à configurer.");
		this.nbRestant.setBounds(30, wy-180, wx-50, 50);
		this.nbRestant.setFont(new Font("Arial", getFont().getStyle(), 25));
		add(this.nbRestant);

		this.btnDeco = new Btn("Déconnexion", 0, 0, (wx-80)/2, 80);
		this.btnNext = new Btn("Suivant", (wx-80)/2, 0, (wx-80)/2, 80);

		this.btnNext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					App.dao.connect();
					utils.LaunchDiff();
					App.dao.disconnect();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});	





		this.paneNav = new Pane(30, wy-100, wx-60, 80);
		this.paneNav.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.paneNav.add(this.btnDeco);
		this.paneNav.add(this.btnNext);
		add(this.paneNav);
	}


	public Pane(int x, int y, int wx, int wy, ArrayList<Column> lstColumn) {
		
		super();
		
		setBounds(x, y , wx, wy);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		
		LoadDiv(); 
		
		
		
		
	}

	
	public Pane(int i, int j, int k, int l, Column c) {
		this.okay = new JButton("Valider");
		this.btn = new JButton("Annuler");
		
		column = c; 
		setBounds(i, j , k, l);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setEnabled(false);
		setVisible(false);
		
		if(!c.getName().equals("null"))
			LoadCol();
		
		this.btn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				ViewPrincipal.getLateral().updateNbRestant();
				
				ViewPrincipal.getLateral().setEnabled(true);
				ViewPrincipal.getLateral().setVisible(true);
				
				ViewPrincipal.getContent().setEnabled(true);
				ViewPrincipal.getContent().setVisible(true);
				
				ViewPrincipal.getModif().setVisible(false);
				ViewPrincipal.getModif().setEnabled(false);
							
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
				
				
				
			}
		
		});
		this.okay.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				getColumn().setValidated(true);
				
				ViewPrincipal.getLateral().updateNbRestant();
				
				ViewPrincipal.getLateral().setEnabled(true);
				ViewPrincipal.getLateral().setVisible(true);
				
				ViewPrincipal.getContent().setEnabled(true);
				ViewPrincipal.getContent().setVisible(true);
				
				ViewPrincipal.getModif().setVisible(false);
				ViewPrincipal.getModif().setEnabled(false);
							
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
			}
		
		});
		
		repaint();
		revalidate();
		
		
	}


	public Pane(ArrayList<ArrayList<Table>> lsLvl) {
		this.okay = new JButton("Valider");
		this.btn = new JButton("Annuler");
		
		
		setBounds(0, 0, 450, App.frame.getContentPane().getHeight());
		setLayout(null);
		setEnabled(true);
		setVisible(true);
		
		
		
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(lsLvl);
		
		DefaultTreeModel dft = new DefaultTreeModel(root);
		
		JTree orga = new JTree(dft);
		
	//	for(Table t : lsLvl.getLstTable()) {
		for (int i = lsLvl.size()-1 ; i >=0 ; i--) {
			for (int j = 0; j < lsLvl.get(i).size(); j++) {
				Table t = lsLvl.get(i).get(j);
				
				DefaultMutableTreeNode tmpTable = new DefaultMutableTreeNode( t.getTableName() );
				root.add( tmpTable );
				
				for(String str : t.getLinkedTable()) {
					DefaultMutableTreeNode tmpCol = new DefaultMutableTreeNode( str );
					tmpTable.add(tmpCol);				
					
				}

		}

		orga.addTreeSelectionListener(new MyTreeSelectionListener());

		orga.setCellRenderer(new MyCellIterator());
		orga.setBounds(10, 10, App.frame.getContentPane().getWidth(), App.frame.getContentPane().getHeight());
		orga.setFont(new Font(orga.getFont().getFamily(), Font.BOLD, 15));
		add(orga);
		
		
		
		
		
		
		
		
		}
		
		
		
		
		
		
		this.btn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				ViewPrincipal.getLateral().updateNbRestant();
				
				ViewPrincipal.getLateral().setEnabled(true);
				ViewPrincipal.getLateral().setVisible(true);
				
				ViewPrincipal.getContent().setEnabled(true);
				ViewPrincipal.getContent().setVisible(true);
				
				ViewPrincipal.getModif().setVisible(false);
				ViewPrincipal.getModif().setEnabled(false);
							
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
				
				
				
			}
		
		});
		this.okay.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				getColumn().setValidated(true);
				
				ViewPrincipal.getLateral().updateNbRestant();
				
				ViewPrincipal.getLateral().setEnabled(true);
				ViewPrincipal.getLateral().setVisible(true);
				
				ViewPrincipal.getContent().setEnabled(true);
				ViewPrincipal.getContent().setVisible(true);
				
				ViewPrincipal.getModif().setVisible(false);
				ViewPrincipal.getModif().setEnabled(false);
							
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
			}
		
		});
		
		repaint();
		revalidate();
		App.frame.repaint();
		App.frame.revalidate();
		
		
	
	}


	public static void LoadCol() {
		Pane p = ViewPrincipal.getModif();
		p.removeAll();
		
		p.add(column.getFaked());
		p.add(p.btn);
		p.add(p.okay);

		p.repaint();
		p.revalidate();
		
		
	}

	private void LoadDiv() {
		removeAll();
		int i = 0;
		
		for(Column c : tableSelected.getLstColumn()) {
			if( !(c.getIsConstrained()) && !(c.getIsPrimary()) ) {
				DivColumn j = new DivColumn(c);
				j.add(new Lab(c.getName()));
				ViewPrincipal.getContent().add(j);
				
			}
		}
	}

	class MyTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
			if( tmp.getUserObject() instanceof Table ) {
				tableSelected = (Table) tmp.getUserObject();
				ViewPrincipal.getContent().LoadDiv();
				ViewPrincipal.getContent().repaint();
				ViewPrincipal.getContent().revalidate();
			}
		}
	}
	
	class MyCellRenderer extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus){
		    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		    if(node.getChildCount() == 0) {
		      this.setEnabled(false);
		      this.setDisabledIcon(this.getDefaultLeafIcon());
		    }
		    return this;
		}
	}

	class MyCellIterator extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus){
		    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		    if(node.getChildCount() == 0) {
		      this.setDisabledIcon(this.getDefaultLeafIcon());
		    }
		    return this;
		}
	}
	
	public static Table getTableSelected() {
		return tableSelected;
	}

	public static void setTableSelected(Table tableSelected) {
		Pane.tableSelected = tableSelected;
	}

	public static Column getColumn() {
		return column;
	}

	public static void setColumn(Column column) {
		Pane.column = column;
	}
	
	public Database getDb() {
		return db;
	}

	public void updateNbRestant() {
		this.nbRestant.setText("Il reste " + this.db.checkNb() + " colonnes à configurer.");
	}

}
