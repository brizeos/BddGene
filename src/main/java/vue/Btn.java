package vue;

import java.awt.Color;

import javax.swing.JButton;

public class Btn extends JButton {

	

	public Btn(String text, int x, int y, int wx, int wy) {
		super(text);
		setBounds(x, y , wx, wy);
		setBackground(Color.darkGray);
		setForeground(Color.white);
		
	}

	

}
