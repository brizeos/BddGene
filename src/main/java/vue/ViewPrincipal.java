package vue;

import java.awt.Color;

import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;

public class ViewPrincipal extends JPanel {
	
	private static Pane content;
	private static Pane lateral;
	private static Pane modif;
	
	public ViewPrincipal() {
		super();
		App.frame.setBounds(0, 0, 1900, 980);
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1900, 950);
		setLayout(null);
		
		lateral = new Pane(0, 0, 450, 950, App.db);
		lateral.setBackground(Color.blue);
		content = new Pane(450, 0, 1450, 950, Pane.tableSelected.getLstColumn());
		content.setBackground(Color.red);
		modif = new Pane(400, 300, 500, 500, new Column());
		modif.setBackground(Color.white);
		
		
		add(lateral);
		add(content);
		add(modif);
		
		
	}

	public static Pane getContent() {
		return content;
	}

	public static void setContent(Pane content) {
		ViewPrincipal.content = content;
	}

	public static Pane getLateral() {
		return lateral;
	}

	public static void setLateral(Pane lateral) {
		ViewPrincipal.lateral = lateral;
	}

	public static Pane getModif() {
		return modif;
	}

	public static void setModif(Pane modif) {
		ViewPrincipal.modif = modif;
	}
	
}
