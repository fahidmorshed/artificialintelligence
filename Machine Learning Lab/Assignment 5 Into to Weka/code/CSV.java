import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;


public class CSV {
	public String line = "";
	public static int trainingCount = 0;
	public static int testCount = 0;
	public CSV(){
	}
	
	public void trainingTestCSV(String trainingFileName, String testFileName, int pc) throws Exception{
		PrintWriter pw = new PrintWriter(new File(trainingFileName));
		PrintWriter pw2 = new PrintWriter(new File(testFileName));
		BufferedReader br = new BufferedReader(new FileReader("assignment1_data_set.csv"));
		StringBuilder stringBuilder1 = new StringBuilder();
		StringBuilder stringBuilder2 = new StringBuilder();
		trainingCount = 0;
		testCount = 0;
		Random random = new Random();
		line = br.readLine();
		String[] values = line.split(",");
		for (int i = 0; i < values.length-1; i++) {
			stringBuilder1.append(values[i] + ",");
			stringBuilder2.append(values[i] + ",");
		}
    	stringBuilder1.append(values[values.length-1] + "\n");
    	stringBuilder2.append(values[values.length-1] + "\n");
    	
	    while ((line = br.readLine()) != null){
	    	if(random.nextInt(100) > pc){
	    		//System.out.println("YO");
	    		values = line.split(",");
		    	for (int i = 0; i < values.length-1; i++) {
					stringBuilder2.append(values[i] + ",");
				}
		    	stringBuilder2.append(values[values.length-1] + "\n");
		    	//sb.append("\n");
		    	testCount++;
	    		continue;
	    	}
	    	values = line.split(",");
	    	for (int i = 0; i < values.length-1; i++) {
				stringBuilder1.append(values[i] + ",");
			}
	    	stringBuilder1.append(values[values.length-1] + "\n");
	    	//sb.append("\n");
	    	trainingCount++;
	    }
	    br.close();
	    pw.write(stringBuilder1.toString());
	    pw2.write(stringBuilder2.toString());
	    pw.close();
	    pw2.close();
	    //System.out.println("done!");
	}
	
}
