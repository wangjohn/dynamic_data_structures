package search_trees;

/**
 * A search tree which is implemented as a VEB Tree. If C is the largest integer
 * that is stored in the structure, all insertions, deletions, and finds will be 
 * completed in O(log log C) time.
 * 
 * @author John
 *
 */
public class VEBTree {

    private int maxInt;
    private int bucketSize;
    private int min;
    private int max;
    private VEBTree summary;
    private VEBTree [] blocks;
    
    public VEBTree(int maxInt){
        this.maxInt = maxInt;
        this.bucketSize = (int) Math.ceil(Math.sqrt(maxInt));
        initializeSummaryAndBlocks();
    }

    /** 
     * Method that initializes the VEB structure by creating the summary and block arrays.
     */
    private void initializeSummaryAndBlocks(){
        this.summary = new VEBTree [bucketSize];
        this.blocks = new VEBTree [bucketSize];
    }
    
    public Node successor(Node start) {
        // TODO Auto-generated method stub
        return null;
    }

    public Node predecessor(Node start) {
        // TODO Auto-generated method stub
        return null;
    }

    public Node findMin() {
        return this.min;
    }

    public Node findMax() {
        return this.max;
    }
    
    /**
     * Method for getting the upper half (summary bucket) to which the key belongs to
     * @param key
     * @return
     */
    private int getUpperHalf(int key){
        return key / this.bucketSize;
    }
    
    /**
     * Method for getting the lower half (block bucket) to which the key belongs
     * @param key
     * @return
     */
    private int getLowerHalf(int key){
        return key % this.bucketSize;
    }

    /**
     * Finds the node with a given key, or returns null if no such key is in the VEB structure.
     * @param key
     * @return
     */
    public Node find(int key) {
        if (key < this.min.getKey() || key > this.max.getKey()){
            return null;
        }
        int upper = getUpperHalf(key);
        int lower = getLowerHalf(key);
        return this.blocks[upper].find(upper);
    }

    /**
     * Adds a node into the structure, if the key is already placed in the structure, then nothing happens.
     * Although the keys are statically typed as double, we will be casting them as ints.
     * 
     */
    public void addNode(Node newNode) {
        // if there is no min, then there is also no max, set this as the min and max
        if (this.min == null){
            this.min = newNode; 
            this.max = newNode;
            return;
         }
        int key = (int) newNode.getKey();
        Node intermediateNode;
        
        // if the new node will become the new minimum or maximum, then we must place the old min/max
        if (key < this.min.getKey()){
            intermediateNode = this.min;
            this.min = newNode;
            newNode = this.min;
        }
        if (key > this.max.getKey()){
            intermediateNode = this.max;
            this.max = newNode;
            newNode = this.max;
        }
        // Set the key to the new node's key (only matters if the newNode to insert has changed)
        int upper = getUpperHalf((int) newNode.getKey());
        int lower = getLowerHalf((int) newNode.getKey());
        
        Node nodeToAdd;
        Node topBlock = this.summary.find(upper);
        // If the Data in the block is null, then we assume there is nothing in that block
        if (topBlock == null){
            // Create a new node to add to the summary data
            nodeToAdd = new Node(upper, 1);
            this.summary.addNode(nodeToAdd);
        }
        
        // Create a new node to add with the same data, but different key
        nodeToAdd = new Node(lower, newNode.getData());
        this.blocks[lower].addNode(nodeToAdd);
        
    }

    /**
     * Delete the node with the given key. Key will be cast into an int.
     */
    public Node deleteKey(double key) {
        int intKey = (int) key;
        int upper = getUpperHalf(intKey);
        int lower = getLowerHalf((intKey);
        
        if (key == this.max.getKey()){
            // Need to find a new maximum
            Node maxTop = this.summary.findMax();
            this.blocks[maxTop.getKey()].findMax();
        } 
        if (key == this.min.getKey()){
            
        }
        // TODO Auto-generated method stub
        return null;
    }

    public Node deleteNode(Node node) {
        // TODO Auto-generated method stub
        return null;
    }

}
