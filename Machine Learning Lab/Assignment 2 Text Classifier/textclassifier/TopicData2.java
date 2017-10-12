/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Black_Knight
 */
public class TopicData2 {
    ArrayList<String> topicStrings = new ArrayList<String> ();
    HashMap<String, Integer> frequency = new HashMap<String,Integer> ();
    
    public TopicData2()
    {
        ;
    }
    
    public TopicData2(ArrayList<String> contents) {
        topicStrings = contents;
        
        frequency = calculateFrequency();
        
        /**
        for(Map.Entry entry:frequency.entrySet())
        {
            System.out.println("Key : "+(String)entry.getKey()+" Value: "+(int)entry.getValue());
        }
        */
    }
    
    public void print()
    {
        for(Map.Entry entry:frequency.entrySet())
        {
            System.out.println("Key : "+(String)entry.getKey()+" Value: "+(int)entry.getValue());
        }
    }
    
    public HashMap<String, Integer> getFrequency()
    {
        return frequency;
    }
    
    public HashMap<String, Integer> calculateFrequency()
    {
        HashMap<String, Integer> freq = new HashMap<String, Integer>();
        
        for(int i=0;i<topicStrings.size();i++)
        {
            if(topicStrings.get(i).length()<=1) continue;
            if(freq.containsKey(topicStrings.get(i)))
            {
                int f = freq.get(topicStrings.get(i));
                freq.put(topicStrings.get(i), f+1);
            }
            else
            {
                freq.put(topicStrings.get(i), 1);
            }
        }
        
        return freq;
    }
}
