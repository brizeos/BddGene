package vue.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ComboSelecter extends JComboBox<String> {


	private HashMap<String, ArrayList<String>> arr;

	public ComboSelecter(ArrayList<String> ls) {
		super();
		
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
		str.addAll(ls);
		setModel(str);
	}
	

	@SuppressWarnings("unchecked")
	public ComboSelecter(HashMap<String, ArrayList<String>> lsStr2, String s) {
		super();
		ArrayList<String> ls = new ArrayList<String>();
		this.arr = lsStr2;
		ls.addAll((Collection<? extends String>) this.arr.get(s.replaceAll("\"", "")));
		
	
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
		str.addAll(ls);
		setModel(str);
	}
	
	
	@SuppressWarnings("unchecked")
	public void ReloadComboSec(String s) {
		DefaultComboBoxModel<String> str = new DefaultComboBoxModel<String>();
		str.addAll((Collection<? extends String>) this.arr.get( s.replaceAll("\"", "") ) );
		this.setModel(str);
		
	}

}
