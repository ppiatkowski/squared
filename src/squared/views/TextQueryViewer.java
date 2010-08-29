package squared.views;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TextQueryViewer extends ContentViewer {
	
	protected Text text;
	
	public TextQueryViewer(Composite parent) {
		
		text = new Text(parent, SWT.READ_ONLY | SWT.WRAP);
		super.hookControl(text);
	}

	@Override
	public Control getControl() {
		return text;
	}

	@Override
	public ISelection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh() {

	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		// TODO Auto-generated method stub

	}
	
	public void setText(String t) {
		text.setText(t);
	}

}
