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
public class NaiveBayes {
    ArrayList <TopicData2> trainingSet = new ArrayList <TopicData2>();
    TopicData2 testTopic;
    double alpha =0.7;
    int totalTrainingWord =0;
    HashMap <String, Integer> All = new HashMap<String,Integer>();
    int allWords= 0;
    public NaiveBayes()
    {
        ;
    }
    
    public NaiveBayes(ArrayList <TopicData2> topicInfo, TopicData2 test)
    {
        trainingSet = topicInfo;
        testTopic = test;
    
        
        for(int i=0;i<trainingSet.size();i++)
        {
            TopicData2 temp = trainingSet.get(i);
            All.putAll(temp.frequency);
            allWords +=temp.frequency.size();
            
        }
        totalTrainingWord = All.size();
        //System.out.println("allwords :"+allWords+" unique :"+totalTrainingWord);
    }
    
    
    
    public double getWordProbability(String word, TopicData2 current)
    {
        double result=1.0;
        int count = 0;
        for(Map.Entry entry:current.frequency.entrySet())
        {
            if(((String)entry.getKey()).equals(word))
            {
                count = (int) (entry.getValue());
                count = (int) (count);
                
                break;
            }
        
        }
        result = (double)(count+alpha)/(current.frequency.size()+(totalTrainingWord*alpha));
        return result;
    }
    
    public double getTopicProbability(int topicIdx)
    {
        TopicData2 currentTopic = new TopicData2();
        currentTopic = trainingSet.get(topicIdx);
        double result =1.0;
        
       // result = (double)1/trainingSet.size();   //priorprob of topic
        
        for(Map.Entry entry:testTopic.frequency.entrySet())
        {
            String word = (String)entry.getKey();
            double wordProbability = getWordProbability(word,currentTopic);
            //System.out.println("wordProbability"+topicIdx+":"+wordProbability);
            double temp = result*wordProbability;
            result = temp;
            //System.out.println("topic prob:"+ topicIdx + ":"+result+"*"+wordProbability);
            //System.out.println("result: "+result);
        }   
       // System.out.println("topic prob:"+ topicIdx + ":"+result);
        return result;   
    }
    
    
    public int solve(double alpha)
    {
        this.alpha = alpha;
        int topicIndex =0;
        double maxProb = 0.0;
        
        for(int i =0;i<trainingSet.size();i++)
        {
            double probability = getTopicProbability(i);
           // System.out.println("probability " +i+ ":"+ probability);
            if(probability>maxProb)
            {
                maxProb = probability;
                topicIndex = i;
            }
        }
        return topicIndex;
    }
    
    
    
}
