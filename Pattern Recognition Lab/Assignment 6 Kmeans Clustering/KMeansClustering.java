import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class KMeansClustering {
	public static int K = 8;
	public static int inputCount = 0;
	public static int MAX_ITERATION_NUM = 1000;
	public static Points[] inputPoints;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "bisecting.xls";
		String line = "";
		String fileSplitBy = "\t";
		inputPoints = new Points[1000];
		
		if(fileName.contains(".xls")){
			XlstoCSV.xls(new File(fileName), new File("input.txt"));
			fileName = "input.txt";
			fileSplitBy = " ";
		}
		
		try (BufferedReader bReader = new BufferedReader(new FileReader(new File(fileName)))){
			while ((line = bReader.readLine()) != null) {
				//line.trim();
				String[] tempStrings = line.split(fileSplitBy);
				inputPoints[inputCount++] = 
						new Points(Double.parseDouble(tempStrings[0]), Double.parseDouble(tempStrings[1]));
			}
			//printPoints();
			bReader.close();
			Solver solver = new Solver(K, inputCount);
			solver.init();
			solver.clusterLopp();
			//System.out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public static void printPoints(){
		for (int i = 0; i < inputCount; i++) {
			System.out.println(inputPoints[i]);
		}
	}

}
