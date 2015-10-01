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
   jogltest is an example of an old style Jogl application.  It
   doesn't use the JoglRenderer.  
*/
public class jogltest extends Frame implements MouseListener, 
                                        MouseMotionListener, 
                                        WindowListener
{
  JoglCanvas gl;

	public static void main( String args[] ) 
	{
		jogltest tt = new jogltest();
	}
		
	public jogltest()
	{
		super( "OpenGL Widget" );

		/* try to instantiate an OpenGL widget */
                gl = new JoglCanvas();
	
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Test" ));
		mb.add( file );
		setMenuBar( mb );

		// set up the Frame
		gl.setSize( 200, 300 );
		add( "North", new Button( "North" ) );
		add( "South", new Button( "South" ) );
		add( "Center", gl );
		setSize( 500, 500 );
		setVisible( true );
		System.out.println( "show" );

		gl.addMouseListener( this );
		gl.addMouseMotionListener( this );
		gl.addKeyListener( new KeyAdapter()
			{ public void keyPressed( KeyEvent e )
			{
				System.out.println( e );
			}});

		try{
			Thread.sleep( 1000 );
			}catch( Exception e ){}

                Thread.yield();
                

		gl.use();
		System.out.println( "USE" );

		
		/* initialize the widget */
		gl.clearColor( 0.8f, 0.8f, 1.0f, 1.0f );
		gl.frontFace( GL.CW );
		gl.enable( GL.DEPTH_TEST );

		Dimension d = gl.getSize();
		System.out.println( d );
		int width  = d.width;
		int height = d.height;

		gl.viewport( 0, 0, width, height );
	
		gl.matrixMode( GL.PROJECTION );
		gl.loadIdentity();

		double fov    = 45.0,
		aspect = 1.0,//width / height,
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

		Cylinder cyl = new Cylinder();

		for( int k=0; k < 100000; k++ )
		{
			gl.use();
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
			Thread.yield();
		}
		System.out.println( "Hit Enter to exit ..." );
		try 
		{
			System.in.read();
			System.exit( 0 );
		} 
		catch (java.io.IOException e) 
		{
			System.out.println( "java.io.IOException" );
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
      System.exit(1);
    }
  
  public void windowClosing(WindowEvent we)
    {
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

