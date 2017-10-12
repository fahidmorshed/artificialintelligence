/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Black_Knight
 */
public class KNN {
    ArrayList<TopicData> trainingDataset = new ArrayList<TopicData>();
    TopicData testData;
    
    int k;
    int nn[];
    
    int index;
    String predictedTopic;
    
    DocumentInfo doc;
    
    //int labelIndex;

    public KNN(DocumentInfo docinfo, ArrayList<TopicData> tr,TopicData test,int k,int distanceFunction) {
        
        doc = docinfo;
        trainingDataset = tr;
        testData = test;
        this.k = k;
        
        if(distanceFunction==0) predictedTopic = KNN_Hamming();
        else if(distanceFunction==1) predictedTopic = KNN_Euclid();
        else predictedTopic = KNN_Cosine();
        //System.out.println(predictedTopic);
        //predictedTopic = KNN_Euclid();
        //System.out.println(predictedTopic);
        
        //System.out.println("-----");
    }
    
    String KNN_Hamming()
    {
        int SIZE = trainingDataset.size();
        //int index=0;
        
        String topic="";
        
        IndexDistancePair d[] = new IndexDistancePair[SIZE];
        TopicData d2 = testData;
            
        for(int i=0;i<SIZE;i++)
        {
            TopicData d1 = trainingDataset.get(i);
            
            topic = d1.topicName;
            
            double distance = HammingDistance(d1, d2);///(double)(d1.frequency.size()+d2.frequency.size());
            
            d[i] = new IndexDistancePair(topic, distance);
        }
        
        /**
        for(int i=0;i<SIZE;i++)
        {
            System.out.println(d[i].index+"----->"+d[i].distance);
        }
        */
        
        Arrays.sort(d);
        
        /**
        for(int i=0;i<SIZE;i++)
        {
            System.out.println(d[i].index+"----->"+d[i].distance);
        }
        */
        
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        
        for(int i=0;i<k;i++)
        {
            //System.out.println(d[i].distance);
            if(map.containsKey(d[i].index))
            {
                int v = map.get(d[i].index)+1;
                map.put(d[i].index,v);
            }
            else
            {
                map.put(d[i].index,1);
            }
        }
        
        String maxTopic="";
        int maxVotes=0;
        
        for(Map.Entry entry:map.entrySet())
        {
            int votes = (int)entry.getValue();
            if(votes>maxVotes)
            {
                maxVotes = votes;
                maxTopic = (String)entry.getKey();
            }
            //System.out.println("Key : "+(String)entry.getKey()+" Value: "+(int)entry.getValue());
        }
        //System.out.println("----------");
        
        return maxTopic;
    }
    
    String KNN_Euclid()
    {
        int SIZE = trainingDataset.size();
        //int index=0;
        
        String topic="";
        
        IndexDistancePair d[] = new IndexDistancePair[SIZE];
        TopicData d2 = testData;
            
        for(int i=0;i<SIZE;i++)
        {
            TopicData d1 = trainingDataset.get(i);
            
            topic = d1.topicName;
            
            double distance = EuclideanDistance(d1, d2);///(double)(d1.frequency.size()+d2.frequency.size());
            
            d[i] = new IndexDistancePair(topic, distance);
        }
        
        Arrays.sort(d);
        
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        
        for(int i=0;i<k;i++)
        {
            //System.out.println(d[i].distance);
            if(map.containsKey(d[i].index))
            {
                int v = map.get(d[i].index)+1;
                map.put(d[i].index,v);
            }
            else
            {
                map.put(d[i].index,1);
            }
        }
        
        String maxTopic="";
        int maxVotes=0;
        
        for(Map.Entry entry:map.entrySet())
        {
            int votes = (int)entry.getValue();
            if(votes>maxVotes)
            {
                maxVotes = votes;
                maxTopic = (String)entry.getKey();
            }
            //System.out.println("Key : "+(String)entry.getKey()+" Value: "+(int)entry.getValue());
        }
        //System.out.println("----------");
        
        return maxTopic;
    }
    
    String KNN_Cosine()
    {
        int SIZE = trainingDataset.size();
        
        String topic="";
        
        IndexDistancePair d[] = new IndexDistancePair[SIZE];
        TopicData d2 = testData;
            
        for(int i=0;i<SIZE;i++)
        {
            TopicData d1 = trainingDataset.get(i);
            
            topic = d1.topicName;
            
            double distance = CosineSimilarity(d1, d2);///(double)(d1.frequency.size()+d2.frequency.size());
            
            d[i] = new IndexDistancePair(topic, distance);
        }
        
        /**
        for(int i=0;i<SIZE;i++)
        {
            System.out.println(d[i].index+"----->"+d[i].distance);
        }
        */
        
        
        Arrays.sort(d);
        
        /**
        for(int i=SIZE-1;i>=SIZE-k;i--)
        {
            System.out.println(d[i].index+"----->"+d[i].distance);
        }
        System.out.println("************");
        */
        
        
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        
        for(int i=SIZE-1;i>=SIZE-k;i--)
        {
            //System.out.println(d[i].distance);
            if(map.containsKey(d[i].index))
            {
                int v = map.get(d[i].index)+1;
                map.put(d[i].index,v);
            }
            else
            {
                map.put(d[i].index,1);
            }
        }
        
        String maxTopic="";
        int maxVotes=0;
        
        for(Map.Entry entry:map.entrySet())
        {
            int votes = (int)entry.getValue();
            if(votes>=maxVotes)
            {
                maxVotes = votes;
                maxTopic = (String)entry.getKey();
            }
            //System.out.println("Key : "+(String)entry.getKey()+" Value: "+(int)entry.getValue());
        }
        //System.out.println("----------");
        
        return maxTopic;
    }
    
    /**
    int _1NN_Hamming()
    {
        int SIZE = trainingDataset.size();
        
        double max_distance=Double.MAX_VALUE;
        int idx=0;
        String topic = "";
        
        for(int i=0;i<SIZE;i++)
        {
            TopicData d1 = trainingDataset.get(i);
            TopicData d2 = testData;
            
            //System.out.println(i+"->"+(HammingDistance(d1, d2)/(d1.frequency.size()+d2.frequency.size())));
            
            if((HammingDistance(d1, d2)/(d1.frequency.size()+d2.frequency.size()))<max_distance)
            {
                max_distance = (HammingDistance(d1, d2)/(d1.frequency.size()+d2.frequency.size()));
                idx = i;
                topic = d1.topicName;
            }
            
        }
        
        
       System.out.println(topic);
       
       return idx;
        
    }
    */
    
    public double log(double x)
    {
        double d = 0.0;
        if(d==0) return 1;
        
        d = Math.log(x)/Math.log(2.0);
        
        return d;
    }
    
    public double TF_IDF(TopicData td, String w)
    {
        //System.out.println(TF(td,w)*IDF(w));
        return TF(td,w)*IDF(w);
    }
    
    public double TF(TopicData td, String w)
    {
        return td.frequency.get(w)/(double)td.totalWords;
    }
    
    public double IDF(String w)
    {
        double d=0;
        
        if(doc.countWords.get(w)==null)
        {
            d = log(doc.totalDocuments);
        }
        else
        {
            d = log(doc.totalDocuments/(double)doc.countWords.get(w));
        }
        
        
        return d;
    }
    
    double CosineSimilarity(TopicData d1, TopicData d2)
    {
        double d = 0;
        
        for(Map.Entry entry:d1.frequency.entrySet())
        {
            String s = (String)entry.getKey();
            
            if(!d2.frequency.containsKey(s)) d+=0;
            else
            {
                d += (TF_IDF(d1, s) *TF_IDF(d2, s));
            }
            
        }
        
        //System.out.println("H: "+(MathExtra.round(vectorValue(d1), 6)*MathExtra.round(vectorValue(d2), 6)));
        double e = (MathExtra.round(vectorValue(d1), 6)*MathExtra.round(vectorValue(d2), 6));
        if(e==0) e=0.0000001;
        d =  d/e;
        //System.out.println(d);
        return d;
    }
    
    double vectorValue(TopicData td)
    {
        double val = 0.0;
        for(Map.Entry entry:td.frequency.entrySet())
        {
            String s = (String)entry.getKey();
            
            double temp = TF_IDF(td, s);
            
            val += (temp*temp);
        }
        //System.out.println(val);
        return Math.sqrt(val);
    }
    
    int HammingDistance(TopicData d1, TopicData d2)
    {
        int d = 0;
        
        for(Map.Entry entry:d1.frequency.entrySet())
        {
            String s = (String)entry.getKey();
            
            if(!d2.frequency.containsKey(s)) d+=1;
            
        }
        
        for(Map.Entry entry:d2.frequency.entrySet())
        {
            String s = (String)entry.getKey();
            
            if(!d1.frequency.containsKey(s)) d+=1;
            
        }
        
        return d;
    }
    
    int EuclideanDistance(TopicData d1, TopicData d2)
    {
        int d = 0;
        
        for(Map.Entry entry:d1.frequency.entrySet())
        {
            String s = (String)entry.getKey();
            
            if(!d2.frequency.containsKey(s))
            {
                d+=((int)entry.getValue()*(int)entry.getValue());
            }
            else
            {
                d+=(d2.frequency.get(s)-(int)entry.getValue())*(d2.frequency.get(s)-(int)entry.getValue());
            }
            
        }
        
        for(Map.Entry entry:d2.frequency.entrySet())
        {
            String s = (String)entry.getKey();
            
            if(!d1.frequency.containsKey(s))
            {
                d+=((int)entry.getValue()*(int)entry.getValue());
            }
            else
            {
                //
            }
            
        }
        
        return d;
    }
    
    
}
