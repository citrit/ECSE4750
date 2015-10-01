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
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Animate extends JoglCanvas implements Runnable, KeyListener
{
	JoglCanvas gl;
	static double spin;
	Thread r_thread;

	// Constructor for our simple class.
	public Animate ()
	{
		gl = this;
		use();
		init();
		// This is kind of funky, we are our own listener for key events
		addKeyListener( this );
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
		gl.loadIdentity();					// Reset the transformation matrix
		if (width <= height)
			gl.ortho (-50.0, 50.0, -50.0*(double)height/(double)width,
				     50.0*(double)height/(double)width, -1.0, 1.0);
		else
			gl.ortho (-50.0*(double)width/(double)height,
			         50.0*(double)width/(double)height, -50.0, 50.0, -1.0, 1.0);
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms
    }


	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
    public synchronized void display() {

		// clear the screen and draw a yellow square
		gl.clear(GL.COLOR_BUFFER_BIT);
		// First rectangle
		gl.pushMatrix();
		gl.translate(-0.5, 0.0, 0.0);
		gl.rotate(spin, 0.0, 0.0, 1.0);
		gl.rect(-0.5, -0.5, 0.5, 0.5);
		gl.popMatrix();
		// Second rectangle
		gl.pushMatrix();
		gl.translate(0.5, 0.0, 0.0);
		gl.rotate(-spin, 0.0, 0.0, 1.0);
		gl.rect(-0.5, -0.5, 0.5, 0.5);
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
		display();
	}

	// This is where things get started
	public static void main( String args[] )
    {
		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		Animate b = new Animate();

		// Make it visible and set size
		b.setVisible(true);
		b.setSize(300, 300);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		fm.add("Center", b);
		fm.pack();
		fm.show();

    }

	// Thread thingy to run at start thread
	public void run()
	{
		try {
			while(true) {
				gl.use();
				spinDisplay();
				Thread.sleep(10);
			}
		}
        catch (InterruptedException e){
            // the user sent an interupt,
            // So lets exit...
		}
	}

	// Animation Loop
	public void spinDisplay()
	{
		spin = spin + 2.0;
		if (spin > 360.0)
			spin = spin - 360.0;
		display();
	}

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {

		switch (e.getKeyChar()) {
		case 'h':
			gl.matrixMode (GL.MODELVIEW);   /* manipulate modelview matrix  */
			gl.rotate(15.0, 0.0,1.0,0.0);
			break;
		case 'j':
			gl.matrixMode (GL.MODELVIEW);   /* manipulate modelview matrix  */
			gl.rotate(15.0, 1.0,0.0,0.0);
			break;
		case 'k':
			gl.matrixMode (GL.MODELVIEW);   /* manipulate modelview matrix  */
			gl.rotate(-15.0, 1.0,0.0,0.0);
			break;
		case 'l':
			gl.matrixMode (GL.MODELVIEW);   /* manipulate modelview matrix  */
			gl.rotate(-15.0, 0.0,1.0,0.0);
			break;
		case '+':
			gl.matrixMode (GL.PROJECTION);  /* manipulate Projection matrix  */
			gl.translate(0.0, 0.0,0.5);
			e.consume();
			break;
		case '-':
			gl.matrixMode (GL.PROJECTION);  /* manipulate Projection matrix  */
			gl.translate(0.0, 0.0,-0.5);
			e.consume();
			break;
		case 's':
			if (r_thread == null) {
				r_thread = new Thread(this);
				r_thread.start();
			}
			else {
			    r_thread.start();
			}
			break;
		case 'p':
			if (r_thread != null) {
				r_thread.interrupt();
				//r_thread = null;
			}
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



