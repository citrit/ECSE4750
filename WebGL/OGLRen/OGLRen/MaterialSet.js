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
OGLRen.MaterialSet = function() {
    /** Vector of Material objects */
	var mts = [];

	/** Add a Material to the collection */
    this.addMaterial = function( datum) {
		mts.push(datum);
		this.updateTime();
    }

	/** return the specified material, on material per vertex */
	this.getMaterial = function( at)
	{
		return mts[at];
	}

	/** Simply return the number of Materials contained */
	this.size = function() { return mts.length; }   
}
OGLRen.MaterialSet.inheritsFrom(OGLRen.ParentObject);
