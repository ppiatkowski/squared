package squared.model;

import java.util.ArrayList;
import java.util.Hashtable;

import squared.core.ClassReflection;

/**
 * @model
 */
public class Node extends DiagramElement {
	
	protected ClassReflection clazz;
	
	protected NodeLink parentLink = null;
	protected Node parent = null;
	protected ArrayList<NodeLink> childrenLinks = new ArrayList<NodeLink>();
	protected ArrayList<Node> children = new ArrayList<Node>();
	protected Hashtable<String, String> constraints = new Hashtable<String, String>();
		
	public Node(ClassReflection clazz)
	{
		this.clazz = clazz;
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
		return parent;
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
	
	/**
	 * 
	 * @param fieldName
	 * @param constraint
	 * @param overwrite if true, constrainField will overwrite any existing constraints on this field. If false, will do nothing and return false.
	 * @return false if method called in non-overwriting mode AND the field is already constrained. True otherwise.
	 */
	public boolean constrainField(String fieldName, String constraint, boolean overwrite)
	{
		if (isFieldConstrained(fieldName) && !overwrite) {
			return false;
		}
		
		if (constraints.containsKey(fieldName)) {
			constraints.remove(fieldName);
		}
		constraints.put(fieldName, constraint);
		Diagram.getInstance().event(Diagram.ModelEvent.EVENT_FIELD_CONSTRAINED, this, fieldName);
			
		return true;
	}
	
	public boolean isFieldConstrained(String fieldName)
	{
		return constraints.containsKey(fieldName) && !constraints.get(fieldName).equalsIgnoreCase("");
	}
	
	public String getConstraint(String fieldName)
	{
		return constraints.get(fieldName);
	}
	
	public Hashtable<String, String> getConstraints()
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
