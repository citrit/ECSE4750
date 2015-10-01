////////////////////////////////////////////////////////
//
//
//
////////////////////////////////////////////////////////

import java.util.Vector;

/**
  * ScalarFloatObject class is a collection of scalar data floaty thingy objects
  */

public class ScalarFloatObject extends ParentObject
{


	/** Actual vector of PointType objects */
	Vector values;
	float max;
	float min; 


	/** Constructor creates an empty values vector and initializes max
	  * and min values */
	public ScalarFloatObject() {
		values = new Vector();
		max = 0;
		min = 0; 
    }

	/** Add a value -- don't forget to update this
	  * instance time 
	  */
    	public void addValue(float datum) {

		values.addElement(new Float(datum));
		updateTime();
    	}

	/** Return the point at a given index */
	public float getValue(int at)
	{	//return ((Integer)intVals.elementAt(pos)).intValue();
		return ((Float)values.elementAt(at)).floatValue();
	}


	/** Simply return the size of the  internal vector */
	public int size() { return values.size(); }


	public boolean doINeedUpdate(long objectTime)
	{
		if (this.getTime() > objectTime ) 
		 	 return true;

		return false; 
 
	}

}

