//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.io.*;
import java.util.*;

class Hw2 extends Frame
{
	OGLRenderer renderer;
	Actor    act;

	public static void main(String[] args)
	{
		if (args.length != 1) 
		{
			System.out.println("Error: Not enough/too many arguments to Hw2.");
			System.out.println ("Do not use the < in the command line.");
			System.out.println("use command line as such: Hw2 filename "); 
			System.exit (-1);
		}		

		

		PointSet myPts = new PointSet();
		LineCell lCell = new LineCell();
		LineCell sCell = new LineCell();
		PolylineCell plCell = new PolylineCell();
		TriangleCell tCell = new TriangleCell();
		PolygonCell pCell = new PolygonCell();

		Material mat;
		MaterialSet matSet;
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		float tmp[] = new float[3];
		float rgba[] = { 1.0F, 0.0F, 0.0F, 0.1F };


		try {
		Parser myParse = new Parser(args[0], actor);
		}

		catch(Exception e)
     		{
			System.out.println("File Parser Error: "+e);
			System.exit(1);
     		}




		mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		rgba[0] = rgba[1] = 0.0F;rgba[2] = 1.0F;
		matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
		tmp[0] = 0.0F;tmp[1] = 0.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 0.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 1.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.0F;tmp[1] = 1.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.0F;tmp[1] = 0.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 0.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 1.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.0F;tmp[1] = 1.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.5F;tmp[1] = 0.5F;tmp[2] = 0.5F; myPts.addPoint(new PointType(tmp));

		// Draw a Triangle with lines
		lCell.addVal(0); lCell.addVal(1);lCell.addVal(1); lCell.addVal(2);
		lCell.addVal(2); lCell.addVal(0);
		lCell.setPoints(myPts);
		lCell.setMaterials(matSet);

		// Draw a Solid Triangle
		pCell.addVal(0); pCell.addVal(1); pCell.addVal(8);// pCell.addVal(0);
		pCell.setPoints(myPts);
		pCell.setMaterials(matSet);

		//Draw an unclosed line segment
		plCell.addVal(0); plCell.addVal(1); plCell.addVal(2);
	        plCell.addVal(3); plCell.addVal(6);
		plCell.setPoints(myPts);
		plCell.setMaterials(matSet);

		// Draw a Triangle with lines
		tCell.addVal(1); tCell.addVal(3);tCell.addVal(4); 
		tCell.setPoints(myPts);
		tCell.setMaterials(matSet);

		// Draw a Square
		sCell.addVal(4); sCell.addVal(5); sCell.addVal(5); sCell.addVal(6);
		sCell.addVal(6); sCell.addVal(7); sCell.addVal(7); sCell.addVal(4);
		sCell.setMaterials(matSet);
		sCell.setPoints(myPts);

		// Add Cells to the DataSet
		//actor.addCell(lCell);
		//actor.addCell(plCell);
		//actor.addCell(pCell);
		//actor.addCell(tCell);
		//actor.addCell(sCell);

		// 
		//actor

		System.out.println();
		System.out.println();
		System.out.println("Actor's translation Position");
		System.out.println("transPosX: " + actor.transPosX);
		System.out.println("transPosY: " + actor.transPosY);
		System.out.println("transPosZ: " + actor.transPosZ);
		System.out.println();
		System.out.println();
		
		actor.translate(actor.transPosX, actor.transPosY, actor.transPosZ);

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
		System.out.println("Here we go");

		System.out.println("---------------------------------------------");

		System.out.println("Homework #2");
		System.out.println("Heidi K. von Ludewig");
		System.out.println();
		System.out.println("Version 2.0 Functionality");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("PolylineCells are created and functioning: PolylineCell.java");
		System.out.println("TriangleCells are created and functioning: TriangleCell.java");
		System.out.println("Reading in and tokenizing of input file using streams is functioning: Parser.java");
		System.out.println("Position of graphics determined by getting the bounding box of the first PointSet and translating the actor by the bounding box's negative center. Of course, this is a little off and so like any good programmer, I cheated: check out the function handleCoordinates in Parser.java to give feedback on why this might not have been working properly.");
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------------");

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
		}
		System.out.println("Event: " + evt + " Object: " + what);
		return super.action(evt, what);
	}
}