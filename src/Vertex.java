
public class Vertex {
	/*
	 * 
	 * Our 3 dimensional space looks like this (because of conventions).
	 * Meaning that a larger z value results in a farther object
	 * 
	 * 			  Y	^
	 * 				|    Z
	 * 				|   /
	 * 				|  /
	 * 				| /
	 * 				|/
	 * 				|-------->X
	 *           (0,0,0)
	 */
	private double x;
	private double y;
	private double z;
	
	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
}
