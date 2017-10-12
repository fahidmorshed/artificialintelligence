import java.util.Vector;


public class Cluster {
	int clusterID;
	Points center;
	Vector<Points> points;
	double avgDistance;
	double oldDistance;
	public Cluster(){
		points = new Vector<Points>();
		avgDistance = 0;
		oldDistance = 0;
	}
	public Cluster(int id) {
		clusterID = id;
		points = new Vector<Points>();
		avgDistance = 0;
		oldDistance = 0;
	}
	
	void addPoint(Points p){
		points.add(p);
	}
	
	void rmvPoint(Points p){
		for (int i = 0; i < points.size(); i++) {
			if(points.get(i).equals(p)){
				points.remove(i);
			}
		}
	}
	
	void rmvPointAll(){
		points.clear();
	}
	
	void calcAll(){
		double totalX=0, totalY=0, totalDist=0;
		for (int i = 0; i < points.size(); i++) {
			totalX += points.get(i).x;
			totalY += points.get(i).y;
		}
		center = new Points(totalX/points.size(), totalY/points.size());
		
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < i; j++) {
				totalDist += points.get(i).getDistance(points.get(j));
				//System.out.println(totalDist);
			}
			totalDist = totalDist / (i+1);
		}
		oldDistance = avgDistance;
		avgDistance = totalDist;
	}
	
	double getDist(Points p){
		return center.getDistance(p);
	}
	
	public String toString(){
		return "Cluster ID: " + clusterID + "\nCenter: " + center + "; Avg Distance: " + avgDistance;
	}
}
