/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

/**
 *
 * @author Black_Knight
 */
public class Data {
    int[] pixel = new int[28*28];
    int label;

    public Data() {
    }
    
    public Data(int[] p, int l)
    {
        for(int i=0;i<pixel.length;i++)
        {
            this.pixel[i] = p[i];
        }
        label = l;
    }
}
