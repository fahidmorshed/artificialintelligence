
public class Solver {
	
	public static int INFINITY = 99999999;
	
	public int[][] ref1;
	public int[][] ref2;
	public int[][] test;
	public int brutX;
	public int brutY;
	
	public class Point{
		int x;
		int y;
		
		public String toString(){
			String string = "POINT " + this.x + ", " + this.y;
			return string;
		}
	}
	
	public Solver(int[][] ref1, int[][] ref2, int[][] test) {
		// TODO Auto-generated constructor stub
		this.ref1 = ref1;
		this.ref2 = ref2;
		this.test = test;
		System.out.println("REF1 size: " + ref1.length + ", " + ref1[0].length);
		System.out.println("REF2 size: " + ref2.length + ", " + ref2[0].length);
		System.out.println("TEST size: " + test.length + ", " + test[0].length);
		bruteForce(test, ref1);
		bruteForce(test, ref2);
		logSearch(test, ref1);
	}
	
//	public int sumSqr(int[][] array){
//		int sum = 0;
//		for (int i = 0; i < array.length; i++) {
//			for (int j = 0; j < array[i].length; j++) {
//				sum += array[i][j] * array[i][j];
//			}
//		}
//		return sum;
//	}
//	
//	public int correlationSum(int[][] test, int[][] ref, int m, int n){
//		int sum = 0;
//		for (int i = m; i < m+ref.length + 1; i++) {
//			for (int j = n; j < n+ref[i].length + 1; j++) {
//				sum += test[i][j] * ref[i-m][j-n];
//			}
//		}
//		return sum;
//	}
//	
//	public int getD(int[][] test, int[][] ref, int m, int n){
//		return sumSqr(test) + sumSqr(ref) - 2*correlationSum(test, ref, m, n);
//	}
	
	public int getDmn(int[][] test, int[][] ref, int m, int n){
		int sum = 0;
		for (int i = m; i < m+ref.length-1; i++) {
			for (int j = n; j < n+ref[0].length-1; j++) {
				sum += (test[i][j] - ref[i-m][j-n]) * (test[i][j] - ref[i-m][j-n]);
			}
		}
		return sum;
	}
	
	
	public void bruteForce(int[][] test, int[][] ref){
		int minD = INFINITY;
		for (int m = 0; m < test.length - ref.length + 1; m++) {
			for (int n = 0; n < test[m].length - ref[0].length + 1; n++) {
				//System.out.println(m + " " + n);
				int currD = getDmn(test, ref, m, n);
				if(currD < minD){
					minD = currD;
					brutX = m;
					brutY = n;
				}
			}
		}
		System.out.println("BRUTE FORCE POSITION: " + brutX + ", " + brutY );
	}
	
	
	
	public Point getCenter(int[][] img){
		Point point = new Point();
		point.x = (int) Math.ceil(img.length/2.0);
		point.y = (int) Math.ceil(img[0].length/2.0);
		//System.out.println(point);
		return point;
	}
	public Point[] getPoints(Point center, int d){
		int centerX = center.x;
		int centerY = center.y;
		Point[] allPoints = new Point[9];
		for (int i = 0; i < allPoints.length; i++) {
			allPoints[i] = new Point();
		}
		allPoints[0].x = centerX;
		allPoints[0].y = centerY;
		allPoints[1].x = centerX - d;
		allPoints[1].y = centerY;
		allPoints[2].x = centerX - d;
		allPoints[2].y = centerY + d;
		allPoints[3].x = centerX;
		allPoints[3].y = centerY + d;
		allPoints[4].x = centerX + d;
		allPoints[4].y = centerY + d;
		allPoints[5].x = centerX + d;
		allPoints[5].y = centerY;
		allPoints[6].x = centerX + d;
		allPoints[6].y = centerY - d;
		allPoints[7].x = centerX;
		allPoints[7].y = centerY - d;
		allPoints[8].x = centerX - d;
		allPoints[8].y = centerY - d;
		
 		return allPoints;
	}
	
	public void printAllPoints(Point[] allPoints){
		System.out.println("ALL POINTS: ");
		for (int i = 0; i < allPoints.length; i++) {
			System.out.println(allPoints[i]);
		}
	}
	
	public int getK(int p){
		return (int) Math.ceil(TemplateMatching.log2(p));
	}
	
	public int getD(int k){
		return (int) Math.pow(2, k-1);
	}
	
	public void logSearch(int[][] test, int[][] ref){
		int pointNum = -1;
		Point centerPoint = getCenter(test);
		int k = getK(centerPoint.x);
		int d = getD(k);
		//System.out.println(k);
		Point[] allPoints = getPoints(centerPoint, d);
		//printAllPoints(allPoints);
		while(d!=1){	
			//int minD = INFINITY;
			Point point = getTopCornerRef(ref, allPoints[0]);
			int minD = errorEst(test, ref, point.x, point.y);
			pointNum = 0;
			System.out.println(allPoints[0].x + ", " + allPoints[0].y);
			for (int i = 1; i < 8; i+=2) {
				point = getTopCornerRef(ref, allPoints[i]);
				if(point == null)
					continue;
				int currD = errorEst(test, ref, point.x, point.y);
				if(currD < minD){
					pointNum = i;
					minD = currD;
				}
			}
			d = d/2;
			allPoints = getPoints(allPoints[pointNum], d);
		}
		
		int minD = INFINITY;
		pointNum = -1;
		for (int i = 0; i < 9; i++) {
			Point point = getTopCornerRef(ref, allPoints[i]);
			if(point == null)
				continue;
			int currD = errorEst(test, ref, point.x, point.y);
			if(currD < minD){
				pointNum = i;
				minD = currD;
			}
		}
		System.out.println(pointNum);
		System.out.println("LOG SEARCH POSITION: " + allPoints[pointNum].x + ", "+ allPoints[pointNum].y);
		//DEBUGGING
//		k = getK(allPoints[1].x);
//		d = getD(k);
//		allPoints = getPoints(allPoints[1], d);
//		printAllPoints(allPoints);
	}
	
	public Point getTopCornerRef(int[][] ref, Point point){
		int width = ref.length;
		int height = ref[0].length;
		Point topCorner = new Point();
		Point botCorner = new Point();
		topCorner.x = point.x - width/2;
		topCorner.y = point.y + height/2;
		botCorner.x = point.x + width/2;
		botCorner.y = point.y - height/2;
		if(isInBoundary(topCorner) && isInBoundary(botCorner)){
			return topCorner;
		}
		else {
			System.err.println("Cant place.");
			return null;
		}
	}
	
	public boolean isInBoundary(Point point){
		if(point.x < 0 || point.x >= test.length || point.y < 0 || point.y >= test[0].length)
			return false;
		return true;
	}
	
	public int errorEst(int[][] test, int[][] ref, int m, int n){
		int iRef = 0;
		int jRef = ref[0].length;
		int sum = 0;
		for (int i = m; i < m + ref.length; i++) {
			for (int j = n; j > n - ref[0].length; j--) {
				sum += (test[m][n] - ref[iRef][jRef]) * (test[m][n] - ref[iRef][jRef]);
				jRef--;
			}
			iRef++;
		}
		return sum;
	}
}
