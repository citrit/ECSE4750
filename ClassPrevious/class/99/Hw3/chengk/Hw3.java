//Kent Cheng
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
	

	public static void main(String argv[])
	{

		Hw3 hw3 = new Hw3();
        	ScalarData scdata;
		hw3.aren = new OGLRenderer();
		int i, dimx, dimy;
		float val, range[];
		Vector isovals = new Vector();
		hw3.Grid = new StructuredGrid();
		scdata = new ScalarData();
		PointType pnt = new PointType();
		
		//Check see if there is a data file provided
		objectReader obr = new objectReader(argv[0]);

		dimx = obr.getInt();  //get the x boundary
		dimy = obr.getInt();  //get the y boundary
	
		hw3.Grid.SetSize(dimx, dimy);  //set the grid size
		hw3.Grid.SetDeltas(1,1);       //set the deltas
		pnt.x = dimx/-2; pnt.y = dimy/-2;  //set the start point in the middle
		hw3.Grid.SetStartPoint(pnt);
		for (i=0;i<dimx*dimy;i++)      
		{
			val = obr.getFloat();  //get all the inputs
			scdata.addScalar(val); //collect the numbers
		}
		hw3.Grid.SetData(scdata);      
		hw3.Grid.DisplayWireframe();
		range = scdata.getRange();
		for (val = range[0];val < range[1];val++)
			isovals.addElement(new Float(val));
		hw3.Grid.SetContours(isovals);
		
		// Add the actor to the Renderers' list
		hw3.aren.addActor(hw3.Grid);
		hw3.aren.addCamera(new OGLCamera());
		hw3.aren.addLight(new OGLLight(0));

		// Make it visible and set size
		hw3.aren.setVisible(true);
		hw3.aren.setSize(300, 300);
		hw3.aren.getCamera().setFrom(0.0F, 0.0F, 50.0F);
		hw3.aren.addKeyListener(hw3);
		System.out.println("Here we go");
		System.out.println("Keys to toggle between wireframe, solid, and contour:");
 		System.out.println("w for wireframe, s for solid, and c for contour.");

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


