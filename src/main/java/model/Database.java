package model;

import java.util.ArrayList;
import java.util.Collections;

public class Database {

	private String name;
	private ArrayList<Table> lstTable;
	private static int nbFk, indexLST;

	
	
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
	


	public Database sortByLevel() {

		boolean flag = true;
		Table tmp = null;
		while(flag) {
			
			for (indexLST=0 ; indexLST<this.lstTable.size() ; indexLST++) {
				if ( indexLST==0 && !this.lstTable.get(indexLST).getLinkedTable().isEmpty() ) {
					tmp = this.lstTable.get(indexLST);
					this.lstTable.remove(indexLST);
					this.lstTable.add( tmp);
					
				}else if(!this.lstTable.get(indexLST).getLinkedTable().isEmpty()) {
					
					nbFk = this.lstTable.get(indexLST).getLinkedTable().size();
					
					this.lstTable.get(indexLST).getLinkedTable().forEach( (k, v) -> {
						for(int indexBack=0 ; indexBack<indexLST ; indexBack++) {
							if(v.contains(this.lstTable.get(indexBack).getTableName())){
								nbFk--;
							}
						}
					});
					
					if(nbFk!=0) {
						tmp = this.lstTable.get(indexLST);
						this.lstTable.remove(indexLST);
						this.lstTable.add(tmp);
					}
					
				}else if(this.lstTable.get(indexLST).getLinkedTable().isEmpty()) {
					
					for(int indexBack=0 ; indexBack<indexLST ; indexBack++) {
						if(!this.lstTable.get(indexBack).getLinkedTable().isEmpty()) {
							tmp = this.lstTable.get(indexLST);
							this.lstTable.remove(indexLST);
							this.lstTable.add(indexBack,  tmp );
						}
					}
				}
				
				
			}
			
			flag = false;
			
			for (indexLST=0 ; indexLST<this.lstTable.size() ; indexLST++) {
				if ( indexLST==0 && !this.lstTable.get(indexLST).getLinkedTable().isEmpty() ) {
					flag = true;
				}else if(!this.lstTable.get(indexLST).getLinkedTable().isEmpty()) {
					nbFk = this.lstTable.get(indexLST).getLinkedTable().size();
					
					this.lstTable.get(indexLST).getLinkedTable().forEach( (k, v) -> {
						for(int indexBack=0 ; indexBack<indexLST ; indexBack++) {
							if(v.contains(this.lstTable.get(indexBack).getTableName())){
								nbFk--;
							}
						}
					});
					
					if(nbFk!=0) {
						flag =true;
					}
					
				}
			}
			
			
			
		}

		//Collections.reverse(lstTable);
//		System.out.println(lstTable);
		return this;
	}
	
	
	
	
}
