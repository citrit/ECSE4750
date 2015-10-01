//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used 
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    October 3, 1995
//
//////////////////////////////////////////////////////////////////////////

#include "PtCell.h"
#include "LineCell.h"
#include "PolyLineCell.h"
#include "PlyCell.h"
#include "TriangleCell.h"
#include "DataSet.h"
#include "OGLRen.h"
#include <fstream.h>
#include <iostream.h>

enum DataType { COORD, POINT, LINE, POLYGON, TRIANGLE, POLYLINE };

main(int argc, char *argv[])
{

  char fname[128];
  DataType type;
  int number, i, ttype;
  float red, green, blue, alpha;

	// array for points
  float tmp[3];
  float fcoord[3];
  int icoord[3];


	// this is our renderer... we still need to initialize it etc.
  OGLRenderer *aren = new OGLRenderer;

	// the entire collection
  DataSet *dSet = new DataSet;


   strcat(fname,"example.dat");

        // open the file
   ifstream inFile(fname,ios::in);
  
        // if it did not exist/open successfully...
   if(!inFile)
    {
     cout << "\nError opening example.dat file..." << endl;
     return(-1);
    }

        // read all the info needed
   while(!inFile.eof())
    {
     inFile >> ttype >> number >> red >> green >> blue;
     alpha = 1.0;
     cout << "Reading " << number << " type " << ttype << " object(s)." << endl;

     type = (DataType)ttype;

	// reading coordinates... always triplets
     switch (type) {

     case COORD:
	// point set to accumulate points to make into lines
     PointSet *myPts = new PointSet;

     for (i=0; i<number; i++)
       {
//       cout << "Reading COORD..." << endl;
	// read in three floats for one point
         inFile >> fcoord[0] >> fcoord[1] >> fcoord[2];
	// and add the set to the coordinate structure
         *myPts += new PointType(fcoord);
       };
     break;

     case POINT:
	// a cell to hold all of our points
     PointCell *oCell = new PointCell;
     oCell->SetPoints(myPts);
     oCell->SetColor(red, green, blue, alpha);

     for (i=0; i<number; i++)
       {
	// read in one coord for one point
         inFile >> icoord[0];
	// add it to the point structure
         *oCell += icoord[0];
       };

	// add the points to the overall data set
     *dSet += oCell;
     break;

     case LINE:
	// two line cells... they hold two point sets apiece ???
         LineCell *lCell = new LineCell;
     for (i=0; i<number; i++)
       {
         cout << "Reading LINE..." << endl;
	// read in one coord for one point
         inFile >> icoord[0] >> icoord[1];
	// add it to the point structure
         *lCell += icoord[0];
         *lCell += icoord[1];
       };

	// add the points to the overall data set
     lCell->SetPoints(myPts);
     lCell->SetColor(red, green, blue, alpha);
     *dSet += lCell;
     break;

     case POLYGON:
     for (i=0; i<number; i++)
       {
	// we need a new polygon...
        PolygonCell *pCell = new PolygonCell;
        pCell->SetPoints(myPts);
        pCell->SetColor(red, green, blue, alpha);
        cout << "Reading POLYGON..." << endl;

        icoord[0] = 0;
	// keep on reading until we hit the end of the polygon data
        while(icoord[0] != -1)
          {
	// read in one coord for one point
            inFile >> icoord[0];

	// add it to the polygon structure
            if(icoord[0] != -1) *pCell += icoord[0];
          }

	// add the polygon to the overall data set
         *dSet += pCell;
       };
     break;

     case TRIANGLE:
     for (i=0; i<number; i++)
       {
	// we need a new triangle...
        TriangleCell *tCell = new TriangleCell;
        tCell->SetPoints(myPts);
        tCell->SetColor(red, green, blue, alpha);

        cout << "Reading TRIANGLE..." << endl;

	// read in one triangle
         inFile >> icoord[0] >> icoord[1] >> icoord[2];

	// add it to the polygon structure
         *tCell += icoord[0];
         *tCell += icoord[1];
         *tCell += icoord[2];
         *tCell += icoord[0];

	// add the polygon to the overall data set
         *dSet += tCell;
       };
     break;



     case POLYLINE:
     for (i=0; i<number; i++)
       {
	// we need a new polygon...
        PolyLineCell *plCell = new PolyLineCell;
        plCell->SetPoints(myPts);
        plCell->SetColor(red, green, blue, alpha);
        cout << "Reading POLYLINE..." << endl;

        icoord[1] = 0;
        inFile >> icoord[0];

	// keep on reading until we hit the end of the polygon data
        while(icoord[1] != -1)
          {
	// read in one coord for one point
            inFile >> icoord[1];

	// add it to the polygon structure
            if(icoord[1] != -1)
              {
               *plCell += icoord[0];
               *plCell += icoord[1];
                icoord[0] = icoord[1];
              }
          }

	// add the polyline set to the overall data set
         *dSet += plCell;
       }
     break;

     default: 
     cout << "Invalid data... must be the end of the input file!" << endl;
     break;
    }	// the switch

   }// the while!eof


  cout << endl << "Done reading data... Let's render!" << endl;


	// Add this DataSet to the Renderers collection.
  aren->AddDataSet(dSet);

	// Initialize the Renderer, creates window and attaches OpenGL Visual
  aren->Initialize(argc, argv);

	// Starts the Event loop.
  aren->MainLoop();

  return(0);
}
