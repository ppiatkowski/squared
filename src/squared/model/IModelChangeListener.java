package squared.model;

public interface IModelChangeListener {
	public void nodeAdded(Node parent, Node child);
	public void fieldConstrained(Node node, String fieldName);
}
