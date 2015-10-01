// Hw2
// author Joshua M. Temkin
// Reads an example.dat file and renders it
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.util.Vector;
class Hw3 extends Frame
{
	OGLRenderer renderer;
	Actor    act;

	public static void main(String argv[])
	{
        Vector CellData = new Vector(); //Vector to hold cell data
        //PointSet ptSet = new PointSet(); //Creates a new pointset
        //DataParser dp = new DataParser();
        Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
        StructuredGrid sgrid = new StructuredGrid(argv[0]);
        //read the filename from the command line
        //dp.parseData(argv[0],CellData,actor);




		// Add this  DataSet to the Renderers collection.
		aren.addActor(sgrid);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));


		Hw3 hw2 = new Hw3();
		hw2.renderer = aren;
		hw2.act = actor;
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 15.0F);
		System.out.println("Here we go");
		System.out.println("Joshua M. Temkin");
		//System.out.println("Nothing new added for scene manipulation");
        System.out.println("New keys");
        System.out.println(" c : Toggles displaying contours");
        System.out.println(" w : Toggles displaying the wireframe");
        System.out.println(" p : Toggles displaying the colormapped plane");
        System.out.println(" s : Toggles the Z strecth mode takes a few seconds to regenerate the pointset ");
        System.out.println("     The Z values are taken from the scalar values for each point ");
		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		// Set up the menu
		//MenuBar mb = new MenuBar();
		//Menu file = new Menu( "File" );
		//file.add( new MenuItem( "Exit" ));
		//mb.add( file );
		//hw2.setMenuBar( mb );

		hw2.show();
	}

	/*public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) {
			// Since we didn't save references to each of the menu objects,
			// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Run")) {
				for (int i = 0;i<360;i++) {
					act.rotateX(1);
					renderer.render();
				}
			}
			else if (((String)evt.arg).equals("Exit")) {
			      System.exit(0);
		    }
		}
		System.out.println("Event: " + evt + " Object: " + what);
		return super.action(evt, what);
	}*/
}