var scene = null;

var createScene = function (canvas, engine, data) {
    scene = new BABYLON.Scene(engine);

    //var camera = new BABYLON.ArcRotateCamera("Camera", 0,0,0, new BABYLON.Vector3(-105,0, 40), scene);
    var camera = new BABYLON.FreeCamera("Camera", new BABYLON.Vector3(-105,10, 40), scene);
    camera.setTarget(new BABYLON.Vector3(-105,0, 40));

    camera.attachControl(canvas, true);

    var light = new BABYLON.HemisphericLight("hemi", new BABYLON.Vector3(-105, 10, 20), scene);

    var states = new BABYLON.Mesh("UnitedStates", scene);
    
    var usTexture = new BABYLON.Texture("usa-physical-map2.jpg", scene);

    //process text file line by line
    $(data).find('Placemark').each(function() {
        var stName = $(this).find('name').text();
        var state = new BABYLON.Mesh($(this).find('name').text(), scene);
        state.parent = states;
        console.log('State: ' + $(this).find('name').text());
        var polys = $(this).find('MultiGeometry').find('Polygon');
        if (polys.length == 0)
            polys = $(this).find('Polygon');
        $.each(polys, function( index, value ) {
            var pts = $(this).find('outerBoundaryIs').find('LinearRing').find('coordinates').text().replace(/,0 /g," ").replace(/,/g, " ");
            var ground = new BABYLON.PolygonMeshBuilder(stName + "_" + index, 
                                                        BABYLON.Polygon.Parse(pts), scene).build();
            ground.parent = state;
            ground.material = new BABYLON.StandardMaterial(stName + "_" +"Texture", scene);
            ground.material.diffuseTexture = usTexture;
            calculateTextureCoords(ground);
            //ground.material = new BABYLON.StandardMaterial("red", scene);
            //ground.material.diffuseColor = new BABYLON.Color3(Math.random(), Math.random(), Math.random());
            //console.log( index + ": " + pts );
        });
        //var head = BABYLON.Mesh.CreateBox("head", { width: 0.4, height: 0.4, depth: 0.4 }, scene);
    
    });
    //camera.zoomOn(states);
    
    return scene;
}

// From the image
var rx1 = -120.0, ry1 = 23.5,
    rx2 = -75.0,  ry2 = 45.0,
    px1 = 84.0,   py1 = 125.0,
    px2 = 611.0,  py2 = 439.0,
    iWidth = 735, iHeight = 550;
var drx = rx2 - rx1,
    dry = ry2 - ry1,
    dpx = px2 - px1,
    dpy = py2 - py1;

function calculateTextureCoords(ground) {
    var ptCoords = ground.getVerticesData(BABYLON.VertexBuffer.PositionKind);
    //var tCoords = ground.getVerticesData(BABYLON.VertexBuffer.UVKind);
    var numCoords = ptCoords.length/3;
    var uvs = [];
    for (var ii=0; ii < numCoords; ii++) {
        var ptx = ptCoords[ii*3], pty = ptCoords[ii*3+2];
        var tx = (((ptx - rx1)/drx)*dpx)+px1;
        tx = tx/iWidth;
        var ty = (((pty - ry1)/dry)*dpy)+py1;
        ty = ty/iHeight;
        uvs.push(tx);
        uvs.push(ty);
        //tCoords[ii*2] = tx;
        //tCoords[ii*2+1] = ty;
    }
    ground.setVerticesData(BABYLON.VertexBuffer.UVKind, uvs);
}

var rotx = 1, roty = 1, rotz = 1;

function handleScroll(sel) {
    var part = $('#stateSel').find(':selected').val();
    var nde = scene.getNodeByName(part);
    var ndes = nde.getDescendants();
    ndes.forEach(function(element, index, array) {
        element.material.wireframe = true;
    });
    
}

function degToRad(degrees) {
    return degrees * Math.PI / 180;
}

function main() {
    jQuery.get('cb_2014_us_state_20m.xml', function(data) {
        //alert(data);
        
        var canvas = document.getElementById("renderCanvas");
        var engine = new BABYLON.Engine(canvas, true);

        scene = new createScene(canvas, engine, data);

        engine.runRenderLoop(function () {
            scene.render();
        });
        
        // Resize
        window.addEventListener("resize", function () {
            engine.resize();
        });
    });
}

