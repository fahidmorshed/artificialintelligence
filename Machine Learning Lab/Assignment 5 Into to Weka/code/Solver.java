import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.server.ServerCloneException;

import weka.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.Id3;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class Solver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		 
		    // load CSV
			CSV csv = new CSV();
			csv.trainingTestCSV("training.csv", "test.csv", 80);
		    CSVLoader loader = new CSVLoader();
		    loader.setSource(new File("training.csv"));
		    Instances data = loader.getDataSet();
		    loader.setSource(new File("test.csv"));
		    Instances tData = loader.getDataSet();
		 
		    // save ARFF
		    ArffSaver saver = new ArffSaver();
		    saver.setInstances(data);
		    saver.setFile(new File("input.arff"));
		    //saver.setDestination(new File("\\"));
		    saver.writeBatch();
		    saver.setInstances(tData);
		    saver.setFile(new File("test.arff"));
		    saver.writeBatch();
		    
		    
		    BufferedReader reader = new BufferedReader(new FileReader("input.arff"));
		    Instances inputData = new Instances(reader);
		    reader = new BufferedReader(new FileReader("test.arff"));
		    Instances testData = new Instances(reader);
		    reader.close();
		    // setting class attribute
		    inputData.setClassIndex(data.numAttributes()-1);
		    
		    NumericToNominal numericToNominal = new NumericToNominal();
		    
		    String[] options = new String[2];
		    options[0] = "-R";
		    options[1] = "first-last";
		    
		    numericToNominal.setOptions(options);
		    numericToNominal.setInputFormat(inputData);
		    Instances newTrainingData = Filter.useFilter(inputData, numericToNominal);
		    Instances newTestData = Filter.useFilter(testData, numericToNominal);
		    newTrainingData.setClassIndex(8);
		    
		    Id3 wekaId3 = new Id3();
		    wekaId3.buildClassifier(newTrainingData);
		    System.out.println("ID3: ");
		    for (int i = 0; i < testData.numInstances(); i++) {
			    System.out.println(i + ": " + newTestData.instance(i).classValue() + " " + wekaId3.classifyInstance(newTestData.instance(i)));
			}
		    
		    System.out.println("\n\nNAIVE BAYES: ");
		    NaiveBayes naiveBayes = new NaiveBayes();
		    naiveBayes.buildClassifier(newTrainingData);
		    for (int i = 0; i < testData.numInstances(); i++) {
		    	System.out.println(i + ": " + newTestData.instance(i).classValue() + " " + naiveBayes.classifyInstance(newTestData.instance(i)));
			}
		    
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("Solver.main()");
		}
	}

}
