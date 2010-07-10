package squared.model;

import java.util.ArrayList;

/**
 * @model
 */
public class Node {
	
	private String name = "Node name";
	private String comment = "Node comment";
	
	private NodeLink parentLink = null;
	private ArrayList<NodeLink> childrenLinks = new ArrayList<NodeLink>();
	private ArrayList<ConstraintLink> constraintLinks = new ArrayList<ConstraintLink>();
		
	public Node(String name)
	{
		setName(name);
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
	
	public void setParentLink(NodeLink parent)
	{
		this.parentLink = parent;
	}
	
	public NodeLink getParentLink()
	{
		return parentLink;
	}
	
	public Node getParent()
	{
		return parentLink.getParentNode();
	}
	
	public void addChildLink(NodeLink link)
	{
		childrenLinks.add(link);
	}
	
	public ArrayList<NodeLink> getChildrenLinks()
	{
		return childrenLinks;
	}
	
	public ArrayList<ConstraintLink> getConstraintLinks()
	{
		return constraintLinks;
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
