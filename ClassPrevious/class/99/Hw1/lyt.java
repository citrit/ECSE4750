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
 *
 *	Modified by Trung Ly for class purpose. (Homework 1)
 *
 */

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

////////
// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
////////

class Hw1 extends JoglCanvas implements KeyListener
{
	JoglCanvas gl;
	boolean initLess = true;
	Cube cube1;
	Cone cone1;
	Sphere sphere1;
	int curShape = 0;
	boolean wires = false;

	public Hw1 ()	// Constructor for our Hw1 class.
	{
		gl = this;
		addKeyListener( this );
		cube1 = new Cube();
		cone1 = new Cone();
		sphere1 = new Sphere();
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
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();		// Reset the transformation matrix
		gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0); // Set region of interest
		gl.matrixMode(GL.MODELVIEW);	// Reset to model transforms

		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
		gl.depthFunc(GL.LEQUAL);
		gl.texEnv(GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE);

		gl.enable(GL.LIGHTING);
		gl.enable(GL.LIGHT0);
		gl.light(GL.LIGHT0, GL.POSITION, lightPos);
		gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir);
		gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb);
		gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff);
		gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec);
		gl.enable(GL.DEPTH_TEST);
		gl.shadeModel(GL.SMOOTH);
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
		gl.enable(GL.DITHER);
    }


	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
	public void display() {

		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);

		cube1.draw( this );
		cone1.draw( this );
		sphere1.draw( this );

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
//		MenuBar mb = new MenuBar();
//		Menu m = new Menu("Change Perspective");

		// Make it visible and set size
		b.setVisible(true);
		b.setSize(300, 300);
		System.out.println("");
		System.out.println("Press 1 to change to the Cube's perspective");
		System.out.println("Press 2 to change to the Cone's perspective");
		System.out.println("Press 3 to change to the Sphere's perspective");
		System.out.println("Press 0 to change to the overall perspective");
		System.out.println("Press j and k to rotate in the x direction");
		System.out.println("Press h and l to rotate in the y direction");
		System.out.println("Press + and - to translate in the z direction");
		System.out.println("Press w to toggle Wireframe mode");

		// Add the canvas to the frame and make it show
  		fm.add("Center", b);

		// Create a menu bar
//		m.add( new MenuItem("Global") );
//		m.add( new MenuItem("Sphere") );
//		m.add( new MenuItem("Cube") );
//		m.add( new MenuItem("Cone") );
//		mb.add(m);
//		fm.setMenuBar(mb);

		fm.pack();
		fm.show();
    }

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {
    
		switch (e.getKeyChar()) {
		case '0':
			curShape=0;
			e.consume();
			break;
		case '1':
			curShape=1;
			e.consume();
			break;
		case '2':
			curShape=2;
			e.consume();
			break;
		case '3':
			curShape=3;
			e.consume();
			break;
		case 'h':
			Rotate(5.0F, 0.0F,1.0F,0.0F);
			e.consume();
			break;
		case 'j':
			Rotate(5.0F, 1.0F,0.0F,0.0F);
			e.consume();
			break;
		case 'k':
			Rotate(-5.0F, 1.0F,0.0F,0.0f);
			e.consume();
			break;
		case 'l':
			Rotate(-5.0F, 0.0F,1.0F,0.0F);
			e.consume();
			break;
		case '+':
			gl.translate(0.0F, 0.0F,0.1F);
			e.consume();
			break;
		case '-':
			gl.translate(0.0F, 0.0F,-0.1F);
			e.consume();
			break;
		case 'w':
			if(wires==false) {
				gl.polygonMode(GL.FRONT_AND_BACK, GL.LINE);
				wires=true;
			}
			else {
				gl.polygonMode(GL.FRONT_AND_BACK, GL.FILL);
				wires=false;
			}
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
	public void keyPressed(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }

	/* Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(float[] p) { gl.vertex(p[0], p[1], p[2]); }

	public void Rotate(float angle, float x, float y, float z)
	{	//convenience routine
		switch(curShape)
		{
		case 0:
			gl.rotate(angle, x, y, z);
			break;
		case 1:
			cube1.rotate(angle, x, y, z);
			break;
		case 2:
			cone1.rotate(angle, x, y, z);
			break;
		case 3:
			sphere1.rotate(angle, x, y, z);
			break;
		default:
			break;
		}
	}
}

////////
// Class: Shape
// Description: This is the parent class for the other shapes
////////

class Shape
{
	Hw1 gl;
	Quadric glu = new Quadric();
	protected float Trans[] = new float[3];
	protected float Rot[] = new float[4];
	protected float Amb[] = {0.0F, 0.0F, 0.0F, 1.0F};
	protected float Spec[] = {0.0F, 0.0F, 0.0F, 1.0F};
	protected float Diff[] = {0.8F, 0.8F, 0.8F, 1.0F};

	public Shape() {}

	public void rotate(float a, float x, float y, float z)
	{
		Rot[0] += a;
		Rot[1] = x;
		Rot[2] = y;
		Rot[3] = z;
	}
}

////////
// Class: Cube
// Description: This is the class that holds all info specific to Cubes
////////

class Cube extends Shape
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

	public Cube()
	{
		Trans[0] = 0.0F; Trans[1] = 0.0F; Trans[2] = 0.0F;

		Amb[0]  = 1.0F; Amb[1]  = 0.0F; Amb[2]  = 0.0F; Amb[3]  = 1.0F;
		Spec[0] = 1.0F; Spec[1] = 1.0F; Spec[2] = 1.0F; Spec[3] = 1.0F;
		Diff[0] = 0.0F; Diff[1] = 0.0F; Diff[2] = 0.0F; Diff[3] = 1.0F;
	}

	public void draw( Hw1 gl )
	{
		gl.pushMatrix();
		gl.translate(Trans[0], Trans[1], Trans[2]);
		gl.rotate(Rot[0], Rot[1], Rot[2], Rot[3]);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, this.Amb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, Diff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, Spec);
		for (int i=0;i<6;i++ )	// for all faces
		{
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++)// for each vertex
			{
				gl.vertexv(boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
		gl.popMatrix();
	}
}

////////
// Class: Cone
// Description: This is the class that holds all info specific to Cones
////////

class Cone extends Shape
{
	public Cone()
	{
		Trans[0] = 2.0F; Trans[1] = 0.0F; Trans[2] = 0.0F;

		Amb[0]  = 0.0F; Amb[1]  = 0.0F; Amb[2]  = 1.0F; Amb[3]  = 1.0F;
		Spec[0] = 0.0F; Spec[1] = 0.0F; Spec[2] = 0.0F; Spec[3] = 1.0F;
		Diff[0] = 0.0F; Diff[1] = 0.0F; Diff[2] = 0.0F; Diff[3] = 1.0F;
	}

	public void draw( Hw1 gl )
	{
		gl.pushMatrix();
		gl.translate(Trans[0], Trans[1], Trans[2]);
		gl.rotate(Rot[0], Rot[1], Rot[2], Rot[3]);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, Amb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, Diff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, Spec);
		glu.cylinder(0.0, 0.5, 1, 16, 16);
		gl.popMatrix();
	}
}


////////
// Class: Sphere
// Description: This is the class that holds all info specific to Spheres
////////

class Sphere extends Shape
{
	public Sphere() {
		Trans[0] = -2.0F; Trans[1] = 0.0F; Trans[2] = 0.0F;

		Amb[0]  = 1.0F; Amb[1]  = 1.0F; Amb[2]  = 0.0F; Amb[3]  = 0.1F;
		Spec[0] = 0.0F; Spec[1] = 0.0F; Spec[2] = 0.0F; Spec[3] = 0.1F;
		Diff[0] = 0.8F; Diff[1] = 0.8F; Diff[2] = 0.8F; Diff[3] = 0.1F;
	}

	public void draw( Hw1 gl )
	{
		gl.pushMatrix();
		gl.translate(Trans[0], Trans[1], Trans[2]);
		gl.rotate(Rot[0], Rot[1], Rot[2], Rot[3]);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, Amb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, Diff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, Spec);
		glu.sphere(0.5, 16, 16);
		gl.popMatrix();
	}
}
