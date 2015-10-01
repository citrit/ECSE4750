//hw1.java
//Frank McDermott
//Advanced Computer Graphics - Homework 1
//package primitive;
import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class hw1 extends JoglCanvas implements KeyListener {
	JoglCanvas gl;    Quadric  glu; 
	// From the glu package    
	boolean initLess = true;    
	Box box;    
	Cone cone;    
	Sphere sphere;    
	int primitive;    
	int bDList;    
	int cDList;    
	int sDList;            
	//Constructor    
	public hw1 () {	
		gl = this;	
		glu = new Quadric();	
		addKeyListener( this );
		primitive = 0;		
		box = new Box(gl,glu);	cone = new Cone(gl, glu);	
		sphere = new Sphere(gl, glu);	    
	}        
	public void init() {
		//  	float lightPos[] = {0.0F, 0.0F, 2.0F, 1.0F};
		//  	float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		//  	float lightAmb[] = {0.7F, 0.7F, 0.7F, 1.0F};
		//  	float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
		//  	float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};
		float lightPos[] = {0.0F, 0.0F, 2.0F, 1.0F};
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		float lightAmb[] = {0.7F, 0.7F, 0.7F, 1.0F};
		float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
		float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};
		initLess = false;	
		/* initialize the widget */	
		int width  = gl.getWidth();	int height = gl.getHeight();
		// Initialize the rendering viewport size to OpenGL	
		gl.viewport( 0, 0, width, height );	
		gl.matrixMode( GL.PROJECTION );	
		// Set up the camera mode	
		gl.loadIdentity();	
		// Reset the transformation matrix	
		gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0); 
		// Set region of interest	
		gl.matrixMode( GL.MODELVIEW );
		// Reset to model transforms
		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);	
		gl.depthFunc( GL.LEQUAL );	
		gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE );	
		gl.enable(GL.LIGHTING);	gl.enable(GL.LIGHT0);	
		gl.light(GL.LIGHT0, GL.POSITION, lightPos);	
		gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir);	
		gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb);	
		gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff);
		gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec);
		gl.enable(GL.DEPTH_TEST);  	gl.shadeModel (GL.SMOOTH);	
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);	
		gl.enable(GL.DITHER);	bDList = gl.genLists(1);	
		cDList = gl.genLists(2);	sDList = gl.genLists(3);
		if (bDList != 0) {
			box.setDList(bDList);
			gl.newList(bDList, GL.COMPILE);	    box.draw();
			gl.endList();	
		}
		else {	    System.out.println("couldn't create lists");	
		}
		if (cDList != 0) {
			cone.setDList(cDList);
			gl.newList(cDList, GL.COMPILE);
			cone.draw();	    gl.endList();
		}
		else {	    System.out.println("couldn't create lists");
		}
		if (sDList != 0) {
			sphere.setDList(sDList);
			gl.newList(sDList, GL.COMPILE);	    sphere.draw();	    
			gl.endList();	
		} 
		else 
			{	    System.out.println("couldn't create lists");	
		}
    
	} 
	public void display() {		
		gl.use();	
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);	
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);	
		gl.callList(bDList);	gl.callList(cDList);	
		gl.callList(sDList);	gl.flush();	gl.swap();
    }        
	// Simple redirector to our display function    
	public void paint(Graphics g)    {	
		// First call the base class paint method to do a one time
		// Initialization - specific to the JoglCanvas class	
		super.paint(g);	
		//System.out.println("Call to paint");	
		if (initLess) init();	
		display();    
	} 
	// This is where things get started    
	public static void main( String args[] )     {
		// Create a new frame to hold the canvas and put it up.	
		Frame f = new Frame();	hw1 obj = new hw1();
		obj.setVisible(true);	obj.setSize(400, 400);
		// Make it visible and set size	
		System.out.println("Here we go");
		// Add the canvas to the frame and make it show	
		f.add("Center", obj);	f.pack();	f.show();
	}        
	// Handle the keystrokes    
	public void keyTyped(KeyEvent e) {	
		switch (e.getKeyChar()) {	
		case '0':  //apply transformation to all
			primitive = 0;	    e.consume();
			break;	
		case '1':  // sphere
			primitive = 1;	    e.consume();
			break;	
		case '2':  //box
			primitive = 2;	    e.consume();
			break;	
		case '3':  //cone	   
			primitive = 3;	    e.consume();
			break;	
		case 'h':	    
			gl.matrixMode (GL.MODELVIEW);	 
			if (primitive != 0) {
				rotateP('y');	    
			}
			else
				gl.rotate(15.0, 0.0,1.0,0.0);
			e.consume();
			break;
		case 'j':	
			gl.matrixMode (GL.MODELVIEW);
			if (primitive != 0)	
				rotateP('x');
			else
				gl.rotate(15.0, 1.0,0.0,0.0);
			e.consume();
			break;
		case 'k':	    
			gl.matrixMode (GL.MODELVIEW);	  
			if (primitive != 0)		rotateP('c');
			else
				gl.rotate(-15.0, 1.0,0.0,0.0);
			e.consume();	   
			break;	
		case 'l':	 
			gl.matrixMode (GL.MODELVIEW);
			if (primitive != 0)	
				rotateP('b');	   
			else	
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
		case 27:           /* Esc will quit */	 
			System.exit(1);	 
			break;
		default:	
			break;
		}
		display();
    } 
	public void keyPressed(KeyEvent e) {	    }
    public void keyReleased(KeyEvent e) {	    } 
	private void rotateP(char c) {
		switch (primitive) {
		case 1:	   
			sphere.rotate(c);	  
			break;	case 2:	
			gl.pushMatrix();
			box.rotate(c);	    
			gl.popMatrix();	   
			break;
		case 3:	   
			cone.rotate(c);	
			break;	
		case 0:	   
			break;	
		}
	}
	/** Convenience function to handle vertex*v call in OpenGL */
}
//package primitive;import jogl.*;import jogl.glu.*;import java.awt.*;
class Box {    JoglCanvas gl;    Quadric glu;        float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};    float boxDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};    float boxSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};    float boxShine[] = {500.0F};    float boxCors[][] = { {-0.5F, -0.5F, -0.5F},			  {0.5F, -0.5F, -0.5F},			  {0.5F,  0.5F, -0.5F},			  {-0.5F,  0.5F, -0.5F},			  {-0.5F, -0.5F,  0.5F},			  {0.5F, -0.5F,  0.5F},			  {0.5F,  0.5F,  0.5F},			  {-0.5F,  0.5F,  0.5F} };    int  
boxIndex[] = { 0, 1, 2, 3, // Face 1
			4, 5, 6, 7, // Face 2
			0, 1, 5, 4, // Face 3
			2, 6, 7, 3, // Face 4
			1, 2, 6, 5, // Face 5
			0, 3, 7, 4 };  // Face 6
    int dlist;        public Box (JoglCanvas ngl, Quadric nglu) {	gl = ngl;	glu = nglu;	Boxinit();    }    public void setDList(int l) {	dlist = l;    }        public void Boxinit() {		draw();    }    public void draw() {	gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);	gl.material(GL.FRONT_AND_BACK, GL.SHININESS, boxShine);	gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);  	gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);	for (int i=0;i<6;i++ ) { // For all faces
		gl.begin(GL.POLYGON);	    
		for (int j=0;j<4;j++) { // for each vertex
			//Added this as a convenience
			vertexv(boxCors[boxIndex[(i*4)+j]]);
	    }
		gl.end();
	}
    }
    public void vertexv(float[] p)     {	gl.vertex(p[0], p[1], p[2]);    }    public void rotate(char c) {	switch (c) {	case 'y':	    gl.rotate(15.0F, 0.0F, 1.0F, 0.0F);	    gl.callList(dlist);	    break;	case 'x':	    gl.rotate(15.0F, 1.0F, 0.0F, 0.0F);	    gl.callList(dlist);	    break;	case 'b':	    gl.rotate(-15.0F, 0.0F, 1.0F, 0.0F);	    gl.callList(dlist);	    break;	case 'c':	    gl.rotate(-15.0F, 1.0F, 0.0F, 0.0F);	    gl.callList(dlist);	    break;	}    }}
//package primitive;import jogl.*;import jogl.glu.*;import java.awt.*;
class Cone {    JoglCanvas gl;    Quadric glu;    float coneAmb[] = {0.0F, 0.0F, 0.7F, 1.0F};    float coneDiff[] = {0.0F, 0.0F, 1.0F, 1.0F};    int dlist;        public Cone (JoglCanvas ngl, Quadric nglu) {	gl = ngl;	glu = nglu;	Coneinit();    }    public void Coneinit() {		draw();    }    public void draw() {	gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, coneAmb);  	gl.translate(2, 0, 0);	glu.cylinder(0.0, 0.5, 1, 16, 16);    }    public void setDList(int l) {	dlist = l;    }        public void rotate(char c) {	switch (c) {	case 'y':	    gl.rotate(15.0F, 0.0F, 1.0F, 0.0F);	    gl.callList(dlist);	    break;	case 'x':	    gl.rotate(15.0F, 1.0F, 0.0F, 0.0F);	    gl.callList(dlist);	    break;	case 'b':	    gl.rotate(-15.0F, 0.0F, 1.0F, 0.0F);	    gl.callList(dlist);	    break;	case 'c':	    gl.rotate(-15.0F, 1.0F, 0.0F, 0.0F);	    gl.callList(dlist);	    break;	}    }}
//import jogl.*;import jogl.glu.*;import java.awt.*;

class Sphere {    
	
	JoglCanvas gl; 
	Quadric glu;   
	float sphSpec[] = {0.8F, 0.8F, 0.0F, 0.1F};   
	float sphAmb[] = {0.9F, 0.9F, 0.0F, 0.1F};  
	int dlist;       
	public Sphere (JoglCanvas ngl, Quadric nglu) {	
		gl = ngl;	glu = nglu;	Sphereinit();   
	} 
	public void Sphereinit() {	
		draw();  
	}
	public void draw() {
		gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);	
		gl.translate(-2, 0, 0);	glu.sphere(0.5, 32, 32);
		glu.sphere(1, 16, 16);
	}  
	public void setDList(int l) {
		dlist = l;  
	} 
	public void rotate(char c) {
		switch (c) {	
		case 'y':	    
			gl.rotate(15.0F, 0.0F, 1.0F, 0.0F);
			gl.callList(dlist);	  
			break;
		case 'x':	  
			gl.rotate(15.0F, 1.0F, 0.0F, 0.0F);	 
			gl.callList(dlist);	   
			break;
		case 'b':	 
			gl.rotate(-15.0F, 0.0F, 1.0F, 0.0F);
			gl.callList(dlist);	    
			break;	
		case 'c':	
			gl.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
			gl.callList(dlist);	  
			break;	

		}    
	}    
}

