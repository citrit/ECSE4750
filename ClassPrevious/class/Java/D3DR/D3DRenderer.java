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

import java.util.Vector;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
  * OGLRenderer is a concrete implementation of the Renderer class
  * used to organize the scene, supply canvas event
  * handlers, and implement a key listener
  */
public class D3DRenderer extends Frame implements KeyListener, Renderer
{

	/** Collection of Actors in the current scene */
	protected Vector ActorSet;
	/** Collection of Lights */
	protected Vector LightSet;
	/** Collection of Cameras */
	protected Vector CameraSet;
	/** handle multiple thread initialization */
	boolean initLess;
	/** size of window */
	Dimension windowSize;

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
	public D3DRenderer()
	{
		ActorSet = new Vector();
		LightSet = new Vector();
		CameraSet = new Vector();
		initLess = true;
		addKeyListener( this );

	}
    
	/** Add an Actor to the scene */
    public void addActor(Actor actor) { ActorSet.addElement(actor); }

	/** Add an Light to the scene */
    public void addLight(Light light) { LightSet.addElement(light); }

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
			break;
		case POLYGON:
			break;
		case POLYLINE:
			break;
		case LINE:
			break;
		case TRIANGLE:
			break;
		case LINE_LOOP:
			break;
		default:
			break;
		}
	}

	/** Signal all vertices have been entered, flush the buffer */
    public void endDraw()
	{
	}

	/** specify a vector for the previous begin command */
    public void vertex(PointType p)
	{
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

		((Camera) CameraSet.elementAt(0)).render(this);

		for (int i = 0;i < LightSet.size();i++) {
			((Light) LightSet.elementAt(i)).render(this);
		}

		for (int i=0;i<ActorSet.size();i++) {
			((Actor) ActorSet.elementAt(i)).render(this);
		}
	}

	/** Lets make sure OpenGL is initialized with some safe settings */
    public void initialize()
	{
		windowSize = this.size();
	}

	/** Set the current rendering color */
    public void setMaterial(Material mat)
	{

	};

	/** Push the current Model transformation matrix */
	public void pushModelMatrix()
	{
	}

	/** Set the current orientation for consequent drawing */
	public void setOrientation(float rotations[], float scale[], 
		PointType pos)
	{
	}

	/** Pop the current Model transformation matrix */
	public void popModelMatrix()
	{
	}

	/**  Simple redirector to our display function */
	public void paint(Graphics g)
	{
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		//super.paint(g);
		//System.out.println("Call to paint");
		render();
	}

	/** Handle the keystrokes */
	public void keyTyped(KeyEvent e) {
    
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
		case '-':
			getCamera().translate(0.0F, 0.0F, -1.0F);
			break;
		case '+':
			getCamera().translate(0.0F, 0.0F, 1.0F);
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

	/** Get/Set the width and Height of the render window */
	public int getWidth() { return getWidth(); }
	public int getHeight() { return getHeight(); }
	public void setSize(int w, int h) { setSize(w,h); }

}

