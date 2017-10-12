/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Black_Knight
 */
public class DocumentInfo {
    
    int totalDocuments;
    HashMap <String, Integer> countWords = new HashMap<String,Integer>();
    
    ArrayList<TopicData> trainingDataset = new ArrayList<TopicData>();

    public DocumentInfo(ArrayList<TopicData> dataset) {
        trainingDataset = dataset;
        
        totalDocuments = trainingDataset.size();
        
        buildMap();
    }
    
    public void buildMap()
    {
        for(int i=0;i<totalDocuments;i++)
        {
            TopicData td = trainingDataset.get(i);
            for(Map.Entry entry:td.frequency.entrySet())
            {
                String key = (String)entry.getKey();
                
                if(countWords.containsKey(td))
                {
                    int val = countWords.get(key)+1;
                    countWords.put(key, val);
                }
                
                else
                {
                    countWords.put(key, 1);
                }
            }
        }
    }
    
    
}
