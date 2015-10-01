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
  * Light class
  */
OGLRen.Light = function(num) {
    this.on = true;
    this.color = new OGLRen.Material();
    this.number = (num != null? num : 0);
    
    /** Convenience function to turn  the light on */
	this.setState = function( onOrOff) { this.on = onOrOff; }
	
	/** Convenience function to turn  the light on */
	this.turnOn = function() { this.on = true; }

	/**
	  * convenience function to turn the light off
	  */
	this.turnOff = function() { this.on = false; }


	/** Set the color of the light */
	this.setColor = function( mat)
	{
		color = mat;
	}
    this.getColor = function() { return color; }

}
OGLRen.Light.inheritsFrom(OGLRen.ParentObject);

/** Called by the Renderer to initialize the light */
OGLRen.Light.prototype.render = function(aren) { alert('Can;t call abstract method'); }
