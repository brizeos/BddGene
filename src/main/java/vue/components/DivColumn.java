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

public class DivColumn extends JPanel {

	private Column col;

	public DivColumn(Column c) {

		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(800, 80));
		setMaximumSize(new Dimension(800, 80));
		setBackground(Color.green);
		
		this.col = c;
		
		//Click sur la DIV
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

	public Column getColumn() {
		return col;
	}

	public void setColumn(Column column) {
		this.col = column;
	}

	

}
