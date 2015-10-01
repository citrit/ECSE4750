/*
 * Hw1 based on Simple3  All points of interest are noted, specifically code
 * changes from original program
 *
 * John Hare
 * 2/9/99
 *
 * I set the specular high on the square and even used the GL.SHININESS to
 * attempt to make it look shiny.  It really never looked that good to me...
 * Possibly the light could be at issue. I didn't move that too much
 *
 * The cube looks initially bright but seems to darken up when a key gets hit
 * No clue what's up with that
 *
 * The sphere has a spot on it.  I don't know what that is...
 *
 * I'm not sure that my rotation is correct.  The lighting doesn't seem right
 * as objects move - especially on the box.  The lights should always hit it
 * from the same spot, but as the box moves, that part of the box that is in
 * the space where I believe the light should hit it isn't always lit.
 * i.e. top (from my perspective) is lit when one side is facing up, but not
 * others...
 *
 */

// add a single light globe on top to see properties better

import jogl.*;
import jogl.glu.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// new class Hw1 based on JoglCanvas
class Hw1 extends JoglCanvas implements KeyListener
{
  JoglCanvas gl;
  Quadric  glu;
  boolean initLess = true;
  int obj = 1; // current object that can be manipulated (default = box)
  // angle of x rotation for box, cylinder, sphere
  float ax [] = {0.0F, 0.0F, 0.0F};
  float ay [] = {0.0F, 0.0F, 0.0F};
  float az [] = {0.0F, 0.0F, 0.0F};


  public Hw1 () // Constructor for our Hw1 class
  {
    gl = this;
    glu = new Quadric();
    addKeyListener( this );
  } // END Hw1 constructor

  public void init()
  {
    float lightPos[] = {0.0F, 0.0F, 2.0F, 1.0F};
    float lightDir[] = {0.0F, 0.0F, 0.0F, 1.0F};
    float lightAmb[] = {0.9F, 0.9F, 0.9F, 1.0F};
    float lightDiff[] = {0.8F, 0.8F, 0.8F, 1.0F};
    float lightSpec[] = {0.4F, 0.4F, 0.4F, 1.0F};

    initLess = false;

    int width  = gl.getWidth();
    int height = gl.getHeight();
      
    gl.viewport( 0, 0, width, height );
    gl.matrixMode( GL.PROJECTION );
    gl.loadIdentity();

    // Expand region of interest so all objects are visible in their entirety
    gl.ortho(-3.0, 3.0, -3.0, 3.0, -3.0, 3.0);
    gl.matrixMode( GL.MODELVIEW );

    gl.enable(GL.BLEND); // added to enable blending and allow see-through sphere
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
  } // END init


  public void display()
  {
    float boxAmb[] = {0.3F, 0.0F, 0.0F, 1.0F};
    float boxDiff[] = {0.7F, 0.0F, 0.0F, 1.0F};
    float boxSpec[] = {1.0F, 0.0F, 0.0F, 1.0F};

    float cylAmb[] = {0.0F, 0.0F, 0.4F, 1.0F};
    float cylDiff[] = {0.0F, 0.0F, 1.0F, 1.0F};
    float cylSpec[] = {0.0F, 0.0F, 0.0F, 1.0F};

    float sphAmb[] = {0.7F, 0.8F, 0.0F, 0.5F};
    float sphDiff[] = {0.7F, 0.8F, 0.0F, 0.5F};
    float sphSpec[] = {0.8F, 0.0F, 0.0F, 0.5F};

    float shine_on = 28.0F;
    float shine_off = 0.0F;

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
    gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, boxAmb);
    gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, boxDiff);
    gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, boxSpec);
    gl.material(GL.FRONT_AND_BACK, GL.SHININESS, shine_on);

    gl.pushMatrix(); // need to push the matrix for the rotations in all axes
    gl.rotate(ax[0], 1.0F, 0.0F, 0.0F); // rotate the current x angle
    gl.rotate(ay[0], 0.0F, 1.0F, 0.0F); // rotate the current y angle
    gl.rotate(az[0], 0.0F, 0.0F, 1.0F); // rotate the current z angle
    for (int i=0;i<6;i++ )
    {
      gl.begin(GL.POLYGON);
      for (int j=0;j<4;j++)
      {
         vertexv(boxCors[boxIndex[(i*4)+j]]);
      } // END for
      gl.end();
    } // END for
    gl.popMatrix(); // pop matrix off again

    // changed ambient, diffuse,
    gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, cylAmb);
    gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, cylDiff);
    gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, cylSpec);
    gl.material(GL.FRONT_AND_BACK, GL.SHININESS, shine_off);
    gl.pushMatrix();
    gl.translate(2, 0, -0.5);

    gl.rotate(ax[1], 1.0F, 0.0F, 0.0F); // rotate the current x angle
    gl.rotate(ay[1], 0.0F, 1.0F, 0.0F); // rotate the current y angle
    gl.rotate(az[1], 0.0F, 0.0F, 1.0F); // rotate the current z angle
    glu.cylinder(0.0, 0.5, 1, 16, 16);
    gl.popMatrix();

    // Used glu to add sphere object two units left of cube
    gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, sphAmb);
    gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, sphDiff);
    gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, sphSpec);
    gl.pushMatrix();
    gl.translate(-2, 0, 0);

    gl.rotate(ax[2], 1.0F, 0.0F, 0.0F); // rotate the current x angle
    gl.rotate(ay[2], 0.0F, 1.0F, 0.0F); // rotate the current y angle
    gl.rotate(az[2], 0.0F, 0.0F, 1.0F); // rotate the current z angle

    glu.sphere(0.5, 16, 16);
    gl.popMatrix();

    gl.flush();
    gl.swap();
  } // END display

  public void paint(Graphics g)
  {
    super.paint(g);
    if (initLess)
      init();
    display();
  } // END paint

  public static void main( String args[] ) 
  {
    Frame fm = new Frame();
    Hw1 b = new Hw1(); // Create new instance of Hw1 class

    b.setVisible(true);
    b.setSize(400, 400);
    System.out.println("Q and W rotate view +/- 15 degrees in X");
    System.out.println("A and S rotate view +/- 15 degrees in Y");
    System.out.println("Z and X rotate view +/- 15 degrees in Z");
    System.out.println("1 select the box as current object");
    System.out.println("2 select the cone as current object");
    System.out.println("3 select the sphere as current object");
    System.out.println("C, V, and B rotate the current object in x, y, z respectively");

    fm.add("Center", b);
    fm.pack();
    fm.show();
  } // END main

  public void keyTyped(KeyEvent e)
  {
    
     switch (e.getKeyChar())
     {
       // redefined keys and what they do...
       case 'c': // add 15 degrees to angle in x of current object
         ax[obj-1] = ax[obj-1] + 15.0F;
         if (ax[obj-1] >= 360.0F) ax[obj-1] = 0.0F;
         e.consume();
         break;
       case 'v': // add 15 degrees to angle in y of current object
         ay[obj-1] = ay[obj-1] + 15.0F;
         if (ay[obj-1] >= 360.0F) ay[obj-1] = 0.0F;
         e.consume();
         break;
       case 'b': // add 15 degrees to angle in z of current object
         az[obj-1] = az[obj-1] + 15.0F;
         if (az[obj-1] >= 360.0F) az[obj-1] = 0.0F;
         e.consume();
         break;  
       case '1': // set current object to box
         obj = 1;
         e.consume();
         break;
       case '2': // set current object to cone
         obj = 2;
         e.consume();
         break;
       case '3': // set current object to sphere
         obj = 3;
         e.consume();
         break;
       case 'q':
         gl.matrixMode (GL.MODELVIEW);
         gl.rotate(15.0, 1.0,0.0,0.0);
         e.consume();
         break;
       case 'a':
         gl.matrixMode (GL.MODELVIEW);
         gl.rotate(15.0, 0.0,1.0,0.0);
         e.consume();
         break;
       case 'z':
         gl.matrixMode (GL.MODELVIEW);
         gl.rotate(15.0, 0.0,0.0,1.0);
         e.consume();
         break;
       case 'w':
         gl.matrixMode (GL.MODELVIEW);
         gl.rotate(-15.0, 1.0,0.0,0.0);
         e.consume();
         break;
       case 's':
         gl.matrixMode (GL.MODELVIEW);
         gl.rotate(-15.0, 0.0,1.0,0.0);
         e.consume();
         break;
       case 'x':
         gl.matrixMode (GL.MODELVIEW);
         gl.rotate(-15.0, 0.0,0.0,1.0);
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
       case 27:
         System.exit(1);
         break;
         default:
         break;
     } // END switch
     display();
   } // END keyTyped

   public void keyPressed(KeyEvent e)
   {
   } // END keyPressed

   public void keyReleased(KeyEvent e)
   {
   } // END keyReleased

   public void vertexv(float[] p) 
   {
     gl.vertex(p[0], p[1], p[2]);
   } // END vertexv

} // END class Hw1

