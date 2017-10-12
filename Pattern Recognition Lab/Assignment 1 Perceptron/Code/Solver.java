import java.util.ArrayList;
import java.util.Random;

public class Solver {
	Vector[] traningSet;
	ArrayList<Vector> testSet;
	Vector wVector;
	double learningRate = 0;
	int wrongCount = 0;
	
	public Solver() {
		traningSet = new Vector[PerceptronAlgo.totalTraningData];
		testSet = new ArrayList<Vector>();
	}
	
	public void addTraningData(int i, String[] values){
		double[] input = new double[PerceptronAlgo.featureNum+1];
		for (int j = 0; j < values.length; j++) {
			input[j] = Double.parseDouble(values[j]);
		}
		traningSet[i] = new Vector(input);
		//System.out.println(input[0] + " " + input[1] + " " + input[2] + " " + input[3]);
	}
	
	public void addTestData(int i, String[] values){
		double[] input = new double[PerceptronAlgo.featureNum+1];
		for (int j = 0; j < values.length; j++) {
			input[j] = Double.parseDouble(values[j]);
		}
		testSet.add(new Vector(input));
		//System.out.println(input[0] + " " + input[1] + " " + input[2] + " " + input[3]);
	}
	
	private void init(){
		Random random = new Random();
		wVector = new Vector();
		for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
			wVector.vectorValues[i] = random.nextInt(1000)/999.0;
		}
		learningRate = .7;//random.nextInt(1000)/999.0;
	}
	
	public void normalPerceptron(){
		init();
		int t = 0;
		ArrayList<Vector> wrongVectors;
		do {
			wrongVectors = new ArrayList<Vector>();
			for (int i = 0; i < PerceptronAlgo.totalTraningData; i++) {
				double res = wVector.vectorMulti(traningSet[i]);
				if(res > 0 && traningSet[i].classType == 2) {		//wrong Classification
					wrongVectors.add(traningSet[i].scalarMulti(1));
				}
				else if(res < 0 && traningSet[i].classType == 1){	//wrong Classification
					wrongVectors.add(traningSet[i].scalarMulti(-1));
				}
			}
			Vector sumOfWorngVector = new Vector();
			for (int i = 0; i < wrongVectors.size(); i++) {
				sumOfWorngVector = sumOfWorngVector.addVector(wrongVectors.get(i));
			}
			sumOfWorngVector = sumOfWorngVector.scalarMulti(-learningRate);
			wVector = wVector.addVector(sumOfWorngVector);
			//TODO adjust learning rate ??
			t++;
		} while (t<PerceptronAlgo.MAX_ITERATION && wrongVectors.size()!=0);
		showResult();
	}
	
	public void rAndPPerceptron(){
		init();
		int t = 0;
		boolean flag = false;
		do {
			//wrongVectors = new ArrayList<Vector>();
			for (int i = 0; i < PerceptronAlgo.totalTraningData; i++) {
				double res = wVector.vectorMulti(traningSet[i]);
				if(res > 0 && traningSet[i].classType == 2) {		//wrong Classification
					wVector = wVector.addVector(traningSet[i].scalarMulti(-learningRate));
					flag = true;
				}
				else if(res < 0 && traningSet[i].classType == 1){	//wrong Classification
					wVector = wVector.addVector(traningSet[i].scalarMulti(learningRate));
					flag = true;
				}
			}
			t++;
		} while (t<PerceptronAlgo.MAX_ITERATION && flag == true);
		showResult();
	}
	
	public void pocketPerceptron(){
		init();
		int t = 0;
		ArrayList<Vector> wrongVectors;
		int count = 0; 
		int prevCount = 0;
		Vector wVectorPocket = new Vector();
		do {
			count = 0;
			wrongVectors = new ArrayList<Vector>();
			for (int i = 0; i < PerceptronAlgo.totalTraningData; i++) {
				double res = wVector.vectorMulti(traningSet[i]);
				if(res > 0 && traningSet[i].classType == 2) {		//wrong Classification
					wrongVectors.add(traningSet[i].scalarMulti(1));
				}
				else if(res < 0 && traningSet[i].classType == 1){	//wrong Classification
					wrongVectors.add(traningSet[i].scalarMulti(-1));
				}
			}
			Vector sumOfWorngVector = new Vector();
			for (int i = 0; i < wrongVectors.size(); i++) {
				sumOfWorngVector = sumOfWorngVector.addVector(wrongVectors.get(i));
			}
			sumOfWorngVector = sumOfWorngVector.scalarMulti(-learningRate);
			wVector = wVector.addVector(sumOfWorngVector);
			//TODO adjust learning rate ??
			for (int i = 0; i < PerceptronAlgo.totalTraningData; i++) {
				double res = wVector.vectorMulti(traningSet[i]);
				if(res < 0 && traningSet[i].classType == 2) {		//wrong Classification
					count++;
				}
				else if(res > 0 && traningSet[i].classType == 1){	//wrong Classification
					count++;
				}
			}
			if(count > prevCount){
				for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
					wVectorPocket.vectorValues[i] = wVector.vectorValues[i];
				}
				prevCount = count;
				//System.out.println("TEST: " + count);
			}
			t++;
		} while (t<PerceptronAlgo.MAX_ITERATION && count!=PerceptronAlgo.totalTraningData);
		wVector = wVectorPocket;
		showResult();
	}
	
	
	private void showResult(){
		for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
			System.out.print(" + (" + wVector.vectorValues[i] + "*x" + (PerceptronAlgo.featureNum-i) + ")");
		}
		System.out.println(" = 0");
		for (int i = 0; i < testSet.size(); i++) {
			double res = wVector.vectorMulti(testSet.get(i));
			if(res > 0 && testSet.get(i).classType == 2) wrongCount++;
			else if(res < 0 && testSet.get(i).classType == 1) wrongCount++;
		}
		System.out.println("Worng classification: " + wrongCount + " out of " + testSet.size());
	}
	
	
	
	public void printTraningSet(){
		for (int i = 0; i < traningSet.length; i++) {
			System.out.println(traningSet[i]);
		}
	}
}
