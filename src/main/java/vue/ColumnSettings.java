package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;
import vue.components.Btn;

public class ColumnSettings extends JPanel {

	private Btn okay;
	private Btn btn;
	private Column column;



	public ColumnSettings(int i, int j, int k, int l, Column c) {
		this.okay = new Btn("Valider");
		this.btn = new Btn("Annuler");
		
		this.column = c; 
		setBounds(i, j , k, l);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setEnabled(false);
		setVisible(false);
		setBackground(App.MainColor);
		if(!c.getName().equals("null"))
			LoadCol();
		
		this.btn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				ViewPrincipal.getLateral().updateNbRestant();
				
				ViewPrincipal.getLateral().setEnabled(true);
				ViewPrincipal.getLateral().setVisible(true);
				
				ViewPrincipal.getContent().setEnabled(true);
				ViewPrincipal.getContent().setVisible(true);
				
				ViewPrincipal.getModif().setVisible(false);
				ViewPrincipal.getModif().setEnabled(false);
							
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
				
				
				
			}
		
		});
		this.okay.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				getColumn().setValidated(true);
				
				ViewPrincipal.getLateral().updateNbRestant();
				
				ViewPrincipal.getLateral().setEnabled(true);
				ViewPrincipal.getLateral().setVisible(true);
				
				ViewPrincipal.getContent().setEnabled(true);
				ViewPrincipal.getContent().setVisible(true);
				
				ViewPrincipal.getModif().setVisible(false);
				ViewPrincipal.getModif().setEnabled(false);
							
				ViewPrincipal.getModif().repaint();
				ViewPrincipal.getModif().revalidate();
			}
		
		});
		
		repaint();
		revalidate();
		
		
	}
	
	
	/**
	 * ******************************************
	 */


	public void LoadCol() {
		ColumnSettings p = ViewPrincipal.getModif();
		p.removeAll();
		p.setBackground(App.MainColor);
		p.add(this.column.getFaked());
		p.add(p.getBtn());
		p.add(p.getOkay());

		p.repaint();
		p.revalidate();
		
		
	}
	
	public Column getColumn() {
		return column;
	}
	public Btn getOkay() {
		return okay;
	}

	public void setOkay(Btn okay) {
		this.okay = okay;
	}

	public Btn getBtn() {
		return btn;
	}

	public void setBtn(Btn btn) {
		this.btn = btn;
	}


	public void setColumn(Column col) {
		this.column = col;
		
	}
}