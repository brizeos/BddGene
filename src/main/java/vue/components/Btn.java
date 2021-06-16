package vue.components;

import java.awt.Color;

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
}
