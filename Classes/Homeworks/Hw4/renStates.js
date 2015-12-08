var scene = null;

var createScene = function (canvas, engine, data) {
    scene = new BABYLON.Scene(engine);

    var camera = new BABYLON.ArcRotateCamera("Camera", 0,0,0, new BABYLON.Vector3(-105,0, 40), scene);
    camera.setPosition(new BABYLON.Vector3(0,100,0));
    
    //var camera = new BABYLON.FreeCamera("Camera", new BABYLON.Vector3(-105,100, 40), scene);
    //camera.setTarget(new BABYLON.Vector3(-105,0,40));

    camera.attachControl(canvas, true);

    var light = new BABYLON.HemisphericLight("hemi", new BABYLON.Vector3(-105, 100, 20), scene);

    var states = new BABYLON.Mesh("UnitedStates", scene);

    //process text file line by line
    $(data).find('Placemark').each(function() {
        var stName = $(this).find('name').text();
        var state = new BABYLON.Mesh(stName, scene);
        state.parent = states;
        console.log('State: ' + $(this).find('name').text());
        var polys = $(this).find('MultiGeometry').find('Polygon');
        if (polys.length == 0)
            polys = $(this).find('Polygon');
        $.each(polys, function( index, value ) {
            var pts = $(this).find('outerBoundaryIs').find('LinearRing').
                              find('coordinates').text().replace(/,0 /g," ").replace(/,/g, " ");
            var ground = new BABYLON.PolygonMeshBuilder(stName + "_" + index, 
                                                        BABYLON.Polygon.Parse(pts), scene).build();
            ground.parent = state;
            ground.material = new BABYLON.StandardMaterial("red", scene);
            ground.material.diffuseColor = new BABYLON.Color3(Math.random(), Math.random(), Math.random());
            console.log( "Polygon: " + index  );
        });
    });
    
    return scene;
}

function degToRad(degrees) {
    return degrees * Math.PI / 180;
}

function main() {
    // Read the KML file
    jQuery.get('cb_2014_us_state_20m.xml', function(data) {
        //alert(data);
        
        // Setup the Babylon system
        var canvas = document.getElementById("renderCanvas");
        var engine = new BABYLON.Engine(canvas, true);

        // Read the KML file and generate the scene.
        scene = new createScene(canvas, engine, data);

        // Lets loop on it.
        engine.runRenderLoop(function () {
            scene.render();
        });
        
        // Handle window Resize
        window.addEventListener("resize", function () {
            engine.resize();
        });
    });
}

