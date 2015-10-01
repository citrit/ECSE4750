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
/////////////////////////////////////////////////////////////////////////

/**
 * Renderer interface, this is a place holder for the function calls 
 * in the Renderer class. Defined as an interface to specify behavior
 * not actions.
 *
 * @author		Thomas D. Citrinit
 * @version		0.1
 */
public interface Renderer
{
	/** some definitions for specifying the drawing mode */
	final int POLYGON =1, 
		LINE = 2, 
		TRIANGLE = 3, 
		LINE_LOOP = 4, 
		POINT = 5, 
		POLYLINE = 6;

	/** Add an Actor to the scene */
    public abstract void addActor(Actor actor);
	/** Get an Actor from the scene */
    public abstract Actor getActor(int index);
	/** Delete an Actor from the scene */
    public abstract void removeActor(int index);
	/** Delete an Actor from the scene */
    public abstract void removeActor(Actor act);
	/** Get number of Actors from the scene */
    public abstract int numActors();

	/** Add an Light to the scene */
    public abstract void addLight(Light light);
	/** Turn lighting off if there are no normals */
    public abstract void lighting(boolean onOrOff);

	/** Add an Camera to the scene */
    public abstract void addCamera(Camera cam);

	/** Get the current active Camera, currently only one camera
	  * supported
	  */
    public abstract Camera getCamera();

	/** Sets the render mode for consequent calls */
    public abstract void beginDraw(int renderMode);

	/** Signal all vertices have been entered, flush the buffer */
    public abstract void endDraw();

	/** specify a vector for the previous begin command */
    public abstract void vertex(PointType p);
	/** specify a texture coord for the vertex */
    public abstract void texCoord(TexCoordType p);
	/** specify a normal for the vertex */
    public abstract void normal(PointType n);
	/** Begins definition of Display List. If the one specified exists
		it is deleted and a new one is started */
	public abstract void beginDL(int dlnum);

	/** Begins definition of Display List.  */
	public abstract int getDL();

	/** End definition of Display List, draw scene */
    public abstract void endDL();

	/** Execute the specified display list. */
	public abstract void drawDL(int dlnum);

	/** Lets draw something, calls all objects render method,
	  * Camera first, then Lights and Actors. Then swap buffers
	  */
    public abstract void render(boolean swapBuffs);

	/** Lets make sure OpenGL is initialized with some safe settings */
    public abstract void initialize();

	/** Set the current rendering color */
    public abstract void setMaterial(Material mat);

	/** Push the current Model transformation matrix */
	public abstract void pushModelMatrix();

	/** Set the current orientation for consequent drawing */
	public abstract void setOrientation(float rotations[], float scale[], 
										PointType pos);

	/** Pop the current Model transformation matrix */
	public abstract void popModelMatrix();
	
	/** Load the current texture
	 */
	public abstract void loadTexture(int ImageNum, Texture atex);
	
	/** Add a key handler callback for the client */
	public abstract void addKeyCallback(KeyCallback kb);
									 
	/** Grab the back buffer as a texture */
	public abstract void backBufferIntoTexture();

	/** Add an action to the renderer */
	public abstract void addAction(Action act);

	/** Start the scene clock, does nothing if no actions were added */
	public abstract void startScene();
	/** Stop the scene clock, does nothing if no actions were added */
	public abstract void stopScene();

	/** Get/Set the width and Height of the render window */
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void setSize(int w, int h);


}
