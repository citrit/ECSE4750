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
    /**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	this.render = function( aren)
	{
		var cnt, i, ptsBuffer = null;

		cnt = mtSet.size();
		// Load the texture first.
		if (imTex != null) {
			aren.loadTexture(0, imTex);
		}
		if (nNormal != null)
			aren.normal(nNormal);
		else
			aren.lighting(false);

		aren.beginDraw(aren.POLYGON);
        if (initLess) {
            if (cnt == 1) { // we have only one material set it outside the loop
                aren.setMaterial(mtSet.getMaterial(0));
                for (i=0;i<intVals.length;i++) {
                    if (tCoords != null)
                        aren.texCoord(tCoords.getTexCoord(i));
                    aren.vertex(ptSet.getPoint(getVal(i))); 
                }
            }
            else {
                for (i=0;i<intVals.length;i++) {
                    aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
                    if (tCoords != null)
                        aren.texCoord(tCoords.getTexCoord(i));
                    aren.vertex(ptSet.getPoint(getVal(i))); 
                }
            }
            ptsBuffer = aren.getPtsBuffer();
            this.initLess = false;
        }
		aren.endDraw(ptsBuffer);
	}
}
OGLRen.PolygonCell.inheritsFrom(OGLRen.Cell);
