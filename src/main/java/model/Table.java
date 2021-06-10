package model;

import java.util.ArrayList;

public class Table {

	private String tableName;
	private ArrayList<Column> lstColumn;
	private boolean isConstrained, leveled, done;
	private int nb;
	
	
	public Table() {
		super();
		this.lstColumn = new ArrayList<Column>();
		this.leveled = false;
		this.isConstrained = false;
		this.setDone(false);
		this.nb = 1; //TODO zero
	}
	
	
	
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<Column> getLstColumn() {
		return lstColumn;
	}

	public void setLstColumn(ArrayList<Column> lstColumn) {
		this.lstColumn = lstColumn;
	}

	public boolean isConstrained() {
		return isConstrained;
	}




	public void setConstrained(boolean isConstrained) {
		this.isConstrained = isConstrained;
	}




	public boolean isLeveled() {
		return leveled;
	}




	public void setLeveled(boolean leveled) {
		this.leveled = leveled;
	}




	public boolean isDone() {
		return done;
	}




	public void setDone(boolean done) {
		this.done = done;
	}




	public String toString() {
		return this.getTableName();
	}




	public int getNb() {
		// TODO Auto-generated method stub
		return this.nb;
	}
	public void setNb(int nb) {
		this.nb = nb;
	}

}
