package squared.part.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

//import squared.model.Column;
//import squared.model.Relationship;
//import squared.model.Schema;
//import squared.model.Table;
//import squared.part.ColumnPart;
//import squared.part.RelationshipPart;
//import squared.part.SchemaDiagramPart;
//import squared.part.TablePart;]

import squared.model.Node;
import squared.model.Diagram;
import squared.model.Constraint;
import squared.part.NodePart;
import squared.part.DiagramPart;
import squared.part.ConstraintPart;

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
		} else if (model instanceof Constraint) {
			part = new ConstraintPart();
		}
		part.setModel(model);
		return part;
	}
}
