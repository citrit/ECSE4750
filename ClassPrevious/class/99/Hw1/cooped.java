//////////////////////////////////////////////////////
//  Dave Cooper
//  Advanced Computer Graphics and Data Visualization
//  Homework #1
//  February 8th 1999
//////////////////////////////////////////////////////

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//////////////////////////////////////////////////////////////////////
// CLASS: HW1
// Extends: JoglCanvas
// Implements: KeyListener, ActionListener
//
// Java OpenGL application that allows three 3D objects to be 
// rotated, scaled, and translated.
//////////////////////////////////////////////////////////////////////
class HW1 extends JoglCanvas implements KeyListener, ActionListener {

	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;
	static MenuBar menubar;
	static CheckboxMenuItem cmiSphere;
	static CheckboxMenuItem cmiBox;
	static CheckboxMenuItem cmiCone;
	Box box;
	Cone cone;
	Sphere sphere;

	float boxAmb[] = {0.8F, 0.2F, 0.2F, 1.0F};
	float boxDiff[] = {0.8F, 0.0F, 0.0F, 1.0F};
	float boxSpec[] = {0.9F, 0.0F, 0.0F, 1.0F};

	float coneAmb[] = {0.3F, 0.3F, 0.3F, 1.0F};
	float coneDiff[] = {0.2F, 0.2F, 0.6F, 1.0F};
	float coneSpec[] = {0.0F, 0.0F, 0.0F, 1.0F};

	float sphAmb[] = {0.8F, 0.8F, 0.0F, 0.3F};
	float sphDiff[] = {0.6F, 0.6F, 0.0F, 0.3F};
	float sphSpec[] = {0.8F, 0.8F, 0.0F, 0.3F};

	//////////////////////////////////////////////
	// function: HW1 [CONSTRUCTOR]
	// purpose:  the purpose of this constructor
	//           is to set up our application when
	//           a HW1 object is instantiated.
	//////////////////////////////////////////////
	public HW1 ()
	{
		gl = this;
		sphere = new Sphere(sphAmb, sphDiff, sphSpec, -2, 0, 0);
		box = new Box(boxAmb, boxDiff, boxSpec, 0, 0, 0);
		cone = new Cone(coneAmb, coneDiff, coneSpec, 2, 0, 0);

		addKeyListener( this );
	}

	/////////////////////////////////////////////
	// function: init
	// purpose:  to handle initial set up of 
	//	          variables to be used later
	/////////////////////////////////////////////
	public void init(){
		initLess = false;
		float lightPos[] = {0.0F, 0.0F, 2.0F, 1.0F};
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		float lightAmb[] = {0.7F, 0.2F, 0.2F, 1.0F};
		float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
		float lightSpec[] = {0.8F, 0.8F, 0.8F, 1.0F};

		int width  = gl.getWidth();
		int height = gl.getHeight();

		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();					// Reset the transformation matrix
		gl.ortho(-4.0, 4.0, -4.0, 4.0, -4.0, 4.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms

		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
		gl.depthFunc( GL.LEQUAL );
		gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE );

		gl.color(1.0, 0.0, 0.0);
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

	////////////////////////////////////////////
	// function: display
	// purpose:  the purpose of this function is
	//           to handle all of the 
	////////////////////////////////////////////
	public void display(){
		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
		gl.matrixMode(GL.MODELVIEW);
		box.draw(this);		//draw the box
		cone.draw(this);		//draw the cone
		sphere.draw(this);	//draw the sphere

		gl.flush();	// Make sure all commands have completed.
		gl.swap();	 // Swap the render buffer with the screen buffer
	}

	////////////////////////////////////////////
	// function: paint
	// purpose:  standard Java paint function
	//           for displaying graphics
	////////////////////////////////////////////
	public void paint(Graphics g)
	{
		super.paint(g);
		if(initLess) init();
		display();
	}


	////////////////////////////////////////////
	// function: keyTyped
	// purpose:  this method handles all key
	// 			 input and acts accordingly.
	////////////////////////////////////////////
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyChar()) {
		case 'h':
			gl.matrixMode (GL.MODELVIEW);			 /* manipulate modelview matrix  */
			gl.rotate(15.0, 0.0,1.0,0.0);
			e.consume();
			break;
		case 'j':
			gl.matrixMode (GL.MODELVIEW);			 /* manipulate modelview matrix  */
			gl.rotate(15.0, 1.0,0.0,0.0);
			e.consume();
			break;
		case 'k':
			gl.matrixMode (GL.MODELVIEW);			 /* manipulate modelview matrix  */
			gl.rotate(-15.0, 1.0,0.0,0.0);       
			e.consume();
			break;
		case 'l':
			gl.matrixMode (GL.MODELVIEW);			 /* manipulate modelview matrix  */
			gl.rotate(-15.0, 0.0,1.0,0.0);       
			e.consume();
			break;
		case '+':
			gl.matrixMode (GL.MODELVIEW);			 /* manipulate modelview matrix  */
			gl.translate(0.0, 0.0,0.5);
			e.consume();
			break;
		case '-':
			gl.matrixMode (GL.MODELVIEW);			 /* manipulate modelview matrix  */
			gl.translate(0.0, 0.0,-0.5);
			e.consume();
			break;
		case '1':		//rot - X
			processCommand(1);   
			break;
		case '3':		//rot + X
			processCommand(3);
			break;
		case '4':		//rot - Y
			processCommand(4);
			break;
		case '6':		//rot + Y
			processCommand(6);
			break;
		case '7':		//rot - Z
			processCommand(7);
			break;
		case '9':		//rot + Z
			processCommand(9);
			break;
		case 27:			//quit application
			System.exit(1);
			break;
		case 'r':		//reset objects
			processCommand(10);
			break;
		case '<':		//scale down
			processCommand(11);
			break;
		case '>':		//scale up
			processCommand(12);
			break;
		case 'q':		//move - X
			processCommand(13);
			break;
		case 'w':		//move + x
			processCommand(14);
			break;
		case 'a':		//move - y
			processCommand(15);
			break;
		case 's':		//move + y
			processCommand(16);
			break;
		case 'z':		//move - z
			processCommand(17);
			break;
		case 'x':		//move + z
			processCommand(18);
			break;
		default:
			break;
		}
		display();
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e){}

	/////////////////////////////////////////////
	// function: actionPerformed
	// purpose:  this function handles events
	//           that come in from the menu
	/////////////////////////////////////////////
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		if(cmd.equals("exit")) System.exit(0);
	}



	/////////////////////////////////////////////
	// function: processCommand
	// purpose:  this function handles events
	//           passed from the keyTyped function
	/////////////////////////////////////////////
	public void processCommand(int numberPressed){
		
		switch(numberPressed) {
		case 10:		//reset objects
			cone.resetScaleFactor();
			cone.resetTranslations();
			cone.resetRotations();
			sphere.resetScaleFactor();
			sphere.resetTranslations();
			sphere.resetRotations();
			box.resetScaleFactor();
			box.resetTranslations();
			box.resetTranslations();
			break;
		case 11:		//decrease scale of objects
			if(cmiCone.getState()) cone.decreaseScaleFactor();
			if(cmiSphere.getState()) sphere.decreaseScaleFactor();
			if(cmiBox.getState()) box.decreaseScaleFactor();
			break;
		case 12:		//increase the scale of objects
			if(cmiCone.getState()) cone.increaseScaleFactor();
			if(cmiSphere.getState()) sphere.increaseScaleFactor();
			if(cmiBox.getState()) box.increaseScaleFactor();
			break;
		case 13:		//translate objects - x
			if(cmiCone.getState()) cone.translation[0]--;
			if(cmiSphere.getState()) sphere.translation[0]--;
			if(cmiBox.getState()) box.translation[0]--;
			break;
		case 14:		//translate objects + x
			if(cmiCone.getState()) cone.translation[0]++;
			if(cmiSphere.getState()) sphere.translation[0]++;
			if(cmiBox.getState()) box.translation[0]++;
			break;
		case 15:		//translate objects - y
			if(cmiCone.getState()) cone.translation[1]--;
			if(cmiSphere.getState()) sphere.translation[1]--;
			if(cmiBox.getState()) box.translation[1]--;
			break;
		case 16:		//translate objects + y
			if(cmiCone.getState()) cone.translation[1]++;
			if(cmiSphere.getState()) sphere.translation[1]++;
			if(cmiBox.getState()) box.translation[1]++;
			break;
		case 17:		//translate objects - z
			if(cmiCone.getState()) cone.translation[2]--;
			if(cmiSphere.getState()) sphere.translation[2]--;
			if(cmiBox.getState()) box.translation[2]--;
			break;
		case 18:		//translate objects + z
			if(cmiCone.getState()) cone.translation[2]++;
			if(cmiSphere.getState()) sphere.translation[2]++;
			if(cmiBox.getState()) box.translation[2]++;
			break;
		default:		//process rotational calls
			if(cmiCone.getState()) cone.setRotation(numberPressed);
			if(cmiSphere.getState()) sphere.setRotation(numberPressed);
			if(cmiBox.getState()) box.setRotation(numberPressed);
			break;
		}
		display();	//display results
	}


	/////////////////////////////////////////////
	// function: main
	// purpose:  the purpose of this function is
	//				 to start the application, and 
	//           set up the frame and menu system
	/////////////////////////////////////////////
	public static void main( String args[] ) 
	{
		System.out.println("=================================================");
		System.out.println("Dave Cooper - Homework #1");
		System.out.println("Advanced Computer Graphics and Data Visualization");
		System.out.println("=================================================");
		System.out.println("Use  [1|3] for Z rotation");
		System.out.println("Use  [4|6] for Y rotation");
		System.out.println("Use  [7|9] for X rotation");
		System.out.println("Use: [H|J|K|L] to adjust the viewpoint");
		System.out.println("Use: [< |>] to increase and decrease object scale");
		System.out.println("Use  [Z|X] for translation in X direction");
		System.out.println("Use  [A|S] for translation in Y direction");
		System.out.println("Use  [Q|W] for translation in Z direction");
		System.out.println("Reset scale, translation and rotation: [R]");
		System.out.println();
		System.out.println("Note: multiple objects can be manipulated");
		System.out.println("simultaneously by checking them in the menu.");
		System.out.println("=================================================");




		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		HW1 h = new HW1();

		// Make it visible and set size
		h.setVisible(true);
		h.setSize(500, 500);

		menubar = new MenuBar();
		fm.setMenuBar(menubar);

		Menu mFile = new Menu("File");
		Menu mShape = new Menu("Shape");
		menubar.add(mFile);
		menubar.add(mShape);
		MenuItem miExit = new MenuItem("Exit");
		miExit.setActionCommand("exit");
		miExit.addActionListener(h);
		mFile.add(miExit);
		cmiSphere = new CheckboxMenuItem("Sphere", true);
		cmiSphere.setActionCommand("sphere");
		cmiSphere.addActionListener(h);
		cmiCone = new CheckboxMenuItem("Cone", false);
		cmiCone.setActionCommand("cone");
		cmiCone.addActionListener(h);
		cmiBox = new CheckboxMenuItem("Box", false);
		cmiBox.setActionCommand("box");
		cmiBox.addActionListener(h);

		mShape.add(cmiSphere);
		mShape.add(cmiCone);
		mShape.add(cmiBox);

		fm.addWindowListener(new WindowAdapter() {
										public void windowClosing(WindowEvent e) {System.exit(0);}
									});

		// Add the canvas to the frame and make it show
		fm.add("Center", h);
		fm.pack();
		fm.show();
	}

}


/////////////////////////////////////////////
//	Class: GeometricPrimitive
// Extends: none
// Implements: none
//
// Geometric primitive class from which other
// geometric primitives can inherit.
/////////////////////////////////////////////
class GeometricPrimitive {

	float scaleFactor;	//scaling factor
	float rotation[];		//rotation store
	float translation[];	//translation store
	float origin[];		//reset information store
	double rc;	 			//rotational constant

	//color settings for a blue rough box
	float Amb[] = {0.0F, 0.0F, 0.0F, 0.0F};
	float Diff[] = {0.0F, 0.0F, 0.0F, 0.0F};
	float Spec[] = {0.0F, 0.0F, 0.0F, 0.0F};

	/////////////////////////////////////////////
	// function: GeometricPrimitive constructor
	// purpose:  sets up initial conditions for
	//				 a primitive
	/////////////////////////////////////////////
	public GeometricPrimitive(){
		scaleFactor = 1;
		rotation = new float[3];
		translation = new float[3];
		origin = new float[3];
		rc = 5;
	}

	/////////////////////////////////////////////
	// function: setAmbient
	// purpose: sets up ambient settings
	/////////////////////////////////////////////
	public void setAmbient(float r, float g, float b, float a){
		Amb[0] = r;
		Amb[1] = g;
		Amb[2] = b;
		Amb[3] = a;
	}

	/////////////////////////////////////////////
	// function: setDiffuse
	// purpose: sets up diffuse settings
	/////////////////////////////////////////////
	public void setDiffuse(float r, float g, float b, float a){
		Diff[0] = r;
		Diff[1] = g;
		Diff[2] = b;
		Diff[3] = a;
	}
	
	/////////////////////////////////////////////
	// function: setSpecular
	// purpose: sets up specular settings
	/////////////////////////////////////////////
	public void setSpecular(float r, float g, float b, float a){
		Spec[0] = r;
		Spec[1] = g;
		Spec[2] = b;
		Spec[3] = a;
	}

	/////////////////////////////////////////////
	// function: increaseScaleFactor
	// purpose: increases an object's scale
	/////////////////////////////////////////////
   public void increaseScaleFactor(){
		scaleFactor += 0.1;
	}
	
	/////////////////////////////////////////////
	// function: decreaseScaleFactor
	// purpose: decreases an object's scale
	/////////////////////////////////////////////
	public void decreaseScaleFactor(){
		if(scaleFactor > 0)
			scaleFactor -= 0.1;
	}

	/////////////////////////////////////////////
	// function: resetScaleFactor
	// purpose: resets an object's scale
	/////////////////////////////////////////////
	public void resetScaleFactor(){
		scaleFactor = 1;
	}

	/////////////////////////////////////////////
	// function: resetTranslations
	// purpose: resets any translations performed
	//				on an object
	/////////////////////////////////////////////
	public void resetTranslations(){
		translation[0] = origin[0];
		translation[1] = origin[1];
		translation[2] = origin[2];
	}

	/////////////////////////////////////////////
	// function: resetRotations
	// purpose: resets any rotations performed
	//				on an object
	/////////////////////////////////////////////
	public void resetRotations(){
		rotation[0] = 0;
		rotation[1] = 0;
		rotation[2] = 0;
	}

	/////////////////////////////////////////////
	// function: setTranslation
	// purpose: translates an object
	/////////////////////////////////////////////
	public void setTranslation(double X, double Y, double Z){
		translation[0] += X;
		translation[1] += Y;
		translation[2] += Z;
	}

	/////////////////////////////////////////////
	// function: setRotation
	// purpose: rotates an object on the specified
	//				axis
	/////////////////////////////////////////////
	public void setRotation(int direction){

		switch(direction) {
		case 1:
			rotation[2] -= rc;
			break;
		case 3:
			rotation[2] += rc;
			break;
		case 4:
			rotation[1] -= rc;
			break;
		case 6:
			rotation[1] += rc;
			break;
		case 7:
			rotation[0] -= rc;
			break;
		case 9:
			rotation[0] += rc;
			break;
		default:
			break;
		}
	}
}

/////////////////////////////////////////////
//	Class: Box
// Extends: GeometricPrimitive
// Implements: none
//
// Geometric primitive box class
/////////////////////////////////////////////
class Box extends GeometricPrimitive {
	static float boxCors[][] = { 
		{-0.5F, -0.5F, -0.5F},
		{0.5F, -0.5F, -0.5F},
		{0.5F,  0.5F, -0.5F},
		{-0.5F,  0.5F, -0.5F},
		{-0.5F, -0.5F,  0.5F},
		{0.5F, -0.5F,  0.5F},
		{0.5F,  0.5F,  0.5F},
		{-0.5F,  0.5F,  0.5F}
	};
	static int boxIndex[] = { 
		0, 1, 2, 3,	// Face 1
		4, 5, 6, 7,	// Face 2
		0, 1, 5, 4,	// Face 3
		2, 6, 7, 3,	// Face 4
		1, 2, 6, 5,	// Face 5
		0, 3, 7, 4};// Face 6

	/////////////////////////////////////////////
	// function: Box [constructor #1]
	// purpose: creates a Box object
	/////////////////////////////////////////////
	public Box(float Amb[], float Diff[], float Spec[], int X, int Y, int Z) {
		this(Amb, Diff, Spec);
		translation[0] = origin[0] = X;
		translation[1] = origin[1] = Y;
		translation[2] = origin[2] = Z;
	}

	/////////////////////////////////////////////
	// function: Box [constructor #2]
	// purpose: creates a Box object
	/////////////////////////////////////////////
	public Box(float Amb[], float Diff[], float Spec[]) {
		setAmbient(Amb[0],Amb[1],Amb[2], Amb[3]);
		setDiffuse(Diff[0],Diff[1],Diff[2], Diff[3]);
		setSpecular(Spec[0],Spec[1],Spec[2], Diff[3]);
	}

	/////////////////////////////////////////////
	// function: draw
	// purpose: responsible for drawing a box
	//				object on the canvas
	/////////////////////////////////////////////
   public void draw(JoglCanvas gl)  {
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, Amb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, Diff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, Spec);
      
		gl.pushMatrix();
		gl.translate(translation[0], translation[1], translation[2]);
		gl.scale(scaleFactor, scaleFactor, scaleFactor);
		gl.rotate(rotation[0],1,0,0);
		gl.rotate(rotation[1],0,1,0);
		gl.rotate(rotation[2],0,0,1);

		for(int i=0;i<6;i++ ) {	// For all faces
			gl.begin(GL.POLYGON);
			for(int j=0;j<4;j++) { // for each vertex
				vertexv(gl, boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
		gl.popMatrix();
	}

	/////////////////////////////////////////////
	// function: vertexv
	// purpose: this is a convenience function to
	//	         handle vextex*v function calls
	/////////////////////////////////////////////
	public void vertexv(JoglCanvas gl, float[] p) 
	{
		gl.vertex(p[0], p[1], p[2]);
	}
}

/////////////////////////////////////////////
// Class: Cone
// Extends: GeometricPrimitive
// Implements: none
//	
// Geometric cone primitive class
/////////////////////////////////////////////
class Cone extends GeometricPrimitive {

	Quadric glu;
	double baseRadius;
	double topRadius;
	double height;
	int slices;
   int stacks;


	/////////////////////////////////////////////
	// function: Cone [constructor #1]
	// purpose: creates a Cone object
	/////////////////////////////////////////////
	public Cone(float Amb[], float Diff[], float Spec[], int X, int Y, int Z){
		this(Amb, Diff, Spec);
		translation[0] = origin[0] = X;
		translation[1] = origin[1] = Y;
		translation[2] = origin[2] = Z;
	}

	/////////////////////////////////////////////
	// function: Cone [constructor #2]
	// purpose: creates a Cone object
	/////////////////////////////////////////////
	public Cone(float Amb[], float Diff[], float Spec[])
	{
		setAmbient(Amb[0],Amb[1],Amb[2], Amb[3]);
		setDiffuse(Diff[0],Diff[1],Diff[2], Diff[3]);
		setSpecular(Spec[0],Spec[1],Spec[2], Diff[3]);

		glu = new Quadric();
		baseRadius = 0.5;
		topRadius = 0.0;
		height = 1.0;
		slices = 16;
		stacks = 16;
	}


	/////////////////////////////////////////////
	// function: draw
	// purpose: responsible for drawing a cone
	//				object on the canvas
	/////////////////////////////////////////////
	public void draw(JoglCanvas gl){
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, Amb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, Diff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, Spec);
		
		gl.pushMatrix();
		gl.translate(translation[0], translation[1], translation[2]);
		gl.scale(scaleFactor, scaleFactor, scaleFactor);
		
		gl.rotate(rotation[0],1,0,0);
		gl.rotate(rotation[1],0,1,0);
		gl.rotate(rotation[2],0,0,1);
		glu.cylinder(topRadius, baseRadius, height, slices, stacks);
		gl.popMatrix();
	}
}

/////////////////////////////////////////////
// Class: Sphere
// Extends: GeometricPrimitive
// Implements: none
//
// Geometric primitive sphere class
/////////////////////////////////////////////
class Sphere extends GeometricPrimitive {

	Quadric glu;
	double radius;
	int slices;
	int stacks;

	/////////////////////////////////////////////
	// function: Sphere [constructor #1]
	// purpose: creates a Sphere object
	/////////////////////////////////////////////
	public Sphere(float Amb[], float Diff[], float Spec[], int X, int Y, int Z){
		this(Amb, Diff, Spec);
		translation[0] = origin[0] = X;
		translation[1] = origin[1] = Y;
		translation[2] = origin[2] = Z;
	}

	/////////////////////////////////////////////
	// function: Sphere [constructor #1]
	// purpose: creates a Sphere object
	/////////////////////////////////////////////
	public Sphere(float Amb[], float Diff[], float Spec[])
	{
		setAmbient(Amb[0],Amb[1],Amb[2], Amb[3]);
		setDiffuse(Diff[0],Diff[1],Diff[2], Diff[3]);
		setSpecular(Spec[0],Spec[1],Spec[2], Diff[3]);
		glu = new Quadric();
		radius = 0.5;
		slices = 16;
		stacks = 16;
	}

	/////////////////////////////////////////////
	// function: draw
	// purpose: responsible for drawing a sphere
	//				object on the canvas
	/////////////////////////////////////////////
	public void draw(JoglCanvas gl){
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, Amb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, Diff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, Spec);

		gl.pushMatrix();
		gl.translate(translation[0], translation[1], translation[2]);
		gl.scale(scaleFactor, scaleFactor, scaleFactor);
		gl.rotate(rotation[0],1,0,0);
		gl.rotate(rotation[1],0,1,0);
		gl.rotate(rotation[2],0,0,1);
		glu.sphere(radius, slices, stacks);
		gl.popMatrix();
	}
}

