

/*
 *	Simple sample of Textures in Java and OpenGL
 *
 *
 *  Simple example of programming using the OpenGL graphics library
 *  Taken from the "OpenGL Programming Guide"
 *
 *	For demo purposes only, used in:
 *		Advanced Computer Graphics and Data Visualization  35-6961
 *		ECSE, Rensselaer Polytechnic Institute
 *
 *	January 1999
 */

import jogl.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class TextureTest extends JoglCanvas implements KeyListener
{
    JoglCanvas gl;
    boolean initless;
	Image gTex;
	byte[][][] gPixels;
	int gTexW, gTexH;

    // Constructor for our simple class.
    public TextureTest ()
    {
		gl = this;
		initless = true;
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
		gl.loadIdentity();		// Reset the transformation matrix
		gl.perspective(35.0, width/height, 0.0, 100.0);
		gl.lookAt(2.0, 1.5, 8.0, 2.0, 1.5, 0.0, 0.0, 1.0, 0.0); 
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms
		//gl.enable(GL.DEPTH_TEST);
		//gl.depthFunc(GL.LEQUAL);

		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_WRAP_S, GL.REPEAT);
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_WRAP_T, GL.REPEAT);
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_MAG_FILTER, GL.NEAREST);
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_MIN_FILTER, GL.NEAREST);
		gl.pixelStore(GL.UNPACK_ALIGNMENT, 1);
		gl.texEnv(GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.DECAL);
		gl.enable(GL.TEXTURE_2D);
		gl.shadeModel(GL.FLAT);

		//Load the texture, make sure its size is a factor of 2 (128x128, 256x256, etc.)
		gTex = getToolkit().getImage("house.gif");
        try {
			MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(gTex, 0);
            tracker.waitForID(0);
        }
        catch ( Exception e ) {}
		gTexW = gTex.getWidth(this);
		gTexH = gTex.getHeight(this);
		System.out.println("Width: " + gTexW + " Height: " + gTexH);
		int[] pixels = new int[gTexW * gTexH];
		PixelGrabber pg = new PixelGrabber(gTex, 0, 0, gTexW, gTexH, pixels, 0, gTexW);
		try {
			pg.grabPixels();
		}
		catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return;	
		}
		System.out.println("Loading texture... ");
		gl.texImage(GL.TEXTURE_2D, 0, 3, gTexW, gTexH, 0,
		    GL.BGRA_EXT, GL.UNSIGNED_BYTE, pixels);

		initless = false;
    }


    // Sort of a legacy type function call, actually inside Java
    // This stuff should be in the paint call
    public void display() {
	
		if (initless) 
		    init();
		// clear the screen and draw a yellow square
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT);
		gl.color(1.0, 1.0, 0.0);
		gl.begin(GL.POLYGON);
		  gl.texCoord(0.0, 0.0);gl.vertex(0.0, 0.0); 
		  gl.texCoord(0.0, 1.0);gl.vertex(0.0, 3.0); 
		  gl.texCoord(1.0, 1.0);gl.vertex(3.0, 3.0); 
		  //gl.texCoord(0.0, 0.0);gl.vertex(4.0, 1.5); 
		  gl.texCoord(1.0, 0.0);gl.vertex(3.0, 0.0); 
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
		TextureTest b = new TextureTest();
	
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
		    System.exit(0);
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

