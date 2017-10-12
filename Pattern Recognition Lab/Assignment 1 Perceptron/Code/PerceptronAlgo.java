import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;


public class PerceptronAlgo {
	public static int MAX_ITERATION = 9999;
	/** FOR PROBLEM 1 **/
	public static int featureNum = 0;
	public static int classType = 0;
	public static int totalTraningData = 0;
	public static int totalTestData = 0;
	
	/** FOR PROBLEM 2 **/
	public static int featureNum2 = 0;
	public static int classType2 = 0;
	public static int totalTraningData2 = 0;
	public static int totalTestData2 = 0;
	
	public static void main(String[] args) {
		String fileName = "1.traning.txt";
		String fileNameTest = "1.test.txt";
		String fileName2 = "2.traning.txt";
		String fileName2Test = "2.test.txt";
		String line = "";
		String spliteBy = " ";
		boolean firstLine = true;
		int i = 0;
		Solver solver = null;
		SolverKesler solverKesler = null;
		try {
			/*** Problem 1 ***/
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.replaceAll("\\s+"," ");
				//System.out.println(line);
				String[] tokenStrings = line.split(spliteBy);
				if(firstLine){
					featureNum = Integer.parseInt(tokenStrings[0]);
					classType = Integer.parseInt(tokenStrings[1]);
					totalTraningData = Integer.parseInt(tokenStrings[2]);
					firstLine = false;
					solver = new Solver();
					continue;
				}
				solver.addTraningData(i++, tokenStrings);
			}
			br.close();
			
			i = 0;
			br = new BufferedReader(new FileReader(fileNameTest));
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.replaceAll("\\s+"," ");
				//System.out.println(line);
				String[] tokenStrings = line.split(spliteBy);
				solver.addTestData(i++, tokenStrings);
			}
			br.close();
			totalTestData = i;
			//solver.printTraningSet();
			//solver.init();
			System.out.println("NORMAL PERCEPTRON");
			solver.normalPerceptron();
			System.out.println("\nREWARD AND PUNISHMENT PERCEPTRON");
			solver.rAndPPerceptron();
			System.out.println("\nPOCKET PERCEPTRON");
			solver.pocketPerceptron();
			
			
			/*** Problem 2 ***/
			i = 0;
			firstLine = true;
			br = new BufferedReader(new FileReader(fileName2));
			
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.replaceAll("\\s+"," ");
				//System.out.println(line);
				String[] tokenStrings = line.split(spliteBy);
				if(firstLine){
					featureNum2 = Integer.parseInt(tokenStrings[0]);
					classType2 = Integer.parseInt(tokenStrings[1]);
					totalTraningData2 = Integer.parseInt(tokenStrings[2]);
					firstLine = false;
					solverKesler = new SolverKesler();
					continue;
				}
				solverKesler.addTraningData(i++, tokenStrings);
			}
			br.close();
			
			i = 0;
			br = new BufferedReader(new FileReader(fileName2Test));
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.replaceAll("\\s+"," ");
				String[] tokenStrings = line.split(spliteBy);
				solverKesler.addTestData(i++, tokenStrings);
			}
			br.close();
			totalTestData2 = i;
			System.out.println("\n\nKELSER PERCEPTRON");
			solverKesler.keslerPerceptron();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
