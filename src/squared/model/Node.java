package squared.model;

import java.util.Vector;

/**
 * @model
 */
public class Node {
	
	private String name = "Node name";
	private String comment = "Node comment";
	private Vector<Constraint> constraints = new Vector<Constraint>();
	private Vector<Node> children = new Vector<Node>();
	private Node parent = null;
		
	public Node(String name)
	{
		setName(name);
	}
	
	public Node(Node parent, String name)
	{
		setParent(parent);
		setName(name);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public Vector<Node> getChildren()
	{
		return children;
	}
	
	public Vector<Constraint> getConstraints()
	{
		return constraints;
	}
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	public String getComment()
	{
		return comment;
	}
}
