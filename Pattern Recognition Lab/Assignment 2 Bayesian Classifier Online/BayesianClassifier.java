/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bayesianclassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Knight
 */
public class BayesianClassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        File f = new File("assignment1_data_set.csv");
        
        
        /**
         * Record is the original input file
         */
        ArrayList < int[] > records = new ArrayList<int[]> ();
        
        /**
         * Attributes are used as an integer index (starting from 0) inside the whole code. attributeNames[i] returns the name of the ith attribute.
         */
        ArrayList <String> attributeNames = new ArrayList<String> ();
        
        
        
        Scanner input = null;
        try {
                        input = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BayesianClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input File Not Found");
            System.exit(-1);
        }
        
        /**
         * Get attribute names -- quite unnecessary
         */
        String t;
        t = input.nextLine();
        
        String attributes[] = t.split(",");
        for(int i=0;i<attributes.length;i++)
        {
            attributeNames.add(attributes[i]);
        }
        
        /**
         * Extract data and store it into "Records" matrix
         */
        int idx=0;
        while(input.hasNextLine())
        {
             
            String str = input.nextLine();
            String s[] = str.split(",");
            
            int n = s.length;
            int temp[] = new int[n];
            
            for(int i=0;i<n;i++)
            {
                temp[i] = Integer.valueOf(s[i]);
            }
            
            records.add(temp);
            
            idx++;
        }
        
        
        int totalRuns  = 1;
        
        for(int run=1;run<=totalRuns;run++)
        {
            /**
             * 80% training and 20% test data.
             */
            int testDataCount = (int) (idx*0.20) ; 

            /**
             * Random Partition
             */

            int trainingDataCount = idx - testDataCount;
            
            ArrayList < int[] > trainingRecords = new ArrayList<int[]> ();
            ArrayList < int[] > testRecords = new ArrayList<int[]> ();

            
            Random random = new Random();
            int begin = random.nextInt(idx-testDataCount);
            int end = begin+testDataCount-1;

            for(int i=0;i<begin;i++)
            {
                 trainingRecords.add(records.get(i));
            }

            for(int i=begin;i<=end;i++)
            {
                testRecords.add(records.get(i));
            }

            for(int i=end+1;i<idx;i++)
            {
                trainingRecords.add(records.get(i));
            }
            
            
            
            int classLabelIndex = 8;
            
            int totalClasses = 2;
            
            ArrayList< ArrayList < int[] > > dataset = new ArrayList<ArrayList < int[] > > ();
            
            for(int i=0;i<totalClasses;i++)
            {
               ArrayList < int[] > temp = new  ArrayList < int[] > ();
               
               for(int j=0;j<trainingDataCount;j++)
               {
                    int data[] = trainingRecords.get(j);
                    if(data[classLabelIndex]==i)
                    {
                        temp.add(data);
                    }
               }
               
               dataset.add(temp);
            }
            
            /**
             * Now, dataset.get(0) contains data of class 0 and dataset[1] contains data of class 1
             * You can print the datasets if required. This segment is only for printing purpose
             */
            
            /**
            ArrayList < int[] > temp = dataset.get(0);
            for(int i=0;i<temp.size();i++)
            {
                int data[] = temp.get(i);
                for(int j=0;j<classLabelIndex;j++)
                {
                    System.out.print(data[j]+",");
                }
                System.out.print(data[classLabelIndex]+"\n");
            }
            */
            
            /**
             * There are two types of class in dataset -> 0: No Breast Cancer, 1 : Breast Cancer
             * Form two instances of "ClassLabel" to keep separated info of classes
             */
            ClassLabel classData[] = new ClassLabel[totalClasses];
            
            for(int i=0;i<totalClasses;i++)
            {
                ArrayList < int[] > S = dataset.get(i);
                
                if(i==0) classData[i] = new ClassLabel("No Breast Cancer", S);
                else if(i==1) classData[i] = new ClassLabel("Breast Cancer", S);
            }
            
            Solver solver = new Solver(trainingRecords, classData);
            
            /**
             * Just test a data. you may manually produce an input. Here I am considering 5th(0 indexed) example in the given dataset.
             */
            
            for(int i=0;i<100;i++)
            {
                int testData[] = records.get(i);
                int ci = solver.solve(testData);
                for(int j=0;j<classLabelIndex;j++)
                {
                    System.out.print(testData[j]+",");
                }
                System.out.print("=> "+testData[classLabelIndex]+"\n");

                System.out.println("Predicted Class : "+ci+" \t ("+classData[ci].className+")");
            }

        }
        
    }
}
