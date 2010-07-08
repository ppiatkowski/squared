package squared.model;

import java.util.List;
import java.util.Vector;

/**
 * @model
 */
public class Diagram {

	private List<Node> nodes = new Vector<Node>();

	public Diagram() {

	}

	public void addNode(Node n) {
		nodes.add(n);
	}

	public List<Node> getNodes() {
		return nodes;
	}
}
