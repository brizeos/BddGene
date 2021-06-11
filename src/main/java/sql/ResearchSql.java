package sql;

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
import MavenBdd.Generator.utils;
import fakery.FakeModel;
import fakery.Faked;
import model.Column;
import model.DaoAccess;
import model.Table;
import vue.ViewPrincipal;

public class ResearchSql {

	
	public static DefaultComboBoxModel<String> loadTables(App app) throws SQLException {
		
		String sql = "SELECT DISTINCT `TABLE_SCHEMA` FROM `TABLES` WHERE `TABLE_TYPE`='BASE TABLE' AND `TABLE_SCHEMA` NOT IN ('mysql', 'performance_schema', 'sys', 'syscom');";
		ResultSet rs = null;
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
    	
    	String login = app.getJt1().getText();
    	String mdp = app.getJt2().getText();
    	String url = "localhost:"+ app.getJt3().getText();
    	
    	App.dao = new DaoAccess(url, "information_schema", login, mdp, null);
    	
    	App.dao.connect();
    	
    	App.dao.setPreparedStatement(sql);
    	rs = App.dao.getPreparedStatement().executeQuery();
    	while(rs.next()) {
    		str.addElement(rs.getString(1));
    
    	}
    	App.dao.disconnect();
 
    	return str;
    	
    }
	
	
	public static void loadDB() throws SQLException, IOException {
		
		int i = 0, j = 0;
		String sql = "SELECT DISTINCT `TABLE_NAME` FROM `TABLES` WHERE `TABLE_SCHEMA` = ?";
		
		ResultSet rs = null;
		App.db.setName(App.cs.getModel().getSelectedItem().toString());
		
		App.dao.connect();
    	
		App.dao.setPreparedStatement(sql);
		App.dao.getPreparedStatement().setString(1, App.cs.getModel().getSelectedItem().toString());
    	
    	rs = App.dao.getPreparedStatement().executeQuery();
    	App.db.setLstTable(new ArrayList<Table>());
    	
    	//Récupere les tables
    	while(rs.next()) {
    		
    		ResultSet rsBis = null;
    		
    		App.db.getLstTable().add(new Table());	   
    		App.db.getLstTable().get(i).setTableName(rs.getString("TABLE_NAME"));
    		App.db.getLstTable().get(i).setLstColumn(new ArrayList<Column>());
    		

    		
    		String sql2 = "SELECT * FROM `COLUMNS` WHERE `TABLE_SCHEMA` = ? AND `TABLE_NAME` = ?;";
    		App.dao.setPreparedStatement(sql2);
    		App.dao.getPreparedStatement().setString(1, App.cs.getModel().getSelectedItem().toString());
    		App.dao.getPreparedStatement().setString(2, rs.getString("TABLE_NAME"));
    		
    		rsBis = App.dao.getPreparedStatement().executeQuery();
    		while(rsBis.next()) {
    			//Récupere les colonnes
    			j = 0;
    			App.db.getLstTable().get(i).getLstColumn().add( new Column(	rsBis.getString("COLUMN_NAME"),
												    					rsBis.getString("DATA_TYPE"),
												    					rsBis.getInt("CHARACTER_MAXIMUM_LENGTH"),
												    					(rsBis.getString("COLUMN_KEY").equals("PRI") && rsBis.getString("COLUMN_KEY") != null? true : false),
												    					((rsBis.getString("IS_NULLABLE").equals("YES") ? true : false)),
												    					App.db.getLstTable().get(i) ) );


	    		
//    			App.db.getLstTable().get(i).getLstColumn().get(j).setLstCons(new ArrayList<Constraint>());
    			ResultSet rsCons = null;
    			
    			String sql3 = "SELECT * FROM `KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = ? AND `TABLE_NAME`= ? AND `COLUMN_NAME`= ? AND `REFERENCED_COLUMN_NAME` IS NOT null;";
    			App.dao.setPreparedStatement(sql3);
    			App.dao.getPreparedStatement().setString(1, App.cs.getModel().getSelectedItem().toString());
    			App.dao.getPreparedStatement().setString(2, rs.getString("TABLE_NAME"));
    			App.dao.getPreparedStatement().setString(3, rsBis.getString("COLUMN_NAME"));
	    		
	    		rsCons = App.dao.getPreparedStatement().executeQuery();
	    		
	    		while(rsCons.next()) {
	    			
//	    			App.db.getLstTable().get(i).getLstColumn().get(j).getLstCons().add(  new Constraint( 
//	    					rsCons.getString("TABLE_NAME")+"."+rsCons.getString("COLUMN_NAME") , 
//	    					rsCons.getString("REFERENCED_TABLE_NAME")+"."+rsCons.getString("REFERENCED_COLUMN_NAME"),
//	    					App.db.getLstTable().get(i).getLstColumn().get(j))  );
	    			App.db.getLstTable().get(i).getLinkedTable().add(rsCons.getString("REFERENCED_TABLE_NAME")+"."+rsCons.getString("REFERENCED_COLUMN_NAME"));
	    			App.db.getLstTable().get(i).getLstColumn().get(j).setIsConstrained(true);
	    			App.db.getLstTable().get(i).setConstrained(true);
	    		}
	    		
    			j++;
    		}
    		
    		i++;
    	}
    	App.dao.disconnect();
    	
    	App.frame.getContentPane().removeAll();
    	App.frame.getContentPane().add(new ViewPrincipal());
    	
    	App.frame.repaint();
    	App.frame.revalidate();
	}


	public static void insertIn() {
		
	}
	
	
	public static void DataStatement(int i, Column column) throws NumberFormatException, SQLException {
		Date d = Date.valueOf(LocalDate.now());
		
		if( column.getType().matches("(?i)TINYINT|SMALLINT|MEDIUMINT|INT|BIGINT") ) {
			App.dao.getPreparedStatement().setInt(  i , Integer.parseInt(column.getData()) );
		}else if(column.getType().matches("(?i)DOUBLE|DECIMAL|REAL") ){
			App.dao.getPreparedStatement().setDouble(  i , Double.parseDouble(column.getData()) );
		}else if(column.getType().matches("(?i)FLOAT") ){
			App.dao.getPreparedStatement().setFloat(  i , Float.parseFloat(column.getData()) );
		}else if(column.getType().matches("(?i)TINYBLOB|MEDIUMBLOB|LONGBLOB|BLOB") ){
			System.out.println("Blob error!");
		}else if(column.getType().matches("(?i)TINYTEXT|MEDIUMTEXT|TEXT|LONGTEXT|VARCHAR") ){
			App.dao.getPreparedStatement().setString(  i , column.getData() );
		}else if(column.getType().matches("(?i)YEAR") ){
			d = Date.valueOf( column.getData());
			SimpleDateFormat sdf = new SimpleDateFormat("Y");
			sdf.format(d);
			App.dao.getPreparedStatement().setDate(  i , d );
		}else if(column.getType().matches("(?i)DATE") ){
			d = Date.valueOf( column.getData());
			App.dao.getPreparedStatement().setDate(  i , d );
		}else if(column.getType().matches("(?i)DATETIME") ){
			SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d hh:mm:ss");
			sdf.format(d);
			d = Date.valueOf( column.getData());
			App.dao.getPreparedStatement().setDate(  i , d );
		}else if(column.getType().matches("(?i)TIME") ){
			Time time = Time.valueOf( column.getData());
			App.dao.getPreparedStatement().setTime(  i , time );
		}else if(column.getType().matches("(?i)TIMESTAMP") ){
			Timestamp timestamp = new Timestamp(Date.valueOf( column.getData()).getTime());
			App.dao.getPreparedStatement().setTimestamp(  i , timestamp );
			
		}else {
			System.out.println("Error!");
		}
		
		
	}

	
public static void recurTable(Table t, int i, ArrayList<ArrayList<Table>> lsLvl) throws SQLException {
		
	/**
	 * Else{} -> Si aucun dépendences
	 */
		if(i!=0) {
			/**
			 * Pour chaque table niveau -1 -> Potentiellement dépendentes
			 */
			for ( Table tableBefore: lsLvl.get( --i ) ) {
				
				/**
				 * Check chaque colonnes
				 */
				

				
				
				for (Column  c : t.getLstColumn()) {
					
					
					if(c.getIsPrimary()) {
						for (String str : tableBefore.getLinkedTable()) {
							if ( (t.getTableName()+"."+c.getName()).equals(str)   ) {
								
								recurTable(tableBefore, i, lsLvl);
								System.out.println(t.getTableName());
								
							}
						}
						
//						for (Constraint cons : c.getLstCons()) {
//							
//							if(cons.getPrimary().contains( tableBefore.getTableName() ) ) {
//								
//								insertSql(t, tableBefore, lsLvl);
//								
//								t.setDone(true);
//								System.out.println(t.getTableName());
//								
//							}
//						}
					}
				}
			}
		} else {
			insertSql(t, null, lsLvl);
			System.out.println("else --- " + t.getTableName());
			t.setDone(true);
		}
}	


	public static void insertSql(Table t, Table before, ArrayList<ArrayList<Table>> lsLvl) throws SQLException {
		
		/**
		 * Creation du début du sql selon le nombre de colonnes !(FK||Primary)
		 */
		String sql = "INSERT INTO `"+App.getDBName()+"`.`"+t.getTableName()+"`(";
		
		boolean first = true;
		for (Column c : t.getLstColumn()) {
			if(!c.getIsPrimary() ) {
				//sql += ( first ? "" : "," ) + "?";
				sql += ( first ? "" : "," ) + "`"+c.getName()+"`";
				first = false;
			}
			
		}
		
		sql+= ") VALUES (";
		first = true;
		for (Column c : t.getLstColumn()) {
			
			if(!c.getIsPrimary()  ) {
				sql += ( first ? "" : "," ) + " ?";
				first = false;
			}
		}
		
		sql += ");";
		App.dao.setPreparedStatement(sql);

		
		int nbCol = 0;
		for (Column v : t.getLstColumn()) {
			if(  !v.getIsPrimary() ) 
				 nbCol++;
		}
		
		
		for(int i=1 ; i <= nbCol ; i++) {
			final String tmpCol = t.getLstColumn().get(i).getName();
			
			//App.dao.getPreparedStatement().setString(  i , t.getLstColumn().get(i).getName() );
			
			t.getLstColumn().forEach( (Column v) -> {
				if (v.getName().equals(tmpCol)) {
					 ( (FakeModel) ((Faked) v.getFaked()).getFakeOption()).Launch() ;
				}
			});
			
			if(t.getLstColumn().get(i).getIsConstrained()) {
				recurTable(before , i, lsLvl);
				ResearchSql.DataStatement( i, t.getLstColumn().get(i)  );

			}else {
				ResearchSql.DataStatement( i, t.getLstColumn().get(i)  );
				
			}
			
			
		}
		
		System.out.println(App.dao.getPreparedStatement().toString());
		
		
		
		for (int i = 0; i < t.getNb(); i++) {
			
			App.dao.getPreparedStatement().executeUpdate();
			
		}
			
			
	}
		
}

