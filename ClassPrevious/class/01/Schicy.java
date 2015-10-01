/*
 *	Hw1 sample of Java and OpenGL
 *
 *
 *  Hw1 example of programming using the OpenGL graphics library
 *  Taken from the "OpenGL Programming Guide"
 *
 *	Hw1 for the course:
 *		Advanced Computer Graphics and Data Visualization  35-6961
 *		ECSE, Rensselaer Polytechnic Institute
 *
 *	19 Jan, 2000
 */

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Hw1 extends JoglCanvas implements KeyListener, AdjustmentListener
{
    JoglCanvas gl;
    Box box; //From box class
    Quadric  glu; // From the glu package
    boolean initLess = true;
    //define some variables  
    float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};
    float cylAmb[] = {0.0F, 0.0F, 1.0F, 1.0F};
    float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
    double cylRot = 90.0;
    double sphRot = 0.0;
    double boxRot = 0.0;
    double cx=1.0;
    double cy=0.0;
    double cz=0.0;
    double sx=1.0;
    double sy=0.0;
    double sz=0.0;
    double bx=1.0;
    double by=0.0;
    double bz=0.0;
       
    Scrollbar ranger[];

    // Constructor for our Hw1 class.
    public Hw1 ()
    {
		gl = this;
		glu = new Quadric();
		ranger = new Scrollbar[3];

    }
    
    // This gets called by the constructor, hopefully only
    // once but it can be called at any time. Called prior to any
    // Window creation so it can only set variables, not draw things
    
    
    public void init()
    {
		float lightPos[] = {0.0F, 0.0F, 0.0F, 1.0F};//light position
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};//light direction
		float lightAmb[] = {0.9F, 0.9F, 0.9F, 1.0F};//light ambient
		float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};//light diffuse
		float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};//light specular
		
		initLess = false;
		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
		box = new Box();
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();					// Reset the transformation matrix
		gl.ortho(-4.0, 4.0, -4.0, 4.0, -4.0, 4.0); // Set region of interest
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
    }


    // Sort of a legacy type function call, actually inside Java
    // This stuff should be in the paint call
    public void display() {
		float boxAmb[] = {0.9F, 0.0F, 0.0F, 1.0F};
		float boxDiff[] = {0.2F, 0.2F, 0.2F, 1.0F};
		float boxSpec[] = {0.9F, 0.9F, 0.9F, 1.0F};
		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);
		
		//add a box
		gl.pushMatrix();
		gl.rotate(boxRot, bx,by,bz);
		box.draw(this);
		gl.popMatrix();
		
		// add a cylinder.
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, cylAmb);
		gl.pushMatrix();
		gl.rotate(cylRot, cx,cy,cz);
		gl.translate(2, 0, -0.5);
		glu.cylinder(0.0, 0.5, 1, 16, 16);
		gl.popMatrix();
		
		
		//add a sphere 
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.pushMatrix();
		gl.rotate(sphRot, sx,sy,sz);
		gl.translate(-2, 0, 0);
		glu.sphere(0.5,16, 16);
		gl.popMatrix();
	
		gl.flush(); // Make sure all commands have completed.
		gl.swap();	 // Swap the render buffer with the screen buffer
    }
    
    // Hw1 redirector to our display function
    public void paint(Graphics g)
    {
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		super.paint(g);
		//System.out.println("Call to paint");
		if (initLess) init();
		display();
    }
    
    // Create a UI
    public void createUI(Frame fm)
    {

		// Make it visible and set size
		setVisible(true);
		setSize(400, 400);
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		
		
		fm.setLayout(gridbag);
		int i;
		for (i=0;i<3;i++) {
		    ranger[i] = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 100);
		    ranger[i].addAdjustmentListener(this);
		    ranger[i].addKeyListener(this);
			ranger[i].setValue(0);
		    gridbag.setConstraints(ranger[i], c);
		    fm.add(ranger[i]);
		}
		ranger[0].setValue(70);
			
		// Add the canvas to the frame and make it show
		fm.add(this);
		fm.pack();
    }
    
    // This is where things get started

    public static void main( String args[] ) 
    {
		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		Hw1 b = new Hw1();
		b.createUI(fm);
		

		System.out.println("\nHere we go...|:)"+
			"\nHomework for week 1" +
			"\nCopyright (c) 2000 Chengyu Shi;-)."+
			"\nModify Sample3 by Tom C" +
			"\nUse the h,j,k, and l keys to rotate the three objects" +
			"\n + and - to zoom."+
			"\n q,w,e for rotating box along x,y,z axis"+
			"\n a,s,d for rotating cylinder along x,y,z axis"+
			"\n z,x,c for rotating sphere along x,y,z axis"+
			"\nScroll bar for changing the color");
		fm.show();
	
    }
    
    // Handle the keystrokes
    public void keyTyped(KeyEvent e) {
	
	switch (e.getKeyChar()) {
	case 'h':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(15.0, 0.0,1.0,0.0);
	    boxAmb[0] = (float) 0.7;
	    e.consume();
	    break;
	case 'j':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(15.0, 1.0,0.0,0.0);
	    boxAmb[0] = (float) 0.7;
	    e.consume();
	    break;
	case 'k':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(-15.0, 1.0,0.0,0.0);
	    boxAmb[0] = (float) 0.7;
	    e.consume();
	    break;
	case 'l':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(-15.0, 0.0,1.0,0.0);
	    boxAmb[0] = (float) 0.7;
	    e.consume();
	    break;
	case '+':
	    gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
	    gl.translate(0.0, 0.0,0.2);
	    boxAmb[0] = (float) 0.7;
	    e.consume();
	    break;
	case '-':
	    gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
	    gl.translate(0.0, 0.0,-0.2);
	    boxAmb[0] = (float) 0.7;
	    e.consume();
	    break;
	case 'z':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    cylRot=cylRot+15;
	    cx=1.0;
	    cy=0.0;
	    cz=0.0;
	    e.consume();
		break;
	case 'x':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    cylRot=cylRot+15;
	    cx=0.0;
	    cy=1.0;
	    cz=0.0;
	    e.consume();
		break;
	case 'c':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    cylRot=cylRot+15;
	    cx=0.0;
	    cy=0.0;
	    cz=1.0;
	    e.consume();
		break;
	case 'a':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    sphRot=sphRot+15;
	    sx=1.0;
	    sy=0.0;
	    sz=0.0;
	    e.consume();
		break;
	case 's':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    sphRot=sphRot+15;
	    sx=0.0;
	    sy=1.0;
	    sz=0.0;
	    e.consume();
		break;
	case 'd':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    sphRot=sphRot+15;
	    sx=0.0;
	    sy=0.0;
	    sz=1.0;
	    e.consume();
		break;

	case 'q':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    boxRot=boxRot+15;
	    bx=1.0;
	    by=0.0;
	    bz=0.0;
	    e.consume();
		break;
	case 'w':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    boxRot=boxRot+15;
	    bx=0.0;
	    by=1.0;
	    bz=0.0;
	    e.consume();
		break;
	case 'e':
		gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    boxRot=boxRot+15;
	    bx=0.0;
	    by=0.0;
	    bz=1.0;
	    e.consume();
		break;
	case 27:           /* Esc will quit */
	    System.exit(1);
	    break;
	default:
	    break;
	}
	if (boxRot > 360.0) 
			boxRot = boxRot - 360.0; 
	if (cylRot > 360.0) 
			cylRot = cylRot - 360.0; 
	if (sphRot > 360.0) 
			sphRot = sphRot - 360.0; 
	display();
    }
    public void keyPressed(KeyEvent e) {
	
    }
    public void keyReleased(KeyEvent e) {
	
    }
    
    // Handle Scroll bar
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
		if (ranger[0] == e.getSource())
			{
			boxAmb[0] = ((float)e.getValue())/100.0F;
			//added by shicy
			cylAmb[0] = ((float)e.getValue())/100.0F;
			sphAmb[0] = ((float)e.getValue())/100.0F;
			//added by shicy end
			}
	    else if  (ranger[1] == e.getSource())
			{
			boxAmb[1] = ((float)e.getValue())/100.0F;
			//added by shicy
			cylAmb[1] = ((float)e.getValue())/100.0F;
			sphAmb[1] = ((float)e.getValue())/100.0F;
			//added by shicy end
			}
	   else if (ranger[2] == e.getSource())
			{
			boxAmb[2] = ((float)e.getValue())/100.0F;
			//added by shicy
			cylAmb[2] = ((float)e.getValue())/100.0F;
			sphAmb[2] = ((float)e.getValue())/100.0F;
			//added by shicy end
			}	
		display();
    }
}
    /** Convenience function to handle vertex*v call in OpenGL */
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