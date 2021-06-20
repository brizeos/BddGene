package fakery;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.github.javafaker.Faker;

import MavenBdd.Generator.App;
import vue.components.Lab;
import vue.components.TCustom;

@SuppressWarnings("serial")
public abstract class FakeModel extends JPanel {

	public static Faker f = new Faker();
	public int meth = 0;
	public Faked faked = null;
	public ArrayList<Component> ls;
	public Lab noOpt = null;
	private TCustom optOne = null, optTwo = null, optThree = null;
	
	/***
	 * 
	 * @param fake Reference to this.faked parent.
	 */
	public FakeModel(Faked fake) {
		setPreferredSize(new Dimension(450, 350));
		setBackground(App.MainColor);
		this.noOpt = new Lab("No options.", false);
		this.faked = fake;
		this.ls = new ArrayList<Component>();
		
	}
	
	/**
	 * Edit data value in this.faked
	 * @throws SQLException 
	 */
	public abstract void Launch() throws SQLException;
	
	/**
	 * Reload content
	 */
	public abstract void resetAll();
	
	

	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/
	
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
	
	public boolean haveOptions() {
		return this.getComponent(0).equals(this.noOpt);
	}
	
	/**
	 * Reset options panel to configure the new Faked data.
	 */
	public void addAll() {
		
		for(Component c : this.ls) {
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
			p.add(c);
			p.setBackground(App.MainColor);
			this.add(p);
		}
	}
	
}


