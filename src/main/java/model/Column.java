package model;

import java.awt.Component;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.github.javafaker.Faker;

import MavenBdd.Generator.App;
import fakery.Faked;

/***
 * @author Brizeos
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 */
public class Column {

	private Boolean isConstrained, isPrimary, validated, nullAccepted, multiPass, leveled;
	private int val;
	private Faked f;
	private Table table;
	private String name, type, data;
	private ArrayList<String> listPrimaryKeyLinked;
	
	/***
	 * Model to build a column
	 * 
	 * @param name			Name of the column
	 * @param type			Type of the column (int, varchar...)
	 * @param val			Value's type (255 is default for varchar)
	 * @param isPrimary		True if the colum is primary
	 * @param nullAccepted	If the column accept "null"
	 * @param table			Reference to column's table
	 * @throws SQLException	
	 * @throws IOException
	 */
	public Column(String name, String type, int val, boolean isPrimary,boolean nullAccepted, Table table) throws SQLException, IOException {
		super();
		this.data = "";
		
		this.name = name;
		this.type = type;
		this.val = val;
		this.isConstrained = false;
		this.isPrimary = isPrimary;
		this.leveled = true;
		this.setMultiPass(false);
		this.setValidated(false);
		this.setTable(table);
		this.listPrimaryKeyLinked = new ArrayList<String>();
		
		
		String str = "SELECT * FROM `KEY_COLUMN_USAGE` WHERE `TABLE_NAME`= ? AND `TABLE_SCHEMA`= ? AND `COLUMN_NAME` = ? AND `CONSTRAINT_NAME` != 'PRIMARY';";
		String sql2 = "SELECT * FROM `KEY_COLUMN_USAGE` WHERE `TABLE_NAME`= ? AND `TABLE_SCHEMA`= ? AND `CONSTRAINT_NAME` = 'PRIMARY';";
		
		
		App.getDao().setPreparedStatement(str);
		App.getDao().getPreparedStatement().setString(1, table.getTableName());
		App.getDao().getPreparedStatement().setString(2, App.getDBName());
		App.getDao().getPreparedStatement().setString(3, this.name);
		
		ResultSet rs = App.getDao().getPreparedStatement().executeQuery();
		System.out.println(this.table.getTableName()+"."+this.name);
		while(rs.next()) {
			
				this.table.getLinkedTable().put(rs.getString("COLUMN_NAME"),  rs.getString("REFERENCED_TABLE_NAME")+"."+rs.getString("REFERENCED_COLUMN_NAME"));
				this.isConstrained = true;
				
				
				App.getDao().setSecond(sql2);
				App.getDao().getSecond().setString(1, rs.getString("REFERENCED_TABLE_NAME"));
				App.getDao().getSecond().setString(2, App.getDBName());
				ResultSet rs2 = App.getDao().getSecond().executeQuery();
				
				String ref = rs.getString("REFERENCED_COLUMN_NAME");
				
				while(rs2.next()) {
					if( !rs2.getString("COLUMN_NAME").equals(ref)) {
						this.listPrimaryKeyLinked.add(rs.getString("REFERENCED_TABLE_NAME") + "." + rs2.getString("COLUMN_NAME"));
					}
				}
				
		}
		System.out.println("Constraint: "+this.listPrimaryKeyLinked);
		
		if(this.isConstrained && this.isPrimary) {
			this.multiPass = true;
		}
		
		this.f = new Faked(this);
		
	}

	/***
	 * Default constructor
	 */
	public Column() {
		this.name = "null";
	}
	
	/**
	 * @return This column name
	 */
	public String toString() {
		return this.getName();
	}
	
	
	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public Boolean getIsConstrained() {
		return isConstrained;
	}

	/**
	 * Default leveled is true. But if on building this column is detected as constrained we need to put false.
	 * 
	 * @param isConstrained Boolean to set
	 */
	public void setIsConstrained(Boolean isConstrained) {
		this.isConstrained = isConstrained;
		this.leveled = false;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Component getFaked() {
		return this.f;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ArrayList<String> getListPrimaryKeyLinked() {
		return listPrimaryKeyLinked;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public boolean isNullAccepted() {
		return nullAccepted;
	}

	public void setNullAccepted(boolean nullAccepted) {
		this.nullAccepted = nullAccepted;
	}

	public Boolean isLeveled() {
		return leveled;
	}
	
	public void setLeveled(boolean bool) {
		this.leveled = bool;
	}

	public boolean isMultiPass() {
		return multiPass;
	}


	public void setMultiPass(boolean multiPass) {
		this.multiPass = multiPass;
	}
	
	public int craftSpecificKey(String ref, String main, String val, Column col) throws SQLException {

		int id = Integer.parseInt(val);
		ArrayList<Integer> listKey = new ArrayList<Integer>();
		String sqlSpec = "SELECT `" + ref.substring(ref.indexOf(".")+1) + "` FROM `"+ref.substring(0, ref.indexOf(".")) + "` WHERE `"+ main + "` = " + id +";";
		App.getDao().setTri(sqlSpec);
		
		ResultSet rs = App.getDao().getTri().executeQuery();
		while(rs.next()) {
			listKey.add(rs.getInt(1));
		}
		
		return listKey.get( Faker.instance().number().numberBetween(0, listKey.size()-1)  );
	}
	
}
