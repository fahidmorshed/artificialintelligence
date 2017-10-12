import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Solver {
	public static int HEIGHT = 400;
	public static int WIDTH = 400;
	Cluster[] clusters;
	Points[] points;
	int numOfClusters;
	int numOfInputs;
	
	public Solver(int k, int n) {
		numOfClusters = k;
		clusters = new Cluster[k];
		for (int i = 0; i < k; i++) {
			clusters[i] = new Cluster(i);
		}
		numOfInputs = n;
		points = new Points[n];
		for (int i = 0; i < n; i++) {
			points[i] = KMeansClustering.inputPoints[i];
		}
	}
	
	void init(){
		Random random = new Random();
		int rand;
		for (int i = 0; i < numOfClusters; i++) {
			rand = random.nextInt(numOfInputs);
			clusters[i].addPoint(points[rand]);
			clusters[i].calcAll();
			System.out.println(clusters[i]);
		}
	}
	
	void clusterLopp(){
		boolean isConversed = false;
		for (int k = 0; k < KMeansClustering.MAX_ITERATION_NUM ; k++) {
			isConversed = true;
			for (int i = 0; i < numOfClusters; i++) {
				clusters[i].rmvPointAll();
			}
			double dist = 0;
			int clusterID = -1;
			for (int i = 0; i < numOfInputs; i++) {
				dist = 9999999;
				for (int j = 0; j < numOfClusters; j++) {
					double tempDist = clusters[j].getDist(points[i]);
					if(dist > tempDist){
						dist = tempDist;
						clusterID = j;
					}
				}
				clusters[clusterID].addPoint(points[i]);
			}
			System.out.println("ROUND " + k);
			for (int i = 0; i < numOfClusters; i++) {
				clusters[i].calcAll();
				System.out.println(clusters[i]);
				if(clusters[i].oldDistance != clusters[i].avgDistance){
					isConversed = false;
				}
			}
		}
		System.out.println("FINAL:");
		for (int i = 0; i < numOfClusters; i++) {
			System.out.println(clusters[i]);
//			System.out.println(i);
//			for (int j = 0; j < clusters[i].points.size(); j++) {
//				System.out.println(clusters[i].points);
//			}
		}
		printPNG();
	}
	
	void printPNG(){
		Color[][] imageColors = new Color[HEIGHT][WIDTH];
		for (int i = 0; i < imageColors.length; i++) {
			for (int j = 0; j < imageColors[0].length; j++) {
				imageColors[i][j] = Color.WHITE;
			}
		}
		
		double maxX=-1, maxY=-1;
		for (int i = 0; i < numOfInputs; i++) {
			if(points[i].x > maxX)
				maxX = points[i].x;
			if(points[i].y > maxY)
				maxY = points[i].y;
		}
		//maxX += 2;
		//maxY += 2;
		
		double unitX, unitY;
		unitX = WIDTH/maxX;
		unitY = HEIGHT/maxY;
		
		for (int i = 0; i < numOfClusters; i++) {
			int x = (int) Math.round(clusters[i].center.x * unitX);
			int y = (int) Math.round(clusters[i].center.y * unitY);
			int temp = (clusters[i].clusterID+1) * 127 / numOfClusters;
			imageColors[x][y] = new Color(temp*2, temp, temp/2);
			imageColors[x][y+1] = new Color(temp*2, temp, temp/2);
			imageColors[x][y+2] = new Color(temp*2, temp, temp/2);
			imageColors[x][y-1] = new Color(temp*2, temp, temp/2);
			imageColors[x][y-2] = new Color(temp*2, temp, temp/2);
			imageColors[x+1][y] = new Color(temp*2, temp, temp/2);
			imageColors[x+1][y+1] = new Color(temp*2, temp, temp/2);
			imageColors[x+1][y+2] = new Color(temp*2, temp, temp/2);
			imageColors[x+1][y-1] = new Color(temp*2, temp, temp/2);
			imageColors[x+1][y-2] = new Color(temp*2, temp, temp/2);
			imageColors[x+2][y] = new Color(temp*2, temp, temp/2);
			imageColors[x+2][y+1] = new Color(temp*2, temp, temp/2);
			imageColors[x+2][y+2] = new Color(temp*2, temp, temp/2);
			imageColors[x+2][y-1] = new Color(temp*2, temp, temp/2);
			imageColors[x+2][y-2] = new Color(temp*2, temp, temp/2);
			imageColors[x-1][y] = new Color(temp*2, temp, temp/2);
			imageColors[x-1][y+1] = new Color(temp*2, temp, temp/2);
			imageColors[x-1][y+2] = new Color(temp*2, temp, temp/2);
			imageColors[x-1][y-1] = new Color(temp*2, temp, temp/2);
			imageColors[x-1][y-2] = new Color(temp*2, temp, temp/2);
			imageColors[x-2][y] = new Color(temp*2, temp, temp/2);
			imageColors[x-2][y+1] = new Color(temp*2, temp, temp/2);
			imageColors[x-2][y+2] = new Color(temp*2, temp, temp/2);
			imageColors[x-2][y-1] = new Color(temp*2, temp, temp/2);
			imageColors[x-2][y-2] = new Color(temp*2, temp, temp/2);
		}
		
		BufferedImage bufferedImage = new BufferedImage(imageColors.length, imageColors[0].length,
		        BufferedImage.TYPE_INT_RGB);
		
		for (int x = 0; x < imageColors.length; x++) {
		    for (int y = 0; y < imageColors[x].length; y++) {
		        bufferedImage.setRGB(x, y, imageColors[x][y].getRGB());
		    }
		}
		
		try {
		    // retrieve image
		    //BufferedImage bi = getMyImage();
		    File outputfile = new File("out.png");
		    ImageIO.write(bufferedImage, "png", outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	double getDistance(Points a, Points b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}
}
