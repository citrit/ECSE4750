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
    var color = (colr.length == 3?colr.concat([1.0]):colr);
	var ptBuffer = gl.createBuffer();
    var normBuffer = gl.createBuffer();
    var ptSet = [];
    var ptNorms = [];
    var initLess = true;
    var subGeoms = [];
    var rotate = [0,0,0],
        scale = [1,1,1],
        translate = [0,0,0];
    
    this.addPoints = function(pts) {
        ptSet = ptSet.concat(pts);
        initLess = true;
    }
    this.getPoints = function() { return ptSet; }
    
    this.addNormals = function(ns) {
        ptNorms = ptNorms.concat(ns);
        initLess = true;
    }
    this.getNormals = function() { return ptNorms; }
    
    this.setRotate = function(r) { rotate = r; }
    this.setRotateX = function(r) { rotate[0] = r; }
    this.setRotateY = function(r) { rotate[1] = r; }
    this.setRotateZ = function(r) { rotate[2] = r; }
    this.addRotate = function(r) { rotate[0] = rotate[0] + r[0];
                                     rotate[1] = rotate[1] + r[1];
                                     rotate[2] = rotate[2] + r[2];
                                    }
    this.setScale = function(s) { scale = s; }
    this.addScale = function(s) { scale[0] = scale[0] + s[0];
                                    scale[1] = scale[1] + s[1];
                                    scale[2] = scale[2] + s[2];
                                    }
    this.setTranslate = function(t) { translate = t; }
    this.addTranslate = function(t) { translate[0] = translate[0] + t[0];
                                        translate[1] = translate[1] + t[1];
                                        translate[2] = translate[2] + t[2];
                                    }
    
    this.setColor = function(col) { color = col; }
    
    this.copy = function() {
        var ret = new WGLRen.Geom(topology, color);
        ret.setRotate(rotate.concat());
        ret.setScale(scale.concat());
        ret.setTranslate(translate.concat());
        ret.addPoints(ptSet);
        ret.addNormals(ptNorms);
        for(var ii=0;ii<subGeoms.length;ii++) {
            ret.addGeom(subGeoms[ii].copy());
        }
        return ret;
    }
    this.addGeom = function(g) {
        subGeoms.push(g);
    }
    this.getSubGeoms=  function() { return subGeoms; }
    
    this.draw = function() {
        pColor = color.concat([1]);
        gl.uniform4fv(program.pColorUniform, pColor);
        mvPushMatrix(rotate, scale, translate);
        if (initLess) {
            gl.bindBuffer(gl.ARRAY_BUFFER, ptBuffer);
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(ptSet), gl.STATIC_DRAW);
            gl.bindBuffer(gl.ARRAY_BUFFER, normBuffer);
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(ptNorms), gl.STATIC_DRAW);
            initLess = false;
        }
        gl.bindBuffer(gl.ARRAY_BUFFER, ptBuffer);
        gl.vertexAttribPointer(program.vPosition, 3, gl.FLOAT, false, 0, 0);
        gl.bindBuffer(gl.ARRAY_BUFFER, normBuffer);
        gl.vertexAttribPointer(program.vNormal, 3, gl.FLOAT, false, 0, 0);
        gl.drawArrays(topology, 0, ptSet.length/3);
        subGeoms.forEach(function(element, index, array) {
                element.draw();
            });
        mvPopMatrix();
    }
    
}