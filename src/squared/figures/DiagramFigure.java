package squared.figures;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class DiagramFigure extends FreeformLayer implements MouseListener {

	public DiagramFigure() {
		setOpaque(true);
		addMouseListener(this);
		
		FreeformLayout layout = new FreeformLayout();
		setLayoutManager(layout);
	}
	
	@Override
	public void addMouseListener(MouseListener listener) {
		// TODO Auto-generated method stub
		super.addMouseListener(listener);
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		System.out.println("mouse pressed on diagram");
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		System.out.println("mouse released on diagram");
	}
	
}
