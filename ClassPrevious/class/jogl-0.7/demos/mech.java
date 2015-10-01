/*
 * mech.java
 *
 * Copyright (c) Simon Parkinson-Bates.
 *
 * Java version: Copyright (c) Javier Perez, 1997.
 *
 */

package demos;


import jogl.*;
import jogl.glu.*;
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


class mech extends Frame implements MouseListener, MouseMotionListener
{
	JoglCanvas oglCanvas;

	// start of display list definitions
	private final int SOLID_MECH_TORSO			= 1;
	private final int SOLID_MECH_HIP			= 2;
	private final int SOLID_MECH_SHOULDER		= 3;
	private final int SOLID_MECH_UPPER_ARM		= 4;
	private final int SOLID_MECH_FOREARM		= 5;
	private final int SOLID_MECH_UPPER_LEG		= 6;
	private final int SOLID_MECH_FOOT			= 7;
	private final int SOLID_MECH_ROCKET			= 8;
	private final int SOLID_MECH_VULCAN			= 9;
	private final int SOLID_ENVIRO				= 10;
	// end of display list definitions

	// start of motion rate variables
	private final int ANKLE_RATE				= 3;
	private final int HEEL_RATE					= 3;
	private final int ROTATE_RATE				= 10;
	private final int TILT_RATE					= 10;
	private final int ELBOW_RATE				= 2;
	private final int SHOULDER_RATE				= 5;
	private final int LAT_RATE					= 5;
	private final int CANNON_RATE				= 40;
	private final int UPPER_LEG_RATE			= 3;
	private final int UPPER_LEG_RATE_GROIN		= 10;
	private final int LIGHT_TURN_RATE			= 10;
	private final int VIEW_TURN_RATE			= 10;
	// end of motion rate variables

	// start of motion  variables
	boolean leg = false;
	boolean solid_part = true;
	int step = 0;

	int shoulder1 = 0, shoulder2 = 0, shoulder3 = 0, shoulder4 = 0, lat1 = 20, lat2 = 20,
		elbow1 = 0, elbow2 = 0, pivot = 0, tilt = 10, ankle1 = 0, ankle2 = 0, heel1 = 0,
		heel2 = 0, hip11 = 0, hip12 = 10, hip21 = 0, hip22 = 10, fire = 0,
		anim = 0, turn = 0, turn1 = 0, lightturn = 0, lightturn1 = 0;

	double elevation = 0.0, distance = 0.0, frame = 3.0;
	// end of motion variables

	// start of material definitions
	float mat_specular[] = { 0.628281f, 0.555802f, 0.366065f, 1.0f };
	float mat_ambient[] = { 0.24725f, 0.1995f, 0.0745f, 1.0f };
	float mat_diffuse[] = { 0.75164f, 0.60648f, 0.22648f, 1.0f };
	float mat_shininess[] = { 128.0f * 0.4f };

	float mat_specular2[] = { 0.508273f, 0.508273f, 0.508373f };
	float mat_ambient2[] = { 0.19225f, 0.19225f, 0.19225f };
	float mat_diffuse2[] = { 0.50754f, 0.50754f, 0.50754f };
	float mat_shininess2[] = { 128.0f * 0.6f };

	float mat_specular3[] = { 0.296648f, 0.296648f, 0.296648f };
	float mat_ambient3[] = { 0.25f, 0.20725f, 0.20725f };
	float mat_diffuse3[] = { 1f, 0.829f, 0.829f };
	float mat_shininess3[] = { 128.0f * 0.088f };

	float mat_specular4[] = { 0.633f, 0.727811f, 0.633f };
	float mat_ambient4[] = { 0.0215f, 0.1745f, 0.0215f };
	float mat_diffuse4[] = { 0.07568f, 0.61424f, 0.07568f };
	float mat_shininess4[] = { 128f * 0.6f };

	float mat_specular5[] = { 0.60f, 0.60f, 0.50f };
	float mat_ambient5[] = { 0.0f, 0.0f, 0.0f };
	float mat_diffuse5[] = { 0.5f, 0.5f, 0.0f };
	float mat_shininess5[] = { 128.0f * 0.25f };

	// end of material definitions

	// start of geometric shape functions
	void Box( double width, double height, double depth, boolean solid ) {
		char i, j = 0;
		double x = width / 2.0f, y = height / 2.0f, z = depth / 2.0f;

		for( i = 0; i < 4; i++ ) {
			oglCanvas.rotate( 90.0, 0.0, 0.0, 1.0 );
			if( j != 0 ) {
				if( !solid )
					oglCanvas.begin( GL.LINE_LOOP );
				else
					oglCanvas.begin( GL.QUADS );
				oglCanvas.normal( -1.0, 0.0, 0.0 );
				oglCanvas.vertex( -x, y, z );
				oglCanvas.vertex( -x, -y, z );
				oglCanvas.vertex( -x, -y, -z );
				oglCanvas.vertex( -x, y, -z );
				oglCanvas.end();
				if( solid )
					oglCanvas.begin( GL.TRIANGLES );
				oglCanvas.normal( 0.0, 0.0, 1.0 );
				oglCanvas.vertex( 0.0, 0.0, z );
				oglCanvas.vertex( -x, y, z );
				oglCanvas.vertex( -x, -y, z );
				oglCanvas.normal( 0.0, 0.0, -1.0 );
				oglCanvas.vertex( 0.0, 0.0, -z );
				oglCanvas.vertex( -x, -y, -z );
				oglCanvas.vertex( -x, y, -z );
				oglCanvas.end();
				j = 0;
			} else {
				if( !solid )
					oglCanvas.begin( GL.LINE_LOOP );
				else
					oglCanvas.begin( GL.QUADS );
				oglCanvas.normal( -1.0, 0.0, 0.0 );
				oglCanvas.vertex( -y, x, z );
				oglCanvas.vertex( -y, -x, z );
				oglCanvas.vertex( -y, -x, -z );
				oglCanvas.vertex( -y, x, -z );
				oglCanvas.end();
				if( solid )
					oglCanvas.begin( GL.TRIANGLES );
				oglCanvas.normal( 0.0, 0.0, 1.0 );
				oglCanvas.vertex( 0.0, 0.0, z );
				oglCanvas.vertex( -y, x, z );
				oglCanvas.vertex( -y, -x, z );
				oglCanvas.normal( 0.0, 0.0, -1.0 );
				oglCanvas.vertex( 0.0, 0.0, -z );
				oglCanvas.vertex( -y, -x, -z );
				oglCanvas.vertex( -y, x, -z );
				oglCanvas.end();
				j = 1;
			}
		}
	}


	static Quadric quadObj;

	static void initQuadObj() {
		if( quadObj == null )
			try 
			{
				quadObj = new Quadric();
			}
			catch( JoglNativeException e ) 
			{
				System.out.println( "can't create Quadric object" );
				System.exit( -1 );
			}
	}

	
	Quadric getQuadric() {

		Quadric qobj = null;
	
		try 
		{
			qobj = new Quadric();
		}
		catch( JoglNativeException e ) 
		{
			System.out.println( "can't create Quadric object" + e);
			System.exit( -1 );
		}

		return qobj;
	}

	void WireSphere( double radius, int slices, int stacks ) {

		initQuadObj();
		quadObj.quadricDrawStyle( GLU.LINE );
		quadObj.quadricNormals( GLU.SMOOTH );
		quadObj.sphere( radius, slices, stacks );
	}

	void SolidSphere( double radius, int slices, int stacks ) {

		initQuadObj();
		quadObj.quadricDrawStyle( GLU.FILL );
		quadObj.quadricNormals( GLU.SMOOTH );
		quadObj.sphere( radius, slices, stacks );
	}

	void Octagon( double side, double height, boolean solid ) {
		int j;
		double x = Math.sin( 0.785398163f ) * side, y = side / 2.0f, z = height / 2.0f, c;

		c = x + y;
		for( j = 0; j < 8; j++ ) {
			oglCanvas.translate( -c, 0.0, 0.0 );
			if( !solid )
				oglCanvas.begin( GL.LINE_LOOP );
			else
				oglCanvas.begin( GL.QUADS );
			oglCanvas.normal( -1.0, 0.0, 0.0 );
			oglCanvas.vertex( 0.0, -y, z );
			oglCanvas.vertex( 0.0, y, z );
			oglCanvas.vertex( 0.0, y, -z );
			oglCanvas.vertex( 0.0, -y, -z );
			oglCanvas.end();
			oglCanvas.translate( c, 0.0, 0.0 );
			if( solid ) {
				oglCanvas.begin( GL.TRIANGLES );
				oglCanvas.normal( 0.0, 0.0, 1.0 );
				oglCanvas.vertex( 0.0, 0.0, z );
				oglCanvas.vertex( -c, -y, z );
				oglCanvas.vertex( -c, y, z );
				oglCanvas.normal( 0.0, 0.0, -1.0 );
				oglCanvas.vertex( 0.0, 0.0, -z );
				oglCanvas.vertex( -c, y, -z );
				oglCanvas.vertex( -c, -y, -z );
				oglCanvas.end();
			}
			oglCanvas.rotate( 45.0, 0.0, 0.0, 1.0 );
		}
	}

	// end of geometric shape functions

	void Normalize( float v[] ) {
		float d = (float) Math.sqrt( v[1] * v[1] + v[2] * v[2] + v[3] * v[3] );

		if( d == 0.0 ) {
			System.out.println( "zero length vector" );
			return;
		}
		v[1] /= d;
		v[2] /= d;
		v[3] /= d;
	}

	void NormXprod( float v1[], float v2[], float v[], float out[] ) {
		int i, j;
		float length;

		out[0] = v1[1] * v2[2] - v1[2] * v2[1];
		out[1] = v1[2] * v2[0] - v1[0] * v2[2];
		out[2] = v1[0] * v2[1] - v1[1] * v2[0];
		Normalize( out );
	}

    void SetMaterial( float spec[], float amb[], float diff[], float shin[] ) {

		oglCanvas.material( GL.FRONT, GL.SPECULAR, spec );
		oglCanvas.material( GL.FRONT, GL.SHININESS, shin );
		oglCanvas.material( GL.FRONT, GL.AMBIENT, amb );
		oglCanvas.material( GL.FRONT, GL.DIFFUSE, diff );
	}

	void MechTorso( boolean solid ) {

		oglCanvas.newList( SOLID_MECH_TORSO, GL.COMPILE );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		Box( 1.0, 1.0, 3.0, solid );
		oglCanvas.translate( 0.75, 0.0, 0.0 );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		Box( 0.5, 0.6, 2.0, solid );
		oglCanvas.translate( -1.5, 0.0, 0.0 );
		Box( 0.5, 0.6, 2.0, solid );
		oglCanvas.translate( 0.75, 0.0, 0.0 );
		oglCanvas.endList();
	}

	void MechHip( boolean solid ) {
		int i;
		Quadric hip[] = new Quadric[2];
		
		oglCanvas.newList( SOLID_MECH_HIP, GL.COMPILE );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		Octagon( 0.7, 0.5, solid );
		for( i = 0; i < 2; i++ ) {
			if( i != 0 )
				oglCanvas.scale( -1.0, 1.0, 1.0 );
			oglCanvas.translate( 1.0, 0.0, 0.0 );
			hip[i] = getQuadric();
			SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
			oglCanvas.color( 0.5, 0.5, 0.5 );
			if( !solid )
				hip[i].quadricDrawStyle( GLU.LINE );
			hip[0].sphere( 0.2, 16, 16 );
			oglCanvas.translate( -1.0, 0.0, 0.0 );
		}
		oglCanvas.scale( -1.0, 1.0, 1.0 );
		oglCanvas.endList();
	}

	void Shoulder( boolean solid ) {
		Quadric deltoid = getQuadric();

		oglCanvas.newList( SOLID_MECH_SHOULDER, GL.COMPILE );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		Box( 1.0, 0.5, 0.5, solid );
		oglCanvas.translate( 0.9, 0.0, 0.0 );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		if( !solid )
			deltoid.quadricDrawStyle( GLU.LINE );
		deltoid.sphere( 0.6, 16, 16 );
		oglCanvas.translate( -0.9, 0.0, 0.0 );
		oglCanvas.endList();
	}

	void UpperArm( boolean solid ) {
		Quadric upper = getQuadric();
		Quadric joint[] = new Quadric[2];
		Quadric joint1[] = new Quadric[2];
		int i;

		oglCanvas.newList( SOLID_MECH_UPPER_ARM, GL.COMPILE );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		Box( 1.0, 2.0, 1.0, solid );
		oglCanvas.translate( 0.0, -0.95, 0.0 );
		oglCanvas.rotate( 90.0, 1.0, 0.0, 0.0 );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		if( !solid )
		upper.quadricDrawStyle( GLU.LINE );
		upper.cylinder( 0.4, 0.4, 1.5, 16, 10 );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		oglCanvas.rotate( -90.0, 1.0, 0.0, 0.0 );
		oglCanvas.translate( -0.4, -1.85, 0.0 );
		oglCanvas.rotate( 90.0, 0.0, 1.0, 0.0 );
		for( i = 0; i < 2; i++ ) {
			joint[i] = getQuadric();
			if( !solid )
				joint[i].quadricDrawStyle( GLU.LINE );
			if( i != 0 )
				joint[i].cylinder( 0.5, 0.5, 0.8, 16, 10 );
			else
				joint[i].cylinder( 0.2, 0.2, 0.8, 16, 10 );
		}
		for( i = 0; i < 2; i++ ) {
			if( i != 0 )
				oglCanvas.scale( -1.0, 1.0, 1.0 );
			joint1[i] = getQuadric();
			if( !solid )
				joint1[i].quadricDrawStyle( GLU.LINE );
			if( i != 0 )
				oglCanvas.translate( 0.0, 0.0, 0.8 );
			joint1[i].disk( 0.2, 0.5, 16, 10 );
			if( i != 0 )
				oglCanvas.translate( 0.0, 0.0, -0.8 );
		}
		oglCanvas.scale( -1.0, 1.0, 1.0 );
		oglCanvas.rotate( -90.0, 0.0, 1.0, 0.0 );
		oglCanvas.translate( 0.4, 2.9, 0.0 );
		oglCanvas.endList();
	}

	void VulcanGun( boolean solid ) {
		int i;
		Quadric Barrel[] = new Quadric[5];
		Quadric BarrelFace[] = new Quadric[5];
		Quadric Barrel2[] = new Quadric[5];
		Quadric Barrel3[] = new Quadric[5];
		Quadric BarrelFace2[] = new Quadric[5];
		Quadric Mount = getQuadric();
		Quadric Mount_face = getQuadric();

		oglCanvas.newList( SOLID_MECH_VULCAN, GL.COMPILE );

		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );

		if( !solid ) {
			Mount.quadricDrawStyle( GLU.LINE );
			Mount_face.quadricDrawStyle( GLU.LINE );
		}
		Mount.cylinder( 0.5, 0.5, 0.5, 16, 10 );
		oglCanvas.translate( 0.0, 0.0, 0.5 );
		Mount_face.disk( 0.0, 0.5, 16, 10 );

		for( i = 0; i < 5; i++ ) {
			Barrel[i] = getQuadric();
			BarrelFace[i] = getQuadric();
			BarrelFace2[i] = getQuadric();
			Barrel2[i] = getQuadric();
			Barrel3[i] = getQuadric();
			oglCanvas.rotate( 72.0, 0.0, 0.0, 1.0 );
			oglCanvas.translate( 0.0, 0.3, 0.0 );
			if( !solid ) {
				Barrel[i].quadricDrawStyle( GLU.LINE );
				BarrelFace[i].quadricDrawStyle( GLU.LINE );
				BarrelFace2[i].quadricDrawStyle( GLU.LINE );
				Barrel2[i].quadricDrawStyle( GLU.LINE );
				Barrel3[i].quadricDrawStyle( GLU.LINE );
			}
			Barrel[i].cylinder( 0.15, 0.15, 2.0, 16, 10 );
			Barrel3[i].cylinder( 0.06, 0.06, 2.0, 16, 10 );
			oglCanvas.translate( 0.0, 0.0, 2.0 );
			BarrelFace[i].disk( 0.1, 0.15, 16, 10 );
			Barrel2[i].cylinder( 0.1, 0.1, 0.1, 16, 5 );
			oglCanvas.translate( 0.0, 0.0, 0.1 );
			BarrelFace2[i].disk( 0.06, 0.1, 16, 5 );
			oglCanvas.translate( 0.0, -0.3, -2.1 );
		}
		oglCanvas.endList();
	}

	void ForeArm( boolean solid ) {
		char i;

		oglCanvas.newList( SOLID_MECH_FOREARM, GL.COMPILE );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		for( i = 0; i < 5; i++ ) {
			oglCanvas.translate( 0.0, -0.1, -0.15 );
			Box( 0.6, 0.8, 0.2, solid );
			oglCanvas.translate( 0.0, 0.1, -0.15 );
			Box( 0.4, 0.6, 0.1, solid );
		}
		oglCanvas.translate( 0.0, 0.0, 2.45 );
		Box( 1.0, 1.0, 2.0, solid );
		oglCanvas.translate( 0.0, 0.0, -1.0 );
		oglCanvas.endList();
	}

	void UpperLeg( boolean solid ) {
		int i;
		Quadric Hamstring = getQuadric();
		Quadric Knee = getQuadric();
		Quadric joint[] = new Quadric[2];

		oglCanvas.newList( SOLID_MECH_UPPER_LEG, GL.COMPILE );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		if( !solid ) {
			Hamstring.quadricDrawStyle( GLU.LINE );
			Knee.quadricDrawStyle( GLU.LINE );
		}
		oglCanvas.translate( 0.0, -1.0, 0.0 );
		Box( 0.4, 1.0, 0.7, solid );
		oglCanvas.translate( 0.0, -0.65, 0.0 );
		for( i = 0; i < 5; i++ ) {
			Box( 1.2, 0.3, 1.2, solid );
			oglCanvas.translate( 0.0, -0.2, 0.0 );
			Box( 1.0, 0.1, 1.0, solid );
			oglCanvas.translate( 0.0, -0.2, 0.0 );
		}
		oglCanvas.translate( 0.0, -0.15, -0.4 );
		Box( 2.0, 0.5, 2.0, solid );
		oglCanvas.translate( 0.0, -0.3, -0.2 );
		oglCanvas.rotate( 90.0, 1.0, 0.0, 0.0 );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		Hamstring.cylinder( 0.6, 0.6, 3.0, 16, 10 );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		oglCanvas.rotate( -90.0, 1.0, 0.0, 0.0 );
		oglCanvas.translate( 0.0, -1.5, 1.0 );
		Box( 1.5, 3.0, 0.5, solid );
		oglCanvas.translate( 0.0, -1.75, -0.8 );
		Box( 2.0, 0.5, 2.0, solid );
		oglCanvas.translate( 0.0, -0.9, -0.85 );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		Knee.cylinder( 0.8, 0.8, 1.8, 16, 10 );
		for( i = 0; i < 2; i++ ) {
			if( i != 0 )
				oglCanvas.scale( -1.0, 1.0, 1.0 );
			joint[i] = getQuadric();
			if( !solid )
				joint[i].quadricDrawStyle( GLU.LINE );
			if( i != 0 )
				oglCanvas.translate( 0.0, 0.0, 1.8 );
			joint[i].disk( 0.0, 0.8, 16, 10 );
			if( i != 0 )
				oglCanvas.translate( 0.0, 0.0, -1.8 );
		}
		oglCanvas.scale( -1.0, 1.0, 1.0 );
		oglCanvas.endList();
	}

	void Foot( boolean solid ) {

		oglCanvas.newList( SOLID_MECH_FOOT, GL.COMPILE );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		oglCanvas.rotate( 90.0, 1.0, 0.0, 0.0 );
		Octagon( 1.5, 0.6, solid );
		oglCanvas.rotate( -90.0, 1.0, 0.0, 0.0 );
		oglCanvas.endList();
	}

	void LowerLeg( boolean solid ) {
		double k, l;
		Quadric ankle = getQuadric();
		Quadric ankle_face[] = new Quadric[2];

		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		for( k = 0.0; k < 2.0; k++ ) {
			for( l = 0.0; l < 2.0; l++ ) {
				oglCanvas.pushMatrix();
				oglCanvas.translate( k, 0.0, l );
				SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
				oglCanvas.color( 1.0, 1.0, 0.0 );
				Box( 1.0, 0.5, 1.0, solid );
				oglCanvas.translate( 0.0, -0.45, 0.0 );
				SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
				oglCanvas.color( 0.5, 0.5, 0.5 );
				if( !solid )
					WireSphere( 0.2, 16, 10 );
				else
					SolidSphere( 0.2, 16, 10 );
				if( leg )
					oglCanvas.rotate( heel1, 1.0, 0.0, 0.0 );
				else
					oglCanvas.rotate( heel2, 1.0, 0.0, 0.0 );
				oglCanvas.translate( 0.0, -1.7, 0.0 );
				SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
				oglCanvas.color( 1.0, 1.0, 0.0 );
				Box( 0.25, 3.0, 0.25, solid );
				oglCanvas.translate( 0.0, -1.7, 0.0 );
				SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
				oglCanvas.color( 0.5, 0.5, 0.5 );
				if( !solid )
					WireSphere( 0.2, 16, 10 );
				else
					SolidSphere( 0.2, 16, 10 );
				if( leg )
					oglCanvas.rotate( - heel1, 1.0, 0.0, 0.0 );
				else
					oglCanvas.rotate( - heel2, 1.0, 0.0, 0.0 );
				oglCanvas.translate( 0.0, -0.45, 0.0 );
				SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
				oglCanvas.color( 1.0, 1.0, 0.0 );
				Box( 1.0, 0.5, 1.0, solid );
				if( k != 0 && l != 0 ) {
					int j;

					oglCanvas.translate( -0.4, -0.8, 0.5 );
					if( leg )
						oglCanvas.rotate( ankle1, 1.0, 0.0, 0.0 );
					else
						oglCanvas.rotate( ankle2, 1.0, 0.0, 0.0 );
					oglCanvas.rotate( 90.0, 0.0, 1.0, 0.0 );
					if( !solid )
						ankle.quadricDrawStyle( GLU.LINE );
					ankle.cylinder( 0.8, 0.8, 1.8, 16, 10 );
					for( j = 0; j < 2; j++ ) {
						ankle_face[j] = getQuadric();
						if( !solid )
							ankle_face[j].quadricDrawStyle( GLU.LINE );
						if( j != 0 ) {
							oglCanvas.scale( -1.0, 1.0, 1.0 );
							oglCanvas.translate( 0.0, 0.0, 1.8 );
						}
						ankle_face[j].disk( 0.0, 0.8, 16, 10 );
						if( j != 0 )
							oglCanvas.translate( 0.0, 0.0, -1.8 );
					}
					oglCanvas.scale( -1.0, 1.0, 1.0 );
					oglCanvas.rotate( -90.0, 0.0, 1.0, 0.0 );
					oglCanvas.translate( 0.95, -0.8, 0.0 );
					oglCanvas.callList( SOLID_MECH_FOOT );
				}
				oglCanvas.popMatrix();
			}
		}
	}

	void RocketPod( boolean solid ) {
        int i, j, k = 0;
		Quadric rocket[] = new Quadric[6];
		Quadric rocket1[] = new Quadric[6];

		oglCanvas.newList( SOLID_MECH_ROCKET, GL.COMPILE );
		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.color( 0.5, 0.5, 0.5 );
		oglCanvas.scale( 0.4, 0.4, 0.4 );
		oglCanvas.rotate( 45.0, 0.0, 0.0, 1.0 );
		oglCanvas.translate( 1.0, 0.0, 0.0 );
		Box( 2.0, 0.5, 3.0, solid );
		oglCanvas.translate( 1.0, 0.0, 0.0 );
		oglCanvas.rotate( 45.0, 0.0, 0.0, 1.0 );
		oglCanvas.translate( 0.5, 0.0, 0.0 );
		Box( 1.2, 0.5, 3.0, solid );
		oglCanvas.translate( 2.1, 0.0, 0.0 );
		oglCanvas.rotate( -90.0, 0.0, 0.0, 1.0 );
		SetMaterial( mat_specular, mat_ambient, mat_diffuse, mat_shininess );
		oglCanvas.color( 1.0, 1.0, 0.0 );
		Box( 2.0, 3.0, 4.0, solid );
		oglCanvas.translate( -0.5, -1.0, 1.3 );
		for( i = 0; i < 2; i++ ) {
			for( j = 0; j < 3; j++ ) {
				rocket[k] = getQuadric();
				rocket1[k] = getQuadric();
				if( !solid ) {
					rocket[k].quadricDrawStyle( GLU.LINE );
					rocket1[k].quadricDrawStyle( GLU.LINE );
				}
				oglCanvas.translate( i, j, 0.6 );
				SetMaterial( mat_specular3, mat_ambient3, mat_diffuse3, mat_shininess3 );
				oglCanvas.color( 1.0, 1.0, 1.0 );
				rocket[k].cylinder( 0.4, 0.4, 0.3, 16, 10 );
				oglCanvas.translate( 0.0, 0.0, 0.3 );
				SetMaterial( mat_specular4, mat_ambient4, mat_diffuse4, mat_shininess4 );
				oglCanvas.color( 0.0, 1.0, 0.0 );
				rocket1[k].cylinder( 0.4, 0.0, 0.5, 16, 10 );
				k++;
				oglCanvas.translate( -i, -j, -0.9 );
			}
		}
		oglCanvas.endList();
	}

	void Enviro( boolean solid ) {
        int i, j;

		oglCanvas.newList( SOLID_ENVIRO, GL.COMPILE );
		SetMaterial( mat_specular4, mat_ambient4, mat_diffuse4, mat_shininess4 );
		oglCanvas.color( 0.0, 1.0, 0.0 );
		Box( 20.0, 0.5, 30.0, solid );
		SetMaterial( mat_specular4, mat_ambient3, mat_diffuse2, mat_shininess );
		oglCanvas.color( 0.6, 0.6, 0.6 );
		oglCanvas.translate( 0.0, 0.0, -10.0 );
		for( j = 0; j < 6; j++ ) {
			for( i = 0; i < 2; i++ ) {
				if( i != 0 )
					oglCanvas.scale( -1.0, 1.0, 1.0 );
				oglCanvas.translate( 10.0, 4.0, 0.0 );
				Box( 4.0, 8.0, 2.0, solid );
				oglCanvas.translate( 0.0, -1.0, -3.0 );
				Box( 4.0, 6.0, 2.0, solid );
				oglCanvas.translate( -10.0, -3.0, 3.0 );
			}
			oglCanvas.scale( -1.0, 1.0, 1.0 );
			oglCanvas.translate( 0.0, 0.0, 5.0 );
		}
		oglCanvas.endList();
	}

	void ldisable() {

		oglCanvas.disable( GL.LIGHTING );
		oglCanvas.disable( GL.DEPTH_TEST );
		oglCanvas.disable( GL.NORMALIZE );
		oglCanvas.polygonMode( GL.FRONT_AND_BACK, GL.LINE );
	}

	void lighting() {
		float position[] = { 0.0f, 0.0f, 2.0f, 1.0f };

		oglCanvas.rotate( lightturn1, 1.0, 0.0, 0.0 );
		oglCanvas.rotate( lightturn, 0.0, 1.0, 0.0 );
		oglCanvas.rotate( 0.0, 1.0, 0.0, 0.0 );
		oglCanvas.enable( GL.LIGHTING );
		oglCanvas.enable( GL.LIGHT0 );
		oglCanvas.enable( GL.NORMALIZE );
		oglCanvas.enable( GL.FLAT );
		oglCanvas.depthFunc( GL.LESS );
		oglCanvas.polygonMode( GL.FRONT_AND_BACK, GL.FILL );

		oglCanvas.light( GL.LIGHT0, GL.POSITION, position );
		oglCanvas.light( GL.LIGHT0, GL.SPOT_CUTOFF, 80.0f );

		oglCanvas.translate( 0.0, 0.0, 2.0 );
		oglCanvas.disable( GL.LIGHTING );
		Box( 0.1, 0.1, 0.1, false );
		oglCanvas.enable( GL.LIGHTING );
	}

	void DrawMech() {
		int i, j;

		oglCanvas.scale( 0.5, 0.5, 0.5 );
		oglCanvas.pushMatrix();
		oglCanvas.translate( 0.0, -0.75, 0.0 );
		oglCanvas.rotate( tilt, 1.0, 0.0, 0.0 );

		oglCanvas.rotate( 90.0, 1.0, 0.0, 0.0 );
		oglCanvas.callList( SOLID_MECH_HIP );
		oglCanvas.rotate( -90.0, 1.0, 0.0, 0.0 );

		oglCanvas.translate( 0.0, 0.75, 0.0 );
		oglCanvas.pushMatrix();
		oglCanvas.rotate( pivot, 0.0, 1.0, 0.0 );
		oglCanvas.pushMatrix();
		oglCanvas.callList( SOLID_MECH_TORSO );
		oglCanvas.popMatrix();
		oglCanvas.pushMatrix();
		oglCanvas.translate( 0.5, 0.5, 0.0 );
		oglCanvas.callList( SOLID_MECH_ROCKET );
		oglCanvas.popMatrix();
		for( i = 0; i < 2; i++ ) {
			oglCanvas.pushMatrix();
			if( i != 0 )
				oglCanvas.scale( -1.0, 1.0, 1.0 );
			oglCanvas.translate( 1.5, 0.0, 0.0 );
			oglCanvas.callList( SOLID_MECH_SHOULDER );
			oglCanvas.translate( 0.9, 0.0, 0.0 );
			if( i != 0 ) {
				oglCanvas.rotate( lat1, 0.0, 0.0, 1.0 );
				oglCanvas.rotate( shoulder1, 1.0, 0.0, 0.0 );
				oglCanvas.rotate( shoulder3, 0.0, 1.0, 0.0 );
			} else {
				oglCanvas.rotate( lat2, 0.0, 0.0, 1.0 );
				oglCanvas.rotate( shoulder2, 1.0, 0.0, 0.0 );
				oglCanvas.rotate( shoulder4, 0.0, 1.0, 0.0 );
			}
			oglCanvas.translate( 0.0, -1.4, 0.0 );
			oglCanvas.callList( SOLID_MECH_UPPER_ARM );
			oglCanvas.translate( 0.0, -2.9, 0.0 );
			if( i != 0 )
				oglCanvas.rotate( elbow1, 1.0, 0.0, 0.0 );
			else
				oglCanvas.rotate( elbow2, 1.0, 0.0, 0.0 );
			oglCanvas.translate( 0.0, -0.9, -0.2 );
			oglCanvas.callList( SOLID_MECH_FOREARM );
			oglCanvas.pushMatrix();
			oglCanvas.translate( 0.0, 0.0, 2.0 );
			oglCanvas.rotate( fire, 0.0, 0.0, 1.0 );
			oglCanvas.callList( SOLID_MECH_VULCAN );
			oglCanvas.popMatrix();
			oglCanvas.popMatrix();
		}
		oglCanvas.popMatrix();

		oglCanvas.popMatrix();

		for( j = 0; j < 2; j++ ) {
			oglCanvas.pushMatrix();
			if( j != 0 ) {
				oglCanvas.scale( -0.5, 0.5, 0.5 );
				leg = true;
			} else {
				oglCanvas.scale( 0.5, 0.5, 0.5 );
				leg = false;
			}
			oglCanvas.translate( 2.0, -1.5, 0.0 );
			if( j != 0 ) {
				oglCanvas.rotate( hip11, 1.0, 0.0, 0.0 );
				oglCanvas.rotate( hip12, 0.0, 0.0, 1.0 );
			} else {
				oglCanvas.rotate( hip21, 1.0, 0.0, 0.0 );
				oglCanvas.rotate( hip22, 0.0, 0.0, 1.0 );
			}
			oglCanvas.translate( 0.0, 0.3, 0.0 );
			oglCanvas.pushMatrix();
			oglCanvas.callList( SOLID_MECH_UPPER_LEG );
			oglCanvas.popMatrix();
			oglCanvas.translate( 0.0, -8.3, -0.4 );
			if( j != 0 )
				oglCanvas.rotate( - hip12, 0.0, 0.0, 1.0 );
			else
				oglCanvas.rotate( - hip22, 0.0, 0.0, 1.0 );
			oglCanvas.translate( -0.5, -0.85, -0.5 );
			LowerLeg( true );
			oglCanvas.popMatrix();
		}
	}

	public void initMech() {

		SetMaterial( mat_specular2, mat_ambient2, mat_diffuse2, mat_shininess2 );
		oglCanvas.enable( GL.DEPTH_TEST );
		MechTorso( true );
		MechHip( true );
		Shoulder( true );
		RocketPod( true );
		UpperArm( true );
		ForeArm( true );
		UpperLeg( true );
		Foot( true );
		VulcanGun( true );
		Enviro( true );
	}


	public void draw() {

		oglCanvas.clearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		oglCanvas.clear( GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT );
		oglCanvas.enable( GL.DEPTH_TEST );

		oglCanvas.pushMatrix();
		oglCanvas.rotate( turn, 0.0, 1.0, 0.0 );
		oglCanvas.rotate( turn1, 1.0, 0.0, 0.0 );
		if( solid_part ) {
			oglCanvas.pushMatrix();
			lighting();
			oglCanvas.popMatrix();
		} else
			ldisable();
		oglCanvas.pushMatrix();
		oglCanvas.translate( 0.0, elevation, 0.0 );
		DrawMech();
		oglCanvas.popMatrix();
		oglCanvas.pushMatrix();
		if( distance >= 20.136 )
			distance = 0.0;
		oglCanvas.translate( 0.0, -5.0, -distance );
		oglCanvas.callList( SOLID_ENVIRO );
		oglCanvas.translate( 0.0, 0.0, 10.0 );
		oglCanvas.callList( SOLID_ENVIRO );
		oglCanvas.popMatrix();
		oglCanvas.popMatrix();

		oglCanvas.flush();
		oglCanvas.swap();
	}

	
	public void advance() {
		double angle;

		if( step == 0 || step == 2 ) {
			if( frame >= 0.0 && frame <= 21.0 ) {
				if( frame == 0.0 )
					frame = 3.0;
				angle = ( 180 / Math.PI ) * ( Math.acos( ( ( Math.cos( ( Math.PI / 180 ) * frame ) * 2.043 ) + 1.1625 ) / 3.2059 ) );
				if( frame > 0 ) {
					elevation = -( 3.2055 - ( Math.cos( ( Math.PI / 180 ) * angle ) * 3.2055 ) );
				} else
					elevation = 0.0;
				if( step == 0 ) {
					hip11 = (int) -( frame * 1.7 );
					if( 1.7 * frame > 15 )
						heel1 = (int) ( frame * 1.7 );
					heel2 = 0;
					ankle1 = (int) ( frame * 1.7 );
					if( frame > 0 )
						hip21 = (int) angle;
					else
						hip21 = 0;
					ankle2 = -hip21;
					shoulder1 = (int) ( frame * 1.5 );
					shoulder2 = (int) -( frame * 1.5 );
					elbow1 = (int) frame;
					elbow2 = (int) -frame;
				} else {
					hip21 = (int) -( frame * 1.7 );
					if( 1.7 * frame > 15 )
						heel2 = (int) ( frame * 1.7 );
					heel1 = 0;
					ankle2 = (int) ( frame * 1.7 );
					if( frame > 0 )
						hip11 = (int) angle;
					else
						hip11 = 0;
					ankle1 = -hip11;
					shoulder1 = (int) -( frame * 1.5 );
					shoulder2 = (int) ( frame * 1.5 );
					elbow1 = (int) -frame;
					elbow2 = (int) frame;
				}
				if( frame == 21 )
					step++;
				if( frame < 21 )
					frame = frame + 3.0;
			}
		}
		if( step == 1 || step == 3 ) {
			if( frame <= 21.0 && frame >= 0.0 ) {
				angle = ( 180 / Math.PI ) * ( Math.acos( ( ( Math.cos( ( Math.PI / 180 ) * frame ) * 2.043 ) + 1.1625 ) / 3.2029 ) );
				if( frame > 0 )
					elevation = -( 3.2055 - ( Math.cos( ( Math.PI / 180 ) * angle ) * 3.2055 ) );
				else
					elevation = 0.0;
				if( step == 1 ) {
					elbow2 = hip11 = (int) -frame;
					elbow1 = heel1 = (int) frame;
					heel2 = 15;
					ankle1 = (int) frame;
					if( frame > 0 )
						hip21 = (int) angle;
					else
						hip21 = 0;
					ankle2 = -hip21;
					shoulder1 = (int) ( 1.5 * frame );
					shoulder2 = (int) ( -frame * 1.5 );
				} else {
					elbow1 = hip21 = (int) -frame;
					elbow2 = heel2 = (int) frame;
					heel1 = 15;
					ankle2 = (int) frame;
					if( frame > 0 )
						hip11 = (int) angle;
					else
						hip11 = 0;
					ankle1 = -hip11;
					shoulder1 = (int) ( -frame * 1.5 );
					shoulder2 = (int) ( frame * 1.5 );
				}
				if( frame == 0.0 )
					step++;
				if( frame > 0 )
					frame = frame - 3.0;
			}
		}
		if( step == 4 )
			step = 0;
		distance += 0.1678;
	}


	public void xsetSize( int width, int height ) {

		oglCanvas.viewport( 0, 0, width, height );
		oglCanvas.matrixMode( GL.PROJECTION );
		oglCanvas.loadIdentity();
		oglCanvas.perspective( 65.0, width / height, 1.0, 100.0 );
		oglCanvas.matrixMode( GL.MODELVIEW );
		oglCanvas.loadIdentity();
		oglCanvas.translate( 0.0, 1.2, -5.5 );	// viewing transform
	}


	public mech() {

		super( "OpenGL Widget" );

		/* try to instantiate an OpenGL widget */
		oglCanvas = new JoglCanvas();
	
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Test" ));
		mb.add( file );
		setMenuBar( mb );

		// set up the Frame
		oglCanvas.setSize( 400, 400 );
		add( "Center", oglCanvas );
		setSize( 400, 400 );
		setVisible( true );
		System.out.println( "show" );

		oglCanvas.addMouseListener( this );
		oglCanvas.addMouseMotionListener( this );
		oglCanvas.addKeyListener( new KeyAdapter()
			{ public void keyPressed( KeyEvent e )
			{
				System.out.println( e );
			}});

		try{
			Thread.sleep( 1000 );
		}catch( Exception e ){}

		oglCanvas.use();
	}


	public void doMech() {

		initMech();
		xsetSize( getSize().width, getSize().height );

		while( true ) {
			oglCanvas.use();
			draw();
			advance();
		}
	}


	public static void main( String args[] ) 
	{
		mech m = new mech();
		m.doMech();
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
}
