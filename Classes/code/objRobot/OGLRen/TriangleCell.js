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
  * TirangleCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 2 3 3 4 4 5 ] will draw a line from 1-2-3-4-5
  *   [ 1 2 3 4 5 ] will draw a line from 1-2 then 3-4 skipping 5
  */
OGLRen.TriangleCell = function() {
    var ptSet = null;
	var mtSet = new OGLRen.MaterialSet();
    var intVals = [];
	var imTex = null;
	var tCoords = null;
	var nNormal = [];
    var initLess = true;

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
	this.addNormal = function( n) { nNormal = nNormal.concat(n);this.initLess = true; }
    this.getNormal = function(at ) { return [nNormal[at*3],nNormal[at*3+1], nNormal[at*3+2]]; }

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
    
    var ptsBuffer = null;
    var nrmBuffer = null;
    /**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	this.render = function( aren)
	{
		var cnt = 0;
		
		cnt = this.getMaterials().size();
		if (cnt == 1) {
			aren.setMaterial(this.getMaterials().getMaterial(0));
		}
		aren.beginDraw(aren.TRIANGLE);
		
        if (this.initLess) {
            for (var i=0;i<this.size();i++) {
                if (cnt > 1) {
                    aren.setMaterial(this.getMaterials().getMaterial(this.getVal(i)%cnt));
                }
                if (this.getNormal(0) != null)
			         aren.normal(this.getNormal(this.getVal(i)));
                else
			         aren.lighting(false);
                
                aren.vertex(this.getPoints().getPoint(this.getVal(i))); 
            }
            ptsBuffer = aren.getPtsBuffer();
            if (this.getNormal(0) != null)
                nrmBuffer = aren.getNormBuffer();
            this.initLess = false;
        }
		aren.endDraw(ptsBuffer, nrmBuffer);
	}
}
OGLRen.TriangleCell.inheritsFrom(OGLRen.Cell);
