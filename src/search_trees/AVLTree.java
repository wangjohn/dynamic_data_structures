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
     * Method for rebalancing the tree.
     * @param node
     */
    private void rebalanceTree(Node node){
        while (node != null){
            node.updateHeight();
            // If the right subtree is greater than the left subtree, perform a left rotation
            if (getHeight(node.getRightChild()) > getHeight(node.getLeftChild()) + 1){
                if (getHeight(node.getRightChild().getRightChild()) >= getHeight(node.getRightChild().getLeftChild())){
                    leftRotate(node);
                } else {
                    // double rotation
                    rightRotate(node.getRightChild());
                    leftRotate(node);
                }
            // Otherwise we go to this case
            } else {
                if (getHeight(node.getLeftChild().getLeftChild()) >= getHeight(node.getLeftChild().getRightChild())){
                    rightRotate(node);
                } else {
                    leftRotate(node.getLeftChild());
                    rightRotate(node);
                }
            }
            node = node.getParent();
        }
    }
    
    /**
     * private method for getting the height of a node. 
     * @param node
     * @return
     */
    private int getHeight(Node node){
        if (node == null){
            return -1;
        } else {
            return node.getSize();
        }
    }
    
    /**
     * Method for making a right AVL rotation
     * @param node
     */
    private void rightRotate(Node node){
        Node y = node.getLeftChild();
        Node b = y.getRightChild();
        
        // We make y the new root of the subtree
        if (node.getParent() == null){
            setRoot(y);
        } else {
            // Set y as the right or left child according to what node is set as
            if (node.equals(node.getParent().getLeftChild())){
                node.getParent().setLeftChild(y);
            } else {
                node.getParent().setRightChild(y);
            }
        }
        
        // Make node into y's new right child
        y.setRightChild(node);
        node.setParent(y);
        
        // Make b into node's new left child
        node.setLeftChild(b);
        if (b != null){
            b.setParent(node);
        }
        
        node.updateHeight();
        b.updateHeight();
    }
    
    /**
     * Method for making a left AVL rotation
     * @param node
     */
    private void leftRotate(Node node){
        Node y = node.getRightChild();
        Node b = y.getLeftChild();
        
        // We make y the new root of the subtree
        if (node.getParent() == null){
            setRoot(y);
        } else {
            // Set y as the right or left child according to what node is set as
            if (node.equals(node.getParent().getLeftChild())){
                node.getParent().setLeftChild(y);
            } else {
                node.getParent().setRightChild(y);
            }
        }
        
        // Make node into y's new left child
        y.setLeftChild(node);
        node.setParent(y);
        
        // Make b into node's new right child
        node.setRightChild(b);
        if (b != null){
            b.setParent(node);
        }
        
        node.updateHeight();
        y.updateHeight();
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
        newNode.updateHeight();
        rebalanceTree(newNode);
    }
    
    /**
     * Deletes the key from the tree
     * @param key the key of the node to delete
     * @return the node that was deleted from the tree
     */
    public Node deleteKey(double key) {
        Node toBeDeletedNode = find(key);
        return deleteNode(toBeDeletedNode);
    }

    /**
     * Delete a node from the tree
     * @param toBeDeletedNode the node that will be deleted
     * @return the deleted node
     */
    public Node deleteNode(Node toBeDeletedNode) {
        // Only need to perform operations if we have a node keyed on the key given.
        if (toBeDeletedNode == null){
            return null;
        } 
        Node theParent = toBeDeletedNode.getParent();
        Node newChild;
        
        if (toBeDeletedNode.getLeftChild() == null || toBeDeletedNode.getRightChild() == null){
            // Find out what the new child will be
            if (toBeDeletedNode.getLeftChild() == null){
                newChild = toBeDeletedNode.getRightChild();
            } else {
                newChild = toBeDeletedNode.getLeftChild();
            }         
        } else {
            // Get the successor if the node to be deleted has two subchildren.
            newChild = successor(toBeDeletedNode);
            toBeDeletedNode.delete();
            newChild.delete();
        }    
        
        // Set the parent's left child and newChild's parent
        if (toBeDeletedNode.equals(theParent.getLeftChild())){
            // If the to be deleted node is the left child
            theParent.setLeftChild(newChild);
        } else {
            // If the to be deleted node is the right child of the parent
            theParent.setRightChild(newChild);
        }            
        
        // If the child is not null, set it's parent
        if (newChild != null){
            newChild.setParent(theParent);
        }

        return toBeDeletedNode;
    }

}
