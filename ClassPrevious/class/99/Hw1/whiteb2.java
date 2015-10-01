/*  Home Work 1
 *	Code modified from Simple3.java
 *	by Bryan Whitehead
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
class hw1 extends JoglCanvas implements KeyListener
{
	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;
	float BoxXYZ[]={0.0F,0.0F,0.0F}; //State Variables
	float SphXYZ[]={-2.0F,0.0F,0.0F};
	float ConeXYZ[]={2.0F,0.0F,0.0F};
	float BoxAng[]={0.0F,0.0F,0.0F};
	float SphAng[]={0.0F,0.0F,0.0F};
	float ConeAng[]={0.0F,0.0F,0.0F};
	float BoxSize=1.0F;
	float SphSize=1.0F;
	float ConeSize=1.0F;
	static int focus=0;

	// Constructor for our simple class.
	public hw1 ()
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
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();// Reset the transformation matrix
		gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );// Reset to model transforms

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
    }


	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
    public void display() {

		float vert[] = {0.0F,0.0F,0.0F};
		float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};
		float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
		float coneAmb[] = {0.0F, 0.0F, 1.0F, 1.0F};
		float boxDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
		float boxSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};
		float coneSpec[]= {0.0F, 0.0F, 0.0F, 1.0F};
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

		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);
		
		// Lets draw a box
		gl.pushMatrix();
		gl.translate(BoxXYZ[0],BoxXYZ[1],BoxXYZ[2]);
		gl.rotate(BoxAng[0],1.0,0.0,0.0);
		gl.rotate(BoxAng[1],0.0,1.0,0.0);
		gl.rotate(BoxAng[2],0.0,0.0,1.0);
		for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vert[0]=BoxSize*boxCors[boxIndex[(i*4)+j]][0];
				vert[1]=BoxSize*boxCors[boxIndex[(i*4)+j]][1];
				vert[2]=BoxSize*boxCors[boxIndex[(i*4)+j]][2];
				vertexv(vert);
			}
			gl.end();
		}
		gl.popMatrix();

		// add a sphere.
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.pushMatrix();
		gl.translate(SphXYZ[0], SphXYZ[1], SphXYZ[2]);
		gl.rotate(SphAng[0],1.0,0.0,0.0);
		gl.rotate(SphAng[1],0.0,1.0,0.0);
		gl.rotate(SphAng[2],0.0,0.0,1.0);
		glu.sphere(SphSize*0.5,16,16);
		gl.popMatrix();

		// add cone.
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, coneAmb);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, coneSpec);
		gl.pushMatrix();
		gl.translate(ConeXYZ[0], ConeXYZ[1], ConeXYZ[2]);
		gl.rotate(ConeAng[0],1.0,0.0,0.0);
		gl.rotate(ConeAng[1],0.0,1.0,0.0);
		gl.rotate(ConeAng[2],0.0,0.0,1.0);
		glu.cylinder(0.0,ConeSize*0.5,ConeSize*1,16,16);
		gl.popMatrix();

		gl.flush(); // Make sure all commands have completed.
		gl.swap(); // Swap the render buffer with the screen buffer
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

	public static void PrintHelp() {
		System.out.println();
		System.out.println("    !!!!Help Screen!!!!");
		System.out.println();
		System.out.println("       Keys : Function");
		System.out.println("        ?,/ : Show Help Screen");
		System.out.println("    1,2,3,4 : Select Object to Control");
		System.out.println("x,X,y,Y,z,Z : Translate Selected Object");
		System.out.println("i,I,j,J,k,K : Rotate Selected Object");
		System.out.println("        s,S : Resize Selected Object");
		System.out.println("        Esc : Exit");
		System.out.println();
		switch(focus){
		case 0:System.out.println("Selected Object : Camera");
			break;
		case 1:System.out.println("Selected Object : Sphere");
			break;
		case 2:System.out.println("Selected Object : Box");
			break;
		case 3:System.out.println("Selected Object : Cone");
			break;
		}
	}

	// This is where things get started
	public static void main( String args[] ) 
    {
		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		hw1 b = new hw1();

		// Make it visible and set size
		b.setVisible(true);
		b.setSize(300, 300);
		PrintHelp();
		// Add the canvas to the frame and make it show
  		fm.add("Center", b);
		fm.pack();
		fm.show();

    }

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {
    
		float x=0.0F,y=0.0F,z=0.0F,ax=0.0F,ay=0.0F,az=0.0F,s=1.0F;
		float step=0.5F,astep=15.0F;
		char key;

		switch (key=e.getKeyChar()) {
		case '1':focus=1;
			System.out.println("Control set to Sphere");			
			break;
		case '2':focus=2;
			System.out.println("Control set to Box");
			break;
		case '3':focus=3;
			System.out.println("Control set to Cone");
			break;
		case '4':focus=0;
			System.out.println("Control set to Camera");
			break;
		case '?':
		case '/':PrintHelp();break;
		case 'x':
		case 'X':
		case 'y':
		case 'Y':
		case 'z':
		case 'Z':
		case 'i':
		case 'I':
		case 'j':
		case 'J':
		case 'k':
		case 'K':
		case 's':
		case 'S':
			switch (key) {
			case 'x':x=-step;break;
			case 'X':x=step;break;
			case 'y':y=-step;break;
			case 'Y':y=step;break;
			case 'z':z=-step;break;
			case 'Z':z=step;break;
			case 'i':ax=-astep;break;
			case 'I':ax=astep;break;
			case 'j':ay=-astep;break;
			case 'J':ay=astep;break;
			case 'k':az=-astep;break;
			case 'K':az=astep;break;
			case 's':s=10.0F/11.0F;break;
			case 'S':s=1.1F;break;
			};
			switch (focus) {
			case 0:gl.matrixMode(GL.MODELVIEW);
				gl.translate(x,y,z);
				gl.rotate(ax,1.0,0.0,0.0);
				gl.rotate(ay,0.0,1.0,0.0);
				gl.rotate(az,0.0,0.0,1.0);
				break;
			case 1:SphXYZ[0]+=x;SphXYZ[1]+=y;SphXYZ[2]+=z;
				SphAng[0]+=ax;SphAng[1]+=ay;SphAng[2]+=az;
				SphSize*=s;
				break;
			case 2:BoxXYZ[0]+=x;BoxXYZ[1]+=y;BoxXYZ[2]+=z;
				BoxAng[0]+=ax;BoxAng[1]+=ay;BoxAng[2]+=az;
				BoxSize*=s;
				break;
			case 3:ConeXYZ[0]+=x;ConeXYZ[1]+=y;ConeXYZ[2]+=z;
				ConeAng[0]+=ax;ConeAng[1]+=ay;ConeAng[2]+=az;
				ConeSize*=s;
				break;
			};
			break;
		case 27:           /* Esc will quit */
			System.exit(1);
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

	/** Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(float[] p) 
	{
		gl.vertex(p[0], p[1], p[2]);
	}
}

