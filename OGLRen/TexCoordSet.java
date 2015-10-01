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
  * PointSet class is a collection of PointType objects
  */
public class TexCoordSet extends ParentObject
{
	/** Bounding box created as points are added
	  * in the form:  minx,miny,minz,maxx,maxy,maxz
	  */
    double BBox[];
	/** Actual vector of PointType objects */
	Vector texs;

	/** Constructor creates an empty pointset and initializes max
	  * and min values */
	public TexCoordSet() {
		texs = new Vector();
		BBox = new double[4];
		BBox[0] = BBox[1] = Double.MAX_VALUE;
		BBox[2] = BBox[3] = -Double.MAX_VALUE;
    }

	/** Add a point and set bouningBox, don't forget to update this
	  * instance time 
	  */
    public void addTexCoord(TexCoordType datum) {
		texs.addElement(datum);
		BBox[0] = (BBox[0]<datum.x?BBox[0]:datum.x);
		BBox[1] = (BBox[1]<datum.y?BBox[1]:datum.y);
		BBox[2] = (BBox[2]>datum.x?BBox[2]:datum.x);
		BBox[3] = (BBox[3]>datum.y?BBox[3]:datum.y);
		updateTime();
    }

	/** Return the point at a given index */
	public TexCoordType getTexCoord(int at)
	{
		return (TexCoordType)texs.elementAt(at);
	}

	/** Simply return the size of the  internal vector */
	public int size() { return texs.size(); }

	/** return a double[] of the bounding box of the points */
    public double[] getBBox() { return BBox; }

}

