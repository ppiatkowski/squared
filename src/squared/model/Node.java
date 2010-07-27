package squared.model;

import java.util.ArrayList;

import com.db4o.reflect.ReflectClass;

/**
 * @model
 */
public class Node extends DiagramElement {
	
	protected ReflectClass clazz;
	protected String descent;
	
	protected NodeLink parentLink = null;
	protected Node parent = null;
	protected ArrayList<NodeLink> childrenLinks = new ArrayList<NodeLink>();
	protected ArrayList<Node> children = new ArrayList<Node>();
	protected ArrayList<Constraint> constraints;
		
	public Node(ReflectClass clazz, String descent)
	{
		this.clazz = clazz;
		this.descent = descent;
		
		constraints = new ArrayList<Constraint>();
	}
	
	public ReflectClass getData()
	{
		return clazz;
	}
	
	public String getDescent() 
	{
		return descent;
	}
	
	public String getName()
	{
		return clazz.getName();
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
