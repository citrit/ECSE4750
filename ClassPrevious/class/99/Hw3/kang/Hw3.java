/////////////////////////////////////////////////////////////////////
//
//		Advanced Computer Graphics and Data Visualization
//		                 Homework #3
//
/////////////////////////////////////////////////////////////////////
//
//		Hyosig Kang
//      Mar. 29.1999
//
/////////////////////////////////////////////////////////////////////


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.util.Vector;

class Hw3 extends Frame
{
	OGLRenderer renderer;
	static StructuredGrid	grid;
	static StreamTokenizer inputFile;
	static int tokenType = StreamTokenizer.TT_NUMBER;	

	public static void main(String args[])
	{
		OGLRenderer aren = new OGLRenderer();
		StructuredGrid sGrid = new StructuredGrid();
		PointSet myPts = new PointSet();
		ScalarData sData = new ScalarData();
		PointType sPnt = new PointType();
		MaterialSet matSet;


		int dimx, dimy;
		float data;
		float xyz[]= { 0.0F, 0.0F, 0.0F };


		sPnt.x = sPnt.y = sPnt.z = 0.0F;
		

		sGrid.SetSize(500,500);
		sGrid.SetStartPoint(sPnt);
		sGrid.SetDelta(1.0F, 1.0F);
		


		// Build StreamTokenizer object reading from an input
		// stream whose source is input file
		try{
			inputFile = new StreamTokenizer(new FileReader(args[0]));
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Input File" + args[0] + "not found");
			System.exit(-1);
		}
		inputFile.ordinaryChar('.');

		try{
			tokenType = inputFile.nextToken();
		}
		catch(IOException e) {
			System.out.println("I/O error during parsing");
		}
		dimx = getIntegerValue();
		dimy = getIntegerValue();
		System.out.println("dimx "+ dimx);
		System.out.println("dimy "+ dimy);

		sGrid.SetDimXY(dimx,dimy);

		// Read the data from data file 
		for(int j=0;j<dimy;j++)
			for(int i=0;i<dimx;i++)
			{
				data = getFloatValue();
				sData.addScalar(data);
			}
		sGrid.SetData(sData);
		System.out.println("Min "+sGrid.sData.MinMax[0]);
		System.out.println("Max "+sGrid.sData.MinMax[1]);
	
		// Generate the Points Set
		for(int j=0;j<dimy;j++)
			for(int i=0;i<dimx;i++)
			{
				xyz[0] = sGrid.startPoint.x+(float)i*sGrid.deltax;
				xyz[1] = sGrid.startPoint.x+(float)j*sGrid.deltay;
				xyz[2] = sGrid.sData.getScalar(i+j*dimy);
				myPts.addPoint(new PointType(xyz));
//				System.out.println("xyz "+ xyz[0] + xyz[1] + xyz[2]);
			}
		sGrid.SetPoints(myPts);

		aren.addsGrid(sGrid);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));

		Hw3 Hw3 = new Hw3();
		Hw3.renderer = aren;
		Hw3.grid = sGrid;

		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(500, 500);
		aren.getCamera().setFrom(-3.0F, -3.0F, -3.0F);
		DisplayKeyFunction();
		
		// Add the canvas to the frame and make it show
  		Hw3.add("Center", aren);
		Hw3.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "Menu" );
		file.add( new MenuItem( "Wire" ));
		file.add( new MenuItem( "Plane" ));
		file.add( new MenuItem( "Color" ));
		file.add( new MenuItem( "AllContours" ));
		file.add( new MenuItem( "<< Contour >>" ));
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		Hw3.setMenuBar( mb );
	
		Hw3.show();

	}

	public static int getIntegerValue()
	{
		int value = (int)inputFile.nval;
		try{
			tokenType = inputFile.nextToken();
		}
		catch(IOException e) {
			System.out.println("I/O error during parsing");
		}
		return(value);
	}
	public static float getFloatValue()
	{
		float value = (float)inputFile.nval;
		try{
			tokenType = inputFile.nextToken();
		}
		catch(IOException e) {
			System.out.println("I/O error during parsing");
		}
		return(value);
	}
	public static void getEOLValue()
	{
		try{
			tokenType = inputFile.nextToken();
		}
		catch(IOException e) {
			System.out.println("I/O error during parsing");
		}
	}
	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) {
			// Since we didn't save references to each of the menu objects,
			// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Wire")) {
				Hw3.grid.SetDisplayMode(0);
				renderer.render();
			}
			if (((String)evt.arg).equals("Plane")) {
				Hw3.grid.SetDisplayMode(1);
				renderer.render();
			}
			if (((String)evt.arg).equals("Color")) {
				Hw3.grid.SetDisplayMode(2);
				renderer.render();
			}
			if (((String)evt.arg).equals("AllContours")) {
				Hw3.grid.SetDisplayMode(3);
				renderer.render();
			}
			if (((String)evt.arg).equals("<< Contour >>")) {
				Hw3.grid.SetDisplayMode(4);
				renderer.render();
			}

			if (((String)evt.arg).equals("Exit")) {
				System.exit(0);
			}
		}
		return super.action(evt, what);
	}
	public static void DisplayKeyFunction()
	{
			System.out.println("___________________________________________________");
			System.out.println(" ");
			System.out.println("Advanced Computer Graphics and Data Visualization");
			System.out.println("               Home Work #3");
			System.out.println("              by Hyosig Kang");
			System.out.println("___________________________________________________");
			
			System.out.println(" ");
			System.out.println("	Description of Key Function :");
			System.out.println("		For Rotating projection view");
			System.out.println("			q:x(+) w:x(-)");
			System.out.println("			a:y(+) s:y(-)");
			System.out.println("			z:z(+) x:z(-)");
			System.out.println("		For Translating the projectin view");
			System.out.println("			e:x(+) r:x(-)");
			System.out.println("			d:y(+) f:y(-)");
			System.out.println("			c:z(+) v:z(-)");

			System.out.println("		    -: contour << +: contour >>");

			System.out.println(" ");
			System.out.println("	(To run : java Hw3 exmaple.grid)");
			System.out.println("___________________________________________________");
			System.out.println(" ");
			
	}

}
