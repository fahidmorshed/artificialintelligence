import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/***nodeArray[0] is free... all city are numbered from 1 to N inclusive.***/

public class TSPSolver {
	public static int N;
	public static int SIGMA = 500;
	public static int ITERATION = 10;
	public static double SOLUTION;
	public static String fileName = "ulysses22";
	//public CityMap map;
	//public static int[][] distance;
	//public static Node[] nodeArray;
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Node[] nodeArray = null;
		BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
            new FileInputStream(fileName + ".tsp")));
            
            while (reader.ready()) {
                String line = reader.readLine();
                //split a line with spaces
                if (line.contains("DIMENSION")) {
					N = Integer.parseInt(line.split(":")[1].trim());
					nodeArray = new Node[N+1];
					//System.out.println(N);
					break;
				}
            }
            while (reader.ready()) {
				String line = reader.readLine();
				if(line.contains("NODE_COORD_SECTION")){
					//System.out.println("Initializing...");
					break;
				}
			}
            while (reader.ready()) {
            	String line = reader.readLine();
            	line = line.replaceAll("^\\s+", "");
            	line = line.trim().replaceAll(" +", " ");
            	//System.out.println(line);
            	if(line.contains("EOF")) break;
            	int id = -1;
            	double[] numbers = new double[2];
            	int i = 0;
            	for (String string : line.split(" ")) {
            		if(i==0) id = Integer.parseInt(string.trim());
            		else{
            			numbers[i-1] = Double.parseDouble(string.trim());
            		}
            		i++;
				}
            	nodeArray[id] = new Node(id, numbers[0], numbers[1]);
			}
            reader.close();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        BufferedReader reader2;
        try {
            reader2 = new BufferedReader(new InputStreamReader(
            new FileInputStream("soln.txt")));
            String line;
            while (reader2.ready()) {
                line = reader2.readLine();
                //split a line with spaces
                if (line.contains(fileName)) {
                	int i = 0;
                	for (String string : line.split(" : ")) {
						if (i==0) {
							i++;
							continue;
						}
						else {
							SOLUTION = Double.parseDouble(string.trim());
							//System.out.println(SOLUTION);
						}
					}
					break;
				}
            }
            reader2.close();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        CityMap map = new CityMap(nodeArray);
        //map.printCityMap();
        //map.printDistance();
        //map.printTopology(map.topology);
        System.out.println("MAP: " + fileName + " || Best Known Solution: " + SOLUTION);
        //map.heuristic2();
        map.H1();
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("1105021.txt", true)))) {
            out.println(map);
            out.close();
            //more code
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        //map.printTopology(map.bestTopology);
        //map.printTopology(map.newTopology);
        System.out.println(map);
	}
}
