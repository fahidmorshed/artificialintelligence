import java.util.Random;


public class Solver {
	public int N;
	public int[][] possibleStates;
	public int[][][] possibleTransitionStates;
	public double[][] stateProbabilities;
	public double[][] meanStateProbabilities;
	public double[] inputXk;
	
	public Solver(int N) {
		// TODO Auto-generated constructor stub
		this.N = N;
		possibleStates = new int[(int) Math.pow(2, N)][N];
		getAllPossibleStates(N);
		//transitionProbablity = new double[possibleStates.length][possibleStates.length];
		possibleTransitionStates = new int[possibleStates.length][2][N];
		stateProbabilities = new double[possibleStates.length][2];
		meanStateProbabilities = new double[stateProbabilities.length][2];
		//ChannelEqualizer.print2DArray(possibleStates);
	}
	
	public void setInputXk(double[] input){
		inputXk = input;
	}
	
	public void calcMeanStateProb(){
		for (int i = 0; i < stateProbabilities.length; i++) {
			meanStateProbabilities[i][0] = stateProbabilities[i][0]/stateProbabilities[i][1];
			meanStateProbabilities[i][1] = possibleStates[i][0];
		}
	}
	
	public void getPossibleTransitionStates(){
		for (int i = 0; i < possibleStates.length; i++) {
			for (int k = 0; k < N-1; k++) {
				possibleTransitionStates[i][0][k] = possibleStates[i][k+1];
				possibleTransitionStates[i][1][k] = possibleStates[i][k+1];
			}
			possibleTransitionStates[i][0][N-1] = 0;
			possibleTransitionStates[i][1][N-1] = 1;
		}
	}
	
	public void getAllPossibleStates(int N){
		int numOfPossibleStates = (int) Math.pow(2, N);
		for(int i = 0; i < numOfPossibleStates; i++) {
			for (int j = 0; j < N; j++) {
				possibleStates[i][possibleStates[i].length-j-1] = (i/((int)Math.pow(2,j)))%2;
			}  
		}
	}
	
	public double getXk(int k, int strNum){
		double x = 0;
		for (int i = 0; i < ChannelEqualizer.N; i++) {
			x += getBit(k-i, strNum) * ChannelEqualizer.H[i];
		}
		x += getRandom();
		stateProbabilities[findStateID(k, strNum)][0] += x;
		stateProbabilities[findStateID(k, strNum)][1] += 1;
		return x;
	}
	
	public double getTestXk(int k, int strNum){
		double x = 0;
		for (int i = 0; i < ChannelEqualizer.N; i++) {
			x += getTestBit(k-i, strNum) * ChannelEqualizer.H[i];
		}
		x += getRandom();
		return x;
	}
	
	public int findStateID(int k, int strNum){
		int []temp = new int[ChannelEqualizer.N];
		for (int i = 0; i < temp.length; i++) {
			if(getBit(k-i, strNum)==-1){
				temp[i] = 0;
			}
			else{
				temp[i] = 1;
			}
		}
		
		for (int i = 0; i < possibleStates.length; i++) {
			boolean flag = true;
			for (int j = 0; j < possibleStates[0].length; j++) {
				if(temp[j] != possibleStates[i][j]){
					flag = false;
					break;
				}
			}
			if(flag == true){
				return i;
			}
		}
		return -1;
	}
	
	public int getBit(int i, int strNum){
		i = ChannelEqualizer.inputString[strNum].length() - 1 - i;
		if(i>=ChannelEqualizer.inputString[strNum].length()) return 0;
		String temp = ChannelEqualizer.inputString[strNum].charAt(i) + "";
		if(Integer.parseInt(temp) == 0){
			return -1;
		}
		else {
			return +1;
		}
	}
	
	public int getTestBit(int i, int strNum){
		i = ChannelEqualizer.testString[strNum].length() - 1 - i;
		if(i>=ChannelEqualizer.testString[strNum].length()) return 0;
		String temp = ChannelEqualizer.testString[strNum].charAt(i) + "";
		if(Integer.parseInt(temp) == 0){
			return -1;
		}
		else {
			return +1;
		}
	}
	
	public double getRandom(){
		Random random = new Random();
		double mySample = random.nextGaussian()*ChannelEqualizer.SIGMA + 0.0;
		return mySample;
	}
	
	public double smallDCapInit(int k, int i, int j){
		return (1/Math.pow(2,n)) * meanStateProbabilities[i][0];
	}
	
//	public void calcTransitionProbability(){
//		for (int i = 0; i < possibleStates.length; i++) {
//			for (int j = 0; j < possibleStates.length; j++) {
//				if(i==j) transitionProbablity[i][j] = 0;
//				else if(isTransitionPossible(i, j)){
//					transitionProbablity[i][j] = ChannelEqualizer.TRANSITION_PROBABILITY;
//				}
//				else {
//					transitionProbablity[i][j] = 0;
//				}
//			}
//		}
//	}
//	
//	public boolean isTransitionPossible(int fromId, int toId){
//		int[] from = possibleStates[fromId];
//		int[] to = possibleStates[toId];
//		int count = 0;
//		for (int i = 0; i < to.length; i++) {
//			if(from[i] != to[i])
//				count++;
//			if(count == 2)
//				return false;
//		}
//		return true;
//	}
}
