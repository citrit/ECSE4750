/*
 *	Homework Assignment One
 *
 *	Name: Shuo Zhao
 *  
 *	2/8/1999
 * 
 *	Instructions about how to use this program
 * 
 *	Features Included: Three objects with the specified location and surface
 *					   Rotate each object individually
 *					   Change camera point
 *					   Move each object individully
 *					   Scale each object individully
 *					   Pop-up menu for selecting objects
 *
 *	To select object to manipulate or the camera to change point, right mouse click
 *	on the scree, this will bring up a pop-up menu. (right mouse click at the lower
 *	right hand corner is most likely to bring up the pop-up menu). Move mouse to choose
 *  an object or the camera to manipulate.
 *  
 *  After choosing object, use key board to do the specific action.
 *	'h':	rotate the y-axis about 15-degree
 *  'l':    rotate the y-axis about -15-degree
 *  'j':    rotate the x-axis about 15-degree
 *  'k':    rotate the x-axis about -15-degree
 *	'^':    move toward the positive direction of y-axis
 *	'v':    move toward the negative direction of y-axis
 *  '>':    move toward the positive direction of x-axis
 *  '<':    move toward the negative direction of x-axis
 *	'+':    scale each object
 *  '-':    scale each object
 */

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

// Lets create a class that extends the JoglCanvas class
// which holds all the OpenGL calls and structures.
class Hw1 extends JoglCanvas implements KeyListener, ActionListener
{
	JoglCanvas gl;
	Quadric  glu; // From the glu package
	boolean initLess = true;
	double ri[] = new double[4];            //Array for the rotate coordination of each object
	double move[] = new double[4];
	float scale;
	
	//The rotate array contains the rotation information about each oject
	//the first two elements contains the rotation angles about xyz, and the rest three
	//elements contains the xyz coordination values
	double cube_rotate[] = {0.0, 0.0, 0.0, 0.0, 0.0};	//Stores the state information for cube 
	int cube_move[] = {0, 0, 0};	//Stores xyz value for move of cube
	float cube_scale = 0.0F;

	double cone_rotate[] = {0.0, 0.0, 0.0, 0.0, 0.0};    //Stores the state information for cone
	int cone_move[] = {0, 0, 0};	//Stores xyz value for move of cone
	float cone_scale = 0.0F;

	double sphere_rotate[] = {0.0, 0.0, 0.0, 0.0, 0.0};  //Stores the state information for sphere
	int sphere_move[] = {0, 0, 0};	//Stores xyz value for move of sphere
	float sphere_scale = 0.0F;

	JCheckBoxMenuItem  camera,cube, cone, sphere;
	JPopupMenu popup;

	boolean it_move = false;
	boolean it_rotate = false;
	boolean it_scale = false;
	boolean first_time_sphere = true;
	boolean first_time_cone = true;

	// Constructor for the Hw1 class.
	public Hw1 ()
	{
		gl = this;
		glu = new Quadric();
	

		// This is kind of funky, we are our own listener for key and mouse events
		addKeyListener( this );
	
		popup = new JPopupMenu();
		camera = new JCheckBoxMenuItem("Camera");
		camera.addActionListener(this);
		popup.add(camera);
		cube = new JCheckBoxMenuItem("Cube");
		cube.addActionListener(this);
		popup.add(cube);
		cone = new JCheckBoxMenuItem("Cone");
		cone.addActionListener(this);
		popup.add(cone);
		sphere = new JCheckBoxMenuItem("Sphere");
		sphere.addActionListener(this);
		popup.add(sphere);
		
		MouseListener popupListener = new PopupListener();

		gl.addMouseListener(popupListener);
		
	}

	// This gets called by the constructor, hopefully only
	// once but it can be called at any time. Called prior to any
	// Window creation so it can only set variables, not draw things
	public void init()
    {
		float lightPos[] = {1.0F, 1.0F, 1.0F, 1.0F};
		float lightDir[] = {1.0F, 1.0F, 1.0F, 1.0F};
		float lightAmb[] = {1.0F, 1.0F, 1.0F, 1.0F};
		float lightDiff[] = {1.0F, 1.0F, 1.0F, 1.0F};
		float lightSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};

		initLess = false;
		/* initialize the widget */
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

//		gl.color(1.0, 0.0, 0.0);
		gl.enable(GL.LIGHTING);
		gl.enable(GL.LIGHT3);
		gl.light(GL.LIGHT3, GL.POSITION, lightPos);
		gl.light(GL.LIGHT3, GL.SPOT_DIRECTION, lightDir);
		gl.light(GL.LIGHT3, GL.AMBIENT, lightAmb);
		gl.light(GL.LIGHT3, GL.DIFFUSE, lightDiff);
		gl.light(GL.LIGHT3, GL.SPECULAR, lightSpec);

		gl.enable(GL.DEPTH_TEST);
		gl.shadeModel (GL.SMOOTH);
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
		gl.enable(GL.DITHER);
    }

	// Sort of a legacy type function call, actually inside Java
	// This stuff should be in the paint call
    public void display() {

		float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};
		float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
		float noMat[] = {0.0F, 0.0F, 0.0F, 0.0F};
		float conAmb[] = {0.0F, 0.0F, 0.9F, 1.0F};
		float conDiff[] = { 0.1F, 0.5F, 0.8F, 1.0F };
		float conSpec[] = { 1.0F, 1.0F, 1.0F, 1.0F };
		float boxDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
		float boxSpec[] = {1.0F, 1.0F, 1.0F, 1.0F};
		float boxCors[][] = { {-0.5F, -0.5F, -0.5F},
							{0.5F, -0.5F, -0.5F},
							{0.5F,  0.5F, -0.5F},
							{-0.5F,  0.5F, -0.5F},
							{-0.5F, -0.5F,  0.5F},
							{0.5F, -0.5F,  0.5F},
							{0.5F,  0.5F,  0.5F},
							{-0.5F,  0.5F,  0.5F} };
		int  boxIndex[] = { 0, 1, 2, 3, // Face 1
							4, 5, 6, 7, // Face 2
							0, 1, 5, 4, // Face 3
							2, 6, 7, 3, // Face 4
							1, 2, 6, 5, // Face 5
							0, 3, 7, 4 };  // Face 6

		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);
		gl.pushMatrix();
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);
		gl.material(GL.FRONT_AND_BACK, GL.SHININESS, 3);

		// Lets draw a box
		
		if(cube.getState())
		{
			if(it_move)
			{
				gl.translate(cube_move[0]+move[0], cube_move[1]+move[1], cube_move[2]+move[2]);
				if(cube_scale!=0.0F)
					gl.scale(cube_scale, cube_scale, cube_scale);
				gl.rotate(cube_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(cube_rotate[1], 0.0, 1.0, 0.0);
				for(int i=0; i<3; i++)
					cube_move[i]=cube_move[i]+(int)move[i];
				it_move=false;
			}
			if(it_scale)
			{
				gl.translate(cube_move[0]+move[0], cube_move[1]+move[1], cube_move[2]+move[2]);
				cube_scale += scale;
				gl.scale(cube_scale, cube_scale, cube_scale);
				gl.rotate(cube_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(cube_rotate[1], 0.0, 1.0, 0.0);
				it_scale=false;
			}
			else
			{
				if(it_rotate)
				{
					if(ri[1]==1.0 && cube_rotate[2]!=1.0)
					{
						cube_rotate[2]=1.0;
						cube_rotate[3]=0.0;
						cube_rotate[0]=cube_rotate[0]+ri[0];
					}
					if(ri[2]==1.0 && cube_rotate[3]!=1.0)
					{
						cube_rotate[2]=0.0;
						cube_rotate[3]=1.0;
						cube_rotate[1]=cube_rotate[1]+ri[0];
					}
			
					if(ri[1]==1.0 && cube_rotate[2]==1.0)
						cube_rotate[0]=cube_rotate[0]+ri[0];
					if(ri[2]==1.0 && cube_rotate[3]==1.0)
						cube_rotate[1]=cube_rotate[1]+ri[0];
					it_rotate=false;
				}
				gl.translate(cube_move[0], cube_move[1], cube_move[2]);
				if(cube_scale!=0.0F)
					gl.scale(cube_scale, cube_scale, cube_scale);
				gl.rotate(cube_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(cube_rotate[1], 0.0, 1.0, 0.0);		
			}
		}
		else
		{
			gl.translate(cube_move[0], cube_move[1], cube_move[2]);
			if(cube_scale!=0.0F)
				gl.scale(cube_scale, cube_scale, cube_scale);
			gl.rotate(cube_rotate[0], 1.0, 0.0, 0.0);
			gl.rotate(cube_rotate[1], 0.0, 1.0, 0.0);
		}

		for (int i=0;i<6;i++ ) { // For all faces
			gl.begin(GL.POLYGON);
			for (int j=0;j<4;j++) { // for each vertex
				//Added this as a convenience
				vertexv(boxCors[boxIndex[(i*4)+j]]);
			}
			gl.end();
		}
		gl.popMatrix();
/*----------------------------------------------------------------------------------*/
		
		// add a sphere.
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.pushMatrix();
		if(first_time_sphere)
		{
			gl.translate(-2, 0, 0);
		}

		if(sphere.getState())
		{
			if(it_move)
			{
				gl.translate(sphere_move[0]+move[0], sphere_move[1]+move[1], sphere_move[2]+move[2]);
				if(sphere_scale!=0.0F)
					gl.scale(sphere_scale, sphere_scale, sphere_scale);
				gl.rotate(sphere_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(sphere_rotate[1], 0.0, 1.0, 0.0);
				for(int i=0; i<3; i++)
					sphere_move[i]=sphere_move[i]+(int)move[i];
				it_move=false;
				first_time_sphere = false;
			}
			if(it_scale)
			{
				gl.translate(sphere_move[0]+move[0], sphere_move[1]+move[1], sphere_move[2]+move[2]);
				sphere_scale += scale;
				gl.scale(sphere_scale, sphere_scale, sphere_scale);
				gl.rotate(sphere_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(sphere_rotate[1], 0.0, 1.0, 0.0);
				it_scale=false;
			}
			else
			{
				if(it_rotate)
				{
					if(ri[1]==1.0 && sphere_rotate[2]!=1.0)
					{
						sphere_rotate[2]=1.0;
						sphere_rotate[3]=0.0;
						sphere_rotate[0]=sphere_rotate[0]+ri[0];
					}
					if(ri[2]==1.0 && sphere_rotate[3]!=1.0)
					{
						sphere_rotate[2]=0.0;
						sphere_rotate[3]=1.0;
						sphere_rotate[1]=sphere_rotate[1]+ri[0];
					}
			
					if(ri[1]==1.0 && sphere_rotate[2]==1.0)
						sphere_rotate[0]=sphere_rotate[0]+ri[0];
					if(ri[2]==1.0 && cube_rotate[3]==1.0)
						sphere_rotate[1]=sphere_rotate[1]+ri[0];
					it_rotate=false;
				}
				gl.translate(sphere_move[0], sphere_move[1], sphere_move[2]);
				if(sphere_scale != 0.0F)
					gl.scale(sphere_scale, sphere_scale, sphere_scale);
				gl.rotate(sphere_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(sphere_rotate[1], 0.0, 1.0, 0.0);	
			}
		}
		else
		{
			gl.translate(sphere_move[0], sphere_move[1], sphere_move[2]);
			if(sphere_scale != 0.0F)
				gl.scale(sphere_scale, sphere_scale, sphere_scale);
			gl.rotate(sphere_rotate[0], 1.0, 0.0, 0.0);
			gl.rotate(sphere_rotate[1], 0.0, 1.0, 0.0);	
		}

		glu.sphere(0.6, 16, 16);
		gl.popMatrix();

/*----------------------------------------------------------------------------------------*/

		// add a cone
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, conAmb);
		gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, conDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, noMat);
		gl.material(GL.FRONT_AND_BACK, GL.SHININESS, 0);
		gl.material(GL.FRONT_AND_BACK, GL.EMISSION, noMat);
		
		gl.pushMatrix();
		if(first_time_cone)
		{
			gl.translate(2,0,0);
		}

		if(cone.getState())
		{
			if(it_move)
			{
				gl.translate(cone_move[0]+move[0], cone_move[1]+move[1], cone_move[2]+move[2]);
				if(cone_scale!=0.0F)
					gl.scale(cone_scale, cone_scale, cone_scale);
				gl.rotate(cone_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(cone_rotate[1], 0.0, 1.0, 0.0);
				for(int i=0; i<3; i++)
					cone_move[i]=cone_move[i]+(int)move[i];
				it_move=false;
				first_time_cone = false;
			}	
			if(it_scale)
			{
				gl.translate(cone_move[0]+move[0], cone_move[1]+move[1], cone_move[2]+move[2]);
				cone_scale += scale;
				gl.scale(cone_scale, cone_scale, cone_scale);
				gl.rotate(cone_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(cone_rotate[1], 0.0, 1.0, 0.0);
				it_scale=false;
			}
			else
			{
				if(it_rotate)
				{
					if(ri[1]==1.0 && cone_rotate[2]!=1.0)
					{
						cone_rotate[2]=1.0;
						cone_rotate[3]=0.0;
						cone_rotate[0]=cone_rotate[0]+ri[0];
					}
					if(ri[2]==1.0 && cone_rotate[3]!=1.0)
					{
						cone_rotate[2]=0.0;
						cone_rotate[3]=1.0;
						cone_rotate[1]=cone_rotate[1]+ri[0];
					}
			
					if(ri[1]==1.0 && cone_rotate[2]==1.0)
						cone_rotate[0]=cone_rotate[0]+ri[0];
					if(ri[2]==1.0 && cone_rotate[3]==1.0)
						cone_rotate[1]=cone_rotate[1]+ri[0];
					it_rotate=false;
				}
				gl.translate(cone_move[0], cone_move[1], cone_move[2]);
				if(cone_scale != 0.0F)
					gl.scale(cone_scale, cone_scale, cone_scale);
				gl.rotate(cone_rotate[0], 1.0, 0.0, 0.0);
				gl.rotate(cone_rotate[1], 0.0, 1.0, 0.0);		
			}
		}
		else
		{
			gl.translate(cone_move[0], cone_move[1], cone_move[2]);
			if(cone_scale != 0.0F)
				gl.scale(cone_scale, cone_scale, cone_scale);
			gl.rotate(cone_rotate[0], 1.0, 0.0, 0.0);
			gl.rotate(cone_rotate[1], 0.0, 1.0, 0.0);	
		}

		glu.cylinder(0.0, 0.5, 1, 16, 16);
		gl.popMatrix();
		
		
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
		if (initLess) init();
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
		b.setSize(400, 400);
		System.out.println("");
		System.out.println("	To select object or camera: ");
		System.out.println("		right-mouse clicke at the lower right-hand corner"); 
		System.out.println("		to bring up the pop-up menu.");
		System.out.println("		move mouse to choose the item.");
		System.out.println("");
		System.out.println("	Use 'h', 'j', 'k', 'l', to rotate the current object.");
		System.out.println("	Use '^', 'V', '<', '>', to move the object.");
		System.out.println("	Use '+', '-' to scale each object");
		System.out.println("");
		// Add the canvas and to the frame and make it show
		fm.setTitle("Designed By Shuo Zhao");
  		fm.add("Center", b);
		fm.pack();
		fm.show();

    }


	public void actionPerformed(ActionEvent e) 
	{
		
	}

	// Handle mouse event
	class PopupListener extends MouseAdapter 
	{
		public void mousePressed(MouseEvent e)
		{
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e)
		{
			maybeShowPopup(e);
		}
		private void maybeShowPopup(MouseEvent e)
		{
			if(e.isPopupTrigger())
			{
				camera.setState(false);
				cube.setState(false);
				cone.setState(false);
				sphere.setState(false);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}


	// Handle the keystrokes
	public void keyTyped(KeyEvent e) {
    
		switch (e.getKeyChar()) {
		case 'h':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			if(camera.getState())
				gl.rotate(15.0, 0.0,1.0,0.0);
			else
			{
				ri[0]=15.0;
				ri[1]=0.0;
				ri[2]=1.0;
				ri[3]=0.0;
				it_rotate=true;
			}
			e.consume();
			break;
		case 'j':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			if(camera.getState())
				gl.rotate(15.0, 1.0, 0.0, 0.0);
			else
			{
				ri[0]=15.0;
				ri[1]=1.0;
				ri[2]=0.0;
				ri[3]=0.0;
				it_rotate=true;
			}
			e.consume();
			break;
		case 'k':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			if(camera.getState())
				gl.rotate(-15.0, 1.0,0.0,0.0);
			else
			{
				ri[0]=-15.0;
				ri[1]=1.0;
				ri[2]=0.0;
				ri[3]=0.0;
				it_rotate=true;
			}
			e.consume();
			break;
		case 'l':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			if(camera.getState())
				gl.rotate(-15.0, 0.0, 1.0, 0.0);
			else
			{
				ri[0]=-15.0;
				ri[1]=0.0;
				ri[2]=1.0;
				ri[3]=0.0;
				it_rotate=true;
			}
			e.consume();
			break;
		case '^':								  /* move individual object upward alone the Y */
			gl.matrixMode(GL.MODELVIEW);
			move[0]=0.0;
			move[1]=1.0;
			move[2]=0.0;
			it_move=true;
			e.consume();
			break;
		case 'v':								  /* move individual object downward alone the Y */
 			gl.matrixMode(GL.MODELVIEW);
			move[0]=0.0;
			move[1]=-1.0;
			move[2]=0.0;
			it_move=true;
			e.consume();
			break;
		case '>':								  /* move individual object right-ward alone the X */
			gl.matrixMode(GL.MODELVIEW);
			move[0]=1.0;
			move[1]=0.0;
			move[2]=0.0;
			it_move=true;
			e.consume();
			break;
		case '<':								  /* move individual object left-ward alone the X */
			gl.matrixMode(GL.MODELVIEW);
			move[0]=-1.0;
			move[1]=0.0;
			move[2]=0.0;
			it_move=true;
			e.consume();
			break;
		case '+':
			gl.matrixMode (5888);        /* manipulate Projection matrix  */
			scale = -0.5F;
			it_scale=true;
			e.consume();
			break;
		case '-':
			gl.matrixMode (5888);        /* manipulate Projection matrix  */
			scale = 0.5F;
			it_scale=true;
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
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}
	public void mouseEntered(MouseEvent e)
	{}
	public void mouseExited(MouseEvent e)
	{}
    public void mousePressed(MouseEvent e) 
	{}
    public void mouseReleased(MouseEvent e)
	{}

	/** Convenience function to handle vertex*v call in OpenGL */
	public void vertexv(float[] p) 
	{
		gl.vertex(p[0], p[1], p[2]);
	}
}

