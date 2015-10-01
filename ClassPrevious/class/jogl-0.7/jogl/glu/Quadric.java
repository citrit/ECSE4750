/* Copyright 1997 Free Software Foundation, Inc.
 */

package jogl.glu;

import jogl.JoglNativeException;


public class Quadric {

	// Stores the pointer to the GLU quadric object
	private int qobj = 0;

	private native boolean newQuadric();
	private native void deleteQuadric();


	// Creates a GLUquadric object

	public Quadric() throws JoglNativeException {
		
		if( newQuadric() == false ) 
		{
			throw new JoglNativeException("jogl.glu.Quadric: newQuadric returned false.");
		}
	}


	// Finalization method

	protected void finalize() {

		deleteQuadric();
	}


	// Instance methods

	public native void quadricDrawStyle( int drawStyle );

	public native void quadricOrientation( int orientation );

	public native void quadricNormals( int normals );

	public native void quadricTexture( int textureCoords );

	public native void cylinder( double baseRadius, double topRadius, double height, int slices, int stacks );

	public native void disk( double innerRadius, double outerRadius, int slices, int loops );

	public native void sphere( double radius, int slices, int stacks );

	public native void partialDisk( double innerRadius, double outerRadius, int slices, int loops, double startAngle, double sweepAngle );

	//public native void quadricCallback( int which, Object obj, String method );
}
