package squared.model;

import java.util.ArrayList;

/**
 * @model
 */
public class Node extends DiagramElement {
	
	private String name = "Node name";
	private String comment = "Node comment";
	
	private NodeLink parentLink = null;
	private Node parent = null;
	private ArrayList<NodeLink> childrenLinks = new ArrayList<NodeLink>();
	private ArrayList<Node> children = new ArrayList<Node>();
	
	private ArrayList<Constraint> constraints;
	
		
	public Node(String name)
	{
		setName(name);
		constraints = new ArrayList<Constraint>();
	}
	
//	public Node(Node parent, String name)
//	{
//		setName(name);
//	}
	
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
		return parentLink.getParentNode();
	}
	
	public void setParentLink(NodeLink parent)
	{
		this.parentLink = parent;
	}
	
	public NodeLink getParentLink()
	{
		return parentLink;
	}
	
	public void addChild(Node child)
	{
		children.add(child);
	}
	
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	
	public void addChildLink(NodeLink link)
	{
		childrenLinks.add(link);
	}
	
	public ArrayList<NodeLink> getChildrenLinks()
	{
		return childrenLinks;
	}
	
	public void addConstraint(Constraint con)
	{
		constraints.add(con);
	}
	
	public ArrayList<Constraint> getConstraints()
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
