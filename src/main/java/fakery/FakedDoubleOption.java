package fakery;

import java.awt.Label;
import java.util.ArrayList;

import vue.components.TCustom;

public class FakedDoubleOption extends FakeModel{

	private TCustom optOne, optTwo, optThree;
	
	public FakedDoubleOption(Faked fake) {
		super(fake);
		this.optOne = new TCustom(); 
		this.optTwo = new TCustom();
		this.optThree = new TCustom();
		
		this.resetAll();
		
	}

	@Override
	public void Launch() {
		double str = 0;		
		ArrayList<Object> lsStr = this.checkParameters();
		switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
			
			case "Random Between" : str = f.number().randomDouble((int) lsStr.get(0), (int) lsStr.get(1), (int) lsStr.get(2));
				break;
			
			
		}
		getFaked().setData(String.valueOf( str ));
	}

	@Override
	public void resetAll() {
		this.removeAll();
		this.ls.clear();

		switch(this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
		
			case "Random Between":
				this.ls.add(new Label("Number of decimals : "));
				this.ls.add(this.optOne);
				this.ls.add(new Label("Minimum : "));
				this.ls.add(this.optTwo);
				this.ls.add(new Label("Maximum : "));
				this.ls.add(this.optThree);
				
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
