/*
 * bounce.java
 *
 * Copyright ( c ) Brian Paul
 *
 * Java version: Copyright ( c ) Javier Perez, 1997.
 * Jogl version: Tommy Reilly
 *
 */

package demos;

import jogl.*;
import java.awt.*;
import java.awt.event.*;

class bounce extends JoglRenderer
{
  int Ball;
  int Mode;
  float Zrot = 0.0f, Zstep = 4.0f;
  float Xpos = 0.0f, Ypos = 1.0f;
  float Xvel = 0.1f, Yvel = 0.0f;
  float Xmin=-4.0f, Xmax=4.0f;
  float Ymin=-3.8f, Ymax=4.0f;
  float G = -0.05f;
  
  float colorRed[] = { 1.0f, 0.0f, 0.0f };
  float colorWhite[] = { 1.0f, 1.0f, 1.0f };
  float colorCyan[] = { 0.0f, 0.5f, 0.9f };
  
  
  float COS( float X )
    {
      return (float) Math.cos( X*3.14159/180.0 );
    }


    float SIN( float X )
    {
      return (float) Math.sin( X*3.14159/180.0 );
    }


    int makeBall()
    {
      int list;
      float a, b;
      float da = 18.0f, db = 18.0f;
      float radius = 1.0f;
      int color;
      float x, y, z;

      list = gl.genLists( 1 );

      gl.newList( list, GL.COMPILE );

      color = 0;
      for( a=-90.0f;a+da<=90.0f;a+=da ) {
	gl.begin( GL.QUAD_STRIP );
	for( b=0.0f;b<=360.0f;b+=db ) {
	  if( color!=0 ) {
	    gl.color( colorRed[0], colorRed[1], colorRed[2] );
	  }
	  else {
	    gl.color( colorWhite[0], colorWhite[1], colorWhite[2] );
	  }

	  x = COS( b ) * COS( a );
	  y = SIN( b ) * COS( a );
	  z = SIN( a );
	  gl.vertex( x, y, z );

	  x = radius * COS( b ) * COS( a+da );
	  y = radius * SIN( b ) * COS( a+da );
	  z = radius * SIN( a+da );
	  gl.vertex( x, y, z );

	  color = 1-color;
	}
	gl.end();
      }

      gl.endList();

      return list;
    }



    float vel0 = -100.0f;

    public void moveBall()
    {

      Zrot += Zstep;

      Xpos += Xvel;
      if( Xpos>=Xmax ) {
	Xpos = Xmax;
	Xvel = -Xvel;
	Zstep = -Zstep;
      }
      if( Xpos<=Xmin ) {
	Xpos = Xmin;
	Xvel = -Xvel;
	Zstep = -Zstep;
      }

      Ypos += Yvel;
      Yvel += G;
      if( Ypos<Ymin ) {
	Ypos = Ymin;
	if( vel0==-100.0 )
	  vel0 = Math.abs( Yvel );
	Yvel = vel0;
      }
    }


  public void init()
    {
      /* initialize the widget */
      int width  = gl.getWidth();
      int height = gl.getHeight();
      
      gl.viewport( 0, 0, width, height );
      gl.matrixMode( GL.PROJECTION );
      gl.loadIdentity();
      gl.ortho( -6.0, 6.0, -6.0, 6.0, -6.0, 6.0 );
      gl.matrixMode( GL.MODELVIEW );

      Ball = makeBall();
      gl.cullFace( GL.BACK );
      gl.enable( GL.CULL_FACE );
      gl.disable( GL.DITHER );
      gl.shadeModel( GL.FLAT );
    }


    public void display() {

      double red, green, blue;
      int i;

      gl.clear( GL.COLOR_BUFFER_BIT );

      gl.color( colorCyan[0], colorCyan[1], colorCyan[2] );
      gl.begin( GL.LINES );
      for( i=-5;i<=5;i++ ) {
	gl.vertex( i, -5 );
	gl.vertex( i, 5 );
      }
      for( i=-5;i<=5;i++ ) {
	gl.vertex( -5,i );
	gl.vertex( 5,i );
      }
      for( i=-5;i<=5;i++ ) {
	gl.vertex( i, -5 );
	gl.vertex( i*1.15f, -5.9f );
      }
      gl.vertex( -5.3f, -5.35f );
      gl.vertex( 5.3f, -5.35f );
      gl.vertex( -5.75f, -5.9f );
      gl.vertex( 5.75f, -5.9f );
      gl.end();

      gl.pushMatrix();
      gl.translate( Xpos, Ypos, 0.0f );
      gl.scale( 2.0f, 2.0f, 2.0f );
      gl.rotate( 8.0f, 0.0f, 0.0f, 1.0f );
      gl.rotate( 90.0f, 1.0f, 0.0f, 0.0f );
      gl.rotate( Zrot, 0.0f, 0.0f, 1.0f );

      gl.callList( Ball );

      gl.popMatrix();

      gl.flush();
      gl.swap();
      Thread.yield();

      moveBall();
    }

  public static void main( String args[] ) 
    {
      bounce b = new bounce();
      Frame f = new Frame();
      JoglCanvas jc = new JoglCanvas(b);

      f.setSize(300,300);

      f.add("Center", jc);
      f.show();
    }
}
