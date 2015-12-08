
var gl;
var points;
var program;
var mvMatrix = mat4.create();
var pMatrix = mat4.create();
var pColor = vec4.create();
var geoms = [];
var screenRot = [0, 0, 0];
var shoulder, elbow, wrist;

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
    gl.enable(gl.DEPTH_TEST);

    
    //  Load shaders and initialize attribute buffers
    
    program = initShaders( gl, "vertex-shader", "fragment-shader" );
    gl.useProgram( program );
    
    // Associate out shader variables with our data buffer
    
    program.vPosition = gl.getAttribLocation( program, "aVertexPosition" );
    gl.enableVertexAttribArray( program.vPosition );
    program.vNormal = gl.getAttribLocation( program, "aVertexNormal" );
    gl.enableVertexAttribArray( program.vNormal );
    program.pMatrixUniform = gl.getUniformLocation(program, "uPMatrix");
    program.mvMatrixUniform = gl.getUniformLocation(program, "uMVMatrix");
    program.pColorUniform = gl.getUniformLocation(program, "uColor");
    
    //pMatrix = mat4.identity(pMatrix);
    //pMatrix = mat4.perspective(pMatrix, 45, canvas.width / canvas.height, 0.1, 100.0);
    //pMatrix = mat4.frustum(pMatrix, 5, -5, 5, -5, 0.1, 100);
    //pMatrix = mat4.lookAt(pMatrix, [1,0,0], [0,0,0], [0,1,0]);


    // read through example data
    readExampleData();
    
    // Scale box for arm and create 2 new ones.
    /*shoulder = geoms[0];
    shoulder.setTranslate([0,0,0]);
    shoulder.setScale([1,1,1]);
    shoulder.setColor([0,0,1]);
    
    elbow = geoms[0].copy();
    elbow.setTranslate([0,0,1]);
    
    wrist = geoms[0].copy();
    wrist.setColor([1,1,0]);
    wrist.setTranslate([0,0,1]);
    shoulder.addGeom(elbow);
    
    elbow.addGeom(wrist);*/
    shoulder = geoms[0];
    shoulder.setColor([0,0,1]);

    elbow = geoms[0].copy();
    elbow.setTranslate([0,0,1]);
    elbow.setColor([0,1,0]);

    wrist = elbow.copy();
    wrist.setTranslate([0,0,-1]);
    wrist.setColor([1,0,0]);

    shoulder.addGeom(elbow);    
    elbow.addGeom(wrist);

    document.onkeydown = handleKeyDown;
    document.onkeyup = handleKeyUp;
            
    render();
};

var mvMatrixStack = [];

function mvPushMatrix(r, s, t) {
    
    var copy = mat4.copy(mat4.create(), mvMatrix);
    mvMatrixStack.push(copy);
    //mat4.identity(mvMatrix);
    mvMatrix = mat4.rotateX(mvMatrix, mvMatrix, r[0] );
    mvMatrix = mat4.rotateY(mvMatrix, mvMatrix, r[1] );
    mvMatrix = mat4.rotateZ(mvMatrix, mvMatrix, r[2] );
    mvMatrix = mat4.scale(mvMatrix, mvMatrix, s);
    mvMatrix = mat4.translate(mvMatrix, mvMatrix, t);
    setUniforms();
}

function mvPopMatrix() {
    if (mvMatrixStack.length == 0) {
        throw "Invalid popMatrix!";
    }
    mvMatrix = mvMatrixStack.pop();
    setUniforms();
}


function setUniforms() {
    gl.uniformMatrix4fv(program.pMatrixUniform, false, pMatrix);
    gl.uniformMatrix4fv(program.mvMatrixUniform, false, mvMatrix);
    
}


function render() {
    //console.log("Rendering: " + screenRot);
    gl.clear( gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT );

    mvMatrix = mat4.identity(mvMatrix);
    mvMatrix = mat4.rotateX(mvMatrix, mvMatrix, screenRot[0]);
    mvMatrix = mat4.rotateY(mvMatrix, mvMatrix, screenRot[1]);
    mvMatrix = mat4.rotateZ(mvMatrix, mvMatrix, screenRot[2]);
    mvMatrix = mat4.scale(mvMatrix, mvMatrix, [0.2, 0.2, 0.2]);
    mvMatrix = mat4.translate(mvMatrix, mvMatrix, [1, 0, 0.0]);
    
    
    setUniforms();
         
    for (var ii=0;ii<geoms.length;ii++) {
        geoms[ii].draw();
    }
    //requestAnimFrame( render );
}

function handleScroll(slider) {
    var part = slider.name;
    var ori = slider.attributes["orien"].nodeValue;
    var val = slider.value;
    switch (part) {
        case 'world':
            switch (ori) {
                case 'x':
                    screenRot[0] = degToRad(val);
                    break;
                case'y':
                    screenRot[1] = degToRad(val);
                    break;
                case 'z':
                    screenRot[2] = degToRad(val);
                    break;
            }
            break;
        default:
            var elem = null;
            if (part == "shoulder") elem = shoulder;
            if (part == "elbow") elem = elbow;
            if (part == "wrist") elem = wrist;
            
            switch (ori) {
                case 'x':
                    elem.setRotateX(degToRad(val));
                    break;
                case'y':
                    elem.setRotateY(degToRad(val));
                    break;
                case 'z':
                    elem.setRotateZ(degToRad(val));
                    break;
            }
            break;
    }
    render();
}




function readExampleData() {
    var cc = [];
    var cn = [];
    for (var ii = 0;ii < exampleData.length; ii++) {
        val = exampleData[ii];
        switch(val[0]) {
            case -1: // Normals.
                console.log(' Read normals [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                cn = exampleData[ii];
                console.log(exampleData[ii]);
                break;
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
                    var pt = [cc[idx*3], cc[idx*3+1], cc[idx*3+2]];
                    pGeom.addPoints(pt);
                    if (cn.length > 0) {
                        var nm = [cn[idx*3], cn[idx*3+1], cn[idx*3+2]];
                        pGeom.addNormals(pt);
                    }
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
                    if (cn.length > 0) {
                        var nm = [cn[idx*3], cn[idx*3+1], cn[idx*3+2]];
                        pGeom.addNormals(pt);
                    }
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
                        if (cn.length > 0) {
                            var nm = [cn[idx*3], cn[idx*3+1], cn[idx*3+2]];
                            pGeom.addNormals(pt);
                        }
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
                    if (cn.length > 0) {
                        var nm = [cn[idx*3], cn[idx*3+1], cn[idx*3+2]];
                        pGeom.addNormals(pt);
                    }
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
                        if (cn.length > 0) {
                            var nm = [cn[idx*3], cn[idx*3+1], cn[idx*3+2]];
                            pGeom.addNormals(pt);
                        }
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
    console.log("Key: " + event.keyCode);
    handleKeys(); 
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
    //console.log("Key: " + event.keyCode);
    //handleKeys();   
}


function handleKeys() {
    if (currentlyPressedKeys[189]) {
        // Page Up
        z -= 0.05;
    }
    if (currentlyPressedKeys[187]) {
        // Page Down
        z += 0.05;
    }
    if (currentlyPressedKeys[37]) {
        // Left cursor key
        console.log("Left key");
        screenRot[1] = screenRot[1] - degToRad(5.0);
    }
    if (currentlyPressedKeys[39]) {
        // Right cursor key
        screenRot[1] = screenRot[1] + degToRad(5.0);
    }
    if (currentlyPressedKeys[38]) {
        // Up cursor key
        screenRot[0] = screenRot[0] + degToRad(5.0);
    }
    if (currentlyPressedKeys[40]) {
        // Down cursor key
        screenRot[0] = screenRot[0] - degToRad(5.0);
    }
    //console.log("screenRot: " + screenRot);
    render();
}

function degToRad(degrees) {
    return degrees * Math.PI / 180;
}
