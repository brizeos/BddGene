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
import fakery.FakeModel;
import fakery.Faked;
import model.Column;
import model.DaoAccess;
import model.Database;
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
    	
    	String login = App.frame.getJt1().getText();
    	String mdp = App.frame.getJt2().getText();
    	String url = App.frame.getJt3().getText();
    	
    	App.dao = new DaoAccess(url, "information_schema", login, mdp, null);
    	
    	App.dao.connect();
    	
    	App.dao.setPreparedStatement(sql);
    	rs = App.dao.getPreparedStatement().executeQuery();
    	while(rs.next()) {
    		dcm.addElement(rs.getString(1));
    	}
    	App.dao.disconnect();
 
    	return dcm;
    	
    }
	
	/***
	 * Load database selected by user and show the ViewPrincipal.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void loadDB() throws SQLException, IOException {
		
		@SuppressWarnings("unused")
		int i = 0, j = 0;
		String sqlQueryDistinctTable = "SELECT DISTINCT `TABLE_NAME` FROM `TABLES` WHERE `TABLE_SCHEMA` = ?";
		String sqlQueryAllColumns = "SELECT * FROM `COLUMNS` WHERE `TABLE_SCHEMA` = ? AND `TABLE_NAME` = ?;";
		
		ResultSet rs = null;
		App.db.setName(App.cs.getModel().getSelectedItem().toString());
		
		App.dao.connect();
    	
		App.dao.setPreparedStatement(sqlQueryDistinctTable);
		App.dao.getPreparedStatement().setString(1, App.cs.getModel().getSelectedItem().toString());
    	
    	rs = App.dao.getPreparedStatement().executeQuery();
    	App.db.setLstTable(new ArrayList<Table>());
    	
    	//Récupere les tables
    	while(rs.next()) {
    		
    		ResultSet rsBis = null;
    		
    		App.db.getLstTable().add(new Table());	   
    		App.db.getLstTable().get(i).setTableName(rs.getString("TABLE_NAME"));
    		App.db.getLstTable().get(i).setLstColumn(new ArrayList<Column>());
    		
    		App.dao.setPreparedStatement(sqlQueryAllColumns);
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
    			j++;
    		}

	    	App.dao.disconnect();
	    	App.frame.getContentPane().removeAll();
	    	App.frame.getContentPane().add(new ViewPrincipal());
	    	
	    	App.frame.repaint();
	    	App.frame.revalidate();
    	}
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
	
	/***
	 * Method to launch write sequance in MySql database.
	 * @param t Table to generate.
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public static void insertSql(Table t) throws SQLException {
		
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
		App.dao.setPreparedStatement(sql);
		/**
		 * Execution nombre de fois précisé par les options/demande utilisateur
		 */
		for (int j = 0; j < t.getNb() ; j++) {
			/**
			 * Ecriture de la data Faker || id de clé étrangère
			 */
			int i = 1;
			for (String str : lsStrColNameToInsert) {
				for (Column v : t.getLstColumn()) {
					if (v.getName().equals(str)) {
						( (FakeModel) ((Faked) v.getFaked()).getFakeOption()).Launch() ;
						ResearchSql.DataStatement( i, v );
						i++;
					}
				}
			}
			
			try {
				App.dao.getPreparedStatement().executeUpdate();
				t.increment();
			}catch(Exception e) {
				System.out.println("Error Catched!");
			}
	
		}
	}	
}
		


