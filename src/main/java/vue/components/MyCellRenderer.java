package vue.components;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public class MyCellRenderer extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus){
		    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		    if(node.getChildCount() == 0) {
		      this.setEnabled(false);
		      this.setDisabledIcon(this.getDefaultLeafIcon());
		    }
		    return this;
		}
	}