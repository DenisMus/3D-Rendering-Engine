
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
	
	public void multiplyByMatrix(double[][] matrix) {
		// this.x,this.y and this.z will change values,
		// so save the previous values to not affect future calculations
		double prevX = this.x;
		double prevY = this.y;
		double prevZ = this.z;
		
		// fourth component
		this.x = prevX*matrix[0][0] + prevY*matrix[1][0] + prevZ*matrix[2][0] + matrix[3][0];
		this.y = prevX*matrix[0][1] + prevY*matrix[1][1] + prevZ*matrix[2][1] + matrix[3][1];
		this.z = prevX*matrix[0][2] + prevY*matrix[1][2] + prevZ*matrix[2][2] + matrix[3][2];
		double w = prevX*matrix[0][3] + prevY*matrix[1][3] + prevZ*matrix[2][3] + matrix[3][3];
		this.x /= w;
		this.y /= w;
		this.z /= w;
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
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}	
	
}
