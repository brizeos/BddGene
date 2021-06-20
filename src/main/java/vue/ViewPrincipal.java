package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;

@SuppressWarnings("serial")
public class ViewPrincipal extends JPanel {
	
	private static ContentColumn content;
	private static LateralTree lateral;
	private static ColumnSettings modif;
	
	/***
	 * Default view after connection.
	 */
	public ViewPrincipal() {
		super();
		
		App.getFrame().setBounds(25, 25, 1900, 980);
//		App.getFrame().getContentPane().setPreferredSize(new Dimension(1900,980));
		
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		
		lateral = new LateralTree(0, 0, 450, 950, App.getDb());
		lateral.setBackground(Color.black);
		content = new ContentColumn(450, 0, 1450, 950, new ArrayList<Column>());
		content.setBackground(Color.black);
		modif = new ColumnSettings(400, 300, 500, 500, new Column());
		modif.setBackground(Color.black);
		
		add(lateral);
		add(content);
		add(modif);
		
		App.getFrame().repaint();
		App.getFrame().revalidate();
	
	}

	public static ContentColumn getContent() {
		return content;
	}

	public static LateralTree getLateral() {
		return lateral;
	}

	public static ColumnSettings getModif() {
		return modif;
	}

}
