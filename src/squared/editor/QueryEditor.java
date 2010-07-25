/**
* This is the example editor skeleton that is build
* in <i>Building an editor</i> in chapter <i>Introduction to GEF</i>.
*
* @see org.eclipse.ui.part.EditorPart
*/
package squared.editor;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import squared.Texts;
import squared.model.Diagram;
import squared.model.Node;
import squared.model.NodeLink;
import squared.part.factory.SquaredEditPartFactory;

public class QueryEditor extends GraphicalEditor
{
	/** the <code>EditDomain</code>, will be initialized lazily */
	private DefaultEditDomain editDomain;
	
	/** the graphical viewer */
	private GraphicalViewer graphicalViewer;
	
	private Diagram diagram;
	
	public QueryEditor()
	{
		editDomain = new DefaultEditDomain(this);
		setEditDomain(editDomain);
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		
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
		Control figureCanvas = graphicalViewer.getControl();
		FileDialog dialog = new FileDialog(figureCanvas.getShell(), SWT.SAVE);
		
		try {
			IPath workspace = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//			String filename = window.getActivePage().getActiveEditor().getEditorInput().getName();
			
			dialog.setFilterPath(workspace.toPortableString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dialog.setOverwrite(true);
		dialog.setText(Texts.QUERY_EDITOR_SAVE_AS_IMG);
		String saveLocation = dialog.open();
		
		ScalableFreeformRootEditPart rootEditPart = (ScalableFreeformRootEditPart)graphicalViewer.getRootEditPart();
		IFigure rootFigure = ((LayerManager) rootEditPart).getLayer(LayerConstants.PRINTABLE_LAYERS);
		Rectangle rootFigureBounds = rootFigure.getBounds();
		

		Image img = new Image(Display.getDefault(), rootFigureBounds.width, 
		rootFigureBounds.height);
		GC imageGC = new GC(img);
		figureCanvas.print(imageGC);

		ImageLoader imgLoader = new ImageLoader();
		imgLoader.data = new ImageData[] { img.getImageData() };
		imgLoader.save(saveLocation, SWT.IMAGE_PNG);
	}
	
	public boolean isDirty()
	{
		return false;
	}
	
	public boolean isSaveAsAllowed()
	{
		return true;
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
		setGraphicalViewer(viewer);
		configureGraphicalViewer();
		hookGraphicalViewer();
		initializeGraphicalViewer();
		
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

		// this makes background unselectable (maybe root part takes whole space?)
		viewer.setContents(getDiagram());

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
		if (diagram == null)
		{
			diagram = new Diagram();
			Node parent = new Node("parent");
			Node child = new Node("child 1");
			Node child2 = new Node("child 2");
			Node child3 = new Node("child 3");
			NodeLink link = new NodeLink(parent, child, "test link");
			NodeLink link2 = new NodeLink(parent, child2, "test link");
			NodeLink link3 = new NodeLink(parent, child3, "bla");
			
			Node grandChild = new Node("grand child");
			Node gChild2 = new Node("g child 2");
			NodeLink link4 = new NodeLink(child3, grandChild);
			NodeLink link5 = new NodeLink(child3, gChild2);
			
			
			diagram.addElement(parent);
			diagram.addElement(child);
			diagram.addElement(child2);
			diagram.addElement(child3);
			diagram.addElement(grandChild);
			diagram.addElement(gChild2);
		}
		return diagram;
	}
	
}


