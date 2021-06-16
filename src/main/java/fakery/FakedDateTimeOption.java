package fakery;

import java.awt.Label;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

import vue.components.TCustom;

@SuppressWarnings("serial")
public class FakedDateTimeOption extends FakeModel {

	private TCustom optOne, optTwo;

	/***
	 * 
	 * @param fake Reference to this.faked parent.
	 */
	public FakedDateTimeOption(Faked fake) {
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
	public void Launch() {
		LocalDate str = null;
		String dateFrom = "2001-01-01";   	//lsStr.get(0);
		String dateTo = "2010-12-30";    	//lsStr.get(1);
		switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
			
			case "Random Between" : str =  f.date().between( Date.valueOf(dateFrom), Date.valueOf(dateTo)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() ;
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
