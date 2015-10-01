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
  * PointCell class 
  */
OGLRen.PointCell = function() {
    /** Render method is called by the Renderer and this Cell "tells"
	  * The renderer how to draw itself.
	  */
	this.render = function( aren)
	{
		var cnt = 0, ptsBuffer = null;

		cnt = this.getMaterials().size();
		aren.beginDraw(aren.POINT);
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
OGLRen.PointCell.inheritsFrom(OGLRen.Cell);