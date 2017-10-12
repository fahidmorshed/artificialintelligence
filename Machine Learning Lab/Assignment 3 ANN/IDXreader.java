/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class IDXreader {
    /**
     * @param args the command line arguments
     */
    
    public static int values_offset = 4;
    public static int magictrain ;
    public static int magiclabel ;
    public static int imageNumber;
    public static int rows;
    public static int column;
    public static int itemNum;

    public IDXreader() {
    }
    
    
    
    public int byteArrayToInt(byte[] b) 
    {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }
    
    public ArrayList<Data> readFile(String fileName,String labelFileName) {
        
        ArrayList< Data > dataset = new ArrayList<Data>();
        
        try {
            //String file = "test-images.idx3-ubyte";
            //String file2 = "test-labels.idx1-ubyte";
            
            Image imageList[];
            
            RandomAccessFile f= new RandomAccessFile(fileName,"r");
            byte[] m = new byte[values_offset];
            f.read(m);
            magictrain=byteArrayToInt(m);
            f.read(m);
            imageNumber = byteArrayToInt(m);
             f.read(m);
            rows = byteArrayToInt(m);
             f.read(m);
            column = byteArrayToInt(m);
            //System.out.println(rows+" "+column);
            
            imageList = new Image[imageNumber];
            for(int i =0;i<imageNumber;i++)
                
            {
                //System.out.println(i);
               // Image imageList = new Image();
                imageList[i]=new Image();
                //if(i%100==0)System.out.println("image:"+i);
                int []pixel = new int[rows*column];
                for(int p=0;p<(rows*column);p++)
                {
                    int gray = f.read();
                    pixel[p]=gray;
                }
                imageList[i].setPixel(pixel);
            }
            
           // imageList[0].print();
            
            RandomAccessFile f1 = new RandomAccessFile(labelFileName, "r");
            f1.read(m);
            magiclabel = byteArrayToInt(m);
            
            f1.read(m);
            itemNum = byteArrayToInt(m);
            //System.out.println(itemNum);
            //label= new int[itemNum];
            for(int i=0;i<itemNum;i++ )
            {
                int label=f1.read();
                //System.out.println("label "+i+":"+label[i]);
                
                Data temp = new Data(imageList[i].pixel, label);
                dataset.add(temp);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IDXreader.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ioex)
        {
            Logger.getLogger(IDXreader.class.getName()).log(Level.SEVERE, null, ioex);
        }
        
        return dataset;
    }
}
