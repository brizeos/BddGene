package vue.components;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Table;
import vue.LateralTree;
import vue.ViewPrincipal;

public class MyTreeSelectionListener implements TreeSelectionListener{

	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
	
		DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
		if( tmp.getUserObject() instanceof Table ) {
			Table tableSelected = (Table) tmp.getUserObject();
			LateralTree.setTableSelected(tableSelected);
			ViewPrincipal.getContent().LoadDiv(tableSelected);
			ViewPrincipal.getContent().repaint();
			ViewPrincipal.getContent().revalidate();
		}
	}
	
	
}