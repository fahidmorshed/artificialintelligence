
public class Node {
	public int id;
	public double posX;
	public double posY;
	
	public Node(int id, double posX, double posY){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
	}
	
	public String toString(){
		String string = this.id + ": " + this.posX + " " + this.posY;
		return string;
	}
}
