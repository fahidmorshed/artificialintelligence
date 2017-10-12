
public class Points {
	double x;
	double y;
	public Points() {
		// TODO Auto-generated constructor stub
	}
	public Points(double x,  double y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return x + " " + y;
	}
	
	public double getDistance(Points b){
		return Math.sqrt((x-b.x)*(x-b.x) + (y-b.y)*(y-b.y));
	}
}
