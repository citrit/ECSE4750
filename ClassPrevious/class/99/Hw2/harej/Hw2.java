/*
 * Hw2 based on Hw2.java given out.  All points of interest are noted,
 * specifically code changes from original program
 *
 * John Hare
 * 3/1/99
 *
 * Hw2.java.bak is your original program with the TriangleCell and
 * PolylineCell being demonstrated just in case my efforts fall short for
 * the full homework 2
 *
 * I tried to created a new MaterialSet for each group of like objects
 * so they would actually be the right color... I know I didn't do it well
 * and it may not work at all.  I haven't figured out how to give references
 * directly to the MaterialSet.  With the PointSet, we add the values of the
 * indexes we want... 
 *
 * It's really just not clear if it was right.  I could read the word computer
 * but I had a hell of a time trying to swing it such that it was easy to see
 * so maybe I'm not even displaying the data correctly
 *
 * I don't know and I'm out of steam.  It must be sort of kind of close, but
 * I don't know how to deal with the materials correctly
 *
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
    PointSet myPts = new PointSet();
    //MaterialSet matSet;
    MaterialSet matSet = new MaterialSet();
    PointCell ptCell;
    LineCell lCell;
    PolygonCell pgCell;
    TriangleCell tCell;
    PolylineCell plCell;
    Actor actor = new Actor();
    OGLRenderer aren = new OGLRenderer();

    int count, i, j, k;
    int ptsindex;
    float coor[] = new float [3];
    float rgba[] = new float [4];
    rgba[3] = 0.1F;

    objectReader infile = new objectReader(argv[0]);

    while (!infile.eof())
    { 
      switch (infile.getInt())
      {
        case 0: // coordinates
          count = infile.getInt();
          for (i=0;i<3;i++)
            rgba[i] = infile.getFloat();         // read worthless RGB values

          for (j=0;j<count;j++)                      // number of coordinates
          {
            for (k=0;k<3;k++)
              coor[k] = infile.getFloat();     // getting coordinate triplets
            myPts.addPoint(new PointType(coor));
          } // END for
          break;
        case 1: // Points
          count = infile.getInt();
          for (i=0;i<3;i++)
            rgba[i] = infile.getFloat();

          if (count == 0) break;

          matSet = new MaterialSet();
          matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
          ptCell = new PointCell();
          for (j=0;j<count;j++)                      // number of coordinates
          {
            ptsindex = infile.getInt();
            ptCell.addVal(ptsindex);
          } // END for
          ptCell.setPoints(myPts);              // add point set to PointCell
          ptCell.setMaterials(matSet);       // add material set to PointCell
          actor.addCell(ptCell);                    // add PointCell to actor
          break;
        case 2: // Lines
          count = infile.getInt();
          for (i=0;i<3;i++)
            rgba[i] = infile.getFloat();

          if (count == 0) break;

          matSet = new MaterialSet();
          matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
          lCell = new LineCell();
          for (j=0;j<count;j++)                            // number of lines
          {
            ptsindex = infile.getInt();
            lCell.addVal(ptsindex);
            ptsindex = infile.getInt();
            lCell.addVal(ptsindex);
          } // END for
          lCell.setPoints(myPts);                // add point set to LineCell
          lCell.setMaterials(matSet);         // add material set to LineCell
          actor.addCell(lCell);                      // add LineCell to actor
          break;
        case 3: // Polygons
          count = infile.getInt();
          for (i=0;i<3;i++)
             rgba[i] = infile.getFloat();

          if (count == 0) break;

          matSet = new MaterialSet();
          matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
          for (j=0;j<count;j++)                         // number of polygons
          {
            pgCell = new PolygonCell();
            ptsindex = infile.getInt();
            while (ptsindex != -1)
            {
              pgCell.addVal(ptsindex);
              ptsindex = infile.getInt();
            }
            pgCell.setPoints(myPts);          // add point set to PolygonCell
            pgCell.setMaterials(matSet);   // add material set to PolygonCell
            actor.addCell(pgCell);                // add PolygonCell to actor
          } // END for
          break;
        case 4: // Triangles
          count = infile.getInt();
          for (i=0;i<3;i++)
            rgba[i] = infile.getFloat();

          if (count == 0) break;

          matSet = new MaterialSet();
          matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
          tCell = new TriangleCell();
          for (j=0;j<count;j++)                        // number of triangles
          {
            ptsindex = infile.getInt();
            tCell.addVal(ptsindex);
            ptsindex = infile.getInt();
            tCell.addVal(ptsindex);
            ptsindex = infile.getInt();
            tCell.addVal(ptsindex);
          } // END for
          tCell.setPoints(myPts);                // add point set to TriangleCell
          tCell.setMaterials(matSet);         // add material set to TriangleCell
          actor.addCell(tCell);                      // add TriangleCell to actor
          break;
        case 5: // Polylines
          count = infile.getInt();
          for (i=0;i<3;i++)
             rgba[i] = infile.getFloat();

          if (count == 0) break;

          matSet = new MaterialSet();
          matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
          for (j=0;j<count;j++)                        // number of polylines
          {
            plCell = new PolylineCell();
            ptsindex = infile.getInt();
            while (ptsindex != -1)
            {
              plCell.addVal(ptsindex);
              ptsindex = infile.getInt();
            }
            plCell.setPoints(myPts);         // add point set to PolylineCell
            plCell.setMaterials(matSet);  // add material set to PolylineCell
            actor.addCell(plCell);               // add PolylineCell to actor
          } // END for
          break;
        default:
          break;
      } // END switch
    } // END while

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
    aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
    System.out.println("Here we go");

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
  } // END main

  public boolean action(Event evt, Object what)
  {
    if (evt.target instanceof MenuItem)
    {
    // Since we didn't save references to each of the menu objects,
    // we check which one was pressed by comparing labels.
      if (((String)evt.arg).equals("Run"))
      {
        for (int i = 0;i<360;i++)
        {
          act.rotateX(1);
          renderer.render();
        } // END for
      } // END if
    } // END if
    System.out.println("Event: " + evt + " Object: " + what);
    return super.action(evt, what);
  } // END action

} // class Hw2
