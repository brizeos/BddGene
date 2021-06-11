package fakery;

import java.awt.Label;
import java.util.ArrayList;

import vue.components.TCustom;

public class FakedTextOption extends FakeModel{
	
	private TCustom optOne, optTwo;

	//private ComboSelecter opt1 = new ComboSelecter( null );

	public FakedTextOption(Faked fake)  {
		super(fake);
		this.optOne = new TCustom(); 
		this.optTwo = new TCustom();
		
		this.resetAll();
	}
	
	/***
	 * 
	 * @return A new String with the FakedData generated.
	 */
	public void Launch() {
		String str ="";		
		ArrayList<Object> lsStr = this.checkParameters();
		switch (this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
			case "First name" : str = f.address().firstName();
				break;
			case "Last name" : str = f.address().lastName();
				break;
			case "Street name" : str = f.address().streetName();
				break;
			case "City name" : str = f.address().cityName();
				break;
			case "Country" : str = f.address().country();
				break;
			case "Full address" : str = f.address().fullAddress();
				break;
			case "Zip code" : str = f.address().zipCode();
				break;
			case "Digits" : str = f.number().digits( Integer.parseInt((String) lsStr.get(0)) );
				break;
			case "Between" : str = String.valueOf(   f.number().numberBetween(  Integer.parseInt( (String) lsStr.get(0)), Integer.parseInt((String) lsStr.get(1) )  )  );
				break;
			case "Random" : str = String.valueOf(  f.number().randomDigit()  );
				break;
			case "Dish" : str = f.food().dish();
				break;
			case "Fruit" : str = f.food().fruit();
				break;
			case "Spice" : str = f.food().spice();
				break;
			case "Ingredient" : str = f.food().ingredient();
				break;
			case "Measurement" : str = f.food().measurement();
				break;
			case "Sushi" : str = f.food().sushi();
				break;
			case "Vegetable" : str = f.food().vegetable();
				break;
			case "Phone number" : str = f.phoneNumber().phoneNumber();
				break;
			case "Cell phone" : str = f.phoneNumber().cellPhone();
				break;
			case "Extension" : str = f.phoneNumber().extension();
				break;
			case "God" : str = f.ancient().god();
				break;
			case "Hero" : str = f.ancient().hero();
				break;
			case "Primordial" : str = f.ancient().primordial();
				break;
			case "Titan" : str = f.ancient().titan();
				break;
			case "Name" : str = f.company().name();
				break;
			case "Profession" : str = f.company().profession();
				break;
			case "Logo" : str = f.company().logo();
				break;
			case "Industry" : str = f.company().industry();
				break;
			case "Buzzword" : str = f.company().buzzword();
				break;
			case "Catchphrase" : str = f.company().catchPhrase();
				break;
			case "Url" : str = f.company().url();
				break;
			case "Animal" : str = f.animal().name();
				break;
			case "Avatar" : str = f.avatar().image();
				break;
			case "Artist" : str = f.artist().name();
				break;
			case "Email" : str = f.internet().emailAddress();
			

		}
		
		
		getFaked().setData(str);
	}



	/**
	 * Reset options panel to configure the new Faked data.
	 */
	public void resetAll() {
		
		this.removeAll();
		this.ls.clear();

		switch(this.faked.getFtSec().getSelectedItem().toString().replaceAll("\"", "")) {
		

			case "Digits":
				this.ls.add(new Label("Number of Digits : "));
				this.ls.add(this.optOne);
				break;
			case "Between":
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
