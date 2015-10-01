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

import java.util.Vector;

/**
  * Abstract Cell class which defines the API for all drawable
  * entities to follow
  */

public abstract class Cell extends ParentObject
{

	protected PointSet ptSet;
	protected MaterialSet mtSet;
	protected Vector intVals;

	public Cell()
	{
		intVals = new Vector();
	}

	/**
	  * Abstract render method overridden in the derived classes
	  * to define the specific cells purpose
	  */
	abstract public void render(Renderer aren);

	/**
	  * Simply set the internal pointer to the Material Set
	  */
	public void setMaterials(MaterialSet mat) { mtSet = mat; }

	/**
	  * Set the internal pointer to the current PointSet
	  */
	public void setPoints(PointSet pts) { ptSet = pts; }

	/**
	  * All cells are simply holders of indeces into a pointset
	  * The derived class defines the cells topology. 
	  */
	public void addVal(int val) { intVals.addElement(new Integer(val)); }

	/**
	  * Convenience function to get a index value.
	  */
	public int getVal(int pos) { 
		return ((Integer)intVals.elementAt(pos)).intValue();
	}

	/**
	  * Return the number of indeces
	  */
	public int size() { return intVals.size(); }
}

