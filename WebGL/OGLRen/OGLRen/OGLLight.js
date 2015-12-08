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
  * WebGL Light class
  */
OGLRen.OGLLight = function(lnum) {
    /** Called by the Renderer to initialize the light */
	this.render = function( aren)
	{
		var gl = aren.getGL();
		var lightnum = 0;
        var from = new OGLRen.PointType(10, 10, 10);
		var to = new OGLRen.PointType();
		color = new OGLRen.Material();
		var lightPos = [from.x, from.y, from.z, 0.0];
		var lightDir = [to.x, to.y, to.z, 0.0];

		if (this.on == true) {
		    switch (this.number) {
				case 0: lightnum = gl.LIGHT0; break;
				case 1: lightnum = gl.LIGHT1; break;
				case 2: lightnum = gl.LIGHT2; break;
				case 3: lightnum = gl.LIGHT3; break;
				case 4: lightnum = gl.LIGHT4; break;
				case 5: lightnum = gl.LIGHT5; break;
				case 6: lightnum = gl.LIGHT6; break;
				case 7: lightnum = gl.LIGHT7; break;
			}
			//gl.enable(gl.LIGHTING);
			//gl.enable(lightnum);
			//gl.light(lightnum, gl.POSITION, lightPos);
			//gl.light(lightnum, gl.SPOT_DIRECTION, lightDir);
			//if (color != null) {
				//gl.light(lightnum, gl.AMBIENT, this.getColor().ambient);
				//gl.light(lightnum, gl.DIFFUSE, this.getColor().diffuse);
				//gl.light(lightnum, gl.SPECULAR, this.getColor().ambient);
			//}
		}
		else {
			//gl.disable(gl.LIGHTING);
		}
	}
}
OGLRen.OGLLight.inheritsFrom(OGLRen.Light);

