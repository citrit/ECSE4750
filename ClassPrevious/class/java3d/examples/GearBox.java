/*
 *	@(#)GearBox.java 1.13 98/04/13 13:03:10
 *
 * Copyright (c) 1996-1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.lang.Integer;

public class GearBox extends Applet {

    static final int defaultToothCount = 48;
    
    public BranchGroup createGearBox(int toothCount) {
	Transform3D tempTransform = new Transform3D();

	// Create the root of the branch graph
	BranchGroup branchRoot = createBranchEnvironment();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setScale(0.4);
        objScale.setTransform(t3d);
        branchRoot.addChild(objScale);

	// Create an Appearance.
	Appearance look = new Appearance();
	Color3f objColor = new Color3f(0.5f, 0.5f, 0.6f);
	Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	look.setMaterial(new Material(objColor, black,
				      objColor, white, 100.0f));


	// Create the transform group node and initialize it to the
	// identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add it to the
	// root of the subgraph.
	TransformGroup gearboxTrans = new TransformGroup();
	gearboxTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	gearboxTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	objScale.addChild(gearboxTrans);

	// Create a bounds for the mouse behavior methods
        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

	// Create a new Behavior object that will rotate the object and
	// add it into the scene graph.
	MouseRotate mouseBeh1 = new MouseRotate(gearboxTrans);
	gearboxTrans.addChild(mouseBeh1);
	mouseBeh1.setSchedulingBounds(bounds);

	MouseZoom mouseBeh2 = new MouseZoom(gearboxTrans);
	gearboxTrans.addChild(mouseBeh2);
	mouseBeh2.setSchedulingBounds(bounds);

	MouseTranslate mouseBeh3 = new MouseTranslate(gearboxTrans);
	gearboxTrans.addChild(mouseBeh3);
	mouseBeh3.setSchedulingBounds(bounds);

	// Define the shaft base information
	int shaftCount = 4;
	int secondsPerRevolution = 8000;

	// Create the Shaft(s)
	Shaft shafts[] = new Shaft[shaftCount];
	TransformGroup shaftTGs[] = new TransformGroup[shaftCount];
	Alpha shaftAlphas[] = new Alpha[shaftCount];
	RotationInterpolator shaftRotors[]
	    = new RotationInterpolator[shaftCount];
	Transform3D shaftAxis[] = new Transform3D[shaftCount];

	// Note: the following arrays we're incorporated to make changing
	// the gearbox easier.
	float shaftRatios[] = new float[shaftCount];
	shaftRatios[0] = 1.0f;
	shaftRatios[1] = 0.5f;
	shaftRatios[2] = 0.75f;
	shaftRatios[3] = 5.0f;

	float shaftRadius[] = new float[shaftCount];
	shaftRadius[0] = 0.2f;
	shaftRadius[1] = 0.2f;
	shaftRadius[2] = 0.2f;
	shaftRadius[3] = 0.2f;

	float shaftLength[] = new float[shaftCount];
	shaftLength[0] = 1.8f;
	shaftLength[1] = 0.8f;
	shaftLength[2] = 0.8f;
	shaftLength[3] = 0.8f;

	float shaftDirection[] = new float[shaftCount];
	shaftDirection[0] = 1.0f;
	shaftDirection[1] = -1.0f;
	shaftDirection[2] = 1.0f;
	shaftDirection[3] = -1.0f;

	Vector3d shaftPlacement[] = new Vector3d[shaftCount];
	shaftPlacement[0] = new Vector3d(-0.75, -0.9, 0.0);
	shaftPlacement[1] = new Vector3d(0.75, -0.9, 0.0);
	shaftPlacement[2] = new Vector3d(0.75, 0.35, 0.0);
	shaftPlacement[3] = new Vector3d(-0.75, 0.60, -0.7);

	// Create the shafts.
	for(int i = 0; i < shaftCount; i++) {
	    shafts[i] = new Shaft(shaftRadius[i], shaftLength[i], 25, look);
	}

	// Create a transform group node for placing each shaft
	for(int i = 0; i < shaftCount; i++) {
	    shaftTGs[i] = new TransformGroup();
	    gearboxTrans.addChild(shaftTGs[i]);
	    shaftTGs[i].getTransform(tempTransform);
	    tempTransform.setTranslation(shaftPlacement[i]);
	    shaftTGs[i].setTransform(tempTransform);
	    shaftTGs[i].addChild(shafts[i]);
	}

	// Add rotation interpolators to rotate the shaft in the appropriate
	// direction and at the appropriate rate
	for(int i = 0; i < shaftCount; i++) {
	    shaftAlphas[i] = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
				      (long)(secondsPerRevolution
					     * shaftRatios[i]),
				      0, 0,
				      0, 0, 0);
	    shaftAxis[i] = new Transform3D();
	    shaftAxis[i].rotX(Math.PI/2.0);
	    shaftRotors[i]
		= new RotationInterpolator(shaftAlphas[i], shafts[i],
					   shaftAxis[i],
					   0.0f,
					   shaftDirection[i] * 
					   (float) Math.PI * 2.0f);
	    shaftRotors[i].setSchedulingBounds(bounds);
	    shaftTGs[i].addChild(shaftRotors[i]);
	}

	// Define the gear base information.  Again, these arrays exist to
	// make the process of changing the GearBox via an editor faster
	int gearCount = 5;
	float valleyToCircularPitchRatio = .15f;
	float pitchCircleRadius = 1.0f;
	float addendum = 0.05f;
	float dedendum = 0.05f;
	float gearThickness = 0.3f;
	float toothTipThickness = 0.27f;

	// Create an array of gears and their associated information
	SpurGear gears[] = new SpurGear[gearCount];
	TransformGroup gearTGs[] = new TransformGroup[gearCount];

	int gearShaft[] = new int[gearCount];
	gearShaft[0] = 0;
	gearShaft[1] = 1;
	gearShaft[2] = 2;
	gearShaft[3] = 0;
	gearShaft[4] = 3;

	float ratio[] = new float[gearCount];
	ratio[0] = 1.0f;
	ratio[1] = 0.5f;
	ratio[2] = 0.75f;
	ratio[3] = 0.25f;
	ratio[4] = 1.25f;

	Vector3d placement[] = new Vector3d[gearCount];
	placement[0] = new Vector3d(0.0, 0.0, 0.0);
	placement[1] = new Vector3d(0.0, 0.0, 0.0);
	placement[2] = new Vector3d(0.0, 0.0, 0.0);
	placement[3] = new Vector3d(0.0, 0.0, -0.7);
	placement[4] = new Vector3d(0.0, 0.0, 0.0);

	// Create the gears.
	for(int i = 0; i < gearCount; i++) {
	    gears[i]
		= new SpurGearThinBody(((int)((float)toothCount * ratio[i])),
				       pitchCircleRadius * ratio[i],
				       shaftRadius[0],
				       addendum, dedendum,
				       gearThickness,
				       toothTipThickness,
				       valleyToCircularPitchRatio, look);
	}

	// Create a transform group node for arranging the gears on a shaft
	// and attach the gear to its associated shaft
	for(int i = 0; i < gearCount; i++) {
	    gearTGs[i] = new TransformGroup();
	    gearTGs[i].getTransform(tempTransform);
	    tempTransform.rotZ((shaftDirection[gearShaft[i]] == -1.0) ?
			       gears[i].getCircularPitchAngle()/-2.0f :
			       0.0f);
	    tempTransform.setTranslation(placement[i]);
	    gearTGs[i].setTransform(tempTransform);
	    gearTGs[i].addChild(gears[i]);
	    shafts[gearShaft[i]].addChild(gearTGs[i]);
	}

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

    public GearBox() {
	this(defaultToothCount);
    }

    public GearBox(int toothCount) {
	setLayout(new BorderLayout());
	Canvas3D c = new Canvas3D(null);
	add("Center", c);
	
	// Create the gearbox and attach it to the virtual universe
	BranchGroup scene = createGearBox(toothCount);
	SimpleUniverse u = new SimpleUniverse(c);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();

	u.addBranchGraph(scene);
    }

    //
    // The following allows GearBox to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
	int value;
	
	if (args.length > 1) {
	    System.out.println("Usage: java GearBox  #teeth (LCD 4)");
	    System.exit(0);
	} else if (args.length == 0) {	
	    new MainFrame(new GearBox(), 640, 480);
	} else
	    {
		try{
		    value = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
		    System.out.println("Illegal integer specified");
		    System.out.println("Usage: java GearBox  #teeth (LCD 4)");
		    value = 0;
		    System.exit(0);
		}
		if (value <= 0 | (value % 4) != 0) {
		    System.out.println("Integer not a positive multiple of 4");
		    System.out.println("Usage: java GearBox  #teeth (LCD 4)");
		    System.exit(0);
		}
		new MainFrame(new GearBox(value), 640, 480);
	    }
    }
}