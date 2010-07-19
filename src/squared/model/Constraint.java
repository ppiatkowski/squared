package squared.model;

/**
 * @model
 */
public class Constraint extends DiagramElement {

	private Node parent;
	private String name;
	
	
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
	
}