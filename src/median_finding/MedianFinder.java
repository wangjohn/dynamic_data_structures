package median_finding;

import java.util.List;
import search_trees.Node;

public class MedianFinder {
    
    public static Node deterministicMedianFinder(List <Node> inputList, int k){
        List <Node> mediansList = new ArrayList <Node> ();
        // break into groups of five nodes each
        for (int i=0; i<inputList.size()/5; i++){
            mediansList.add(getMedianOfFive(inputList.subList(i*5, (i+1*5)), 3));
        }
        
        // Look for the median of the MediansList
        Node medianOfMedians = deterministicMedianFinder(mediansList, mediansList.size()/2);
        
        // Partition the space according to the median of medians
        
                
    }
    
    private static Node getMedianOfFive(List <Node> inputList, int k){
        Node [] sorted = (Node[]) inputList.toArray();
        Node intermediate;
        for (int i=0; i<sorted.length; i++){
            for (int j=0; j<sorted.length-1; j++){
                if (sorted[j].getKey() > sorted[j+1].getKey()){
                    intermediate = sorted[j+1];
                    sorted[j+1] = sorted[j];
                    sorted[j] = intermediate;      
                }       
            }
        }
        return sorted[k];
    }
    
}
