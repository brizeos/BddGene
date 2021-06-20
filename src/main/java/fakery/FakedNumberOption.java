package fakery;

import java.awt.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MavenBdd.Generator.App;
import model.Column;
import vue.components.TCustom;

@SuppressWarnings("serial")
public class FakedNumberOption extends FakeModel {
	
	private TCustom optOne, optTwo;

	/***
	 * 
	 * @param fake Reference to this.faked parent.
	 */
	public FakedNumberOption(Faked fake) {
		super(fake);
		this.optOne = new TCustom(); 
		this.optTwo = new TCustom();
		this.resetAll();
	}

	/**
	 * Edit data value in this.faked
	 * @throws SQLException 
	 */
	@Override
	public void Launch() throws SQLException {
		int str = 0;		
		ArrayList<Object> lsStr = this.checkParameters();
		
		//If this column parent is constrained.
		if(this.getFaked().getParentCol().getIsConstrained()) {
			this.getFaked().setData( String.valueOf( searchForConstraint(this.faked.getParentCol())) );
		}else {
			
			switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
				
				case "Random" : str = f.number().randomDigit();
					break;
				case "RandomNotZero" : str = f.number().randomDigitNotZero();
					break;
				case "Number Between" : str = f.number().numberBetween((int)lsStr.get(0), (int)lsStr.get(1));
					break;
				
			}
			this.getFaked().setData( String.valueOf(str));
			
		}

	}


	/***
	 * Search in database value of a constrained column.
	 * @param c Column to import a primary key
	 * @return Value of referenced column.
	 * @throws SQLException
	 */
	private int searchForConstraint(Column c) throws SQLException {
		int i = 0;
		
		String sql ="Select COUNT(`"+ c.getTable().getLinkedTable().get(c.getName()).substring( c.getTable().getLinkedTable().get( c.getName()).indexOf(".")+1 ) +"`)"
				+" from `" +c.getTable().getLinkedTable().get(c.getName()).substring(0, c.getTable().getLinkedTable().get(c.getName()).indexOf("."))+ "`;";
		
		App.getDao().setSecond(sql);
		
		ResultSet rs = App.getDao().getSecond().executeQuery();
		
		while (rs.next())
			i = f.number().numberBetween(1, rs.getInt(1));
		
		return i;
	}

	/**
	 * Reload content
	 */
	@Override
	public void resetAll() {
		this.removeAll();
		this.ls.clear();

		switch(this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
		
			case "Number Between":
				this.ls.add(new Label("Minimum : "));
				this.ls.add(this.optOne);
				this.ls.add(new Label("Maximum : "));
				this.ls.add(this.optTwo);
				break;
				
			default : 
				this.ls.add(this.noOpt);
				break;
				
		}

		this.addAll();
		this.faked.repaint();
		this.faked.revalidate();
		repaint();
		revalidate();
		
	}
}
