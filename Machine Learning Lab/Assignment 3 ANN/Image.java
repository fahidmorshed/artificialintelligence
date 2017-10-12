
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;


public class Image {
    public int[] pixel = new int[28*28];;
    
    
    public void setPixel(int[]a)
    {
        //System.out.println(this.pixel.length+" "+a.length);
        for(int i=0;i<a.length;i++)
        {
           this. pixel[i]=a[i];
        }
         
    }
    
    public void print()
    {
        for(int i=0;i<this.pixel.length;i++)
        {
            if(i!=0 && i%8==0)System.out.println("");
            System.out.print(this.pixel[i]+" ");
            
        }
    }
}
