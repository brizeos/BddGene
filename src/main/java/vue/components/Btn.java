package vue.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Btn extends JButton {

	/**
	 * 
	 * @param text 	Text to put in the Button
	 * @param x		Coordinate X
	 * @param y 	Coordinate Y
	 * @param wx 	Width
	 * @param wy	Height
	 */
	public Btn(String text, int x, int y, int width, int height) {
		super(text);
		
		setBounds(x, y , width, height);
		setBackground(Color.darkGray);
		setForeground(Color.white);
		
	}
	
	public Btn(String text) {
		super(text);
		
		setBackground(Color.darkGray);
		setForeground(Color.white);
		
	}
	
	
	public Btn(Icon icon) {
		super(icon);
		Dimension dim = new Dimension(25, 25);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setBackground(Color.black);
		setBorder(BorderFactory.createEmptyBorder());

	}




}
