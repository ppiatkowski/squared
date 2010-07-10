package squared.part.anchor;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

public class ConstraintNodeAnchor extends AbstractConnectionAnchor {

	public ConstraintNodeAnchor(IFigure source) {
		super(source);
	}
	
	@Override
	public Point getLocation(Point reference) {
		// TODO Auto-generated method stub
		return reference;
	}

}
