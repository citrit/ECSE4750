//////////////////////////////////////////////////////
//  Dave Cooper
//  Advanced Computer Graphics and Data Visualization
//  Homework #1
//  February 8th 1999
//
//  Adapted from oglrtest
//////////////////////////////////////////////////////
import java.awt.*;
import java.awt.event.*;
import jogl.*;


//////////////////////////////////////////////////////////////////////
// CLASS: HW3
// Extends: Frame
// Implements: ActionListener
//
// Java OpenGL application that reads in geometric and color data
// of varying types of objects and displays them.
//////////////////////////////////////////////////////////////////////
class Hw3 extends Frame implements ActionListener {
	static Hw3 hw3;
	OGLRenderer renderer;
	static Actor act;
	static objectReader objReader;
	static StructuredGrid sGrid;
	static ScalarData scalarData;

	/////////////////////////////////////////////
	// function: main
	// purpose:  One big ugly main function that
	//				 handles the file parsing and 
	//				 object instantiation.
	/////////////////////////////////////////////
	public static void main(String argv[])
	{
		int nodeType;
		int dimX, dimY;
		int count;
		float r,g,b, a;
		float tmp[] = new float[3];
		float centX = 0.0F;
		float centY = 0.0F;
		float centZ = 0.0F;
		Material mt;
		MaterialSet mtSet;
		PointSet ptSet = new PointSet();				//defines point set
		OGLRenderer aren = new OGLRenderer();		//defines OGLRenderer

		if((argv.length < 1) || (!((argv[0].endsWith(".grid")) || (argv[0].endsWith(".dat"))))) {
			System.out.println("Syntax: java Hw2 <filename>");
			System.out.println("Files with a .grid extension will be treated as grid objects");
			System.out.println("Files with a .dat extension will be treated as miscelaneous geometry files.");
			System.exit(1);
		} else {
			System.out.println("=======================================================");
			System.out.println("Dave Cooper - Homework #2");
			System.out.println("Advanced Computer Graphics and Data Visualization");
			System.out.println("=======================================================");
			System.out.println("Use: [H|J|K|L] to adjust the viewpoint");
			System.out.println("Camera movement:   left: 4");
			System.out.println("                  right: 6");
			System.out.println("                     up: 8");
			System.out.println("                   down: 2");
			System.out.println("                zoom in: +");
			System.out.println("               zoom out: -");
			System.out.println("=======================================================");



			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// NEW STRUCTURED GRID CONSTRUCT
			//
			if(argv[0].endsWith(".grid")) {
				objReader = new objectReader(argv[0]);
				sGrid = new StructuredGrid();							//creates a structured grid object
				scalarData = new ScalarData();			//creates a scalar data object to hold scalar info
				ptSet = new PointSet();
				float deltaX = sGrid.deltaX;
				float deltaY = sGrid.deltaY;
				float zCoord;

				while(!objReader.eof()) {

					dimX = objReader.getInt();							//gets # of x grid coordinates
					dimY = objReader.getInt();							//gets # of y grid coordinates
					sGrid.SetSize(dimX-1,dimY-1);						//defines the width and height number of grid cells

					for(float i=0; i < dimY; i++)
						for(float j=0; j < dimX; j++) {
							zCoord = objReader.getFloat();
							scalarData.addPoint(zCoord);
							tmp[0]=j * deltaY;
							tmp[1]=i * deltaX;
							tmp[2]=zCoord;
							ptSet.addPoint(new PointType(tmp));
						}

					sGrid.SetData(scalarData);
					sGrid.SetPoints(ptSet);

				}

				// Add this DataSet to the Renderers collection.
				aren.addActor(sGrid);
				aren.addCamera(new OGLCamera());
				aren.addLight(new OGLLight(0));

				hw3 = new Hw3();
				hw3.renderer = aren;
				hw3.act = sGrid;
			}
			//
			// END NEW STRUCTURED GRID CONSTRUCT
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// OLD CONSTRUCTS
			//
			else {
				PointCell ptCell = new PointCell();
				LineCell lnCell = new LineCell();
				PolygonCell pgCell = new PolygonCell();
				PolylineCell plCell = new PolylineCell();
				TriangleCell tnCell = new TriangleCell();
				Actor actor = new Actor();


				objReader = new objectReader(argv[0]);

				while(!objReader.eof()) {

					//retrieve header information	
					nodeType = objReader.getInt();
					count = objReader.getInt();

					r = objReader.getFloat();
					g = objReader.getFloat();
					b = objReader.getFloat();
					a = 0.1F;

					mt = new Material(r,g,b,a);
					mtSet = new MaterialSet();
					mtSet.addMaterial(mt);

					switch(nodeType) {
					//Coordinate Values
					case 0:{
							ptSet = new PointSet();
							for(int i=0; i < count; i++) {
								tmp[0] = objReader.getFloat();
								tmp[1] = objReader.getFloat();
								tmp[2] = objReader.getFloat();
								ptSet.addPoint(new PointType(tmp));
							}
							break;
						}
						//Point Values
					case 1:{
							for(int i = 0; i < count; i++) {
								ptCell = new PointCell();
								ptCell.addVal(objReader.getInt());
								ptCell.setPoints(ptSet);
								ptCell.setMaterials(mtSet);
								actor.addCell(ptCell);
							}
							break;
						}
						//Line Values
					case 2:{
							for(int i=0; i < count; i++) {
								lnCell = new LineCell();
								lnCell.addVal(objReader.getInt());
								lnCell.addVal(objReader.getInt());

								lnCell.setPoints(ptSet);
								lnCell.setMaterials(mtSet);
								actor.addCell(lnCell);
							}
							break;
						}
						//Polygon Values
					case 3:{
							int tmpRef=0;

							for(int i=0; i < count; i++) {
								pgCell = new PolygonCell();
								tmpRef = objReader.getInt();

								if(tmpRef != -1) {
									do {
										pgCell.addVal(tmpRef);
										tmpRef = objReader.getInt();
									}while(tmpRef != -1);
								}

								pgCell.setPoints(ptSet);
								pgCell.setMaterials(mtSet);
								actor.addCell(pgCell);
							}
							break;
						}
						//Triangle Values
					case 4:{
							for(int i=0; i < count; i++) {
								tnCell = new TriangleCell();
								tnCell.addVal(objReader.getInt());
								tnCell.addVal(objReader.getInt());
								tnCell.addVal(objReader.getInt());

								tnCell.setPoints(ptSet);
								tnCell.setMaterials(mtSet);
								actor.addCell(tnCell);
							}
							break;
						}
						//Polyline Values
					case 5:{ 
							int tmpRef = 0;

							for(int i=0; i < count; i++) {
								plCell = new PolylineCell();
								tmpRef = objReader.getInt();

								if(tmpRef != -1) {
									do {
										plCell.addVal(tmpRef);
										tmpRef = objReader.getInt();
									}while(tmpRef != -1);
								}

								plCell.setPoints(ptSet);
								plCell.setMaterials(mtSet);
								actor.addCell(plCell);
							}
							break;
						}
					}

				}

				// Add this  DataSet to the Renderers collection.
				aren.addActor(actor);
				aren.addCamera(new OGLCamera());
				aren.addLight(new OGLLight(0));

				hw3 = new Hw3();
				hw3.renderer = aren;
				hw3.act = actor;
			}
		}
		//
		// END OLD CONSTRUCTS
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////



		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);

		centX = (float)((ptSet.BBox[0] + ptSet.BBox[3]) / 2);
		centY = (float)((ptSet.BBox[1] + ptSet.BBox[4]) / 2);
		centZ = (float)((ptSet.BBox[2] + ptSet.BBox[5]) / 2);

		aren.getCamera().setFrom(centX, centY, 40);
		aren.getCamera().setTo(centX, centY, centZ);

		// Add the canvas to the frame and make it show
		hw3.setLayout(new BorderLayout());
		hw3.add(aren, BorderLayout.CENTER);
		hw3.pack();

		// Add a window-listener to handle window-closing events
		hw3.addWindowListener(new WindowAdapter() 
									 {public void windowClosing(WindowEvent e) {System.exit(0);}});


		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		MenuItem miRun = new MenuItem("Run");
		miRun.setActionCommand("Run");
		miRun.addActionListener(hw3);
		file.add(miRun);
		MenuItem miExit = new MenuItem("Exit");
		miExit.setActionCommand("Exit");
		miExit.addActionListener(hw3);
		file.add(miExit);
		mb.add( file );
		if(argv[0].endsWith(".grid")) {

			Menu grid = new Menu( "Grid" );

			MenuItem miWire = new MenuItem("Wireframe");
			miWire.setActionCommand("Wireframe");
			miWire.addActionListener(hw3);
			grid.add(miWire);

			MenuItem miPlane = new MenuItem("Plane");
			miPlane.setActionCommand("plane");
			miPlane.addActionListener(hw3);
			grid.add(miPlane);

			MenuItem miContour = new MenuItem("Contour");
			miContour.setActionCommand("contour");
			miContour.addActionListener(hw3);
			grid.add(miContour);

			mb.add(grid);
		}

		hw3.setMenuBar( mb );

		hw3.show();
	}

	/////////////////////////////////////////////
	// function: actionPerformed
	// purpose: non-deprecated function that
	//          handles menu actions
	/////////////////////////////////////////////
	public void actionPerformed(ActionEvent evt)
	{
		String cmd = evt.getActionCommand();

		if(cmd.equals("Run"))
			for(int i = 0;i<360;i++) {
				act.rotateX(1);
				renderer.render();
			} else
			if(cmd.equals("Wireframe")) {
			sGrid.renderMode = 1;
			renderer.render();
			//make call to render
		} else
			if(cmd.equals("plane")) {
			sGrid.renderMode = 2;
			renderer.render();
			//make call to render
		} else
			if(cmd.equals("contour")) {
			sGrid.renderMode = 3;
			renderer.render();
			//make call to render
		} else
			if(cmd.equals("Text")) {
		}
		if(cmd.equals("Exit")) {
			System.exit(1);
		}
	}
}