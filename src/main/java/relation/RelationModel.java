package relation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;
import model.Table;


/***
 * NOT USED
 * @author Brizeos
 *
 */
public class RelationModel extends JPanel{
	
	
	private static boolean is0N, root;
	private Table table;
	private HashMap<String, RelationModel> relationMap;
	private Column column;
	
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
		
		//System.out.println(column.getName());

		if(c != null )
			add( new JLabel( c.getTable().getLinkedTable().get(c.getName())  ) );
		
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
		
//		System.out.println(colCurrent.getTable().getTableName()+"."+ colCurrent.getName());
//		colCurrent.getTable().getLinkedTable().forEach( (k,v) -> {
//			System.out.println(k + "  -->  " + v);
//		});
//		System.out.println();


		
		//Valeur en rapport//
		String strPrimary = colCurrent.getTable().getLinkedTable().get(colCurrent.getName());
		//str2 de la table concerné par primaire
		String tablePrimaire = strPrimary.substring(0, strPrimary.indexOf("."));
		//Str de la colonne primaire
		String colPrimaire = strPrimary.substring(strPrimary.indexOf(".")+1);
		
		/**
		 * C'est une relation N/N
		 * 
		 */
		System.out.println(strPrimary);
		System.out.println(tablePrimaire);
		System.out.println(colPrimaire);
		
		
		
		/**
		 * Relation 1/N
		 */
		for (Table table : ls) {
//			System.out.println(table.getTableName());
			if(table.getTableName().equals(tablePrimaire)) {
//				System.out.println(2);
				for (Column col : table.getLstColumn()) {
//					System.out.println(3);
					if(col.getName().equals(colPrimaire)) {
//						System.out.println(4);
						if(col.isNullAccepted()) {
							return "0/N";
						}else {
							return "1/N";
						}
					}
				}
			}
		}
		
		

		
		
//		/**
//		 * Relation 1/N || 0/N
//		 */
//		boolean isRecur = false;
//		for (String str : colCurrent.getTable().getLinkedTable().values() ) {
//			for (int i = 0; i < ls.size() && !ls.get(ls.indexOf(colCurrent.getTable())).equals(colCurrent.getTable()) ; i++) {
//				if(str.contains( ls.get(i).getTableName() ) ) {
//					
//					for (String str2 : ls.get(i).getLinkedTable().values()) {
//						if (str2.contains(colCurrent.getTable().getTableName())) {
//							isRecur = true;
//						}
//					}
//				}
//			}
//		}
//		
//		if(!isRecur) {
//			for (Column c : colCurrent.getTable().getLstColumn()) {
//				
//			}
//			return "1/N";
//		}
		
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
