package search_trees;

import java.util.List;

public class AVLTree implements Tree {

    private Node root = null;
    
    /**
     * Constructor that does nothing, root is set to null.
     */
    public AVLTree(){}
    
    /**
     * Constructor used to set up an AVL tree based on a single root
     * @param initialNode
     */
    public AVLTree(Node initialNode){
        setRoot(initialNode);
    }
    
    /**
     * Constructor used to set up an AVL Tree based on a list of initial nodes to start out with
     * @param initialNodes
     */
    public AVLTree(List <Node> initialNodes){
        for (int i=0; i<initialNodes.size(); i++){
            addNode(initialNodes.get(i));
        }
    }
    
    /**
     * Private method for setting to root to initialNode.
     * @param initialNode
     */
    private void setRoot(Node initialNode){
        this.root = initialNode;
    }
    
    /**
     * Returns the successor of the start node, or null if the node does not have a successor
     */
    public Node successor(Node start) {
        if (start == null){
            return null;
        }
        Node node;
        if (start.getRightChild() != null){
            // If there is a right subtree, start there
            node = start.getRightChild(); 
        } else {
            // Otherwise, we have to walk back up to the parent
            Node parent = start.getParent();
            if (start.equals(parent.getLeftChild())){
                // if the start node is the left child of the parent, then we walk down to the right until we can go left
                node = parent;
                while (node.getLeftChild() == null){
                    if (node.getRightChild() == null){
                        // If we can't continue to go right, return the original parent, since we haven't been able to 
                        // go left at all.
                        return parent;
                    }
                    node = node.getRightChild();
                }
                node = node.getLeftChild();
            } else {
                // if the start node is the right child of the parent, then we're done because the start node doesn't have a successor
                return null;
            }
        }
        
        return findMin(node);
    }

    public Node predecessor(Node start) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * private method for getting the leftmost node starting at a given node.
     * @param node
     * @return
     */
    private Node findMin(Node node){
        if (node == null){
            return null;
        }
        while (node.getLeftChild() != null){
            node = node.getLeftChild();
        }
        return node;
    }
    
    /**
     * privte method for getting the rightmost node starting at a given node
     * @param node
     * @return
     */
    private Node findMax(Node node){
        if (node == null){
            return null;
        }
        while (node.getRightChild() != null){
            node = node.getRightChild();
        }
        return node;
    }
    
    public Node findMin() {
        return findMin(this.root);
    }

    public Node findMax() {
        return findMax(this.root);
    }
    
    /**
     * Private function which returns either the node which has key equal to the specified key from the input,
     * or the second to last node (parent) if that key does not exist in the tree. 
     * @param key
     * @param backtrack
     * @return
     */
    private Node find(double key, boolean backtrack){
        Node node = this.root;
        Node lastNode = this.root;
        
        while (node != null){
            if (key < node.getKey()){
                lastNode = node;
                node = node.getLeftChild();
            } else if (key > node.getKey()){
                lastNode = node;
                node = node.getRightChild();
            } else {
                return node;
            }
        }
        // Return the second to last node if we can't find the node in the tree.
        if (backtrack){
            return lastNode;
        } else {
            return null;
        }
    }

    public Node find(double key) {
        return find(key, false);
    }

    /**
     * Adds newNode into the AVL Tree. If newNode has a key which is equal to a key already in the tree, that node will not be updated,
     * and instead the original key will remain in the tree.
     */
    public void addNode(Node newNode) {
        // If the root isn't set yet, set the newnode as the root.
        if (this.root == null){
            setRoot(newNode);
            return;
        }
        
        // Otherwise, we find the node closest to newNode and add it to its child.
        Node closestNode = find(newNode.getKey(), true);
        if (closestNode.getKey() == newNode.getKey()){
            // Keys conflict, so just return and don't insert the new node
            return;
        } else if (newNode.getKey() < closestNode.getKey()){
            closestNode.setLeftChild(newNode);
        } else {
            closestNode.setRightChild(newNode);
        }
        newNode.setParent(closestNode);
        
    }

    /**
     * Not yet implemented correctly
     */
    public Node deleteKey(double key) {
        Node toBeDeletedNode = find(key);
        // Only need to perform operations if we have a node keyed on the key given.
        if (toBeDeletedNode != null){
            Node leftChild = toBeDeletedNode.getLeftChild();
            Node rightChild = toBeDeletedNode.getRightChild();
            Node parent = toBeDeletedNode.getParent();
                        
            // Get a node to replace the old one
            Node replacementNode;
            if (leftChild == null){
                replacementNode = rightChild;
            } else if (rightChild == null){
                replacementNode = leftChild;
            } else {
                replacementNode = successor(toBeDeletedNode);
                if (replacementNode == null){
                    replacementNode = predecessor(toBeDeletedNode);
                }
            }
            
            
            
            // Figure out whether the deleted node is a left or right child
            if (parent != null){
                if (toBeDeletedNode.equals(parent.getLeftChild())){
                    parent.setLeftChild(replacementNode);
                } else {
                    parent.setRightChild(replacementNode);
                }
            }
            

            

        }
        
        // TODO Auto-generated method stub
        return null;
    }

    public Node deleteNode(Node node) {
        // TODO Auto-generated method stub
        return null;
    }

}
