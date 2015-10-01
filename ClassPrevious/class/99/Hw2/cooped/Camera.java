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

import jogl.*;
import ParentObject;

/**
  * Camera: Base class defines all camera behavior for specific
  * Rendering API Camera
  */
abstract class Camera extends ParentObject
{
	int width, height;
	float eyeAngle;
	float clipRange[];
	Renderer renderer;
	PointType from, to, up;
	boolean initLess = true;

	/**
	  * constructor to handle initialization, eyeAngle to 35,
	  * clipRange 0.1 to 1000.0 from.z = 1.0
	  */
	public Camera() 
	{
		eyeAngle = 35;
		clipRange = new float[2];
		clipRange[0] = 0.1F;
		clipRange[1] = 1000.0F;
		from = new PointType();
		to = new PointType();
		up = new PointType();
		from.z = 5;
		up.y = 1;
	}

	/**
	  * Called by the Renderer to make sure camera is setup
	  * in place.
	  */
    public void render(Renderer aren)
	{
		width = aren.getWidth();
		height = aren.getHeight();
		if(initLess) {
			init();
			initLess = false;
		}
	}

	/** Method to set up the current viewpoint */
    public void setPosition(PointType pos)
	{
		from = pos;
		initLess = true;
	}
	/** Set the camera position */
	public void setFrom(float x, float y, float z)
	{
		from.x = x;
		from.y = y;
		from.z = z;
		initLess = true;
	}
	/** Set the camera position */
	public void setFrom(PointType pos)
	{
		from = pos;
		initLess = true;
	}

	/** Called by the Renderer after added */
	public abstract void setRenderer(Renderer aren);

	/**  Method to rotate the camera along the X axis */
    public abstract void rotateX(float angle);
	/**  Method to rotate the camera along the Y axis */
    public abstract void rotateY(float angle);
	/**  Method to rotate the camera along the Z axis */
    public abstract void rotateZ(float angle);

	/**  Method to translate the camera */
    public abstract void translate(float x, float y, float z);

	/** (Hopefully) a method to zoom the camera */
    public abstract void zoom(float amount);

	/** Convenience function to make the camera set the viewpoint */
    public abstract void init();


}

