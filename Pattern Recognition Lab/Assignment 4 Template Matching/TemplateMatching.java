import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import javax.imageio.ImageIO;

public class TemplateMatching {

	public static int[][] compute(File file){
		try {
		    BufferedImage img = ImageIO.read(file);
		    Raster raster = img.getData();
		    int w=raster.getWidth();
		    int h = raster.getHeight();
		    int pixels[][]=new int[w][h];
		    for (int x=0; x<w; x++){
		        for(int y=0; y<h; y++){
		            pixels[x][y] = raster.getSample(x,y,0);
		        }
		    }
	
		    return pixels;
		}catch (Exception e){
		    e.printStackTrace();
		}
		return null;
	}
	
	public static void print2DArray(int[][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static int log2(int n){
	    if(n <= 0) throw new IllegalArgumentException();
	    return 31 - Integer.numberOfLeadingZeros(n);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File ref1File = new File("ref1.bmp");
		int[][] ref1Array = compute(ref1File);
		File ref2File = new File("ref2.bmp");
		int[][] ref2Array = compute(ref2File);
		File testFile = new File("test.bmp");
		int[][] testArray = compute(testFile);
		Solver solver = new Solver(ref1Array, ref2Array, testArray);
		//print2DArray(ref1Array);
	}
	
}
