package squared.part.factory;

import java.util.HashMap;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import squared.model.Constraint;
import squared.model.Diagram;
import squared.model.Node;
import squared.model.NodeLink;
import squared.part.ConstraintPart;
import squared.part.DiagramPart;
import squared.part.NodeLinkPart;
import squared.part.NodePart;

/**
 * Edit part factory for creating EditPart instances as delegates for model objects
 * 
 */
public class SquaredEditPartFactory implements EditPartFactory
{
	private HashMap<Node, NodePart> nodeMap = new HashMap<Node, NodePart>();
	
	public EditPart createEditPart(EditPart context, Object model)
	{
		EditPart part = null;
		if (model instanceof Diagram) {
			part = new DiagramPart();
		} else if (model instanceof Node) {
			part = new NodePart();
			nodeMap.put((Node)model, (NodePart) part);
		} else if (model instanceof NodeLink) {
			part = new NodeLinkPart(((NodeLink)model).getLabel());
		} else if (model instanceof Constraint) {
			part = new ConstraintPart();
		}
			
		part.setModel(model);
		return part;
	}
	
	public NodePart getPartByModel(Node node) 
	{
		return nodeMap.get(node);
	}
}
