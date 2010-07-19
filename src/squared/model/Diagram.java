package squared.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @model
 */
public class Diagram {

	private List<DiagramElement> diagramElements = new ArrayList<DiagramElement>();
	private DiagramElement root = null;

	public Diagram() {

	}

	public void addElement(DiagramElement n) {
		if (diagramElements.isEmpty()) {
			root = n;
		}
		
		diagramElements.add(n);
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
}
