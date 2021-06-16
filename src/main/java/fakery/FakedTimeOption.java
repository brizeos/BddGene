package fakery;

import java.awt.Label;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import vue.components.TCustom;

@SuppressWarnings("serial")
public class FakedTimeOption extends FakeModel {

	private TCustom optOne, optTwo;
	
	/***
	 * 
	 * @param fake Reference to this.faked parent.
	 */
	public FakedTimeOption(Faked fake) {
		super(fake);
		this.optOne = new TCustom(); 
		this.optTwo = new TCustom();
	}

	/**
	 * Edit data value in this.faked
	 * @throws SQLException 
	 */
	@Override
	public void Launch() {
		ArrayList<Object> lsStr = this.checkParameters();
		Date str = null;		
		
		String dateFrom = "01-01-" + lsStr.get(0);
		String dateTo = "31-12-" + lsStr.get(1);
		
		switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
			
			case "Random Between" : str = (Date) f.date().between( Date.valueOf(dateFrom), Date.valueOf(dateTo));
				break;
			
		}
		getFaked().setData(str.toString());
		
	}

	/**
	 * Reload content
	 */
	@Override
	public void resetAll() {
		this.removeAll();
		this.ls.clear();

		switch(this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
		
			case "Random Between":
				
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
