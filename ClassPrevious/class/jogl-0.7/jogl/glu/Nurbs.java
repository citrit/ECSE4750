/* Copyright 1997 Free Software Foundation, Inc.
 * Written by Javier Perez
 * Modifications by Tommy Reilly
 */

package jogl.glu;

import jogl.JoglNativeException;

public class Nurbs {

	// Stores the pointer to the GLU object
	private int nurbsobj = 0;

	private native boolean newNurbsRenderer();
	private native void deleteNurbsRenderer();


	// Creates a GLUnurbs object

	public Nurbs() throws JoglNativeException {
		
		if( newNurbsRenderer() == false ) 
		  {
		    throw new JoglNativeException("jogl.glu.Nurbs: newNurbsRenderer returned false.");
		}
	}


	// Finalization method

	protected void finalize() {

		deleteNurbsRenderer();
	}


	// Instance methods

	public native void loadSamplingMatrices( float modelMatrix[],
											 float projMatrix[],
											 int viewport[] );

	public native void nurbsProperty( int property, float value );

	public native void getNurbsProperty( int property, Float value );

	public native void beginCurve();

	public native void endCurve();

	public native void nurbsCurve( int nknots, float[] knot,
								   int stride, float[] ctlarray, int order,
								   int type );

	public native void beginSurface();

	public native void endSurface();

	public native void nurbsSurface( int sknot_count, float[] sknot,
									 int tknot_count, float[] tknot,
									 int s_stride, int t_stride,
									 float[] ctlarray,
									 int sorder, int torder,
									 int type );

	public native void beginTrim();

	public native void endTrim();

	public native void pwlCurve( int count, float[] array, int stride, int type );

	public native void nurbsCallback( int which, Object obj, String method );
}
