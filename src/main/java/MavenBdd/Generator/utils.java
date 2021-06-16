package MavenBdd.Generator;

import java.sql.SQLException;

import model.Table;
import sql.ResearchSql;

/***
 * @author Brizeos
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 */
public class utils {

	/***
	 * Lauch Generation of all tables.
	 * @throws SQLException
	 */
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
		
		System.out.println("Done!");

	}	

	/***
	 * @param str Table name.
	 * @return	Table with name in parameter
	 */
	public static Table returnTable(String str) {
		
		for (Table t : App.db.getLstTable()) {
			if(t.getTableName().equals(str)) {
				return t;
			}
		}
		return null;
		
	}
	
	
}
