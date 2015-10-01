import jogl.*;
import java.awt.*;
import java.awt.event.*;

public class ThreeDTest extends JoglCanvas implements KeyListener, ItemListener
{
  JoglCanvas gl;
  boolean initLess;
  float lightPos[] = {2.0F, 2.0F, 2.0F, 0.5F};
  float lightDir[] = {0.0F, 0.0F, 0.0F, 0.5F};
  float lightAmb[] = {0.7F, 0.7F, 0.7F, 0.5F};
  float lightDiff[] = {0.8F, 0.8F, 0.8F, 0.5F};
  float lightSpec[] = {0.4F, 0.4F, 0.4F, 0.5F};

  float boxAmb[] = {0.7F, 0.0F, 0.0F, 1.0F};
  float boxDiff[] = {0.2F, 0.0F, 0.0F, 1.0F};
  float boxSpec[] = {0.8F, 0.0F, 0.0F, 1.0F};

  float coneAmb[] = {0.0F, 0.0F, 0.7F, 0.2F};
  float coneDiff[] = {0.0F, 0.0F, 0.9F, 0.2F};
  float coneSpec[] = {0.0F, 0.0F, 0.2F, 0.2F};

  float sphereAmb[] = {0.0F, 0.6F, 0.6F, 0.6F};
  float sphereDiff[] = {0.0F, 0.6F, 0.6F, 0.6F};
  float sphereSpec[] = {0.0F, 0.6F, 0.6F, 0.6F};

  ThreeDObject tdo[];
  int state = -1;

  CheckboxMenuItem camera, box, cone, sphere;
  CheckboxMenuItem flat, smooth;
  CheckboxMenuItem lighting, alpha, dither, depth;

  ThreeDTest(Frame f) {
    gl = this;
    initLess = true;

    tdo = new ThreeDObject[3];
    tdo[0] = new Box();
    tdo[1] = new Cone(0.0F, 1.0F, 1.0F, 32, 32);
    tdo[2] = new Sphere(1, 32, 32);

    tdo[0].setAmbientV(boxAmb);
    tdo[0].setDiffuseV(boxDiff);
    tdo[0].setSpecularV(boxSpec);

    tdo[1].setAmbientV(coneAmb);
    tdo[1].setDiffuseV(coneDiff);
    tdo[1].setSpecularV(coneSpec);

    tdo[2].setAmbientV(sphereAmb);
    tdo[2].setDiffuseV(sphereDiff);
    tdo[2].setSpecularV(sphereSpec);
    
    tdo[0].scale(0.5F, 0.5F, 0.5F, ThreeDObject.ABSOLUTE);
    tdo[1].scale(0.5F, 0.5F, 1.0F, ThreeDObject.ABSOLUTE);
    tdo[2].scale(0.5F, 0.5F, 0.5F, ThreeDObject.ABSOLUTE);

    tdo[0].translate(0.0F, 0.0F, 0.0F, ThreeDObject.ABSOLUTE);
    tdo[1].translate(-3.0F, 0.0F, -0.5F, ThreeDObject.ABSOLUTE);
    tdo[2].translate(3.0F, 0.0F, 0.0F, ThreeDObject.ABSOLUTE);

    addKeyListener(this);

    MenuBar mb = new MenuBar();
    f.setMenuBar(mb);

    Menu object = new Menu("Object");
    camera = new CheckboxMenuItem("Camera", true);
    box = new CheckboxMenuItem("Box", false);
    cone = new CheckboxMenuItem("Cone", false);
    sphere = new CheckboxMenuItem("Sphere", false);

    object.add(camera); camera.addItemListener(this);
    object.add(box);    box.addItemListener(this);
    object.add(cone);   cone.addItemListener(this);
    object.add(sphere); sphere.addItemListener(this);

    mb.add(object);

    Menu shading = new Menu("Shading");
    smooth = new CheckboxMenuItem("Smooth", true);
    flat = new CheckboxMenuItem("Flat", false);

    shading.add(smooth); smooth.addItemListener(this);
    shading.add(flat);   flat.addItemListener(this);

    mb.add(shading);

    Menu toggles = new Menu("Toggles");
    lighting = new CheckboxMenuItem("Lighting", true);
    alpha = new CheckboxMenuItem("Alpha Testing", true);
    dither = new CheckboxMenuItem("Dithering", true);
    depth = new CheckboxMenuItem("Depth Testing", true);

    toggles.add(lighting); lighting.addItemListener(this);
    toggles.add(alpha);    alpha.addItemListener(this);
    toggles.add(dither);   dither.addItemListener(this);
    toggles.add(depth);    depth.addItemListener(this);

    mb.add(toggles);

    System.out.println("Use the menu to toggle which object to manipulate, etc.");
    System.out.println("'a': move selected object left");
    System.out.println("'s': move selected object right");
    System.out.println("'z': move selected object down");
    System.out.println("'w': move selected object up");
    System.out.println("'h', 'j', 'k', and 'l' work like in the examples");
    System.out.println("'+' and '-' scale the selected object");
  }

  void init() {
    initLess = false;
    int width = gl.getWidth();
    int height = gl.getHeight();

    gl.viewport(0, 0, width, height);
    gl.matrixMode(GL.PROJECTION);
    gl.loadIdentity();
    gl.ortho(-4.0, 4.0, -4.0, 4.0, -4.0, 4.0);

    gl.matrixMode(GL.MODELVIEW);
    gl.blendFunc(GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA);
    gl.texEnv(GL.TEXTURE_ENV, GL.TEXTURE_ENV_MODE, GL.MODULATE);

    gl.light(GL.LIGHT0, GL.POSITION, lightPos);
    gl.light(GL.LIGHT0, GL.SPOT_DIRECTION, lightDir);
    gl.light(GL.LIGHT0, GL.AMBIENT, lightAmb);
    gl.light(GL.LIGHT0, GL.DIFFUSE, lightDiff);
    gl.light(GL.LIGHT0, GL.SPECULAR, lightSpec);
    gl.enable(GL.DEPTH_TEST);
    gl.shadeModel(GL.SMOOTH);
    gl.lightModel(GL.LIGHT_MODEL_TWO_SIDE, GL.TRUE);
    gl.enable(GL.DITHER);
    gl.enable(GL.ALPHA_TEST);
    gl.enable(GL.LIGHTING);
    gl.enable(GL.LIGHT0);
  }

  public void paint(Graphics g) {
    super.paint(g);
    if (initLess) init();
    display();
  }

  void display() {
    gl.use();
    
    gl.clearColor (0.0F, 0.0F, 0.0F, 0.0F);
    gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);

    tdo[0].draw(gl);
    tdo[1].draw(gl);
    tdo[2].draw(gl);

    gl.swap();
  }

  // Handle the keystrokes
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
    case 'h':
      gl.matrixMode (GL.MODELVIEW);/* manipulate modelview matrix*/
      if (state == -1)
	gl.rotate(15.0, 0.0, 1.0, 0.0);
      else
	tdo[state].rotate(0.0F, 15.0F, 0.0F, ThreeDObject.RELATIVE);
      e.consume();
      break;
    case 'j':
      gl.matrixMode (GL.MODELVIEW);/* manipulate modelview matrix*/
      if (state == -1)
	gl.rotate(15.0, 1.0, 0.0, 0.0);
      else
	tdo[state].rotate(15.0F, 0.0F, 0.0F, ThreeDObject.RELATIVE);
      e.consume();
      break;
    case 'k':
      gl.matrixMode (GL.MODELVIEW); /* manipulate modelview matrix*/
      if (state == -1)
	gl.rotate(-15.0, 1.0, 0.0, 0.0);
      else
	tdo[state].rotate(-15.0F, 0.0F, 0.0F, ThreeDObject.RELATIVE);
      e.consume();
      break;
    case 'l':
      gl.matrixMode (GL.MODELVIEW);   /* manipulate modelview matrix  */
      if (state == -1)
	gl.rotate(-15.0, 0.0, 1.0, 0.0);
      else
	tdo[state].rotate(0.0F, -15.0F, 0.0F, ThreeDObject.RELATIVE);
      e.consume();
      break;
    case '+':
      if (state == -1) {
	gl.matrixMode (GL.PROJECTION);  /* manipulate Projection matrix  */
	gl.translate(0.0, 0.0,0.5);
      } else {
	gl.matrixMode(GL.MODELVIEW);
	tdo[state].scale(0.2F, 0.2F, 0.2F, ThreeDObject.RELATIVE);
      }
      e.consume();
      break;
    case '-':
      if (state == -1) {
	gl.matrixMode (GL.PROJECTION);    /* manipulate Projection matrix  */
	gl.translate(0.0, 0.0,-0.5);
      } else {
	gl.matrixMode(GL.MODELVIEW);
	tdo[state].scale(-0.2F, -0.2F, -0.2F, ThreeDObject.RELATIVE);
      }
      e.consume();
      break;
    case 'w':
      //gl.matrixMode(GL.PROJECTION);
      if (state == -1) {
	gl.matrixMode(GL.PROJECTION);
	gl.translate(0.0, 0.1, 0.0);
      } else {
	gl.matrixMode(GL.MODELVIEW);
	tdo[state].translate(0.0F, 0.1F, 0.0F, ThreeDObject.RELATIVE);
      }
      e.consume();
      break;
    case 'z':
      //gl.matrixMode(GL.PROJECTION);
      if (state == -1) {
	gl.matrixMode(GL.PROJECTION);
	gl.translate(0.0, -0.1, 0.0);
      } else {
	gl.matrixMode(GL.MODELVIEW);
	tdo[state].translate(0.0F, -0.1F, 0.0F, ThreeDObject.RELATIVE);
      }
      e.consume();
      break;
    case 'a':
      //gl.matrixMode(GL.PROJECTION);
      if (state == -1) {
	gl.matrixMode(GL.PROJECTION);
	gl.translate(-0.1, 0.0, 0.0);
      } else {
	gl.matrixMode(GL.MODELVIEW);
	tdo[state].translate(-0.1F, 0.0F, 0.0F, ThreeDObject.RELATIVE);
      }
      e.consume();
      break;
    case 's':
      //gl.matrixMode(GL.PROJECTION);
      if (state == -1) {
	gl.matrixMode(GL.PROJECTION);
	gl.translate(0.1, 0.0, 0.0);
      } else {
	gl.matrixMode(GL.MODELVIEW);
	tdo[state].translate(0.1F, 0.0F, 0.0F, ThreeDObject.RELATIVE);
      }
      e.consume();
      break;
    case 27:           /* Esc will quit */
      System.exit(1);
      break;
    default:
      break;
    }
    display();
  }

  public void keyPressed(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}

  public void itemStateChanged(ItemEvent e) {
    if (e.getItem() == "Camera")
      toggleObject(true, false, false, false);
    if (e.getItem() == "Box")
      toggleObject(false, true, false, false);
    if (e.getItem() == "Cone")
      toggleObject(false, false, true, false);
    if (e.getItem() == "Sphere")
      toggleObject(false, false, false, true);

    if (e.getItem() == "Smooth")
      toggleShading(true, false);
    if (e.getItem() == "Flat")
      toggleShading(false, true);

    if (e.getItem() == "Lighting")
      toggleLighting();
    if (e.getItem() == "Alpha Testing")
      toggleAlpha();
    if (e.getItem() == "Dithering")
      toggleDither();
    if (e.getItem() == "Depth Testing")
      toggleDepth();

    display();
  }

  void toggleObject(boolean cam, boolean b, boolean c, boolean s)
  {
    if (cam) state = -1;
    if (b) state = 0;
    if (c) state = 1;
    if (s) state = 2;

    camera.setState(cam);
    box.setState(b);
    cone.setState(c);
    sphere.setState(s);
  }

  void toggleShading(boolean s, boolean f) {
    if (s) gl.shadeModel(GL.SMOOTH);
    if (f) gl.shadeModel(GL.FLAT);

    smooth.setState(s);
    flat.setState(f);
  }

  void toggleLighting() {
    if (!lighting.getState()) {
      gl.disable(GL.LIGHTING);
      gl.disable(GL.LIGHT0);
      lighting.setState(false);
    } else {
      gl.enable(GL.LIGHTING);
      gl.enable(GL.LIGHT0);
      lighting.setState(true);
    }
  }
      
  void toggleAlpha() {
    if (!alpha.getState()) {
      gl.disable(GL.ALPHA_TEST);
      alpha.setState(false);
    } else {
      gl.enable(GL.ALPHA_TEST);
      alpha.setState(true);
    }
  }

  void toggleDither() {
    if (!dither.getState()) {
      gl.disable(GL.DITHER);
      dither.setState(false);
    } else {
      gl.enable(GL.DITHER);
      dither.setState(true);
    }
  }

  void toggleDepth() {
    if (!depth.getState()) {
      gl.disable(GL.DEPTH_TEST);
      depth.setState(false);
    } else {
      gl.enable(GL.DEPTH_TEST);
      depth.setState(true);
    }
  }
  
  public static void main(String args[]) {
    Frame f = new Frame();
    ThreeDTest tdt = new ThreeDTest(f);

    tdt.setVisible(true);
    f.add(tdt);

    f.pack();
    f.setSize(500, 500);
    f.show();
  }
}
