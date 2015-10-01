/*
 * planes.java
 *
 * Copyright (c) 1993, 1994, Silicon Graphics, Inc.
 * Copyright (c) Mark J. Kilgard, 1994.
 *
 * Java version: Copyright (c) Javier Perez, 1997.
 *
 */

package demos;

import jogl.*;
import java.util.Random;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


class planes extends JoglRenderer
{
  private static final double PI = Math.PI;
  private static final double PI_2 = Math.PI / 2;

  private Random random;

  boolean moving = false;
  
  private static final int MAX_PLANES = 15;
  
  private plane thePlanes[];


  private void addPlane() {
    int i;

		for( i = 0; i < MAX_PLANES; i++ )
			if( thePlanes[i].speed == 0 ) {
				switch( Math.abs( random.nextInt() % 6 ) ) {
				case 0:
					thePlanes[i].red = 1.0; thePlanes[i].green = 0.0; thePlanes[i].blue = 0.0;  // red
					break;
				case 1:
					thePlanes[i].red = 1.0; thePlanes[i].green = 1.0; thePlanes[i].blue = 1.0;  // white
					break;
				case 2:
					thePlanes[i].red = 0.0; thePlanes[i].green = 1.0; thePlanes[i].blue = 0.0;  // green
					break;
				case 3:
					thePlanes[i].red = 1.0; thePlanes[i].green = 0.0; thePlanes[i].blue = 1.0;  // magenta
					break;
				case 4:
					thePlanes[i].red = 1.0; thePlanes[i].green = 1.0; thePlanes[i].blue = 0.0;  // yellow
					break;
				case 5:
					thePlanes[i].red = 0.0; thePlanes[i].green = 1.0; thePlanes[i].blue = 1.0;  // cyan
					break;
				}
				thePlanes[i].speed = ( Math.abs( random.nextInt() % 20 ) ) * 0.001 + 0.02;
				if( ( random.nextInt() & 0x1 ) != 0 )
					thePlanes[i].speed *= -1;
				thePlanes[i].theta = ( (float) ( Math.abs( random.nextInt() % 257 ) ) ) * 0.1111;
				tickPerPlane( i );
				if( !moving )
					display();
				return;
			}
	}


	private void removePlane() {
		int i;

		for( i = MAX_PLANES - 1; i >= 0; i-- )
			if( thePlanes[i].speed != 0 ) {
				thePlanes[i].speed = 0;
				if( !moving )
					display();
				return;
			}
	}


	private void tickPerPlane( int i ) {

		double theta = thePlanes[i].theta += thePlanes[i].speed;
		thePlanes[i].z = -9 + 4 * Math.cos( theta );
		thePlanes[i].x = 4 * Math.sin( 2 * theta );
		thePlanes[i].y = Math.sin( theta / 3.4 ) * 3;
		thePlanes[i].angle = ( ( Math.atan( 2.0 ) + PI_2 ) * Math.sin( theta ) - PI_2 ) * 180 / PI;
		if( thePlanes[i].speed < 0.0 )
			thePlanes[i].angle += 180;
	}


	public void init() {

		int i;

		thePlanes = new plane[MAX_PLANES];
		for( i = 0; i < MAX_PLANES; i++ )
			thePlanes[i] = new plane();

                int width  = gl.getWidth();
		int height = gl.getHeight();

		gl.viewport( 0, 0, width, height );

		// setup OpenGL state
		gl.clearDepth( 1.0 );
		gl.clearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		gl.matrixMode( GL.PROJECTION );
		gl.frustum( -1.0, 1.0, -1.0, 1.0, 1.0, 20 );
		gl.matrixMode( GL.MODELVIEW );

		// add three initial random thePlanes
		random = new Random( System.currentTimeMillis() );
		for( i = 0; i < 3; i++ )
			addPlane();

		moving = true;
	}


	public void movePlanes() {

		int i;

		for( i = 0; i < MAX_PLANES; i++ )
			if( thePlanes[i].speed != 0.0 )
				tickPerPlane( i );
	}

	public void display() {

		double red, green, blue;
		int i;

			gl.clear( GL.DEPTH_BUFFER_BIT );
			// paint black to blue smooth shaded polygon for background
			gl.disable( GL.DEPTH_TEST );
			gl.shadeModel( GL.SMOOTH );
			gl.begin( GL.POLYGON );
			gl.color( 0.0, 0.0, 0.0 );
			gl.vertex( -20, 20, -19 );
			gl.vertex( 20, 20, -19 );
			gl.color( 0.0, 0.0, 1.0 );
			gl.vertex( 20, -20, -19 );
			gl.vertex( -20, -20, -19 );
			gl.end();
			// paint thePlanes
			gl.enable( GL.DEPTH_TEST );
			gl.shadeModel( GL.FLAT );
			for( i = 0; i < MAX_PLANES; i++ )
				if( thePlanes[i].speed != 0.0 ) {
					gl.pushMatrix();
					gl.translate( thePlanes[i].x, thePlanes[i].y, thePlanes[i].z );
					gl.rotate( 290.0, 1.0, 0.0, 0.0 );
					gl.rotate( thePlanes[i].angle, 0.0, 0.0, 1.0 );
					gl.scale( 1.0 / 3.0, 1.0 / 4.0, 1.0 / 4.0 );
					gl.translate( 0.0, -4.0, -1.5 );
					gl.begin( GL.TRIANGLE_STRIP );
					// left wing
					gl.vertex( -7.0, 0.0, 2.0 );
					gl.vertex( -1.0, 0.0, 3.0 );
					gl.color( red = thePlanes[i].red, green = thePlanes[i].green, blue = thePlanes[i].blue );
					gl.vertex( -1.0, 7.0, 3.0 );
					// left side
					gl.color( 0.6 * red, 0.6 * green, 0.6 * blue );
					gl.vertex( 0.0, 0.0, 0.0 );
					gl.vertex( 0.0, 8.0, 0.0 );
					// right side
					gl.vertex( 1.0, 0.0, 3.0 );
					gl.vertex( 1.0, 7.0, 3.0 );
					// final tip of right wing
					gl.color( red, green, blue );
					gl.vertex( 7.0, 0.0, 2.0 );
					gl.end();
					gl.popMatrix();
				}

			gl.flush();
			gl.swap();

			movePlanes();
	}


  public static void main( String args[] ) 
    {       
      Frame f = new Frame();
      planes p = new planes();
      JoglCanvas jc = new JoglCanvas(p);

      f.add("Center", jc);
      
      f.setSize(300, 300);
      f.show();
    }
}


class plane {
	public double speed;		// zero speed means not flying
	public double red, green, blue;
	public double theta;
	public double x, y, z, angle;
}
