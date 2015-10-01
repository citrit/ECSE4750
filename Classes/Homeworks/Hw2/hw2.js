
var gl;
var points;
var program;
var mvMatrix = mat4.create();
var pMatrix = mat4.create();
var pColor = vec4.create();
var geoms = [];
var screenRot = [0, 0, 0];

window.onload = function init()
{
    
    var canvas = document.getElementById( "gl-canvas" );
    
    gl = WebGLUtils.setupWebGL( canvas );
    if ( !gl ) { alert( "WebGL isn't available" ); }

    //
    //  Configure WebGL
    //
    gl.viewport( 0, 0, canvas.width, canvas.height );
    gl.clearColor( 0.0, 0.0, 0.0, 1.0 );
    
    //  Load shaders and initialize attribute buffers
    
    program = initShaders( gl, "vertex-shader", "fragment-shader" );
    gl.useProgram( program );
    
    // Associate out shader variables with our data buffer
    
    program.vPosition = gl.getAttribLocation( program, "vPosition" );
    gl.enableVertexAttribArray( program.vPosition );
    program.pMatrixUniform = gl.getUniformLocation(program, "uPMatrix");
    program.mvMatrixUniform = gl.getUniformLocation(program, "uMVMatrix");
    program.pColorUniform = gl.getUniformLocation(program, "uColor");

    // read through example data
    readExampleData();
 
    document.onkeydown = handleKeyDown;
    document.onkeyup = handleKeyUp;
            
    render();
};

function setUniforms() {
    gl.uniformMatrix4fv(program.pMatrixUniform, false, pMatrix);
    gl.uniformMatrix4fv(program.mvMatrixUniform, false, mvMatrix);
    
}


function render() {
    console.log("Rendering");
    gl.clear( gl.COLOR_BUFFER_BIT );

    console.log("screenRot: " + screenRot);
    mat4.identity(mvMatrix);
    mat4.rotateX(mvMatrix, mvMatrix, screenRot[0]);
    mat4.rotateY(mvMatrix, mvMatrix, screenRot[1]);
    mat4.rotateZ(mvMatrix, mvMatrix, screenRot[2]);
    mat4.scale(mvMatrix, mvMatrix, [0.5, 0.5, 1.0]);
    mat4.translate(mvMatrix, mvMatrix, [-2.5, -2.5, 0.0]);
    
    
    setUniforms();
         
    for (var ii=0;ii<geoms.length;ii++) {
        geoms[ii].draw();
    }
}

function readExampleData() {
    var cc = [];
    for (var ii = 0;ii < exampleData.length; ii++) {
        val = exampleData[ii];
        switch(val[0]) {
            case 0: // coords
                console.log(' Read coords [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                cc = exampleData[ii];
                console.log(exampleData[ii]);
                break;
            case 1: // Point topology
                console.log(' Read Point Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new WGLRen.Geom(gl.POINTS, [val[2], val[3], val[4]]);
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var idx = exampleData[ii][jj];
                    var pt = [cc[idx*3], cc[idx*3+1], cc[idx*3+2]]
                    pGeom.addPoints(pt);
                }
                geoms.push(pGeom);
                break;
            case 2: // Lines topology
                // [2,2,1.0,1.0,0.0],
                // [31,32,33,34],
                console.log(' Read Line Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new WGLRen.Geom(gl.LINES, [val[2], val[3], val[4]]);
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1]*2;jj++) {
                    var idx = exampleData[ii][jj];
                    var pt = [cc[idx*3], cc[idx*3+1], cc[idx*3+2]]
                    pGeom.addPoints(pt);
                }
                geoms.push(pGeom);
                break;
            case 3: // Polygons topology
                // [3,1,0.0,1.0,1.0],
                // [1,2,3,4,5,6,-1],
                console.log(' Read Polygon Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var pGeom = new WGLRen.Geom(gl.LINE_LOOP, [val[2], val[3], val[4]]);
                    var kk = 0;
                    var idx = exampleData[ii][kk];
                    do {
                        var pt = [cc[idx*3], cc[idx*3+1], cc[idx*3+2]]
                        pGeom.addPoints(pt);
                        kk++;
                        idx = exampleData[ii][kk];
                    } while (idx != -1 && kk < exampleData[ii].length);
                    geoms.push(pGeom);
                }
                break;
            case 4: // Triangles topology
                // [4,1,1.0,0.0,1.0],
                // [14,15,16],
                console.log(' Read Triangle Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new WGLRen.Geom(gl.TRIANGLES, [val[2], val[3], val[4]]);
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1]*3;jj++) {
                    var idx = exampleData[ii][jj];
                    var pt = [cc[idx*3], cc[idx*3+1], cc[idx*3+2]]
                    pGeom.addPoints(pt);
                }
                geoms.push(pGeom);
                break;
            case 5: // PolyLines  topology
                // [5,1,0.0,0.0,1.0],
                // [1,2,3,4,5,6,7,8,-1],
                console.log(' Read Polygon Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var pGeom = new WGLRen.Geom(gl.LINE_STRIP, [val[2], val[3], val[4]]);
                    var kk = 0;
                    var idx = exampleData[ii][kk];
                    do {
                        var pt = [cc[idx*3], cc[idx*3+1], cc[idx*3+2]]
                        pGeom.addPoints(pt);
                        kk++;
                        idx = exampleData[ii][kk];
                    } while (idx != -1 && kk < exampleData[ii].length);
                    geoms.push(pGeom);
                }
                break;
        }
    }
}

var currentlyPressedKeys = {};

function handleKeyDown(event) {
    currentlyPressedKeys[event.keyCode] = true;

    switch (String.fromCharCode(event.keyCode)) {
        case "F":
            filter += 1;
            if (filter == 3) {
                filter = 0;
            }
            break;
        case "R":
            render();
            break;
    }
}


function handleKeyUp(event) {
    currentlyPressedKeys[event.keyCode] = false;
    console.log("Key: " + event.keyCode);
    handleKeys();
}


function handleKeys() {
    if (currentlyPressedKeys[33]) {
        // Page Up
        z -= 0.05;
    }
    if (currentlyPressedKeys[34]) {
        // Page Down
        z += 0.05;
    }
    if (currentlyPressedKeys[37]) {
        // Left cursor key
        screenRot[1] -= 1;
    }
    if (currentlyPressedKeys[39]) {
        // Right cursor key
        screenRot[1] += 1;
    }
    if (currentlyPressedKeys[38]) {
        // Up cursor key
        screenRot[0] += 1;
    }
    if (currentlyPressedKeys[40]) {
        // Down cursor key
        screenRot[0] -= 1;
    }
}
