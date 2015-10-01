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
	boolean firstRender = true; 

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

	//	
	// integer count for display lists
	//
	int idCount =0;

	// some definitions for specifying the drawing mode 
	//
	final int POLYGON =1, 
		LINE = 2, 
		TRIANGLE = 3, 
		LINE_LOOP = 4, 
		POINT = 5, 
		POLYLINE = 6;
	
	// Constructor sets the gl = this, creates vectors, initializes
	// OpenGL, and sets itself as the key listener
	//
	public OGLRenderer()
	{
		gl = this;
		ActorSet = new Vector();
		LightSet = new Vector();
		CameraSet = new Vector();
		initLess = true;
		addKeyListener( this );

	}
    
	//Add an Actor to the scene 
	//
    	public void addActor(Actor actor) { ActorSet.addElement(actor); }

	// Add an Light to the scene 
	// 
    	public void addLight(Light light) { LightSet.addElement(light); }

	// Add an Camera to the scene 
	//
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

		beginDL();

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
		endList();
		gl.end();
		gl.flush();
	}




	//
	//
	//
	public int generateDL ()
	{
		//int id = gl.generateList(1);
		//System.out.println ("GENERATING display list id= " + id);		
		//return (id);

		gl.newList (idCount, GL.COMPILE_AND_EXECUTE);
		System.out.println ("GENERATING display list idCount= " + idCount);
		idCount ++;
		return (idCount -1);

	}



	//
	//
	//
	public void deleteDL ()
	{
		
		for (int i=idCount; i>=0; i--)
		{
			boolean isDL = isList(i);
			System.out.println ("IS LIST idCount= " + i);
			System.out.println ("DELETING display list idCount= " + i);

			// XXX this does not compile !!?!?!?!?
			//gl.deleteLists(i);
		}

	}

	//	
	// 
	// 
	public void beginDL ()
	{
		// XXX this does not compile !!?!?!?!?
		//gl.beginList(); 
		
	}

	//
	//
	public void endDL ()
	{
		gl.endList();
		
	
	}

	//
	//
	public void callDL(int dl)
	{

		gl.callList (dl);

	}



	/** specify a vector for the previous begin command */
    public void vertex(PointType p)
	{
	        // System.out.println("vert: "+p.x+", "+p.y+", "+p.z);
		gl.vertex(p.x, p.y, p.z);
	}





	/** Lets draw something, calls all objects render method,
	  * Camera first, then Lights and Actors. Then swap buffers
	  */
    public void render()
	{
	
		// XXX
		// loop through actors and ask if anything has changed?
		// or do we just expect that if the render was called that
		// something changed -- so we have an flag (firstTime) that creates the lists
		// if it is set and recreates the list if it is not set ?? 
		// 
			
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


		//if (firstRender)
		//{
		//	for (int i=0;i<ActorSet.size();i++) 
		//	{
		//		 create one display list for each actor and fill it. 
		//	}	
		// 
		// XXX
		// call display list[i] (of current Actor) here 
		// 
		// firstRender = false; 
		//}

		//else 	
		//{
		for (int i=0;i<ActorSet.size();i++) 
		{	
			// XXX
			// here we go -- check to see if we need update
			// if so, delete Actor's display list and regenerate it, then call it
			// if not, just call display list  
			//

			//
			// Actor needs updating? 
			//
			if (((Actor) ActorSet.elementAt(i)).doINeedUpdate(System.currentTimeMillis()))
			{
				System.out.println("Hey, this actor at: " + i + " needs updating"); 	
				//System.out.println ("RENDER: display list id= " + id);	

			} 

			int id = generateDL ();
			System.out.println ("RENDER: display list id= " + id);
			deleteDL();
			
			// XXX take this out later
			((Actor) ActorSet.elementAt(i)).render(this);		

		}
		//} // end else !firstRender

		// 
		// XXX
		// call display list[i] (of current Actor) here 
		// 

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
		gl.disable(GL.COLOR_MATERIAL);
		gl.shadeModel(GL.SMOOTH);
		gl.depthFunc( GL.LEQUAL );
		gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE );
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
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
			getCamera().zoom(-1.0F);
			break;
		case '+':
			getCamera().zoom(1.0F);
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
//	public int getWidth() { return gl.getWidth(); }
//	public int getHeight() { return gl.getHeight(); }
//	public void setSize(int w, int h) { gl.setSize(w,h); }

}

