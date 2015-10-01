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

//package oglr;
import java.util.Vector;

public class ScalarFloat extends ParentObject
{

	/** Holds the bounds of the scalar values [min, max] */
	float bBox[];
	/** Holds the float scalars */
	Vector scalarVals;

	/** Constructor */
	public ScalarFloat() 
	{
		bBox = new float[2];
		scalarVals = new Vector();
		bBox[0] = Float.MAX_VALUE;
		bBox[1] = Float.MIN_VALUE;
	}

	/** Return the scalar range */
	public float[] getScalarRange() { return bBox; }

	/** Add a scalar value */
	public void addScalar(float val)
	{

		bBox[0] = (bBox[0]<val?bBox[0]:val);
		bBox[1] = (bBox[1]>val?bBox[1]:val);
		scalarVals.addElement(new Float(val));
		updateTime();
	}

	/** Get specified scalar value */
	public float getScalar(int i)
	{
		return ((Float)scalarVals.elementAt(i)).floatValue(); 
	}
}

