//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    January 1998
//
//////////////////////////////////////////////////////////////////////////

import gl4java.GLFunc;
import gl4java.GLUFunc;
import gl4java.awt.GLCanvas;

/**
  * Camera class handles viewport information
  *
  */
public class GL4JCamera extends Camera
{
	GLFunc gl;
	GLUFunc glu;

	/** Called by the Renderer after added */
	public void setRenderer(Renderer aren) { 
		renderer = aren;
		gl = ((GL4JRenderer)aren).gl;
		glu = ((GL4JRenderer)aren).glu;
	}

	/**  Method to rotate the camera along the X axis */
    public void rotateX(float angle)
	{
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glRotatef(angle, 1, 0, 0);
	}
	/**  Method to rotate the camera along the Y axis */
    public void rotateY(float angle)
	{
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glRotatef(angle, 0, 1, 0);
	}
	/**  Method to rotate the camera along the Z axis */
    public void rotateZ(float angle)
	{
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glRotatef(angle, 0, 0, 1);
	}

	/**  Method to translate the camera */
    public void translate(float x, float y, float z)
	{
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glTranslatef(x, y, z);
	}

	/** (Hopefully) a method to zoom the camera */
    public void zoom(float amount)
	{
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glTranslatef(0, 0, amount);
	}

	/** Convenience function to make the camera set the viewpoint */
    public void init()
	{
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(eyeAngle, 
				1.0F*width/height, 
				clipRange[0], clipRange[1]);
		glu.gluLookAt(from.x, from.y, from.z,
						to.x, to.y, to.z,
						up.x, up.y, up.z);
	}
}

