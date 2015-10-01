# get the interactor ui
source vtkInt.tcl

# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

#
# Load up the data
vtkStructuredPointsReader vol;
    vol SetFilename "MRIdata.vtk"
    vol DebugOn;

vtkMarchingCubes contour1;
  contour1 SetInput [vol GetOutput];
  contour1 GenerateValues 1 0.0 25.0;
vtkMarchingCubes contour2;
  contour2 SetInput [vol GetOutput];
  contour2 GenerateValues 1 25.0 50.0;
vtkMarchingCubes contour3;
  contour3 SetInput [vol GetOutput];
  contour3 GenerateValues 1 50.0 75.0;
vtkMarchingCubes contour4;
  contour4 SetInput [vol GetOutput];
  contour4 GenerateValues 1 75.0 100.0;
vtkMarchingCubes contour5;
  contour5 SetInput [vol GetOutput];
  contour5 GenerateValues 1 100.0 125.0;
vtkMarchingCubes contour6;
  contour6 SetInput [vol GetOutput];
  contour6 GenerateValues 1 125.0 150.0;
vtkMarchingCubes contour7;
  contour7 SetInput [vol GetOutput];
  contour7 GenerateValues 1 150.0 175.0;
vtkMarchingCubes contour8;
  contour8 SetInput [vol GetOutput];
  contour8 GenerateValues 1 175.0 200.0;
vtkMarchingCubes contour9;
  contour9 SetInput [vol GetOutput];
  contour9 GenerateValues 1 200.0 225.0;
vtkMarchingCubes contour10;
  contour10 SetInput [vol GetOutput];
  contour10 GenerateValues 1 225.0 255.0;



vtkPolyMapper volMapper1;
  volMapper1 SetInput [contour1 GetOutput];
  volMapper1 ScalarsVisibleOff;
vtkPolyMapper volMapper2;
  volMapper2 SetInput [contour2 GetOutput];
  volMapper2 ScalarsVisibleOff;
vtkPolyMapper volMapper3;
  volMapper3 SetInput [contour2 GetOutput];
  volMapper3 ScalarsVisibleOff;
vtkPolyMapper volMapper4;
  volMapper4 SetInput [contour4 GetOutput];
  volMapper4 ScalarsVisibleOff;
vtkPolyMapper volMapper5;
  volMapper5 SetInput [contour5 GetOutput];
  volMapper5 ScalarsVisibleOff;
vtkPolyMapper volMapper6;
  volMapper6 SetInput [contour6 GetOutput];
  volMapper6 ScalarsVisibleOff;
vtkPolyMapper volMapper7;
  volMapper7 SetInput [contour7 GetOutput];
  volMapper7 ScalarsVisibleOff;
vtkPolyMapper volMapper8;
  volMapper8 SetInput [contour8 GetOutput];
  volMapper8 ScalarsVisibleOff;
vtkPolyMapper volMapper9;
  volMapper9 SetInput [contour9 GetOutput];
  volMapper9 ScalarsVisibleOff;
vtkPolyMapper volMapper10;
  volMapper10 SetInput [contour10 GetOutput];
  volMapper10 ScalarsVisibleOff;



vtkActor volActor1;
  volActor1 SetMapper volMapper1;
vtkActor volActor2;
  volActor2 SetMapper volMapper2;
  volActor2 VisibilityOff;
vtkActor volActor3;
  volActor3 SetMapper volMapper3;
  volActor3 VisibilityOff;
vtkActor volActor4;
  volActor4 SetMapper volMapper4;
  volActor4 VisibilityOff;
vtkActor volActor5;
  volActor5 SetMapper volMapper5;
  volActor5 VisibilityOff;
vtkActor volActor6;
  volActor6 SetMapper volMapper6;
  volActor6 VisibilityOff;
vtkActor volActor7;
  volActor7 SetMapper volMapper7;
  volActor7 VisibilityOff;
vtkActor volActor8;
  volActor8 SetMapper volMapper8;
  volActor8 VisibilityOff;
vtkActor volActor9;
  volActor9 SetMapper volMapper9;
  volActor9 VisibilityOff;
vtkActor volActor10;
  volActor10 SetMapper volMapper10;
  volActor10 VisibilityOff;
  

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors outActor;

$ren1 AddActors volActor1;
$ren1 AddActors volActor2;
$ren1 AddActors volActor3;
$ren1 AddActors volActor4;
$ren1 AddActors volActor5;
$ren1 AddActors volActor6;
$ren1 AddActors volActor7;
$ren1 AddActors volActor8;
$ren1 AddActors volActor9;
$ren1 AddActors volActor10;


$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

volActor1 VisibilityOff;
volActor2 VisibilityOn;
$renWin Render;
volActor2 VisibilityOff;
volActor3 VisibilityOn;
$renWin Render;
volActor3 VisibilityOff;
volActor4 VisibilityOn;
$renWin Render;
volActor4 VisibilityOff;
volActor5 VisibilityOn;
$renWin Render;
volActor5 VisibilityOff;
volActor6 VisibilityOn;
$renWin Render;
volActor6 VisibilityOff;
volActor7 VisibilityOn;
$renWin Render;
volActor7 VisibilityOff;
volActor8 VisibilityOn;
$renWin Render;
volActor8 VisibilityOff;
volActor9 VisibilityOn;
$renWin Render;
volActor9 VisibilityOff;
volActor10 VisibilityOn;
$renWin Render;
