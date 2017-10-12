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
   
  
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String filenameTrain = "assignment1_data_set.csv";
         ArrayList<Integer> accuracy = new ArrayList<Integer>();
         ArrayList<Integer> precission = new ArrayList<Integer>();
        ArrayList<Integer> recall = new ArrayList<Integer>();
        ArrayList <Double> fscore = new ArrayList<Double>();
        
        for(int k=0;k<10;k++){
        Dt_learn ID3 = new Dt_learn(filenameTrain) ;
        ID3.MatForm();
        Node treeRootNode;
        treeRootNode = new Node ();
        while(ID3.valMat.validationRows.size()!=0)
        {
            treeRootNode = ID3.train();
            for(int i=0;i<5;i++)
            {
                if(i>=ID3.valMat.validationRows.size())break;
                int []temp = new int[ID3.valMat.attributes.size()];
               // System.out.println(i+" "+ID3.valMat.validationRows.size());
               // if(ID3.valMat.validationRows!= null)
                 temp = ID3.valMat.validationRows.get(i);
                int classVal = ID3.validate(treeRootNode, temp);
                temp[temp.length-1]=classVal;
                int[] temp1 = new int [temp.length];
                for(int j=0;j<temp1.length;j++)
                {
                    temp1[j]=temp[j];
                    temp[j] =0;
                }
                ID3.valMat.rows.add(temp);
                ID3.valMat.validationRows.remove(i);
            }
        }

           Result result = ID3.test(treeRootNode);
           accuracy.add(result.accuracy);
           precission.add(result.precission);
           recall.add(result.recall);
           fscore.add(result.fscore);
           System.out.println("Accuracy:"+ result.accuracy+" precission:"+result.precission+" Recall:"+result.recall+" Fscore:"+result.fscore);
        
        }  
           
        int avgAcc=0,avgRecall=0, avgPrec=0;
        double avgfscore = 0.0;
        for(int i=0;i<10;i++)
        {
           avgAcc+= accuracy.get(i);
           avgRecall += recall.get(i);
           avgPrec += precission.get(i);
           avgfscore+=fscore.get(i);
        }
        avgAcc= avgAcc/10;
        avgRecall = avgRecall/10;
        avgPrec = avgPrec/10;
        avgfscore = (double)avgfscore/10;
        
        System.out.println("");
        System.out.println("Finally:");
        
        System.out.println("Accuracy: "+avgAcc+" recall:"+avgRecall+" precission:"+avgPrec+" fscore:"+avgfscore);
       
    }
    
}
