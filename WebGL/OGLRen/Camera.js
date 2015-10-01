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
  * Camera class is a holder of cells into single objects.
  */
OGLRen.Camera = function() {
    
    /**
	  * Called by the Renderer to make sure camera is setup
	  * in place.
	  */
    this.render = function( aren)
	{
		
	}

	/** Method to set up the current viewpoint */
    this.setPosition = function( pos)
	{
		this.from = pos;
		initLess = true;
		viewSize = 2.0*(dist(from,to)*Math.tan(eyeAngle));
	}
	/** Set the camera position */
	this.setFrom = function( x,  y,  z)
	{
		this.from.x = x;
		this.from.y = y;
		this.from.z = z;
		initLess = true;
		viewSize = 2.0*(dist(from,to)*Math.tan(eyeAngle));
	}
	/** Set the camera position */
	this.setFrom = function( pos)
	{
		this.from = pos;
		initLess = true;
	}

	/** Called by the Renderer after added */
	this.setRenderer = function( aren) {};

	/**  Method to rotate the camera along the X axis */
    this.rotateX = function( angle) {};
	/**  Method to rotate the camera along the Y axis */
    this.rotateY = function( angle) {};
	/**  Method to rotate the camera along the Z axis */
    this.rotateZ = function( angle) {};

	/**  Method to translate the camera */
    this.translate = function( x,  y,  z) {};

	/** (Hopefully) a method to zoom the camera */
    this.zoom = function( amount) {};

	/** Convenience function to make the camera set the viewpoint */
    this.init = function() {};

	/** Private functions Distance between two points */
	this.dist = function( a,  b) {
		return Math.sqrt(Math.pow((b.x-a.x),2) + Math.pow((b.y-a.y),2)+ Math.pow((b.z-a.z),2));
	}
    
    // Instance variables.
    this.initLess = true;
    this.eyeAngle = 35;
    this.clipRange = [ 0.1, 1000.0];
    this.from = new OGLRen.PointType();
    this.to = new OGLRen.PointType();
    this.up = new OGLRen.PointType();
    this.from.z = 25;
    this.up.y = 1;
    this.viewSize = 2.0*(this.dist(this.from,this.to)*Math.tan(this.eyeAngle));
}
OGLRen.Camera.inheritsFrom(OGLRen.ParentObject);
