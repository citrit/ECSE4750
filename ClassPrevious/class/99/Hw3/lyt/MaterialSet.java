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

import java.util.Vector;

/**
  * MaterialSet class holds a collection of materials for per vertex
  * coloring
  */
public class MaterialSet extends ParentObject
{
	/** Vector of Material objects */
	Vector mts;

	/** Constructor simply create a Vector for collection */
	public MaterialSet() {
		mts = new Vector();
	}

	/** Add a Material to the collection */
	public void addMaterial(Material datum) {
		mts.addElement(datum);
		updateTime();
	}

	/** Overload this method for convenience */
	public void addMaterial(float r, float g, float b, float a)
	{
		addMaterial(new Material(r, g, b, a));
	}

	/** return the specified material, on material per vertex */
	public Material getMaterial(int at)
	{
		return (Material)mts.elementAt(at);
	}

	/** Simply return the number of Materials contained */
	public int size() { return mts.size(); }
}

