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
//  Date:    October 3, 1995, February 1997
//
//////////////////////////////////////////////////////////////////////////

// #include "objTest.h"
#include <iostream.h>

#include "PtCell.h"
#include "LineCell.h"
#include "PlyGCell.h"
#ifdef WIN32
#include "OGLWin32Ren.h"
#else
#include "OGLXRen.h"
#endif

main(int argc, char *argv[])
{

  PointSet *myPts = new PointSet;
  LineCell *lCell = new LineCell;
  LineCell *sCell = new LineCell;
  PolygonCell *pCell = new PolygonCell;
  Material *mat;
  MaterialSet *matSet;
  Actor *actor = new Actor;
  OGLRenderer *aren = new OGLRenderer;
  float tmp[3];
  float rgba[] = { 0.8, 0.8, 0.0, 0.5 };

  mat = new Material(rgba);
  matSet = new MaterialSet;
  *matSet += mat;
  rgba[0] = rgba[1] = 0.0;rgba[2] = 1.0;
  *matSet += new Material(rgba);
  myPts->Reserve(10);
  tmp[0] = 0.0;tmp[1] = 0.0;tmp[2] = 0.0; *myPts += new PointType(tmp);
  tmp[0] = 1.0;tmp[1] = 0.0;tmp[2] = 0.0; *myPts += new PointType(tmp);
  tmp[0] = 1.0;tmp[1] = 1.0;tmp[2] = 0.0; *myPts += new PointType(tmp);
  tmp[0] = 0.0;tmp[1] = 1.0;tmp[2] = 0.0; *myPts += new PointType(tmp);
  tmp[0] = 0.0;tmp[1] = 0.0;tmp[2] = 1.0; *myPts += new PointType(tmp);
  tmp[0] = 1.0;tmp[1] = 0.0;tmp[2] = 1.0; *myPts += new PointType(tmp);
  tmp[0] = 1.0;tmp[1] = 1.0;tmp[2] = 1.0; *myPts += new PointType(tmp);
  tmp[0] = 0.0;tmp[1] = 1.0;tmp[2] = 1.0; *myPts += new PointType(tmp);
  tmp[0] = 0.5;tmp[1] = 0.5;tmp[2] = 0.5; *myPts += new PointType(tmp);

  // Draw a Triangle with lines
  *lCell += 0; *lCell += 1; *lCell += 1; *lCell+= 2; *lCell += 2; *lCell += 0;
  lCell->SetPoints(myPts);
  lCell->SetMaterials(matSet);
  // Draw a Solid Triangle
  *pCell += 0; *pCell += 1; *pCell+= 8; *pCell += 0;
  pCell->SetPoints(myPts);
  pCell->SetMaterials(matSet);
  // Draw a Square
  *sCell += 4; *sCell += 5; *sCell += 5; *sCell += 6; *sCell += 6; *sCell += 7; *sCell +=7; *sCell += 4;
  sCell->SetMaterials(matSet);
  sCell->SetPoints(myPts);
  // Add Cells to the DataSet
  *actor += lCell;
  *actor += pCell;
  *actor += sCell;

  // Initialize the Renderer, creates window and attaches OpenGL Visual
  aren->Initialize(argc, argv);
  // Add this  DataSet to the Renderers collection.
  aren->AddActor(actor);
  aren->AddCamera(new Camera);
  aren->AddLight(new Light(0));

  // Debug
  aren->GetCamera()->PrintSelf(cout);

  // Starts the Event loop.
  aren->MainLoop();

  return(0);
}
