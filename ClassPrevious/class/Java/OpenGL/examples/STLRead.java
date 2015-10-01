/*
 *	Simple sample of Primitives in Java and OpenGL
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
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class STLRead extends JoglCanvas implements KeyListener
{
    JoglCanvas gl;
    boolean initless;
    Vector vPoints;
    Vector vTriangles;
    float[] BBox;

    // Constructor for our simple class.
    public STLRead ()
    {
		gl = this;
		initless = true;
		// This is kind of funky, we are our own listener for key events
		addKeyListener( this );
		vPoints = new Vector();
		vTriangles = new Vector();
		BBox = new float[6];
    }
    
    public void ReadFile(String fileName)
    {
    	System.out.println("Reading file:" + fileName);
    	float[] vals;
    	int[] tri;
    	int j;
    	objectReader obr = new objectReader(fileName);
		String inStr;
		String vrtxStr = new String("VERTEX");
		String endlStr = new String("ENDLOOP");

		do {
		    inStr = obr.getString();
		    if (inStr.compareTo(vrtxStr) == 0) {
		    	vals = new float[3];
				for (j=0;j<3;j++) // read in three vals
					vals[j] = obr.getFloat();
				BBox[0] = (BBox[0]<vals[0]?BBox[0]:vals[0]);
				BBox[1] = (BBox[1]<vals[1]?BBox[1]:vals[1]);
				BBox[2] = (BBox[2]<vals[2]?BBox[2]:vals[2]);
				BBox[3] = (BBox[3]>vals[0]?BBox[3]:vals[0]);
				BBox[4] = (BBox[4]>vals[1]?BBox[4]:vals[1]);
				BBox[5] = (BBox[5]>vals[2]?BBox[5]:vals[2]);
				vPoints.addElement(vals);
		    }
		    if (inStr.compareTo(endlStr) == 0) {
		    	tri = new int[3];
		    	tri[0] = vPoints.size()-3;
		    	tri[1] = vPoints.size()-2;
		    	tri[2] = vPoints.size()-1;
		    	vTriangles.addElement(tri);
		    }
		} while (! obr.eof());

    }
    
    // This gets called by the constructor, hopefully only
    // once but it can be called at any time. Called prior to any
    // Window creation so it can only set variables, not draw things
    public void init()
    {
		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
	
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();		// Reset the transformation matrix
		gl.perspective(35.0, width/height, 10.0, 1000.0);
		gl.lookAt(0.0, 0, 200, 0, 0, 0, 0.0, 1.0, 0.0); 
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms
		gl.loadIdentity();		// Reset the transformation matrix
		initless = false;
    }


    // Sort of a legacy type function call, actually inside Java
    // This stuff should be in the paint call
    public void display() 
    {
    	int i,j;
    	
		if (initless) 
		    init();
		// clear the screen and draw a yellow square
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT);
		gl.color(1.0, 1.0, 0.0);
		double center[] = new double[3];
		center[0] = (BBox[0] + BBox[3]) * 0.5;
		center[1] = (BBox[1] + BBox[4]) * 0.5;
		center[2] = (BBox[2] + BBox[5]) * 0.5;
		//System.out.println("Trans: " + -center[0] + ", " + -center[1] + ", "+ -center[2]);
		gl.pushMatrix();
		gl.translate(-center[0],-center[1], -center[2]);
		gl.begin(GL.TRIANGLES);
		for (i=0;i<vTriangles.size();i++) {
			int[] tri = (int[])vTriangles.elementAt(i);
			//System.out.println("Tri: " + tri[0] + ", " + tri[1] + ", "+ tri[2]);
			for (j=0;j<3;j++) {
				float[] pts = (float[])vPoints.elementAt(tri[j]);
				gl.vertex(pts[0],pts[1],pts[2]); 
				//System.out.println("vertex: " + pts[0] + ", " + pts[1] + ", "+ pts[2]);
			}
		}
		gl.popMatrix();
		gl.end();
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
		display();
    }
    
    // This is where things get started
    public static void main( String args[] ) 
    {
		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		STLRead b = new STLRead();
		b.ReadFile("small.stl");
		
		// Make it visible and set size
		b.setVisible(true);
		b.setSize(300, 300);
		System.out.println("Here we go");
		
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
    
}

