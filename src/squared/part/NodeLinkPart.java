package squared.part;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.swt.SWT;

import squared.policy.NodeLinkPolicy;

public class NodeLinkPart extends AbstractConnectionEditPart {
	protected Label label;

	public NodeLinkPart(String label) {
		super();
		this.label = new Label(label);
		this.label.setOpaque(true);
		this.label.setBackgroundColor(ColorConstants.buttonLightest);
		this.label.setBorder(new LineBorder());
	}
	
	/**
	 * @see org.eclipse.gef.EditPart#activate()
	 */
	public void activate() {
		super.activate();
	}
	
	
	/**
	 * @see org.eclipse.gef.EditPart#activate()
	 */
	public void deactivate() {
		super.deactivate();
	}
	
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies()
	{
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeLinkPolicy());
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	protected IFigure createFigure()
	{
		PolylineConnection conn = (PolylineConnection) super.createFigure();
		conn.setConnectionRouter(new BendpointConnectionRouter());
		conn.setTargetDecoration(new PolygonDecoration());
		conn.setAntialias(SWT.ON);
		conn.add(label, new MidpointLocator(conn, 0));
		return conn;
	}

}
