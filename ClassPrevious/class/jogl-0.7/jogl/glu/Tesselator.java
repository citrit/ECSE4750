/* Copyright 1997 Free Software Foundation, Inc.
 * Written by Javier Perez
 * Modifications by Tommy Reilly
 */

package jogl.glu;

import jogl.JoglNativeException;

public class Tesselator {

	// Stores the pointer to the GLU object
	private int tessobj = 0;

	private native boolean newTess();
    private native void deleteTess();


	// Creates a GLUtesselator object

	public Tesselator() throws JoglNativeException {
		
		if( newTess() == false ) 
		{
			throw new JoglNativeException("jogl.glu.Tesselator: newTess returned false.");
		}
	}


	// Finalization method

	protected void finalize() {

		deleteTess();
	}


	// Instance methods

	public native void beginPolygon();

	public native void endPolygon();

	public native void nextContour( int type );

	public native void tessVertex( double v[], Object data );

	public native void tessCallback( int which, Object obj, String method );
}
