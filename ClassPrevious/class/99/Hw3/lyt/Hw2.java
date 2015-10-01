// Trung Ly		3/1/99
//
// This was modified from the original Hw2.java to satisfy the criteria
// for Homework #2 for Advanced Grapics and Visualization
//
//
// Important Note:  the input data file cannont have any extra lines
// at the end of the file.  (i.e. whitespace)
// Otherwise the object reader reads it as an integer value of 0.

import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.lang.*;

class Hw2 extends Frame implements ActionListener
{
	OGLRenderer renderer;
	Actor    act;

	public Hw2 ()
	{
	}

	public static void main(String argv[])
	{
		objectReader or;
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		PointSet myPts = new PointSet();
		MaterialSet matSet= new MaterialSet();
		PointCell pCell;
		LineCell lCell;
		PolygonCell pgCell;
		TriangleCell tCell;
		PolyLineCell plCell;
		MaterialSet matSet1 = new MaterialSet();
		MaterialSet matSet2 = new MaterialSet();
		MaterialSet matSet3 = new MaterialSet();
		MaterialSet matSet4 = new MaterialSet();
		MaterialSet matSet5 = new MaterialSet();
		float tmp[] = new float[3];
		float rgba[] = {0.0F, 0.0F, 1.0F, 1.0F};
		int type, count;

		rgba[0] = rgba[1] = 0.0F; rgba[2] = rgba[3] = 1.0F;
		matSet.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);

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
					matSet1.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);
					pCell.addVal(or.getInt());
				}
				pCell.setMaterials(matSet1);
				pCell.setPoints(myPts);
				actor.addCell(pCell);
				break;
			case 2:		// it's a Line
				lCell = new LineCell();
				for(int i=0; i<count; i++) {
					matSet2.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);
					matSet2.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);
					lCell.addVal(or.getInt());
					lCell.addVal(or.getInt());
				}
				lCell.setMaterials(matSet2);
				lCell.setPoints(myPts);
				actor.addCell(lCell);
				break;
			case 3:		// it's a Polygon
				pgCell = new PolygonCell();
				for(int i=0; i<count; i++) {
					int j=0;
					while(j!=-1) {
						j=or.getInt();
						if(j!=-1) {
							matSet3.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);
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
						matSet4.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);
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
							matSet5.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);
							plCell.addVal(j);
						}
					}
				}
				plCell.setMaterials(matSet5);
				plCell.setPoints(myPts);
				actor.addCell(plCell);
				break;
			default:
				System.out.println("Error in input file");
				System.exit(1);
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
		double box[] = myPts.getBBox();

		// Set the camera view from 3 times the highest coordinate to the origin
		aren.getCamera().setFrom(3*(float)box[3], 3*(float)box[4], 3*(float)box[5]);
		aren.getCamera().setTo(0.0F, 0.0F, 0.0F);

		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "Animation" );
		MenuItem mi = new MenuItem( "Run");
		mi.addActionListener(hw2);
		file.add( mi );
		mb.add( file );
		hw2.setMenuBar( mb );

		hw2.show();

	}

	public void actionPerformed(ActionEvent e)
	{
		for (int i = 0;i<360;i++) {
			act.rotateX(1);
			renderer.render();
		}
	}
}