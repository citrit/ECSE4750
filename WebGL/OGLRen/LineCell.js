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
  * LineCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 2 3 3 4 4 5 ] will draw a line from 1-2-3-4-5
  *   [ 1 2 3 4 5 ] will draw a line from 1-2 then 3-4 skipping 5
  */
OGLRen.LineCell = function() {
    
	/**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	this.render = function( aren)
	{
		var cnt = 0, ptsBuffer = null;

		cnt = this.getMaterials().size();
		aren.beginDraw(aren.LINES);
        if (this.initLess) {
            for (var i=0;i<this.size();i++) {
                aren.setMaterial(this.getMaterials().getMaterial(this.getVal(i)%cnt));
                aren.vertex(this.getPoints().getPoint(this.getVal(i))); 
            }
            ptsBuffer = aren.getPtsBuffer();
            this.initLess = false;
		}
		aren.endDraw(ptsBuffer);
	}
}
OGLRen.LineCell.inheritsFrom(OGLRen.Cell);
