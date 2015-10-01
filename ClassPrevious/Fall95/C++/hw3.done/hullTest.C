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
#include <stdlib.h>

#include "DataType.H"
#include "StructG.H"
#include "OGLRen.H"

#define DIMX 10
#define DIMY 10

int main(int argc, char *argv[]) {

  OGLCamera *acam = new OGLCamera;
  OGLRenderer *aren = new OGLRenderer;
  ScalarFloat *data = new ScalarFloat;
  StructuredGrid sgrid, plane;
  Material *mat = new Material;
  PointSet *ptset = new PointSet;
  VectorType<float> *conts = new VectorType<float>;
  int    dimx, dimy, meth, i, j;
  float  val, pt[3], pts[100][100][3], vals[100][100];
  double *bbox;

//  Seed the data object.
  ifstream inf("hull.grid");
  inf >> dimx >> dimy;
  for (j=0;j<dimy;j++) {
    for (i=0;i<dimx;i++) {
      inf >> pts[i][j][0] ;
    }
    for (i=0;i<dimx;i++) {
      inf >> pts[i][j][1] ;
    }
    for (i=0;i<dimx;i++) {
      inf >> pts[i][j][2];
    }
    for (i=0;i<dimx;i++) {
      inf >> vals[i][j];
    }
  }

  for ( i=0;i<dimx;i++) {
    for (j=0;j<dimy;j++) {
      *ptset += new PointType(pts[i][j]);
      *data += vals[i][j];
    }
  }

  bbox = data->GetBBox();
  cout << "Data Min: [" << bbox[0] << "]" << endl
       << "Data Max: [" << bbox[1] << "]" << endl;
//  cout << "Data: ";
//  for (i=0;i<data->Count();i++)
//    cout << (*data)[i] << " ";
//  cout << endl;

// Initialize the Structured Grid
  sgrid.SetSize(dimx, dimy);
  sgrid.SetDeltas(1.0, 1.0);
  sgrid.SetData(data);
  sgrid.SetPoints(ptset);
  sgrid.SetScalarIsZ(0);
  if (argc == 1)
    for (val=-7.0;val<8.5;val += 0.5)
      *conts += val;
  else
    *conts += atof(argv[1]);
  sgrid.SetContours(conts);

// Initialize the plane
  plane.SetSize(dimx, dimy);
  plane.SetDeltas(1.0, 1.0);
//  plane.SetData(data);
//  plane.DisplayPlane();

  cout << " Method (1) Grid, (2) Plane, (3) Contours: ";
  cin >> meth;
  switch (meth) {
    case 3: sgrid.DisplayContours();break;
    case 2: sgrid.DisplayPlane();break;
    default: sgrid.DisplayWireFrame();break;
  }
//  sgrid.DisplayPlane();

// Start the Rendering process
  bbox = ptset->GetBBox();
  acam->Initialize();
//  acam->SetROI(bbox);
  aren->AddCamera(acam);
  aren->AddLight(new OGLLight(0));
  // Initialize the Renderer, creates window and attaches OpenGL Visual
  aren->Initialize(argc, argv);
  // Add this  CellSet to the Renderers collection.
  aren->AddCellSet(&sgrid);
//  aren->AddCellSet(&plane);

  // Starts the Event loop.
  aren->PrintSelf(cout);
  aren->MainLoop();

  return(0);
}

