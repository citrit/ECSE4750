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
#include <unistd.h>

#include "PtCell.H"
#include "LineCell.H"
#include "PlyGCell.H"
#include "PlyLCell.H"
#include "TriCell.H"
#include "CellSet.H"
#include "OGLRen.H"


main(int argc, char *argv[])
{

  PointSet *myPts = new PointSet;
  LineCell *lCell = new LineCell;
  LineCell *sCell = new LineCell;
  PolygonCell *pCell = new PolygonCell;
  Material *mat;
  MaterialSet *matSet;
  CellSet *dSet = new CellSet;
  OGLCamera *acam = new OGLCamera;
  OGLRenderer *aren = new OGLRenderer;
  float tmp[3];
  float rgba[] = { 1.0, 1.0, 0.0, 1.0 };
  double *bbox;

  mat = new Material(rgba);
  matSet = new MaterialSet;
  *matSet += mat;
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

  bbox = myPts->GetBBox();
  cout << "Points Min: [" << bbox[0] << "," << bbox[1] << "," << bbox[2] << "]" << endl
       << "Points Max: [" << bbox[3] << "," << bbox[4] << "," << bbox[5] << "]" << endl;
  // Draw a Triangle with lines
  *lCell += 0; *lCell += 1; *lCell += 1; *lCell+= 2; *lCell += 2; *lCell += 0;
  lCell->SetPoints(myPts);
  lCell->SetMaterials(matSet);
  // Draw a Solid Triangle
  *pCell += 0; *pCell += 1; *pCell+= 8; *pCell += 0;
  pCell->SetPoints(myPts);
  pCell->SetMaterials(matSet);
  // Draw a Square
  sleep(2);  // To test the time stamp.
  *sCell += 4; *sCell += 5; *sCell += 5; *sCell += 6; *sCell += 6; *sCell += 7; *sCell +=7; *sCell += 4;
  sCell->SetMaterials(matSet);
  sCell->SetPoints(myPts);
  // Add Cells to the CellSet
  *dSet += lCell;
  *dSet += pCell;
  *dSet += sCell;

  lCell->PrintSelf(cout);
  pCell->PrintSelf(cout);
  sCell->PrintSelf(cout);

  acam->Initialize(aren);
  aren->AddCamera(acam);
  aren->AddLight(new OGLLight(0));
  // Initialize the Renderer, creates window and attaches OpenGL Visual
  aren->Initialize(argc, argv);
  // Add this  CellSet to the Renderers collection.
  aren->AddCellSet(dSet);

  // Starts the Event loop.
  aren->PrintSelf(cout);
  aren->MainLoop();

  return(0);
}
