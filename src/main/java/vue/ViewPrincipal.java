package vue;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import MavenBdd.Generator.App;
import model.Column;

@SuppressWarnings("serial")
public class ViewPrincipal extends JPanel {
	
	private static Pane content;
	private static Pane lateral;
	private static Pane modif;
	
	/***
	 * Default view after connection.
	 */
	public ViewPrincipal() {
		super();
		
		App.frame.setBounds(0, 0, 1900, 980);
		
		this.setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1900, 950);
		setLayout(null);
		
		lateral = new Pane(0, 0, 450, 950, App.db);
		lateral.setBackground(Color.blue);
		content = new Pane(450, 0, 1450, 950, new ArrayList<Column>());
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
