package squared.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;

public class ConstraintFigure extends RectangleFigure {
	private Label nameLabel;
	
	public ConstraintFigure(Label n) {
		nameLabel = n;
		
		setSize(75, 75);
		setFill(true);
		setOutline(true);
		//setEnabled(false);
		ToolbarLayout layout = new ToolbarLayout();
		layout.setVertical(true);
		layout.setStretchMinorAxis(true);
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.red, 2));
		setBackgroundColor(ColorConstants.yellow);
		setForegroundColor(ColorConstants.red);
		setOpaque(true);

		nameLabel.setForegroundColor(ColorConstants.red);
		add(nameLabel);
	}
}
