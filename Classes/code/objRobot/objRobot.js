
 /*gl_Position = vec4(aVertexPosition, 1.0);
            uPMatrix * 
            */

var ren = null,
    robot = null;

function main() {
    robot = readExampleData(cubeData);

    ren = new OGLRen.OGLRenderer("3d-Canvas","shader-vs","shader-fs");
    var cam = new OGLRen.OGLCamera(ren);
    //ren.setOrientation(null, null,  [0, 0, -2.5]);
    ren.addCamera(cam);
    cam.zoom(1);
    var lgt = new OGLRen.OGLLight();
    ren.addLight(lgt);
    ren.addActor(robot);
    robot.translate(0,0,-1);
    ren.render();
    
    document.onkeydown = handleKeyDown;
    document.onkeyup = handleKeyUp;
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
            ren.render();
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
        robot.rotateY(degToRad(-5.0));
    }
    if (currentlyPressedKeys[39]) {
        // Right cursor key
        robot.rotateY(degToRad(5.0));
    }
    if (currentlyPressedKeys[38]) {
        // Up cursor key
        robot.rotateX(degToRad(5.0));
    }
    if (currentlyPressedKeys[40]) {
        // Down cursor key
        robot.rotateX(degToRad(-5.0));
    }
    //console.log("screenRot: " + screenRot);
    ren.render();
}

function degToRad(degrees) {
    return degrees * Math.PI / 180;
}


function makeMaterial(r, g, b) {
    var mat = new OGLRen.Material();
    mat.setAmbient(val[2], val[3], val[4]);
    var ret = new OGLRen.MaterialSet();
    ret.addMaterial(mat);
    return ret;
}


function readExampleData(objData) {
    var ret = new OGLRen.Actor();
    var cc = null;
    var cn = null;
    for (var ii = 0;ii < objData.length; ii++) {
        val = objData[ii];
        switch(val[0]) {
             case -1: // Normals.
                console.log(' Read normals [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                cn = objData[ii];
                console.log(objData[ii]);
                break;
           case 0: // coords
                console.log(' Read coords [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                cc = new OGLRen.PointSet();
                for (var jj = 0;jj< val[1]; jj++) {
                    cc.addPoint([objData[ii][jj*3], objData[ii][jj*3+1], objData[ii][jj*3+2]]);
                }
                console.log(objData[ii]);
                break;
            case 1: // Point topology
                console.log(' Read Point Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new OGLRen.PointCell();
                pGeom.setMaterials(makeMaterial(val[2], val[3], val[4]));
                pGeom.setPoints(cc);
                ii++;
                console.log(objData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    pGeom.addVal(objData[ii][jj]);
                }
                ret.addCell(pGeom);
                break;
            case 2: // Lines topology
                // [2,2,1.0,1.0,0.0],
                // [31,32,33,34],
                console.log(' Read Line Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new OGLRen.LineCell();
                pGeom.setMaterials(makeMaterial(val[2], val[3], val[4]));
                pGeom.setPoints(cc);
                ii++;
                console.log(objData[ii]);
                for (var jj=0;jj<val[1]*2;jj++) {
                    pGeom.addVal(objData[ii][jj]);
                }
                ret.addCell(pGeom);
                break;
            case 3: // Polygons topology
                // [3,1,0.0,1.0,1.0],
                // [1,2,3,4,5,6,-1],
                console.log(' Read Polygon Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var mat = makeMaterial(val[2], val[3], val[4]);
                ii++;
                console.log(objData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var pGeom = new OGLRen.PolygonCell();
                    pGeom.setMaterials(mat);
                    pGeom.setPoints(cc);
                    var kk = 0;
                    var idx = objData[ii][kk];
                    do {
                        pGeom.addVal(idx);
                        kk++;
                        idx = objData[ii][kk];
                    } while (idx != -1 && kk < objData[ii].length);
                    ret.addCell(pGeom);
                }
                break;
            case 4: // Triangles topology
                // [4,1,1.0,0.0,1.0],
                // [14,15,16],
                console.log(' Read Triangle Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new OGLRen.TriangleCell();
                pGeom.setMaterials(makeMaterial(val[2], val[3], val[4]));
                pGeom.setPoints(cc);
                ii++;
                console.log(objData[ii]);
                for (var jj=0;jj<val[1]*3;jj++) {
                    var idx = objData[ii][jj];
                    pGeom.addVal(idx);
                    if (cn.length > 0) {
                        var nm = [cn[idx*3], cn[idx*3+1], cn[idx*3+2]];
                        pGeom.addNormal(nm);
                    }
                }
                ret.addCell(pGeom);
                break;
            case 5: // PolyLines  topology
                // [5,1,0.0,0.0,1.0],
                // [1,2,3,4,5,6,7,8,-1],
                console.log(' Read Polygon Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var mat = makeMaterial(val[2], val[3], val[4]);
                ii++;
                console.log(objData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var pGeom = new OGLRen.PolylineCell();
                    pGeom.setMaterials(mat);
                    pGeom.setPoints(cc);
                    var kk = 0;
                    var idx = objData[ii][kk];
                    do {
                        pGeom.addVal(idx);
                        kk++;
                        idx = objData[ii][kk];
                    } while (idx != -1 && kk < objData[ii].length);
                    ret.addCell(pGeom);
                }
                break;
        }
    }
    return ret;
}