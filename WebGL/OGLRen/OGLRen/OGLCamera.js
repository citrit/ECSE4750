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
  * Camera class is a holder of cells into single objects.
  */
OGLRen.OGLCamera = function(aren) {
    var gl;
    var renderer;
    var pMatrix = mat4.create();
    mat4.identity(pMatrix);
    
    /** Render the camera **/
    this.render = function () {
        this.init();
        renderer.setPerspectiveMatrix(pMatrix);
    }
    
    /** Called by the Renderer after added */
    this.setRenderer = function( aren) { 
		renderer = aren;
		gl = aren.getGL();
        this.init();
	}

	/**  Method to rotate the camera along the X axis */
    this.rotateX = function( angle)
	{
		mat4.rotateX(pMatrix, pMatrix, angle);
	}
	/**  Method to rotate the camera along the Y axis */
    this.rotateY = function( angle)
	{
		mat4.rotateY(pMatrix, pMatrix, angle);
	}
	/**  Method to rotate the camera along the Z axis */
    this.rotateZ = function( angle)
	{
		mat4.rotateZ(pMatrix, pMatrix, angle);
	}

	/**  Method to translate the camera */
    this.translate = function( val)
	{
		mat4.translate(pMatrix, pMatrix, val);
	}
    
     /** (Hopefully) a method to scale the camera */
    this.scale = function(vec) {
        mat4.scale(pMatrix, pMatrix, vec);
    };


	/** (Hopefully) a method to zoom the camera */
    this.zoom = function( amount)
	{
		mat4.translate(pMatrix, pMatrix, [0, 0, amount]);
	}

	/** Convenience function to make the camera set the viewpoint */
    this.init = function()
	{
        if (this.initLess) {
            var width = renderer.getWidth();
            var height = renderer.getHeight();
            
            //mat4.perspective(pMatrix, 45, width / height, 1, 2000.0);
            /*mat4.lookAt(pMatrix, [this.from.x, this.from.y, this.from.z],
                            [this.to.x, this.to.y, this.to.z],
                            [this.up.x, this.up.y, this.up.z]);*/
            this.initLess = false;
        }
	}
    
    this.setRenderer(aren);
}
OGLRen.OGLCamera.inheritsFrom(OGLRen.Camera);
