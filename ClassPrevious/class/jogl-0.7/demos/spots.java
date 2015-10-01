/*
 * spots.java
 *
 * Copyright (c) 1993, 1994, Silicon Graphics, Inc.
 * Copyright (c) Mark J. Kilgard, 1994.
 *
 * Java version: Copyright (c) Javier Perez, 1997.
 * Jogl version: Tommy Reilly
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


class spots extends JoglRenderer
{
  private static final float PI = (float) Math.PI;
  private static final float TWO_PI = 2f * PI;

	private static float ini_spots[][] = {
		{ 0.2f, 0.0f, 0.0f, 1.0f },  //  ambient
		{ 0.8f, 0.0f, 0.0f, 1.0f },  //  diffuse
		{ 0.4f, 0.0f, 0.0f, 1.0f },  //  specular
		{ 0.0f, 0.0f, 0.0f, 1.0f },  //  position
		{ 0.0f, -1.0f, 0.0f },   //  direction
		{ 20.0f },
		{ 60.0f },             //  exponent, cutoff
		{ 1.0f, 0.0f, 0.0f },    //  attenuation
		{ 0.0f, 1.25f, 0.0f },   //  translation
		{ 0.0f, 0.0f, 0.0f },    //  rotation
		{ 20.0f, 0.0f, 40.0f },  //  swing
		{ 0.0f, 0.0f, 0.0f },    //  arc
		{ TWO_PI / 70.0f, 0.0f, TWO_PI / 140.0f },  //  arc increment

		{ 0.0f, 0.2f, 0.0f, 1.0f },  //  ambient
		{ 0.0f, 0.8f, 0.0f, 1.0f },  //  diffuse
		{ 0.0f, 0.4f, 0.0f, 1.0f },  //  specular
		{ 0.0f, 0.0f, 0.0f, 1.0f },  //  position
		{ 0.0f, -1.0f, 0.0f },   //  direction
		{ 20.0f },
		{ 60.0f },             //  exponent, cutoff
		{ 1.0f, 0.0f, 0.0f },    //  attenuation
		{ 0.0f, 1.25f, 0.0f },   //  translation
		{ 0.0f, 0.0f, 0.0f },    //  rotation
		{ 20.0f, 0.0f, 40.0f },  //  swing
		{ 0.0f, 0.0f, 0.0f },    //  arc
		{ TWO_PI / 120.0f, 0.0f, TWO_PI / 60.0f },  //  arc increment

		{ 0.0f, 0.0f, 0.2f, 1.0f },  //  ambient
		{ 0.0f, 0.0f, 0.8f, 1.0f },  //  diffuse
		{ 0.0f, 0.0f, 0.4f, 1.0f },  //  specular
		{ 0.0f, 0.0f, 0.0f, 1.0f },  //  position
		{ 0.0f, -1.0f, 0.0f },   //  direction
		{ 20.0f },
		{ 60.0f },             //  exponent, cutoff
		{ 1.0f, 0.0f, 0.0f },    //  attenuation
		{ 0.0f, 1.25f, 0.0f },   //  translation
		{ 0.0f, 0.0f, 0.0f },    //  rotation
		{ 20.0f, 0.0f, 40.0f },  //  swing
		{ 0.0f, 0.0f, 0.0f },    //  arc
		{ TWO_PI / 50.0f, 0.0f, TWO_PI / 100.0f }  //  arc increment
	};


	private final float modelAmb[] = { 0.2f, 0.2f, 0.2f, 1.0f };

	private final float matAmb[] = { 0.2f, 0.2f, 0.2f, 1.0f };
	private final float matDiff[] = { 0.8f, 0.8f, 0.8f, 1.0f };
	private final float matSpec[] = { 0.4f, 0.4f, 0.4f, 1.0f };
	private final float matEmission[] = { 0.0f, 0.0f, 0.0f, 1.0f };

	private final int NUM_LIGHTS = 3;

	private Light spots[];

	private boolean useSAME_AMB_SPEC = true;
	private double spin = 0;


	void initLights() {
		int k;

		for(  k = 0; k < NUM_LIGHTS; ++k  ) {
			int lt = GL.LIGHT0 + k;
			Light light = spots[k];

			gl.enable(  lt  );
			gl.light(  lt, GL.AMBIENT, light.amb  );
			gl.light( lt, GL.DIFFUSE, light.diff );

			if( useSAME_AMB_SPEC )
				gl.light( lt, GL.SPECULAR, light.amb );
			else
				gl.light( lt, GL.SPECULAR, light.spec );

			gl.light( lt, GL.SPOT_EXPONENT, light.spotExp );
			gl.light( lt, GL.SPOT_CUTOFF, light.spotCutoff );
			gl.light( lt, GL.CONSTANT_ATTENUATION, light.atten[0] );
			gl.light( lt, GL.LINEAR_ATTENUATION, light.atten[1] );
			gl.light( lt, GL.QUADRATIC_ATTENUATION, light.atten[2] );
		}
	}


	void aimLights() {
		int k;

		for( k = 0; k < NUM_LIGHTS; ++k ) {
			Light light = spots[k];

			light.rot[0] = light.swing[0] * (float) Math.sin( light.arc[0] );
			light.arc[0] += light.arcIncr[0];
			if( light.arc[0] > TWO_PI )
				light.arc[0] -= TWO_PI;

			light.rot[1] = light.swing[1] * (float) Math.sin( light.arc[1] );
			light.arc[1] += light.arcIncr[1];
			if( light.arc[1] > TWO_PI )
				light.arc[1] -= TWO_PI;

			light.rot[2] = light.swing[2] * (float) Math.sin( light.arc[2] );
			light.arc[2] += light.arcIncr[2];
			if( light.arc[2] > TWO_PI )
				light.arc[2] -= TWO_PI;
		}
	}


	void setLights() {
		int k;

		for( k = 0; k < NUM_LIGHTS; ++k ) {
			int lt = GL.LIGHT0 + k;
			Light light = spots[k];

			gl.pushMatrix();
			gl.translate( light.trans[0], light.trans[1], light.trans[2] );
			gl.rotate( light.rot[0], 1, 0, 0 );
			gl.rotate( light.rot[1], 0, 1, 0 );
			gl.rotate( light.rot[2], 0, 0, 1 );
			gl.light( lt, GL.POSITION, light.pos );
			gl.light( lt, GL.SPOT_DIRECTION, light.spotDir );
			gl.popMatrix();
		}
	}


	void drawLights() {
		int k;

		gl.disable( GL.LIGHTING );
		for( k = 0; k < NUM_LIGHTS; ++k ) {
			Light light = spots[k];

			//gl.color( light.diff );
			gl.color( light.diff[0], light.diff[1], light.diff[2], light.diff[3] );

			gl.pushMatrix();
			gl.translate( light.trans[0], light.trans[1], light.trans[2] );
			gl.rotate( light.rot[0], 1, 0, 0 );
			gl.rotate( light.rot[1], 0, 1, 0 );
			gl.rotate( light.rot[2], 0, 0, 1 );
			gl.begin( GL.LINES );
			gl.vertex( light.pos[0], light.pos[1], light.pos[2] );
			gl.vertex( light.spotDir[0], light.spotDir[1], light.spotDir[2] );
			gl.end();
			gl.popMatrix();
		}
		gl.enable( GL.LIGHTING );
	}


	void drawPlane( int w, int h ) {
		int i, j;
		double dw = 1.0 / w;
		double dh = 1.0 / h;

		gl.normal( 0.0, 0.0, 1.0 );
		for( j = 0; j < h; ++j ) {
			gl.begin( GL.TRIANGLE_STRIP );
			for( i = 0; i <= w; ++i ) {
				gl.vertex( dw * i, dh * ( j + 1 ) );
				gl.vertex( dw * i, dh * j );
			}
		gl.end();
		}
	}


	public void init() {
		int l, i;


		spots = new Light[NUM_LIGHTS];
		i = 0;
		for( l = 0; l < NUM_LIGHTS; l++ ) {
			ini_spot( l, i );
			i += 13;
		}

		gl.matrixMode( GL.PROJECTION );
		gl.frustum( -1, 1, -1, 1, 2, 6 );

		gl.matrixMode( GL.MODELVIEW );
		gl.translate( 0.0, 0.0, -3.0 );
		gl.rotate( 45.0, 1, 0, 0 );

		gl.enable( GL.LIGHTING );
		gl.enable( GL.NORMALIZE );

		gl.lightModel( GL.LIGHT_MODEL_AMBIENT, modelAmb );
		gl.lightModel( GL.LIGHT_MODEL_LOCAL_VIEWER, GL.TRUE );
		gl.lightModel( GL.LIGHT_MODEL_TWO_SIDE, GL.FALSE );

		gl.material( GL.FRONT, GL.AMBIENT, matAmb );
		gl.material( GL.FRONT, GL.DIFFUSE, matDiff );
		gl.material( GL.FRONT, GL.SPECULAR, matSpec );
		gl.material( GL.FRONT, GL.EMISSION, matEmission );
		gl.material( GL.FRONT, GL.SHININESS, 10.0f );

		initLights();
	}


	void ini_spot( int l, int i ) {

        spots[l] = new Light();
		Light light = spots[l];

		light.amb = new float[4];
		light.amb = ini_spots[i + 0];

		light.diff = new float[4];
		light.diff = ini_spots[i + 1];

		light.spec = new float[4];
		light.spec = ini_spots[i + 2];

		light.pos = new float[4];
		light.pos = ini_spots[i + 3];

		light.spotDir = new float[3];
		light.spotDir = ini_spots[i + 4];

		light.spotExp = ini_spots[i + 5][0];
		light.spotCutoff = ini_spots[i + 6][0];

		light.atten = new float[3];
		light.atten = ini_spots[i + 7];

		light.trans = new float[3];
		light.trans = ini_spots[i + 8];

		light.rot = new float[3];
		light.rot = ini_spots[i + 9];

		light.swing = new float[3];
		light.swing = ini_spots[i + 10];

		light.arc = new float[3];
		light.arc = ini_spots[i + 11];

		light.arcIncr = new float[3];
		light.arcIncr = ini_spots[i + 12];
	}


	public void display() {

          gl.clear( GL.COLOR_BUFFER_BIT );

          gl.pushMatrix();
			gl.rotate( spin, 0, 1, 0 );

			aimLights();
			setLights();

			gl.pushMatrix();
			gl.rotate( -90.0, 1, 0, 0 );
			gl.scale( 1.9, 1.9, 1.0 );
			gl.translate( -0.5, -0.5, 0.0 );
			drawPlane( 16, 16 );
			gl.popMatrix();

			drawLights();
			gl.popMatrix();

			gl.swap();
			gl.flush();

			spin += 0.5;
			if( spin > 360.0 )
				spin -= 360.0;
	}

	public static void main( String args[] ) 
	{
	  spots s = new spots();
	  JoglCanvas jc = new JoglCanvas(s);
	  Frame f = new Frame();
    
	  jc.setSize(300, 300);
	  f.setSize(300, 300);
    
	  f.add("Center", jc);
	  f.show();
	}
}


class Light {
	float amb[];
	float diff[];
	float spec[];
	float pos[];
	float spotDir[];
	float spotExp;
	float spotCutoff;
	float atten[];

	float trans[];
	float rot[];
	float swing[];
	float arc[];
	float arcIncr[];
}
