package vue.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import model.Column;
import vue.Pane;
import vue.ViewPrincipal;

@SuppressWarnings("serial")
public class DivColumn extends JPanel {

	private Column col;

	/***
	 * Panel who is loaded in the ViewPrincipal to show columns needed to be parameterized.
	 * Contains a column and can be clicked to show setting options.
	 * @param column Colum to load in this panel.
	 */
	public DivColumn(Column column) {

		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(800, 80));
		setMaximumSize(new Dimension(800, 80));
		setBackground(Color.green);
		
		this.col = column;
		
		//OnClick load this column settings.
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				ViewPrincipal.getLateral().setEnabled(false);
				ViewPrincipal.getLateral().setVisible(false);
				
				ViewPrincipal.getContent().setEnabled(false);
				ViewPrincipal.getContent().setVisible(false);
				
				ViewPrincipal.getModif().setVisible(true);
				ViewPrincipal.getModif().setEnabled(true);
				
				Pane.setColumn(col);
				Pane.LoadCol();
				
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
			}
		});
	}

	
	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/
	public Column getColumn() {
		return col;
	}

	public void setColumn(Column column) {
		this.col = column;
	}

	

}
