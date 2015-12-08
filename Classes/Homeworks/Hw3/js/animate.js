var animPath = [
    {leftknee: [[-3,0,0],[-3,0,0],[-3,0,0],[-3,0,0],[3,0,0],[3,0,0],[3,0,0],[3,0,0]]},
    {rightknee: [[3,0,0],[3,0,0],[3,0,0],[3,0,0],[-3,0,0],[-3,0,0],[-3,0,0],[-3,0,0]]},
    {rightelbow: [[3,0,0],[3,0,0],[3,0,0],[3,0,0],[-3,0,0],[-3,0,0],[-3,0,0],[-3,0,0]]}
    
];


var animObj = {
    
    thingy: null,

    animate: function(val, doit) {
        $.each( val, function( i, val ) {
            console.log(val);
            $.each(val, function(key, val) {
                console.log( "The key is '" + key + "'"); 
                
                // Find the node
                var finder = new FindByNameVisitor( key );
                root.accept( finder );

                if (finder.found !== undefined) {
                    if (doit) {
                        var cb = new animUpdateCallback(val);;
                        finder.found.addUpdateCallback(cb);
                    }
                    else {
                        var cbs = finder.found.getUpdateCallbackList();
                        $.each(cbs, function(key, val) {
                            finder.found.removeUpdateCallback(val);
                        });
                            
                    }
                }
            });
        });
    },
}

var animUpdateCallback = function(trs) {
    var trans = trs;
    var cnt = 0;

    this.update = function(node, nv) {
        var accumMat = node.getMatrix();
        var v = trans[cnt];
        cnt = (cnt + 1) % trans.length;
        osg.Matrix.preMult(accumMat, osg.Matrix.makeRotate(degToRad(v[0]), 1, 0, 0, osg.Matrix.create() ));
        osg.Matrix.preMult(accumMat, osg.Matrix.makeRotate(degToRad(v[1]), 0, 1, 0, osg.Matrix.create() ));
        osg.Matrix.preMult(accumMat, osg.Matrix.makeRotate(degToRad(v[2]), 0, 0, 1, osg.Matrix.create() ));
        node.setMatrix(accumMat);
    }
}

