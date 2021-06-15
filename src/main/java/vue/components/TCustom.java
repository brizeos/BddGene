package vue.components;

import java.awt.Dimension;

import javax.swing.JTextField;

public class TCustom extends JTextField {

	private Dimension dim;

	public TCustom() {
		this.dim = new Dimension(100, 40);
//		setBounds(x, y, 100, 40);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setText("0");
		
	}

	public TCustom(String text) {
		super(text);
	}

	public TCustom(int columns, int x, int y) {
		super(columns);
		this.dim = new Dimension(100, 40);
		setBounds(x, y, 100, 40);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		
	}

	public TCustom(String text, int columns) {
		super(text, columns);
	}


}
