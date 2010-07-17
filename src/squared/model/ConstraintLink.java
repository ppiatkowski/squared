package squared.model;

public class ConstraintLink {
	private Node node;
	private Constraint constraint;

	public ConstraintLink(Node n, Constraint c)
	{
		setNode(n);
		setConstraint(c);
		
		node.addConstraintLink(this);
		constraint.addLinkToNode(this);
	}
	
	public Node getNode()
	{
		return node;
	}
	
	public void setNode(Node n)
	{
		node = n;
	}
	
	public Constraint getConstraint()
	{
		return constraint;
	}
	
	public void setConstraint(Constraint c)
	{
		constraint = c;
	}
}
