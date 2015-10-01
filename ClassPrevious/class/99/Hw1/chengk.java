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
/*Homework 1 modified from Simple3.java by Kent Cheng*/


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
	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;
        double cube[] = {0.0, 0.0, 0.0, 0.0, 0.0};         //cube's coordinates
        double cone[] = {0.0, 0.0, 0.0, 0.0, 0.0};         //cone's coordinates
        double sphere[] = {0.0, 0.0, 0.0, 0.0, 0.0};       //sphere's coordinates
        boolean cube_rot = false;               //use to determine which object is moving
        boolean cone_rot = false;
        boolean sphere_rot = false;
        boolean goin = false;
        double rot[] = {0.0, 0.0, 0.0, 0.0};   //rotation degrees and coordinates
        double scalef[] = {1.0, 1.0, 1.0};     //scaling factor
        double cube_tr[] = {0, 0, 0};          //translate coordinates for moving
        double cone_tr[] = {2, 0, 0};
        double sphere_tr[] = {-2, 0, 2};

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
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();					// Reset the transformation matrix
		gl.ortho(-4.0, 4.0, -4.0, 4.0, -4.0, 4.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms

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

		
                float boxAmb[] = {1.0F, 0.0F, 0.0F, 1.0F};
		float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
                float coneAmb[] = {0.0F, 0.0F, 1.0F, 0.0F};
                float coneDiff[] = {0.2F, 0.0F, 0.2F, 0.0F};
                float boxDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
                float boxSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};
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
                gl.translate(cube_tr[0], cube_tr[1], cube_tr[2]);  //move object
                if (cube_rot && goin){
       	            if(rot[1]==1.0 && cube[2]!=1.0)
		    {
				cube[2]=1.0;
				cube[3]=0.0;
				cube[0]=cube[0]+rot[0];
		    }
		    if(rot[2]==1.0 && cube[3]!=1.0)
		    {
				cube[2]=0.0;
				cube[3]=1.0;
				cube[1]=cube[1]+rot[0];
		    }
			
		    if(rot[1]==1.0 && cube[2]==1.0)
		      cube[0]=cube[0]+rot[0];
		    if(rot[2]==1.0 && cube[3]==1.0)
		       cube[1]=cube[1]+rot[0];
                    goin = false;
                }
                gl.scale(scalef[0], scalef[0], scalef[0]);   //scale object
                gl.rotate(cube[0], 1.0, 0.0, 0.0); 			                 
                gl.rotate(cube[1], 0.0, 1.0, 0.0);	
                for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vertexv(boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
                gl.popMatrix();


		// add a cone.
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, coneDiff);
                gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, coneAmb);
                gl.pushMatrix();
                gl.translate(cone_tr[0], cone_tr[1], cone_tr[2]);   //move object
                if (cone_rot && goin){
       	            if(rot[1]==1.0 && cone[2]!=1.0)
		    {
				cone[2]=1.0;
				cone[3]=0.0;
				cone[0]=cone[0]+rot[0];
		    }
		    if(rot[2]==1.0 && cone[3]!=1.0)
		    {
				cone[2]=0.0;
				cone[3]=1.0;
				cone[1]=cone[1]+rot[0];
		    }
			
		    if(rot[1]==1.0 && cone[2]==1.0)
		      cone[0]=cone[0]+rot[0];
		    if(rot[2]==1.0 && cone[3]==1.0)
		      cone[1]=cone[1]+rot[0];
                                     
                    goin = false;
                }
                gl.scale(scalef[1], scalef[1], scalef[1]);  //scale object
                gl.rotate(cone[0], 1.0, 0.0, 0.0); 			                 
                gl.rotate(cone[1], 0.0, 1.0, 0.0);	
                glu.cylinder(0.0, 0.5, 1, 16, 16);
		gl.popMatrix();


                //add a sphere.
                gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
                gl.pushMatrix();
                gl.translate(sphere_tr[0], sphere_tr[1], sphere_tr[2]);  //move object
                if (sphere_rot && goin){
       	            if(rot[1]==1.0 && sphere[2]!=1.0)
		    {
				sphere[2]=1.0;
				sphere[3]=0.0;
				sphere[0]=sphere[0]+rot[0];
		    }
		    if(rot[2]==1.0 && sphere[3]!=1.0)
		    {
				sphere[2]=0.0;
				sphere[3]=1.0;
				sphere[1]=sphere[1]+rot[0];
		    }
			
		    if(rot[1]==1.0 && sphere[2]==1.0)
		      sphere[0]=sphere[0]+rot[0];
		    if(rot[2]==1.0 && sphere[3]==1.0)
		      sphere [1]=sphere[1]+rot[0];
                                      
                    goin = false;
                }
                gl.scale(scalef[2], scalef[2], scalef[2]);   //scale object
                gl.rotate(sphere[0], 1.0, 0.0, 0.0); 			                 
                gl.rotate(sphere[1], 0.0, 1.0, 0.0);	
                glu.sphere(0.5, 16, 16);
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
		b.setSize(300, 300);
		System.out.println("The commands to run this program:");
                System.out.println("To toggle between objects to rotate: 's' for sphere, 'c' for cube, 'o' for cone");
                System.out.println("To rotate the objects, use keys: 'h', 'j', 'k', 'l'");
                System.out.println("To scale the size of an object: '>' to increase, '<' to decrease");
                System.out.println("To move an object: 'X' to go right, 'x' to go left");
                System.out.println("                   'Y' to go up,   'y' to go down");
                System.out.println("                   'Z' to go in,   'z' to come out");

		// Add the canvas to the frame and make it show
  		fm.add("Center", b);
		fm.pack();
		fm.show();

    }

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {
    
		switch (e.getKeyChar()) {
                case '>': 
                        if (cube_rot)
                            scalef[0]++;
                        if (cone_rot)
                            scalef[1]++;
                        if (sphere_rot)
                            scalef[2]++;
                        break;
                case '<': 
                        if (cube_rot)
                            scalef[0]--;
                        if (cone_rot)
                            scalef[1]--;
                        if (sphere_rot)
                            scalef[2]--;
                        break;
                case 'c': 
                        cube_rot = true;
                        cone_rot = false;
                        sphere_rot = false;
                        break;
                case 'o':
                        cube_rot = false;
                        cone_rot = true;
                        sphere_rot = false;
                        break;
                case 's':
                        cube_rot = false;
                        cone_rot = false;
                        sphere_rot = true;
                        break;
		case 'h':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(15.0, 0.0,1.0,0.0);
                        rot[0] = 15.0;
                        rot[1] = 0.0;
                        rot[2] = 1.0;
                        rot[3] = 0.0;
                        goin = true;
			e.consume();
			break;
		case 'j':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(15.0, 1.0,0.0,0.0);
                        rot[0] = 15.0;
                        rot[1] = 1.0;
                        rot[2] = 0.0;
                        rot[3] = 0.0;
                        goin = true;
			e.consume();
			break;
		case 'k':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(-15.0, 1.0,0.0,0.0);
                        rot[0] = -15.0;
                        rot[1] = 1.0;
                        rot[2] = 0.0;
                        rot[3] = 0.0;
                        goin = true;
			e.consume();
			break;
		case 'l':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(-15.0, 0.0,1.0,0.0);
                        rot[0] = -15.0;
                        rot[1] = 0.0;
                        rot[2] = 1.0;
                        rot[3] = 0.0; 
                        goin = true;
			e.consume();
			break;
                case 'X':
                        if (cube_rot)
                            cube_tr[0]++;
                        if (cone_rot)
                            cone_tr[0]++;
                        if (sphere_rot)
                            sphere_tr[0]++;
                        break;
                case 'x':
                        if (cube_rot)
                            cube_tr[0]--;
                        if (cone_rot)
                            cone_tr[0]--;
                        if (sphere_rot)
                            sphere_tr[0]--;
                        break;
                case 'Y':
                        if (cube_rot)
                            cube_tr[1]++;
                        if (cone_rot)
                            cone_tr[1]++;
                        if (sphere_rot)
                            sphere_tr[1]++;
                        break;
                case 'y':
                        if (cube_rot)
                            cube_tr[1]--;
                        if (cone_rot)
                            cone_tr[1]--;
                        if (sphere_rot)
                            sphere_tr[1]--;
                        break;
                case 'Z':
                        if (cube_rot)
                            cube_tr[2]++;
                        if (cone_rot)
                            cone_tr[2]++;
                        if (sphere_rot)
                            sphere_tr[2]++;
                        break;
                case 'z':
                        if (cube_rot)
                            cube_tr[2]--;
                        if (cone_rot)
                            cone_tr[2]--;
                        if (sphere_rot)
                            sphere_tr[2]--;
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
