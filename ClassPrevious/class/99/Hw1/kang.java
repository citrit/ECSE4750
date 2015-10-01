/////////////////////////////////////////////////////////////////////
//
//		Advanced Computer Graphics and Data Visualization
//		                 Homework #1
//
/////////////////////////////////////////////////////////////////////
//
//		Hyosig Kang
//      Feb. 5.1999
//
/////////////////////////////////////////////////////////////////////


import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;

class Hw1 extends JoglCanvas implements KeyListener,ActionListener
{
	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;

	
	private static int movingObject;
	private static int movingMode;
	private static int bufferMode;
	private static int movingAxis;
	private static double movingIncForRot=2.0;
	private static double movingIncForMov=0.1;
	private static double movingIncSign;
	private static double scaleFactor[] = {1.0,1.0,1.0};
	private static double scaleSign;
	private static double rot[][] = new double[3][3];
	private static double mov[][] = new double[3][3];
	private static double originObject[] = {-2.0, 0.0, 2.0};
	 
	private static String objectNames[] =
		{"Cone", "Cube", "Sphere", "All"};
	private static String moveNames[] =
		{"Rotate", "Translate"};
	private static String bufferNames[] =
		{"Double", "Single"};
	private static MenuBar bar;
	private static Menu viewMenu, selectMenu, moveMenu, bufferMenu;
	private static MenuItem objects[], moves[], buffers[];

	float no_mat[] = {0.0F, 0.0F, 0.0F, 1.0F};

	float lightPos[] = {0.0F, 0.0F, 1.0F, 1.0F};
	float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
	float lightAmb[] = {0.5F, 0.5F, 0.5F, 1.0F};
	float lightDiff[] = {1.0F, 1.0F, 1.0F, 1.0F};
	float lightSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};

	float conAmb[] =  {0.0F, 0.0F,  1.0F, 1.0F};
	float conDiff[] = {0.1F, 0.1F,  0.7F, 1.0F};
	float conSpec[] = {1.0F, 1.0F,  1.0F, 1.0F};
	float conShin[] = {0.0F};

	float cubAmb[] =  {1.0F, 0.0F, 0.0F, 1.0F};
	float cubDiff[] = {0.5F, 0.1F, 0.1F, 1.0F};
	float cubSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};
	float cubShin[] = {120.0F};

	float sphAmb[] =  {1.0F, 1.0F, 0.0F, .3F};
	float sphDiff[] = {0.5F, 0.5F, 0.1F, .3F};
	float sphSpec[] = {1.0F, 1.0F, 1.0F, .3F};
	float sphShin[] = {5.0F};

	float cubCors[][] = { {-0.5F, -0.5F, -0.5F},
						{0.5F, -0.5F, -0.5F},
						{0.5F,  0.5F, -0.5F},
						{-0.5F,  0.5F, -0.5F},
						{-0.5F, -0.5F,  0.5F},
						{0.5F, -0.5F,  0.5F},
						{0.5F,  0.5F,  0.5F},
						{-0.5F,  0.5F,  0.5F} };
	int  cubIndex[] = { 0, 1, 2, 3, // Face 1
						4, 5, 6, 7, // Face 2
						0, 1, 5, 4, // Face 3
						2, 6, 7, 3, // Face 4
						1, 2, 6, 5, // Face 5
						0, 3, 7, 4 };  // Face 6

	
	// Constructor for our simple class.
	public Hw1()
	{
		gl = this;
		glu = new Quadric();
		// This is kind of funky, we are our own listener for key events
		addKeyListener( this );
	}

	// This gets called by the constructor, hopefully only
	// once but it can be called at any time. Called prior to any
	// Window creation so it can only set variables, not draw things
	public void init()
    {

		initLess = false;
		/* initialize the widget */
		int width  = gl.getWidth();
		int height = gl.getHeight();
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.matrixMode( GL.PROJECTION );	// Set up the camera mode
		gl.loadIdentity();					// Reset the transformation matrix
		gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0); // Set region of interest
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms

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
		
		gl.newList(1, GL.COMPILE);
	    gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, conAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, no_mat);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, no_mat);
		gl.material(GL.FRONT_AND_BACK, GL.SHININESS, no_mat);		
		glu.cylinder(0.0, 0.5, 1, 16, 16);
		gl.endList();

		gl.newList(2, GL.COMPILE);
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, cubAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, cubDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, cubSpec);
		gl.material(GL.FRONT_AND_BACK, GL.SHININESS, cubShin);		
	 	for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vertexv(cubCors[cubIndex[(i*4)+j]]);
			}
			gl.end();
		}
		gl.endList();

		gl.newList(3, GL.COMPILE);
	    gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, sphDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);
		gl.material(GL.FRONT_AND_BACK, GL.SHININESS, sphShin);		
		glu.sphere(0.5,16,16);
		gl.endList();

    }


	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
    public void display() 
	{

		if(bufferMode == 0) gl.drawBuffer(GL.BACK);
		if(bufferMode == 1) gl.drawBuffer(GL.FRONT);

		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
	
		for(int i=0;i<3;i++)
		{
    		gl.pushMatrix();
			gl.translate(originObject[i]+mov[i][0], mov[i][1], mov[i][2]);
			gl.rotate(rot[i][0],1.0,0.0,0.0);
			gl.rotate(rot[i][1],0.0,1.0,0.0);
			gl.rotate(rot[i][2],0.0,0.0,1.0);
			gl.scale(scaleFactor[i],scaleFactor[i],scaleFactor[i]);
			gl.callList(i+1);
			gl.popMatrix();
		}
		gl.flush(); // Make sure all commands have completed.
		if(bufferMode == 0)	gl.swap();	 // Swap the render buffer with the screen buffer
	}

	// Simple redirector to our display function
	public void paint(Graphics g)
	{
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		super.paint(g);
		//if (initLess) init();
		init();
		display();
	}

	// This is where things get started
	public static void main( String args[] ) 
    {
		// Create a new frame to hold the canvas and put it up.
		Frame fm = new Frame();
		Hw1 b = new Hw1();

		// Make it visible and set size
		b.setVisible(true);
		b.setSize(300, 300);

	    bar = new MenuBar();
		viewMenu = new Menu("Move");
		selectMenu = new Menu("Select");
		objects = new MenuItem[objectNames.length];
		for(int i=0;i<objects.length;i++){
			objects[i] = new MenuItem(objectNames[i]);
			selectMenu.add(objects[i]);
			objects[i].addActionListener(b);
		}

		viewMenu.add(selectMenu);
		viewMenu.addSeparator();

		moveMenu = new Menu("Mode");
		moves = new MenuItem[moveNames.length];
		for(int i=0;i<moves.length;i++){
			moves[i] = new MenuItem(moveNames[i]);
			moveMenu.add(moves[i]);
			moves[i].addActionListener(b);
		}
		viewMenu.add(moveMenu);
		viewMenu.addSeparator();		
		bufferMenu = new Menu("Buffer");
		buffers = new MenuItem[bufferNames.length];
		for(int i=0;i<buffers.length;i++){
			buffers[i] = new MenuItem(bufferNames[i]);
			bufferMenu.add(buffers[i]);
			buffers[i].addActionListener(b);
		}
		viewMenu.add(bufferMenu);

		bar.add(viewMenu);
		fm.setMenuBar(bar);

		// Add the canvas to the frame and make it show
  		fm.add("Center", b);
		fm.pack();
		fm.show();
		DisplayKeyFunction();
    }
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<objects.length;i++)
			if(e.getSource() == objects[i]) {
				movingObject = i;
				System.out.println(objectNames[i] +" is selected ");
				break;
			}
		for(int i=0;i<moves.length;i++)
			if(e.getSource() == moves[i]) {
				movingMode = i;
				System.out.println(moveNames[i] +" is selected ");
				break;
			}
		for(int i=0;i<buffers.length;i++)
			if(e.getSource() == buffers[i]) {
				bufferMode = i;
				System.out.println(bufferNames[i] +" is selected ");
				break;
			}
	}

	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {
    
		switch (e.getKeyChar()) {
		case 'y':
			movingAxis = 0; movingIncSign=1.0;
			DoMoveObject();
			e.consume();
			break;
		case 'u':
			movingAxis = 0; movingIncSign=-1.0;
			DoMoveObject();
			e.consume();
			break;
		case 'h':
			movingAxis = 1; movingIncSign=1.0;
			DoMoveObject();
			e.consume();
			break;
		case 'j':
			movingAxis = 1; movingIncSign=-1.0;
			DoMoveObject();
			e.consume();
			break;
		case 'n':
			movingAxis = 2; movingIncSign=1.0;
			DoMoveObject();
			e.consume();
			break;
		case 'm':
			movingAxis = 2; movingIncSign=-1.0;
			DoMoveObject();
			e.consume();
			break;
		case '+':
			scaleSign = 1.0;
			DoScaleObject();
			e.consume();
			break;
		case '-':
			scaleSign = -1.0;
			DoScaleObject();
			e.consume();
			break;

		case 'r':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			if(movingMode == 0)		gl.rotate(2.0,1.0,0.0,0.0);
			else					gl.translate(0.1, 0.0,0.0);
			e.consume();
			break;
		case 't':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			if(movingMode == 0)		gl.rotate(-2.0,1.0,0.0,0.0);
			else					gl.translate(-0.1, 0.0,0.0);
			e.consume();
			break;
		case 'f':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			if(movingMode == 0)		gl.rotate(2.0,0.0,1.0,0.0);
			else					gl.translate(0.0,0.1,0.0);
			e.consume();
			break;
		case 'g':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			if(movingMode == 0)		gl.rotate(-2.0,0.0,1.0,0.0);
			else					gl.translate(0.0,-0.1,0.0);
			e.consume();
			break;
		case 'v':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			if(movingMode == 0)		gl.rotate(2.0,0.0,0.0,1.0);
			else					gl.translate(0.0,0.0,0.1);
			e.consume();
			break;
		case 'b':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			if(movingMode == 0)		gl.rotate(-2.0,0.0,0.0,1.0);
			else					gl.translate(0.0,0.0,-0.1);
			e.consume();
			break;
		case 27:           /* Esc will quit */
			System.exit(1);
			break;
		default:
			break;
		}
		display();
	}
	public void DoMoveObject()
	{
			if(movingMode == 0)
				if(movingObject <=2)
					rot[movingObject][movingAxis] = rot[movingObject][movingAxis] + movingIncForRot*movingIncSign; 
				else
					for(int i=0;i<3;i++)
						rot[i][movingAxis] = rot[i][movingAxis] + movingIncForRot*movingIncSign; 
			else
			if(movingObject <=2)
					mov[movingObject][movingAxis] = mov[movingObject][movingAxis] + movingIncForMov*movingIncSign; 
				else
					for(int i=0;i<3;i++)
						mov[i][movingAxis] = mov[i][movingAxis] + movingIncForMov*movingIncSign; 	

	}
	public void DoScaleObject()
	{
			if(movingObject <=2)
				scaleFactor[movingObject] = scaleFactor[movingObject]+0.1*scaleSign; 
			else
				for(int i=0;i<3;i++)
					scaleFactor[i] = scaleFactor[i]+0.1*scaleSign; 

	}

	public void keyPressed(KeyEvent e) {
 
	}
	public void keyReleased(KeyEvent e) {

	}
	
	public static void DisplayKeyFunction()
	{
			System.out.println("___________________________________________________");
			System.out.println(" ");
			System.out.println("Advanced Computer Graphics and Data Visualization");
			System.out.println("               Home Work #1");
			System.out.println("              by Hyosig Kang");
			System.out.println("___________________________________________________");

			System.out.println(" ");
			System.out.println("	Description of Key Function :");
			System.out.println("		For Changing the projection view");
			System.out.println("			r:x(+) t:x(-)");
			System.out.println("			f:y(+) g:y(-)");
			System.out.println("			v:z(+) b:z(-)");
			System.out.println("		For Changing the model view");
			System.out.println("			y:x(+) u:x(-)");
			System.out.println("			h:y(+) j:y(-)");
			System.out.println("			n:z(+) m:z(-)");
			System.out.println("		For Changing the Scale");
			System.out.println("			+:scale up  -:scale down");
			System.out.println("		For Exit, press ESC key");
			System.out.println("	(Initial : Cone, rotate, double buffer)");
			System.out.println("___________________________________________________");
			System.out.println(" ");
			
	}

	/** Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(float[] p) 
	{
		gl.vertex(p[0], p[1], p[2]);
	}
}

