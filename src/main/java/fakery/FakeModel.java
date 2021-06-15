package fakery;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.github.javafaker.Faker;

import vue.components.Lab;
import vue.components.TCustom;

public abstract class FakeModel extends JPanel {

	public static Faker f = new Faker();
	public int meth = 0;
	public Faked faked = null;
	public ArrayList<Component> ls;
	public Lab noOpt = null;
	private TCustom optOne = null, optTwo = null, optThree = null;
	

	public FakeModel(Faked fake) {
//		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(450, 350));
		
		this.noOpt = new Lab("No options.");
		this.faked = fake;
		this.ls = new ArrayList<Component>();
		
		
		
		
		
	}
	
	/**
	 * 
	 * @param lsStr List of parameters to use in Faker command line
	 * @return String :  Generated data
	 * @throws SQLException 
	 */
	public abstract void Launch() throws SQLException;
	
	/**
	 * Reload content
	 */
	public abstract void resetAll();
	
	
	/**
	 * Reset options panel to configure the new Faked data.
	 */
	public void addAll() {
		
		for(Component c : this.ls) {
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
			p.add(c);
			this.add(p);
		}
	}

	
	/*
	 * Getters/Setters
	 */
	
	/**
	 * Check if there are options.
	 * @return Boolean
	 */
	public boolean haveOptions() {
		return this.getComponent(0).equals(this.noOpt);
	}
	
	public int getMeth() {
		return meth;
	}

	public ArrayList<Component> getLs() {
		return ls;
	}
	
	public TCustom getOpt1() {
		return optOne;
	}

	public void setOpt1(TCustom opt1) {
		this.optOne = opt1;
	}

	public TCustom getOpt2() {
		return optTwo;
	}

	public void setOpt2(TCustom opt2) {
		this.optTwo = opt2;
	}

	public TCustom getOpt3() {
		return optThree;
	}

	public void setOpt3(TCustom opt3) {
		this.optThree = opt3;
	}

	public Faked getFaked() {
		return faked;
	}
	
	public void setMeth(int meth) {
		this.meth = meth;
	}
	
	public void setFaked(Faked faked) {
		this.faked = faked;
	}
	
	public void setLs(ArrayList<Component> ls) {
		this.ls = ls;
	}
	
	public ArrayList<Object> checkParameters(){
		ArrayList<Object> ls = new ArrayList<Object>();
		
		for (Component ob : this.getLs() ) {
			if ( ob instanceof TCustom)
				ls.add(((TCustom) ob).getText());
		}
		return ls;
		
	}
	
//	private ArrayList<TCustom> returnAllParams(){
//		ArrayList<TCustom> ls = new ArrayList<TCustom>();
//		
//		if (this.getOpt1() != null) {
//			ls.add(this.optOne);
//		}
//		if (this.getOpt2() != null) {
//			ls.add(this.optTwo);
//		}
//		if (this.getOpt3() != null) {
//			ls.add(this.optThree);
//		}
//		return ls;
//		
//		
//	}
	

}


