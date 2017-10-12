import java.util.ArrayList;
import java.util.Random;

public class SolverKesler {
	Vector[] traningSet;
	ArrayList<Vector> testSet;
	Vector[] traningSetKeslar;
	Vector wVector;
	ArrayList<Vector> wVectorsKesler;
	double learningRate = 0;
	int wrongCount = 0;
	int wrongCount2 = 0;
	int wrongCount3 = 0;
	int count1 = 0;
	int count2 = 0;
	int count3 = 0;
	
	public SolverKesler() {
		traningSet = new Vector[PerceptronAlgo.totalTraningData2];
		testSet = new ArrayList<Vector>();
		traningSetKeslar = new Vector[(PerceptronAlgo.totalTraningData2)*(PerceptronAlgo.featureNum2-1)];
	}
	
	public void addTraningData(int i, String[] values){
		double[] input = new double[PerceptronAlgo.featureNum2+1];
		for (int j = 0; j < values.length; j++) {
			input[j] = Double.parseDouble(values[j]);
		}
		traningSet[i] = new Vector(input);
		//System.out.println(input[0] + " " + input[1] + " " + input[2] + " " + input[3]);
	}
	
	public void addTestData(int i, String[] values){
		double[] input = new double[PerceptronAlgo.featureNum2+1];
		for (int j = 0; j < values.length; j++) {
			input[j] = Double.parseDouble(values[j]);
		}
		testSet.add(new Vector(input));
		switch ((int) input[3]) {
		case 1:
			count1++;
			break;
		case 2:
			count2++;
			break;
		case 3:
			count3++;
			break;
		default:
			break;
		}
		//System.out.println(input[0] + " " + input[1] + " " + input[2] + " " + input[3]);
	}
	
	private void init(){
		Random random = new Random();
		wVector = new Vector(true); 	//boolean for Keslar form
		for (int i = 0; i < wVector.vectorValues.length; i++) {
			wVector.vectorValues[i] = random.nextInt(1000)/999.0;
		}
		learningRate = .7;//random.nextInt(1000)/999.0;
		//int keslarInputCount = PerceptronAlgo.totalTraningData2 * (PerceptronAlgo.classType2 - 1);
		//int keslarFeatureCount = (PerceptronAlgo.featureNum2 + 1) * (PerceptronAlgo.classType2);
		//int traningI = 0;
		for (int i = 0; i < PerceptronAlgo.totalTraningData2; i++) {
			int clsTyp = traningSet[i].classType;
			Vector negVector = traningSet[i].scalarMulti(-1);
			traningSetKeslar[2*i] = new Vector(true);
			traningSetKeslar[2*i+1] = new Vector(true);
			//System.out.println(i);
			if(clsTyp == 1){
				for (int j = 0; j < 4; j++) {
					traningSetKeslar[2*i].vectorValues[j] = traningSet[i].vectorValues[j];
					traningSetKeslar[2*i+1].vectorValues[j] = traningSet[i].vectorValues[j];
				}
				for (int j = 4; j < 8; j++) {
					traningSetKeslar[2*i].vectorValues[j] = negVector.vectorValues[j-4];
					traningSetKeslar[2*i+1].vectorValues[j] = 0;
				}
				for (int j = 8; j < 12; j++) {
					traningSetKeslar[2*i].vectorValues[j] = 0;
					traningSetKeslar[2*i+1].vectorValues[j] = negVector.vectorValues[j-8];
				}
			}
			else if (clsTyp == 2) {
				for (int j = 0; j < 4; j++) {
					traningSetKeslar[2*i].vectorValues[j] = negVector.vectorValues[j];
					traningSetKeslar[2*i+1].vectorValues[j] = 0;
				}
				for (int j = 4; j < 8; j++) {
					traningSetKeslar[2*i].vectorValues[j] = traningSet[i].vectorValues[j-4];
					traningSetKeslar[2*i+1].vectorValues[j] = traningSet[i].vectorValues[j-4];
				}
				for (int j = 8; j < 12; j++) {
					traningSetKeslar[2*i].vectorValues[j] = 0;
					traningSetKeslar[2*i+1].vectorValues[j] = negVector.vectorValues[j-8];
				}
			}
			else if (clsTyp == 3) {
				for (int j = 0; j < 4; j++) {
					traningSetKeslar[2*i].vectorValues[j] = negVector.vectorValues[j];
					traningSetKeslar[2*i+1].vectorValues[j] = 0;
				}
				for (int j = 4; j < 8; j++) {
					traningSetKeslar[2*i].vectorValues[j] = 0;
					traningSetKeslar[2*i+1].vectorValues[j] = negVector.vectorValues[j-4];
				}
				for (int j = 8; j < 12; j++) {
					traningSetKeslar[2*i].vectorValues[j] = traningSet[i].vectorValues[j-8];
					traningSetKeslar[2*i+1].vectorValues[j] = traningSet[i].vectorValues[j-8];
				}
			}
			
//			int clsTyp = traningSet[traningI].classType;
//			Vector negVector = traningSet[traningI].scalarMulti(-1);
//			for (int j = 0; j < PerceptronAlgo.classType2; j++) {
//				int k = 0;
//				for (int j2 = j*(PerceptronAlgo.featureNum2+1); j2 < (j+1)*(PerceptronAlgo.featureNum2+1); j2++) {
//					if(clsTyp-1 == j){
//						traningSetKeslar[i].vectorValues[j2] = traningSet[traningI].vectorValues[k++];
//					}
//					else {
//						traningSetKeslar[i].vectorValues[j2] = negVector.vectorValues[k++];
//					}
//				}
//			}
		}
		for (int j = 0; j < traningSetKeslar.length; j++) {
			for (int j2 = 0; j2 < 12; j2++) {
				//System.out.print(traningSetKeslar[j].vectorValues[j2] + " ");
			}
			//System.out.println();
		}
	}
	
	public void keslerPerceptron(){
		init();
		int t = 0;
		ArrayList<Vector> wrongVectors;
		do {
			wrongVectors = new ArrayList<Vector>();
			for (int i = 0; i < traningSetKeslar.length; i++) {
				double res = wVector.vectorMulti(traningSetKeslar[i], true);
				if(res < 0) {		//wrong Classification
					wrongVectors.add(traningSetKeslar[i].scalarMulti(1, true));
				}
			}
//				else if(res < 0 && traningSet[i].classType == 1){	//wrong Classification
//					wrongVectors.add(traningSet[i].scalarMulti(-1));
//				}
//			}
			Vector sumOfWorngVector = new Vector(true);
			for (int i = 0; i < wrongVectors.size(); i++) {
				sumOfWorngVector = sumOfWorngVector.addVector(wrongVectors.get(i), true);
			}
			sumOfWorngVector = sumOfWorngVector.scalarMulti(-learningRate, true);
			wVector = wVector.addVector(sumOfWorngVector, true);
//			//TODO adjust learning rate ??
			t++;
		} while (t<PerceptronAlgo.MAX_ITERATION && wrongVectors.size()!=0);
		wVectorsKesler = new ArrayList<Vector>();
		Vector vector = new Vector();
		for (int j = 0; j < 4; j++) {
			vector.vectorValues[j] = wVector.vectorValues[j];
		}
		vector.classType = 1;
		wVectorsKesler.add(vector);
		vector = new Vector();
		for (int j = 0; j < 4; j++) {
			vector.vectorValues[j] = wVector.vectorValues[j+4];
		}
		vector.classType = 2;
		wVectorsKesler.add(vector);
		vector = new Vector();
		for (int j = 0; j < 4; j++) {
			vector.vectorValues[j] = wVector.vectorValues[j+8];
		}
		vector.classType = 3;
		wVectorsKesler.add(vector);
		
		showResult();
	}
	
		
	private void showResult(){
		System.out.println("For Class 1");
		for (int i = 0; i < 4; i++) {
			System.out.print(" + (" + wVector.vectorValues[i] + "*x" + (4-i) + ")");
		}
		System.out.println(" = 0");
		System.out.println("For Class 2");
		for (int i = 4; i < 8; i++) {
			System.out.print(" + (" + wVector.vectorValues[i] + "*x" + (8-i) + ")");
		}
		System.out.println(" = 0");
		System.out.println("For Class 1");
		for (int i = 8; i < 12; i++) {
			System.out.print(" + (" + wVector.vectorValues[i] + "*x" + (12-i) + ")");
		}
		System.out.println(" = 0");
		
		for (int i = 0; i < testSet.size(); i++) {
			Vector w1 = wVectorsKesler.get(0);
			Vector w2 = wVectorsKesler.get(1);
			Vector w3 = wVectorsKesler.get(2);
			double res1 = w1.vectorMulti(testSet.get(i));
			double res2 = w2.vectorMulti(testSet.get(i));
			double res3 = w3.vectorMulti(testSet.get(i));
			res1 = res1 * -1;
			res2 = res2 * -1;
			res3 = res3 * -1;
			//System.out.println(res1 + " " + res2 + " " + res3);
			double max = res1;
			if(res2 > max){
				max = res2;
			}
			if(res3 > max){
				max = res3;
			}
			
			if(res1 == max){
				if(testSet.get(i).classType != 1) this.wrongCount++;
			}else if (res2 == max) {
				if(testSet.get(i).classType != 2) this.wrongCount2++;
			}else if (res3 == max) {
				if(testSet.get(i).classType != 3) this.wrongCount3++;
			}
			
		}
		System.out.println("Worng classification 1: " + wrongCount + " out of " + count1);
		System.out.println("Worng classification 2: " + wrongCount2 + " out of " + count2);
		System.out.println("Worng classification 1: " + wrongCount3 + " out of " + count3);
	}
	
	
	
	public void printTraningSet(){
		for (int i = 0; i < traningSet.length; i++) {
			System.out.println(traningSet[i]);
		}
	}
}
