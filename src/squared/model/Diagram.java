package squared.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @model
 */
public class Diagram {

	private List<DiagramElement> diagramElements = new ArrayList<DiagramElement>();

	public Diagram() {

	}

	public void addElement(DiagramElement n) {
		diagramElements.add(n);
	}

	public List<DiagramElement> getElements() {
		return diagramElements;
	}
	
}
