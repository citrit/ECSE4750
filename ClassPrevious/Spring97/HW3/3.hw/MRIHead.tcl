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
  contour SetValue 0 1500;

vtkDecimate deci;
    deci SetInput [contour GetOutput];
    deci SetTargetReduction 0.10;
    deci SetAspectRatio 20;
    deci SetInitialError 0.0002;
    deci SetErrorIncrement 0.0005;
    deci SetMaximumIterations 6;
    deci SetInitialFeatureAngle 45;

vtkPolyMapper volMapper;
  volMapper SetInput [deci GetOutput];
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
set cam1 [$ren1 GetActiveCamera];
$cam1 Elevation 200;
$cam1 Azimuth 15;
$cam1 Roll 180;
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

for {set i 1400} {$i>100} {incr i -100} {
eval contour SetValue 0 $i;
$renWin Render;
}