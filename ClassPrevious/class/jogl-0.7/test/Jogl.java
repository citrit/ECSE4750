/* Copyright 1997 Free Software Foundation, Inc.*/
/*
 * Leo Chan
 *
 * October 1995
 */

package test;

import jogl.*;
import java.awt.*;
import java.awt.event.*;

/**
   Jogl is a good example of a Jogl application.  It uses keyboard
   events ('s' suspends it and 'r' resumes it).  It also uses
   a JoglRenderer
*/
public class Jogl extends Frame implements MouseListener, 
                                    MouseMotionListener,
                                    WindowListener
{
  class MyRenderer extends JoglRenderer implements KeyListener
  {
    Cylinder cyl;
    int k=0;

    public void init()
      {
        gl.clearColor( 0.8f, 0.8f, 1.0f, 1.0f );
        gl.frontFace( GL.CW );
        gl.enable( GL.DEPTH_TEST );
		
        Dimension d = gl.getSize();

        gl.viewport( 0, 0, d.width, d.height );
	
        gl.matrixMode( GL.PROJECTION );
        gl.loadIdentity();

        double fov    = 45.0,
          aspect = d.width / d.height,
          near   = 1.0,
          far    = 200.0;
        gl.perspective( fov, aspect, near, far );

      /* render three rotated cylinders */
        gl.clear( GL.COLOR_BUFFER_BIT );
        gl.clear( GL.DEPTH_BUFFER_BIT );

        gl.matrixMode( GL.MODELVIEW );
     
      
        /* enable lighting */
        gl.enable( GL.LIGHTING );
        float lightArr[] = new float[4];
        lightArr[0] = 1.0f; lightArr[1] = 1.0f; 
        lightArr[2] = 1.0f; lightArr[3] = 1.0f;
        gl.light( GL.LIGHT0, GL.DIFFUSE, lightArr );
        lightArr[0] = 90.0f; lightArr[1] = 90.0f; 
        lightArr[2] = 0.0f; lightArr[3] = 0.0f;
        gl.light( GL.LIGHT0, GL.POSITION, lightArr );
        lightArr[0] = 0.1f; lightArr[1] = 0.1f; 
        lightArr[2] = 0.1f; lightArr[3] = 1.0f;
        gl.light( GL.LIGHT0, GL.AMBIENT, lightArr );
        gl.lightModel( GL.LIGHT_MODEL_TWO_SIDE, 1 );
        gl.enable( GL.LIGHT0 );
      
        /* test materials */
        float farr[] = new float[4];
      
        gl.material( GL.FRONT, GL.SHININESS, 30.0f );
      
        farr[0] = 0.0f; farr[1] = 0.0f; farr[2] = 0.0f; farr[3] = 1.0f;
        gl.material( GL.FRONT, GL.SPECULAR, farr );
      
        farr[0] = 0.0f; farr[1] = 1.0f; farr[2] = 0.0f; farr[3] = 1.0f;
        gl.material( GL.FRONT, GL.DIFFUSE, farr );
      
        gl.material( GL.BACK, GL.SHININESS, 50.0f );
      
        farr[0] = 0.0f; farr[1] = 0.0f; farr[2] = 1.0f; farr[3] = 1.0f;
        gl.material( GL.BACK, GL.SPECULAR, farr );
      
        farr[0] = 1.0f; farr[1] = 1.0f; farr[2] = 0.0f; farr[3] = 1.0f;
        gl.material( GL.BACK, GL.DIFFUSE, farr );
      
        cyl = new Cylinder();
      }

    public void display()
      {
        gl.clearColor( 0.8f, 0.8f, 1.0f, 1.0f );
        gl.clear( GL.COLOR_BUFFER_BIT ); 
        gl.clear( GL.DEPTH_BUFFER_BIT );
        gl.pushMatrix();
        gl.translate( 0.0, 0.0, -65.0 );
        gl.rotate(  40.0+(2*k), 0.0, 0.0, 1.0 );
        gl.rotate( 190.0+(2*k), 0.0, 1.0, 0.0 );
        gl.rotate( 200.0+(k*2), 1.0, 0.0, 0.0 );
        gl.pushMatrix();
        gl.scale( 1.0, 1.0, 10.0 );
        cyl.draw( gl );
        gl.popMatrix();
        gl.pushMatrix();
        gl.rotate( 90.0, 0.0, 1.0, 0.0 );
        gl.scale( 1.0, 1.0, 10.0 );
        cyl.draw( gl );
        gl.popMatrix();
        gl.pushMatrix();
        gl.rotate( 90.0, 1.0, 0.0, 0.0 );
        gl.scale( 1.0, 1.0, 10.0 );
        cyl.draw( gl );
        gl.popMatrix();
        gl.popMatrix();
          
        gl.swap();
        gl.flush();

        k++;
      }
    
    public void keyPressed( KeyEvent e ) 
      {       	
        System.out.println( e );

        if(e.getKeyChar() == 's')
          suspend();

        if(e.getKeyChar() == 'r')
          resume();
            
      }
    public void	keyTyped(KeyEvent e) {}
    public void	keyReleased(KeyEvent e) {}
  }
  

  public static void main( String args[] ) 
    {
      Jogl tt = new Jogl();
      tt.setSize( 500, 500 );
      tt.setVisible( true );
    }
  
  public Jogl()
    {
      JoglCanvas jc;
      MenuBar mb = new MenuBar();
      Menu file = new Menu( "File" );
      file.add( new MenuItem( "Test" ));
      mb.add( file );
      setMenuBar( mb );
      MyRenderer rend = new MyRenderer();

      /* try to instantiate an OpenGL widget */
      try 	
        {
          jc = new JoglCanvas( rend )
            {
              public boolean isFocusTraversable()
                {
                  return true;
                }
            };
          jc.addMouseListener( this );
          jc.addMouseMotionListener( this );
          jc.addKeyListener( rend );
      
          addWindowListener( this );

          add("Center", jc);
          add("North", new Button( "North Button" ));
          add("South", new Button( "South Button" ));

        }
      catch( JoglNativeException e ) 
        {
          System.out.println( "can't open the OGL library" );
          System.exit(-1);
        }
      
      
    } 

  // Methods required for the implementation of MouseListener
  public void mouseClicked( MouseEvent evt )
    {
    }

  public void mouseEntered( MouseEvent evt )
    {
    }

  public void mouseExited( MouseEvent evt )
    {
    }

  public void mousePressed( MouseEvent evt )
    {
      System.out.println( "MousePressed: "+evt.getX()+":"+evt.getY()+"|" + evt.getModifiers() +"|" + MouseEvent.BUTTON3_MASK );
    }

  public void mouseReleased( MouseEvent evt )
    {
    }

  public void mouseDragged( MouseEvent evt )
    {
      System.out.println( "Dragged" + evt );
    }

  public void mouseMoved( MouseEvent evt )
    {
      System.out.println( "Moved" + evt );
    }  

  public void windowActivated(WindowEvent we)
    {
    }
  
  public void windowClosed(WindowEvent we)
    {
    }
  
  public void windowClosing(WindowEvent we)
    {
      System.exit(1);
    }
  
  public void windowDeactivated(WindowEvent we)
    {
    }
  
  public void windowDeiconified(WindowEvent we)
    {
    }
  
  public void windowIconified(WindowEvent we)
    {
    }
  
  public void windowOpened(WindowEvent we) 
    {
    }
}

