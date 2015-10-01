//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    January 1998
//
//////////////////////////////////////////////////////////////////////////

import jogl.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;

/**
  * OGLRenderer is a concrete implementation of the Renderer class
  * used to organize the scene, supply canvas event
  * handlers, and implement a key listener
  */
public class OGLRenderer extends JoglCanvas 
				implements KeyListener, Renderer, ComponentListener, MouseMotionListener,MouseListener
{

	/** Collection of Actors in the current scene */
	protected Vector ActorSet;
	/** Collection of Lights */
	protected Vector LightSet;
	/** Collection of Cameras */
	protected Vector CameraSet;
	/** Convenience pointer, simply points to the current instance */
	JoglCanvas gl;
	/** handle multiple thread initialization */
	boolean initLess;
	protected int MouseDown;
	int[] LastMouse;

	/** some definitions for specifying the drawing mode */
	final int POLYGON =1, 
		LINE = 2, 
		TRIANGLE = 3, 
		LINE_LOOP = 4, 
		POINT = 5, 
		POLYLINE = 6;
	
	/** Constructor sets the gl = this, creates vectors, initializes
	  * OpenGL, and sets itself as the key listener
	  */
	public OGLRenderer()
	{
		gl = this;
		ActorSet = new Vector();
		LightSet = new Vector();
		CameraSet = new Vector();
		initLess = true;
		addKeyListener( this );
		addMouseListener( this );
  		addMouseMotionListener(this);
		MouseDown = 0;
		LastMouse = new int[2];

	}
    
	/** Add an Actor to the scene */
    public void addActor(Actor actor) { ActorSet.addElement(actor); }

	/** Add an Light to the scene */
    public void addLight(Light light) { LightSet.addElement(light); }
	/** Turn lighting off if there are no normals */
    public void lighting(boolean onOrOff)
    {
    	int i;
    	for (i=0;i<LightSet.size();i++) {
    		((Light)LightSet.elementAt(i)).setState(onOrOff);
    	}
    }

	/** Add an Camera to the scene */
    public void addCamera(Camera cam) 
	{ 
		CameraSet.addElement(cam);
		cam.setRenderer(this);
	}

	/** Get the current active Camera, currently only one camera
	  * supported
	  */
    public Camera getCamera() { return (Camera)CameraSet.elementAt(0); }

	/** Sets the render mode for consequent calls */
    public void beginDraw(int renderMode)
	{

		switch(renderMode) {
		case POINT:
			gl.begin(GL.POINTS);
			break;
		case POLYGON:
			gl.begin(GL.POLYGON);
			break;
		case POLYLINE:
			gl.begin(GL.LINE_STRIP);
			break;
		case LINE:
			gl.begin(GL.LINES);
			break;
		case TRIANGLE:
			gl.begin(GL.TRIANGLES);
			break;
		case LINE_LOOP:
			gl.begin(GL.LINE_LOOP);
			break;
		default:
			break;
		}
	}

	/** Signal all vertices have been entered, flush the buffer */
    public void endDraw()
	{
		// Set back to defaults in case someone set these.
		gl.end();
		gl.flush();
		gl.disable(GL.TEXTURE_2D);
		gl.shadeModel(GL.SMOOTH);
	}

	/** specify a vector for the previous begin command */
    public void vertex(PointType p)
	{
	        // System.out.println("vert: "+p.x+", "+p.y+", "+p.z);
		gl.vertex(p.x, p.y, p.z);
	}

	/** specify a texture coord for the vertex */
    public void texCoord(TexCoordType p)
	{
	        // System.out.println("tex: "+p.x+", "+p.y);
		gl.texCoord(p.x, p.y);		
	}
	/** specify a normal for the vertex */
    public void normal(PointType n)
    {
	    //System.out.println("norm: "+n.x+", "+n.y+", "+n.z);
		gl.normal(n.x, n.y, n.z);		
	}


	/** Lets draw something, calls all objects render method,
	  * Camera first, then Lights and Actors. Then swap buffers
	  */
    public void render()
	{
		if (initLess) {
			initialize();
			initLess = false;
		}
		gl.use();
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT );

		((Camera) CameraSet.elementAt(0)).render(this);

		for (int i = 0;i < LightSet.size();i++) {
			((Light) LightSet.elementAt(i)).render(this);
		}

		gl.matrixMode(GL.MODELVIEW);
		for (int i=0;i<ActorSet.size();i++) {
			((Actor) ActorSet.elementAt(i)).render(this);
		}
		gl.swap();
	}

	/** Lets make sure OpenGL is initialized with some safe settings */
    public void initialize()
	{
		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.clearColor (0.0F, 0.0F, 0.0F, 1.0F);
		// initialize blending for transparency
		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
		gl.enable(GL.DEPTH_TEST);
		gl.enable(GL.DITHER);
		//gl.disable(GL.ALPHA_TEST);
		//gl.disable(GL.COLOR_MATERIAL);
		gl.shadeModel(GL.SMOOTH);
		gl.depthFunc( GL.LEQUAL );
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_WRAP_S, GL.REPEAT);
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_WRAP_T, GL.REPEAT);
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_MAG_FILTER, GL.NEAREST);
		gl.texParameter(GL.TEXTURE_2D, GL.TEXTURE_MIN_FILTER, GL.NEAREST);
		gl.pixelStore(GL.UNPACK_ALIGNMENT, 1);
		gl.texEnv(GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.DECAL);
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
	}

	/** Set the current rendering color */
    public void setMaterial(Material mat)
	{
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, mat.ambient);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, mat.diffuse);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, mat.specular);
		gl.material(GL.FRONT_AND_BACK, GL.SHININESS, 0.8F);
		gl.color(mat.diffuse[0],mat.diffuse[1],mat.diffuse[2]);
	};

	/** Push the current Model transformation matrix */
	public void pushModelMatrix()
	{
		gl.matrixMode (GL.MODELVIEW);
		gl.pushMatrix();
	}

	/** Set the current orientation for consequent drawing */
	public void setOrientation(float rotations[], float scale[], 
		PointType pos)
	{
		gl.translate(pos.x, pos.y,pos.z);
		gl.scale(scale[0], scale[1], scale[2]);
		gl.rotate(rotations[0], 1, 0, 0);
		gl.rotate(rotations[1], 0, 1, 0);
		gl.rotate(rotations[2], 0, 0, 1);
	}

	/** Pop the current Model transformation matrix */
	public void popModelMatrix()
	{
		gl.matrixMode (GL.MODELVIEW);
		gl.popMatrix();
	}

	/** Load the current texture
	 */
	public void loadTexture(int imageNum, Texture atex)
	{
		gl.enable(GL.TEXTURE_2D);
		gl.shadeModel(GL.FLAT);
		gl.texImage(GL.TEXTURE_2D, imageNum, 3, atex.gTexW, atex.gTexH, 0,
		    GL.BGRA_EXT, GL.UNSIGNED_BYTE, atex.pixels);
	}

	/**  Simple redirector to our display function */
	public void paint(Graphics g)
	{
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		super.paint(g);
		//System.out.println("Call to paint");
		render();
	}

	/** Handle the keystrokes */
	public void keyTyped(KeyEvent e) {
    
    	float viewDelta = 0.05F * (float)getCamera().viewSize;
		switch (e.getKeyChar()) {
		case 'h':
			getCamera().rotateY(15.0F);
			break;
		case 'j':
			getCamera().rotateX(15.0F);
			break;
		case 'k':
			getCamera().rotateX(-15.0F);
			break;
		case 'l':
			getCamera().rotateY(-15.0F);
			break;
		case 'a':
			getCamera().translate(-viewDelta, 0.0F, 0.0F);
			break;
		case 's':
			getCamera().translate(viewDelta, 0.0F, 0.0F);
			break;
		case 'w':
			getCamera().translate(0.0F, viewDelta, 0.0F);
			break;
		case 'z':
			getCamera().translate(0.0F, -viewDelta, 0.0F);
			break;
		case '-':
			getCamera().zoom(-viewDelta);
			break;
		case '+':
			getCamera().zoom(viewDelta);
			break;
		case 'e':
		case 'E':
			gl.polygonMode(GL.FRONT_AND_BACK, GL.LINE);
			break;
		case 'f':
		case 'F':
			gl.polygonMode(GL.FRONT_AND_BACK, GL.FILL);
			break;
		case 27:           /* Esc will quit */
			System.exit(1);
			break;
		default:
			break;
		}
		e.consume();
		render();
	}
	/** Handle the keystrokes */
	public void keyPressed(KeyEvent e) {

	}
	/** Handle the keystrokes */
	public void keyReleased(KeyEvent e) {

	}
	
	/* Functions to implement ComponentListener */
	public void componentResized(ComponentEvent e) 
	{
		System.out.println("Resized: " + e);
		super.componentResized(e);
	}
	public void componentHidden(ComponentEvent e) {
		System.out.println("Hidden: " + e);
		super.componentHidden(e);
	}
	public void componentShown(ComponentEvent e) {
			System.out.println("Shown: " + e);
		super.componentShown(e);
	}
	public void componentMoved(ComponentEvent e) {
			System.out.println("Moved: " + e);
		super.componentMoved(e);
	}
	
	/**Invoked when a mouse button is pressed on a component and then dragged. */
	public void mouseDragged(MouseEvent e) 
	{
		switch (MouseDown) {
			case 1:
				getCamera().rotateY(((float)LastMouse[0]-e.getX())*0.4F);
				getCamera().rotateX(((float)LastMouse[1]-e.getY())*-0.4F);
				break;
			case 2:
				getCamera().translate(((float)LastMouse[0]-e.getX())*-0.1F,0,0);
				getCamera().translate(0,((float)LastMouse[1]-e.getY())*0.1F,0);
				break;
			case 3:
				getCamera().zoom(((float)LastMouse[1]-e.getY())*0.1F);
				break;
		}
		e.consume();
		LastMouse[0] = e.getX();
		LastMouse[1] = e.getY();
		render();		
	}
    /** Invoked when the mouse button has been moved on a component (with no buttons no down).  */
 	public void mouseMoved(MouseEvent e)
 	{

 	} 


    public void mousePressed(MouseEvent e) {
    	if ((e.getModifiers() & InputEvent.BUTTON1_MASK)
				== InputEvent.BUTTON1_MASK) {
			MouseDown = 1;
		}
     	else if ((e.getModifiers() & InputEvent.BUTTON2_MASK)
				== InputEvent.BUTTON2_MASK) {
			MouseDown = 2;
		}
     	else if ((e.getModifiers() & InputEvent.BUTTON3_MASK)
				== InputEvent.BUTTON3_MASK) {
			MouseDown = 3;
		}
		LastMouse[0] = e.getX();
		LastMouse[1] = e.getY();
		e.consume();
   }

    public void mouseReleased(MouseEvent e) {
    	MouseDown = 0;
		e.consume();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }


	/** Get/Set the width and Height of the render window */
//	public int getWidth() { return gl.getWidth(); }
//	public int getHeight() { return gl.getHeight(); }
//	public void setSize(int w, int h) { gl.setSize(w,h); }

}

