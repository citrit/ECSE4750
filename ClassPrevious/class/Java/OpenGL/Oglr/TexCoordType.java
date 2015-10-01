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
  * TexCoordType class simply holds the x,y of a texture coord
  */
public class TexCoordType extends ParentObject
{
	/** x,y components of the point */
	public float x, y;

	/** Create a point at 0,0 */
	public TexCoordType()
	{
		x = y = 0;
	}
	/** create a PointType at the specified float values 
	  * x = p[0];y = p[1];
	  */
	public TexCoordType(float inx, float iny)
	{
		x = inx;
		y = iny;
	}
	public TexCoordType(float p[])
	{
		this(p[0], p[1]);
	}
}
