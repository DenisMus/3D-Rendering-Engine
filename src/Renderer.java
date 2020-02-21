import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Color;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Renderer extends JPanel {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final double FOV = 90;
	public static final double Z_FAR = 1000;
	public static final double Z_NEAR = 1;	
	
	/*
	 * returns a rotation matrix according to the requested axis and the degree
	 */
	public static double[][] getRotationMatrix(char axis, double theta){
		double sin = Math.sin(Math.toRadians(theta));
		double cos = Math.cos(Math.toRadians(theta)); 
		
		switch(axis) {
		
		case 'X':
		case 'x':
			double[][] xMatrix = {
					{1,			0,			0,			0},
					{0,			cos,		sin,		0},
					{0,			-sin,		cos,		0},
					{0,			0,			0,			1}
			};
			return xMatrix;
			
		case 'Y':
		case 'y':
			double[][] yMatrix = {
					{cos,			0,			-sin,		0},
					{0,				1,			0,			0},
					{sin,			0,			cos,		0},
					{0,				0,			0,			1}
			};
			return yMatrix;
			
		case 'Z':
		case 'z':
			double[][] zMatrix = {
					{cos,			sin,		0,			0},
					{-sin,			cos,		0,			0},
					{0,				0,			1,			0},
					{0,				0,			0,			1}
			};
			return zMatrix;
		
		// if reached here we'll throw exception after the switch
		default:
			
		}
		// in case that the given axis is not valid throw exception
		throw new IllegalArgumentException("Not a valid axis");
		
	}

	private Mesh mesh;
	
	public Renderer(Mesh mesh) {
		this.mesh = mesh;
	}
	
	/*
	 * overriding paintComponent from JPanel to render the graphics we want
	 */
	@Override
	public void paintComponent(Graphics g) {
		// does some stuff in JPanel to prepare for a new draw
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g; 				// gives us more power with g
		g2d.setColor(Color.BLACK);			
		g2d.fillRect(0, 0, getWidth(), getHeight());	// fill screen with black
		// drawing triangles from tris
		g2d.setColor(Color.white);
		
		for (Triangle triangle : this.mesh.getTris()) {
			
			Vertex vertexA = triangle.getVertexA();
			Vertex vertexB = triangle.getVertexB();
			Vertex vertexC = triangle.getVertexC();
			
			// make new vertices to be projected to not change the original vertices
			Vertex projectedVertexA = new Vertex(vertexA.getX(), vertexA.getY(), vertexA.getZ());
			Vertex projectedVertexB = new Vertex(vertexB.getX(), vertexB.getY(), vertexB.getZ());
			Vertex projectedVertexC = new Vertex(vertexC.getX(), vertexC.getY(), vertexC.getZ());
			
			// move current mesh "forward" so the camera won't be "inside" the nesh
			projectedVertexA.setZ(projectedVertexA.getZ() + 5);
			projectedVertexB.setZ(projectedVertexB.getZ() + 5);
			projectedVertexC.setZ(projectedVertexC.getZ() + 5);
		
			// move from 3D space to 2D space into perspective projection
			double tan = Math.tan(Math.toRadians(FOV/2));
			
			double aspectRatio = Math.max(WIDTH/HEIGHT, HEIGHT/WIDTH);
			
			double[][] matrix = {
					{aspectRatio*1/tan,		0,						0,									0},
					{0,						aspectRatio*1/tan,		0,									0},
					{0,						0,						(Z_FAR+Z_NEAR)/(Z_FAR-Z_NEAR),		1},
					{0,						0,						2*(Z_FAR*Z_NEAR)/(Z_FAR-Z_NEAR),	0}
			};
			projectedVertexA.multiplyByMatrix(matrix);
			projectedVertexB.multiplyByMatrix(matrix);
			projectedVertexC.multiplyByMatrix(matrix);
			
			// convert from [-1,1] to [0,1] with respect to height and width
			projectedVertexA.setX((projectedVertexA.getX() + 1)*0.5*WIDTH);
			projectedVertexA.setY((projectedVertexA.getY() + 1)*0.5*HEIGHT);
			
			projectedVertexB.setX((projectedVertexB.getX() + 1)*0.5*WIDTH);
			projectedVertexB.setY((projectedVertexB.getY() + 1)*0.5*HEIGHT);
			
			projectedVertexC.setX((projectedVertexC.getX() + 1)*0.5*WIDTH);
			projectedVertexC.setY((projectedVertexC.getY() + 1)*0.5*HEIGHT);
			
			
			
			
			// drawing A -> B
			g2d.draw(new Line2D.Double(projectedVertexA.getX(), projectedVertexA.getY(),
					projectedVertexB.getX(), projectedVertexB.getY()));
			
			// drawing B -> C
			g2d.draw(new Line2D.Double(projectedVertexB.getX(), projectedVertexB.getY(),
					projectedVertexC.getX(), projectedVertexC.getY()));
			
			// drawing C -> A
			g2d.draw(new Line2D.Double(projectedVertexC.getX(), projectedVertexC.getY(),
					projectedVertexA.getX(), projectedVertexA.getY()));
		}
		
		
	}
	
	public static void main(String[] args) {
		
		// basic set up of frame to display our panel on screen
		JFrame frame = new JFrame();
		frame.setTitle("3D Render Engine");
		frame.setSize(HEIGHT,WIDTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		// ---create cube mesh---
		
		// front
		Triangle triangle1 = new Triangle(new Vertex(0,0,0), new Vertex(0,1,0), new Vertex(1,1,0));
		Triangle triangle2 = new Triangle(new Vertex(0,0,0), new Vertex(1,0,0), new Vertex(1,1,0));
		// back
		Triangle triangle3 = new Triangle(new Vertex(1,0,1), new Vertex(1,1,1), new Vertex(0,1,1));
		Triangle triangle4 = new Triangle(new Vertex(1,0,1), new Vertex(0,0,1), new Vertex(0,1,1));
		// left
		Triangle triangle5 = new Triangle(new Vertex(0,0,0), new Vertex(0,1,0), new Vertex(0,1,1));
		Triangle triangle6 = new Triangle(new Vertex(0,0,0), new Vertex(0,0,1), new Vertex(0,1,1));
		// right
		Triangle triangle7 = new Triangle(new Vertex(1,0,1), new Vertex(1,1,1), new Vertex(1,1,0));
		Triangle triangle8 = new Triangle(new Vertex(1,0,1), new Vertex(1,0,0), new Vertex(1,1,0));
		// top
		Triangle triangle9 = new Triangle(new Vertex(0,1,0), new Vertex(0,1,1), new Vertex(1,1,1));
		Triangle triangle10 = new Triangle(new Vertex(0,1,0), new Vertex(1,1,0), new Vertex(1,1,1));
		// bottom
		Triangle triangle11 = new Triangle(new Vertex(1,0,0), new Vertex(1,0,1), new Vertex(0,0,1));
		Triangle triangle12 = new Triangle(new Vertex(1,0,0), new Vertex(0,0,0), new Vertex(0,0,1));
		
		// adding triangles to tris
		ArrayList<Triangle> tris = new ArrayList<Triangle>();;
		tris.add(triangle1);
		tris.add(triangle2);
		tris.add(triangle3);
		tris.add(triangle4);
		tris.add(triangle5);
		tris.add(triangle6);
		tris.add(triangle7);
		tris.add(triangle8);
		tris.add(triangle9);
		tris.add(triangle10);
		tris.add(triangle11);
		tris.add(triangle12);
		
		// creating mesh with tris
		Mesh mesh = new Mesh("Cube", tris);
		
		// rendering mesh
		Renderer renderer = new Renderer(mesh);
		frame.setContentPane(renderer);
		
		frame.setVisible(true);
		
		for(int i=0;i<100000;i++) {

		try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// make rotations and movements
		mesh.rotateAxis(getRotationMatrix('X',0.1));
		mesh.rotateAxis(getRotationMatrix('Y',-0.1));
		mesh.rotateAxis(getRotationMatrix('Z',0.2));
		
		frame.validate();
		frame.repaint();
		}
	
	}
	
}
