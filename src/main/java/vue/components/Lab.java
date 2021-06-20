package vue.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Lab extends JLabel {

	/***
	 * Not configured yet.
	 * @param text String to put in this Label
	 */
	public Lab(String text, boolean isTitle) {
		super(text);
		setForeground(Color.white);
		Dimension dim = new Dimension((isTitle?380:150), 50);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
	}
}
