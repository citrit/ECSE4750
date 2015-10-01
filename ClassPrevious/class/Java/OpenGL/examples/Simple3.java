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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Simple extends JoglCanvas implements KeyListener, AdjustmentListener
{
    JoglCanvas gl;
    Quadric  glu; // From the glu package
    boolean initLess = true;
    float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};
    float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
    Scrollbar ranger[];

    // Constructor for our simple class.
    public Simple ()
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
		gl.ortho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0); // Set region of interest
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
	
		float boxDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
		float boxSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};
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
		for (int i=0;i<6;i++ ) { // For all faces
		    gl.begin(GL.POLYGON);
		    for (int j=0;j<4;j++) { // for each vertex
					//Added this as a convenience
			vertexv(boxCors[boxIndex[(i*4)+j]]);
		    }
		    gl.end();
		}
		// add a sphere.
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.pushMatrix();
		gl.translate(1, 0, 0);
		glu.cylinder(0.0, 0.5, 1, 16, 16);
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
    
    // Create a UI
    public void createUI(Frame fm)
    {

		// Make it visible and set size
		setVisible(true);
		setSize(300, 300);
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
		Simple b = new Simple();
		b.createUI(fm);
		System.out.println("Here we go");
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
    
    // Handle Scroll bar
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
		if (ranger[0] == e.getSource())
			boxAmb[0] = ((float)e.getValue())/100.0F;
		else if(ranger[1] == e.getSource())
			boxAmb[1] = ((float)e.getValue())/100.0F;
		else if(ranger[2] == e.getSource())
			boxAmb[2] = ((float)e.getValue())/100.0F;

		display();
    }

    /** Convenience function to handle vertex*v call in OpenGL */
    public void vertexv(float[] p) 
    {
		gl.vertex(p[0], p[1], p[2]);
    }
}

