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
OGLRen.PolylineCell = function() {
    /**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
    this.render = function( aren)
	{
		var cnt = 0, ptsBuffer = null;

		cnt = mtSet.size();
		aren.beginDraw(aren.POLYLINE);
        if (this.initLess) {
            for (var i=0;i<intVals.size();i++) {
                aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
                aren.vertex(ptSet.getPoint(getVal(i))); 
            }
            ptsBuffer = aren.getPtsBuffer();
            this.initLess = false;
        }
		aren.endDraw(ptsBuffer);
	}
}
OGLRen.PolylineCell.inheritsFrom(OGLRen.Cell);
