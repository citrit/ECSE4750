/*
 *	Light example, Tom Citriniti
 *
 *
 *  Simple example of programming using the OpenGL graphics library
 *
 *	For demo purposes only, used in:
 *		Advanced Computer Graphics and Data Visualization  35-6961
 *		ECSE, Rensselaer Polytechnic Institute
 *
 *	January 1999 
 */

import jogl.*;import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Lights extends JoglCanvas implements KeyListener, ComponentListener
{
	JoglCanvas gl;
	Box box;
	boolean initLess = true;
	// Light information
	float lightPos[] = {2.0F, 2.0F, 2.0F, 1.0F};
	float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
	float lightAmb[] = {0.7F, 0.7F, 0.7F, 1.0F};
	float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
	float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};
	// Camera information
	float camOrient[] = new float[4];

	// Constructor for our simple class.
	public Lights ()	{
		gl = this;
		box = new Box();
		// This is kind of funky, we are our own listener for key events
		addKeyListener( this );
	}

	// This gets called by the paint function, hopefully only
	// once but it can be called at any time. It needs to be called
	// once per thread.
	public void myInit()	{
		initLess = false;
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();				// Reset the transformation matrix
		gl.ortho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );	// Reset to model transforms
		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
		gl.depthFunc( GL.LEQUAL );
		gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE );
		gl.enable(GL.LIGHTING);
		gl.enable(GL.LIGHT0);
		gl.light(GL.LIGHT0, GL.POSITION, lightPos);
		gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir);
		gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb);
		gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff);
		gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec);
		gl.enable(GL.DEPTH_TEST);
		gl.shadeModel (GL.SMOOTH);
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
		gl.enable(GL.DITHER);
		// rememeber to enable alpha blending and set the func
		gl.enable(GL.ALPHA_TEST);

    }


	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
    public void display() {
		float ambient[] = {0.9F, 0.0F, 0.0F, 1.0F};
		float diffuse[] = {0.2F, 0.2F, 0.2F, 1.0F};
		float specular[] = {0.9F, 0.9F, 0.9F, 1.0F};
		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);		// Lets draw a box
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, ambient[0]);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, diffuse[0]);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, specular[0]);
		box.draw(this);
		// Finish up.
		gl.flush(); // Make sure all commands have completed.
		gl.swap();	 // Swap the render buffer with the screen buffer
	}
	// Simple redirector to our display function
	public void paint(Graphics g)	{
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		super.paint(g);
		//System.out.println("Call to paint");
		if (initLess) myInit();
		display();
	}
	// This is where things get started	
	public static void main( String args[] ) 
    {		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		Lights b = new Lights();
		// Make it visible and set size
		b.setVisible(true);
		b.setSize(300, 300);
		// Add the canvas to the frame and make it show
		fm.add("Center", b);
		fm.pack();
		// Lets tell them how to use the system
		System.out.println("\n\nLights - Simple light example\n" +
			"Copyright (c) 1999 Tom C ;-).\n\n" +
			"\tUse the h,j,k, and l keys to rotate the object\n" +
			"\t + and - to zoom.\n\n");
		fm.show();
    }
	// Handle the keystrokes and modify appropriate object
    // Handle the keystrokes
    public void keyTyped(KeyEvent e) {
	
		switch (e.getKeyChar()) {
		case 'h':
		    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
		    gl.rotate(15.0, 0.0,1.0,0.0);
		    e.consume();
		    break;
		case 'j':
		    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
		    gl.rotate(15.0, 1.0,0.0,0.0);
			e.consume();
			break;
		case 'k':
		    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
		    gl.rotate(-15.0, 1.0,0.0,0.0);
		    e.consume();
		    break;
		case 'l':
		    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
		    gl.rotate(-15.0, 0.0,1.0,0.0);
		    e.consume();
		    break;
		case '+':
		    gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
		    gl.translate(0.0, 0.0,0.5);
		    e.consume();
		    break;
		case '-':
		    gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
		    gl.translate(0.0, 0.0,-0.5);
		    e.consume();
		    break;
		case 27:           /* Esc will quit */
		    System.exit(0);
		    break;
		default:
		    break;
		}
		display();
    }
    public void keyPressed(KeyEvent e) {
	
    }
    public void keyReleased(KeyEvent e) {
	
    }

	/* Functions to implement ComponentListener */
	public void componentResized(ComponentEvent e) 
	{
		System.out.println("Resized: " + e);
		super.componentResized(e);
		initLess = true;
	}
	public void componentHidden(ComponentEvent e) {
		System.out.println("Hidden: " + e);
		super.componentHidden(e);
	}
	public void componentShown(ComponentEvent e) {
			System.out.println("Shown: " + e);
		super.componentShown(e);
	}
	public void componentMoved(ComponentEvent e) {
			System.out.println("Moved: " + e);
		super.componentMoved(e);
	}
}

class Box{	
	static float boxCors[][] = { 
						{-0.5F, -0.5F, -0.5F},
						{0.5F, -0.5F, -0.5F},
						{0.5F,  0.5F, -0.5F},
						{-0.5F,  0.5F, -0.5F},
						{-0.5F, -0.5F,  0.5F},
						{0.5F, -0.5F,  0.5F},
						{0.5F,  0.5F,  0.5F},
						{-0.5F,  0.5F,  0.5F}
	};
	static int boxIndex[] = { 0, 1, 2, 3, // Face 1
						4, 5, 6, 7, // Face 2
						0, 1, 5, 4, // Face 3
						2, 6, 7, 3, // Face 4
						1, 2, 6, 5, // Face 5
						0, 3, 7, 4 };  // Face 6
	
	public Box() {}
	public void draw(JoglCanvas gl)	{

		for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vertexv(gl, boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
	}
	/** Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(JoglCanvas gl, float[] p) {
		gl.vertex(p[0], p[1], p[2]);
	}
}