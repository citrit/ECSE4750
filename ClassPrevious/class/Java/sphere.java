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
class sphere extends JoglCanvas implements KeyListener
{
    JoglCanvas gl;
    Quadric  glu; // From the glu package
    boolean  initLess;

    // Constructor for our simple class.
    public sphere  ()
    {
	gl = this;
	glu = new Quadric();
	//glu.quadricNormals(GLU.SMOOTH);
	// This is kind of funky, we are our own listener for key events
	addKeyListener( this );
	initLess = true;
    }
    
    // This gets called by the constructor, hopefully only
    // once but it can be called at any time. Called prior to any
    // Window creation so it can only set variables, not draw things
    public void init()
    {
	use();
    }
    

    // Sort of a legacy type function call, actually inside Java
    // This stuff should be in the paint call
    public void display() {

	gl.use();
	if (initLess) {
	    init();
	    initLess = false;
	}
	// clear the screen and draw a yellow square
	float mat_spec[] = { 1.0F, 1.0F, 1.0F, 1.0F};
	float mat_shin[] = { 50.0F };
	float light_pos[] = { 1.0F, 1.0F, 1.0F, 0.0F };

	gl.enable(GL.LIGHTING);
	gl.enable(GL.DEPTH_TEST);
	gl.depthFunc(GL.LEQUAL);
	gl.material(GL.FRONT, GL.SPECULAR, mat_spec);
	gl.material(GL.FRONT, GL.SHININESS, mat_shin);
	gl.light(GL.LIGHT0, GL.POSITION, light_pos);

	//gl.enable(GL.LIGHT0);
	gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
	gl.clear(GL.COLOR_BUFFER_BIT);
	gl.color(1.0F,0.0F,0.0F);
	glu.sphere(0.5, 16, 16);
	
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
	sphere b = new sphere();
	
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
	    break;
	case 'j':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(15.0, 1.0,0.0,0.0);
	    break;
	case 'k':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(-15.0, 1.0,0.0,0.0);
	    break;
	case 'l':
	    gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
	    gl.rotate(-15.0, 0.0,1.0,0.0);
	    break;
	case '-':
	    gl.matrixMode(GL.PROJECTION);
	    gl.translate(0, 0, -1);
	    break;
	case '+':
	    gl.matrixMode(GL.PROJECTION);
	    gl.translate(0, 0, 1);
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
    
}

