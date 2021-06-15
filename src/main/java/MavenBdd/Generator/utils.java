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

	

	
	public static void startGeneration() throws SQLException {

		App.db.getLstTable().forEach((Table table) -> {
			if(!table.isDone()) {
				try {
					ResearchSql.insertSql(table);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				table.setDone(true);
			}
		});
		
		
		//ResearchSql.recurTable( App.db.getLstTable().get(0) );
//		for(int i= 0 ; i<App.db.getLstTable().size() ; i++) {
//			if( db.getLstTable().indexOf(t) < db.getLstTable().size() ) {
//				insertSql(t);
//				t.setDone(true);
//				recurTable(db.getLstTable().get( db.getLstTable().indexOf(t)+1 ) );
//			}
//		}
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
