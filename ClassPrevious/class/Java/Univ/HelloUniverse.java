
import java.applet.*;
import	peony.vecmath.*;
import	peony.media.j3d.*;
import	peony.geometry.*;


public class HelloUniverse extends Applet {
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        // Create the transform group node and initialize it to the
        // identity.  Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime.  Add it to the
        // root of the subgraph.
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(
                            TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objTrans);
        // Create a simple shape leaf node, add it to the scene graph.
        objTrans.addChild(new ColorCube().getShape());

        // Create a new Behavior object that will perform the desired
        // operation on the specified transform object and add it into
        // the scene graph.
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(
                -1, Alpha.INCREASING_ENABLE,
                0, 0,            4000, 0, 0,                        0, 0, 0);
        RotationInterpolator rotator = new RotationInterpolator(
                rotationAlpha, objTrans, yAxis,
                0.0f, (float) Math.PI*2.0f);
        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        rotator.setSchedulingBounds(bounds);
        objTrans.addChild(rotator);

        return objRoot;
    }

    public HelloUniverse() {
        setLayout(new BorderLayout());
        Canvas3D c = new Canvas3D(graphicsConfig);
        add("Center", c);
        // Create a simple scene and attach it to the virtual universe
        BranchGroup scene = createSceneGraph();
        UniverseBuilder u = new UniverseBuilder(c);
        u.addBranchGraph(scene);
    }
}
public class UniverseBuilder extends Object {
    // User-specified canvas
    Canvas3D canvas;

    // Scene graph elements that the user may want access to
    VirtualUniverse                        universe;
    Locale                        locale;
    TransformGroup                        vpTrans;
    View                        view;

    public UniverseBuilder(Canvas3D c) {
        this.canvas = c;

        // Establish a virtual universe, with a single hi-res Locale
        universe = new VirtualUniverse();
        locale = new Locale(universe);

        // Create a PhysicalBody and Physical Environment object
        PhysicalBody body = new PhysicalBody();
        PhysicalEnvironment environment =
                                            new PhysicalEnvironment();

        // Create a View and attach the Canvas3D and the physical
        // body and environment to the view.
        view = new View();
        view.addCanvas3D(c);
        view.setPhysicalBody(body);
        view.setPhysicalEnvironment(environment);

        // Create a branch group node for the view platform
        BranchGroup vpRoot = new BranchGroup();

        // Create a ViewPlatform object, and its associated
        // TransformGroup object, and attach it to the root of the
        // subgraph.  Attach the view to the view platform.
        Transform3D t = new Transform3D();
        t.set(new Vector3f(0.0f, 0.0f, 2.0f));
        ViewPlatform vp = new ViewPlatform();
        TransformGroup vpTrans = new TransformGroup(t);

        vpTrans.addChild(vp);
        vpRoot.addChild(vpTrans);

        view.attachViewPlatform(vp);

        // Attach the branch graph to the universe, via the Locale.
        // The scene graph is now live!
        locale.addBranchGraph(vpRoot);
    }

    public void addBranchGraph(BranchGroup bg) {
        locale.addBranchGraph(bg);
    }
}

public class ColorCube extends Object {
    private static final float[] verts = {
    // front face
         1.0f, -1.0f,  1.0f,                             1.0f,  1.0f,  1.0f,
        -1.0f,  1.0f,  1.0f,                            -1.0f, -1.0f,  1.0f,
    // back face
        -1.0f, -1.0f, -1.0f,                            -1.0f,  1.0f, -1.0f,
         1.0f,  1.0f, -1.0f,                             1.0f, -1.0f, -1.0f,
    // right face
         1.0f, -1.0f, -1.0f,                             1.0f,  1.0f, -1.0f,
         1.0f,  1.0f,  1.0f,                             1.0f, -1.0f,  1.0f,
    // left face
        -1.0f, -1.0f,  1.0f,                            -1.0f,  1.0f,  1.0f,
        -1.0f,  1.0f, -1.0f,                            -1.0f, -1.0f, -1.0f,
    // top face
         1.0f,  1.0f,  1.0f,                             1.0f,  1.0f, -1.0f,
        -1.0f,  1.0f, -1.0f,                            -1.0f,  1.0f,  1.0f,
    // bottom face
        -1.0f, -1.0f,  1.0f,                            -1.0f, -1.0f, -1.0f,
         1.0f, -1.0f, -1.0f,                             1.0f, -1.0f,  1.0f,
    };
    private static final float[] colors = {
    // front face (red)
        1.0f, 0.0f, 0.0f,                            1.0f, 0.0f, 0.0f,
        1.0f, 0.0f, 0.0f,                            1.0f, 0.0f, 0.0f,
    // back face (green)
        0.0f, 1.0f, 0.0f,                            0.0f, 1.0f, 0.0f,
        0.0f, 1.0f, 0.0f,                            0.0f, 1.0f, 0.0f,
    // right face (blue)
        0.0f, 0.0f, 1.0f,                            0.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f,                            0.0f, 0.0f, 1.0f,
    // left face (yellow)
        1.0f, 1.0f, 0.0f,                            1.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f,                            1.0f, 1.0f, 0.0f,
    // top face (magenta)
        1.0f, 0.0f, 1.0f,                            1.0f, 0.0f, 1.0f,
        1.0f, 0.0f, 1.0f,                            1.0f, 0.0f, 1.0f,
    // bottom face (cyan)
        0.0f, 1.0f, 1.0f,                            0.0f, 1.0f, 1.0f,
        0.0f, 1.0f, 1.0f,                            0.0f, 1.0f, 1.0f,
    };

    private Shape3D shape;

    public ColorCube() {
        QuadArray cube = new QuadArray(24,
                        QuadArray.COORDINATES | QuadArray.COLOR_3);

        cube.setCoordinates(0, verts);
        cube.setColors(0, colors);

        shape = new Shape3D(cube, new Appearance());
    }

    public Shape3D getShape() {
        return shape;
    }
}

