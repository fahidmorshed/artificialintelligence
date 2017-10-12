/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bayesianclassifier;

import java.util.ArrayList;

/**
 *
 * @author Black_Knight
 */
public class ClassLabel {
    String className ; //unnecessary
    ArrayList < int[] > dataset = new ArrayList<int[]> ();
    int exampleCount;
    
    public ClassLabel()
    {
        
    }
    
    public ClassLabel(String name, ArrayList < int[] > S)
    {
        dataset = S;
        exampleCount = dataset.size();
        className = name;
    }
    
    
    /**
     * Returns frequency for attribute values
     * @param attributeIndex
     * @param value
     * @return 
     */
    public int getCount(int attributeIndex,int value)
    {
        int count = 0;
        
        for(int i=0;i<dataset.size();i++)
        {
            int data[] = dataset.get(i);
            if(data[attributeIndex]==value) count++;
        }
        
        return count;
    }
    
    public double likelihood(int attrIndex,int value)
    {
        return getCount(attrIndex, value)/(double)exampleCount;
    }
    
}
