//
//
// oglrtest
//
//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import jogl.*;

class Hw2STL extends Frame
{
	OGLRenderer renderer;
	Actor    act;
	static StreamTokenizer inputFile;
	static int tokenType = StreamTokenizer.TT_NUMBER;	
	public static void main(String args[])
	{
		String strValue;
		int nodeType, count;
		float xyz[]= { 0.0F, 0.0F, 0.0F };
		float normal[]= { 0.0F, 0.0F, 0.0F };

		PointSet myPts = new PointSet();
		TriangleCell tCell = new TriangleCell();
	
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		float rgba[] = { 1.0F, 1.0F, 1.0F, 1.0F };

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

		// FACET NORMAL 0.245267 -0.969456 -8.06466e-005
		//  OUTER LOOP
		//  VERTEX 133.157 82.71 223.077
		//   VERTEX 133.032 82.6784 222.796
		//   VERTEX 133.17 82.7133 222.903
		//  ENDLOOP
		// ENDFACET		
		try{
			tokenType = inputFile.nextToken();
		}
		catch(IOException e) {
			System.out.println("I/O error during parsing");
		}
		for(int i=0;i<5;i++)
		{
			strValue = getStringValue();
			System.out.println(strValue);
		}
		MaterialSet matSet = new MaterialSet();
		while(tokenType != StreamTokenizer.TT_EOF)
		{
			strValue = getStringValue();   // FACET
			strValue = getStringValue();   // NORMAL
			normal[0] = getFloatValue(); normal[1] = getFloatValue(); normal[2] = getFloatValue(); 
			strValue = getStringValue();   // OUTER
			strValue = getStringValue();   // LOOP
			
			matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
			PointSet Pts = new PointSet();
			myPts = Pts;
			for(int i=0;i<3;i++)
			{
				strValue = getStringValue();
				xyz[0] = getFloatValue();
				xyz[1] = getFloatValue();						
				xyz[2] = getFloatValue();
				myPts.addPoint(new PointType(xyz));
			}
			strValue = getStringValue();   // ENDLOOP
			strValue = getStringValue();   // ENDFACET
			TriangleCell tri = new TriangleCell();
			tCell=tri;
			tCell.addVal(0);
			tCell.addVal(1);
			tCell.addVal(2);
			tCell.setPoints(myPts);
			tCell.setMaterials(matSet);
			actor.addCell(tCell);
		}
		// Add this  DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));


		Hw2STL Hw2STL = new Hw2STL();
		Hw2STL.renderer = aren;
		Hw2STL.act = actor;
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(500, 500);
		aren.getCamera().setFrom(0.0F, 0.0F, 10.0F);
		DisplayKeyFunction();
		
		// Add the canvas to the frame and make it show
  		Hw2STL.add("Center", aren);
		Hw2STL.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Run" ));
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		Hw2STL.setMenuBar( mb );
	
		Hw2STL.show();
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
	public static String getStringValue()
	{
		String value = inputFile.sval;
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
			if (((String)evt.arg).equals("Run")) {
				for (int i = 0;i<360;i++) {
					act.rotateX(1);
					renderer.render();
				}
			}
			if (((String)evt.arg).equals("Exit")) {
				System.exit(0);
			}
		}
		System.out.println("Event: " + evt + " Object: " + what);
		return super.action(evt, what);
	}
	public static void DisplayKeyFunction()
	{
			System.out.println("___________________________________________________");
			System.out.println(" ");
			System.out.println("Advanced Computer Graphics and Data Visualization");
			System.out.println("               Home Work #2");
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
			System.out.println("		    -:z(+) +:z(-), faster");

			System.out.println(" ");
			System.out.println("	(To run : java Hw2STL exmaple.dat)");
			System.out.println("___________________________________________________");
			System.out.println(" ");
			
	}

}
