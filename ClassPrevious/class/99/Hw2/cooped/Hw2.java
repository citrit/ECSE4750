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
// CLASS: HW2
// Extends: Frame
// Implements: ActionListener
//
// Java OpenGL application that reads in geometric and color data
// of varying types of objects and displays them.
//////////////////////////////////////////////////////////////////////
class Hw2 extends Frame implements ActionListener {
	OGLRenderer renderer;
	static Actor act;
	static objectReader objReader;


	/////////////////////////////////////////////
	// function: main
	// purpose:  One big ugly main function that
	//				 handles the file parsing and 
	//				 object instantiation.
	/////////////////////////////////////////////
	public static void main(String argv[])
	{
		int nodeType;
		int count;
		float r,g,b, a;
		float tmp[] = new float[3];
		Material mt;
		MaterialSet mtSet;
		PointSet ptSet = new PointSet();
		PointCell ptCell = new PointCell();
		LineCell lnCell = new LineCell();
		PolygonCell pgCell = new PolygonCell();
		PolylineCell plCell = new PolylineCell();
		TriangleCell tnCell = new TriangleCell();
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();

		if(argv.length < 1) {
			System.out.println("Syntax: java Hw2 <filename>");
			System.exit(1);
		} else {
		System.out.println("=================================================");
		System.out.println("Dave Cooper - Homework #2");
		System.out.println("Advanced Computer Graphics and Data Visualization");
		System.out.println("=================================================");
		System.out.println("Use: [H|J|K|L] to adjust the viewpoint");
		System.out.println("Camera movement:   left: 4");
		System.out.println("                  right: 6");
		System.out.println("                     up: 8");
		System.out.println("                   down: 2");
		System.out.println("                zoom in: +");
		System.out.println("               zoom out: -");
		System.out.println("=================================================");

			
			
			
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

		}

		// Add this  DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));

		Hw2 hw2 = new Hw2();
		hw2.renderer = aren;
		hw2.act = actor;

		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 15.0F);

		// Add the canvas to the frame and make it show
		hw2.add("Center", aren);
		hw2.pack();


		// Add a window-listener to handle window-closing events
		hw2.addWindowListener(new WindowAdapter() 
									 {public void windowClosing(WindowEvent e) {System.exit(0);}});


		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		MenuItem miRun = new MenuItem("Run");
		miRun.setActionCommand("Run");
		miRun.addActionListener(hw2);
		file.add(miRun);
		MenuItem miExit = new MenuItem("Exit");
		miExit.setActionCommand("Exit");
		miExit.addActionListener(hw2);
		file.add(miExit);
		mb.add( file );
		hw2.setMenuBar( mb );

		hw2.show();
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
			if(cmd.equals("Exit"))
			System.exit(1);
	}
}