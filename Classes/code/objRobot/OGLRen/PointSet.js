//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a college course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Computer Graphics 
//           Rensselaer Polytechnic Institute
//  Date:    Sept 2015
//
//////////////////////////////////////////////////////////////////////////

var OGLRen = OGLRen || {};

/**
  * PointType class simply holds the x,y,z of a point
  */
OGLRen.PointSet = function() {
    	/** Bounding box created as points are added
	  * in the form:  minx,miny,minz,maxx,maxy,maxz
	  */
    /** Actual vector of PointType objects */
	var pts = [];
    var BBox = [6];
    BBox[0] = BBox[1] = BBox[2] = 9999999999.9;
    BBox[3] = BBox[4] = BBox[5] = -99999999999.9;
    
	/** Add a point and set bouningBox, don't forget to update this
	  * instance time 
	  */
    this.addPoint = function(pt) {
		pts.push(pt);
		BBox[0] = (BBox[0]<pt[0]?BBox[0]:pt[0]);
		BBox[1] = (BBox[1]<pt[1]?BBox[1]:pt[1]);
		BBox[2] = (BBox[2]<pt[2]?BBox[2]:pt[2]);
		BBox[3] = (BBox[3]>pt[0]?BBox[3]:pt[0]);
		BBox[4] = (BBox[4]>pt[1]?BBox[4]:pt[1]);
		BBox[5] = (BBox[5]>pt[2]?BBox[5]:pt[2]);
		this.updateTime();
    }

	/** Return the point at a given index */
	this.getPoint = function(at)
	{
		return pts[at];
	}

	/** Simply return the size of the  internal vector */
	this.size = function() { return pts.length; }

	/** return a float[] of the bounding box of the points */
    this.getBBox = function() { return BBox; }
}
OGLRen.PointSet.inheritsFrom(OGLRen.ParentObject);
