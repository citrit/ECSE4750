//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Trung Ly		lyt@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    March 1999
//
/////////////////////////////////////////////////////////////////////////

import jogl.*;
import java.util.Vector;

/**
  * The ScalarData object is a container holding scalar values for each of
  * the points in a system.
  */

public class ScalarData extends ParentObject
{
	/* Vector of scalar data values */
	private float[] data;
	private int pos = 0;

	private float min = Float.MAX_VALUE;
	private float max = -Float.MAX_VALUE;

	public ScalarData( int size ) { data = new float[size]; }

	public void addScalar(float datum)
	{
		data[pos++] = datum;
		min = min<datum?min:datum;
		max = max>datum?max:datum;
		updateTime();
	}

	/** return a minimum bound of the points */
	public float getMin() { return min; }

	/** return a maximum bound of the points */
	public float getMax() { return max; }

	public float getScalar(int i) { return data[i]; }

	public Material getColor(int i)
	{
		float rgba[] = new float[4];
		float r=0, g=0, b=0;
		float s;

		s = (data[i]-min)/(max-min);
		if(s<0.5) { g=2*s; r=1-g; }
		else { b=2*s-1; g=1-b; }
//		b=s; r=1-b;
		return( new Material(r, g, b, 1.0F) );
	}
}