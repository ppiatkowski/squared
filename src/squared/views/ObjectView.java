package squared.views;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import squared.Activator;
import squared.ClassReflection;
import squared.DBConnection;
import squared.Texts;
import squared.editor.QueryEditor;
import squared.utils.TreeNode;

import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.Db4oException;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ObjectView extends ViewPart {
	
	/**
	 * Action that changes icon when it's inactive
	 * @author pablo
	 *
	 */
	private class StateAction extends Action {
		private ImageDescriptor enabledIcon;
		private ImageDescriptor disabledIcon;
		
		public void setIconForState(boolean state, String iconPath)
		{
		    try {
		    	URL url = new URL(Activator.getDefault().getBundle().getEntry("/"), iconPath);
		    	if (state)
		    		enabledIcon = ImageDescriptor.createFromURL(url);
		    	else
		    		disabledIcon = ImageDescriptor.createFromURL(url);
		    } catch (MalformedURLException e) {
		    	System.err.println("StateAction.setEnabledIcon() ERROR: malformed URL.");
		    	e.printStackTrace();
		    }
		}
		
		public void setEnabled(boolean status)
		{
			super.setEnabled(status);
		    setImageDescriptor(status ? enabledIcon : disabledIcon);
		}
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "squared.views.ObjectView";

	private TreeViewer viewer;
	private ViewContentProvider objectViewContentProvider;
	private DrillDownAdapter drillDownAdapter;
	private Action actionOpenDB;
	private StateAction actionCloseDB;
	private Action doubleClickAction;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class TreeObject<T> implements IAdaptable {
		private String name;
		private T dataReference;
		private TreeParent<T> parent;
		
		public TreeObject(String name, T data) {
			this.name = name;
			this.dataReference = data;
		}
		public String getName() {
			return name;
		}
		public T getDataReference() {
			return dataReference;
		}
		public void setParent(TreeParent<T> parent) {
			this.parent = parent;
		}
		public TreeParent<T> getParent() {
			return parent;
		}
		public String toString() {
			return getName();
		}
		public Object getAdapter(Class key) {
			return null;
		}
	}
	
	class TreeParent<T> extends TreeObject<T> {
		private ArrayList children;
		public TreeParent(String name, T data) {
			super(name, data);
			children = new ArrayList();
		}
		public void addChild(TreeObject<T> child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject<T> child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject<T> [] getChildren() {
			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private TreeParent<ClassReflection> invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
		
		public void initialize() {
			if (invisibleRoot == null) {
				invisibleRoot = new TreeParent("", null);
			}
			if (DBConnection.getInstance().isOpened())
			{
				for (TreeNode<ClassReflection> node : DBConnection.getInstance().getDBReflection().getRootElement().getChildren())
				{
					traverse(node, invisibleRoot);
				}
			}
			else
			{
				invisibleRoot = null;
				invisibleRoot = new TreeParent("", null);
			}
		}
		
		private void traverse(TreeNode<ClassReflection> node, TreeParent<ClassReflection> parentNode) {
			TreeParent<ClassReflection> newNode = new TreeParent<ClassReflection>(node.getData().getType().getName(), node.getData());
			parentNode.addChild(newNode);
			for (TreeNode<ClassReflection> child : node.getChildren()) {
				traverse(child, newNode);
			}
		}
		
		public TreeParent<ClassReflection> getRoot() {
			return invisibleRoot;
		}
		
	}
	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			String name = "";
			if (obj instanceof TreeParent) {
				name = ((ClassReflection)((TreeParent)obj).getDataReference()).getDescent();
			}
			return name + " ("+ obj.toString() + ")";
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
			   imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			else if (obj instanceof TreeObject)
				imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public ObjectView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		objectViewContentProvider = new ViewContentProvider();
		viewer.setContentProvider(objectViewContentProvider);
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		getSite().setSelectionProvider(viewer);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "squared.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ObjectView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actionOpenDB);
		manager.add(new Separator());
		manager.add(actionCloseDB);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionOpenDB);
		manager.add(actionCloseDB);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionOpenDB);
		manager.add(actionCloseDB);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		actionOpenDB = new Action() {
			public void run() {
                FileDialog fd = new FileDialog(viewer.getControl().getShell(), SWT.OPEN);
                fd.setText(Texts.OBJ_VIEW_OPEN_DIALOG_TITLE);
                String selected = fd.open();
                
                if (!selected.equals(""))
                {
                	try {
                		DBConnection.getInstance().open(selected);
                		actionCloseDB.setEnabled(true);
                		
                		// refresh Object View
                		objectViewContentProvider.initialize();
                		viewer.refresh();
                    } catch (DatabaseFileLockedException e) {
                        MessageDialog.openError(viewer.getControl().getShell(), 
                        		Texts.OBJ_VIEW_OPEN_DB_FAILED, 
                        		e.getMessage() + "\n\n" + Texts.OBJ_VIEW_DB_LOCKED);
                        
                    } catch (Db4oException e) {
                        MessageDialog.openError(viewer.getControl().getShell(),
                        		Texts.OBJ_VIEW_OPEN_DB_FAILED, e.getMessage());
            			
            		} finally {
            			
            		}
                }
                
			}
		};
		actionOpenDB.setText(Texts.OBJ_VIEW_OPEN_DB);
		actionOpenDB.setToolTipText(Texts.OBJ_VIEW_OPEN_DB_TOOLTIP);
		
	    try {
	    	URL url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/open_db.png");
	    	actionOpenDB.setImageDescriptor(ImageDescriptor.createFromURL(url));
	    } catch (MalformedURLException e) {
	    	System.err.println("ObjectView.makeActions() ERROR: malformed URL.");
	    	e.printStackTrace();
	    }
		
		
		actionCloseDB = new StateAction() {
			public void run() {
				DBConnection.getInstance().close();
				actionCloseDB.setEnabled(false);
				
        		// refresh Object View
        		objectViewContentProvider.initialize();
        		viewer.refresh();
			}
		};
		actionCloseDB.setIconForState(true, "icons/close.png");
		actionCloseDB.setIconForState(false, "icons/close_inactive.png");
		actionCloseDB.setText(Texts.OBJ_VIEW_CLOSE_DB);
		actionCloseDB.setToolTipText(Texts.OBJ_VIEW_CLOSE_DB_TOOLTIP);
		actionCloseDB.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		actionCloseDB.setEnabled(false);
	    try {
	    	URL url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/close_inactive.png");
	    	actionCloseDB.setImageDescriptor(ImageDescriptor.createFromURL(url));
	    } catch (MalformedURLException e) {
	    	System.err.println("ObjectView.makeActions() ERROR: malformed URL.");
	    	e.printStackTrace();
	    }
		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				TreeParent<ClassReflection> obj = (TreeParent<ClassReflection>)((IStructuredSelection)selection).getFirstElement();
				if ((obj.getChildren().length != 0) && (QueryEditor.getInstance() != null)) {
					QueryEditor.getInstance().setDiagramRoot(obj.getDataReference());
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"ObjectView",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}