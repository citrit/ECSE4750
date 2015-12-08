
 /*gl_Position = vec4(aVertexPosition, 1.0);
            uPMatrix * 
            */

function main() {
    var act = readExampleData();

    var ren = new OGLRen.OGLRenderer("3d-Canvas","shader-vs","shader-fs");
    var cam = new OGLRen.OGLCamera(ren);
    ren.setOrientation([0, 0, 0], [0.5, 0.5, 1.0],  [-2.5, -2.5, 0.0]);
    ren.addCamera(cam);
    //cam.setTo(2.5, 2.5, 0.0);
    var lgt = new OGLRen.OGLLight();
    ren.addLight(lgt);
    ren.addActor(act);
    ren.render();
}

function makeMaterial(r, g, b) {
    var mat = new OGLRen.Material();
    mat.setAmbient(val[2], val[3], val[4]);
    var ret = new OGLRen.MaterialSet();
    ret.addMaterial(mat);
    return ret;
}

function readExampleData() {
    var ret = new OGLRen.Actor();
    var cc = null;
    for (var ii = 0;ii < exampleData.length; ii++) {
        val = exampleData[ii];
        switch(val[0]) {
            case 0: // coords
                console.log(' Read coords [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                ii++;
                cc = new OGLRen.PointSet();
                for (var jj = 0;jj< val[1]; jj++) {
                    cc.addPoint([exampleData[ii][jj*3], exampleData[ii][jj*3+1], exampleData[ii][jj*3+2]]);
                }
                console.log(exampleData[ii]);
                break;
            case 1: // Point topology
                console.log(' Read Point Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var pGeom = new OGLRen.PointCell();
                pGeom.setMaterials(makeMaterial(val[2], val[3], val[4]));
                pGeom.setPoints(cc);
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    pGeom.addVal(exampleData[ii][jj]);
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
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1]*2;jj++) {
                    pGeom.addVal(exampleData[ii][jj]);
                }
                ret.addCell(pGeom);
                break;
            case 3: // Polygons topology
                // [3,1,0.0,1.0,1.0],
                // [1,2,3,4,5,6,-1],
                console.log(' Read Polygon Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var mat = makeMaterial(val[2], val[3], val[4]);
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var pGeom = new OGLRen.PolygonCell();
                    pGeom.setMaterials(mat);
                    pGeom.setPoints(cc);
                    var kk = 0;
                    var idx = exampleData[ii][kk];
                    do {
                        pGeom.addVal(idx);
                        kk++;
                        idx = exampleData[ii][kk];
                    } while (idx != -1 && kk < exampleData[ii].length);
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
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1]*3;jj++) {
                    var idx = exampleData[ii][jj];
                    pGeom.addVal(idx);
                }
                ret.addCell(pGeom);
                break;
            case 5: // PolyLines  topology
                // [5,1,0.0,0.0,1.0],
                // [1,2,3,4,5,6,7,8,-1],
                console.log(' Read Polygon Topology [' + val[1] + ']: RGB(' + val[2] + ',' + val[3] + ',' + val[4] + ')');
                var mat = makeMaterial(val[2], val[3], val[4]);
                ii++;
                console.log(exampleData[ii]);
                for (var jj=0;jj<val[1];jj++) {
                    var pGeom = new OGLRen.PolylineCell();
                    pGeom.setMaterials(mat);
                    pGeom.setPoints(cc);
                    var kk = 0;
                    var idx = exampleData[ii][kk];
                    do {
                        pGeom.addVal(idx);
                        kk++;
                        idx = exampleData[ii][kk];
                    } while (idx != -1 && kk < exampleData[ii].length);
                    ret.addCell(pGeom);
                }
                break;
        }
    }
    return ret;
}