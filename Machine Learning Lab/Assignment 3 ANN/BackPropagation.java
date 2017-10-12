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
public class BackPropagation {
    public double W[][][];
    public ArrayList< Data > dataset = new ArrayList<Data>();
    public int k[];
    

    public BackPropagation(ArrayList<Data> D) {
        for(int i=0;i<D.size();i++)
        {
            Data temp = D.get(i);
            this.dataset.add(temp);
        }
        
        /**
         * memory allocation for W
         */
        k = new int[ANN.L+1];
        for(int i=0;i<=ANN.L;i++)
        {
            if(i==0) k[i] = ANN.numFeatures;
            else if(i==ANN.L) k[i] = ANN.numClasses;
            else k[i]=ANN.hiddenLayerNodes;
        }
        
        W = new double[ANN.L+1][][];
        for(int i=1;i<=ANN.L;i++)
        {
            W[i]=new double[k[i]][];
            
            for(int j=0;j<W[i].length;j++)
            {
                W[i][j] = new double[k[i-1]+1];
            }
        }
        
        initWeight();
        
        for(int i=0;i<15;i++)
        {
            //System.out.print("Iteration: "+i+"\t");
            
            algo();
            
            int acc=0;
            for(int idx=0;idx<dataset.size();idx++)
            {
                Data temp = dataset.get(idx);
                int p = predictor(temp);

                if(p==temp.label) acc++;

                //System.out.println("ACtual Class: "+temp.label+"\tPredicted Class: "+p);
            }
            //System.out.println("Accuracy: "+((double)(acc*100.0)/dataset.size())+" %");
        }
        
    }
    
    public void algo()
    {
        for(int i=0;i<dataset.size();i++)
        {
            //System.out.println(i);
            
            Data data = dataset.get(i);
            
            double Y[][] = new double[ANN.L+1][];
            
            Y[0] = new double[k[0]];
            double arr[] = new double[k[0]];
            
            //double sumExp = 0;
            
            for(int t=0;t<Y[0].length;t++)
            {
                arr[t] = (double) data.pixel[t];
                //sumExp+=Math.exp(arr[t]);
            }
            
            for(int t=0;t<Y[0].length;t++)
            {
                //Y[0][t] = Math.exp(arr[t])/sumExp;
                Y[0][t] = sigmoid(arr[t]);
            }
            
            //System.out.println("#1 : Input layer done");
            /**
             * Forward propagation
             */
            for(int r=1;r<=ANN.L;r++)
            {
                Y[r] = new double[k[r]];
                double array[] = new double[k[r]];
                double sumExp = 0;
                
                //calculate Y[r] from Y[r-1]
                for(int j=0;j<k[r];j++)
                {
                    double sum = W[r][j][0];
                    
                    for(int t=0;t<k[r-1];t++)
                    {
                        sum+=(W[r][j][t+1]*Y[r-1][t]);
                    }
                    array[j] = sum;
                    
                    if(r==ANN.L) sumExp+=Math.exp(sum);
                    
                }
                
                for(int j=0;j<k[r];j++)
                {
                    if(r==ANN.L) Y[r][j] = Math.exp(array[j])/sumExp;
                    Y[r][j] = sigmoid(array[j]);
                    
                }
                
                
            }
            
            //System.out.println("Other Layers done --- Forward Propagation done");
            /**
             * allocate memory for error calculation
             */
            double error[][] = new double[ANN.L+1][];
            for(int r=1;r<=ANN.L;r++)
            {
                error[r] = new double[k[r]];
            }
            
            /**
             * Calculate error terms of output layer
             */
            for(int j=0;j<k[ANN.L];j++)
            {
                double ok,tk;
                
                ok = Y[ANN.L][j];
                
                if(data.label==j)
                {
                   tk  = 1;
                }
                else tk = 0;
                
                error[ANN.L][j] = ok*(1-ok)*(tk-ok);
            }
            
            //System.out.println("Error calculation of output layer done");
            /**
             * Calculate error terms for other layers
             */
            for(int r=ANN.L-1;r>=1;r--)
            {
                for(int j=0;j<k[r];j++)
                {
                    double oh = Y[r][j];
                    
                    double tmpSum = 0;
                    
                    for(int t=0;t<k[r+1];t++)
                    {
                        tmpSum += W[r+1][t][j+1]*error[r+1][t];
                    }
                    
                    error[r][j] = oh*(1-oh)*tmpSum;
                }
            }
            
            //System.out.println("Error calculation of other layers done");
            
            /**
             * weight update
             */
            for(int r=1;r<=ANN.L;r++)
            {
                for(int j=0;j<k[r];j++)
                {
                    /* update W[r][j] */
                    W[r][j][0] +=(ANN.learningRate*error[r][j]);
                    
                    for(int inp = 0; inp<k[r-1]; inp++)
                    {
                        W[r][j][inp+1] += (ANN.learningRate*error[r][j]*Y[r-1][inp]);
                    }
                }
            }
            
            //System.out.println("Weight update done");
        }
    }
    
    public void initWeight()
    {
        Random random = new Random(System.currentTimeMillis());
        
        for(int i=1;i<=ANN.L;i++)
        {
            for(int j=0;j<k[i];j++)
            {
                W[i][j][0] = -0.05+(0.1*random.nextDouble());
                for(int p=1;p<=k[i-1];p++)
                {
                    W[i][j][p] = -0.05+(0.1*random.nextDouble());
                }
            }
        }
    }
    
    public double softmax(double[] values,double v)
    {
        double sumExp = 0.0;
        for(int i=0;i<values.length;i++)
        {
            sumExp += (Math.exp(values[i]));
        }
        
        return Math.exp(v)/sumExp;
    }
    
    public double sigmoid(double x)
    {
        return 1.0/(1.0+Math.exp(-x));
    }
    
    public int predictor(Data d)
    {
        int predictedClass = 0;
        
        double Y[][] = new double[ANN.L+1][];
            
        Y[0] = new double[k[0]];
        for(int t=0;t<Y[0].length;t++)
        {
            Y[0][t] = (double) d.pixel[t];
        }
            
            /**
             * Forward propagation
             */
        for(int r=1;r<=ANN.L;r++)
        {
            Y[r] = new double[k[r]];
            
            //calculate Y[r] from Y[r-1]
            for(int j=0;j<k[r];j++)
            {
                double sum = W[r][j][0];
                    
                for(int t=0;t<k[r-1];t++)
                {
                    sum+=(W[r][j][t+1]*Y[r-1][t]);
                }
                    
                Y[r][j] = sigmoid(sum);
                    
                    //if r==L , apply softmax
            }
                
        }
            
        double maxVal = Double.MIN_VALUE;
        for(int i=0;i<k[ANN.L];i++)
        {
            if(Y[ANN.L][i]>maxVal)
            {
                predictedClass = i;
                maxVal = Y[ANN.L][i];
            }
        }
        
        return predictedClass;
    }
}
