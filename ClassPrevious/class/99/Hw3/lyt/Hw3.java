// Trung Ly		3/31/99
//
// This was modified from the original Hw2.java to satisfy the criteria
// for Homework #3 for Advanced Grapics and Visualization
//
//
// Important Note:  the input data file cannont have any extra lines
// at the end of the file.  (i.e. whitespace)
// Otherwise the object reader reads it as an integer value of 0.

import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.lang.*;
import java.util.Vector;

class Hw3 extends Frame implements ActionListener
{
	OGLRenderer renderer;
	Actor    act;
	StructuredGrid grid;

	public static void main(String argv[])
	{
		objectReader or;
		OGLRenderer aren = new OGLRenderer();
		Actor actor = new Actor();
		StructuredGrid grid = new StructuredGrid();
		ScalarData gdata;
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
		int w, h;
		String extension = new String();
		float box[] = new float[6];

		rgba[0] = rgba[1] = 0.0F; rgba[2] = rgba[3] = 1.0F;
		matSet.addMaterial(rgba[0],rgba[1],rgba[2],rgba[3]);

		or = new objectReader(argv[0]);

		System.out.println("Reading in data file...");

		extension = argv[0].substring( argv[0].indexOf('.')+1 );

		if( extension.equalsIgnoreCase(new String("grid")) )
		{
			w = or.getInt();
			h = or.getInt();
			grid.setSize( w, h );
			gdata = new ScalarData( w*h );
			for(int i=0; i<w*h; i++)
				gdata.addScalar( or.getFloat() );
			grid.setData( gdata );
			grid.generatePointSet();
			grid.displayWireFrame();
			grid.setContours(10);  // Six contours
			box = grid.getBBox();
		}

		else if( extension.equalsIgnoreCase(new String("dat")) )
		{
			while( !or.eof() )
			{
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
					box = myPts.getBBox();
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
		}

		else
		{
			System.out.println( "Error: You must provide a filename ending in .dat or .grid" );
			System.exit(1);
		}

		System.out.println("Done!");

		// Add this DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addActor(grid);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));

		Hw3 hw3 = new Hw3();
		hw3.renderer = aren;
		hw3.act = actor;
		hw3.grid = grid;

		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);

		// Set the camera looking parrelel to the Z axis
		aren.getCamera().setFrom(
			((float)box[0]+(float)box[3])/2,
			((float)box[1]+(float)box[4])/2,
			2*((float)box[0]+(float)box[3] + (float)box[1]+(float)box[4])
		);
		aren.getCamera().setTo(
			((float)box[0]+(float)box[3])/2,
			((float)box[1]+(float)box[4])/2,
			((float)box[2]+(float)box[5])/2
		);

		// Add the canvas to the frame and make it show
  		hw3.add("Center", aren);
		hw3.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "Change Mode" );
		file.addActionListener(hw3);
//		file.add( new MenuItem( "Rotate") );
		file.add( new MenuItem( "WireFrame") );
		file.add( new MenuItem( "Plane") );
		file.add( new MenuItem( "Contours") );
		mb.add( file );
		hw3.setMenuBar( mb );

		hw3.show();
	}

	public void actionPerformed(ActionEvent e)
	{
		float box[] = new float[6];

		if(e.getActionCommand()=="Rotate")
		{
			for (int i = 0;i<360;i++)
			{
				act.rotateX(1);
				renderer.render();
			}
		}
		else if(e.getActionCommand()=="WireFrame")
		{
			grid.clearGrid();
			renderer.render();
			grid.displayWireFrame();
			renderer.render();
		}
		if(e.getActionCommand()=="Plane")
		{
			grid.clearGrid();
			renderer.render();
			grid.displayPlane();
			renderer.render();
		}
		if(e.getActionCommand()=="Contours")
		{
			grid.clearGrid();
			renderer.render();
			grid.displayContours();
			renderer.render();
		}
	}
}