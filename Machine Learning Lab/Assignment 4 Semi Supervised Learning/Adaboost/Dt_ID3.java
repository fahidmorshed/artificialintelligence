/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdaBoost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dipon
 */
public class Dt_ID3 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    //static int k= 15;
      static int k[]={1,5,10,15,20,30};
     static Random random = new Random(System.currentTimeMillis());  
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String filenameTrain = "assignment1_data_set.csv";
        
       Dt_learn ID3 = new Dt_learn(filenameTrain) ;
       ID3.MatForm();
       
       for(int a=0;a<k.length;a++)
       {
           //System.out.println(k[a]);
           int x = k[a];
         
         ArrayList<Double> accuracy = new ArrayList<Double>();
         for(int p=0;p<50;p++)
         {

             int m = ID3.valMat.trainRows.size();
             double []weight = new double [m];
             double []pr = new double [m];
             double []cdf = new double[m];
             int []finalclass= new int[m];
             ID3.valMat.arrayFill2(finalclass,ID3.valMat.columns-1, ID3.valMat.trainRows);
             int []finalclasstest = new int [ID3.valMat.testRows.size()];
             ID3.valMat.arrayFill2(finalclasstest,ID3.valMat.columns-1, ID3.valMat.testRows);

             for(int i=0;i<m;i++)
             {
                 weight[i]=(double) 1/m;
             }

             Node  []treeRootNode = new Node[x];
             double []beta = new double[x];

             for(int r=0;r<x;r++)
             {
                 //probability calculation

                 double sumW =0;
                 for(int i=0;i<m;i++)
                 {
                     sumW+=weight[i];
                     cdf[i]=0;
                 }
                 for(int i=0;i<m;i++)
                 {
                     pr[i] = weight[i]/sumW;

                 }

                 for(int i=0;i<m;i++)
                 {
                     if(i==0)
                     cdf[i]=pr[i];
                     else
                         cdf[i]=cdf[i-1]+pr[i];

                 }

                // System.out.println(cdf[m-1]);

                 //train set generate
                 ID3.valMat.rows.clear();
                 for(int i=0;i<400;i++)
                 {
                     double rand = random.nextDouble();
                     for(int j=1;j<m;j++)
                     {
                        // System.out.println(rand+" "+pr[j-1]+" "+pr[j]);
                         if(rand>cdf[j-1] & rand<cdf[j])
                         {
                             ID3.valMat.rows.add(ID3.valMat.trainRows.get(j)); 
                             break;
                         }

                     }
                 }

                 //System.out.println(ID3.valMat.rows.size()+" "+m);
                 treeRootNode[r] = new Node ();
                 treeRootNode[r] = ID3.train();
                 //treeRootNode[r].printTree(treeRootNode[r], 0);

                 ArrayList<Integer> trainResult = new ArrayList<Integer>();
                 for(int[]temp:ID3.valMat.trainRows)
                 {
                     int res = ID3.valMat.getTestTrain(treeRootNode[r], temp);
                     trainResult.add(res);
                 }
                // System.out.println(trainResult.size()+" "+finalclass.length);
                 double error=0;
                 for(int i=0;i<m;i++)
                 {
                     if(finalclass[i]!=trainResult.get(i))
                         error+=pr[i];
                 }

                 if(error>.5)
                 {
                    x=r-1;
                     break;
                 }
                 beta[r]=(double)error/(1-error);
                 for(int i=0;i<m;i++)
                 {
                     if(finalclass[i]==trainResult.get(i))
                      weight[i]= weight[i]*beta[r];
                 }

             }


             ArrayList<Integer> testClass= new ArrayList<Integer>();

             for(int[]temp:ID3.valMat.testRows)
             {
                 double class0val=0;
                 double class1val=0;
                 for(int r=0;r<x;r++) 
                 {
                     int result = ID3.valMat.getTestTrain(treeRootNode[r], temp);
                     if(result == 0)
                     {
                         class0val += Math.log((double)1/beta[r]);
                     }
                     else
                     {
                         class1val +=Math.log((double)1/beta[r]);
                     }
                 }

                 if(class0val>class1val)
                     testClass.add(0);
                 else
                     testClass.add(1);

             }
             double accu=0;
             for(int i=0;i<ID3.valMat.testRows.size();i++)
             {
                 //System.out.println(testClass.get(i)+" "+finalclasstest[i]);
                 if(testClass.get(i)==finalclasstest[i])
                     accu++;
             }
             accu = (double)100*accu/testClass.size();
             //System.out.println("Accuracy: "+accu);
             accuracy.add(accu);
         }
         double finaly=0;
         for(double i:accuracy)
         {
             finaly+=i;
         }
         finaly = (double)finaly/accuracy.size();
           System.out.println("accuracy for K= "+k[a]);
         System.out.println("final Accuracy: "+finaly);
           System.out.println("");
       }
       
        /**
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
        */
    }
    
}
