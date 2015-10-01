// By:	Bryan Whitehead

import java.awt.*;
import java.awt.event.*;
import jogl.*;

class Hw2 extends Frame
{
	OGLRenderer renderer;
	Actor    act;

	public static void main(String argv[]) throws Exception
	{

		PointSet myPts = new PointSet();
		PointCell poCell;
		LineCell lCell;
		LineCell sCell;
		PolygonCell pCell;
		TriangleCell tCell;
		PolylineCell plCell;
		Material mat;
		MaterialSet matSet = new MaterialSet();;
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		float tmp[] = new float[3];
		float rgba[] = { 1.0F, 0.0F, 0.0F, 0.1F };
        int NodeType=0;
		int Count=0;
		int line_num=0;
		int a;
		int size =0;

		mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
		matSet.addMaterial(mat);

		try
		{
			objectReader r = new objectReader(argv[0]);
		
		
			/* Start reading data from example.dat */
			while(!r.eof())
			{
				//r.getString();
				if((line_num % 2) ==0)
				{
					NodeType = r.getInt();
					Count = r.getInt();
					
					for(int i=0; i<3; i++)
						rgba[i]=r.getFloat();

					matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
				}
				else
				{
					switch (NodeType)
					{

					case 0:
						size = myPts.size();
						for(int i=0; i < Count; i++)
						{
							tmp[0]=r.getFloat();	
							tmp[1]=r.getFloat();
							tmp[2]=r.getFloat();		
							myPts.addPoint(new PointType(tmp));
						}
						break;
				
					case 1:
						//Draw Points
						poCell = new PointCell();
						for(int i=0; i<Count; i++)
						{
							a = r.getInt();
							if(size > 0)
								a = a + size;
							poCell.addVal(a);
						}
						poCell.setPoints(myPts);
						poCell.setMaterials(matSet);
						actor.addCell(poCell);
						break;

					case 2:

						// Draw Lines
						lCell = new LineCell();
						for(int i=0; i< Count; i++)
						{	
							a = r.getInt();
							if(size > 0)
								a = a + size;
							lCell.addVal(a);

							a = r.getInt();
							if(size > 0)
								a = a + size;
							lCell.addVal(a);
						}
						lCell.setPoints(myPts);
						lCell.setMaterials(matSet);
						actor.addCell(lCell);
						break;

					case 3:
	
						// Draw Polygons
						pCell = new PolygonCell();
						for(int i=0; i< Count;)
						{
							a = r.getInt();
							if(a == -1)
								i++;
							else
							{
								if(size > 0)
									a = a + size;
								pCell.addVal(a);
							}
						}
						pCell.setPoints(myPts);
						pCell.setMaterials(matSet);
						actor.addCell(pCell);
						break;

					case 4:

						//Draw Triangles
						tCell = new TriangleCell();
						for(int i=0; i<Count; i++)
						{
							a = r.getInt();
							if(size > 0)
								a= a + size;
							tCell.addVal(a);

							a = r.getInt();
							if(size > 0)
								a= a+ size;
							tCell.addVal(a);

							a = r.getInt();
							if(size > 0)
								a= a+ size;
							tCell.addVal(a);
						}
						tCell.setPoints(myPts);
						tCell.setMaterials(matSet);
					    actor.addCell(tCell);
						break;

					case 5:

						//Draw PolyLines
						plCell = new PolylineCell();
						for(int i=0; i<Count;)
						{
							a = r.getInt();
							if( a == -1)
								i++;
							else
							{
								if(size > 0)
									a = a + size;
								plCell.addVal(a);
							}
						}
						plCell.setPoints(myPts);
						plCell.setMaterials(matSet);
						actor.addCell(plCell);
						break;

					default:
						break;
					}
				}

				line_num = line_num +1;
			}
		}
		catch(Exception e)
		{
			 System.out.println("Exception: " + e.toString());
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
		aren.setSize(600, 400);
		aren.getCamera().setFrom(0.0F, 9.5F, 7.0F);
		System.out.println("Here we go");
		System.out.println("	To rotate the object, press 'h', 'j', 'k', 'l'");
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