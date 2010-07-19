package squared.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import squared.figures.ConstraintFigure;
import squared.model.Constraint;

public class ConstraintPart extends AbstractGraphicalEditPart {
	
	@Override
	protected IFigure createFigure() {
		Constraint c = (Constraint)getModel();
		return new ConstraintFigure(new Label(c.getName()));
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
}
