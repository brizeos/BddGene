package vue.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class ComboSelecter extends JComboBox<String> {

	private HashMap<String, ArrayList<String>> arr;

	/**
	 * 
	 * @param ls Arraylist of string to show in the ComboBox
	 */
	public ComboSelecter(ArrayList<String> ls) {
		super();
		
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
		str.addAll(ls);
		setModel(str);
	}
	
	/***
	 * 
	 * @param lsStr2 HashMap to use in ComboBox
	 * @param s String of option to load.
	 */
	public ComboSelecter(HashMap<String, ArrayList<String>> lsStr2, String s) {
		super();
		
		this.arr = lsStr2;

		ArrayList<String> ls = new ArrayList<String>();
		ls.addAll((Collection<? extends String>) this.arr.get(s.replaceAll("\"", "")));
	
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
		str.addAll(ls);
		setModel(str);
	}
	
	/**
	 * Reload the ComboBox and choose one with the parameter "str"
	 * @param str String of option to load.
	 */
	public void ReloadComboSec(String str) {
		DefaultComboBoxModel<String> dcm = new DefaultComboBoxModel<String>();
		dcm.addAll((Collection<? extends String>) this.arr.get( str.replaceAll("\"", "") ) );
		this.setModel(dcm);
		
	}

}
