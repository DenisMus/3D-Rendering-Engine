import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Color;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Renderer extends JPanel {
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
			// drawing A -> B
			g2d.draw(new Line2D.Double(triangle.getVertexA().getX(), triangle.getVertexA().getY(),
					triangle.getVertexB().getX(), triangle.getVertexB().getY()));
			
			// drawing B -> C
			g2d.draw(new Line2D.Double(triangle.getVertexB().getX(), triangle.getVertexB().getY(),
					triangle.getVertexC().getX(), triangle.getVertexC().getY()));
			
			// drawing C -> A
			g2d.draw(new Line2D.Double(triangle.getVertexC().getX(), triangle.getVertexC().getY(),
					triangle.getVertexA().getX(), triangle.getVertexA().getY()));
		}
		
		
	}
	
	public static void main(String[] args) {
		
		// basic set up of frame to display our panel on screen
		JFrame frame = new JFrame();
		frame.setTitle("3D Render Engine");
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ---create cube mesh---
		
		// 8 vertices for the cube (order by binary representation)
		Vertex v0 = new Vertex(400,400,400); // (0,0,0)
		Vertex v1 = new Vertex(400,400,500); // (0,0,1)
		Vertex v2 = new Vertex(400,500,400); // and so on ...
		Vertex v3 = new Vertex(400,500,500);
		Vertex v4 = new Vertex(500,400,400);
		Vertex v5 = new Vertex(500,400,500);
		Vertex v6 = new Vertex(500,500,400);
		Vertex v7 = new Vertex(500,500,500);
		
		// front
		Triangle triangle1 = new Triangle(v0, v2, v6);
		Triangle triangle2 = new Triangle(v0, v4, v6);
		// back
		Triangle triangle3 = new Triangle(v5, v7, v3);
		Triangle triangle4 = new Triangle(v5, v1, v3);
		// left
		Triangle triangle5 = new Triangle(v0, v2, v3);
		Triangle triangle6 = new Triangle(v0, v1, v3);
		// right
		Triangle triangle7 = new Triangle(v5, v7, v6);
		Triangle triangle8 = new Triangle(v5, v4, v6);
		// top
		Triangle triangle9 = new Triangle(v2, v3, v7);
		Triangle triangle10 = new Triangle(v2, v6, v7);
		// bottom
		Triangle triangle11 = new Triangle(v4, v5, v1);
		Triangle triangle12 = new Triangle(v4, v0, v1);
		
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
		
		//frame.validate();
		//frame.repaint();
		
	}
	
	
	
}
