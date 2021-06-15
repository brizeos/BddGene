package relation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;
import model.Database;
import model.Table;

public class RelationModel extends JPanel{
	
	
	private static boolean is0N, root;
	private Table table;
	private HashMap<String, RelationModel> relationMap;
	
	public RelationModel(Table t) {
		
		this.relationMap = new HashMap<String, RelationModel>();
		this.table = t;
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(1450,250));
		setMaximumSize(new Dimension(1450,250));
		setBackground(Color.yellow);
		
		
		
	}
	
	public HashMap<String, RelationModel> getRelationMap() {
		return relationMap;
	}

	public void setRelationMap(HashMap<String, RelationModel> relationMap) {
		this.relationMap = relationMap;
	}
	
	
	

	public RelationModel(Column c) {
		
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(550,250));
		setMaximumSize(new Dimension(550,250));
		setBackground(Color.green);
		
		
		String str = (c != null ? checkRelation(c) : "root");
		
		if(c != null )
			add( new JLabel(  c.getTable().getLinkedTable().get(c.getName())  ) );
		
		switch(str) {
		
		case"root" : 
			loadGroot();
			break;
		case "0/N":
			loadNullable(c);
			
		
		case "1/N":
			load1N(c);
			break;
		case "N/N":
			loadNN(c);
			break;
		case "Comp":
			loadComp(c);
			break;
			
			
			
		default:
			add(new JLabel("Error"));
		
		
		}
		
		
	}
	
	private void loadComp(Column c) {
		this.add(new JLabel(c.getName()));
		this.add(new JLabel("Composé"));
		
	}

	private void loadNN(Column c) {
		this.add(new JLabel(c.getName()));
		this.add(new JLabel("N/N"));
		
	}

	private void loadGroot() {
		
		this.add(new JLabel("groot"));
		
	}
	
	private void loadNullable(Column c) {
		this.add(new JLabel(c.getName()));
		this.add(new JLabel("0/N"));
		
	}
	
	private void load1N(Column c) {
		this.add(new JLabel(c.getName()));
		this.add(new JLabel("1/N"));
		
	}

	


	
	public static  String checkRelation(Column colCurrent) {
		ArrayList<Table> ls = App.db.getLstTable();
		
		
//		/**
//		 * Is a Groot
//		 */
//		if(colCurrent.getTable().getLinkedTable().size() == 0) {
//			return "root";
//		}
//			
		
		/**
		 * C'est une relation N/N
		 * 
		 */
		
		
		
		
		
	
		if( colCurrent.getTable().getLinkedTable().size() > 1) {
			System.out.println();
			System.out.println(colCurrent.getName());
			for (String str : colCurrent.getTable().getLinkedTable().values()) {
				System.out.println(str);
			}
		
			return "Comp";
			
			
			
//			ArrayList<Column> lsTmp = new ArrayList<Column>();
//			for (Column c : colCurrent.getTable().getLstColumn()) {
//				if (c.isMultiPass())
//					lsTmp.add(c);
//				
//			}
//			if(lsTmp.size() > 1 )
//				
//				
//				
//				//TODO Check here
//				
//			
		}
		
		
		
		
		/**
		 * Relation 1/N || 0/N
		 */
		boolean isRecur = false;
		for (String str : colCurrent.getTable().getLinkedTable().values() ) {
			for (int i = 0; i < ls.size() && !ls.get(ls.indexOf(colCurrent.getTable())).equals(colCurrent.getTable()) ; i++) {
				if(str.contains( ls.get(i).getTableName() ) ) {
					
					for (String str2 : ls.get(i).getLinkedTable().values()) {
						if (str2.contains(colCurrent.getTable().getTableName())) {
							isRecur = true;
						}
					}
				}
			}
		}
		
		if(!isRecur) {
			for (Column c : colCurrent.getTable().getLstColumn()) {
				
			}
			return "1/N";
		}
		
		return null;
		
	}

	public void reload() {
		this.removeAll();
		for (RelationModel rm : this.relationMap.values()) {
			this.add(rm);
		}
		repaint();
		revalidate();
	}

	
}
