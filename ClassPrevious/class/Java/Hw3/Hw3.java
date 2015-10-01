//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class Hw3 extends Frame implements ActionListener
{
	// Structured Grid object
	StructuredGrid structG;
	GL4JRenderer aren;
	
	public Hw3(String title)
	{
		super(title);
		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Wireframe" ));
		file.add( new MenuItem( "Solid" ));
		file.add( new MenuItem( "Contour" ));
		file.add( new MenuItem( "-" ));
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		setMenuBar( mb );
		file.addActionListener(this);
	}

	public static void main(String argv[])
	{

		// Create an instance of the class
		Hw3 hw3 = new Hw3("vtkHw3");

		// Scalar object
		ScalarFloat scdata;

		// Collection objects
		hw3.aren = new GL4JRenderer();

		// Some helper structures
		int type, cnt, i, j;
		float val, range[];
		int dimx, dimy;
		Vector isovals = new Vector();

		// create a structured grid and scalar data
		hw3.structG = new StructuredGrid();
		scdata = new ScalarFloat();
		PointType pnt = new PointType();

		// Open up a reader
		objectReader obr = new objectReader(argv[0]);

		dimx = obr.getInt();
		dimy = obr.getInt();
		System.out.println("dimx: " + dimx + " dimy: " + dimy);
		hw3.structG.SetSize(dimx, dimy);
		pnt.x = dimx /-2; pnt.y = dimy/-2;
		hw3.structG.SetStartPoint(pnt);
		for (i=0;i<dimx*dimy;i++) {
			val = obr.getFloat();
			scdata.addScalar(val);
		}
		hw3.structG.SetData(scdata);
		hw3.structG.DisplayContours();
		range = scdata.getScalarRange();
		for (val = range[0];val < range[1];val+=0.5F)
			isovals.addElement(new Float(val));
		System.out.println("Generating contours: " + isovals);
		hw3.structG.SetContours(isovals);

		// Add this  Actor to the Renderers collection.
		hw3.aren.addActor(hw3.structG);
		hw3.aren.addCamera(new GL4JCamera());
		hw3.aren.addLight(new GL4JLight(0));

		// Make it visible and set size
		hw3.aren.setVisible(true);
		hw3.aren.setSize(300, 300);
		hw3.aren.getCamera().setFrom(0.0F, 0.0F, 50.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		hw3.add("Center", hw3.aren);
		hw3.pack();

		hw3.show();
	}

	/** Let capture menu events (and all others...) */
	public void actionPerformed(ActionEvent evt) 
	{
		// Since we didn't save references to each of the menu objects,
		// we check which one was pressed by comparing labels.
		if (evt.getActionCommand().equals("Wireframe")) {
			structG.DisplayWireframe();
			aren.render(true);
		}
		if (evt.getActionCommand().equals("Solid")) {
			structG.DisplayPlane();
			aren.render(true);
		}
		if (evt.getActionCommand().equals("Contour")) {
			structG.DisplayContours();
			aren.render(true);
		}
		if (evt.getActionCommand().equals("Exit")) {
			System.exit(1);
		}
		System.out.println("Event: " + evt.getActionCommand());
	}
}
