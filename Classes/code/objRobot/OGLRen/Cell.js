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
OGLRen.Cell = function() 
{

	
}

OGLRen.Cell.inheritsFrom(OGLRen.ParentObject);
/**
	  * Abstract render method overridden in the derived classes
	  * to define the specific cells purpose
	  */
OGLRen.Cell.prototype.render = function(aren) { alert('Can;t call abstract method'); }


