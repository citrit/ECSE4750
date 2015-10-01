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

#include <iostream.h>
#include <fstream.h>

#include "PtCell.h"
#include "LineCell.h"
#include "PlyGCell.h"
#include "PlyLCell.h"
#include "TriCell.h"
#ifdef WIN32
#include "OGLWin32Ren.h"
#else
#include "OGLXRen.h"
#endif


main(int argc, char *argv[])
{
  PointSet *myPts;
  PointCell *ptCell;
  LineCell *liCell;
  PolygonCell *pgCell;
  TriangleCell *trCell;
  PolylineCell *plCell;
  Material *mat;
  MaterialSet *matSet;
  Actor *myactor = new Actor;
  OGLRenderer *aren = new OGLRenderer;
  float x, y, z, rgba[4];
  int type = -1, index = 0, cnt  = 0, i;

   if (argc < 2) {
	  cerr << "usage: " << argv[0] << " Filename" << endl;
	  return 1;
  }
//  Seed the data object.
  ifstream inf(argv[1],ios::nocreate);
  if (! inf) {
	  cerr << "error: canot open <" << argv[1] << "> for reading." << endl;
	  return 999;
  }

  while (! inf.eof()) {
    inf >> type >> cnt >> rgba[0] >> rgba[1] >> rgba[2];rgba[3] = 1.0;
    mat = new Material(rgba);
    matSet = new MaterialSet;
    *matSet += mat;
    switch (type) {
    case 0:
      myPts = new PointSet();
      myPts->Reserve(cnt);
      for (i = 0;i < cnt; i++) {
		  inf >> x >> y >> z;
		  *myPts += new PointType(x, y, z);
      }
      break;
    case 1:
      ptCell = new PointCell;
      ptCell->Reserve(cnt);
      for (i = 0;i < cnt; i++) {
		  inf >> index;
		  *ptCell += index;
      }
      ptCell->SetPoints(myPts);
      ptCell->SetMaterials(matSet);
      *myactor += ptCell;
      break;
    case 2:
      liCell = new LineCell;
      liCell->Reserve(cnt);
      for (i = 0;i <(2* cnt); i++) {
		  inf >> index;
		  *liCell += index;
      }
      liCell->SetPoints(myPts);
      liCell->SetMaterials(matSet);
      *myactor += liCell;
      break;
    case 3:
      for (i = 0;i <cnt; i++) {
		  pgCell = new PolygonCell;
		  inf >> index;
		  while (index != -1) {
			  *pgCell += index;
			  inf >> index;
		  }
		  pgCell->SetPoints(myPts);
		  pgCell->SetMaterials(matSet);
		  *myactor += pgCell;
      }
      break;
    case 4:
      trCell = new TriangleCell;
      trCell->Reserve(cnt*3);
      for (i = 0;i < (3 * cnt); i++) {
		  inf >> index;
		  *trCell += index;
      }
      trCell->SetPoints(myPts);
      trCell->SetMaterials(matSet);
      *myactor += trCell;
      break;
    case 5:
      for (i = 0;i <cnt; i++) {
		  plCell = new PolylineCell;
		  inf >> index;
		  while (index != -1) {
			  *plCell += index;
			  inf >> index;
		  }
		  plCell->SetPoints(myPts);
		  plCell->SetMaterials(matSet);
		  *myactor += plCell;
      }
      break;
    default:
      cerr << "Unknown cell type: " << type << endl;
      break;
    }
  }
  // Add this  DataSet to the Renderers collection.
  aren->AddActor(myactor);
  cout << "Actor Count: " << myactor->Count() <<endl;

  aren->AddCamera(new Camera);
  aren->AddLight(new Light(0));

  // Initialize the Renderer, creates window and attaches OpenGL Visual
  aren->Initialize(argc, argv);
  // Starts the Event loop.
  aren->MainLoop();

  return(0);
}
