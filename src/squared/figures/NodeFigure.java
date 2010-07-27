package squared.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import squared.editor.QueryEditor;
import squared.model.Node;
import squared.utils.Utils;

import com.db4o.reflect.ReflectField;

class MouseAwareLabel extends Label implements MouseMotionListener, MouseListener {
	public static Font hoverFont = null;
	public static Font normalFont = null;
	
	static {
		hoverFont = new Font(Display.getCurrent(), new FontData("", 8, SWT.BOLD));
		normalFont = new Font(Display.getCurrent(), new FontData("", 8, SWT.NORMAL));
	}
	
	public MouseAwareLabel(String str) {
		super(str);
		setFont(normalFont);
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		setFont(hoverFont);
	}

	@Override
	public void mouseExited(MouseEvent me) {
		setFont(normalFont);
	}

	@Override
	public void mouseHover(MouseEvent me) {
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		if (getParent() != null) {
			((CompartmentFigure)getParent()).onLabelClicked(this);
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		System.out.println("mouse released");
	}
	
}

class CompartmentFigure extends Figure {
	public enum Mode {
		ATTRIBUTE,
		PRIMITIVE
	}
	
	protected Mode mode;

	public CompartmentFigure(Mode mode) {
		this.mode = mode;
	
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(false);
		layout.setSpacing(2);
		setLayoutManager(layout);
		setBorder(new CompartmentFigureBorder());
	}

	public class CompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(1, 0, 0, 0);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
					tempRect.getTopRight());
		}
	}
	
	public void onLabelClicked(MouseAwareLabel label) {
		switch (this.mode) {
		case ATTRIBUTE:
			if (getParent() != null) {
				QueryEditor.getInstance().spawnChildNode(
						((NodeFigure)getParent()).node,
						label.getText());
			}
			break;
		case PRIMITIVE:
			System.out.println("constrain primitive "+label.getText());
			break;
		default:
			break;
		}
	}

}

public class NodeFigure extends RoundedRectangle {
	public static final int LEFT_MARGIN = 3;
	public static final int RIGHT_MARGIN = 3;
	public static final int TOP_MARGIN = 3;
	public static final int BOTTOM_MARGIN = 3;
	
	protected Node node;
	protected Label nameLabel;
	
	protected CompartmentFigure attributesFigure = new CompartmentFigure(CompartmentFigure.Mode.ATTRIBUTE);
	protected CompartmentFigure primitivesFigure = new CompartmentFigure(CompartmentFigure.Mode.PRIMITIVE);
	
	public NodeFigure(Node data)
	{
		this.node = data;
		
		nameLabel = new Label(node.getName());
		nameLabel.setFont(new Font(Display.getCurrent(), new FontData("", 10, SWT.BOLD)));
		setFill(true);
		setOutline(true);
		ToolbarLayout layout = new ToolbarLayout();
//		layout.setVertical(true);
		setLayoutManager(layout);
		setBackgroundColor(ColorConstants.green);
		setOpaque(true);
		
		nameLabel.setForegroundColor(ColorConstants.black);
		add(nameLabel);
		
//		Label attribute1 = new Label("field 1", new Image(d, 
//				UMLClassFigure.class.getResourceAsStream("field_private_obj.gif")));

		ReflectField[] fields = node.getData().getDeclaredFields();
		for (ReflectField field : fields) {
			if (Utils.isIgnored(field.getName()))
				continue;
			
			if (field.getFieldType().isPrimitive())
				primitivesFigure.add(new MouseAwareLabel(field.getName()));
			else
				attributesFigure.add(new MouseAwareLabel(field.getName()));
		}
		
	    add(attributesFigure);
	    add(primitivesFigure);
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
	
	public Rectangle getConstraint() {
		Rectangle constr = new Rectangle(0, 0, -1, -1);
		Dimension labelDim = FigureUtilities.getStringExtents(nameLabel.getText(), nameLabel.getFont());
		constr.width = labelDim.width;
		constr.height = labelDim.height;
		
		CompartmentFigure[] metaList = { attributesFigure, primitivesFigure };
		for (CompartmentFigure compartment : metaList) {
			for (Object label : compartment.getChildren()) {
				if (label instanceof Label) {
					labelDim = FigureUtilities.getStringExtents(((Label) label).getText(), ((Label) label).getFont());
					constr.height += labelDim.height;
					if (labelDim.width > constr.width) {
						constr.width = labelDim.width;
					}
				}
			}
			constr.height += TOP_MARGIN;
		}
		constr.height += TOP_MARGIN + BOTTOM_MARGIN;
		constr.width += LEFT_MARGIN + RIGHT_MARGIN;
		
		return constr;
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
