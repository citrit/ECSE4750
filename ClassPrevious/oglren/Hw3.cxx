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

#define DIMX 10
#define DIMY 10

#ifdef WIN32
#include "OGLWin32Ren.h"
#else
#include "OGLXRen.h"
#endif


main(int argc, char *argv[])
{

  Camera *acam = new Camera;
  OGLRenderer *aren = new OGLRenderer;
  ScalarFloat *data = new ScalarFloat;
  StructuredGrid sgrid, plane;
  Material *mat = new Material;
  PointSet *ptset = new PointSet;
  VectorType<float> *conts = new VectorType<float>;
  int    dimx, dimy, meth, i, j;
  float  val, pt[3], pts[100][100][3], vals[100][100];
  double *bbox;

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
  inf >> dimx >> dimy;
	cout << "Dimx: " << dimx << "  Dimy: " << dimy << endl;
  for ( i=0;i<dimx;i++) {
    for (j=0;j<dimy;j++) {
		inf >> val;
		*data += val;
    }
  }
	cout << endl;
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
  sgrid.Translate(0-dimx/2, 0-dimy/2,0);
  //sgrid.SetPoints(ptset);
  sgrid.SetScalarIsZ(1);
  if (argc == 2)
    for (val=bbox[0];val<bbox[1];val += 0.5)
      *conts += val;
  else
    *conts += atof(argv[1]);
  sgrid.SetContours(conts);
  conts->PrintSelf(cout);

// Initialize the plane
  plane.SetSize(dimx, dimy);
  plane.SetDeltas(1.0, 1.0);
  plane.SetData(data);
  plane.DisplayWireFrame();
  plane.Translate(0-dimx/2, 0-dimy/2,0);

  cout << " Method (1) Grid, (2) Plane, (3) Contours: ";
  cin >> meth;
  switch (meth) {
    case 3: sgrid.DisplayContours();break;
    case 2: sgrid.DisplayPlane();break;
    default: sgrid.DisplayWireFrame();break;
  }
//  sgrid.DisplayPlane();

// Start the Rendering process
  acam->Initialize();
//  acam->SetROI(bbox);
  aren->AddCamera(acam);
  aren->AddLight(new Light(0));
  // Initialize the Renderer, creates window and attaches OpenGL Visual
  aren->Initialize(argc, argv);
  // Add this  CellSet to the Renderers collection.
  aren->AddActor(&sgrid);
  aren->AddActor(&plane);

  // Point the camera
  //aren->GetCamera()->SetPosition(12, 12, 40, 15, 15, 0, 0, 1, 0);
  // Starts the Event loop.
  aren->PrintSelf(cout);
  aren->MainLoop();

  return(0);
}
