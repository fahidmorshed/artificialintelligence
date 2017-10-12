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
        
        Dt_learn ID3 = new Dt_learn(filenameTrain) ;
        ID3.MatForm();
        Node treeRootNode;
        treeRootNode = new Node ();
        treeRootNode = ID3.train();
        treeRootNode.printTree(treeRootNode, 0);
        ArrayList<Integer> accuracy = new ArrayList<Integer>();
         ArrayList<Integer> precission = new ArrayList<Integer>();
        ArrayList<Integer> recall = new ArrayList<Integer>();
        ArrayList<Double> fscore = new ArrayList<Double>();
        for(int i=0;i<100;i++)
        {
           Result result = ID3.test(treeRootNode);
           accuracy.add(result.accuracy);
           precission.add(result.precission);
           recall.add(result.recall);
           fscore.add(result.fscore);
           //System.out.println("Accuracy:"+ result.accuracy);
        }
        int avgAcc=0,avgRecall=0, avgPrec=0;
        double avgfscore =0.0;
        for(int i=0;i<100;i++)
        {
           avgAcc+= accuracy.get(i);
           avgRecall += recall.get(i);
           avgPrec += precission.get(i);
           avgfscore+=fscore.get(i);
        }
        avgAcc= avgAcc/100;
        avgRecall = avgRecall/100;
        avgPrec = avgPrec/100;
        avgfscore = (double)avgfscore/100;
        
        System.out.println("Accuracy: "+avgAcc+" recall:"+avgRecall+" precission:"+avgPrec+"fscore:"+avgfscore);
        
    }
    
}
