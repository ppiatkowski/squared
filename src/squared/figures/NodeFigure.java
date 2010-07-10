package squared.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class NodeFigure extends RoundedRectangle {
	
	private Label nameLabel;
	
	public NodeFigure(Label name)
	{
		nameLabel = name;
		setSize(100, 100);
		setFill(true);
		setOutline(true);
		//setEnabled(false);
		ToolbarLayout layout = new ToolbarLayout();
		layout.setVertical(true);
		layout.setStretchMinorAxis(true);
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(ColorConstants.cyan);
		setForegroundColor(ColorConstants.lightGreen);
		setOpaque(true);

		name.setForegroundColor(ColorConstants.black);
		add(name);

	}

	public void setSelected(boolean isSelected)
	{
		LineBorder lineBorder = (LineBorder) getBorder();
		if (isSelected)
		{
			lineBorder.setWidth(2);
		}
		else
		{
			lineBorder.setWidth(1);
		}
	}

	
	/**
	 * @return returns the label used to edit the name
	 */
	public Label getNameLabel()
	{
		return nameLabel;
	}

	@Override
	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
	}

}
