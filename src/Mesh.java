import java.util.ArrayList;

public class Mesh {
	private String name;
	private ArrayList<Triangle> tris;
	
	public Mesh(String name, ArrayList<Triangle> tris) {
		this.name = name;
		this.tris = tris;
	}
	
	public void rotateAxis(double[][] matrix) {
		for (Triangle triangle : this.tris) {
			triangle.getVertexA().multiplyByMatrix(matrix);
			triangle.getVertexB().multiplyByMatrix(matrix);
			triangle.getVertexC().multiplyByMatrix(matrix);
		}
	}
		
	
	public String getMeshName() {
		return this.name;
	}
	
	public ArrayList<Triangle> getTris() {
		return this.tris;
	}
	
		
	
}
