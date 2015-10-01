/*Homework 2
  Kent Cheng
  Modification of Hw2.java from hw2.zip file from Prof. Citriniti
*/

import java.awt.*;
import java.awt.event.*;
import jogl.*;

class Hw2 extends Frame
{
	OGLRenderer renderer;
	Actor    act;

	public static void main(String argv[])
	{
		PolylineCell polyLCell = new PolylineCell();   //new class
                TriangleCell triCell = new TriangleCell();     //new class
		PointSet myPts = new PointSet();
                PointCell ptCell = new PointCell();
		LineCell lCell = new LineCell();
		LineCell sCell = new LineCell();
		PolygonCell pCell = new PolygonCell();
		Material mat;
		MaterialSet matSet = new MaterialSet();
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
                int count, shape, j, i, k;
		float tmp[] = new float[3];
		float rgba[] = new float[3];
                objectReader in = new objectReader(argv[0]);
                PointType data;
                

                
                while (!in.eof()){   
                  shape = in.getInt();
                  count = in.getInt();
                  for (i=0; i<3; i++) 
                      rgba[i] = in.getFloat();  //get color

                  switch(shape){
                    case 0:  //coord. of points
                          myPts = new PointSet();                   
                          for(i=0; i<count; i++){
                              for(j=0; j<3; j++)tmp[j] = in.getFloat();  //get the x,y,z coord.
                              data = new PointType(tmp);
                              myPts.addPoint(data);
                          }
                          break;
                    case 1:  //a point
          		  ptCell = new PointCell();
                          matSet = new MaterialSet();
                          mat = new Material(rgba[0], rgba[1], rgba[2], 0);
                          matSet.addMaterial(mat);      //set color
                          for(i=0;i<count;i++)ptCell.addVal(in.getInt()); //get point
                          ptCell.setMaterials(matSet);
			  ptCell.setPoints(myPts);
                          actor.addCell(ptCell);  //add it to the entity
                          break;
                    case 2:  //a line
 			  lCell = new LineCell();
                          matSet = new MaterialSet();
                          mat = new Material(rgba[0], rgba[1], rgba[2], 0);
			  matSet.addMaterial(mat);   //set color
			  for(i=0;i<count;i++)       //read 2 numbers
                             for(j=0;j<2;j++)
 			        lCell.addVal(in.getInt());
		  	  lCell.setMaterials(matSet);
			  lCell.setPoints(myPts);
			  actor.addCell(lCell);  //add to entity
 			  break;
		    case 3:  //a polygon
                          pCell = new PolygonCell();
			  matSet = new MaterialSet();
                          mat = new Material(rgba[0], rgba[1], rgba[2], 0);
			  matSet.addMaterial(mat);   //set color
                          for(i=0;i<count;i++)      
 			     while((j = in.getInt()) != -1)  //read until -1 for each set
          			  pCell.addVal(j);
			  pCell.setMaterials(matSet);
			  pCell.setPoints(myPts);
			  actor.addCell(pCell);  //add to entity
			  break;
		    case 4:  //a triangle
			  triCell = new TriangleCell();
                          matSet = new MaterialSet();
                          mat = new Material(rgba[0], rgba[1], rgba[2], 0);
                          matSet.addMaterial(mat);  //set color
                          for (i=0;i<count;i++)  
                               for(k=0;k<3;k++)
                                  triCell.addVal(in.getInt());   //read 3 numbers
                          triCell.setMaterials(matSet);
                          triCell.setPoints(myPts);
                          actor.addCell(triCell);   //add to entity
                          break;
	  	    case 5:  //a polyline
                          polyLCell = new PolylineCell();
			  matSet = new MaterialSet();
                          mat = new Material(rgba[0], rgba[1], rgba[2], 0);  //set color
                          matSet.addMaterial(mat);
                          for (i=0;i<count;i++)    
                               while ((j = in.getInt()) != -1)  //read until -1 for each set
                                      polyLCell.addVal(j); 
                          polyLCell.setMaterials(matSet); 
                          polyLCell.setPoints(myPts);
                          actor.addCell(polyLCell);   //add to entity
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
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 5.0F, 15.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		//Set up the menu
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