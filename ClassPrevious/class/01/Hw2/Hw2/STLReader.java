//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;

class STLReader extends Frame
{

	public static void main(String argv[])
	{

		// Topology
		PointCell ptCell;
		LineCell lineCell;
		PolylineCell polyLCell;
		TriangleCell triCell;

		// Geometry
		PointSet ptSet;

		// Color
		MaterialSet matSet;

		// Collection objects
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();

		// Some helper structures
		int type, cnt, i, j;
		float vals[] = new float[3];
		float rgba[] = new float[4];


		// Open up a reader
		objectReader obr = new objectReader(argv[0]);
		ptSet = new PointSet(); // Create a current PointSet
		matSet = new MaterialSet();
		rgba[0] = 0.8F;rgba[1] = 0.8F;rgba[2] = 0.0F;rgba[3] = 1.0F;
		matSet.addMaterial(new Material(rgba));
		String inStr;
		String vrtxStr = new String("VERTEX");
		String endlStr = new String("ENDLOOP");
		String normStr = new String("NORMAL");

		triCell = new TriangleCell();
		do {
		    inStr = obr.getString();
		    if (inStr.compareTo(vrtxStr) == 0) {
				for (j=0;j<3;j++) // read in three vals
					vals[j] = obr.getFloat();
				ptSet.addPoint(new PointType(vals)); // add a point to the set
		    }
		    else if (inStr.compareTo(endlStr) == 0) {
				triCell.addVal(ptSet.size()-3); // grab three points 
				triCell.addVal(ptSet.size()-2); // for each triangle
				triCell.addVal(ptSet.size()-1); 
		    }
		    else if (inStr.compareTo(normStr) == 0) {
				triCell = new TriangleCell();
				triCell.setMaterials(matSet);
				triCell.setPoints(ptSet);
				actor.addCell(triCell);
				for (j=0;j<3;j++) // read in three vals
					vals[j] = obr.getFloat();
				triCell.setNormal(new PointType(vals)); // add a normal to the cell
			}
		} while (! obr.eof());

		// Add this  Actor to the Renderers collection.
		aren.addActor(actor);
		// move the object and center it at zero
		double bbox[] = ptSet.getBBox(); //minx,miny,minz,maxx,maxy,maxz
		double center[] = new double[3];
		center[0] = (bbox[0] + bbox[3]) * 0.5;
		center[1] = (bbox[1] + bbox[4]) * 0.5;
		center[2] = (bbox[2] + bbox[5]) * 0.5;
		actor.translate(-center[0], -center[1], -center[2]);

		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));


		STLReader stlr = new STLReader();
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, ((float)(bbox[5]-bbox[2]))*4.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		stlr.add("Center", aren);
  		stlr.addMouseListener(aren);
  		stlr.addMouseMotionListener(aren);
		stlr.pack();

		stlr.show();
	}
}
