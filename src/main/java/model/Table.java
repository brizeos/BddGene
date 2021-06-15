package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import relation.RelationModel;

import vue.Pane;

public class Table {

	private String tableName;
	private ArrayList<Column> lstColumn;
	private boolean isConstrained, leveled, done;
	private int nb;
	
	private HashMap<String, String> linkedTable;
	private int nbDone;
	private RelationModel relationPane;
	
	public Table() {
		super();
		this.lstColumn = new ArrayList<Column>();
		this.leveled = false;
		this.isConstrained = false;
		this.setDone(false);
		this.linkedTable = new HashMap<String, String>();
		/**
		 * Fields about Iterations
		 */
		this.nbDone = 0;
		this.nb = 10; //TODO zero
		
		
		
		this.relationPane = new RelationModel(this);
		
		
		//TODO relationPane : charger les options
	}
	
	public void increment() {
		this.nbDone ++;
		if (this.nbDone >= this.nb ) {
			this.setDone(true);
			
		}
		
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
		return this.nb;
	}
	public void setNb(int nb) {
		this.nb = nb;
	}




	public HashMap<String, String> getLinkedTable() {
		return linkedTable;
	}




	public void setLinkedTable(HashMap<String, String> linkedTable) {
		this.linkedTable = linkedTable;
	}




	public boolean isAllLeveled() {
		for (Column c : this.lstColumn) {
			if(!c.isLeveled()) 
				return false;
		}
		return true;
	}
	
	public boolean thereIsThisConstraint(String str) {
		
		if(linkedTable.containsKey(str))
			return true;

		return false;
	}

	public RelationModel getRelationModel() {
		
		return this.relationPane;
	}

}
