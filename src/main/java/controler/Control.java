package controler;

import java.util.ArrayList;

import MavenBdd.Generator.App;
import model.Table;
import vue.Pane;

public class Control {

	public static void LaunchViewIteration(ArrayList<ArrayList<Table>> lsLvl) {

		App.frame.getContentPane().removeAll();
		Pane pane = new Pane(lsLvl);
	
		for (int i = lsLvl.size(); i >=0; i--) {
			
			
			
		}
		
		
		App.frame.getContentPane().add(pane);
	}

}
