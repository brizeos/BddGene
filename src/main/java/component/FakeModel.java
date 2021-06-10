package component;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.github.javafaker.Faker;

import vue.Lab;
import vue.TCustom;

public abstract class FakeModel extends JPanel {

	public static Faker f = new Faker();
	public int meth = 0;
	public Faked faked = null;
	public ArrayList<Component> ls;
	public Lab noOpt = null;
	

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
	 */
	public abstract void Launch();
	
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
		
		for (Component ob : ((JPanel)this.getComponent(0)).getComponents() ) {
			if (ob instanceof TCustom) {
				ls.add(((TCustom) ob).getText());
			}
		}
		return ls;
		
	}
	

}


