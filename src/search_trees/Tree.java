package search_trees;

public interface Tree {
    
    public Node successor(Node start);
    public Node predecessor(Node start);
    public Node findMin();
    public Node findMax();
    public Node find(double key);
    public void addNode(Node newNode);
    public Node deleteKey(double key);
    public Node deleteNode(Node node);
    
}
