package squared.model;

import java.util.Vector;

/**
 * @model
 */
public class Diagram {
	
	private Vector<Node> m_nodes = new Vector<Node>();

	Diagram()
	{
		
	}
	
	Vector<Node> getNodes()
	{
		return m_nodes;
	}
}
