import java.util.ArrayList;

public class Mesh {
	private String name;
	private ArrayList<Triangle> tris;
	
	public Mesh(String name, ArrayList<Triangle> tris) {
		this.name = name;
		this.tris = tris;
	}
	
	
	public String getMeshName() {
		return this.name;
	}
	
	public ArrayList<Triangle> getTris() {
		return this.tris;
	}
	
}
