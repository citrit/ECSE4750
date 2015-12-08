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
  * Abstract Cell class which defines the API for all drawable
  * entities to follow
  */

OGLRen.ParentObject = function()
{
	/** Constructor  simply sets the time of creation, all derived
	  * classes automatically inherit this constructor
	  */
	var objectTime = Date.now();


	/** Methods to Get/Update time of pipeline objects, used when object
	  * is changed in some way.
	  */
	this.getTime = function() { return objectTime; }
	/** Methods to Get/Update time of pipeline objects, used when object
	  * is changed in some way.
	  */
	this.updateTime = function() { objectTime = Date.now(); }
}

// Convenience function for inheritance
Function.prototype.inheritsFrom = function( parentClassOrObject ){ 
	if ( parentClassOrObject.constructor == Function ) 
	{ 
		//Normal Inheritance 
		this.prototype = new parentClassOrObject;
		this.prototype.constructor = this;
		this.prototype.parent = parentClassOrObject.prototype;
	} 
	else 
	{ 
		//Pure Virtual Inheritance 
		this.prototype = parentClassOrObject;
		this.prototype.constructor = this;
		this.prototype.parent = parentClassOrObject;
	} 
	return this;
} 