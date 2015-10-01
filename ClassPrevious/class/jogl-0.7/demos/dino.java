/*
 * dino.java
 *
 * Copyright (c) 1993, 1994, Silicon Graphics, Inc.
 * Copyright (c) Mark J. Kilgard, 1994.
 *
 * Java version: Copyright (c) Javier Perez, 1997.
 *
 */

package demos;

import jogl.*;
import jogl.glu.*;
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


class dino extends JoglRenderer implements MouseListener, MouseMotionListener
{
	final int RESERVED		= 0;
	final int BODY_SIDE		= 1;
	final int BODY_EDGE		= 2;
	final int BODY_WHOLE	= 3;
	final int ARM_SIDE		= 4;
	final int ARM_EDGE		= 5;
	final int ARM_WHOLE		= 6;
	final int LEG_SIDE		= 7;
	final int LEG_EDGE		= 8;
	final int LEG_WHOLE		= 9;
	final int EYE_SIDE		= 10;
	final int EYE_EDGE		= 11;
	final int EYE_WHOLE		= 12;
	final int DINOSAUR		= 13;


	double bodyWidth = 2.0;
	boolean spinning = false, moving = false;
	boolean scaling;
	boolean newModel = true;
	int beginx, beginy;
	float curquat[];
	float lastquat[];
	float scalefactor = 1.0f;

	int W = 300, H = 300;

	float body[][] = { {0f, 3f}, {1f, 1f}, {5f, 1f}, {8f, 4f}, {10f, 4f}, {11f, 5f},
					   {11f, 11.5f}, {13f, 12f}, {13f, 13f}, {10f, 13.5f}, {13f, 14f},
					   {13f, 15f}, {11f, 16f}, {8f, 16f}, {7f, 15f}, {7f, 13f},
					   {8f, 12f},  {7f, 11f}, {6f, 6f}, {4f, 3f}, {3f, 2f}, {1f, 2f} };
	float arm[][] = { {8f, 10f}, {9f, 9f}, {10f, 9f}, {13f, 8f}, {14f, 9f}, {16f, 9f},
					  {15f, 9.5f}, {16f, 10f}, {15f, 10f}, {15.5f, 11f}, {14.5f, 10f},
					  {14f, 11f}, {14f, 10f}, {13f, 9f}, {11f, 11f}, {9f, 11f} };
	float leg[][] = { {8f, 6f}, {8f, 4f}, {9f, 3f}, {9f, 2f}, {8f, 1f}, {8f, 0.5f}, {9f, 0f},
					  {12f, 0f}, {10f, 1f}, {10f, 2f}, {12f, 4f}, {11f, 6f}, {10f, 7f}, {9f, 7f} };
	float eye[][] = { {8.75f, 15f}, {9f, 14.7f}, {9.6f, 14.7f},
					  {10.1f, 15f}, {9.6f, 15.25f}, {9f, 15.25f} };
	float lightZeroPosition[] = { 10.0f, 4.0f, 10.0f, 1.0f };
	float lightZeroColor[] = { 0.8f, 1.0f, 0.8f, 1.0f }; // green-tinted
	float lightOnePosition[] = { -1.0f, -2.0f, 1.0f, 0.0f };
	float lightOneColor[] = { 0.6f, 0.3f, 0.2f, 1.0f }; // red-tinted
	float skinColor[] = { 0.1f, 1.0f, 0.1f, 1.0f };
	float eyeColor[] = { 1.0f, 0.2f, 0.2f, 1.0f };


	Tesselator tobj;

	void begin( int type ) {

		//System.out.println( "begin: " + type );
		gl.begin( type );
	}

	void vertex( float v[] ) {
	
		//System.out.println( "vertex: " + v[0] + " - " + v[1] );
		gl.vertex( v[0], v[1] );
	}

	void end() {

		//System.out.println( "end" );
		gl.end();
	}

	void extrudeSolidFromPolygon( float[] data[], double thickness, int side, int edge, int whole )
	{
		double vertex[] = new double[3];
		double dx, dy, len;
		int i;
		int count = data.length;

		if( tobj == null ) {
			// create and initialize a GLU polygon tesselation object
			tobj = new Tesselator();
			tobj.tessCallback( GLU.BEGIN, gl, "begin" );
			tobj.tessCallback( GLU.VERTEX, this, "vertex" );  // semi-tricky
			tobj.tessCallback( GLU.END, gl, "end" );
		}
		gl.newList( side, GL.COMPILE );
		gl.shadeModel( GL.SMOOTH );  // smooth minimizes seeing tessellation
		tobj.beginPolygon();
		for( i = 0; i < count; i++ ) {
			vertex[0] = data[i][0];
			vertex[1] = data[i][1];
			vertex[2] = 0.1;
			tobj.tessVertex( vertex, data[i] );
			//System.out.println( "data: " + data[i][0] + " - " + data[i][1] );
		}
		tobj.endPolygon();
		gl.endList();
		gl.newList( edge, GL.COMPILE );
		gl.shadeModel( GL.FLAT );  // flat shade keeps angular hands from being "smoothed"
		gl.begin( GL.QUAD_STRIP );
		for( i = 0; i <= count; i++ ) {
			// mod function handles closing the edge
			gl.vertex( data[i % count][0], data[i % count][1], 0.0f );
			gl.vertex( data[i % count][0], data[i % count][1], (float) thickness );
			// Calculate a unit normal by dividing by Euclidean
			// distance. We could be lazy and use
			// gl.enable( GL.NORMALIZE ) so we could pass in arbitrary
			// normals for a very slight performance hit.
			dx = data[( i + 1 ) % count][1] - data[i % count][1];
			dy = data[i % count][0] - data[( i + 1 ) % count][0];
			len = Math.sqrt( dx * dx + dy * dy );
			gl.normal( dx / len, dy / len, 0.0 );
		}
		gl.end();
		gl.endList();
		gl.newList( whole, GL.COMPILE );
		gl.frontFace( GL.CW );
		gl.callList( edge );
		gl.normal( 0.0f, 0.0f, -1.0f );  // constant normal for side
		gl.callList( side );
		gl.pushMatrix();
		gl.translate( 0.0f, 0.0f, (float) thickness );
		gl.frontFace( GL.CCW );
		gl.normal( 0.0f, 0.0f, 1.0f );  // opposite normal for other side
		gl.callList( side );
		gl.popMatrix();
		gl.endList();
	}


	void makeDinosaur()
	{
		float bodyWidth = 3.0f;

		extrudeSolidFromPolygon( body, bodyWidth, BODY_SIDE, BODY_EDGE, BODY_WHOLE );
		extrudeSolidFromPolygon( arm, bodyWidth / 4, ARM_SIDE, ARM_EDGE, ARM_WHOLE );
		extrudeSolidFromPolygon( leg, bodyWidth / 2, LEG_SIDE, LEG_EDGE, LEG_WHOLE );
		extrudeSolidFromPolygon( eye, bodyWidth + 0.2, EYE_SIDE, EYE_EDGE, EYE_WHOLE );
		gl.newList( DINOSAUR, GL.COMPILE );
		gl.material( GL.FRONT, GL.DIFFUSE, skinColor );
		gl.callList( BODY_WHOLE );
		gl.pushMatrix();
		gl.translate( 0.0f, 0.0f, bodyWidth );
		gl.callList( ARM_WHOLE );
		gl.callList( LEG_WHOLE );
		gl.translate( 0.0f, 0.0f, -bodyWidth - bodyWidth / 4 );
		gl.callList( ARM_WHOLE );
		gl.translate( 0.0f, 0.0f, -bodyWidth / 4 );
		gl.callList( LEG_WHOLE );
		gl.translate( 0.0f, 0.0f, bodyWidth / 2 - 0.1f );
		gl.material( GL.FRONT, GL.DIFFUSE, eyeColor );
		gl.callList( EYE_WHOLE );
		gl.popMatrix();
		gl.endList();
	}


	void recalcModelView()
	{
		float m[] = new float[16];

		gl.popMatrix();
		gl.pushMatrix();
		Trackball.build_rotmatrix( m, curquat );
		gl.multMatrix( m );
		gl.scale( scalefactor, scalefactor, scalefactor );
		gl.translate( -8f, -8f, (float) ( -bodyWidth / 2 ) );
		newModel = false;
	}


	public void init()
	{

		curquat = new float[4];
		lastquat = new float[4];

		java.awt.Dimension d = gl.getSize();

		Trackball.trackball( curquat, 0.0f, 0.0f, 0.0f, 0.0f );

		makeDinosaur();
		gl.enable( GL.CULL_FACE );
		gl.enable( GL.DEPTH_TEST );
		gl.enable( GL.LIGHTING );
		gl.matrixMode( GL.PROJECTION );
		gl.perspective( 40.0, 1.0, 1.0, 40.0 );
		gl.matrixMode( GL.MODELVIEW );
		gl.lookAt( 0.0, 0.0, 30.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 );
		gl.pushMatrix();
		gl.lightModel( GL.LIGHT_MODEL_LOCAL_VIEWER, 1 );
		gl.light( GL.LIGHT0, GL.POSITION, lightZeroPosition );
		gl.light( GL.LIGHT0, GL.DIFFUSE, lightZeroColor );
		gl.light( GL.LIGHT0, GL.CONSTANT_ATTENUATION, 0.1f );
		gl.light( GL.LIGHT0, GL.LINEAR_ATTENUATION, 0.05f );
		gl.light( GL.LIGHT1, GL.POSITION, lightOnePosition );
		gl.light( GL.LIGHT1, GL.DIFFUSE, lightOneColor );
		gl.enable( GL.LIGHT0 );
		gl.enable( GL.LIGHT1 );
		gl.lineWidth( 2.0f );
	}


	public void display()
	{

		if( newModel )
			recalcModelView();
		gl.clear( GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT );
		gl.callList( DINOSAUR );

		gl.flush();
		gl.swap();
	}


  public static void main( String args[] ) 
    {
      Frame f = new Frame();
      dino d = new dino();
      JoglCanvas jc = new JoglCanvas(d);

      f.setSize(300,300);
      
      f.add("Center", jc);
      jc.addMouseListener(d);
      jc.addMouseMotionListener(d);

      f.show();
      
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

		moving = true;
		beginx = evt.getX();
		beginy = evt.getY();

		/*if( evt.shiftDown() )
			scaling = true;
		else*/
			scaling = false;
	}

	public void mouseReleased( MouseEvent evt )
	{

		moving = false;
	}

	public void mouseDragged( MouseEvent evt )
	{

		if( scaling ) {
			scalefactor = scalefactor * ( 1.0f + ( ( (float) ( beginy - evt.getY() ) ) / H ) );
			beginx = evt.getX();
			beginy = evt.getY();
			newModel = true;
                        display();
		}

		if( moving ) {
			Trackball.trackball( lastquat,
								 ( 2.0f * beginx - W ) / W,
								 ( H - 2.0f * beginy ) / H,
								 ( 2.0f * evt.getX() - W ) / W,
								 ( H - 2.0f * evt.getY() ) / H );
			beginx = evt.getX();
			beginy = evt.getY();
			spinning = true;

			Trackball.add_quats( lastquat, curquat, curquat );
			newModel = true;
                        display();
		}
	}

	public void mouseMoved( MouseEvent evt )
	{
	}
  
}
