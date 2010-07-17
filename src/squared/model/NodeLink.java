package squared.model;

public class NodeLink {
	
	private Node parentNode;
	private Node childNode;
	private String label;
	
	public NodeLink(Node parent, Node child, String label)
	{
		this(parent, child);
		setLabel(label);
	}

	public NodeLink(Node parent, Node child)
	{
		parentNode = parent;
		childNode = child;
		parentNode.addChild(child);
		childNode.setParent(parent);
		parentNode.addChildLink(this);
		childNode.setParentLink(this);
	}
	
	public Node getChildNode()
	{
		return childNode;
	}
	
	public Node getParentNode()
	{
		return parentNode;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String getLabel()
	{
		return this.label;
	}
}
