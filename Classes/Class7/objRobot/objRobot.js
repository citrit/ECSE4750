var scene = null;

var createScene = function (canvas, engine) {
    scene = new BABYLON.Scene(engine);

    var camera = new BABYLON.ArcRotateCamera("Camera", Math.PI, Math.PI / 8, 5, BABYLON.Vector3.Zero(), scene);

    camera.attachControl(canvas, true);

    var light = new BABYLON.HemisphericLight("hemi", new BABYLON.Vector3(1, 3, 2), scene);

     /* Lets create a robot
    // Robot:
        // Head
        // chest
            // right shoulder
                // right Elbow
                // right Wrist
            // Left Shoulder
                // left Elbow
                // Left Wrist
        // Waist
            // Right Leg
            // Left Leg
        
    */
    var robot = BABYLON.Mesh.CreateBox("lamont", 0.0001, scene); 
    var head = BABYLON.Mesh.CreateBox("head", { width: 0.4, height: 0.4, depth: 0.4 }, scene);
    head.parent = robot;
    head.position.y = 0.6;
    head.material = new BABYLON.StandardMaterial("red", scene);
    head.material.diffuseColor = new BABYLON.Color3(0.8, 0.0, 0.0);
    var chest = BABYLON.Mesh.CreateBox("chest", { width: 1, height: 1, depth: 0.6 }, scene);
    chest.material = new BABYLON.StandardMaterial("red", scene);
    chest.material.diffuseColor = new BABYLON.Color3(0.0, 0.0, 0.8);
    //chest.scaling.y = 3;
    //chest.scaling.x = 2;
    chest.parent = robot;
    createArm("right",  chest);
    createArm("left",  chest);
    
    var waist = BABYLON.Mesh.CreateBox("waist", { width: 1, height: 0.4, depth: 0.6 }, scene);
    waist.parent = robot;
    waist.position.y = -0.7;
    waist.material = new BABYLON.StandardMaterial("yellow", scene);
    waist.material.diffuseColor = new BABYLON.Color3(0.0, 0.8, 0.8);

    createLeg("right", waist);
    createLeg("left", waist);
    
    return scene;
}

function createArm(side, parent) {
    var opts = { width: 0.25, height: 0.5, depth: 0.25 };
    var shoulder = BABYLON.Mesh.CreateBox(side + "shoulder",opts, scene);
    shoulder.parent = parent;
    shoulder.position.x = (side == "right"?0.65:-0.65);
    shoulder.position.y = 0.25;
    //shoulder.scaling.x = 0.25;shoulder.scaling.z = 0.5;shoulder.scaling.y = 0.5;
    shoulder.material = new BABYLON.StandardMaterial("shouldercolor", scene);
    shoulder.material.diffuseColor = new BABYLON.Color3(0.0, 0.8, 0.8);
    
    opts.depth = 0.23;opts.height = 0.4;opts.width = 0.23;
    var elbow = BABYLON.Mesh.CreateBox(side + "elbow", opts, scene);
    elbow.parent = shoulder;
    elbow.position.y = -0.45;
    //elbow.scaling.y = 0.5;
    elbow.material = new BABYLON.StandardMaterial("elbowcolor", scene);
    elbow.material.diffuseColor = new BABYLON.Color3(0.8, 0.8, 0.0);
    
    opts.depth = 0.2;opts.height = 0.3;opts.width = 0.2;
    var wrist = BABYLON.Mesh.CreateBox(side + "wrist", opts, scene);
    wrist.parent = elbow;
    wrist.position.y = -0.35;
    //wrist.scaling.y = 0.75;
    wrist.material = new BABYLON.StandardMaterial("elbowcolor", scene);
    wrist.material.diffuseColor = new BABYLON.Color3(0.0, 0.8, 0.0);
    
}

function createLeg(side, parent) {
    var opts = { width: 0.25, height: 0.5, depth: 0.25 };
    var shoulder = BABYLON.Mesh.CreateBox(side + "hip",opts, scene);
    shoulder.parent = parent;
    shoulder.position.x = (side == "right"?0.35:-0.35);
    shoulder.position.y = -0.4;
    //shoulder.scaling.x = 0.25;shoulder.scaling.z = 0.5;shoulder.scaling.y = 0.5;
    shoulder.material = new BABYLON.StandardMaterial("hipcolor", scene);
    shoulder.material.diffuseColor = new BABYLON.Color3(0.0, 0.8, 0.8);
    
    opts.depth = 0.23;opts.height = 0.4;opts.width = 0.23;
    var elbow = BABYLON.Mesh.CreateBox(side + "knee", opts, scene);
    elbow.parent = shoulder;
    elbow.position.y = -0.45;
    //elbow.scaling.y = 0.5;
    elbow.material = new BABYLON.StandardMaterial("kneecolor", scene);
    elbow.material.diffuseColor = new BABYLON.Color3(0.8, 0.8, 0.0);
    
    opts.depth = 0.2;opts.height = 0.3;opts.width = 0.2;
    var wrist = BABYLON.Mesh.CreateBox(side + "ankle", opts, scene);
    wrist.parent = elbow;
    wrist.position.y = -0.35;
    //wrist.scaling.y = 0.75;
    wrist.material = new BABYLON.StandardMaterial("anklecolor", scene);
    wrist.material.diffuseColor = new BABYLON.Color3(0.0, 0.8, 0.0);
    
}

var rotx = 1, roty = 1, rotz = 1;

function handleScroll(sel) {
    var part = $('#bodypart').find(':selected').val();
    var nde = scene.getNodeByName(part);
    rotx = $('input[name="rotX"]').get(0).valueAsNumber;
    roty = $('input[name="rotY"]').get(0).valueAsNumber;
    rotz = $('input[name="rotZ"]').get(0).valueAsNumber;
    nde.rotation.x = degToRad(rotx);
    nde.rotation.y = degToRad(roty);
    nde.rotation.z = degToRad(rotz);
}

function degToRad(degrees) {
    return degrees * Math.PI / 180;
}

function main() {
    var canvas = document.getElementById("renderCanvas");
    var engine = new BABYLON.Engine(canvas, true);

    engine.runRenderLoop(function () {
        scene.render();
    });
    var scene = new createScene(canvas, engine);
    
    // Resize
    window.addEventListener("resize", function () {
        engine.resize();
    });
}

