package model;

import java.util.ArrayList;

public class Database {

	private String name;
	private ArrayList<Table> lstTable;

	
	
	public Database() {
		super();
	
		this.lstTable = new ArrayList<Table>();
		
		
	}



	@Override
	public String toString() {
		return this.getName();
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public ArrayList<Table> getLstTable() {
		return lstTable;
	}



	public void setLstTable(ArrayList<Table> lstTable) {
		this.lstTable = lstTable;
	}



	public int checkNb() {
		int i = 0;
		
		for (Table t : lstTable) {
			for (Column c : t.getLstColumn()) {
				if( !c.getIsConstrained() && !c.getIsPrimary() && !c.isValidated()) 
					i++;
			}
		}
		
		return i;
	}



	public boolean checkLvlAll() {
		for (Table table : lstTable) {
			if(!table.isLeveled())
				return false;
		}
		return true;
	}
	
	
	
	
}
