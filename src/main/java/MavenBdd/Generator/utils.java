package MavenBdd.Generator;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JPanel;

import fakery.FakeModel;
import fakery.Faked;
import model.Column;
import model.Database;
import model.Table;
import sql.ResearchSql;
import vue.ViewPrincipal;

public class utils {

	public static ArrayList< ArrayList<Table>  > lsLvl;
	
	public static void LaunchDiff() throws SQLException {
		boolean flag = true;

		Database db = ViewPrincipal.getLateral().getDb();

		lsLvl = new ArrayList<ArrayList<Table>>();
		lsLvl.add(new ArrayList<Table>());

		int i = 0;

		while (flag) {
			// Pour chaque table
			for ( Table t : db.getLstTable()) {
				// Si elle est contrainte par une clé étrangère
				if(t.isConstrained() && i!=0) {
					// Pour chacune de ces colonnes
					for ( Column c : t.getLstColumn() ) {
						// Si c'est une clé étrangère
						if(c.getIsConstrained()) {
							String toCheck = t.getTableName()+"."+c.getName();
							// Regarde chaque table au niveau -1
							for (Table lsTable : lsLvl.get(i-1)) {
								// Chaque colonne
								for (Column col : lsTable.getLstColumn()) {
									//Si elle est primaire
									if(col.getIsPrimary()) {
										//Création de la clé de comparaison "tableName.ColumnName"
										String checkedCol = lsTable.getTableName()+"."+col.getName();
										
										//Pour chaque enregistrement de contrainte 
										for(String str : t.getLinkedTable()) {
											//Si c'est elle Alors on l'ajouter au niveau en cours
											if( !c.isLeveled() &&  str.equals(checkedCol)) {
												
												
												c.setLeveled(true);
												
												if(c.isLeveled())
													lsLvl.get(i).add(t);

												
												if (t.isAllLeveled()) {
													t.setLeveled(true);
												}
											}
										}
										
//										for (Constraint cons : c.getLstCons()) {
//											if(cons.getPrimary().equals(checkedCol)) {
//												lsLvl.get(i).add(t);
//												t.setLeveled(true);
//											}
//										}
									}
								}
							}
						}  
					}
					//Si il n'y a pas de contraintes -> Ajout au niveau 0
				}else if( !t.isConstrained() && i==0 ){
					lsLvl.get(0).add( t );
					t.setLeveled(true);
				}
			}
			
			//Si toute les table sont gérées on quite
			if (db.checkLvlAll() ) {
				flag = false;
				break;
			}else {
				//Sinon on ajoute un niveau suplémentaire
				lsLvl.add(new ArrayList<Table>());
			}
			i++;
		}
		int j=0;
		
		
		//Pur Affichage
		for (ArrayList<Table> al : lsLvl) {

			System.out.println("\nNiveau " + j);
			for (Table t : al) {
				System.out.println(t.getTableName());
			}
			j++;
		}
		
		
		//Création de la vue suivante
		controler.Control.LaunchViewIteration(lsLvl);

	}

	//TODO A déplacer...
	public static void startGeneration() throws SQLException {
		for(int i = lsLvl.size()-1 ; i >= 0 ; i--) {	
			for ( Table t : lsLvl.get(i)) {
				if(!t.isDone())
					ResearchSql.recurTable( t, i, lsLvl );
			}
		}	
	}
	
	
	
	
	



	

	
	public static ArrayList<JPanel> showIterations(ArrayList<ArrayList<Table>> lsLvl){
		
		ArrayList<JPanel> lsPane = new ArrayList<JPanel>();
		
		
		
		
		return lsPane;
		
	}
	
	
	public static Table returnTable(String str) {
		
		for (Table t : App.db.getLstTable()) {
			if(t.getTableName().equals(str)) {
				return t;
			}
		}
		return null;
		
	}
	
	
}
