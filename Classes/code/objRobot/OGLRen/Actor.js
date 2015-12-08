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
  * Actor class is a holder of cells into single objects.
  */
OGLRen.Actor = function() {
    var cells = [];
    /** Orientation as rotations around X,Y, and Z */
	var orientation = [0.0, 0.0, 0.0];
	var position = [0,0,0]; // Obvious
	var scale = [1.0, 1.0, 1.0]; // Obvious
    
    this.copy = function() {
        var cp = new OGLRen.Actor();
        
    }
    
    /** Method called by Renderer to signal an update is needed. */
	this.render = function( aren)
	{
		aren.pushModelMatrix();
		aren.setOrientation(orientation, scale, position);
		for (var i=0;i<cells.length;i++) {
			cells[i].render(aren);
		}
		aren.popModelMatrix();
	}

	/** Change orientation */
	this.rotateX = function( angle)
	{
		orientation[0] += angle;
	}
	/** Change orientation */
	this.rotateY = function( angle)
	{
		orientation[1] += angle;
	}
	/** Change orientation */
	this.rotateZ = function( angle)
	{
		orientation[2] += angle;
	}
	this.scale = function( sc)
	{
		scale[0] = sc[0]; scale[1] = sc[1]; scale[2] = sc[2];
	}
	this.translate = function( x,  y,  z)
	{
		position[0] += x;
		position[1] += y;
		position[2] += z;
	}
	
	/** Add Cells to the actors collection. */
	this.addCell = function( cell) { cells.push(cell); }
    
}
OGLRen.Actor.inheritsFrom(OGLRen.ParentObject);
