/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Black_Knight
 */
public class ANN {

    public static int L = 3;
    public static double learningRate = 0.08;
    public static int numFeatures = 28*28;
    public static int numClasses = 10;
    public static int hiddenLayerNodes = 80;
    
    
    public static ArrayList< Data > mainSet = new ArrayList<Data>();
    public static ArrayList< Data > trainingSet = new ArrayList<Data>();
    public static ArrayList< Data > testSet = new ArrayList<Data>();
    public static ArrayList< Data > validationSet = new ArrayList<Data>();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Random random = new Random(System.currentTimeMillis());
        IDXreader reader = new IDXreader();
        
        mainSet = reader.readFile("train-images.idx3-ubyte", "train-labels.idx1-ubyte");
        System.out.println(mainSet.size());
        
        for(int i=0;i<mainSet.size();i++)
        {
            Data temp = mainSet.get(i);
            if(random.nextDouble()<=0.80)
            {
                trainingSet.add(temp);
            }
            else validationSet.add(temp);
        }
        
        testSet = reader.readFile("test-images.idx3-ubyte", "test-labels.idx1-ubyte");
        
        System.out.println("Training: "+trainingSet.size());
        System.out.println("validation: "+validationSet.size());
        System.out.println("Test: "+testSet.size());
        
        BackPropagation solver = null;
                
        int tempHiddenLayer = hiddenLayerNodes;
        
        double maxAccuracy = 0.00;
        int maxHLN = 20;
        
        for(int vi=0;vi<20;vi++)
        {
            hiddenLayerNodes+=(5*vi);
            
            //System.out.print("Hidden Layer Neurons: "+hiddenLayerNodes);
            
            solver = new BackPropagation(trainingSet);
        
            int acc=0;
            for(int i=0;i<validationSet.size();i++)
            {
                Data temp = validationSet.get(i);
                int p = solver.predictor(temp);

                if(p==temp.label) acc++;
            }
            
            if((acc*100.0)/validationSet.size()>maxAccuracy)
            {
                maxAccuracy = (acc*100.0)/validationSet.size();
                maxHLN = hiddenLayerNodes;
            }
            
            System.out.println("Hidden Layer Neurons: "+hiddenLayerNodes+"\t Accuracy: "+((acc*100.0)/validationSet.size())+"%");
            hiddenLayerNodes = tempHiddenLayer;
        }
        
        hiddenLayerNodes = maxHLN;
        
        solver = new BackPropagation(trainingSet);
        
        int acc=0;
        for(int i=0;i<testSet.size();i++)
        {
            Data temp = testSet.get(i);
            int p = solver.predictor(temp);
            
            if(p==temp.label) acc++;
            
            System.out.println("ACtual Class: "+temp.label+"\tPredicted Class: "+p);
        }
        System.out.println("Accuracy: "+((double)(acc*100.0)/testSet.size()));
    }
}
