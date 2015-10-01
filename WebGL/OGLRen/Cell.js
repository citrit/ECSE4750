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

	var ptSet = null;
	var mtSet = new OGLRen.MaterialSet();
    var intVals = [];
	var imTex = null;
	var tCoords = null;
	var nNormal = null;
    this.initLess = true;

	/**
	  * Simply set the internal pointer to the Material Set
	  */
	this.setMaterials = function( mat) { mtSet = mat;this.initLess = true; }
    this.getMaterials = function() { return mtSet; }

	/**
	  * Set the internal pointer to the current PointSet
	  */
	this.setPoints = function( pts) { ptSet = pts;this.initLess = true; }
    this.getPoints = function( ) { return ptSet; }

	/**
	  * Set the internal pointer to the current Texture
	  */
	this.setTexture = function( atex) { imTex = atex;this.initLess = true; }
	this.getTexture = function( ) { return imTex; }
	/**
	  * Set the internal pointer to the current TextureCoords
	  */
	this.setTexCoords = function( tcoords) { tCoords = tcoords;this.initLess = true; }
	this.getTexCoords = function( ) { return tCoords; }
	/**
	  * Set the internal pointer to the current Normal for the cell
	  */
	this.setNormal = function( n) { nNormal = n;this.initLess = true; }
    this.getNormal = function( ) { return nNormal; }

	/**
	  * All cells are simply holders of indeces into a pointset
	  * The derived class defines the cells topology. 
	  */
	this.addVal = function( val) { intVals.push(val);this.initLess = true; }

	/**
	  * Convenience function to get a index value.
	  */
	this.getVal = function( pos) { return intVals[pos]; }

	/**
	  * Return the number of indeces
	  */
	this.size = function() { return intVals.length; }
}

OGLRen.Cell.inheritsFrom(OGLRen.ParentObject);
/**
	  * Abstract render method overridden in the derived classes
	  * to define the specific cells purpose
	  */
OGLRen.Cell.prototype.render = function(aren) { alert('Can;t call abstract method'); }


