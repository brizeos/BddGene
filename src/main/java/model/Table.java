package model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import vue.components.TCustom;


/***
 * @author Brizeos
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 */
public class Table {

	private String tableName;
	private ArrayList<Column> lstColumn;
	private boolean isConstrained;
	private boolean leveled; 
	private boolean done;
	private int nb;
	private int nbDone;
	private TCustom nbJft;
	
	/***
	* Key 	: 	String "this.column.name" of this table</br>
	* Value : 	String "table.column" of the constrained column passed in key>
	*/
	private HashMap<String, String> linkedTable;
	
	/***
	 *	Default constructor.
	 */
	public Table() {
		
		super();
		
		this.lstColumn = new ArrayList<Column>();
		this.leveled = false;								// True if the table doesn't have constraints in the Database ArrayList.
		this.isConstrained = false;							// True if this table has a contrained column
		this.done = false;									// True if this table is done.
		this.linkedTable = new HashMap<String, String>();	
		this.nbDone = 0;									// Increment each time this table is generated.
		this.nb = 100; 										// Generation table Iteration.
		this.nbJft = new TCustom(4);
		
		/**
		 * Listener to change this.nb value.
		 */
		this.nbJft.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					nb = Integer.parseInt( e.getDocument().getText(0, e.getDocument().getLength()) );
				} catch (NumberFormatException | BadLocationException e1) {
					e1.printStackTrace();
					nb = 100;
				}
				
			}
		});
		
	}
	
	
	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/
	
	public TCustom getNbJft() {
		return nbJft;
	}

	public void setNbJft(TCustom nbJft) {
		this.nbJft = nbJft;
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

	/*************
	 *  Methods	 *
	 *************/
	
	
	
	/***
	 * Increment a counter to check generation iteration.
	 * Set done true if iteration are equals to number of iteration ask by user.
	 */
	public void increment() {
		this.nbDone ++;
		if (this.nbDone >= this.nb ) {
			this.setDone(true);
		}
	}

	/**
	 * 
	 * @return true if all column has all primary key before this table in the Arraylist of the database. 
	 */
	public boolean isAllLeveled() {
		for (Column c : this.lstColumn) {
			if(!c.isLeveled()) 
				return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param str String key of column to check.
	 * @return True if there a key with this value.
	 */
	public boolean thereIsThisConstraint(String str) {
		if(linkedTable.containsKey(str)) {
			return true;
		}
		return false;
	}
	
	public Column getThisColumn(String str) {
		for (Column column : lstColumn) {
			if(column.getName().equals(str)){
				return column;
			}
		}
		return null;
	}
}
