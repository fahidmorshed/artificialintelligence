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
public class Solver {

    /**
     * dataset is the entire training dataset
     */
    ArrayList < int[] > dataset = new ArrayList<int[]> ();
    ClassLabel classData[];
    
    public Solver(ArrayList < int[] > S,ClassLabel c[]) {
        classData = c;
        dataset = S;
    }
    
    /**
     * for this example dataset, testData[] contains indices 0-8, but we don't use index 8 as we pretend to be unaware of the actual classLabel
     * @param classIndex
     * @param testData
     * @return 
     */
    public double getClassProbability(int classIndex,int[] testData)
    {
        //Get total Number of attributes
        int totalAttributes = testData.length-1;
        
        double p = 1.0;
        
        double priorProbability = classData[classIndex].exampleCount/((double)dataset.size());
        
        for(int i=0;i<totalAttributes;i++)
        {
            p = p*classData[classIndex].likelihood(classIndex, testData[i]);
        }
        
        return priorProbability*p;
    }
    
    public int solve(int[] testData)
    {
        int classIndex = 0;
        double maxProb = 0.0;
        
        //System.out.println(classData.length);
        
        /**
         * For each class, calculate posterior probability
         */
        for(int i=0;i<classData.length;i++)
        {
            double p = getClassProbability(i, testData);
            if(p>maxProb)
            {
                maxProb = p;
                classIndex = i;
            }
        }
        
        return classIndex;
    }
    
}
