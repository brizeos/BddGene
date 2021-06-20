package sql;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import MavenBdd.Generator.App;
import fakery.FakeModel;
import fakery.Faked;
import model.Column;
import model.DaoAccess;
import model.Table;
import vue.ViewPrincipal;


/***
 * @author Brizeos
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 */
public class ResearchSql {

	/***
	 * Load Table names in the ComboSelecter in connection Panel.</br>
	 * Use JTextfields value returned by user in connection panel.
	 * @return	ComboBoxModel who contains all table return by MySql Query
	 * @throws SQLException
	 */
	public static DefaultComboBoxModel<String> loadTables() throws SQLException {
		
		String sql = "SELECT DISTINCT `TABLE_SCHEMA` FROM `TABLES` WHERE `TABLE_TYPE`='BASE TABLE' AND `TABLE_SCHEMA` NOT IN ('mysql', 'performance_schema', 'sys', 'syscom');";
		ResultSet rs = null;
		DefaultComboBoxModel<String> dcm = new DefaultComboBoxModel<String>();
    	
    	String login = App.getConn().getJt1().getText();
    	String mdp = App.getConn().getJt2().getText();
    	String url = App.getConn().getJt3().getText();
    	
    	App.setDao(new DaoAccess(url, "information_schema", login, mdp, null));
    	
    	App.getDao().connect();
    	
    	App.getDao().setPreparedStatement(sql);
    	rs = App.getDao().getPreparedStatement().executeQuery();
    	while(rs.next()) {
    		dcm.addElement(rs.getString(1));
    	}
    	App.getDao().disconnect();
 
    	return dcm;
    	
    }
	
	/***
	 * Load database selected by user and show the ViewPrincipal.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void loadDB() throws SQLException, IOException {
		
		
		int i = 0;
		String sqlQueryDistinctTable = "SELECT DISTINCT `TABLE_NAME` FROM `TABLES` WHERE `TABLE_SCHEMA` = ?";
		String sqlQueryAllColumns = "SELECT * FROM `COLUMNS` WHERE `TABLE_SCHEMA` = ? AND `TABLE_NAME` = ?;";
		
		ResultSet rs = null;
		App.getDb().setName(App.getConn().getCs().getModel().getSelectedItem().toString());
		
		App.getDao().connect();
    	
		App.getDao().setPreparedStatement(sqlQueryDistinctTable);
		App.getDao().getPreparedStatement().setString(1, App.getConn().getCs().getModel().getSelectedItem().toString());
    	
    	rs = App.getDao().getPreparedStatement().executeQuery();
    	App.getDb().setLstTable(new ArrayList<Table>());
    	
    	//Récupere les tables
    	while(rs.next()) {
    		
    		ResultSet rsBis = null;
    		
    		App.getDb().getLstTable().add(new Table());	   
    		App.getDb().getLstTable().get(i).setTableName(rs.getString("TABLE_NAME"));
    		App.getDb().getLstTable().get(i).setLstColumn(new ArrayList<Column>());
    		
    		App.getDao().setPreparedStatement(sqlQueryAllColumns);
    		App.getDao().getPreparedStatement().setString(1, App.getConn().getCs().getModel().getSelectedItem().toString());
    		App.getDao().getPreparedStatement().setString(2, rs.getString("TABLE_NAME"));
    		
    		rsBis = App.getDao().getPreparedStatement().executeQuery();
    		while(rsBis.next()) {
    			//Récupere les colonnes
    			
    			App.getDb().getLstTable().get(i).getLstColumn().add( new Column(	rsBis.getString("COLUMN_NAME"),
												    					rsBis.getString("DATA_TYPE"),
												    					rsBis.getInt("CHARACTER_MAXIMUM_LENGTH"),
												    					(rsBis.getString("COLUMN_KEY").equals("PRI") && rsBis.getString("COLUMN_KEY") != null? true : false),
												    					((rsBis.getString("IS_NULLABLE").equals("YES") ? true : false)),
												    					App.getDb().getLstTable().get(i) ) );
    		}
    		i++;
    	}
    	
	    	App.getDao().disconnect();
	    	App.getFrame().getContent().removeAll();
	    	App.getFrame().setWidth(1900);
	    	App.getFrame().setHeight(980);
	    	App.getFrame().setLocationRelativeTo(null);
	    	
	    	App.getFrame().getContent().add(new ViewPrincipal());
	    	
	    	App.getFrame().repaint();
	    	App.getFrame().revalidate();
	    	
	}
	
	/***
	 * Modify a preparedStatement with method depending on column type.
	 * @param i			Index of the prepareStratement to modify
	 * @param column	Column to check
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static void DataStatement(int i, Column column) throws NumberFormatException, SQLException {
		Date d = Date.valueOf(LocalDate.now());
		
		if( column.getType().matches("(?i)TINYINT|SMALLINT|MEDIUMINT|INT|BIGINT") ) {
			App.getDao().getPreparedStatement().setInt(  i , Integer.parseInt(column.getData()) );
		}else if(column.getType().matches("(?i)DOUBLE|DECIMAL|REAL") ){
			App.getDao().getPreparedStatement().setDouble(  i , Double.parseDouble(column.getData()) );
		}else if(column.getType().matches("(?i)FLOAT") ){
			App.getDao().getPreparedStatement().setFloat(  i , Float.parseFloat(column.getData()) );
		}else if(column.getType().matches("(?i)TINYBLOB|MEDIUMBLOB|LONGBLOB|BLOB") ){
			System.out.println("Blob error!");
		}else if(column.getType().matches("(?i)TINYTEXT|MEDIUMTEXT|TEXT|LONGTEXT|VARCHAR") ){
			App.getDao().getPreparedStatement().setString(  i , column.getData() );
		}else if(column.getType().matches("(?i)YEAR") ){
			d = Date.valueOf( column.getData());
			SimpleDateFormat sdf = new SimpleDateFormat("Y");
			sdf.format(d);
			App.getDao().getPreparedStatement().setDate(  i , d );
		}else if(column.getType().matches("(?i)DATE") ){
			d = Date.valueOf( column.getData());
			App.getDao().getPreparedStatement().setDate(  i , d );
		}else if(column.getType().matches("(?i)DATETIME") ){
			SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d hh:mm:ss");
			sdf.format(d);
			d = Date.valueOf( column.getData());
			App.getDao().getPreparedStatement().setDate(  i , d );
		}else if(column.getType().matches("(?i)TIME") ){
			Time time = Time.valueOf( column.getData());
			App.getDao().getPreparedStatement().setTime(  i , time );
		}else if(column.getType().matches("(?i)TIMESTAMP") ){
			Timestamp timestamp = new Timestamp(Date.valueOf( column.getData()).getTime());
			App.getDao().getPreparedStatement().setTimestamp(  i , timestamp );
			
		}else {
			System.out.println("Error!");
		}
	}
	

	
	
	
	
	/***
	 * Method to launch write sequance in MySql database.
	 * @param t Table to generate.
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public static void insertSql(Table t) throws SQLException {
		ArrayList<String> colDone = new ArrayList<String>();
		ArrayList<String> lsStrColNameToInsert = new ArrayList<String>(); 
		
		/**
		 * Create begining of the sqlQuery  col == (!Primary || multiPass)
		 */
		String sql = "INSERT INTO `"+App.getDBName()+"`.`"+t.getTableName()+"`(";
		int nbCol = 0;
		boolean first = true;
		for (Column c : t.getLstColumn()) {
			if(!c.getIsPrimary() || c.isMultiPass() ) {
				sql += ( first ? "" : "," ) + "`"+c.getName()+"`";
				first = false;
				nbCol++;
				lsStrColNameToInsert.add(c.getName());
			}
		}
		
		sql+= ") VALUES (";
		first = true;
		
		
		for (String str : lsStrColNameToInsert ) {
			sql += ( first ? "" : "," ) + " ?";
			first = false;
		}
		
		sql += ");";
		App.getDao().setPreparedStatement(sql);
		/**
		 * Execution nombre de fois précisé par les options/demande utilisateur
		 */
		
		System.out.println(t.getLinkedTable().size());
		
		for (int j = 0; j < t.getNb() ; j++) {
			/**
			 * Ecriture de la data Faker || id de clé étrangère
			 */
			colDone.clear();
			int i = 1;
	
			
			
			
			for (String str : lsStrColNameToInsert) {
				for (Column col : t.getLstColumn()) {
					if (col.getName().equals(str) && !colDone.contains(col.getName())) {
			
						( (FakeModel) ((Faked) col.getFaked()).getFakeOption()).Launch();
						ResearchSql.DataStatement( i, col );
						i++;
						
						colDone.add(col.getName());
						
						if(col.getListPrimaryKeyLinked().size() != 0) {
							System.out.println(1);
							for (String colToLaunch : col.getListPrimaryKeyLinked()) {
								System.out.println(colToLaunch);
								for (Table table : App.getDb().getLstTable()) {
									
									if(table.getTableName().equals(  colToLaunch.subSequence(0, colToLaunch.indexOf("."))  )) {
										System.out.println(table.getTableName() + " Trouvé");
										Column targetCol = table.getThisColumn(colToLaunch.substring(colToLaunch.indexOf(".")+1));
										System.out.println("index = "+lsStrColNameToInsert.indexOf(targetCol.getName()) + " pour " + targetCol.getName());
										
										int spec = targetCol.craftSpecificKey(colToLaunch, col.getName(), col.getData(), col);
										
										App.getDao().getPreparedStatement().setInt(  lsStrColNameToInsert.indexOf(targetCol.getName())+1 , spec );
										colDone.add(targetCol.getName());
										lsStrColNameToInsert.remove(colToLaunch);
									}
								}
							}
						}
					}
				}
			}
			try {
				App.getDao().getPreparedStatement().executeUpdate();
				t.increment();
			}catch(Exception e) {
				System.out.println(App.getDao().getPreparedStatement().toString());
				System.out.println("Error Catched!");
			}
	
		}
	}
	


//	static int nbContraints;
	private static boolean verif(String str, Table current, ArrayList<String> lsStrColNameToInsert, int i) throws NumberFormatException, SQLException {
		return false;
//		
//		nbContraints = 0;
//		
//		System.out.println("-------------------");
//		System.out.println("Str :"+str);
//		System.out.println("Table :"+current.getTableName());
//		System.out.println(lsStrColNameToInsert);
//		
//		System.out.println("-------------------");
//		//Pour chaque autre table qui n'est pas celle actuel
//		
//		
//		
//		current.getLstColumn().forEach( (Column t) -> {
//			if(t.getIsConstrained())
//				nbContraints++;
//		});
//		
//		System.out.println(nbContraints);
//		System.out.println(current.getLinkedTable().size());
//		if(current.getLinkedTable().size() > nbContraints) {
//
//			for (Table t : App.getDb().getLstTable()) {
//				System.out.println(t.getTableName());
//				if(!t.getTableName().equals(current)) {
//					//Pour chaque colonne
//					for (Column c : t.getLstColumn()) {
//						//Si le nom de la colonne est égal au nom de la colonne à éditer et est primaire
//						System.out.println("\t"+c.getName());
//						if(c.getName().equals(str) && c.getIsPrimary()) {
//							//On regarde si une autre colonne est multipass
//							for (Column colMulti : t.getLstColumn()) {
//								System.out.println("\t\t"+colMulti.getName());
//								if(colMulti.isMultiPass()) {
//									int index = 0;
//									//On regarde si elle est présente dans la liste à éditer
//									for (String strLst : lsStrColNameToInsert) {
//										System.out.println("\t\t\t"+strLst);
//										if(strLst.equals(colMulti.getName())) {
//											System.out.println("MERDEUH!!!!");
//											//On fait le traitement sur la colonne en cours 
//											ResearchSql.DataStatement( i, current.getLstColumn().get(i) );
//
//											//On fait le traitement sur la colonne corespondante dans la table en cours
//											ResearchSql.DataStatement( index, c );
//											return true;
//										}
//										index++;
//									}
//
//								}
//
//							}
//
//						}
//
//					}
//
//				}
//			}
//
//		}
//		
//		
//		
//		
//		
////		for (Table t : App.getDb().getLstTable()) {
////			if(!t.getTableName().equals(current)) {
////				for (Column c : t.getLstColumn()) {
////					if(c.getIsPrimary() && str.equals(c.getName()) ) {
////						
////						for (String string : lsStrColNameToInsert) {
////							if(t.getLinkedTable().get(string.substring(string.indexOf(".")+1)) != null) {
////								System.out.println(current.getTableName());
////								ResearchSql.DataStatement(i, current.getLstColumn().get(i-1));
////								
////								String sql = "SELECT `"+c.getName() + "` FROM `"+t.getTableName()+"` WHERE `"+ c.getName() +"` = "+ c.getData() +";";
////								App.getDao().setSecond(sql);
////								ResultSet rs = App.getDao().getSecond().executeQuery();
////								while(rs.next()) {
////									App.getDao().getPreparedStatement().setInt(  lsStrColNameToInsert.indexOf(string) ,  rs.getInt(1) );
////								}
////								return true;
////							}
////						}
////						
////					}
////				}				
////			}
////		}
////		
//		
//		return false;
	}	
}
		


