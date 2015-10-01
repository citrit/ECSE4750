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
OGLRen.Material = function() {
    
    /** Ambient component of color */
	var ambient = [];
	/** Diffuse component of color */
	var diffuse = [];
	/** Specular component of color */
	var specular = [];

	/** Set the Ambient component of a Material */
    this.setAmbient = function( r,  g,  b,  a)
	{
		ambient = [r, g, b, a];
	}
	/** Set the Diffuse component of a Material */
    this.setDiffuse = function( r,  g,  b,  a)
	{
		diffuse = [r, g, b, a];
	}
	/** Set the Specular component of a Material */
	this.setSpecular = function( r,  g,  b,  a)
	{
		specular = [r, g, b, a];
	}
    
    /**
	  * Constructor to create the component arrays and set them to
	  * a safe value.
	  */
    this.setDiffuse(0.8, 0.8, 0.8, 1.0);
	this.setAmbient(0.7, 0.7, 0.7, 1.0);
    this.setSpecular(0.4, 0.4, 0.4, 1.0);

}
OGLRen.Material.inheritsFrom(OGLRen.ParentObject);
