package squared.model;

/**
 * @model
 */
public class Constraint extends DiagramElement {

	private Node parent;
	private String name;
	private ConstraintLink link;
	
	
	public Constraint(Node parent, String name)
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
	
	public Node getParent()
	{
		return parent;
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public void addLinkToNode(ConstraintLink link)
	{
		this.link = link;
	}
	
	public ConstraintLink getLinkToNode()
	{
		return this.link;
	}

}