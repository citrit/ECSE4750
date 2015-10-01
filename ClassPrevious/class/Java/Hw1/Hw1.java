/*
 *	Hw1 sample, Tom Citriniti
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Hw1 extends JoglCanvas implements KeyListener, ActionListener
{
	JoglCanvas gl;
	Box box;
	Sphere sphere;
	Cone cone;
	boolean initLess = true;
	static int objMode = 0;
	String newline;

	// Constructor for our simple class.
	public Hw1 ()
	{
		gl = this;
		box = new Box();
		sphere = new Sphere();
		sphere.translate(-2, 0, 0);
		cone = new Cone();
		cone.translate(2, 0, 0);
        newline = System.getProperty("line.separator");
		// This is kind of funky, we are our own listener for key events
		addKeyListener( this );
	}

	// This gets called by the constructor, hopefully only
	// once but it can be called at any time. Called prior to any
	// Window creation so it can only set variables, not draw things
	public void init()
    {
		float lightPos[] = {2.0F, 2.0F, 2.0F, 1.0F};
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		float lightAmb[] = {0.7F, 0.7F, 0.7F, 1.0F};
		float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
		float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};

		initLess = false;
		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();				// Reset the transformation matrix
		gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );	// Reset to model transforms

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
		gl.enable(GL.ALPHA_TEST);
		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
    }


	// Sort of a legacy type function call.
	// This stuff should be in the paint call
    public void display() {

		float ambient[][] = {{0.9F, 0.0F, 0.0F, 1.0F},	// Box
							 {0.0F, 0.0F, 0.7F, 1.0F},	// Cone
							 {0.7F, 0.7F, 0.0F, 0.75F} };// Sphere
		float diffuse[][] = {{0.2F, 0.2F, 0.2F, 1.0F},
							 {0.8F, 0.8F, 0.8F, 1.0F},
							 {0.3F, 0.3F, 0.3F, 0.75F} };
		float specular[][] = {{0.7F, 0.7F, 0.7F, 1.0F},
							 {0.2F, 0.2F, 0.2F, 1.0F},
							 {0.7F, 0.7F, 0.7F, 0.75F} };

		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);

		// Lets draw a box
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, ambient[0]);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, diffuse[0]);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, specular[0]);
		box.draw(this);
		// Add the cone
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, ambient[1]);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, diffuse[1]);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, specular[1]);
		cone.draw(this);
		// add a sphere.
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, ambient[2]);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, diffuse[2]);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, specular[2]);
		sphere.draw(this);
		// Finish up.
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
		b.setSize(300, 300);

		// Add the canvas to the frame and make it show
  		fm.add("Center", b);

		fm.pack();

		// Lets tell them how to use the system
		System.out.println("\n\nHome Work 1 - Simple graphics viewer and manipulator\n" +
			"Copyright (c) 1998 Tom C ;-).\n\n" +
			"\tUse the h,j,k, and l keys to rotate the current object\n" +
			"\tUse the + and - to translate the current object\n" +
			"\tUse the < and > keys to scale the current object\n" +
			"\tUse the\n" +
			"\t\t 0 key to make the camera the current object\n" +
			"\t\t 1 key to make the box the current object\n" +
			"\t\t 2 key to make the cone the current object\n" +
			"\t\t 3 key to make the sphere the current object\n\n" +
			"Enjoy!\n");

		fm.show();

    }

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {

    
		switch (e.getKeyChar()) {
		case 'h':
			if (objMode == 0) { // Rotate camera
				gl.matrixMode (GL.PROJECTION); /* manipulate modelview matrix  */
				gl.rotate(15.0, 0.0,1.0,0.0);
			}
			else if(objMode == 1) box.rotateY(5);
			else if(objMode == 2) cone.rotateY(5);
			else if(objMode == 3) sphere.rotateY(5);
			break;
		case 'j':
			if (objMode == 0) { // Rotate camera
				gl.matrixMode (GL.PROJECTION); /* manipulate modelview matrix  */
				gl.rotate(15.0, 1.0,0.0,0.0);
			}
			else if(objMode == 1) box.rotateX(5);
			else if(objMode == 2) cone.rotateX(5);
			else if(objMode == 3) sphere.rotateX(5);
			break;
		case 'k':
			if (objMode == 0) { // Rotate camera
				gl.matrixMode (GL.PROJECTION); /* manipulate modelview matrix  */
				gl.rotate(-15.0, 1.0,0.0,0.0);
			}
			else if(objMode == 1) box.rotateX(-5);
			else if(objMode == 2) cone.rotateX(-5);
			else if(objMode == 3) sphere.rotateX(-5);
			break;
		case 'l':
			if (objMode == 0) { // Rotate camera
				gl.matrixMode (GL.PROJECTION); /* manipulate modelview matrix  */
				gl.rotate(-15.0, 0.0,1.0,0.0);
			}
			else if(objMode == 1) box.rotateY(-5);
			else if(objMode == 2) cone.rotateY(-5);
			else if(objMode == 3) sphere.rotateY(-5);
			break;
		case '+':
			if(objMode == 1) box.translate(0, 1, 0);
			else if(objMode == 2) cone.translate(0,1,0);
			else if(objMode == 3) sphere.translate(0,1,0);
			break;
		case '-':
			if(objMode == 1) box.translate(0, -1, 0);
			else if(objMode == 2) cone.translate(0,-1,0);
			else if(objMode == 3) sphere.translate(0,-1,0);
			break;
		case '<':
			if(objMode == 1) box.scale(-0.25F);
			else if(objMode == 2) cone.scale(-0.25F);
			else if(objMode == 3) sphere.scale(-0.25F);
			break;
		case '>':
			if(objMode == 1) box.scale(0.25F);
			else if(objMode == 2) cone.scale(0.25F);
			else if(objMode == 3) sphere.scale(0.25F);
			break;
		case 's':
		case 'S':
			box.wireFrameOff();
			cone.wireFrameOff();
			sphere.wireFrameOff();
			break;
		case 'w':
		case 'W':
			box.wireFrameOn();
			cone.wireFrameOn();
			sphere.wireFrameOn();
			break;
		case '1':
			objMode = 1;
			break;
		case '2':
			objMode = 2;
			break;
		case '3':
			objMode = 3;
			break;
		case '0':
			objMode = 0;
			break;
		case 27:           /* Esc will quit */
			System.exit(0);
			break;
		default:
			break;
		}
		e.consume();
		display();
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}


	/** Function to handle the menu choices
	*/
	public void actionPerformed(ActionEvent e) {
        System.out.println("\"" + e.getActionCommand()
                      + "\" action detected in menu labeled \""
                      + ((MenuItem)(e.getSource())).getLabel()
                      + "\"." + newline);
    }

}

class Box
{
	// Orientation as rotations around X,Y, and Z
	float orientation[];
	float position[]; // Obvious
	float scale; // Obvious
	boolean wireFrame;

	public Box()
	{
		orientation = new float[3];
		position = new float[3];
		scale = 1;
		wireFrame = false;
	}

	public void draw(JoglCanvas gl)
	{
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

		gl.matrixMode (GL.MODELVIEW);
		gl.pushMatrix();
		gl.loadIdentity();
		gl.translate(position[0], position[1],position[2]);
		gl.scale(scale, scale, scale);
		gl.rotate(orientation[0], 1, 0, 0);
		gl.rotate(orientation[1], 0, 1, 0);
		gl.rotate(orientation[2], 0, 0, 1);
		if (wireFrame)
			gl.polygonMode(GL.FRONT_AND_BACK, GL.LINE);
		else
			gl.polygonMode(GL.FRONT_AND_BACK, GL.FILL);
		for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vertexv(gl, boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
		gl.popMatrix();
	}
	/** Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(JoglCanvas gl, float[] p) 
	{
		gl.vertex(p[0], p[1], p[2]);
	}

	public void rotateX(float angle)
	{
		orientation[0] += angle;
	}
	public void rotateY(float angle)
	{
		orientation[1] += angle;
	}
	public void rotateZ(float angle)
	{
		orientation[2] += angle;
	}
	public void translate(float x, float y, float z)
	{
		position[0] += x;
		position[1] += y;
		position[2] += z;
	}
	public void scale(float sc)
	{
		scale += sc;
	}
	public void wireFrameOn() 
	{
		wireFrame = true;
	}
	public void wireFrameOff() 
	{
		wireFrame = false;
	}
}
class Sphere
{
	// Orientation as rotations around X,Y, and Z
	float orientation[];
	float position[];
	float scale; // Obvious
	Quadric glu;
	boolean wireFrame;

	public Sphere()
	{
		orientation = new float[3];
		position = new float[3];
		glu = new Quadric();
		scale = 1;
		wireFrame = false;
	}

	public void draw(JoglCanvas gl)
	{
		gl.enable(GL.BLEND);
		// Setup the position and scale.
		gl.matrixMode (GL.MODELVIEW);
		gl.pushMatrix();
		gl.loadIdentity();
		gl.translate(position[0], position[1],position[2]);
		gl.scale(scale, scale, scale);
		gl.rotate(orientation[0], 1, 0, 0);
		gl.rotate(orientation[1], 0, 1, 0);
		gl.rotate(orientation[2], 0, 0, 1);
		// Draw the sphere
		if (wireFrame)
			gl.polygonMode(GL.FRONT_AND_BACK, GL.LINE);
		else
			gl.polygonMode(GL.FRONT_AND_BACK, GL.FILL);
		glu.sphere(0.5F, 24, 18);
		gl.popMatrix();
		gl.disable(GL.BLEND);
	}
	public void rotateX(float angle)
	{
		orientation[0] += angle;
	}
	public void rotateY(float angle)
	{
		orientation[1] += angle;
	}
	public void rotateZ(float angle)
	{
		orientation[2] += angle;
	}
	public void translate(float x, float y, float z)
	{
		position[0] += x;
		position[1] += y;
		position[2] += z;
	}
	public void scale(float sc)
	{
		scale += sc;
	}
	public void wireFrameOn() 
	{
		wireFrame = true;
	}
	public void wireFrameOff() 
	{
		wireFrame = false;
	}
}
class Cone
{
	// Orientation as rotations around X,Y, and Z
	float orientation[];
	float position[];
	float scale; // Obvious
	Quadric glu;
	boolean wireFrame;

	public Cone()
	{
		orientation = new float[3];
		position = new float[3];
		position[2] = -0.5F;
		glu = new Quadric();
		scale = 1;
		wireFrame = false;
	}

	public void draw(JoglCanvas gl)
	{
		// Setup the position and scale.
		gl.matrixMode (GL.MODELVIEW);
		gl.pushMatrix();
		gl.loadIdentity();
		gl.translate(position[0], position[1],position[2]);
		gl.scale(scale, scale, scale);
		gl.rotate(orientation[0], 1, 0, 0);
		gl.rotate(orientation[1], 0, 1, 0);
		gl.rotate(orientation[2], 0, 0, 1);
		// Draw the cylinder baseR, topR, height
		if (wireFrame)
			gl.polygonMode(GL.FRONT_AND_BACK, GL.LINE);
		else
			gl.polygonMode(GL.FRONT_AND_BACK, GL.FILL);
		glu.cylinder(0, 0.5F, 1, 16, 16);
		gl.popMatrix();
	}
	public void rotateX(float angle)
	{
		orientation[0] += angle;
	}
	public void rotateY(float angle)
	{
		orientation[1] += angle;
	}
	public void rotateZ(float angle)
	{
		orientation[2] += angle;
	}
	public void translate(float x, float y, float z)
	{
		position[0] += x;
		position[1] += y;
		position[2] += z;
	}
	public void scale(float sc)
	{
		scale += sc;
	}
	public void wireFrameOn() 
	{
		wireFrame = true;
	}
	public void wireFrameOff() 
	{
		wireFrame = false;
	}
}
