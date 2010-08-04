package squared.model;

import java.util.List;
import java.util.Vector;

/**
 * TODO refactor this class so it's not singleton
 * @model
 */
public class Diagram {
	
	enum ModelEvent {
		EVENT_FIELD_CONSTRAINED,
		EVENT_NODE_ADDED
	};

	protected Vector<DiagramElement> diagramElements = new Vector<DiagramElement>();
	protected DiagramElement root = null;
	protected Vector<IModelChangeListener> listeners = new Vector<IModelChangeListener>();
	
	private static Diagram instance = null;
	
	public static Diagram getInstance() {
		if (instance == null) {
			instance = new Diagram();
		}
		return instance;
	}
	
	private Diagram() {
	}

	public void addElement(DiagramElement n) {
		if (diagramElements.isEmpty()) {
			root = n;
		}
		diagramElements.add(n);
		event(ModelEvent.EVENT_NODE_ADDED, ((Node) n).getParent(), n);
	}

	public List<DiagramElement> getElements() {
		return diagramElements;
	}
	
	public DiagramElement getRootElement() {
		return root;
	}
	
	public void clear() {
		root = null;
		diagramElements.clear();
	}
	
	public boolean isEmpty() {
		return diagramElements.isEmpty();
	}
	
	public void addModelChangeListener(IModelChangeListener l) {
		listeners.add(l);
	}
	
	public void removeModelChangeListener(IModelChangeListener l) {
		listeners.removeElement(l);
	}
	
	public void event(ModelEvent eventId, Object param1, Object param2) {
		switch (eventId) {
		case EVENT_FIELD_CONSTRAINED:
			Node node = (Node) param1;
			String fieldName = (String) param2;
			for (IModelChangeListener l : listeners) {
				l.fieldConstrained(node, fieldName);
			}
			break;
			
		case EVENT_NODE_ADDED:
			Node parent = (Node) param1;
			Node child = (Node) param2;
			for (IModelChangeListener l : listeners) {
				l.nodeAdded(parent, child);
			}
			break;

		default:
			System.out.println("Diagram event ignored.");
			break;
		}
	}
	
}
