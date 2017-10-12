import java.util.Random;


/***nodeArray[0] is free... all city are numbered from 1 to N inclusive.***/
public class CityMap {
	Node[] cityMapNodes;
	double[][] distanceGraph;
	int[] topology;
//	int[] newTopology;		//just for testing purpose TODO
	int[] bestTopology;
//	double currentTopoCost;
	double bestTopoCost;
	
	double[] bestCosts;
	int[] minCostTopology;
	double minCost;
	double avgCost;
	double gap;
	
	public CityMap(Node[] nodeArray) {
		cityMapNodes = new Node[TSPSolver.N+1];
		for (int i = 1; i < nodeArray.length; i++) {
			cityMapNodes[i] = new Node(nodeArray[i].id, nodeArray[i].posX, nodeArray[i].posY);
		}
		
		distanceGraph = new double[TSPSolver.N+1][TSPSolver.N+1];
		calcGraph();
		
		topology = randomTopology();
		bestCosts = new double[TSPSolver.ITERATION];
		minCost = 999999;
//		currentTopoCost = topoCost(topology);
//		newTopology = zero1Exchange(topology);
		
	}
	
	public void H1(){
		for (int i = 0; i < TSPSolver.ITERATION; i++) {
			Random rgen = new Random();
			int randomProb = rgen.nextInt(2);
			topology = randomTopology();
			if(randomProb == 0)
				bestCosts[i] = heuristic1();
			else
				bestCosts[i] = heuristic2();
			if(bestCosts[i] < minCost){
				minCost = bestCosts[i];
				minCostTopology = copyArray(bestTopology);
			}
		}
		double total = 0;
		for (int i = 0; i < TSPSolver.ITERATION; i++) {
			total += bestCosts[i];
		}
		avgCost = total/TSPSolver.ITERATION;
		
		gap = ((Math.abs(avgCost - TSPSolver.SOLUTION))/TSPSolver.SOLUTION)*100;
	}
	public double heuristic1(){
		double bestCost = topoCost(topology);
		double currentCost = bestCost;
		int[] currentTopology = copyArray(topology);
		int[] newTopology;
		int i = 0;
		while (i < TSPSolver.SIGMA) {
			Random rgen = new Random();
			int randomPos1 = rgen.nextInt(currentTopology.length-3) + 2;	//apart from 0, 1, and the last one
			int j = 0;
			while (j < TSPSolver.SIGMA) {
				newTopology = zero1Exchange(currentTopology, randomPos1);
				currentCost = topoCost(newTopology);
				if(currentCost < bestCost){
					bestCost = currentCost;
					currentTopology = copyArray(newTopology);
					i = 0;		//reset sigma count
					break;
				}
				j++;
			}
			i++;
		}
		bestTopology = copyArray(currentTopology);
		bestTopoCost = bestCost;
		return bestCost;
	}
	
	public double heuristic2(){
		double bestCost = topoCost(topology);
		double currentCost = bestCost;
		int[] currentTopology = copyArray(topology);
		int[] newTopology;
		int i = 0;
		while (i < TSPSolver.SIGMA) {
			bestCost = topoCost(currentTopology);
			for (int j = 1; j < currentTopology.length - 3; j++) {		//num of eligable nodes to be swapped, 0 is garbage
				for (int j2 = j+1; j2 < currentTopology.length - 2; j2++) {
					newTopology = twoOpt(currentTopology, j, j2);
					currentCost = topoCost(newTopology);
					if(currentCost < bestCost){
						currentTopology = copyArray(newTopology);
						i=0;
						break;
					}
				}
			}
			i++;
		}
		bestTopology = copyArray(currentTopology);
		//printTopology(currentTopology);
		//printTopology(bestTopology);
		bestTopoCost = bestCost;
		
		return bestCost;
	}
	
	private int[] twoOpt(int[] topology, int i, int k){
//		i--; k--;
		int[] newTopology = new int[topology.length];
		newTopology[0] = topology[0];
		int l = 1;
		for (int j = 1; j < i; j++) {
			newTopology[l++] = topology[j];
		}
		for (int j = k; j >= i; j--) {
			newTopology[l++] = topology[j];
		}
		for (int j = k+1; j < newTopology.length; j++) {
			newTopology[l++] = topology[j];
		}
		return newTopology;
	}
	
	public int[] zero1Exchange(int[] topology, int randomPos1){
		int[] newTopology = copyArray(topology);
		Random rgen = new Random();
//		int randomPos1 = rgen.nextInt(newTopology.length-3) + 2;	//apart from 0, 1, and the last one
		int randomPos2 = rgen.nextInt(newTopology.length-3) + 2;	//apart from 0, 1, and the last one
		int city1 = newTopology[randomPos1];
//		System.out.println("TEST: " + city1 + "  " + randomPos2);		//TESTING TODO
		int city2;
		newTopology[randomPos1] = -9; //just a flag
		int currentPos = randomPos2;
		do {
			city2 = newTopology[currentPos];
			newTopology[currentPos] = city1;
			city1 = city2;
			currentPos++;
			if(currentPos == newTopology.length-1)	currentPos = 2;
		} while (city2 != -9);
		
		return newTopology;
	}
	
	private int[] copyArray(int[] array){
		int[] newArray = new int[array.length];
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	public double topoCost(int[] topology){
		double cost = 0;
		for (int i = 1; i < topology.length-1; i++) {
			cost += distanceGraph[topology[i]][topology[i+1]];
		}
		return cost;
	}
	
	public int[] randomTopology(){
		Random rgen = new Random();			//Random number generator			
		int[] array = new int[TSPSolver.N+2];		//0 is not used. and for a cycle, an extra one.
		for (int i = 0; i < array.length-1; i++) {
			array[i] = i;
		}
		array[0] = -1;
		array[array.length-1] = -1;
		
		for (int i=1; i<array.length-1; i++) {
			int randomPosition = rgen.nextInt(array.length-2) + 1; //apart from the last 1
		    
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
		
		array[array.length-1] = array[1];
		return array;
	}
	
	private void calcGraph(){
		for (int i = 1; i < cityMapNodes.length; i++) {
			for (int j = i; j < cityMapNodes.length; j++) {
				distanceGraph[i][j] = calcDist(cityMapNodes[i], cityMapNodes[j]);
				distanceGraph[j][i] = distanceGraph[i][j];
			}
		}
	}
	
	private double calcDist(Node node1, Node node2){
		double x = Math.abs(node1.posX - node2.posX);
		double y = Math.abs(node1.posY - node2.posY);
		return Math.sqrt(x*x + y*y);
	}
	
	public void printCityMap(){
		for (Node city : cityMapNodes) {
			System.out.println(city);
		}
	}
	
	public void printDistance(){
		for (int i = 1; i < distanceGraph.length; i++) {
			for (int j = 1; j < distanceGraph.length; j++) {
				System.out.print(distanceGraph[i][j] + "    ");
			}
			System.out.println();
		}
	}
	
	public void printTopology(int[] topology){
		for (int i = 1; i < topology.length; i++) {
			System.out.print(topology[i] + " ");
		}
		System.out.println();
	}

	public String toString(){
		String string = "MAP: " + TSPSolver.fileName + " || SIGMA: " + TSPSolver.SIGMA + " || ITERATION: "
				+ TSPSolver.ITERATION + " || AVG COST: " + this.avgCost + " || MIN COST: " + this.minCost
				+ " || GAP: " + this.gap;
		return string;
	}
}
