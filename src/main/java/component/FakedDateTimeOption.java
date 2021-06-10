package component;

import java.awt.Label;
import java.sql.Date;
import java.util.ArrayList;

import vue.TCustom;

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
		Date str = null;
		ArrayList<Object> lsStr = this.checkParameters();
		String dateFrom = "01-01-" + lsStr.get(0);
		String dateTo = "31-12-" + lsStr.get(1);
		
		switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
			
			case "Random Between" : str = (Date) f.date().between( Date.valueOf(dateFrom), Date.valueOf(dateTo));
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
