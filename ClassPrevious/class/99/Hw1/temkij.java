/*
Computer Graphics Hw 1
Joshua M. Temkin
email temkij@cs.rpi.edu
cs grad student





*/
import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;


class Light
{
       float lightPos[] = {0.0F, 0.0F, 2.0F, 1.0F};
		float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
		float lightAmb[] = {0.7F, 0.7F, 0.7F, 1.0F};
        float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
        float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};

   public Light(JoglCanvas gl)
   {

       gl.enable(GL.LIGHTING);
		gl.enable(GL.LIGHT0);
		gl.light(GL.LIGHT0, GL.POSITION, lightPos);
		gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir);
		gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb);

//		gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff);

	   gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec);
		gl.enable(GL.DEPTH_TEST);
		gl.shadeModel (GL.SMOOTH);
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
         gl.enable(GL.DITHER);





   }

   public void relight(JoglCanvas gl)
   {



       gl.enable(GL.LIGHTING);
		gl.enable(GL.LIGHT0);
		gl.light(GL.LIGHT0, GL.POSITION, lightPos);
		gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir);
		gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb);

//		gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff);

	   gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec);
		gl.enable(GL.DEPTH_TEST);
		gl.shadeModel (GL.SMOOTH);
		gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
         gl.enable(GL.DITHER);





   }






}

//Generic Super Parent Class for defining a shape
class Shape

{
    //Hold the translation points
    float xPos;
    float yPos;
    float zPos;

    //Holds the information on Rotation points vector
    float xRot;
    float yRot;
    float zRot;
    float spin;

    float xScale;
    float yScale;
    float zScale;
    public Shape()

    {

        xPos = 0;
        yPos = 0;
        zPos = 0;

    }



    public Shape(float x,float y,float z)

    {

        xPos =x;
        yPos =y;
        zPos =z;
        spin = 0;
        xRot = 0;
        yRot = 0;
        zRot = 0;
        xScale = 0.8F;
        yScale = 0.8F;
        zScale = 0.8F;

    }



    public void draw(JoglCanvas gl,Quadric glu)

    {


    }



    public void rotate(float angle,float x,float y,float z)

    {

        spin = angle + spin;
        xRot = x;
        yRot = y;
        zRot = z;


    }

    public void shrink()
    {
        xScale -= 0.1F;
        yScale -= 0.1F;
        zScale -= 0.1F;
    }

    public void grow()
    {
        xScale += 0.1F;
        yScale += 0.1F;
        zScale += 0.1F;
    }

    public void movePosX(float x)
    {
        xPos += x;
    }

    public void moveNegX(float x)
    {
        xPos -= x;
    }

    public void movePosY(float y)
    {
        yPos += y;
    }

    public void moveNegY(float y)
    {
        yPos -= y;
    }

    public void movePosZ(float z)
    {
        zPos += z;
    }

    public void moveNegZ(float z)
    {
        zPos -= z;
    }

}




class Cube extends Shape

{
     float boxDiff[] = {0.8F, 0.0F, 0.8F, 0.8F};
	 float boxSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};
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





    public Cube(float x,float y,float z)

    {

        super(x,y,z);

    }



    public void draw(JoglCanvas gl,Quadric glu)

    {

        float boxAmb[] = {0.8F, 0.0F, 0.0F, 0.0F};


        //System.out.println("Cube");
        gl.pushMatrix();

        gl.translate(xPos,yPos,zPos);
        gl.scale(xScale,yScale,zScale);
        gl.rotate(spin, xRot,yRot,zRot);
        gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
        gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);

        gl.material(GL.FRONT_AND_BACK,GL.SHININESS,70);

        for (int i=0;i<6;i++ ) { // For all faces

			gl.begin(GL.POLYGON);

			for (int j=0;j<4;j++) { // for each vertex

				//Added this as a convenience

				vertexv(gl,boxCors[boxIndex[(i*4)+j]]);

			}

			gl.end();

		}
		gl.popMatrix();
        gl.flush();
    }

    public void vertexv(JoglCanvas gl,float[] p)

	{

		gl.vertex(p[0], p[1], p[2]);

	}

}


//A class for sphere
class Sphere extends Shape

{

    Quadric glu  = new Quadric();

    float sphAmb[] = {0.9F, 0.9F, 0.0F, 1.0F};
    float sphDiff[] = {0.2F, 0.0F, 0.0F, 0.0F};
	float sphSpec[] = {0.8F, 0.0F, 0.0F, 0.0F};
    public Sphere (float x,float y,float z)

    {

        super(x,y,z);

    }



    public void draw(JoglCanvas gl,Quadric glus)

    {



       gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
       gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, sphDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);

        gl.pushMatrix();
        // gl.enable(GL.BLEND);
        //gl.depthMask(false);
       // gl.blendFunc(GL.SRC_ALPHA,GL.ONE);
        gl.translate(xPos, yPos, zPos);

        gl.rotate(spin, xRot,yRot,zRot);
        gl.scale(xScale,yScale,zScale);

	     glu.sphere(0.75F, 32, 32);
        //gl.depthMask(true);
        //gl.disable(GL.BLEND);

    	gl.popMatrix();
        gl.flush();




    }

}


//Derive a class for the cone
class Cone extends Shape

{

    Quadric glu  = new Quadric();
//    float sphAmb[] = {0.2F, 0.2F, 0.2F, 1.0F};
    float sphAmb[]= {0.0F, 0.0F, 0.8F, 1.0F};
    float sphDiff[] = {0.0F, 0.0F, 0.8F, 0.0F};
	float sphSpec[] = {0.0F, 0.0F, 0.8F, 0.0F};


    public Cone (float x,float y,float z)

    {

        super(x,y,z);

    }



    public void draw(JoglCanvas gl,Quadric glus)

    {


        gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
        gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, sphDiff);
		gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);

        gl.material(GL.FRONT_AND_BACK, GL.SHININESS, 0);


       gl.pushMatrix();
       gl.translate(xPos, yPos, zPos);
       gl.rotate(spin, xRot,yRot,zRot);
       gl.scale(xScale,yScale,zScale);

       glu.cylinder(0.0, 0.5, 1, 32, 32);

       gl.popMatrix();
       gl.flush();


    }

}



class Hw1 extends JoglCanvas implements KeyListener,ActionListener

{

    JoglCanvas gl;

	Quadric  glu; // From the glu package
    Light light;
	boolean initLess = true;
    Shape CurrentShape = null;
        boolean shapeOn = false;
    Vector Shapes;
    //MenuItem ExitItem;
	public Hw1()

	{

	    gl = this;
		glu = new Quadric();
		Shapes = new Vector();
		addKeyListener( this );

		Shape tempShape;

        tempShape = new Cone(2,0,-0.5F);
        Shapes.addElement(tempShape);

	    tempShape = new Cube(0,0,0);
        Shapes.addElement(tempShape);


        tempShape = new Sphere(-2,0,0);
        Shapes.addElement(tempShape);


          light = new Light(gl);
	}



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
        //gl.ortho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0);
		gl.matrixMode( GL.MODELVIEW );		// Reset to model transforms



		gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
		gl.depthFunc( GL.LEQUAL );
		gl.texEnv( GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE );

        light.relight(gl);
    }

     public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

    public void display() {



		int x;

		gl.use();
		gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);

		for (x = 0; x < Shapes.size();x++)

		{

		    Shape s = (Shape)Shapes.elementAt(x);
		    s.draw(gl,glu);

		}



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


    public void actionPerformed(ActionEvent e)
    {
          //Object arg = e.getSource();
          //String label = arg.toString();
          String slabel = e.getActionCommand();
          //String plabel = e.paramString();
          //System.out.println("Label: =" + label + " "+ slabel + " " + plabel);
          if(slabel.equals("Exit"))
          {

            //e.consume();
            System.exit(0);
          }
          else
          {
            Shape s;

            //System.out.println("Rotate Cube");
            if(slabel.equals("Rotate Cube") )
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.rotate(-10,1,0,0);

            }

            else if (slabel.equals("Rotate Sphere"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.rotate(-10,1,0,0);
            }

            else if (slabel.equals("Rotate Cone"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.rotate(-10,1,0,0);
            }
            else if (slabel.equals("Cube +X"))
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.movePosX(1);
            }
            else if (slabel.equals("Cube -X"))
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.moveNegX(1);
            }
             else if (slabel.equals("Cube +Y"))
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.movePosY(1);
            }
            else if (slabel.equals("Cube -Y"))
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.moveNegY(1);
            }
             else if (slabel.equals("Cube +Z"))
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.movePosZ(1);
            }
            else if (slabel.equals("Cube -Z"))
            {
                 s = (Shape)Shapes.elementAt(1);
                 s.moveNegZ(1);
            }
            else if (slabel.equals("Cone +X"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.movePosX(1);
            }
            else if (slabel.equals("Cone -X"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.moveNegX(1);
            }
             else if (slabel.equals("Cone +Y"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.movePosY(1);
            }
            else if (slabel.equals("Cone -Y"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.moveNegY(1);
            }
             else if (slabel.equals("Cone +Z"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.movePosZ(1);
            }
            else if (slabel.equals("Cone -Z"))
            {
                 s = (Shape)Shapes.elementAt(0);
                 s.moveNegZ(1);
            }
            else if (slabel.equals("Sphere +X"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.movePosX(1);
            }
            else if (slabel.equals("Sphere -X"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.moveNegX(1);
            }
             else if (slabel.equals("Sphere +Y"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.movePosY(1);
            }
            else if (slabel.equals("Sphere -Y"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.moveNegY(1);
            }
             else if (slabel.equals("Sphere +Z"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.movePosZ(1);
            }
            else if (slabel.equals("Sphere -Z"))
            {
                 s = (Shape)Shapes.elementAt(2);
                 s.moveNegZ(1);
            }
            display();

          }

    }





    public void keyTyped(KeyEvent e) {
        //System.out.println("Works here");

        //System.out.println(e.getKeyChar());
		switch (e.getKeyChar()) {

		case '0':
		     shapeOn = false;
		     break;
		case  '1': //Cube
		     shapeOn = true;
		     //System.out.println("Called");
		     CurrentShape = (Shape)Shapes.elementAt(0);
		     break;
		case '2': //Sphere;
		     shapeOn = true;
		     CurrentShape = (Shape)Shapes.elementAt(1);
		     break;
		 case '3'://Cone
		      shapeOn = true;
		      CurrentShape = (Shape)Shapes.elementAt(2);
		      break;
		 case 'g':
		      if (shapeOn)
		         CurrentShape.grow();

		         break;
		 case 's':
		      if (shapeOn)
		         CurrentShape.shrink();
		         break;

		case 'h':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			if (!shapeOn)
			{
    			gl.rotate(15.0, 0.0,1.0,0.0);
    		}
    		else
    		{
    		    CurrentShape.rotate(15.0F,0.0F,1.0F,0.0F);
    		}
			e.consume();
			break;
		case 'j':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(15.0, 1.0,0.0,0.0);
			if (!shapeOn)
			{
    			gl.rotate(15.0, 1.0,0.0,0.0);
    		}
    		else
    		{
    		   // System.out.println("Works");
    		    CurrentShape.rotate(15.0F,1.0F,0.0F,0.0F);
    		}
			e.consume();
			break;
		case 'k':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(-15.0, 1.0,0.0,0.0);
			if (!shapeOn)
			{
    			gl.rotate(-15.0, 1.0,0.0,0.0);
    		}
    		else
    		{
    		    CurrentShape.rotate(-15.0F,1.0F,0.0F,0.0F);
    		}
			e.consume();
			break;
		case 'l':
			gl.matrixMode (GL.MODELVIEW);        /* manipulate modelview matrix  */
			//gl.rotate(-15.0, 0.0,1.0,0.0);
			if (!shapeOn)
			{
    			gl.rotate(-15.0, 0.0,1.0,0.0);
    		}
    		else
    		{
    		    CurrentShape.rotate(-15.0F, 0.0F,1.0F,0.0F);
    		}
			e.consume();
			break;
		case '+':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
			gl.translate(0.0, 0.0,0.5);
			e.consume();
			break;
		case '-':
			gl.matrixMode (GL.PROJECTION);        /* manipulate Projection matrix  */
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



    public static void usage()
    {
        System.out.println("Joshua Temkin");
        System.out.println("  Usage");
        System.out.println(" Camera angles are controled the same as Simple3.java ");
        System.out.println(" Rotating each object occurs under the file menu");
        System.out.println(" 0r by selecting them 0 for camara, 1 for cone, 2 for cube, 3 for sphere");
        System.out.println(" and then using the standard Simple3.java rotation commands");
        System.out.println(" g for making an object grow, s for shrink ");
        System.out.println(" Moving objects occurs under move and allows you to move +/- in x yz ");
    }
	// This is where things get started

	public static void main( String args[] )

    {

		// Create a new frame to hold the canvas and put it up.
        Frame fm = new Frame();
        MenuBar mbar = new MenuBar();
	    fm.setMenuBar(mbar);
        Hw1 b = new Hw1();
        Menu myMenu = new Menu("File");
        Menu myMove = new Menu("Move");
        MenuItem RotSquare = new MenuItem("Rotate Cube");
        MenuItem RotSphere = new MenuItem("Rotate Sphere");
        MenuItem RotCone = new MenuItem("Rotate Cone");
        MenuItem CubeXPos = new MenuItem("Cube +X");
        MenuItem CubeXNeg = new MenuItem("Cube -X");
        MenuItem CubeYPos = new MenuItem("Cube +Y");
        MenuItem CubeYNeg = new MenuItem("Cube -Y");
        MenuItem CubeZPos = new MenuItem("Cube +Z");
        MenuItem CubeZNeg = new MenuItem("Cube -Z");
        MenuItem ConeXPos = new MenuItem("Cone +X");
        MenuItem ConeXNeg = new MenuItem("Cone -X");

        MenuItem ConeYPos = new MenuItem("Cone +Y");
        MenuItem ConeYNeg = new MenuItem("Cone -Y");
        MenuItem ConeZPos = new MenuItem("Cone +Z");
        MenuItem ConeZNeg = new MenuItem("Cone -Z");
        MenuItem SphXPos = new MenuItem("Sphere +X");
        MenuItem SphXNeg = new MenuItem("Sphere -X");
        MenuItem SphYPos = new MenuItem("Sphere +Y");
        MenuItem SphYNeg = new MenuItem("Sphere -Y");
        MenuItem SphZPos = new MenuItem("Sphere +Z");
        MenuItem SphZNeg = new MenuItem("Sphere -Z");

        MenuItem ExitItem = new MenuItem("Exit");
        ExitItem.addActionListener(b);
        RotSquare.addActionListener(b);
        RotSphere.addActionListener(b);
        RotCone.addActionListener(b);
        CubeXPos.addActionListener(b);
        CubeXNeg.addActionListener(b);
        CubeYPos.addActionListener(b);
        CubeYNeg.addActionListener(b);
        CubeZPos.addActionListener(b);
        CubeZNeg.addActionListener(b);
        ConeXPos.addActionListener(b);
        ConeXNeg.addActionListener(b);
        ConeYPos.addActionListener(b);
        ConeYNeg.addActionListener(b);
        ConeZPos.addActionListener(b);
        ConeZNeg.addActionListener(b);
        SphXPos.addActionListener(b);
        SphXNeg.addActionListener(b);
        SphYPos.addActionListener(b);
        SphYNeg.addActionListener(b);
        SphZPos.addActionListener(b);
        SphZNeg.addActionListener(b);

        mbar.add(myMenu);
        myMenu.add(RotSquare);
        myMenu.add(RotSphere);
        myMenu.add(RotCone);
        myMenu.add(ExitItem);

        mbar.add(myMove);
        myMove.add(CubeXPos);
        myMove.add(CubeXNeg);
        myMove.add(CubeYPos);
        myMove.add(CubeYNeg);
        myMove.add(CubeZPos);
        myMove.add(CubeZNeg);
        myMove.add(ConeXPos);
        myMove.add(ConeXNeg);
        myMove.add(ConeYPos);
        myMove.add(ConeYNeg);
        myMove.add(ConeZPos);
        myMove.add(ConeZNeg);
         myMove.add(SphXPos);
        myMove.add(SphXNeg);
        myMove.add(SphYPos);
        myMove.add(SphYNeg);
        myMove.add(SphZPos);
        myMove.add(SphZNeg);


        fm.add("Center", b);

         usage();






		// Make it visible and set size

		b.setVisible(true);

		b.setSize(300, 300);

		//System.out.println("Here we go");



		// Add the canvas to the frame and make it show


         fm.pack();

		fm.show();




    }

}

