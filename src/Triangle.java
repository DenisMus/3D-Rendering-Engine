
public class Triangle {
	
	private Vertex a;
	private Vertex b;
	private Vertex c;
	
	public Triangle(Vertex a, Vertex b, Vertex c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Vertex getVertexA() {
		return this.a;
	}
	
	public Vertex getVertexB() {
		return this.b;
	}
	
	public Vertex getVertexC() {
		return this.c;
	}
	
}
