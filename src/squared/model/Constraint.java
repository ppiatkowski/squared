package squared.model;

/**
 * @model
 */
public class Constraint {

	private Node m_parent;
	private String m_name;
	
	
	Constraint(Node parent, String name)
	{
		setParent(parent);
		setName(name);
	}
	
	void setName(String name)
	{
		m_name = name;
	}
	
	String getName()
	{
		return m_name;
	}
	
	Node getParent()
	{
		return m_parent;
	}
	
	void setParent(Node parent)
	{
		m_parent = parent;
	}

}