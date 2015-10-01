//
//
// oglrtest
//
//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import jogl.*;

class Hw2 extends Frame
{
	OGLRenderer renderer;
	Actor    act;
	static StreamTokenizer inputFile;
	static int tokenType = StreamTokenizer.TT_NUMBER;	
	public static void main(String args[])
	{
		int nodeType, count;
		float xyz[]= { 0.0F, 0.0F, 0.0F };

		PointSet myPts = new PointSet();

		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		float rgba[] = { 1.0F, 0.0F, 0.0F, 0.1F };

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
		MaterialSet matSet = new MaterialSet();
		while(tokenType != StreamTokenizer.TT_EOF)
		{

			nodeType = getIntegerValue();
			count = getIntegerValue();
			rgba[0] = getFloatValue(); rgba[1] = getFloatValue(); rgba[2] = getFloatValue();  rgba[3] = 0.1F;
			matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
			switch(nodeType) {
				case 0 :
					PointSet Pts = new PointSet();
					myPts = Pts;
					for(int i=0;i<count;i++)
					{
						xyz[0] = getFloatValue();
						xyz[1] = getFloatValue();						
						xyz[2] = getFloatValue();
						myPts.addPoint(new PointType(xyz));
					}
				break;
				case 1 :
					PointCell pCell =new PointCell();
					for(int i=0;i<count;i++)
					{
						int val = getIntegerValue();
						pCell.addVal(val);
					}
					pCell.setPoints(myPts);
					pCell.setMaterials(matSet);
					actor.addCell(pCell);
				break;
				case 2 :
					LineCell lCell = new LineCell();		
					if(!lCell.intVals.isEmpty()) lCell.intVals.removeAllElements();
					for(int i=0;i<count;i++)
					{
						int from = getIntegerValue();	int to = getIntegerValue();
						lCell.addVal(from);			lCell.addVal(to);
					}
					lCell.setPoints(myPts);
					lCell.setMaterials(matSet);
					actor.addCell(lCell);
				break;
				case 3 :
					for(int i=0;i<count;i++)
					{
						int vertex;
						PolygonCell pgCell = new PolygonCell();
						do{
							vertex = getIntegerValue();
							if(vertex >= 0 )  pgCell.addVal(vertex); 
						}while(vertex != -1);
						pgCell.setPoints(myPts);
						pgCell.setMaterials(matSet);
						actor.addCell(pgCell);
					}
				break;
				case 4 :
					for(int i=0;i<count;i++)
					{
						int vertex;
						TriangleCell tCell = new TriangleCell();
						vertex = getIntegerValue();  tCell.addVal(vertex);
						vertex = getIntegerValue();  tCell.addVal(vertex);		
						vertex = getIntegerValue();  tCell.addVal(vertex);
						tCell.setPoints(myPts);
						tCell.setMaterials(matSet);
						actor.addCell(tCell);
					}
				break;
				case 5 :
					for(int i=0;i<count;i++)
					{
						int vertex;
						PolylineCell plCell = new PolylineCell();
						do{
							vertex = getIntegerValue();
							if(vertex >= 0 )  plCell.addVal(vertex); 
						}while(vertex != -1);
						plCell.setPoints(myPts);
						plCell.setMaterials(matSet);
						actor.addCell(plCell);
					}
				break;
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
		aren.setSize(500, 500);
		aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
		DisplayKeyFunction();
		
		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Run" ));
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		hw2.setMenuBar( mb );
	
		hw2.show();
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
			System.out.println("	(To run : java Hw2 exmaple.dat)");
			System.out.println("___________________________________________________");
			System.out.println(" ");
			
	}

}
