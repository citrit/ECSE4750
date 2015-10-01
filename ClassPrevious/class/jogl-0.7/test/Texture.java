/* Copyright 1997 Free Software Foundation, Inc. */
/*
 * Tommy Reilly
 *
 */

package test;

import jogl.*;
import java.awt.*;
import java.awt.event.*;

/**
   This is supposed to draw a checker texture but it doesn't
   work 
*/
public class Texture extends Frame implements MouseListener, 
                                       MouseMotionListener,
                                       WindowListener
{
  byte[][][] checkimage;

  class MyRenderer extends JoglRenderer
  {
    Checker check;        
    int k=0;

    public void init()
      {
        gl.clearColor( 0.8f, 0.8f, 1.0f, 1.0f );
        gl.frontFace( GL.CW );
        gl.enable( GL.DEPTH_TEST );
        
        int width  = gl.getWidth();
        int height = gl.getHeight();
      
        gl.viewport( 0, 0, width, height );
      
        gl.matrixMode( GL.PROJECTION );
        gl.loadIdentity();
      
        double fov    = 45.0,
          aspect = width / height,
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
        gl.material( GL.FRONT,GL.DIFFUSE, farr );
      
        gl.material( GL.BACK, GL.SHININESS, 50.0f );
      
        farr[0] = 0.0f; farr[1] = 0.0f; farr[2] = 1.0f; farr[3] = 1.0f;
        gl.material( GL.BACK, GL.SPECULAR, farr );
      
        farr[0] = 1.0f; farr[1] = 1.0f; farr[2] = 0.0f; farr[3] = 1.0f;
        gl.material( GL.BACK, GL.DIFFUSE, farr );
      
        gl.enable( GL.DEPTH_TEST );
        gl.enable( GL.TEXTURE_2D );
        gl.texEnv(GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, 
                         GL.DECAL);
      
        checkimage = new byte[64][64][4];
      
        for(int i=0; i < 64; i++)
          for(int j=0; j < 64; j++)
            {
              byte c=0;
              if(((i & 0x8) == 0)^((j & 0x8))==0)
                c = (byte) 255;
            
              checkimage[i][j][0] = c;
              checkimage[i][j][1] = c;
              checkimage[i][j][2] = c;
              checkimage[i][j][3] = (byte) 255;
            }
      
        gl.pixelStore( GL.UNPACK_ALIGNMENT, 1);
      
        int texname = gl.genTextures(1);
      
        gl.bindTexture( GL.TEXTURE_2D, texname);
      
        gl.texParameter( GL.TEXTURE_2D, 
                         GL.TEXTURE_WRAP_S, GL.REPEAT);
      
        gl.texParameter( GL.TEXTURE_2D, 
                         GL.TEXTURE_WRAP_T, GL.REPEAT);
      
        gl.texParameter( GL.TEXTURE_2D, 
                         GL.TEXTURE_MAG_FILTER, GL.NEAREST);

        gl.texParameter( GL.TEXTURE_2D, 
                         GL.TEXTURE_MIN_FILTER,  GL.NEAREST);
      
        gl.texImage( GL.TEXTURE_2D, 0, GL.RGBA, 64, 64, 0, GL.RGBA, GL.BYTE, checkimage);
 
        check = new Checker();          
      }

    public void display()
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
          check.draw( gl );
          gl.popMatrix();
          gl.pushMatrix();
          gl.rotate( 90.0, 0.0, 1.0, 0.0 );
          gl.scale( 1.0, 1.0, 10.0 );
          check.draw( gl );
          gl.popMatrix();
          gl.pushMatrix();
          gl.rotate( 90.0, 1.0, 0.0, 0.0 );
          gl.scale( 1.0, 1.0, 10.0 );
          check.draw( gl );
          gl.popMatrix();
          gl.popMatrix();
          
          gl.swap();
          gl.flush();
          k++;
      }
    
    
  }
  
  public static void main( String args[] ) 
    {
      Texture tt = new Texture();
    }
		
  public Texture()
    {
      JoglCanvas gl;
      MenuBar mb = new MenuBar();
      Menu file = new Menu( "File" );
      file.add( new MenuItem( "Test" ));
      mb.add( file );
      setMenuBar( mb );
      
      setSize( 500, 500 );
      setVisible( true );
      addMouseListener( this );
      addMouseMotionListener( this );
      addKeyListener( new KeyAdapter()
                      { public void keyPressed( KeyEvent e )
			{
                          System.out.println( e );
			}}      );

		/* try to instantiate an OpenGL widget */
		try 
		{
			gl = new JoglCanvas( new MyRenderer() );
                        add("Center", gl);
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

class Checker {
	public Checker() {
	}

	public void draw( JoglCanvas gl ) {
	    gl.begin( GL.QUADS );
	    gl.texCoord(0.0, 0.0); gl.vertex(-2.0, -1.0, 0.0);
	    gl.texCoord(0.0, 1.0); gl.vertex(-2.0, 1.0, 0.0);
	    gl.texCoord(1.0, 1.0); gl.vertex(0.0, 1.0, 0.0);
	    gl.texCoord(1.0, 0.0); gl.vertex(0.0, -1.0, 0.0);

	    gl.texCoord(0.0, 0.0); gl.vertex(1.0, -1.0, 0.0);
	    gl.texCoord(0.0, 1.0); gl.vertex(1.0, 1.0, 0.0);
	    gl.texCoord(1.0, 1.0); gl.vertex(2.41421, 1.0, -1.41421);
	    gl.texCoord(1.0, 0.0); gl.vertex(2.41421, -1.0, -1.41421);
	    gl.end();
	}
}

