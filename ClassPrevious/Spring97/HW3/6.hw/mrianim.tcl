# Brutus Youn
# Graphics and Visualization
# HW#3
# MRIHead Scan Animation

# If you are using Window95 and vtk 1.3 uncomment the line below
load vtktcl;

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

vtkMarchingCubes contour;
  contour SetInput [vol GetOutput];
  contour SetValue 1 2000.0;

vtkPolyMapper volMapper;
  volMapper SetInput [contour GetOutput];
  volMapper ScalarsVisibleOff;

vtkActor volActor;
  volActor SetMapper volMapper;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors outActor;
$ren1 AddActors volActor;
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

  contour SetValue 1 1900.0;
  $renWin Render;
  contour SetValue 1 1800.0;
  $renWin Render;
  contour SetValue 1 1700.0;
  $renWin Render;
  contour SetValue 1 1600.0;
  $renWin Render;
  contour SetValue 1 1500.0;
  $renWin Render;
  contour SetValue 1 1400.0;
  $renWin Render;
  contour SetValue 1 1300.0;
  $renWin Render;
  contour SetValue 1 1200.0;
  $renWin Render;
  contour SetValue 1 1100.0;
  $renWin Render;
  contour SetValue 1 1000.0;
  $renWin Render;
  contour SetValue 1 900.0;
  $renWin Render;
  contour SetValue 1 800.0;
  $renWin Render;
  contour SetValue 1 700.0;
  $renWin Render;
  contour SetValue 1 600.0;
  $renWin Render;
  contour SetValue 1 500.0;
  $renWin Render;
  contour SetValue 1 400.0;
  $renWin Render;
  contour SetValue 1 300.0;
  $renWin Render;
  contour SetValue 1 225.0;
  $renWin Render;
# prevent the tk window from showing up then start the event loop
wm withdraw .

