package search_trees;

public class Node {

    private Object data;
    private final double key;
    
    private Node leftChild = null;
    private Node rightChild = null;
    private Node parent = null;
    
    private int size = 0;
    
    public Node(double key, Object data){
        this.key = key;
        this.data = data;
    }
    
    public Node(double key, Object data, Node parent){
        this.key = key;
        this.data = data;
        this.parent = parent;
    }
    
    public void setParent(Node parent){
        this.parent = parent;
    }
    
    public void setLeftChild(Node child){
        this.leftChild = child;
    }
    
    public void setRightChild(Node child){
        this.rightChild = child;
    }
    
    public Node getParent(){
        return this.parent;
    }
    
    public Node getLeftChild(){
        return this.leftChild;
    }
    
    public Node getRightChild(){
        return this.rightChild;
    }
    
    public double getKey(){
        return this.key;
    }
    
    public Object getData(){
        return this.data;
    }
    
    public void setData(Object newData){
        this.data = newData;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public int getSize(){
        return this.size;
    }
}
