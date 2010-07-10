package squared.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.NodeFigure;
import squared.model.Node;
import squared.model.NodeLink;

public class NodePart extends AbstractGraphicalEditPart implements NodeEditPart {
	
	@Override
	protected IFigure createFigure() {
		String name = ((Node)getModel()).getName();
		return new NodeFigure(new Label(name));
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	protected List getModelChildren() {
//		Node n = (Node) getModel();
//		return n.getChildrenLinks();
//	}
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	protected List getModelSourceConnections()
	{
		Node n = (Node) getModel();
		return n.getChildrenLinks();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	protected List getModelTargetConnections()
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
}
