/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdaBoost;

import com.sun.xml.internal.ws.message.RootElementSniffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dipon
 */


public class Dt_learn {
    
    String fileToRead;
    Matrix valMat;
    
    public Dt_learn(String file)
    {
        this.fileToRead = file;
        this.valMat = new Matrix();
    }
    
    public void MatForm() throws IOException
    {
        
        valMat.createMat(fileToRead);
       // valMat.printMatrix();
    }
    
    public Result test(Node root)
    {
        Result result = valMat.getTest(root);
        return result;
    }
    
    
    
    
    
    public Node train()
    {
        HashMap <String, int []> trainingSet = new HashMap<String, int[]>();
        
        for(int i=0;i<valMat.columns-1;i++)
        {
            int []trainValue = new int[valMat.rows.size()];
            valMat.arrayFill(trainValue, i);
            trainingSet.put(valMat.attributes.get(i), trainValue);
        }
        
        int []classValueSet = new int [valMat.rows.size()];
        valMat.arrayFill(classValueSet, valMat.columns-1);
        
        /*for(int i :classValueSet)
            System.out.print(i+" ,");*/
        
        Node root = new Node();
        root.setAttrValue(-1);
        ID3_learn(trainingSet,classValueSet,root,valMat);
        return root;
    }
    
    
    public void ID3_learn(HashMap <String , int[]> trainingSet, int[]classValueSet, Node root,Matrix valMat)
    {
        //terminal value checking
        
        if (checkClass(classValueSet, 0))
        {
            root.setClassValue(0);
            return;
        }
        else if (checkClass(classValueSet, 1))
        {
            root.setClassValue(1);
            return;
        }
        
        if (trainingSet.entrySet().size() == 1)
        {
            int pos=0;
            int neg = 0;
            for(int i :classValueSet)
            {
                if (i==1)pos++;
                else neg++;
            }
            if (pos>neg) 
            {
                root.setClassValue(1);
                return;
            }
            else 
            {
                root.setClassValue(0);
                return;
            }
        }
        else
        {
            HashMap<String, Double> attributeGain = new HashMap<String,Double>();
            HashMap<String , ArrayList<Integer>> uniqueAttributesMap= new HashMap<String, ArrayList<Integer>>();
            
            for (Map.Entry entry:trainingSet.entrySet())
            {
                ArrayList<Integer> tempUnique = new ArrayList<Integer>(); 
                int []temp= (int [])entry.getValue();
                for(int i:temp)
                {
                    if(!tempUnique.contains(i))
                        tempUnique.add(i);
                            
                }
                uniqueAttributesMap.put((String)entry.getKey(), tempUnique);
            }
            
            //System.out.println("claculating Gain....");
            calculateGain(trainingSet,classValueSet,attributeGain);
              //System.out.println("claculating maxGain....");
            
            String attrMaxGain = "";
            double maxGain=0.0;
            int flag =0;
            for(Map.Entry attr: trainingSet.entrySet())
            {
                 double tempGain = attributeGain.get((String)attr.getKey());
                if (flag==0)
                {
                  maxGain = tempGain;
                  attrMaxGain = (String)attr.getKey();
                  flag=1;
                }
                else
                {
                    if(tempGain>maxGain)
                    {
                        maxGain = tempGain;
                        attrMaxGain = (String)attr.getKey();
                    }
                }
            }
            // System.out.println("Finally attribute :"+ attrMaxGain);
             //System.out.println("has the max gain  :"+maxGain);
            
            root.setAttrName(attrMaxGain);
            root.setClassValue(-1);
            root.setGain(maxGain);
            
             
            // for every attrval we call ID3_learn again.
             
            ArrayList<Integer> attrRoot = uniqueAttributesMap.get(attrMaxGain);
             
            for(int j:attrRoot)
            {
                Node child = new Node();
                child.setAttrValue(j);
                int class0=0;
                int class1=0;
                for(Map.Entry allAttrVal:trainingSet.entrySet())
                {
                    String attrName = (String) allAttrVal.getKey();
                    if(attrName == attrMaxGain)
                    {
                        int []maxAttrVal = (int[]) allAttrVal.getValue();
                        
                        for(int i=0;i<maxAttrVal.length;i++)
                        {
                            if(maxAttrVal[i]==j)
                            {
                                if(classValueSet[i]==0)class0++;
                                else class1++;
                            }
                        }
                        
                        
                    }
                }
                if(class0>class1)child.setClassValue(0);
                else child.setClassValue(1);
                
                
                
                root.getChildren().add(child);
                
                
                
            }
             
            
        }
        
    }
    
    public void calculateGain(HashMap <String,int[]>trainingSet,int[] classValueSet,
            HashMap<String , Double> attributeGain)
    {
        double gain = getTotalEntropy(classValueSet);
        for(Map.Entry entry:trainingSet.entrySet())
        {
            HashMap<Integer, Integer> atrPositive = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> atrNegative = new HashMap<Integer, Integer>();
            ArrayList<Integer> atrUnique = new ArrayList<Integer>();
            
            int []trainingVal = (int[])entry.getValue();
            int index =0;
            for(int i:trainingVal)
            {
                if (!atrUnique.contains(i))
                    atrUnique.add(i);
                
                if(classValueSet[index]==1)
                {
                    if (atrPositive.containsKey(trainingVal[index])) 
                    {
			atrPositive.put(trainingVal[index],
				atrPositive.get(trainingVal[index]) + 1);
                    } 
                    else 
                    {
			atrPositive.put(trainingVal[index], 1);
                    }
                }
                
                else
                {
                    if (atrNegative.containsKey(trainingVal[index])) 
                    {
			atrNegative.put(trainingVal[index],
				atrNegative.get(trainingVal[index]) + 1);
                    } 
                    else 
                    {
			atrNegative.put(trainingVal[index], 1);
                    }
                }
                index++;
                
            }
            
         
            for (int tempAttr : atrUnique) 
            {
		double entropyTemp = 0.0;
		int positives = 0;
		int negatives = 0;
		if (atrPositive.get(tempAttr) != null)
                    positives = atrPositive.get(tempAttr);
		if (atrNegative.get(tempAttr) != null)
                    negatives = atrNegative.get(tempAttr);

		
		double val1 = (double) (positives)
				/ (positives + negatives);
		double val2 = (double) (negatives)
				/ (positives + negatives);
		entropyTemp = -(val1 * log2(val1))- (val2 * log2(val2));
		// System.out.print(",entropy temp :"+entropyTemp+"\n");

		gain = gain- ((((double) positives + negatives) / trainingVal.length) * entropyTemp);
            }
					// System.out.println("Gain came out::"+gain);
            attributeGain.put((String) entry.getKey(), gain);
            
            
        }
        
    }
    
    public double getTotalEntropy(int[] classValue)
    {
        double result = 0.0;
        int pos=0,neg =0;
        for(int i:classValue)
        {
            if(i==1)pos++;
            else neg++;
        }
        double val1 = (double)pos/(pos+neg);
        double val2 = (double)neg/(pos+neg);
        
        result = - (val1*log2(val1))-(val2*log2(val2));
        return result;
        
    }
    
    public static double log2(double num) 
    {
	if (num <= 0)
            return 0.0;
	return (Math.log(num) / Math.log(2));
    }
    
    
    
    public boolean checkClass(int []finalClass,int val)
    {
        for(int i:finalClass)
        {
            if(i != val)
                return false;
        }
        return true;
    }
}
