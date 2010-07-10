package squared.part.anchor;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

public class NodeTargetAnchor extends AbstractConnectionAnchor {
	
	public NodeTargetAnchor(IFigure source) {
		super(source);
	}

	@Override
	public Point getLocation(Point reference) {
		// TODO Auto-generated method stub
		return reference;
	}

}
