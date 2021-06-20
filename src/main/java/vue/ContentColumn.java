package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;
import model.Table;
import vue.components.DivColumn;
import vue.components.Lab;

@SuppressWarnings("serial")
public class ContentColumn extends JPanel{

	public ContentColumn(int x, int y, int wx, int wy, ArrayList<Column> lstColumn) {
		
		super();
		
		setBounds(x, y , wx, wy);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		
	}
	
	public void LoadDiv( Table tableSelected) {
		
		removeAll();
		if(tableSelected != null) {
			
			JPanel pan = new JPanel();
			pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
			pan.add(new Lab("Nombre de N-Uplet à générer : ", true));
			pan.add(tableSelected.getNbJft());
			pan.setBackground(App.MainColor);
			add(pan);
			
			
			for(Column c : tableSelected.getLstColumn()) {
				if( !(c.getIsConstrained()) && !(c.getIsPrimary()) ) {
					DivColumn j = new DivColumn(c);
					j.add(new Lab(c.getName(),true));
					ViewPrincipal.getContent().add(j);
					ViewPrincipal.getContent().add(Box.createRigidArea(new Dimension(15,0)));
				}
			}
		}
	}
}