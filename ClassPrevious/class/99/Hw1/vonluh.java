
///////////////////////////////////////////
//
// Advanced Computer Graphics
// Homework #1
//
// Heidi K. von Ludewig
// Feb. 8, 1999
//
// draws three objects which rotate independently of one another
// with changing viewpoint
// BOX - red, shiny
// CONE - blue, rough, 2 units to the right of BOX
// SPHERE - yellow, unspecified material, 2 units to the left of BOX
//
//
////////////////////////////////////////////


import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
//
//------------------------------------------
class Hw1 extends JoglCanvas implements KeyListener
{
	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;

	//
	// declare the object to stick in the canvas
	//
	Box heidiBox; 
	Cone heidiCone; 
	Sphere heidiSphere;

  //
  // Constructor for our simple class.
  // 
  //------------------------------------------
  public Hw1 ()
  {
	gl = this;
	glu = new Quadric();
	
	//
	// This is kind of funky, we are our own listener for key events
        //
	addKeyListener( this );
  }

  // This gets called by the constructor, hopefully only
  // once but it can be called at any time. Called prior to any
  // Window creation so it can only set variables, not draw things
  //------------------------------------------
  public void init()
  {
		
	float lightPos_0[] = {0.0F, 0.0F, 2.0F, 1.0F};
	float lightDir_0[] = {0.0F, 0.0F, 0.0F, 1.0F};
	float lightAmb_0[] = {0.7F, 0.7F, 0.7F, 0.0F};
	float lightDiff_0[] = {0.8F, 0.8F, 0.8F, 1.0F};
	float lightSpec_0[] = {0.4F, 0.4F, 0.4F, 1.0F};

	initLess = false;

	// initialize the widget 
	int width  = gl.getWidth();
	int height = gl.getHeight();
      
	// Initialize the rendering viewport size to OpenGL
	gl.viewport( 0, 0, width, height );
	gl.matrixMode( GL.PROJECTION );	
		
	// Set up the camera mode
	gl.loadIdentity();					

        //Reset the transformation matrix
	gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0); 
		
	// Set region of interest
 	gl.matrixMode( GL.MODELVIEW );		

	// Reset to model transforms
	gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
	gl.depthFunc( GL.LEQUAL );
	gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE,GL.MODULATE );

	gl.enable(GL.LIGHTING);
	gl.light(GL.LIGHT0, GL.POSITION, lightPos_0);
	gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir_0);
	gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb_0);
	gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff_0);
	gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec_0);
	gl.enable(GL.LIGHT0);


	//
	// added a light on the box to aid shiny behavior
	//
	float lightPos_1[] = {0.0F, 0.0F, 2.0F, 1.0F};
	float lightDir_1[] = {0.0F, 0.0F, 0.0F, 1.0F};
	float lightAmb_1[] = {0.7F, 0.7F, 0.7F, 0.0F};
	float lightDiff_1[] = {0.8F, 0.8F, 0.8F, 1.0F};
	float lightSpec_1[] = {0.4F, 0.4F, 0.4F, 1.0F};

	gl.light(GL.LIGHT1, GL.POSITION, lightPos_1);
	gl.light(GL.LIGHT1, GL.SPOT_DIRECTION, lightDir_1);
	gl.light(GL.LIGHT1, GL.AMBIENT, lightAmb_1);
	gl.light(GL.LIGHT1, GL.DIFFUSE, lightDiff_1);
	gl.light(GL.LIGHT1, GL.SPECULAR, lightSpec_1);
	gl.enable(GL.LIGHT1);


	gl.enable(GL.DEPTH_TEST);
	gl.shadeModel (GL.SMOOTH);
	gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
	gl.enable(GL.DITHER);

	heidiBox = new Box (this); 
	heidiCone = new Cone (this, glu); 
	heidiSphere = new Sphere (this, glu); 

  }


  // Sort of a legacy type function call, actually inside Java
  // This stuff should be in the paint call
  //------------------------------------------
  public void display()
  {
	
	gl.use();
	gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
	gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
		
	//
	//set up the "base" matrix by which to build everything else upon
	// 
	gl.translate(0,0,0);

	//
	// display all the objects
	//
	heidiBox.display();
	heidiCone.display();
	heidiSphere.display();

	//	
	// Make sure all commands have completed.
	// 
	gl.flush(); 


	//	
	// Swap the render buffer with the screen buffer
	// 
	gl.swap();	

  }
 

  // 
  //  Simple redirector to our display function
  // 
  //------------------------------------------
  public void paint(Graphics g)
  {

		// 
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class
		//
		super.paint(g);

		if (initLess) init();
		display();
  }

	
  // 
  // 
  //   
  //------------------------------------------
  public static void main( String args[] ) 
  {

	
	System.out.println(" ");
	System.out.println(" ");
	System.out.println("\tHomework #1 by H. K. von Ludewig");
	System.out.println("\tFeb. 1999 ");
	System.out.println(" ");
	System.out.println("\t\tUse the h,j,k,l keys to rotate viewpoint ");
	System.out.println("\t\tUse s key to rotate sphere ");
	System.out.println("\t\tUse c key to rotate cone ");
	System.out.println("\t\tUse b key to rotate box ");
	System.out.println("\t\tESC to quit ");
	System.out.println(" ");
	System.out.println(" ");
	System.out.println("\tPlease see README file for more information. ");
	System.out.println(" ");
	System.out.println(" ");
	System.out.println(" ");
	



	//
	// Create a new frame to hold the canvas and put it up.
	// 
	Frame fm = new Frame();

	Hw1 threeThings = new Hw1();

	// Make it visible and set size
	threeThings.setVisible(true);
	threeThings.setSize(400, 400);
	System.out.println("Super fun.....!");

	// Add the canvas to the frame and make it show
  	fm.add("Center", threeThings);
	fm.pack();
	fm.show();

  }


  // 
  //  Handle the keystrokes
  //   
  //------------------------------------------
  public void keyTyped(KeyEvent e) 
  {
    
	switch (e.getKeyChar()) 
	{
	case 'h':
		gl.matrixMode (GL.MODELVIEW);
                /* manipulate modelview matrix  */
		gl.rotate(15.0, 0.0,1.0,0.0);
		e.consume();
		break;
	
	case 'j':
		gl.matrixMode (GL.MODELVIEW);
                /* manipulatemodelview matrix  */
		gl.rotate(15.0, 1.0,0.0,0.0);
		e.consume();
		break;
	
	case 'k':
                gl.matrixMode (GL.MODELVIEW);     
                /* manipulatemodelview matrix  */
		gl.rotate(-15.0, 1.0,0.0,0.0);
		e.consume();
		break;

	case 'l':
                gl.matrixMode (GL.MODELVIEW);       
		gl.rotate(-15.0, 0.0,1.0,0.0);
		e.consume();
		break;

	case '+':
                gl.matrixMode (GL.PROJECTION);        
		gl.translate(0.0, 0.0,0.5);
		e.consume();
		break;

	case '-':
                gl.matrixMode (GL.PROJECTION);        
		gl.translate(0.0, 0.0,-0.5);
		e.consume();
		break;

	case 'b':			
		gl.matrixMode(GL.MODELVIEW);	
		heidiBox.ROTATE = true; 
		e.consume();
		break; 

	case 'c':	
		gl.matrixMode(GL.MODELVIEW);
		heidiCone.ROTATE = true; 
		e.consume();
		break; 

	case 's':
		gl.matrixMode(GL.MODELVIEW);
		heidiSphere.ROTATE = true; 
		e.consume();
		break; 


	case 27:   /* Esc will quit */
		System.exit(1);
		break;

	default:
		break;
	}

	// 
	// now display changes created from keystrokes
	//
	display();

  }



  // 
  // 
  //   
  //------------------------------------------
  public void keyPressed(KeyEvent e) 
  {
  }



  // 
  // 
  //   
  //------------------------------------------
  public void keyReleased(KeyEvent e) 
  {
  }


  // 
  // Convenience function to handle vertex*v call in OpenGL 
  //   
  //------------------------------------------
  public void vertexv(float[] p) 
  {
	gl.vertex(p[0], p[1], p[2]);
  }


}


//*************************************************************
//*************************************************************
// box class
// box should be red with a shiny surface. 
//
// ----------------------------------------

class Box extends JoglCanvas
{

	JoglCanvas gl;

  	//
  	// some data members
  	// 
  	boolean initLess = true;
	boolean ROTATE = false; 

	int rotationCount = 0; 

	int angleDelta = 15;
	int XPosDelta = 5;
	int YPosDelta = 5;
	int ZPosDelta = 5; 	
	
	int angle = 0;
	int XPos = 0;
	int YPos= 0;
	int ZPos= 0; 	
	

	//
 	// set up box basics
 	//
	//float boxAmb[] = {0.8F, 0.0F, 0.0F, 0.0F};
	//float boxDiff[] = {0.1F, 0.1F, 0.1F, 0.1F};
	//float boxSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};
	float boxCors[][] = { {-0.5F, -0.5F, -0.5F},
                              {0.5F, -0.5F,-0.5F},
                              {0.5F,  0.5F,-0.5F},
                              {-0.5F,  0.5F,-0.5F},
                              {-0.5F, -0.5F,0.5F},
                              {0.5F, -0.5F,0.5F},
                              {0.5F,  0.5F,0.5F},
                              {-0.5F,  0.5F,0.5F} };


	float boxAmb[] = {1.0F, 0F, 0F, 0.0F};
	float boxDiff[] = {0.0F, 0.0F, 1.0F, 0.8F};


	int  boxIndex[] = { 0, 1, 2, 3,    // Face 1
                            4, 5, 6, 7,    // Face 2
                            0, 1, 5, 4,    // Face 3
                            2, 6, 7, 3,    // Face 4
                            1, 2, 6, 5,    // Face 5
                            0, 3, 7, 4 };  // Face 6

  // 
  // 
  // 
  //------------------------------------------
  public Box(Hw1 mainGL)
  {
	gl = mainGL; 
  }



  // 
  // calculates the rotation parameters for 
  // the object. 
  // 
  //------------------------------------------
  public void calcRotation()
  {

	rotationCount = rotationCount +1; 
	angle = rotationCount * angleDelta;
	XPos = rotationCount * XPosDelta;
	YPos = rotationCount * YPosDelta;
	ZPos = rotationCount * ZPosDelta; 	
	
  }



  // This gets called by the constructor, hopefully only
  // once but it can be called at any time. Called prior to any
  // Window creation so it can only set variables, not draw things
  //------------------------------------------
  public void init()
  {

	//
        // to initialize more than once
	//
        initLess = false;


  }
	


	
  // Sort of a legacy type function call, actually inside Java
  // This stuff should be in the paint call
  // 
  //------------------------------------------
  public void display() 
  {
	gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
	gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
	gl.material(GL.FRONT_AND_BACK, GL.SHININESS, 128);

	if (ROTATE)
	{
		calcRotation();
	}

	gl.pushMatrix();
	gl.rotate(angle, XPos, YPos, ZPos);

	// Lets draw a box
	// 
	for (int i=0;i<6;i++ ) 
	{ 
		// For all faces
		// 
		gl.begin(GL.POLYGON);
		for (int j=0;j<4;j++) 
		{ 
			// for each vertex
			//Added this as a convenience
			// 
			vertexv(boxCors[boxIndex[(i*4)+j]]);
		}

	gl.end();
	}
	
	ROTATE = false;		
	
	gl.popMatrix();

	//gl.flush(); // Make sure all commands have completed.
	//gl.swap();	 // Swap the render buffer with the screen buffer
  }



  // 
  // simply redirecting our display function
  // 
  //------------------------------------------

  public void paint(Graphics g)
  {
	
	//
	// First call the base class paint method to do a one time
	// Initialization - specific to the JoglCanvas class
	//
	//
	// XXX don't seem to need this
	//super.paint(g);

	if (initLess) init();
	display();
  }



  // 
  // Convenience function to handle vertex*v call in OpenGL 
  // 
  //------------------------------------------
	
  public void vertexv(float[] p) 
  {
	gl.vertex(p[0], p[1], p[2]);
  }

}




//*************************************************************
//*************************************************************
// cone class. 
//
//
// ----------------------------------------

class Cone extends JoglCanvas
{

	JoglCanvas gl;
	Quadric glu;

  	//
  	// some data members
  	// 
  	boolean initLess = true;
	float no_shine [] = {0}; 

	boolean ROTATE = false; 

	int rotationCount = 0; 

	int angleDelta = 15;
	
	int angle = 0;
	int XPos = 0;
	int YPos= 0;
	int ZPos= 1; 	

	//
 	// set up cone basics
 	//
	float coneAmb[] = {0F, 0F, 1.0F, 0.0F};
	float coneDiff[] = {0.0F, 0.0F, 0.0F, 0.8F};


	//float coneAmb[] = {0.0F, 0.0F, 1.0F, 0.0F};
	//float coneDiff[] = {0.1F, 0.1F, 0.1F, 0.1F};
	//float coneSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};

  // 
  // 
  // 
  //------------------------------------------
  public Cone(Hw1 mainGL, Quadric mainGLU)
  {
	
	gl = mainGL; 
	glu = mainGLU; 
  }



  // This gets called by the constructor, hopefully only
  // once but it can be called at any time. Called prior to any
  // Window creation so it can only set variables, not draw things
  //------------------------------------------
  public void init()
  {

	//
        // to initialize more than once
	//
        initLess = false;


  }
	


  // 
  // calculates the rotation parameters for 
  // the object. 
  // 
  //------------------------------------------
  public void calcRotation()
  {

	rotationCount = rotationCount +1; 
	angle = rotationCount * angleDelta;
	
  }


	
  // Sort of a legacy type function call, actually inside Java
  // This stuff should be in the paint call
  // 
  //------------------------------------------
  public void display() 
  {

	
	//gl.color(0.0, 0.0, 1.0);
	gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, coneAmb);
	//gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, coneDiff);
	gl.material(GL.FRONT_AND_BACK, GL.SHININESS, no_shine);


	//gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, coneAmb);
	//gl.material(GL.FRONT, GL.DIFFUSE, coneDiff);
	//gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, coneSpec);
	//gl.material(GL.FRONT_AND_BACK, GL.SHININESS, 128);

	if (ROTATE)
	{
		calcRotation();
	}
		gl.pushMatrix();
		gl.translate(2, 0, -0.5);
		gl.rotate(angle, XPos, YPos, ZPos);		
		
	glu.cylinder(0.0, 0.5, 1, 16, 16);
	gl.popMatrix();
	
	ROTATE = false;
  }




  // 
  // simply redirecting our display function
  // 
  //------------------------------------------

  public void paint(Graphics g)
  {
	
	//
	// First call the base class paint method to do a one time
	// Initialization - specific to the JoglCanvas class
	//
	// XXX don't seem to need this
	//super.paint(g);

	if (initLess) init();
	display();
  }



  // 
  // Convenience function to handle vertex*v call in OpenGL 
  // 
  //------------------------------------------
	
  public void vertexv(float[] p) 
  {
	gl.vertex(p[0], p[1], p[2]);
  }

}





//*************************************************************
//*************************************************************
// sphere class. 
//
//
// ----------------------------------------

class Sphere extends JoglCanvas
{

	JoglCanvas gl;
	Quadric glu;

  	//
  	// some data members
  	// 
  	boolean initLess = true;

	boolean ROTATE = false; 

	int rotationCount = 0; 

	int angleDelta = 45;
	//int XPosDelta = 25;
	int YPosDelta = 25;
	int ZPosDelta = 25; 	
	
	int angle = 0;
	int XPos = 0;
	int YPos= 0;
	int ZPos= 0; 	

	//
 	// set up sphere basics
 	//
	float sphAmb[] = {0.99F, 0.99F, 0.0F, 0.8F};


  // 
  // 
  // 
  //------------------------------------------
  public Sphere(Hw1 mainGL, Quadric mainGLU)
  {
	gl = mainGL; 
	glu = mainGLU; 
  }


  // 
  // This gets called by the constructor, hopefully only
  // once but it can be called at any time. Called prior to any
  // Window creation so it can only set variables, not draw things
  //------------------------------------------
  public void init()
  {
	//
        // to initialize more than once
	//
        initLess = false;
  }


	
  // 
  // calculates the rotation parameters for 
  // the object. 
  // 
  //------------------------------------------
  public void calcRotation()
  {
	rotationCount = rotationCount +1; 
	angle = rotationCount * angleDelta;
	//XPos = rotationCount * XPosDelta;
	YPos = rotationCount * YPosDelta;
	ZPos = rotationCount * ZPosDelta; 
  }



  //
  // Sort of a legacy type function call, actually inside Java
  // This stuff should be in the paint call
  // 
  //------------------------------------------
  public void display() 
  {
	// add a sphere.
	gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);

	if (ROTATE)
	{
		calcRotation();
	}
		
	gl.pushMatrix();
	gl.translate(-2, 0, 0);
	gl.rotate(angle, XPos, YPos, ZPos);

	glu.sphere(0.5, 40, 40);

	gl.popMatrix();
	ROTATE = false;

  }




  // 
  // simply redirecting our display function
  // 
  //------------------------------------------

  public void paint(Graphics g)
  {
	//
	// First call the base class paint method to do a one time
	// Initialization - specific to the JoglCanvas class
	//
	//
	// XXX don't seem to need this
	//super.paint(g);

	if (initLess) init();
	display();
  }



  // 
  // Convenience function to handle vertex*v call in OpenGL 
  // 
  //------------------------------------------
	
  public void vertexv(float[] p) 
  {
	gl.vertex(p[0], p[1], p[2]);
  }

}