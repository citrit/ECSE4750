// from require to global var
var OSG = window.OSG;
OSG.globalify();

var osg = window.osg;
var osgViewer = window.osgViewer;
var osgGA = window.osgGA;
var viewer;
var world, shoulder, elbow, wrist, root;

var main = function () {
    // The 3D canvas.
    var canvas = document.getElementById( '3DView' );
    
    //try {
        viewer = new osgViewer.Viewer( canvas, {
            antialias: true,
            //alpha: true
        } );
        viewer.init();
        mvMatrix = new osg.MatrixTransform();
        mvMatrix.addChild( createScene() );
        viewer.setSceneData( mvMatrix );


        viewer.setupManipulator();
        // set distance
        viewer.getManipulator().setDistance( 5.0 );

        viewer.run();

        var mousedown = function ( ev ) {
            ev.stopPropagation();
        };
    //} catch ( er ) {
    //    osg.log( 'exception in osgViewer ' + er );
    //    alert( 'exception in osgViewer ' + er );
    //}
};

var SimpleUpdateCallback = function ( material ) {
    this.material = material;
};

SimpleUpdateCallback.prototype = {
    // rotation angle
    alpha: 0,

    update: function ( node, nv ) {
        var t = nv.getFrameStamp().getSimulationTime();
        var dt = t - node._lastUpdate;
        if ( dt < 0 ) {
            return true;
        }
        node._lastUpdate = t;
        document.getElementById( 'update' ).innerHTML = node._lastUpdate.toFixed( 2 );
        document.getElementById( 'alpha' ).innerHTML = this.alpha.toFixed( 2 );

        this.alpha += 0.01;
        if ( this.alpha > 1.0 ) this.alpha = 0.0;
        var channel;

        channel = this.material.getDiffuse();
        channel[ 3 ] = this.alpha;

        return true;
    }
};

function createScene() {
    var doOgre = true;
    
    root = new osg.Node();
    // Base world transform
    world = new osg.MatrixTransform();
    world.setName('world');
    root.addChild(world);
    
    if (doOgre) {
        var ogre = osgDB.parseSceneGraph( getOgre() );
        world.addChild(ogre);
    } else {
        // create a cube in center of the scene(0, 0, 0) and set it's size to 7
        var size = 1;
        var cubeModel = osg.createTexturedBoxGeometry( 0, 0, 0, size, size, size );

        shoulder = createSegment(cubeModel, [ 0.8, 0.0, 0.0, 0.8 ]);
        shoulder.setName('shoulder');
        // add to root and return
        world.addChild( shoulder );

        elbow = createSegment(cubeModel, [ 0.0, 0.8, 0.0, 0.8 ]);
        elbow.setName('elbow');
        elbow.setMatrix( osg.Matrix.makeTranslate( 1.0, 0.0, 0.0, osg.Matrix.create() ) );
        shoulder.addChild(elbow);

        wrist = createSegment(cubeModel, [ 0.0, 0.0, 0.8, 0.8 ]);
        wrist.setName('wrist');
        wrist.setMatrix( osg.Matrix.makeTranslate( 1.0, 0.0, 0.0, osg.Matrix.create() ) );
        elbow.addChild(wrist);
    }
    
    return root;
}

function createSegment(geom, color) {
    var seg = new osg.MatrixTransform();
    seg.addChild( geom );

    seg.getOrCreateStateSet().setRenderingHint( 'TRANSPARENT_BIN' );
    seg.getOrCreateStateSet().setAttributeAndModes( new osg.BlendFunc( 'SRC_ALPHA', 'ONE_MINUS_SRC_ALPHA' ) );
    seg.getOrCreateStateSet().setAttributeAndModes( new osg.CullFace( 'DISABLE' ) );

    // add a stateSet of texture to cube
    var material = new osg.Material();
    material.setDiffuse( color );
    material.setAmbient( color );
    material.setSpecular( [ 1.0, 1.0, 0.0, 0.0 ] );
    material.setEmission( [ 0.0, 0.0, 0.0, 0.5 ] );
    seg.getOrCreateStateSet().setAttributeAndMode( material );
    return seg;
}

function handleScroll(slider) {
    var part = slider.name;
    var ori = slider.attributes["orien"].nodeValue;
    var val = slider.value;
    
    // Find the node
    var finder = new FindByNameVisitor( part );
    root.accept( finder );

    if (finder.found !== undefined) {
        elem = finder.found;
        switch (ori) { // (angle, x, y, z, result)
            case 'x':
                osg.Matrix.makeRotate(degToRad(val), 1.0, 0.0, 0.0, elem.getMatrix() );
                break;
            case'y':
                osg.Matrix.makeRotate(degToRad(val), 0.0, 1.0, 0.0, elem.getMatrix() );
                break;
            case 'z':
                osg.Matrix.makeRotate(degToRad(val), 0.0, 0.0, 1.0, elem.getMatrix() );
                break;
        }
    }
    
}

function degToRad(degrees) {
    return degrees * Math.PI / 180;
}

// Here we create a new form of
// Scene Graph Visitor
var FindByNameVisitor = function ( name ) {
    osg.NodeVisitor.call( this, osg.NodeVisitor.TRAVERSE_ALL_CHILDREN );
    this._name = name;
};

FindByNameVisitor.prototype = osg.objectInherit( osg.NodeVisitor.prototype, {
    // in found we'll store our resulting matching node
    init: function () {
        this.found = undefined;
    },
    // the crux of it
    apply: function ( node ) {
        if ( node.getName() == this._name ) {
            this.found = node;
            return;
        }
        this.traverse( node );
    }
} );


window.addEventListener( 'load', main, true );
