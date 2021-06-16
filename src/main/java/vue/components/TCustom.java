package vue.components;

import java.awt.Dimension;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TCustom extends JTextField {

	private Dimension dim;

	/***
	 * JTextField with custom dimensions and value.
	 */
	public TCustom() {
		this.dim = new Dimension(100, 40);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setText("0");
		
	}

	public TCustom(String text) {
		super(text);
	}

	/***
	 * JTextField with custom dimension.
	 * @param columns 	Max number of columns
	 * @param x			Position X
	 * @param y			Position Y
	 */
	public TCustom(int columns, int x, int y) {
		super(columns);
		this.dim = new Dimension(100, 40);
		setBounds(x, y, 100, 40);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		
	}
}
