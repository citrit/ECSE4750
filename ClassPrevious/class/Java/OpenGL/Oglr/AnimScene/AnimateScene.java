//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    March 2001
//
//////////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.awt.event.*;

class myKey implements KeyCallback
{
	Renderer aRen;
	
	public myKey(Renderer aren)
	{
		aRen = aren;
	}
	
	/** Callback so client can handle keystrokes.  */
	public void keyCallback(KeyEvent e)
	{
		switch(e.getKeyChar())
		{
			case '1':
				aRen.startScene();
				break;
			case '2':
				aRen.stopScene();
				break;
		}
	}
}

class RotateXAction extends Action
{
	/** Method to handle whenever the SceneClock ticks.
	 * This will be over-ridden by the derived class for a specific 
	 * action.
	 */
	public void tick(int tickTime)
	{
		int ii;
		for (ii=0;ii<Actors.size();ii++) {
			((Actor)Actors.elementAt(ii)).rotateX(5);
			//System.out.print("Tick: " + tickTime + "\r");
		}
	}
}
class RotateYAction extends Action
{
	/** Method to handle whenever the SceneClock ticks.
	 * This will be over-ridden by the derived class for a specific 
	 * action.
	 */
	public void tick(int tickTime)
	{
		int ii;
		for (ii=0;ii<Actors.size();ii++) {
			((Actor)Actors.elementAt(ii)).rotateY(5);
			//System.out.print("Tick: " + tickTime + "\r");
		}
	}
}

public class AnimateScene extends Frame
{
	public static void main(String argv[])
	{
		Actor act1 = null, act2 = null;
		System.out.println("Argv.length: " + argv.length);
		if (argv.length > 0) {
			act1 = new STLReader(argv[0]);
			if (argv.length == 2) {
				act2 = new STLReader(argv[1]);
				act2.rotateY(90);
				float []sc = new float[3];
				sc[0]=sc[1]=sc[2] = 3;
				act2.scale(sc);
				act2.translate(5,0,0);
			}
		}
		else {
			CubeCell cube = new CubeCell();
			MaterialSet mats = new MaterialSet();
			mats.addMaterial(new Material(1.0F,1.0F,0.0F,1.0F));
			cube.setMaterials(mats);
			act1 = new Actor();
			act1.addCell(cube);
		}
		GL4JRenderer aren = new GL4JRenderer();
		aren.addActor(act1);
		if (act2 != null)
			aren.addActor(act2);
		aren.addCamera(new GL4JCamera());
		aren.addLight(new GL4JLight(0));
		aren.addKeyCallback(new myKey(aren));
		RotateXAction jax = new RotateXAction();
		RotateYAction jay = new RotateYAction();
		jax.addActor(act1);
		jay.addActor(act2);
		aren.addAction(jax);
		aren.addAction(jay);

		AnimateScene scene = new AnimateScene();
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		scene.add("Center", aren);
		scene.pack();
		scene.show();
	}
	
}
