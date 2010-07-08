package squared.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Triangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class ConstraintPart extends AbstractGraphicalEditPart {
	
	public ConstraintPart(Object model) {
		setModel(model);
	}

	@Override
	protected IFigure createFigure() {
		return new Triangle();
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

}
