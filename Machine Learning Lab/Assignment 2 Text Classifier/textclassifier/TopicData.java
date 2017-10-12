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
public class TopicData {
    
    int totalWords = 0;
    
    ArrayList<String> topicStrings = new ArrayList<String> ();
    HashMap<String, Integer> frequency = new HashMap<String,Integer> ();
    String topicName;
    
    //String topicString;

    public TopicData(ArrayList<String> contents,String topic) {
        topicName = topic;
        
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
                totalWords++;
                freq.put(topicStrings.get(i), 1);
            }
        }
        
        return freq;
    }
    
    /**
    public double log(double x)
    {
        double d = 0.0;
        if(d==0) return 0;
        
        d = Math.log(x)/Math.log(2.0);
        
        return d;
    }
    
    public double IDF(String w)
    {
        double d=0;
        
        //d = log()
        
        return d;
    }
    */
}
