/*
 * Trackball code:
 *
 * Implementation of a virtual trackball.
 * Implemented by Gavin Bell, lots of ideas from Thant Tessman and
 *   the August '88 issue of Siggraph's "Computer Graphics," pp. 121-129.
 *
 * Vector manip code:
 *
 * Original code from:
 * David M. Ciemiewicz, Mark Grossman, Henry Moreton, and Paul Haeberli
 *
 * Much mucking with by:
 * Gavin Bell
 *
 * Java version:
 * Javier Perez
 *
 */

package demos;

class Trackball {

	/*
	 * This size should really be based on the distance from the center of
	 * rotation to the point on the object underneath the mouse.  That
	 * point would then track the mouse as closely as possible.  This is a
	 * simple example, though, so that is left as an Exercise for the
	 * Programmer.
	 */
	static final float TRACKBALLSIZE = 0.8f;

	static void vzero( float[] v )
	{
		v[0] = 0.0f;
		v[1] = 0.0f;
		v[2] = 0.0f;
	}

	static void vset( float[] v, float x, float y, float z )
	{
		v[0] = x;
		v[1] = y;
		v[2] = z;
	}

	static void vsub( float[] src1, float[] src2, float[] dst )
	{
		dst[0] = src1[0] - src2[0];
		dst[1] = src1[1] - src2[1];
		dst[2] = src1[2] - src2[2];
	}

	static void vcopy( float[] v1, float[] v2 )
	{
		for ( int i = 0 ; i < 3 ; i++ )
			v2[i] = v1[i];
	}

	static void vcross( float[] v1, float[] v2, float[] cross )
	{
		float temp[] = new float[3];

		temp[0] = ( v1[1] * v2[2] ) - ( v1[2] * v2[1] );
		temp[1] = ( v1[2] * v2[0] ) - ( v1[0] * v2[2] );
		temp[2] = ( v1[0] * v2[1] ) - ( v1[1] * v2[0] );
		vcopy( temp, cross );
	}

	static float vlength( float[] v )
	{
		return (float) Math.sqrt( v[0] * v[0] + v[1] * v[1] + v[2] * v[2] );
	}

	static void vscale( float[] v, float div )
	{
		v[0] *= div;
		v[1] *= div;
		v[2] *= div;
	}

	static void vnormal( float[] v )
	{
		vscale( v, 1.0f / vlength( v ) );
	}

	static float vdot( float[] v1, float[] v2 )
	{
		return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
	}

	static void vadd( float[] src1, float[] src2, float[] dst )
	{
		dst[0] = src1[0] + src2[0];
		dst[1] = src1[1] + src2[1];
		dst[2] = src1[2] + src2[2];
	}

	/*
	 * Ok, simulate a track-ball.  Project the points onto the virtual
	 * trackball, then figure out the axis of rotation, which is the cross
	 * product of P1 P2 and O P1 ( O is the center of the ball, 0,0,0 )
	 * Note:  This is a deformed trackball-- is a trackball in the center,
	 * but is deformed into a hyperbolic sheet of rotation away from the
	 * center.  This particular function was chosen after trying out
	 * several variations.
	 *
	 * It is assumed that the arguments to this routine are in the range
	 * ( -1.0 ... 1.0 )
	 */
	public static void trackball( float q[], float p1x, float p1y, float p2x, float p2y )
	{
		float a[]; /* Axis of rotation */
		float phi;  /* how much to rotate about axis */
		float p1[], p2[], d[];
		float t;

		a = new float[3];
		p1 = new float[3];
		p2 = new float[3];
		d = new float[3];

		if ( p1x == p2x && p1y == p2y ) {
			/* Zero rotation */
			vzero( q );
			q[3] = 1.0f;
			return;
		}

		/*
		 * First, figure out z-coordinates for projection of P1 and P2 to
		 * deformed sphere
		 */
		vset( p1, p1x, p1y, tb_project_to_sphere( TRACKBALLSIZE, p1x, p1y ) );
		vset( p2, p2x, p2y, tb_project_to_sphere( TRACKBALLSIZE, p2x, p2y ) );

		/*
		 *  Now, we want the cross product of P1 and P2
		 */
		vcross( p2, p1, a );

		/*
		 *  Figure out how much to rotate around that axis.
		 */
		vsub( p1, p2, d );
		t = vlength( d ) / ( 2.0f * TRACKBALLSIZE );

		/*
		 * Avoid problems with out-of-control values...
		 */
		if ( t > 1.0 ) t = 1.0f;
		if ( t < -1.0 ) t = -1.0f;
		phi = 2.0f * (float) Math.asin( t );

		axis_to_quat( a, phi, q );
	}

	/*
	 *  Given an axis and angle, compute quaternion.
	 */
	static void axis_to_quat( float a[], float phi, float q[] )
	{
		vnormal( a );
		vcopy( a, q );
		vscale( q, (float) Math.sin( phi / 2.0 ) );
		q[3] = (float) Math.cos( phi / 2.0 );
	}

	/*
	 * Project an x,y pair onto a sphere of radius r OR a hyperbolic sheet
	 * if we are away from the center of the sphere.
	 */
	static float tb_project_to_sphere( float r, float x, float y )
	{
		float d, t, z;

		d = (float) Math.sqrt( x * x + y * y );
		if ( d < r * 0.70710678118654752440 ) {    /* Inside sphere */
			z = (float) Math.sqrt( r * r - d * d );
		} else {           /* On hyperbola */
			t = (float) ( r / 1.41421356237309504880 );
			z = t * t / d;
		}
		return z;
	}

	/*
	 * Given two rotations, e1 and e2, expressed as quaternion rotations,
	 * figure out the equivalent single rotation and stuff it into dest.
	 *
	 * This routine also normalizes the result every RENORMCOUNT times it is
	 * called, to keep error from creeping in.
	 *
	 * NOTE: This routine is written so that q1 or q2 may be the same
	 * as dest ( or each other ).
	 */

	static final int RENORMCOUNT = 97;

	public static void add_quats( float q1[], float q2[], float dest[] )
	{
		int count = 0;
		float t1[], t2[], t3[];
		float tf[];

		t1 = new float[4];
		t2 = new float[4];
		t3 = new float[4];
		tf = new float[4];

		vcopy( q1,t1 );
		vscale( t1,q2[3] );

		vcopy( q2,t2 );
		vscale( t2,q1[3] );

		vcross( q2,q1,t3 );
		vadd( t1,t2,tf );
		vadd( t3,tf,tf );
		tf[3] = q1[3] * q2[3] - vdot( q1, q2 );

		dest[0] = tf[0];
		dest[1] = tf[1];
		dest[2] = tf[2];
		dest[3] = tf[3];

		if ( ++count > RENORMCOUNT ) {
			count = 0;
			normalize_quat( dest );
		}
	}

	/*
	 * Quaternions always obey:  a^2 + b^2 + c^2 + d^2 = 1.0
	 * If they don't add up to 1.0, dividing by their magnitued will
	 * renormalize them.
	 *
	 * Note: See the following for more information on quaternions:
	 *
	 * - Shoemake, K., Animating rotation with quaternion curves, Computer
	 *   Graphics 19, No 3 ( Proc. SIGGRAPH'85 ), 245-254, 1985.
	 * - Pletinckx, D., Quaternion calculus as a basic tool in computer
	 *   graphics, The Visual Computer 5, 2-13, 1989.
	 */
	static void normalize_quat( float q[] )
	{
		int i;
		float mag;

		mag = ( q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3] );
		for ( i = 0; i < 4; i++ ) q[i] /= mag;
	}

	/*
	 * Build a rotation matrix, given a quaternion rotation.
	 *
	 */
	static void build_rotmatrix( float m[], float q[] )
	{
		m[ 0] = 1.0f - 2.0f * ( q[1] * q[1] + q[2] * q[2] );
		m[ 1] = 2.0f * ( q[0] * q[1] - q[2] * q[3] );
		m[ 2] = 2.0f * ( q[2] * q[0] + q[1] * q[3] );
		m[ 3] = 0.0f;

		m[ 4] = 2.0f * ( q[0] * q[1] + q[2] * q[3] );
		m[ 5] = 1.0f - 2.0f * ( q[2] * q[2] + q[0] * q[0] );
		m[ 6] = 2.0f * ( q[1] * q[2] - q[0] * q[3] );
		m[ 7] = 0.0f;

		m[ 8] = 2.0f * ( q[2] * q[0] - q[1] * q[3] );
		m[ 9] = 2.0f * ( q[1] * q[2] + q[0] * q[3] );
		m[10] = 1.0f - 2.0f * ( q[1] * q[1] + q[0] * q[0] );
		m[11] = 0.0f;

		m[12] = 0.0f;
		m[13] = 0.0f;
		m[14] = 0.0f;
		m[15] = 1.0f;
	}
}
