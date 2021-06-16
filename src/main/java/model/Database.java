package model;

import java.util.ArrayList;

/***
 * @author Brizeos
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 */

public class Database {

	private String name;
	private ArrayList<Table> lstTable;
	private static int nbFk, indexLST;

	
	/***
	 * Default constructor. Contains an ArrayList with it's tables.
	 */
	public Database() {
		super();
	
		this.lstTable = new ArrayList<Table>();
	}

	@Override
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

	public ArrayList<Table> getLstTable() {
		return lstTable;
	}

	public void setLstTable(ArrayList<Table> lstTable) {
		this.lstTable = lstTable;
	}

	/***
	 * 
	 * @return Number of columns not manualy validated by the user.
	 */
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



	/***
	 * 
	 * @return True if all tables are correctely leveled in this ArrayList.
	 */
	public boolean checkLvlAll() {
		for (Table table : lstTable) {
			if(!table.isLeveled())
				return false;
		}
		return true;
	}
	

	/***
	 * Method to sort all tables by level of dependency. 
	 * An index of the returned arraylist always have constrained column with a column's primary key of a previously table.
	 * @return This DataBase with an ordonned ArrayList.
	 */
	public Database sortByLevel() {

		boolean flag = true;
		Table tmp = null;
		
		/*
		 * Loop while as long as the Arraylist is not ordered.
		 */
		while(flag) {
			
			//For each table in the database.
			for (indexLST=0 ; indexLST<this.lstTable.size() ; indexLST++) {
				//If this is the first index and it has a contraint key, put this column at the end of the ArrayList.
				if ( indexLST==0 && !this.lstTable.get(indexLST).getLinkedTable().isEmpty() ) {
					tmp = this.lstTable.get(indexLST);
					this.lstTable.remove(indexLST);
					this.lstTable.add( tmp);
					
				}
				//Else if the table has a constraint.
				else if(!this.lstTable.get(indexLST).getLinkedTable().isEmpty()) {
					
					//Check how many constrained columns.
					nbFk = this.lstTable.get(indexLST).getLinkedTable().size();
					
					//Decrement each time one of this columns constraint is find since the begin of the ArrayList
					this.lstTable.get(indexLST).getLinkedTable().forEach( (k, v) -> {
						for(int indexBack=0 ; indexBack<indexLST ; indexBack++) {
							if(v.contains(this.lstTable.get(indexBack).getTableName())){
								nbFk--;
							}
						}
					});
					
					//If all keys are not found place this column at the end of the ArrayList.
					if(nbFk!=0) {
						tmp = this.lstTable.get(indexLST);
						this.lstTable.remove(indexLST);
						this.lstTable.add(tmp);
					}
				}
				//Else if this column does'nt have any constrained column, put it after the last column who doesn't have constrained columns.
				else if(this.lstTable.get(indexLST).getLinkedTable().isEmpty()) {
					
					for(int indexBack=0 ; indexBack<indexLST ; indexBack++) {
						if(!this.lstTable.get(indexBack).getLinkedTable().isEmpty()) {
							tmp = this.lstTable.get(indexLST);
							this.lstTable.remove(indexLST);
							this.lstTable.add(indexBack,  tmp );
						}
					}
				}
				
				
			}
			// Verification.
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
		
		return this;
	}
	
	
	
	
}
