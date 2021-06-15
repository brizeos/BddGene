package fakery;

import java.awt.Label;
import java.sql.Date;
import java.time.*;
import java.util.ArrayList;

import vue.components.TCustom;

public class FakedDateTimeOption extends FakeModel {

	private TCustom optOne, optTwo, optThree;

	public FakedDateTimeOption(Faked fake) {
		super(fake);
		this.optOne = new TCustom(); 
		this.optTwo = new TCustom();
		this.optThree = new TCustom();
		this.resetAll();
	}

	
	@Override
	public void Launch() {
		LocalDate str = null;
		ArrayList<Object> lsStr = this.checkParameters();
		String dateFrom = "2001-01-01";   	//lsStr.get(0);
		String dateTo = "2010-12-30";    	//lsStr.get(1);
		switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
			
			case "Random Between" : str =  f.date().between( Date.valueOf(dateFrom), Date.valueOf(dateTo)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() ;
				break;
			
		}
		getFaked().setData(str.toString());
	}

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
