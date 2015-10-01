///////////////////////////////////////////////////////////////
//
//	Name: Shuo Zhao 
//	Homework 3
//      Hw3.java
//
///////////////////////////////////////////////////////////////

import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.util.Vector;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Hw3 extends Frame implements KeyListener
{
	StructuredGrid Grid;
	OGLRenderer aren;
	ScalarData data;
	PointType point;

	public Hw3()
	{
		Grid = new StructuredGrid();
		aren = new OGLRenderer(); 
		data = new ScalarData();
		point = new PointType();
	}

	public static void main(String argv[])
	{

		// Create an instance of the class
		Hw3 hw3 = new Hw3();
		MaterialSet materialColor = new MaterialSet();
		int type, i, j;
		float val, range[];
		int x_range, y_range;

		//Check see if there is a data file provided
		if(argv.length==0)
		{
			x_range = 25;
			y_range = 25;
			hw3.Grid.SetSize(x_range, y_range);
			hw3.point.x = x_range /-2; 
			hw3.point.y = y_range/-2;
			hw3.Grid.SetStartPoint(hw3.point);

			//Create a white color material set

			materialColor.addMaterial(new Material());
			hw3.Grid.SetMaterial(materialColor);
			hw3.Grid.DisplayPlane();
			hw3.Grid.DisplayWireframe();
		}
		// Open up a reader, and read in from a data file
		else
		{
			objectReader obr = new objectReader(argv[0]);


			x_range = obr.getInt();
			y_range = obr.getInt();
		
			hw3.Grid.SetSize(x_range, y_range);
			hw3.point.x = x_range /-2; 
			hw3.point.y = y_range/-2;
			hw3.Grid.SetStartPoint(hw3.point); //Setup starting point coordination
			
			//Read in each float number from the input file
			for (i=0;i<x_range * y_range;i++)
			{
				val = obr.getFloat();
				hw3.data.addScalar(val);
			}

			/* Add color component to the material set */	
			hw3.data.setColorRange(hw3.data.getScalarRange());
			for(i=0; i<x_range * y_range; i++)
				materialColor.addMaterial(hw3.data.colorValue(hw3.data.getScalar(i)));
			
			
			hw3.Grid.SetMaterial(materialColor);
			hw3.Grid.SetData(hw3.data);
			hw3.Grid.DisplayPlane();
			hw3.Grid.DisplayWireframe();
			range = hw3.data.getScalarRange();
			hw3.Grid.SetContours(range);
			hw3.Grid.DisplayContours();
			hw3.Grid.DisplayWireframe();
		}
		// Add this  Actor to the Renderers collection.
		hw3.aren.addActor(hw3.Grid);
		hw3.aren.addCamera(new OGLCamera());
		hw3.aren.addLight(new OGLLight(0));

		// Make it visible and set size
		hw3.aren.setVisible(true);
		hw3.aren.setSize(400, 400);
		hw3.aren.getCamera().setFrom(0.0F, 0.0F, 50.0F);
		hw3.aren.addKeyListener(hw3);
		System.out.println("Here we go");
		System.out.println("\t Usage.....");
		System.out.println("\t\t 'w' -> to display wireframe");
		System.out.println("\t\t 's' -> to display solid");
		System.out.println("\t\t 'c' -> to display contour");
		System.out.println("\t\t And same keys to rotate the object");	
		
		// Add the canvas to the frame and make it show
  		hw3.add("Center", hw3.aren);
		hw3.pack();

		hw3.show();
	}

	// Keystroke events to draw solid/wireframe/contours
	public  void keyTyped(KeyEvent e)
	{
		switch (e.getKeyChar())
		{
			case 'w':
				Grid.DisplayWireframe();
				aren.render();
				break;
			case 's': 
				Grid.DisplayPlane();
				aren.render();
				break;
			case 'c':
				Grid.DisplayContours();
				aren.render();
				break;
			default:
				break;
		}
		
	}

	/** Handle the keystrokes */
	public void keyPressed(KeyEvent e) {

	}
	/** Handle the keystrokes */
	public void keyReleased(KeyEvent e) {

	}


}


