// from require to global var
var OSG = window.OSG;
OSG.globalify();

var osg = window.osg;
var osgViewer = window.osgViewer;
var osgGA = window.osgGA;
var viewer;
var root;

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
        viewer.getManipulator().setDistance( 15.0 );

        viewer.run();

        var mousedown = function ( ev ) {
            ev.stopPropagation();
        };
    
        
        $('button[name="animButton"]').on('click', function() {
            animObj.animate(animPath, true); 
        });
        $('button[name="stopAnimButton"]').on('click', function() {
            animObj.animate(animPath, false); 
        });
    $('')
    //} catch ( er ) {
    //    osg.log( 'exception in osgViewer ' + er );
    //    alert( 'exception in osgViewer ' + er );
    //}
};

// create a cube in center of the scene(0, 0, 0) and set it's size to 1
var size = 1;


function createScene() {
    
    root = new osg.Node();
    root.setName('world');
    
    var promise = osgDB.parseSceneGraph( getPokerScene() );
    root.addChild(promise);
    
    return root;
}

var rotx = 1, roty = 1, rotz = 1;
function handleScroll(slider) {
    var part = $('#bodypart').find(':selected').val();
    rotx = $('input[name="rotX"]').get(0).valueAsNumber - rotx;
    roty = $('input[name="rotY"]').get(0).valueAsNumber - roty;
    rotz = $('input[name="rotZ"]').get(0).valueAsNumber - rotz;
    
    // Find the node
    var finder = new FindByNameVisitor( part );
    root.accept( finder );

    if (finder.found !== undefined) {
        elem = finder.found;
        var accumMat = elem.getMatrix();
        switch (slider.name) {
            case 'rotX':
                osg.Matrix.preMult(accumMat, osg.Matrix.makeRotate(degToRad(1), 1, 0, 0, osg.Matrix.create() ));
                break;
            case 'rotY':
                osg.Matrix.preMult(accumMat, osg.Matrix.makeRotate(degToRad(1), 0, 1, 0, osg.Matrix.create() ));
                break;
            case 'rotZ':
                osg.Matrix.preMult(accumMat, osg.Matrix.makeRotate(degToRad(1), 0, 0, 1, osg.Matrix.create() ));
                break;
        }
        elem.setMatrix(accumMat);
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
