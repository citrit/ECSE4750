//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;

class Hw2 extends Frame
{

	public static void main(String argv[])
	{

		// Topology
		PointCell ptCell;
		LineCell lineCell;
		PolylineCell polyLCell;
		PolygonCell  polyGCell;
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
		float rgb[] = new float[3];
		double bbox[] = null;

		// Open up a reader
		objectReader obr = new objectReader(argv[0]);

		ptSet = new PointSet(); // Create a current PointSet
		do {
			type = obr.getInt(); // get the type
			cnt = obr.getInt();  // get the number of things comming
			for (i=0;i<3;i++) 
				rgb[i] = obr.getFloat(); // read the rgb
			switch (type) {
			case 0: // read in coordinates
				ptSet = new PointSet(); // Create a current PointSet
				for (i=0;i<cnt;i++) { // for all points
					for (j=0;j<3;j++) // read in three vals
						vals[j] = obr.getFloat();
					ptSet.addPoint(new PointType(vals)); // add a point to the set
				}
				if (bbox == null)
				    bbox = ptSet.getBBox();
				break; // Simply ignore the rgb vals
			case 1: // Create a PointCell
				ptCell = new PointCell(); // Create a PointCell
				for (i=0;i<cnt;i++) { // for all points
					ptCell.addVal(obr.getInt());
				}
				matSet = new MaterialSet();
				matSet.addMaterial(new Material(rgb[0],rgb[1],rgb[2],0));
				ptCell.setMaterials(matSet);
				ptCell.setPoints(ptSet);
				actor.addCell(ptCell);
				break;
			case 2: // Create a LineCell
				lineCell = new LineCell();
				for (i=0;i<cnt;i++) { // for all points
					lineCell.addVal(obr.getInt()); // grab two points 
					lineCell.addVal(obr.getInt()); // for each line
				}
				matSet = new MaterialSet();
				matSet.addMaterial(new Material(rgb[0],rgb[1],rgb[2],0));
				lineCell.setMaterials(matSet);
				lineCell.setPoints(ptSet);
				actor.addCell(lineCell);
				break;
			case 3: // Create a PolygonCell
				matSet = new MaterialSet();
				matSet.addMaterial(new Material(rgb[0],rgb[1],rgb[2],0));
				for (i=0;i<cnt;i++) { // for all polygons
					polyGCell = new PolygonCell(); // create a polygoncell
					while ((j = obr.getInt()) != -1) // get subsequent polygons
						polyGCell.addVal(j); // grab vertices
					polyGCell.setMaterials(matSet); // set for current polygon
					polyGCell.setPoints(ptSet);
					actor.addCell(polyGCell); // add current polygon
				}
				break;
			case 4: // Create a TriangleCell
				triCell = new TriangleCell();
				for (i=0;i<cnt;i++) { // for all triangle
					triCell.addVal(obr.getInt()); // grab three points 
					triCell.addVal(obr.getInt()); // for each triangle
					triCell.addVal(obr.getInt()); 
				}
				matSet = new MaterialSet();
				matSet.addMaterial(new Material(rgb[0],rgb[1],rgb[2],0));
				triCell.setMaterials(matSet);
				triCell.setPoints(ptSet);
				actor.addCell(triCell);
				break;
			case 5: // Create a PolylineCell
				matSet = new MaterialSet();
				matSet.addMaterial(new Material(rgb[0],rgb[1],rgb[2],0));
				for (i=0;i<cnt;i++) { // for all polylines
					polyLCell = new PolylineCell(); // create a polylinecell
					while ((j = obr.getInt()) != -1) // get subsequent polylines
						polyLCell.addVal(j); // grab vertices
					polyLCell.setMaterials(matSet); // set for current polygon
					polyLCell.setPoints(ptSet);
					actor.addCell(polyLCell); // add current polygon
				}
				break;
			}
		

		} while (! obr.eof());

		double center[] = new double[3];
		center[0] = (bbox[0] + bbox[3]) * 0.5;
		center[1] = (bbox[1] + bbox[4]) * 0.5;
		center[2] = (bbox[2] + bbox[5]) * 0.5;
		actor.translate(-center[0], -center[1], -center[2]);
		System.out.println("BBox: "+bbox[0]+","+bbox[1]+","+bbox[2]+","+bbox[3]+","+bbox[4]+","+bbox[5]);
		System.out.println("Cntr: "+center[0]+","+center[1]+","+center[2]);
		// Add this  Actor to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));


		Hw2 hw2 = new Hw2();
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		float camfrom = ((bbox[5]-bbox[2]!=0) ? ((float)(bbox[5]-bbox[2]))*4.0F : 10);
		aren.getCamera().setFrom(0.0F, 0.0F,camfrom );
		System.out.println("Here we go "+camfrom);

		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		hw2.show();
	}

}
