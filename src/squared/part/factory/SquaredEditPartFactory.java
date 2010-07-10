package squared.part.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import squared.model.Constraint;
import squared.model.ConstraintLink;
import squared.model.Diagram;
import squared.model.Node;
import squared.model.NodeLink;
import squared.part.ConstraintLinkPart;
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
	public EditPart createEditPart(EditPart context, Object model)
	{
		EditPart part = null;
		if (model instanceof Diagram) {
			part = new DiagramPart();
		} else if (model instanceof Node) {
			part = new NodePart();
		} else if (model instanceof NodeLink) {
			part = new NodeLinkPart();
		} else if (model instanceof Constraint) {
			part = new ConstraintPart();
		} else if (model instanceof ConstraintLink) {
			part = new ConstraintLinkPart();
		}
			
		part.setModel(model);
		return part;
	}
}
