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

/**
  * OGLRenderer is a concrete implementation of the Renderer class
  * used to organize the scene, supply canvas event
  * handlers, and implement a key listener
  */
public class OGLRenderer extends JoglCanvas implements KeyListener, Renderer
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


	/** some definitions for specifying the drawing mode */
	final int	POINT = 1,
				LINE = 2,
				POLYGON =3, 
				TRIANGLE = 4, 
				POLYLINE = 5;
	
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
		use();
		addKeyListener( this );

	}
    
	/** Add an Actor to the scene */
    public void addActor(Actor actor) { ActorSet.addElement(actor); }
   public void addsGrid(StructuredGrid grid) { ActorSet.addElement(grid); }

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
			gl.begin(GL.POINTS);
			break;
		case LINE:
			gl.begin(GL.LINES);
			break;
		case POLYGON:
			gl.begin(GL.POLYGON);
			break;
		case TRIANGLE:
			gl.begin(GL.TRIANGLES);
			break;
		case POLYLINE:
			gl.lineWidth(2.0F);
			gl.begin(GL.LINE_STRIP);
			break;
		default:
			break;
		}
	}

	/** Signal all vertices have been entered, flush the buffer */
    public void endDraw()
	{
		gl.end();
		gl.flush();
	}

	/** specify a vector for the previous begin command */
    public void vertex(PointType p)
	{
		gl.vertex(p.x, p.y, p.z);
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

//		for (int i = 0;i < LightSet.size();i++) {
//			((Light) LightSet.elementAt(i)).render(this);
//		}

		gl.matrixMode(GL.MODELVIEW);

		for (int i=0;i<ActorSet.size();i++) {
			((StructuredGrid) ActorSet.elementAt(i)).render(this);	
		}		

		gl.swap();
	}

	/** Lets make sure OpenGL is initialized with some safe settings */
    public void initialize()
	{

		float lightPos[] = {0.0F, 0.0F, 1.0F, 1.0F};
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		float lightAmb[] = {0.6F, 0.6F, 0.6F, 1.0F};
		float lightDiff[] = {0.5F, 0.5F, 0.5F, 1.0F};
		float lightSpec[] = {0.5F, 0.5F, 0.5F, 1.0F};

		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.clearColor (0.0F, 0.0F, 0.0F, 1.0F);
		gl.ortho(0.0, 25.0, 0.0, 25.0, -10.0, 10.0); // Set region of interest
		// initialize blending for transparency

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

	/** Set the current rendering color */
    public void setMaterial(Material mat)
	{
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, mat.ambient);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, mat.diffuse);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, mat.specular);

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
    
		switch (e.getKeyChar()) {
		case 'q':
			getCamera().rotateX(5.0F);
			break;
		case 'w':
			getCamera().rotateX(-5.0F);
			break;
		case 'a':
			getCamera().rotateY(5.0F);
			break;
		case 's':
			getCamera().rotateY(-5.0F);
			break;
		case 'z':
			getCamera().rotateZ(5.0F);
			break;
		case 'x':
			getCamera().rotateZ(-5.0F);
			break;
		case 'e':
			getCamera().translate(0.2F, 0.0F, 0.0F);
			break;
		case 'r':
			getCamera().translate(-0.2F, 0.0F, 0.0F);
			break;
		case 'd':
			getCamera().translate(0.0F, 0.2F, 0.0F);
			break;
		case 'f':
			getCamera().translate(0.0F, -0.2F, 0.0F);
			break;
		case 'c':
			getCamera().translate(0.0F, 0.0F, 0.2F);
			break;
		case 'v':
			getCamera().translate(0.0F, 0.0F, -0.2F);
			break;
	
		case '-':
			((StructuredGrid) ActorSet.elementAt(0)).contourValue -=0.5F;	
			((StructuredGrid) ActorSet.elementAt(0)).render(this);	
			break;
		case '+':
			((StructuredGrid) ActorSet.elementAt(0)).contourValue +=0.5F;	
			((StructuredGrid) ActorSet.elementAt(0)).render(this);	
			break;
		
//		case '-':
//			getCamera().translate(0.0F, 0.0F, -1.0F);
//			break;
//		case '+':
//			getCamera().translate(0.0F, 0.0F, 1.0F);
//			break;

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
//	public int getWidth() { return gl.getWidth(); }
//	public int getHeight() { return gl.getHeight(); }
//	public void setSize(int w, int h) { gl.setSize(w,h); }

}

