import java.util.ArrayList;


public class Vector {
	double[] vectorValues;
	int classType;
	public Vector() {
		vectorValues = new double[PerceptronAlgo.featureNum + 1];
	}
	public Vector(boolean isKesler) {
		if(isKesler) vectorValues = new double[(PerceptronAlgo.featureNum2 + 1)*(PerceptronAlgo.classType2)];
	}
	public Vector(double[] values){
		vectorValues = new double[PerceptronAlgo.featureNum+1];
		for (int i = 0; i < values.length-1; i++) {
			vectorValues[i] = values[i];
		}
		vectorValues[PerceptronAlgo.featureNum] = 1;
		classType = (int) values[values.length-1];
	}
	
	//TODO
	public Vector(double[] values, boolean isKelser){
		vectorValues = new double[PerceptronAlgo.featureNum2+1];
		for (int i = 0; i < values.length; i++) {
			vectorValues[i] = values[i];
		}
	}
	
	public Vector scalarMulti(double val){
		Vector vector = new Vector();
		for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
			vector.vectorValues[i] = this.vectorValues[i] * val;
		}
		vector.classType = this.classType;
		return vector;
	}
	public Vector scalarMulti(double val, boolean isKesler){
		Vector vector = new Vector(isKesler);
		for (int i = 0; i < vector.vectorValues.length; i++) {
			vector.vectorValues[i] = this.vectorValues[i] * val;
		}
		vector.classType = this.classType;
		return vector;
	}
	
	public double vectorMulti(Vector b){
		double retVal = 0;
		for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
			retVal += (vectorValues[i] * b.vectorValues[i]);
		}
		return retVal;
	}
	public double vectorMulti(Vector b, boolean isKesler){
		double retVal = 0;
		for (int i = 0; i < b.vectorValues.length; i++) {
			retVal += (vectorValues[i] * b.vectorValues[i]);
		}
		return retVal;
	}
	
	public Vector addVector(Vector b){
		Vector vector = new Vector();
		for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
			vector.vectorValues[i] = this.vectorValues[i] + b.vectorValues[i];
		}
		return vector;
	}
	
	public Vector addVector(Vector b, boolean isKesler){
		Vector vector = new Vector(true);
		for (int i = 0; i < vector.vectorValues.length; i++) {
			vector.vectorValues[i] = this.vectorValues[i] + b.vectorValues[i];
		}
		return vector;
	}
	
	public String toString(){
		String retString = "";
		for (int i = 0; i < PerceptronAlgo.featureNum+1; i++) {
			retString += vectorValues[i];
			retString += " ";
		}
		retString += classType;
		return retString;
	}
}
