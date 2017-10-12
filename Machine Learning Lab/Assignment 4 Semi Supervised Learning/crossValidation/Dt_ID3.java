/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dt_id3;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author dipon
 */
public class Dt_ID3 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static int [] Kvalue = {5,10,20};
    
    public static void kfold() throws IOException
    {
         // TODO code application logic here
       
        
        
        for(int k=0;k<Kvalue.length;k++)
        {
             String filenameTrain = "assignment1_data_set.csv";
            ArrayList<Integer> accuracy = new ArrayList<Integer>();
            ArrayList<Integer> precission = new ArrayList<Integer>();
            ArrayList<Integer> recall = new ArrayList<Integer>();
            ArrayList <Double> fscore = new ArrayList<Double>();


            Dt_learn ID3 = new Dt_learn(filenameTrain) ;
            ID3.MatForm();
            Node treeRootNode;
            treeRootNode = new Node ();
            ArrayList<int []>temp = new ArrayList<int []>();
            for(int []row:ID3.valMat.allRows)
            {
                int []temprow = row.clone();
                temp.add(temprow);
            }
        
            ArrayList<ArrayList<int []>> sets = new ArrayList<ArrayList<int[]>>();
            int setSize = (int)ID3.valMat.allRows.size()/Kvalue[k];

            for(int i=0;i<Kvalue[k];i++)
            {
                ArrayList<int []> ksets = new ArrayList<int []>();
                for(int j=0;j<setSize;j++)
                {
                    if(j>=temp.size())break;
                    ksets.add(temp.get(j));
                    temp.remove(j);
                }
                sets.add(ksets);
            }
            // System.out.println(sets.size()); 
         
            for(int j=0;j<Kvalue[k];j++)
            {
                ID3.valMat.rows.clear();
                ID3.valMat.testRows.clear();
                for(int i=0;i<Kvalue[k];i++)
               {
                   ArrayList<int []> addrows = new ArrayList<int []>();
                   addrows = sets.get(i);
                   for(int[]add:addrows)
                   {
                       if(i!=j)
                           ID3.valMat.rows.add(add);
                       else
                           ID3.valMat.testRows.add(add);
                   }
               }

              treeRootNode = ID3.train();
              Result result = ID3.test(treeRootNode);
              accuracy.add(result.accuracy);
              precission.add(result.precission);
              recall.add(result.recall);
              fscore.add(result.fscore);
             // System.out.println("Accuracy:"+ result.accuracy+" precission:"+result.precission+" Recall:"+result.recall+" Fscore:"+result.fscore);
              
            }
            int avgAcc=0,avgRecall=0, avgPrec=0;
               double avgfscore = 0.0;
               for(int i=0;i<accuracy.size();i++)
               {
                  avgAcc+= accuracy.get(i);
                  avgRecall += recall.get(i);
                  avgPrec += precission.get(i);
                  avgfscore+=fscore.get(i);
               }
               avgAcc= avgAcc/accuracy.size();
               avgRecall = avgRecall/accuracy.size();
               avgPrec = avgPrec/accuracy.size();
               avgfscore = (double)avgfscore/accuracy.size();

               //System.out.println("");
               System.out.println("Finally:for "+Kvalue[k]);

               System.out.println("Accuracy: "+avgAcc+" recall:"+avgRecall+" precission:"+avgPrec+" fscore:"+avgfscore);  
        }
    }
    
    public static void leaveOne() throws IOException
    {
         String filenameTrain = "assignment1_data_set.csv";    
        Dt_learn ID3 = new Dt_learn(filenameTrain) ;
            ID3.MatForm();
            ArrayList<Integer> accuracy = new ArrayList<Integer>();
           double acc = 0.0;
           int loop = 0;
            for(int i=0;i<ID3.valMat.allRows.size();i++)
            {
                
                Node treeRootNode;
                treeRootNode = new Node ();
                ArrayList<int []>temp = new ArrayList<int []>();
                for(int []row:ID3.valMat.allRows)
                {
                    int []temprow = row.clone();
                    temp.add(temprow);
                }
                
                ID3.valMat.rows.clear();
                ID3.valMat.testRows.clear();
                for(int j=0;j<temp.size();j++)
                {
                    int [] temprows = temp.get(j);
                    if(i!=j)ID3.valMat.rows.add(temprows);
                    else ID3.valMat.testRows.add(temprows);
                }
                //System.out.println("size: "+ ID3.valMat.testRows.size());
               
                treeRootNode = ID3.train();
                boolean result = ID3.valMat.getclass(treeRootNode, ID3.valMat.testRows.get(0));
                if(result)acc++;
            }
            System.out.println("Accu: "+(double)100*acc/ID3.valMat.allRows.size());
           
 }
    
    
    public static void main(String[] args) throws IOException {
       
        //kfold();
         leaveOne();
        
    }
    
}
