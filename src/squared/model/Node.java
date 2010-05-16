package squared.model;

import java.util.Vector;

/**
 * @model
 */
public class Node {
	
	private String m_name = "Node name";
	private String m_comment = "Node comment";
	private Vector<Constraint> m_constraints = new Vector<Constraint>();
	private Vector<Node> m_children = new Vector<Node>();
	private Node m_parent = null;
		
	Node(String name)
	{
		setName(name);
	}
	
	Node(Node parent, String name)
	{
		setParent(parent);
		setName(name);
	}
	
	void setName(String name)
	{
		m_name = name;
	}
	
	String getName()
	{
		return m_name;
	}
	
	void setParent(Node parent)
	{
		m_parent = parent;
	}
	
	Node getParent()
	{
		return m_parent;
	}
	
	Vector<Node> getChildren()
	{
		return m_children;
	}
	
	Vector<Constraint> getConstraints()
	{
		return m_constraints;
	}
	
	void setComment(String comment)
	{
		m_comment = comment;
	}
	
	String getComment()
	{
		return m_comment;
	}
}
