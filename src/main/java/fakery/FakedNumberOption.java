package fakery;

import java.awt.Label;
import java.util.ArrayList;

import MavenBdd.Generator.App;
import vue.components.TCustom;

public class FakedNumberOption extends FakeModel {

	
	private TCustom optOne, optTwo;



	public FakedNumberOption(Faked fake) {
		super(fake);
		this.optOne = new TCustom(); 
		this.optTwo = new TCustom();
		this.resetAll();
	}

	
	
	
	

	@Override
	public void Launch() {
		int str = 0;		
		ArrayList<Object> lsStr = this.checkParameters();
		
		if(this.getFaked().getParentCol().getIsConstrained()) {
			this.getFaked().setData( String.valueOf( searchForConstraint()) );
		}else {
			
			switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
				
				case "Random" : str = f.number().randomDigit();
					break;
				case "RandomNotZero" : str = f.number().randomDigitNotZero();
					break;
				case "Number Between" : str = f.number().numberBetween((int)lsStr.get(0), (int)lsStr.get(1));
					break;
				
			}
		}
		getFaked().setData( String.valueOf(str));

	}

	

	private int searchForConstraint() {
		int i = 0;

		String sql ="";
		
		App.dao.setSecond(sql);
		
		
		
		
		return i;
	}






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
