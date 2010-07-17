package squared.layout;

import java.util.Iterator;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import squared.figures.ConstraintFigure;
import squared.figures.DiagramFigure;
import squared.figures.NodeFigure;
import squared.model.Diagram;
import squared.part.DiagramPart;

public class DiagramLayout extends FreeformLayout {

	private DiagramPart diagramPart;
	private Diagram diagram;
	
	public DiagramLayout(DiagramPart diagram)
	{
		this.diagramPart = diagram;
		this.diagram = (Diagram)diagram.getModel();
	}
	
	public void layout(IFigure container)
	{
		Iterator children = container.getChildren().iterator();
		Point offset = getOrigin(container);
		IFigure f;
		int x = 50;
		while (children.hasNext()) {
			f = (IFigure)children.next();
			Rectangle bounds = (Rectangle)getConstraint(f);
			if (bounds == null) continue;

			if (bounds.width == -1 || bounds.height == -1) {
				Dimension preferredSize = f.getPreferredSize(bounds.width, bounds.height);
				bounds = bounds.getCopy();
				if (bounds.width == -1)
					bounds.width = preferredSize.width;
				if (bounds.height == -1)
					bounds.height = preferredSize.height;
			}
			bounds = bounds.getTranslated(offset);
			bounds = bounds.getTranslated(x, x);
			f.setBounds(bounds);
		}
	}
	
	@Override
	public Object getConstraint(IFigure child)
	{
		if (child instanceof NodeFigure)
			return new Rectangle(0, 0, 75, 75);
		else if (child instanceof ConstraintFigure)
			return new Rectangle(150, 0, 200, 50);
		else
			return super.getConstraint(child);
//		Object constraint = constraints.get(child);
//		if (constraint != null || constraint instanceof Rectangle)
//		{
//			return (Rectangle)constraint;
//		}
//		else
//		{
//			Rectangle currentBounds = child.getBounds();
//			return new Rectangle(currentBounds.x, currentBounds.y, -1,-1);
//		}
	}
	
	@Override
	public Point getOrigin(IFigure figure) {
		if (figure instanceof DiagramFigure)
			return new Point(50, 50);
		else
			return super.getOrigin(figure);
	}
}
