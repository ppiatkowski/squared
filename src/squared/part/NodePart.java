package squared.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.NodeFigure;
import squared.model.Node;
import squared.model.NodeLink;

public class NodePart extends AbstractGraphicalEditPart implements NodeEditPart {
	
	@Override
	protected IFigure createFigure() {
		return new NodeFigure(((Node)getModel()));
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	protected List<NodeLink> getModelSourceConnections()
	{
		Node n = (Node) getModel();
		return n.getChildrenLinks();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	protected List<NodeLink> getModelTargetConnections()
	{
		Node n = (Node) getModel();
		List<NodeLink> list = new ArrayList<NodeLink>();
		if (n.getParentLink() != null) {
			list.add(n.getParentLink());
		}
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
	
	public void setSelected(int value)
	{
		super.setSelected(value);
		if (value != EditPart.SELECTED_NONE)
			((NodeFigure) getFigure()).setAlpha(100);
		else
			((NodeFigure) getFigure()).setAlpha(255);
	}
}
