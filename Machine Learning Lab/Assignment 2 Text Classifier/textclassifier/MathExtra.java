package textclassifier;


import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Black_Knight
 */
public class MathExtra {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static double mean(double[] A,int length)
    {
        double m = 0;
        int SIZE = length;
        
        double sum = 0.0;
        
        for(int i=0;i<length;i++)
        {
            sum+=A[i];
        }
        m = sum/length;
        return m;
    }
    
    public static double stddev(double[] A,int length)
    {
        double sumSquare = 0.0;
        double mean = mean(A,length);
        
        for(int i=0;i<length;i++)
        {
            sumSquare+=((A[i]-mean)*(A[i]-mean));
        }
        return Math.sqrt(sumSquare/(length-1));
    }
}
