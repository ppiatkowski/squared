/**
* This is the example editor skeleton that is build
* in <i>Building an editor</i> in chapter <i>Introduction to GEF</i>.
*
* @see org.eclipse.ui.part.EditorPart
*/
package squared.editor;

import java.io.InputStream;
import java.io.ObjectInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

import squared.model.Diagram;

//import squared.action.SchemaContextMenuProvider;
//import squared.directedit.StatusLineValidationMessageHandler;
//import squared.dnd.DataEditDropTargetListener;
import squared.part.factory.SquaredEditPartFactory;

public class QueryEditor extends GraphicalEditorWithFlyoutPalette
{
	/** the <code>EditDomain</code>, will be initialized lazily */
	private DefaultEditDomain editDomain;
	
	/** the graphical viewer */
	private GraphicalViewer graphicalViewer;
	
	private Diagram diagram;
	
	public QueryEditor()
	{
		System.out.println("QueryEditor constr");
		editDomain = new DefaultEditDomain(this);
		setEditDomain(editDomain);
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		System.out.println("Editor.init(site, input)");
		
		// store site and input
		setSite(site);
		setInput(input);

		// add CommandStackListener
		getCommandStack().addCommandStackListener(this);

		// add selection change listener
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);

		// initialize actions
		createActions();
		
	}
	
	public void setFocus()
	{
		// what should be done if the editor gains focus?
		// it's your part
	}
	
	public void doSaveAs()
	{
		// your save as implementation here
	}
	
	public boolean isDirty()
	{
		return false;
	}
	
	public boolean isSaveAsAllowed()
	{
		// your implementation here
		return false;
	}
	
//	public void gotoMarker(IMarker marker)
//	{}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		createGraphicalViewer(parent);
	}

	@Override
	protected void initializeGraphicalViewer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*
	* Returns the <code>EditDomain</code> used by this editor.
	* @return the <code>EditDomain</code> used by this editor
	*/
	public DefaultEditDomain getEditDomain()
	{
		if (editDomain == null)
			editDomain = new DefaultEditDomain(null);
		return editDomain;
	}
	
	/**
	 * Creates a new <code>GraphicalViewer</code>, configures, registers and
	 * initializes it.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return a new <code>GraphicalViewer</code>
	 */
	protected void createGraphicalViewer(Composite parent)
	{

		IEditorSite editorSite = getEditorSite();
		GraphicalViewer viewer = new ScrollingGraphicalViewer();
		viewer.createControl(parent);			
		
		// configure the viewer
		viewer.getControl().setBackground(ColorConstants.white);
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		//viewer.addDropTargetListener(new DataEditDropTargetListener(viewer));

		// initialize the viewer with input
		viewer.setEditPartFactory(getEditPartFactory());
		GraphicalViewerKeyHandler graphicalViewerKeyHandler = new GraphicalViewerKeyHandler(viewer);
		KeyHandler parentKeyHandler = graphicalViewerKeyHandler.setParent(getCommonKeyHandler());
		viewer.setKeyHandler(parentKeyHandler);

		// hook the viewer into the EditDomain
		getEditDomain().addViewer(viewer);

		// acticate the viewer as selection provider for Eclipse
		getSite().setSelectionProvider(viewer);

		//viewer.setContents(getDiagram());

//		ContextMenuProvider provider = new SchemaContextMenuProvider(viewer, getActionRegistry());
//		viewer.setContextMenu(provider);
//		getSite().registerContextMenu("squared.editor.contextmenu", provider, viewer);

		this.graphicalViewer = viewer;

	}
	
	/**
	 * Returns the <code>EditPartFactory</code> that the
	 * <code>GraphicalViewer</code> will use.
	 * 
	 * @return the <code>EditPartFactory</code>
	 */
	protected EditPartFactory getEditPartFactory()
	{
		// todo return your EditPartFactory here
		return new SquaredEditPartFactory();
	}
	
	protected KeyHandler getCommonKeyHandler()
	{
		KeyHandler sharedKeyHandler = new KeyHandler();
		sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0), getActionRegistry().getAction(
				GEFActionConstants.DELETE));
		sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0), getActionRegistry().getAction(
				GEFActionConstants.DIRECT_EDIT));

		return sharedKeyHandler;
	}
	
	/**
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
//	protected void setInput(IEditorInput input)
//	{
//		super.setInput(input);
//
//		IFile file = ((IFileEditorInput) input).getFile();
//		try
//		{
//			setPartName(file.getName());
//			InputStream is = file.getContents(true);
//			ObjectInputStream ois = new ObjectInputStream(is);
//			diagram = (Diagram) ois.readObject();
//			ois.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			diagram = null;
//		}
//	}
	
	public Diagram getDiagram()
	{
		return diagram;
	}
	
}

