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

// #include "vtk.hh"
#include "vtkRenderMaster.hh"
#include "vtkContourFilter.hh"
#include "vtkPolyMapper.hh"
#include "vtkScalars.hh"
#include "vtkOutlineFilter.hh"
#include "vtkRenderWindowInteractor.hh"
#include <iostream.h>
#include <fstream.h>

main ()
{
  vtkRenderMaster rm;
  vtkRenderWindow *renWin;
  vtkRenderer *aren;
  vtkCamera   *camera1;
  vtkLight    *light1;
  vtkActor    *actor1, *actor2;
  vtkStructuredPoints *volume;
  vtkFloatScalars *scalars;
  vtkContourFilter *cf;
  vtkPolyMapper *mapper, *omapper;
  vtkOutlineFilter *outline;
  vtkRenderWindowInteractor *iren;
  int dimx, dimy;
  float val, range[2];
  
  renWin  = rm.MakeRenderWindow();
  iren = renWin->MakeRenderWindowInteractor();
  aren    = renWin->MakeRenderer();

  //  Get the problem size.
  ifstream inf("example.grid");
  inf >> dimx >> dimy;

  // define geometry of volume
  volume = new vtkStructuredPoints;
    volume->DebugOn();
    volume->SetDimensions(dimx, dimy, 1);
    volume->SetOrigin(0.0, 0.0, 0.0);
    volume->SetAspectRatio(1,1,1);

  //  Seed the data object.
  scalars = new vtkFloatScalars(dimx*dimy);
  for (int i=0;i<dimx*dimy;i++) {
    inf >> val;
    scalars->SetScalar(i, val);
  }
  volume->GetPointData()->SetScalars(scalars);

  cf = new vtkContourFilter;
    cf->DebugOn();
    cf->SetInput(volume);
    range[0] = -7.0;range[1] = 8.0;
    cf->GenerateValues(20, range);
//    cf->SetValue(0, 1.3);

  mapper = new vtkPolyMapper;
    mapper->SetInput(cf->GetOutput());

  actor1 = new vtkActor;
    actor1->SetMapper(mapper);
    actor1->GetProperty()->SetColor(0.8,1.0,0.9);

  // draw an outline
  outline = new vtkOutlineFilter;
    outline->SetInput(volume);

  omapper = new vtkPolyMapper;
    omapper->SetInput(outline->GetOutput());

  actor2 = new vtkActor;
    actor2->SetMapper(omapper);
    actor2->GetProperty()->SetColor(1,1,1);

  light1 = new vtkLight;
  aren->AddLights(light1);
  aren->AddActors(actor2);
  aren->AddActors(actor1);
  aren->SetBackground(0.0,0.0,0.0);

  renWin->Render();

  // interact with data
  iren->Start();
}
