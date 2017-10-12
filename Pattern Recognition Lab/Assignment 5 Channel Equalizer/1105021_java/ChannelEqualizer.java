import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.text.AbstractDocument.BranchElement;


public class ChannelEqualizer {
	public static int N = 4;
	public static double SIGMA = 0;
	public static double TRANSITION_PROBABILITY = 1;
	public static double[] H;
	public static String[] inputString;
	public static double[][] inputXk;
	public static int numOfTraningSet;
	public static int numOfTestSet;
	public static String[] testString;
	public static double[][] testXk;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String inputFile = "input.txt";
		String line = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			line = br.readLine();
			N = Integer.parseInt(line);
			line = br.readLine();
			SIGMA = Double.parseDouble(line);
			line = br.readLine();
			H = new double[N];
			String[] tokens = line.split(" ");
			for (int i = 0; i < tokens.length; i++) {
				H[i] = Double.parseDouble(tokens[i]);
			}
			line = br.readLine();
			numOfTraningSet = Integer.parseInt(line);
			inputString = new String[numOfTraningSet];
			for (int i = 0; i < numOfTraningSet; i++) {
				line = br.readLine();
				inputString[i] = line;
			}
			line = br.readLine();
			numOfTestSet = Integer.parseInt(line);
			testString = new String[numOfTestSet];
			for (int i = 0; i < numOfTestSet; i++) {
				line = br.readLine();
				testString[i] = line;
			}
			br.close();
			System.out.println("Num of bits: " + N);
			System.out.println("Sigma: " + SIGMA);
			System.out.print("H: " );
			for (int i = 0; i < H.length; i++) {
				System.out.print(H[i] + " ");
			}
			System.out.println();
			for (int i = 0; i < numOfTraningSet; i++) {
				System.out.println("Input: " + inputString[i]);
			}
			for (int i = 0; i < numOfTestSet; i++) {
				System.out.println("Test: " + testString[i]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Solver solver = new Solver(N);
		//print2DArray(solver.possibleStates);
		//solver.calcTransitionProbability();
		solver.getPossibleTransitionStates();
		
		/*for (int i = 0; i < solver.possibleTransitionStates.length; i++) {
			print2DArray(solver.possibleTransitionStates[i]);
			System.out.println();
		}*/
		//System.out.println(solver.getBit(4));
		inputXk = new double[inputString.length][inputString[0].length() - N + 1];
		for (int i = 0; i < inputXk.length; i++) {
			for (int j = N-1; j < inputString[0].length(); j++) {
				inputXk[i][j-(N-1)] = solver.getXk(j, i);
			}
		}
		
		testXk = new double[testString.length][testString[0].length() - N + 1];
		for (int i = 0; i < testXk.length; i++) {
			for (int j = N-1; j < testString[0].length(); j++) {
				testXk[i][j-(N-1)] = solver.getXk(j, i);
			}
		}
		solver.calcMeanStateProb();
		System.out.println("Test Xk: ");
		print2DArray(testXk);
		//print2DArray(solver.stateProbabilities);
		System.out.println("Mean State Probabilities: ");
		print2DArray(solver.meanStateProbabilities);
		//solver.setInputXk(inputXk);
	}
	
	public static void print1DArray(int[] array){
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void print1DArray(double[] array){
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void print2DArray(int[][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	public static void print2DArray(double[][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}
