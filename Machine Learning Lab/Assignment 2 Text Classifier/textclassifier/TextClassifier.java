/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Black_Knight
 */
public class TextClassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // TODO code application logic here
        
        /**
        for(int i=0;i<stopWords.size();i++)
        {
            System.out.println(stopWords.get(i));
        }
        */
        
        double bestAlpha=0.0;
        
        ArrayList<TopicData2> trainingDataset2 = new ArrayList<TopicData2>();
        
        ArrayList<TopicData> trainingDataset = new ArrayList<TopicData>();
        
        
        File f = new File("Data/topics.txt");
        
        FileWriter fw = new FileWriter("outNB.txt", false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        //System.out.println("Reading Topic Lists....");
        
        
        Scanner input = null;
        try {
                        input = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input File Not Found");
            System.exit(-1);
        }
        
        String topics[] = new String[50];
        
        
        
        int topicIdx = 0;
        
        while(input.hasNextLine())
        {
            String s = input.nextLine();
            topics[topicIdx++] = s;
        }
        
        for(int i=0;i<topicIdx;i++)
        {
            //System.out.println("----------------------");
            //System.out.println("TOPIC: "+topics[i]);
            
            ArrayList<String> topicStrings = new ArrayList<String>();
            ArrayList<String> topicStrings2 = new ArrayList<String>();
            
            String fName = "Data/Training/"+topics[i]+".xml";
            
            TopicReader tr = new TopicReader(fName,500);
            topicStrings = tr.getStrings();
            
            TopicReader2 tr2 = new TopicReader2(fName,500);
            topicStrings2 = tr2.getStrings();
            
            TopicData2 td2 = new TopicData2(topicStrings2);
            trainingDataset2.add(td2);
            
            /**
             * 1 -> loop
             */
            
            for(int j=0;j<topicStrings.size();j++)
            {
                StringFilter sf = new StringFilter(topicStrings.get(j));
                ArrayList<String> words = sf.getWords();
                TopicData td = new TopicData(words,topics[i]);
                
                trainingDataset.add(td);
            }
            
        }
        
        DocumentInfo doc = new DocumentInfo(trainingDataset);
        double maxAccu =0.0;
        double alpha =0.5;
         
        while(true)
        {
            if(alpha>1)break;
            alpha=alpha+ 0.025;
            double finalAccu = 0.0;
            //System.out.println("Creating test data... ");
            for(int i=0;i<topicIdx;i++)
            {
                ArrayList<String> topicStrings2 = new ArrayList<String>();
                double accuracy =0.0;
                int row = 50;
                String fName = "Data/Test/"+topics[i]+".xml";
               for(int rowNum=0;rowNum<row;rowNum++)
               {
                   TopicReader2 tr2 = new TopicReader2(fName,1,rowNum);
                   topicStrings2 = tr2.getStrings();

                   TopicData2 td2 = new TopicData2(topicStrings2);

                   NaiveBayes solver = new NaiveBayes (trainingDataset2,td2);


                   int idx = solver.solve(alpha);
                   if (idx==i)accuracy ++;

               }
                accuracy = accuracy*100/row;
                finalAccu+=accuracy;
                //System.out.println("Accuracy topic "+topics[i]+": "+accuracy );
                //System.out.println("");
            }
            finalAccu = (double)finalAccu/topicIdx; 
            out.println(alpha+"\t"+finalAccu); 
            if(finalAccu>=maxAccu)
            {
                maxAccu = finalAccu;
                bestAlpha = alpha;
            }
        }
        System.out.println("maxAccuracy : "+maxAccu + "% best Alpha: "+ bestAlpha);
        out.close();
        
        double best_acc = 0.00;
        int best_k = 1;
        int best_algo = 0;
                
        FileWriter fw_knn = new FileWriter("outKNN.txt", false);
        BufferedWriter bw_knn = new BufferedWriter(fw_knn);
        PrintWriter out_knn = new PrintWriter(bw_knn);
      
        int accurates[] = new int[10];
                int acc_euclid[]= new int[10];
                int acc_cosine[]= new int[10];
                
                for(int k=0;k<10;k++)
                {
                    accurates[k]=0;
                    acc_euclid[k] =0;
                    acc_cosine[k] = 0;
                }
                
                
        int n=0;
        
        for(int i=0;i<topicIdx;i++)
        {
            out_knn.println("----------------------");
            out_knn.println("TOPIC: "+topics[i]);
            
            ArrayList<String> topicStrings = new ArrayList<String>();
            
            String fName = "Data/Test/"+topics[i]+".xml";
            
            
                TopicReader tr = new TopicReader(fName,50);
                topicStrings = tr.getStrings();
                
                for(int k=0;k<topicStrings.size();k++)
                {
                    StringFilter sf = new StringFilter(topicStrings.get(k));
                    ArrayList<String> words = sf.getWords();
                    
                    
                    
                    TopicData td = new TopicData(words,topics[i]);
                    n++;
                
                    int knn;
                    for(knn=1;knn<=5;knn+=2)
                    {
                        KNN solver = new KNN(doc,trainingDataset,td,knn,0);
                        //System.out.println("Hamming: "+solver.predictedTopic);
                        if(solver.predictedTopic.equals(topics[i])) accurates[knn]++;

                        KNN solver2 = new KNN(doc,trainingDataset,td,knn,1);
                        //System.out.println("Euclid: "+solver2.predictedTopic);
                        if(solver2.predictedTopic.equals(topics[i])) acc_euclid[knn]++;

                        KNN solver3 = new KNN(doc,trainingDataset,td,knn,2);
                        //System.out.println("Cosine: "+solver3.predictedTopic);
                        if(solver3.predictedTopic.equals(topics[i])) acc_cosine[knn]++;

                    }
                    
                       
                }
                
                
                

                
                
                
                
            }
        
            for(int knn=1;knn<=5;knn+=2)
                {
                    out_knn.println("k = "+knn);
                    
                    double hamming_acc = (accurates[knn]/(double)n);
                    double euclid_acc = (acc_euclid[knn]/(double)n);
                    double cosine_acc = (acc_cosine[knn]/(double)n);
                    
                    out_knn.println("Hamming Accuracy: "+hamming_acc*100+"%");
                    out_knn.println("Euclid Accuracy: "+euclid_acc*100+"%");
                    out_knn.println("Cosine Accuracy: "+cosine_acc*100+"%");
                    
                    if(hamming_acc>best_acc)
                    {
                        best_acc = hamming_acc;
                        best_k = knn;
                        best_algo = 0;
                    }
                    
                    if(euclid_acc>best_acc)
                    {
                        best_acc = euclid_acc;
                        best_k = knn;
                        best_algo = 1;
                    }
                    
                    if(cosine_acc>best_acc)
                    {
                        best_acc = cosine_acc;
                        best_k = knn;
                        best_algo = 2;
                    }
                }
            
            out_knn.close();
            
            
            System.out.println("Best k: "+best_k+"\t Best Algo: "+best_algo);
            
            
            trainingDataset.clear();
            
            double accuNB[]= new double[100];
            double accKNN[] = new double[100];
            
            int trainRow =0; 
            int R=0;
            while(true)
            {
                if(trainRow >=1000||R>=10)break;

                 trainRow +=100;
                 for(int i=0;i<topicIdx;i++)
                 {
                     ArrayList<String> topicStrings2 = new ArrayList<String>();
                     ArrayList<String> topicStrings = new ArrayList<String>();

                     String fName = "Data/Training/"+topics[i]+".xml";

                     //System.out.println("Reading .....");

                     TopicReader2 tr2 = new TopicReader2(fName,100,trainRow);
                     topicStrings2 = tr2.getStrings();

                     TopicData2 td2 = new TopicData2(topicStrings2);
                     trainingDataset2.add(td2);
                     
                     TopicReader tr = new TopicReader(fName,100,trainRow);
                     topicStrings = tr.getStrings();
                     
                     for(int j=0;j<topicStrings.size();j++)
                     {
                        StringFilter sf = new StringFilter(topicStrings.get(j));
                        ArrayList<String> words = sf.getWords();
                        TopicData td = new TopicData(words,topics[i]);
                        trainingDataset.add(td);
                    }

                 }

                 DocumentInfo doc2 = new DocumentInfo(trainingDataset);
                 
                 double finalAccu = 0.0;
                 double finalAccuKNN = 0.0;
                 for(int i=0;i<topicIdx;i++)
                     {
                         ArrayList<String> topicStrings2 = new ArrayList<String>();
                         ArrayList<String> topicStrings = new ArrayList<String>();
                         
                         double accuracy =0.0;
                         double accuracyKNN =0.0;
                         
                         int row = 50;
                         String fName = "Data/Test/"+topics[i]+".xml";
                         for(int rowNum=0;rowNum<row;rowNum++)
                        {
                            TopicReader2 tr2 = new TopicReader2(fName,1,rowNum);
                            topicStrings2 = tr2.getStrings();
                             
                             TopicReader tr = new TopicReader(fName,1,rowNum);
                             topicStrings = tr.getStrings();

                             TopicData2 td2 = new TopicData2(topicStrings2);
                             
                             StringFilter sf = new StringFilter(topicStrings.get(0));
                             ArrayList<String> words = sf.getWords();
                             TopicData td = new TopicData(words,topics[i]);
                              //trainingDataset.add(td);
                           

                            NaiveBayes solver = new NaiveBayes (trainingDataset2,td2);
                            
                            KNN solver_knn = new KNN(doc2, trainingDataset, td, best_k, best_algo);
                            if(solver_knn.predictedTopic==topics[i]) accuracyKNN++;
                            //System.out.println("Prediction >"+solver_knn.predictedTopic);


                            int idx = solver.solve(bestAlpha);
                            if (idx==i)accuracy ++;

                        }
                         accuracy = accuracy*100/row;
                         accuracyKNN = accuracyKNN*100/row;
                         
                         finalAccu+=accuracy;
                         finalAccuKNN+=accuracyKNN;
                         //System.out.println("Accuracy topic "+topics[i]+": "+accuracy );
                         //System.out.println("");
                     }
                     finalAccu = (double)finalAccu/topicIdx; 
                     finalAccuKNN = (double)finalAccuKNN/topicIdx;
                     
                     System.out.println("final Accuracy: Naive Bayes"+ finalAccu+" % \t KNN: "+finalAccuKNN+"%");
                     accuNB[R]=finalAccu;
                     accKNN[R]=finalAccuKNN;
                     
                     
                     R++;

            }
            
            System.out.println("KNN Statistics for "+R+" runs.\nMean: "+MathExtra.mean(accKNN, R)+"\t Standard Deviation: "+MathExtra.stddev(accKNN, R));
            System.out.println("NB Statistics for "+R+" runs.\nMean: "+MathExtra.mean(accuNB, R)+"\t Standard Deviation: "+MathExtra.stddev(accuNB, R));
            
            //trainingDataset.add(td);
        }
        
            
        
        
        /**
         * DATA PARSE ---- DONE
         */
        
        /**
         * PASS TO TOPIC DATA CLASS FOR FREQUENCY GENERATION
         */
        
        /**
         * PASS TOPIC DATA TO KNN/NAIVE BAYES FOR PREDICTION
         */
    }
