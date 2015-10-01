/*
 * @(#)AnimTexture.java 1.0 01/02/25
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_1\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */
 
import java.awt.*;
import java.awt.event.*;
import gl4java.GLFunc;
import gl4java.GLUFunc;
import gl4java.utils.glut.*;

class AnimateTexture extends Frame
{
	public AnimateTexture()
	{
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.exit(0);
			}
		});
	}
	
	public static void main(String args[])
	{
		System.out.println("Starting AnimateTexture...");
		AnimateTexture mainFrame = new AnimateTexture();
		mainFrame.setSize(400, 400);
		mainFrame.setTitle("AnimateTexture");
		mainFrame.setVisible(true);

		GL4JRenderer aren = new GL4JRenderer();
		Actor act = new Actor();
		Material mat = new Material(0.8F,0,0,1);
		MaterialSet matSet = new MaterialSet();
		matSet.addMaterial(mat);
		CubeCell cube = new CubeCell();
		cube.setMaterials(matSet);
		Texture tex = new Texture("Contour.jpg", aren);
		cube.setTexture(tex);
		act.addCell(cube);
		aren.addActor(act);
	
		aren.addCamera(new GL4JCamera());
		aren.addLight(new GL4JLight(0));
		
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(256, 256);
		aren.getCamera().setFrom(0.0F, 0.0F, 10.0F);

  		mainFrame.add("Center", aren);
  		mainFrame.pack();
	}
}

