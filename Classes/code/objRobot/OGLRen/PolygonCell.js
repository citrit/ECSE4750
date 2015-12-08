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
  * PolylineCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 3 4 5 ] will draw a polygon thorugh the points and close 
  *   from 1 to 5.
  */
OGLRen.PolygonCell = function() {

    var ptSet = null;
	var mtSet = new OGLRen.MaterialSet();
    var intVals = [];
	var imTex = null;
	var tCoords = null;
	var nNormal = null;
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
    
    var ptsBuffer = null;

    /**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	this.render = function( aren)
	{
		var cnt, i;

		cnt = this.getMaterials().size();
		// Load the texture first.
		if (this.getTexture() != null) {
			aren.loadTexture(0, this.getTexture());
		}
		if (this.getNormal() != null)
			aren.normal(this.getNormal());
		else
			aren.lighting(false);

		aren.beginDraw(aren.POLYGON);
        if (this.initLess) {
            if (cnt == 1) { // we have only one material set it outside the loop
                aren.setMaterial(this.getMaterials().getMaterial(0));
                for (i=0;i<this.size();i++) {
                    if (this.getTexCoords() != null)
                        aren.texCoord(this.getTexCoords().getTexCoord(i));
                    aren.vertex(this.getPoints().getPoint(this.getVal(i))); 
                }
            }
            else {
                for (i=0;i<this.size();i++) {
                    aren.setMaterial(this.getMaterials().getMaterial(this.getVal(i)%cnt));
                    if (this.getTexCoords() != null)
                        aren.texCoord(this.getTexCoords().getTexCoord(i));
                    aren.vertex(this.getPoints().getPoint(this.getVal(i))); 
                }
            }
            ptsBuffer = aren.getPtsBuffer();
            this.initLess = false;
        }
		aren.endDraw(ptsBuffer);
	}
}
OGLRen.PolygonCell.inheritsFrom(OGLRen.Cell);
