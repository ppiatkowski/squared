package squared.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.ToolbarLayout;

public class NodeFigure extends Ellipse implements MouseMotionListener, MouseListener { //Clickable {
	
	private Label nameLabel;
	
	public NodeFigure(Label name)
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		
		nameLabel = name;
		setSize(100, 100);
		setFill(true);
		setOutline(true);
		//setEnabled(false);
		ToolbarLayout layout = new ToolbarLayout();
//		layout.setVertical(true);
//		layout.setStretchMinorAxis(true);
		setLayoutManager(layout);
		setBackgroundColor(ColorConstants.green);
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

	@Override
	public void mouseDragged(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		setBackgroundColor(ColorConstants.darkGreen);
	}

	@Override
	public void mouseExited(MouseEvent me) {
		setBackgroundColor(ColorConstants.green);
	}

	@Override
	public void mouseHover(MouseEvent me) {
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
	}

}
