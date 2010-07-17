package squared.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.ConstraintFigure;
import squared.model.Constraint;
import squared.model.ConstraintLink;

public class ConstraintPart extends AbstractGraphicalEditPart implements NodeEditPart {
	
	@Override
	protected IFigure createFigure() {
		Constraint c = (Constraint)getModel();
		return new ConstraintFigure(new Label(c.getName()));
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	protected List<ConstraintLink> getModelSourceConnections()
	{
		return null;
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	protected List<ConstraintLink> getModelTargetConnections()
	{
		Constraint c = (Constraint) getModel();
		ArrayList<ConstraintLink> list = new ArrayList<ConstraintLink>();
		list.add(c.getLinkToNode());
		return list;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

}
