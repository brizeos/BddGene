package model;

import java.awt.Component;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import MavenBdd.Generator.App;
import fakery.Faked;

public class Column {

	private String name, type;
	private int val;
	private Boolean isConstrained, isPrimary;
//	private ArrayList<Constraint> lstCons = new ArrayList<Constraint>();
	private Faked f;
	private String data;
	private boolean validated, nullAccepted;
	private Table table;
	private Boolean leveled;
	
	
	public Column(String name, String type, int val, boolean isPrimary,boolean nullAccepted, Table table) throws SQLException, IOException {
		super();
		this.data = "";
		
		this.name = name;
		this.type = type;
		this.val = val;
		this.isConstrained = false;
		this.isPrimary = isPrimary;
		this.leveled = true;
//		this.lstCons = new ArrayList<Constraint>();
		this.setValidated(false);
		this.setTable(table);
		
		
		
		String str = "SELECT * FROM `KEY_COLUMN_USAGE` WHERE `TABLE_NAME`= ? AND `TABLE_SCHEMA`= ? AND `COLUMN_NAME` = ? ;";
		App.dao.setPreparedStatement(str);
		App.dao.getPreparedStatement().setString(1, table.getTableName());
		App.dao.getPreparedStatement().setString(2, App.getDBName());
		App.dao.getPreparedStatement().setString(3, this.name);
		
		ResultSet rs = null;
		
		rs = App.dao.getPreparedStatement().executeQuery();
		
		while(rs.next()) {
			this.isConstrained = true;
		}
		
		
		this.f = new Faked(this);
		
		
		
		
		
	}


	public Column() {
		this.name = "null";
	
	}
	


	public String toString() {
		return this.getName();
	}
	
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


	public void setIsConstrained(Boolean isConstrained) {
		this.isConstrained = isConstrained;
		this.leveled = false;
	}


//	public ArrayList<Constraint> getLstCons() {
//		return lstCons;
//	}
//
//
//	public void setLstCons(ArrayList<Constraint> lstCons) {
//		this.lstCons = lstCons;
//	}


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
	
	
	
	
	
}
