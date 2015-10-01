// Trung Ly		3/1/99
//
// This was modified from the original Hw2.java to satisfy the criteria
// for Homework #2 for Advanced Grapics and Visualization
//
//

import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.lang.*;

class Hw2 extends Frame
{
	OGLRenderer renderer;
	Actor    act;

	public static void main(String argv[])
	{
		objectReader or;
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		PointSet myPts = new PointSet();
		PointCell pCell;
		LineCell lCell;
		PolygonCell pgCell;
		TriangleCell tCell;
		PolyLineCell plCell;
		Material mat;
		MaterialSet matSet;
		MaterialSet matSet1 = new MaterialSet();
		MaterialSet matSet2 = new MaterialSet();
		MaterialSet matSet3 = new MaterialSet();
		MaterialSet matSet4 = new MaterialSet();
		MaterialSet matSet5 = new MaterialSet();
		float tmp[] = new float[3];
		float rgba[] = { 1.0F, 0.0F, 0.0F, 1.0F };
		int type, count;

		mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		rgba[0] = rgba[1] = 0.0F;rgba[2] = 1.0F;
		matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
		matSet.addMaterial(new Material(0.0F, 1.0F, 0.0F, 1.0F));

		or = new objectReader(argv[0]);

		// Read in data file
		System.out.println("Reading in data file...");
		while( !or.eof() ) {
			type = or.getInt();		// get Type of Cell
			count = or.getInt();		// get Number of Cells
			rgba[0] = or.getFloat();	// get Red component
			rgba[1] = or.getFloat();	// get Green component
			rgba[2] = or.getFloat();	// get Blue component
			switch(type) {
			case 0:		// it's a Coordinate
				myPts = new PointSet();
				for(int i=0; i<count; i++) {
					tmp[0] = or.getFloat();
					tmp[1] = or.getFloat();
					tmp[2] = or.getFloat();
					myPts.addPoint(new PointType(tmp));
				}
				break;
			case 1:		// it's a Point
				pCell = new PointCell();
				for(int i=0; i<count; i++) {
					matSet1.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
					pCell.addVal(or.getInt());
				}
				pCell.setMaterials(matSet1);
				pCell.setPoints(myPts);
				actor.addCell(pCell);
				break;
			case 2:		// it's a Line
				lCell = new LineCell();
				for(int i=0; i<count; i++) {
					matSet2.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
					matSet2.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
					lCell.addVal(or.getInt());
					lCell.addVal(or.getInt());
				}
				lCell.setMaterials(matSet2);
				lCell.setPoints(myPts);
				actor.addCell(lCell);
				break;
			case 3:		// it's a Polygon
				pgCell = new PolygonCell();
				matSet.addMaterial(new Material(0.0F, 1.0F, 0.0F, 1.0F));
				for(int i=0; i<count; i++) {
					int j=0;
					while(j!=-1) {
						j=or.getInt();
						if(j!=-1) {
							matSet3.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
							pgCell.setMaterials(matSet3);
							pgCell.addVal(j);
						}
					}
				}
				pgCell.setMaterials(matSet3);
				pgCell.setPoints(myPts);
				actor.addCell(pgCell);
				break;
			case 4:		// it's a Triangle
				tCell = new TriangleCell();
				for(int i=0; i<count; i++) {
					for(int j=0; j<3; j++) {
						matSet4.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
						tCell.addVal(or.getInt());
					}
				}
				tCell.setMaterials(matSet4);
				tCell.setPoints(myPts);
				actor.addCell(tCell);
				break;
			case 5:		// it's a PolyLine
				plCell = new PolyLineCell();
				for(int i=0; i<count; i++) {
					int j=0;
					while(j!=-1) {
						j=or.getInt();
						if(j!=-1) {
							matSet5.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
							plCell.addVal(j);
						}
					}
				}
				plCell.setMaterials(matSet5);
				plCell.setPoints(myPts);
				actor.addCell(plCell);
				break;
			default:
				break;
			}
		}

		// Add this DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));

		System.out.println("Done!");

		Hw2 hw2 = new Hw2();
		hw2.renderer = aren;
		hw2.act = actor;
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(2.0F, 2.0F, 10.0F);
		aren.getCamera().setTo(2.0F, 2.0F, 0.0F);

		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Run" ));
		mb.add( file );
		hw2.setMenuBar( mb );

		hw2.show();

	}

	public boolean Action(Event evt, Object what)
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
		}
		System.out.println("Event: " + evt + " Object: " + what);
//		return super.action(evt, what);
		return true;
	}
}