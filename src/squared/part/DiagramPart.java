package squared.part;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.DiagramFigure;
import squared.model.Diagram;
import squared.model.Node;

public class DiagramPart extends AbstractGraphicalEditPart { // FreeformGraphicalRootEditPart {
	
	@Override
	protected IFigure createFigure() {
		Figure f = new DiagramFigure();
		f.setLayoutManager(new FreeformLayout());
		return f;
	}

	@Override
	protected void createEditPolicies() {
		//installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContainerEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, null);
	}
	
	public Diagram getDiagram() {
		return ((Diagram) getModel());
	}

	protected List<Node> getModelChildren() {
		return getDiagram().getNodes();
	}
	
	public boolean isSelectable()
	{
		return false;
	}
}
