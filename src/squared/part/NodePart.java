package squared.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.NodeFigure;

public class NodePart extends AbstractGraphicalEditPart {
	
	public NodePart(Object model) {
		setModel(model);
	}

	@Override
	protected IFigure createFigure() {
		return new NodeFigure();
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

}
