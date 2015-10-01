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
/////////////////////////////////////////////////////////////////////////

/**
  * PointType class simply holds the x,y,z of a point
  */
public class PointType extends ParentObject
{
	/** x,y,z components of the point */
	public float x, y, z;

	/** Create a point at 0,0,0 */
	public PointType()
	{
		x = y =	z = 0;
	}
	/** create a PointType at the specified float values 
	  * x = p[0];y = p[1];z = p[2];
	  */
	public PointType(float inx, float iny, float inz)
	{
		x = inx;
		y = iny;
		z = inz;
	}
	public PointType(float p[])
	{
		this(p[0], p[1], p[2]);
	}
}

