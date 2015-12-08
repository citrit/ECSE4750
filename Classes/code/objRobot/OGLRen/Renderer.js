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
OGLRen.Renderer = function(lnum) {
    
    /** some definitions for specifying the drawing mode */
	var POLYGON = 1, 
		LINE = 2, 
		TRIANGLE = 3, 
		LINE_LOOP = 4, 
		POINT = 5, 
		POLYLINE = 6;

}
OGLRen.Renderer.inheritsFrom(OGLRen.Light);

/** Add an Actor to the scene */
OGLRen.Renderer.prototype.addActor = function( actor) {};

/** Add an Light to the scene */
OGLRen.Renderer.prototype.addLight = function( light) {};
/** Turn lighting off if there are no normals */
OGLRen.Renderer.prototype.lighting = function( onOrOff) {};

/** Add an Camera to the scene */
OGLRen.Renderer.prototype.addCamera = function( cam) {};

/** Get the current active Camera, currently only one camera
  * supported
  */
OGLRen.Renderer.prototype.getCamera = function() {};

/** Sets the render mode for consequent calls */
OGLRen.Renderer.prototype.beginDraw = function( renderMode) {};

/** Signal all vertices have been entered, flush the buffer */
OGLRen.Renderer.prototype.endDraw = function() {};

/** specify a vector for the previous begin command */
OGLRen.Renderer.prototype.vertex = function( p) {};
/** specify a texture coord for the vertex */
OGLRen.Renderer.prototype.texCoord = function( p) {};
/** specify a normal for the vertex */
OGLRen.Renderer.prototype.normal = function( n) {};

/** Lets draw something, calls all objects render method,
  * Camera first, then Lights and Actors. Then swap buffers
  */
OGLRen.Renderer.prototype.render = function() {};

/** Lets make sure OpenGL is initialized with some safe settings */
OGLRen.Renderer.prototype.initialize = function() {};

/** Set the current rendering color */
OGLRen.Renderer.prototype.setMaterial = function( mat) {};

/** Push the current Model transformation matrix */
OGLRen.Renderer.prototype.pushModelMatrix = function() {};

/** Set the current orientation for consequent drawing */
OGLRen.Renderer.prototype.setOrientation = function( rotations,  scale, pos) {};

/** Pop the current Model transformation matrix */
OGLRen.Renderer.prototype.popModelMatrix = function() {};

/** Load the current texture
 */
OGLRen.Renderer.prototype.loadTexture = function( ImageNum,  atex) {};

/** Get/Set the width and Height of the render window */
OGLRen.Renderer.prototype.getWidth = function() {};
OGLRen.Renderer.prototype.getHeight = function() {};
OGLRen.Renderer.prototype.setSize = function( w,  h) {};