/*
 *	Simple sample of Java and OpenGL
 *
 *
 */

import jogl.*;
import java.awt.*;
import java.awt.event.*;

class Simple extends JoglCanvas
{

	JoglCanvas gl;

	// Constructor
	public Simple()
	{
		gl = this;
		init();
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

	// Initialization rotuine called from constructor, only used
	// for setup not drawing things
	public void init()
    {
      /* initialize the widget */
      int width  = gl.getWidth();
      int height = gl.getHeight();
      
      gl.viewport( 0, 0, width, height );
      gl.matrixMode( GL.PROJECTION );
      gl.loadIdentity();
      gl.ortho( -1.0, 1.0, -1.0, 1.0, -1.0, 1.0 );
      gl.matrixMode( GL.MODELVIEW );
    }


    public void display() {

		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT);
		gl.ortho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
		gl.begin(GL.POLYGON);
               gl.vertex(-0.5, -0.5);
               gl.vertex(-0.5, 0.5);
               gl.vertex(0.5, 0.5);
               gl.vertex(0.5, -0.5);

		gl.end();
		gl.flush(); 
		gl.swap();
	}

	public static void main( String args[] ) 
    {

		Simple b = new Simple();

		Frame f = new Frame();

		System.out.println("Here we go");
		b.setSize(300,300);
		f.add("Center", b);
		f.pack();
		f.show();

	}
}
