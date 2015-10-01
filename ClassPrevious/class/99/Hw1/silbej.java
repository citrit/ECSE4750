/*
 *	Justin Silberberg
 *	Advanced Computer Graphics and Data Visualization  35-6961
 *	Homework 1
 *	8 February 1999
 *
 */

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Hw1 extends JoglCanvas implements KeyListener
{
	double distance=0;
	int twist=0;
	int elevation=0;
	int azimuth=0;
	int cubeRoll=0;
	int cubePitch=0;
	int cubeYaw=0;
	int cylinderRoll=0;
	int cylinderPitch=0;
	int cylinderYaw=0;
	int sphereRoll=0;
	int spherePitch=0;
	int sphereYaw=0;

	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;

	// Constructor for our Hw1 class.
	public Hw1 ()
	{
		gl = this;
		glu = new Quadric();
		// This is kind of funky, we are our own listener for key events
		addKeyListener( this );
	}

	// This gets called by the constructor, hopefully only
	// once but it can be called at any time. Called prior to any
	// Window creation so it can only set variables, not draw things
	public void init()
    {
		float lightPos[] = {0.0F, 0.0F, 2.0F, 1.0F};
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		float lightAmb[] = {0.7F, 0.7F, 0.7F, 1.0F};
		float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
		float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};

		initLess = false;
		
		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
		float boxCors[][] = { {-0.5F, -0.5F, -0.5F},
							{0.5F, -0.5F, -0.5F},
							{0.5F,  0.5F, -0.5F},
							{-0.5F,  0.5F, -0.5F},
							{-0.5F, -0.5F,  0.5F},
							{0.5F, -0.5F,  0.5F},
							{0.5F,  0.5F,  0.5F},
							{-0.5F,  0.5F,  0.5F} };
		int  boxIndex[] = { 0, 1, 2, 3, // Face 1
							4, 5, 6, 7, // Face 2
							0, 1, 5, 4, // Face 3
							2, 6, 7, 3, // Face 4
							1, 2, 6, 5, // Face 5
							0, 3, 7, 4 };  // Face 6
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, -2, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();					// Reset the transformation matrix
		gl.ortho(-6.0, 6.0, -6.0, 6.0, -6.0, 6.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms
		gl.translate(0.0,0.0,distance);
		gl.rotate(twist,0.0,0.0,1.0);
		gl.rotate(elevation,1.0,0.0,0.0);
		gl.rotate(azimuth,0.0,0.0,1.0);

		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
		gl.depthFunc( GL.LEQUAL );
		gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE );

//		gl.color(1.0, 0.0, 0.0);
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

		// Lets draw a box
		
		gl.newList(1, GL.COMPILE);
		for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vertexv(boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
		gl.endList();

    }


	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
    public void display() {

		float boxAmb[] = {0.9F, 0.0F, 0.0F, 1.0F};
		float boxDiff[] = {1.0F, 0.0F, 0.0F, 1.0F};
		float boxSpec[] = {0.2F, 0.2F, 0.0F, 1.0F};
		float cylAmb[] = {0.0F, 0.0F, 0.3F, 1.0F};
		float cylDiff[] = {0.0F, 0.0F, 0.3F, 1.0F};
		float cylSpec[] = {0.1F, 0.1F, 0.1F, 1.0F};
		float sphAmb[] = {1.0F, 1.0F, 0.0F, 0.5F};
		float sphDiff[] = {0.5F, 0.5F, 0.0F, 0.5F};
		float sphSpec[] = {1.0F, 1.0F, 0.0F, 0.0F};
		
		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);


		gl.translate(0.0,0.0,distance);
		gl.rotate(-twist,0.0,0.0,1.0);
		gl.rotate(-elevation,1.0,0.0,0.0);
		gl.rotate(azimuth,0.0,0.0,1.0);

		// add a box
		gl.pushMatrix();
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);
		gl.rotate(cubeRoll, 1.0,0.0,0.0);
		gl.rotate(cubePitch, 0.0,1.0,0.0);
		gl.rotate(cubeYaw, 0.0,0.0,1.0);
		gl.callList(1);
		gl.popMatrix();
		
		// add a cylinder. 
		gl.pushMatrix();
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, cylAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, cylDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, cylSpec);
		gl.translate(2, 0, 0);
		gl.rotate(cylinderRoll, 1.0,0.0,0.0);
		gl.rotate(cylinderPitch, 0.0,1.0,0.0);
		gl.rotate(cylinderYaw, 0.0,0.0,1.0);
		glu.cylinder(0.0, 1.0, 1.5, 32, 32);
		gl.translate(0, 0.0, 1.5);  
		glu.disk(0.0, 1.0, 32, 5);
		gl.popMatrix();

		// add a sphere. 
		gl.pushMatrix();
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, sphDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);
		gl.translate(4, 0, 0);  
		glu.sphere(0.5, 32, 32);
		gl.popMatrix();

		gl.flush(); // Make sure all commands have completed.
		gl.swap();	 // Swap the render buffer with the screen buffer
	}

	// Simple redirector to our display function
	public void paint(Graphics g)
	{
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		super.paint(g);
		//System.out.println("Call to paint");
		if (initLess) init();
		display();
	}

	// This is where things get started
	public static void main( String args[] ) 
    {
		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		Hw1 b = new Hw1();

		// Make it visible and set size
		b.setVisible(true);
		b.setSize(400,400);
		System.out.println("Here we go");
		System.out.println("	");
		System.out.println("Press (H) to move the camera forward, (h) to back up");
		System.out.println("Press (J) to increase twist, (j) to decrease");
		System.out.println("Press (K) to increase elevation, (k) to decrease");
		System.out.println("Press (L) to increase azimuth, (l) to decrease");
		System.out.println("	");
		System.out.println("Press (Q) to increase cube roll, (q) to decrease");
		System.out.println("Press (W) to increase cube pitch, (w) to decrease");
		System.out.println("Press (E) to increase cube yaw, (e) to decrease");
		System.out.println("	");
		System.out.println("Press (A) to increase cube roll, (a) to decrease");
		System.out.println("Press (S) to increase cube pitch, (s) to decrease");
		System.out.println("Press (D) to increase cube yaw, (d) to decrease");
		System.out.println("	");
		System.out.println("Press (Z) to increase cylinder roll, (z) to decrease");
		System.out.println("Press (X) to increase cylinder pitch, (x) to decrease");
		System.out.println("Press (C) to increase cylinder yaw, (c) to decrease");
		System.out.println("	");
		System.out.println("Press (+) to zoom in, (-) to zoom out");
		System.out.println("	");


		// Add the canvas to the frame and make it show
  		fm.add("Center", b);
		fm.pack();
		fm.show();

    }

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {
    
		switch (e.getKeyChar()) {
		case 'H':
			distance=distance+0.25;
			e.consume();
			break;
		case 'h':
			distance=distance-0.25;
			e.consume();
			break;
		case 'J':
			twist=twist+15;
			e.consume();
			break;
		case 'j':
			twist=twist-15;
			e.consume();
			break;
		case 'K':
			elevation=elevation+15;
			e.consume();
			break;
		case 'k':
			elevation=elevation-15;
			e.consume();
			break;
		case 'L':
			azimuth=azimuth+15;
			e.consume();
			break;
		case 'l':
			azimuth=azimuth-15;
			e.consume();
			break;
		case 'Q':
			sphereRoll=sphereRoll+15;
			e.consume();
			break;
		case 'q':
			sphereRoll=sphereRoll-15;
			e.consume();
			break;
		case 'W':
			spherePitch=spherePitch+15;
			e.consume();
			break;
		case 'w':
			spherePitch=spherePitch-15;
			e.consume();
			break;
		case 'E':
			sphereYaw=sphereYaw+15;
			e.consume();
			break;
		case 'e':
			sphereYaw=sphereYaw-15;
			e.consume();
			break;
		case 'A':
			cubeRoll=cubeRoll+15;
			e.consume();
			break;
		case 'a':
			cubeRoll=cubeRoll-15;
			e.consume();
			break;
		case 'S':
			cubePitch=cubePitch+15;
			e.consume();
			break;
		case 's':
			cubePitch=cubePitch-15;
			e.consume();
			break;
		case 'D':
			cubeYaw=cubeYaw+15;
			e.consume();
			break;
		case 'd':
			cubeYaw=cubeYaw-15;
			e.consume();
			break;
		case 'Z':
			cylinderRoll=cylinderRoll+15;
			e.consume();
			break;
		case 'z':
			cylinderRoll=cylinderRoll-15;
			e.consume();
			break;
		case 'X':
			cylinderPitch=cylinderPitch+15;
			e.consume();
			break;
		case 'x':
			cylinderPitch=cylinderPitch-15;
			e.consume();
			break;
		case 'C':
			cylinderYaw=cylinderYaw+15;
			e.consume();
			break;
		case 'c':
			cylinderYaw=cylinderYaw-15;
			e.consume();
			break;
		case '-':
			gl.scale(0.5,0.5,0.5);			/* zoom out  */
			e.consume();
			break;
		case '+':
			gl.scale(1.5,1.5,1.5);			/* zoom out  */
			e.consume();
			break;
		case 27:           /* Esc will quit */
			System.exit(1);
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


	/** Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(float[] p) 
	{
		gl.vertex(p[0], p[1], p[2]);
	}
}

