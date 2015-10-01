/*
 *	Simple sample of Java and OpenGL
 *
 *
 *  Simple example of programming using the OpenGL graphics library
 *  Taken from the "OpenGL Programming Guide"
 *
 *	For demo purposes only, used in:
 *		Advanced Computer Graphics and Data Visualization  35-6961
 *		ECSE, Rensselaer Polytechnic Institute
 *
 *	January 1998
 */

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Simple extends JoglCanvas implements KeyListener
{

	int cone1 = 0;
	int cone2 = 0;
	float cone3 = 0;
	float cone4 = 0;
	float sph1 = 0;
	float sph2 = 0;
	int sph3 = 0;
	int box1 = 0;
	int box2 = 0;
	int box3 = 0;
	float box4 = 0;
	float box5 = 0;

	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;

	// Constructor for our simple class.
	public Simple ()
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
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();					// Reset the transformation matrix
		gl.ortho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms

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

		float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};
		float boxDiff[] = {0.2F, 0.2F, 0.2F, 1.0F};
		float boxSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};

		float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
		float sphDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
		float sphSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};	

		float coneAmb[] = {0.0F, 0.0F, 0.7F, 1.0F};
		float coneDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
		float coneSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};

		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);

		gl.pushMatrix();		
		gl.translate(box4, 0 , 0);
		gl.translate(0,box5,0);
		gl.rotate(box1, 1.0,0.0,0.0);
		gl.rotate(box2, 0.0,1.0,0.0);
		gl.rotate(box3, 0.0,0.0,1.0);

		gl.callList(1);
		gl.popMatrix();

		// add a sphere. 
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, sphDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);
		
		gl.pushMatrix();

		gl.translate(-2, 0, 0);
		gl.translate(sph1, 0 , 0);
		gl.translate(0,sph2,0);  
		glu.sphere(0.5, 32, 32);
		gl.popMatrix();

		//add a cone
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, coneAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, coneDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, coneSpec);
		
		gl.pushMatrix();
		gl.translate(2,0,0);	
		gl.translate(cone3, 0 , 0);
		gl.translate(0,cone4,0);
		gl.rotate(cone1, 1.0,0.0,0.0);
		gl.rotate(cone2, 0.0,1.0,0.0);
		glu.cylinder(0.0,0.5,1,16,16);

		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	

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
		Simple b = new Simple();

		// Make it visible and set size
		b.setVisible(true);
		b.setSize(400, 400);
		System.out.println("Here we go");
		System.out.println("Numbers 1&2 rotate the cone, and 3-6 translate the cone");
		System.out.println("Letters q,w,e rotate the box, and r,t,y,u translate the box");
		System.out.println("Letters a,s,d,f translate the sphere");
		System.out.println("< scales the figures down, > scales them up");
		System.out.println("The camera view is the same as from simple3");
		// Add the canvas to the frame and make it show
  		fm.add("Center", b);
		fm.pack();
		fm.show();

    }

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
		case '1':
			cone1 += 30;
			e.consume();
			break;
		case '2':
			cone2 += 30;
			e.consume();
			break;
		case '3':
			cone3 += .25;
			e.consume();
			break;

		case '4':
			cone3 -= .25;
			e.consume();
			break;
		case '5':
			cone4 += .25;
			e.consume();
			break;

		case '6':
			cone4 -= .25;
			e.consume();
			break;

		case 'q':
			box1 += 30;
			e.consume();
			break;
		case 'w':
			box2 += 30;
			e.consume();
			break;
		case 'e':
			box3 += 30;
			e.consume();
			break;
		case 'r':
			box4 += .25;
			e.consume();
			break;
		case 't':
			box4 -= .25;
			e.consume();
			break;
		case 'y':
			box5 += .25;
			e.consume();
			break;
		case 'u':
			box5 -= .25;
			e.consume();
			break;
		case 'a':
			sph1 += .25;
			e.consume();
			break;
		case 's':
			sph1 -= .25;
			e.consume();
			break;
		case 'd':
			sph2 += .25;
			e.consume();
			break;
		case 'f':
			sph2 -= .25;
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
		case '<':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate Projection matrix  */
			gl.scale(0.8, 0.8,0.8);
			e.consume();
			break;

		case '>':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate Projection matrix  */
			gl.scale(1.25, 1.25, 1.25);
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

