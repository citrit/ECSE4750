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

var WGLRen = WGLRen || {};

/**
  * Abstract Cell class which defines the API for all drawable
  * entities to follow
  */   
WGLRen.Geom = function(typ, colr) 
{

    var topology = typ;
    var color = colr.concat([1.0]);
	var ptBuffer = gl.createBuffer();
    var ptSet = [];
    var initLess = true;
    
    this.addPoints = function(pts) {
        ptSet = ptSet.concat(pts);
        initLess = true;
    }
    
    this.draw = function() {
        pColor = color;
        gl.uniform4fv(program.pColorUniform, pColor);
        gl.bindBuffer(gl.ARRAY_BUFFER, ptBuffer);
        if (initLess) {
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(ptSet), gl.STATIC_DRAW);
            initLess = false;
        }
        gl.vertexAttribPointer(program.vPosition, 3, gl.FLOAT, false, 0, 0);
        gl.drawArrays(topology, 0, ptSet.length/3);
    }
    
    
    
}