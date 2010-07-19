package squared.layout;

import java.util.List;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.ConstraintFigure;
import squared.figures.DiagramFigure;
import squared.figures.NodeFigure;
import squared.model.Diagram;
import squared.model.Node;
import squared.part.DiagramPart;
import squared.part.NodePart;
import squared.part.factory.SquaredEditPartFactory;

public class DiagramLayout extends FreeformLayout {
	public final int TOP_MARGIN = 25;
	public final int VERTICAL_GAP = 50;
	
	private DiagramPart diagramPart;
	private Diagram diagram;
	
	public DiagramLayout(DiagramPart diagram)
	{
		this.diagramPart = diagram;
		this.diagram = (Diagram)diagram.getModel();
	}
	
	public void layout(IFigure container)
	{
		List<EditPart> children = diagramPart.getChildren();
		if (children.isEmpty())
			return;
			
		EditPart diagramRootPart = children.get(0);
		IFigure root = ((AbstractGraphicalEditPart)diagramRootPart).getFigure();
		
		Dimension canvasSize = container.getSize();
		Point canvasCenter = new Point(canvasSize.width >> 1, canvasSize.height >> 1);
		Rectangle bounds = (Rectangle)getConstraint(root);
		Dimension preferredSize = root.getPreferredSize();
		
		bounds.x = canvasCenter.x - (preferredSize.width >> 1);
		bounds.y = TOP_MARGIN;
		root.setBounds(bounds);
		
		for (int i = 1; i < children.size(); i++)
		{
			
		}
		
		traverse((AbstractGraphicalEditPart)diagramRootPart, 
				bounds.y + bounds.height + VERTICAL_GAP, 
				canvasSize);
	}
	
	private void traverse(AbstractGraphicalEditPart element, int ceiling, Dimension canvasSize)
	{
		Node node = (Node)element.getModel();
		List<Node> children = node.getChildren();
		
		int childCount = children.size();
		int margin = canvasSize.width / (childCount  + 1);
		
		for (int i = 0; i < children.size(); i++) {
			Node child = (Node)children.get(i);
			NodePart childPart = ((SquaredEditPartFactory)element.getViewer().getEditPartFactory()).getPartByModel(child);
			IFigure childFigure = childPart.getFigure();
			
			Rectangle bounds = (Rectangle)getConstraint(childFigure);
			Dimension preferredSize = childFigure.getPreferredSize();
			bounds.x = margin * (i+1) - (preferredSize.width >> 1);
			bounds.y = ceiling;
			childFigure.setBounds(bounds);
			
			traverse(childPart, ceiling + bounds.height + VERTICAL_GAP, canvasSize);
		}

	}
	
	@Override
	public Object getConstraint(IFigure child)
	{
		if (child instanceof NodeFigure)
			return new Rectangle(0, 0, 75, 50);
		else if (child instanceof ConstraintFigure)
			return new Rectangle(150, 0, 200, 50);
		else
			return super.getConstraint(child);
	}
	
	@Override
	public Point getOrigin(IFigure figure) {
		if (figure instanceof DiagramFigure)
			return new Point(50, 50);
		else
			return super.getOrigin(figure);
	}
	
}
