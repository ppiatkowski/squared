package squared.model;

import java.util.ArrayList;

import squared.ClassReflection;

/**
 * @model
 */
public class Node extends DiagramElement {
	
	protected ClassReflection clazz;
	
	protected NodeLink parentLink = null;
	protected Node parent = null;
	protected ArrayList<NodeLink> childrenLinks = new ArrayList<NodeLink>();
	protected ArrayList<Node> children = new ArrayList<Node>();
	protected ArrayList<Constraint> constraints;
		
	public Node(ClassReflection clazz)
	{
		this.clazz = clazz;
		
		constraints = new ArrayList<Constraint>();
	}
	
	public ClassReflection getData()
	{
		return clazz;
	}
	
	public String getDescent() 
	{
		return clazz.getDescent();
	}
	
	public String getName()
	{
		return clazz.getType().getName();
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
		child.setParent(this);
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
	
	public boolean alreadySpawned(String fieldName) {
		for (Node child : children) {
			if (child.getDescent().equals(fieldName))
				return true;
		}
		return false;
	}
	
}
