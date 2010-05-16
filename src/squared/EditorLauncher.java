package squared;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorLauncher;

public class EditorLauncher implements IEditorLauncher {

	@Override
	public void open(IPath file) {
		// TODO Auto-generated method stub
		System.out.println("TODO open editor for file: "+ file.toString());
	}

}
