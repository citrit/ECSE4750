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

import gl4java.GLContext;
import gl4java.awt.GLCanvas;
import gl4java.awt.GLAnimCanvas;
import gl4java.utils.glut.*;
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
  * GL4JRenderer is a concrete implementation of the Renderer class
  * used to organize the scene, supply canvas event
  * handlers, and implement a key listener
  */
public class GL4JRenderer extends GLAnimCanvas 
	implements KeyListener, Renderer, 
	ComponentListener, MouseMotionListener,MouseListener
{

	/** Collection of Actors in the current scene */
	protected Vector ActorSet;
	/** Collection of Lights */
	protected Vector LightSet;
	/** Collection of Cameras */
	protected Vector CameraSet;
	/** Collection of Actions */
	protected Vector Actions;
	/** Clock thread pointer	 */
	protected Thread ClockThread;
	/** handle multiple thread initialization */
	protected boolean initLess;
	protected int MouseDown;
	protected int[] LastMouse;
	protected int[] InternalTexture;
	protected KeyCallback KeyHandler;
	protected GLUTFunc	glut;
	protected int currTime;
	
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
	public GL4JRenderer()
	{
		//gl = this;
		super(256,256);
		ActorSet = new Vector();
		LightSet = new Vector();
		CameraSet = new Vector();
		Actions = new Vector();
		initLess = true;
		MouseDown = 0;
		LastMouse = new int[2];
		InternalTexture = new int[1];
		InternalTexture[0] = 0;
		KeyHandler = null;
		ClockThread = null;
		glut = new GLUTFuncLightImpl(gl, glu);
		addMouseMotionListener(this);
		addKeyListener(this);
		currTime = 0;
	}
    
	/** Add an Actor to the scene */
    public void addActor(Actor actor) { ActorSet.addElement(actor); }
	/** Get an Actor from the scene */
    public Actor getActor(int index) { return (Actor)ActorSet.elementAt(index); }
	/** Delete an Actor from the scene */
    public void removeActor(int index) { ActorSet.removeElementAt(index); }
	/** Delete an Actor from the scene */
    public void removeActor(Actor act) { ActorSet.removeElement(act); }
	/** Get number of Actors from the scene */
    public int numActors() { return ActorSet.size(); }

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
			gl.glBegin(GL_POINTS);
			break;
		case POLYGON:
			gl.glBegin(GL_POLYGON);
			break;
		case POLYLINE:
			gl.glBegin(GL_LINE_STRIP);
			break;
		case LINE:
			gl.glBegin(GL_LINES);
			break;
		case TRIANGLE:
			gl.glBegin(GL_TRIANGLES);
			break;
		case LINE_LOOP:
			gl.glBegin(GL_LINE_LOOP);
			break;
		default:
			break;
		}
	}

	/** Signal all vertices have been entered, flush the buffer */
    public void endDraw()
	{
		// Set back to defaults in case someone set these.
		gl.glEnd();
		gl.glFlush();
		gl.glDisable(GL_TEXTURE_2D);
		gl.glShadeModel(GL_SMOOTH);
	}

	/** specify a vector for the previous begin command */
    public void vertex(PointType p)
	{
	        // System.out.println("vert: "+p.x+", "+p.y+", "+p.z);
		gl.glVertex3d(p.x, p.y, p.z);
	}

	/** specify a texture coord for the vertex */
    public void texCoord(TexCoordType p)
	{
	        // System.out.println("tex: "+p.x+", "+p.y);
		gl.glTexCoord2d(p.x, p.y);		
	}
	/** specify a normal for the vertex */
    public void normal(PointType n)
    {
	    //System.out.println("norm: "+n.x+", "+n.y+", "+n.z);
		gl.glNormal3d(n.x, n.y, n.z);		
	}
	
	/** Begins definition of Display List. If the one specified exists
		it is deleted and a new one is started */
	public void beginDL(int dlnum)
	{

		//if (dlnum > 0 && gl.isList(dlnum)) {
		//	gl.deleteLists(dlnum, 1);
		//}
		gl.glNewList(dlnum, GL_COMPILE_AND_EXECUTE);
	}

	/** Begins definition of Display List.  */
	public int getDL()
	{
		return gl.glGenLists(1);
	}

	/** End definition of Display List, draw scene */
    public void endDL()
	{
		gl.glEndList();
	}

	/** Execute the specified display list. */
	public void drawDL(int dlnum)
	{
		gl.glCallList(dlnum);
	}


	/** Lets draw something, calls all objects render method,
	  * Camera first, then Lights and Actors. Then swap buffers
	  */
    public void render(boolean swapBuffs)
	{
		if (glj.gljMakeCurrent() == false) return;
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

		((Camera) CameraSet.elementAt(0)).render(this);

		for (int i = 0;i < LightSet.size();i++) {
			((Light) LightSet.elementAt(i)).render(this);
		}

		gl.glMatrixMode(GL_MODELVIEW);
		for (int i=0;i<ActorSet.size();i++) {
			((Actor) ActorSet.elementAt(i)).render(this);
		}
		if (swapBuffs)
			glj.gljSwap();
        //glj.gljCheckGL();
        //glj.gljFree();
 	}

	/** GL4Java required call */
    public void init()
	{
		this.initialize();
	}
	
	/** Lets make sure OpenGL is initialized with some safe settings */
    public void initialize()
	{
		gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		// initialize blending for transparency
		gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glEnable(GL_DITHER);
		//gl.disable(GL_ALPHA_TEST);
		//gl.disable(GL_COLOR_MATERIAL);
		gl.glShadeModel(GL_SMOOTH);
		gl.glDepthFunc( GL_LEQUAL );
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		gl.glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);
		gl.glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, 1);
		glj.gljCheckGL();
	}

	/** Set the current rendering color */
    public void setMaterial(Material mat)
	{
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, mat.ambient);
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, mat.diffuse);
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, mat.specular);
		gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 0.8F);
		gl.glColor3f(mat.diffuse[0],mat.diffuse[1],mat.diffuse[2]);
	};

	/** Push the current Model transformation matrix */
	public void pushModelMatrix()
	{
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glPushMatrix();
	}

	/** Set the current orientation for consequent drawing */
	public void setOrientation(float rotations[], float scale[], 
		PointType pos)
	{
		gl.glTranslatef(pos.x, pos.y,pos.z);
		gl.glRotatef(rotations[0], 1, 0, 0);
		gl.glRotatef(rotations[1], 0, 1, 0);
		gl.glRotatef(rotations[2], 0, 0, 1);
		gl.glScalef(scale[0], scale[1], scale[2]);
	}

	/** Pop the current Model transformation matrix */
	public void popModelMatrix()
	{
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glPopMatrix();
	}

	/** Load the current texture
	 */
	public void loadTexture(int imageNum, Texture atex)
	{
		gl.glEnable(GL_TEXTURE_2D);
		gl.glShadeModel(GL_FLAT);
		gl.glTexImage2D(GL_TEXTURE_2D, imageNum, 3, atex.gTexW, atex.gTexH, 0,
		    GL_BGRA_EXT, GL_UNSIGNED_BYTE, atex.pixels);
	}

	/**  Simple redirector to our display function */
	public void display()
	{
		//System.out.println("Call to display");
		if (this.isRunning) {
			for (int i=0;i<Actions.size();i++) {
				((Action)Actions.elementAt(i)).tick(currTime++);
			}
		}
		render(true);
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
			gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			break;
		case 'f':
		case 'F':
			gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			break;
		case 27:           /* Esc will quit */
			System.exit(1);
			break;
		default:
			break;
		}
		if (KeyHandler != null)
			KeyHandler.keyCallback(e);

		e.consume();
		render(true);
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
		//System.out.println("Resized: " + e);
		super.componentResized(e);
	}
	public void componentHidden(ComponentEvent e) {
		//System.out.println("Hidden: " + e);
		super.componentHidden(e);
	}
	public void componentShown(ComponentEvent e) {
			//System.out.println("Shown: " + e);
		super.componentShown(e);
	}
	public void componentMoved(ComponentEvent e) {
			//System.out.println("Moved: " + e);
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
				getCamera().translate(((float)LastMouse[0]-e.getX())*-0.01F,0,0);
				getCamera().translate(0,((float)LastMouse[1]-e.getY())*0.01F,0);
				break;
			case 3:
				getCamera().zoom(((float)LastMouse[1]-e.getY())*0.1F);
				break;
		}
		//System.out.print("MMove: " + e.getPoint().toString() + "\r");
		e.consume();
		LastMouse[0] = e.getX();
		LastMouse[1] = e.getY();
		render(true);		
	}
    /** Invoked when the mouse button has been moved on a component (with no buttons no down).  */
 	public void mouseMoved(MouseEvent e)
 	{
		//System.out.print("MMove: " + e.getPoint().toString() + "\r");
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
		//System.out.print("MDown: " + e.getPoint().toString() + "\r");
		LastMouse[0] = e.getX();
		LastMouse[1] = e.getY();
		e.consume();
   }

    public void mouseReleased(MouseEvent e) {
    	MouseDown = 0;
		e.consume();
		//System.out.print("MReleased: " + e.getPoint().toString() + "\r");
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

	/** Add a key handler callback for the client */
	public void addKeyCallback(KeyCallback kb) {
		KeyHandler = kb;
	}

	/** Grab the back buffer as a texture */
	public void backBufferIntoTexture()
	{
		gl.glEnable(GL_TEXTURE_2D);
		gl.glShadeModel(GL_FLAT);
		if (InternalTexture[0] == 0) {
			gl.glGenTextures(1,InternalTexture);
			System.out.print("Initializing internal texture "+InternalTexture[0]+":["+getWidth()+","+getHeight()+"]\n");
			gl.glBindTexture(GL_TEXTURE_2D, InternalTexture[0]);
			gl.glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 0, 0, this.getWidth(), this.getHeight(), 0);
		}
		else {
			gl.glBindTexture(GL_TEXTURE_2D, InternalTexture[0]);
			gl.glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, this.getWidth(), this.getHeight());
		}
		glut.glutReportErrors();
	}
	
	/** Add an action to the renderer */
	public void addAction(Action act)
	{
		Actions.addElement(act);
	}											  

	/** Start the scene clock, does nothing if no actions were added */
	public void startScene()
	{
		this.start();
	}

	/** Stop the scene clock, does nothing if no actions were added */
	public void stopScene()
	{
		this.stop();
	}

	/** Get/Set the width and Height of the render window */
	public int getWidth() { return super.cvsGetWidth(); }
	public int getHeight() { return super.cvsGetHeight(); }
//	public void setSize(int w, int h) { gl.setSize(w,h); }

}

