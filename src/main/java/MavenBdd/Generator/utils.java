package MavenBdd.Generator;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.mysql.cj.jdbc.Blob;

import component.FakeModel;
import component.Faked;
import component.FakedTextOption;
import model.Column;
import model.Constraint;
import model.Database;
import model.Table;
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
			for ( Table t : db.getLstTable()) {
				if(t.isConstrained() && i!=0) {
					for ( Column c : t.getLstColumn() ) {
						if(c.getIsConstrained()) {
							String toCheck = t.getTableName()+"."+c.getName();
							for (Table lsTable : lsLvl.get(i-1)) {
								for (Column col : lsTable.getLstColumn()) {
									if(col.getIsPrimary()) {
										String checkedCol = lsTable.getTableName()+"."+col.getName();
										for (Constraint cons : c.getLstCons()) {
											if(cons.getPrimary().equals(checkedCol)) {
												lsLvl.get(i).add(t);
												t.setLeveled(true);
											}
										}
									}
								}
							}
						}  
					}
				}else if( !t.isConstrained() && i==0 ){
					lsLvl.get(0).add( t );
					t.setLeveled(true);
				}
			}
			if (db.checkLvlAll() ) {
				flag = false;
				break;
			}
			if(flag) {
				lsLvl.add(new ArrayList<Table>());
			}
			i++;
		}
		int j=0;
		for (ArrayList<Table> al : lsLvl) {

			System.out.println("\nNiveau " + j);
			for (Table t : al) {
				System.out.println(t.getTableName());
			}
			j++;
		}
		
		controler.Control.LaunchViewIteration(lsLvl);
//		startGeneration();
	}

	
	public static void startGeneration() throws SQLException {
		System.out.println();
		for(int i = lsLvl.size()-1 ; i >= 0 ; i--) {	
			for ( Table t : lsLvl.get(i)) {
				if(!t.isDone())
					recurTable( t, i );
			}
		}	
	}
	
	
	public static void recurTable(Table t, int i) throws SQLException {
		
		if(i!=0) {
			
			for ( Table tableBefore: lsLvl.get( --i ) ) {
				
				for (Column  c : t.getLstColumn()) {
					//check constraints
					if(c.getIsPrimary()) {
						
						for (Constraint cons : c.getLstCons()) {
							
							if(cons.getPrimary().contains( tableBefore.getTableName() ) ) {
								
								insertSql(t, tableBefore);
								
								t.setDone(true);
								System.out.println(t.getTableName());
								
								
								
								
							}
						}
					}
				}
			}
		} else {
			insertSql(t, null);
			System.out.println("else --- " + t.getTableName());
			t.setDone(true);
		}
		
		
		
	}
	
	public static void insertSql(Table t, Table before) throws SQLException {
		
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
				recurTable(before , i);
				DataStatement( i, t.getLstColumn().get(i)  );

			}else {
				DataStatement( i, t.getLstColumn().get(i)  );
				
			}
			
			
		}
		
		System.out.println(App.dao.getPreparedStatement().toString());
		
		
		
		for (int i = 0; i < t.getNb(); i++) {
			
			App.dao.getPreparedStatement().executeUpdate();
			
		}
		
		
	}



	private static void DataStatement(int i, Column column) throws NumberFormatException, SQLException {
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

	
	public static ArrayList<JPanel> showIterations(ArrayList<ArrayList<Table>> lsLvl){
		
		ArrayList<JPanel> lsPane = new ArrayList<JPanel>();
		
		
		
		
		return lsPane;
		
	}
	
	
}
