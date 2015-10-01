import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.lang.Integer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Hw1 extends Applet implements KeyListener {

    TransformGroup sceneTrans;
    TransformGroup objTrans;
    MouseRotate mouseBeh1;
    MouseZoom mouseBeh2;
    MouseTranslate mouseBeh3;

    public Hw1() {
	setLayout(new BorderLayout());
	Canvas3D c = new Canvas3D(null);
	c.addKeyListener( this );
	add("Center", c);
	
	// Create the gearbox and attach it to the virtual universe
	BranchGroup scene = createScene();
	SimpleUniverse u = new SimpleUniverse(c);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();

	u.addBranchGraph(scene);
    }

    private void addMouseControl(TransformGroup sceneTransG) {
	// Create the transform group node and initialize it to the
	// identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add it to the
	// root of the subgraph.

	// Create a bounds for the mouse behavior methods
        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

	// Create a new Behavior object that will rotate the object and
	// add it into the scene graph.
	mouseBeh1.setTransformGroup(sceneTransG);
	mouseBeh1.setSchedulingBounds(bounds);

	mouseBeh2.setTransformGroup(sceneTransG);
	mouseBeh2.setSchedulingBounds(bounds);

	mouseBeh3.setTransformGroup(sceneTransG);
	mouseBeh3.setSchedulingBounds(bounds);
    }

    public BranchGroup createScene() {
	Transform3D tempTransform = new Transform3D();

	// Create the root of the branch graph
	BranchGroup branchRoot = createBranchEnvironment();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        sceneTrans = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setScale(0.4);
        sceneTrans.setTransform(t3d);
	sceneTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	sceneTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        branchRoot.addChild(sceneTrans);

	// Create an Appearance.
	Appearance look = new Appearance();
	Color3f objColor = new Color3f(0.5f, 0.5f, 0.6f);
	Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	look.setMaterial(new Material(objColor, black,
				      objColor, white, 100.0f));

	// Add mouse control
	mouseBeh1 = new MouseRotate();
	sceneTrans.addChild(mouseBeh1);
	mouseBeh2 = new MouseZoom();
	sceneTrans.addChild(mouseBeh2);
	mouseBeh3 = new MouseTranslate();
	sceneTrans.addChild(mouseBeh3);
	addMouseControl(sceneTrans);

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objTrans = new TransformGroup();
        Transform3D cubeTrans = new Transform3D();
        objTrans.setTransform(cubeTrans);
        sceneTrans.addChild(objTrans);
	// Create a simple shape leaf node, add it to the scene graph.
	objTrans.addChild(new ColorCube(0.4));

        // Have Java 3D perform optimizations on this scene graph.
	branchRoot.compile();

	return branchRoot;
    }

    BranchGroup createBranchEnvironment(){
        // Create the root of the branch graph
        BranchGroup branchRoot = new BranchGroup();
         
        // Create a bounds for the background and lights
        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        // Set up the background
        Color3f bgColor = new Color3f(0.05f, 0.05f, 0.5f);
        Background bgNode = new Background(bgColor);
        bgNode.setApplicationBounds(bounds);
        branchRoot.addChild(bgNode);

        // Set up the ambient light
        Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        branchRoot.addChild(ambientLightNode);

        // Set up the directional lights
        Color3f light1Color = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f light1Direction  = new Vector3f(4.0f, -7.0f, -12.0f);
        Color3f light2Color = new Color3f(0.3f, 0.3f, 0.4f);
        Vector3f light2Direction  = new Vector3f(-6.0f, -2.0f, -1.0f);

        DirectionalLight light1
            = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        branchRoot.addChild(light1);

        DirectionalLight light2
            = new DirectionalLight(light2Color, light2Direction);
        light2.setInfluencingBounds(bounds);
        branchRoot.addChild(light2);

        return branchRoot;
    }

    // The following allows GearBox to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
	    new MainFrame(new Hw1(), 640, 480);
    }

        // Handle the keystrokes
    public void keyTyped(KeyEvent e) {
	
		switch (e.getKeyChar()) {
		case '0':
		    addMouseControl(sceneTrans);
		    e.consume();
		    break;
		case '1':
		    addMouseControl(objTrans);
		    e.consume();
		    break;
		case '+':
		    e.consume();
		    break;
		case '-':
		    e.consume();
		    break;
		case 27:           /* Esc will quit */
		    System.exit(0);
		    break;
		default:
		    break;
		}
    }
    public void keyPressed(KeyEvent e) {
	
    }
    public void keyReleased(KeyEvent e) {
	
    }

}