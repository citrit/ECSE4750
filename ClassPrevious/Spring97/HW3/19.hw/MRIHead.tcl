# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

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
  contour SetValue 0 225.0;

vtkPolyNormals normals;
  normals SetInput [contour GetOutput];
  normals SetFeatureAngle 60;

vtkDecimate decimator;
  decimator SetInput [normals GetOutput];

vtkPolyMapper volMapper;
  volMapper SetInput [decimator GetOutput];
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

# prevent the tk window from showing up then start the event loop
#wm withdraw .
button .start -text "Start Animation" -command {animate 100 1800 100}
pack .start
button .quit -text "Quit" -command exit
pack .quit -fill x


proc animate {lowVal highVal stepVal} {
  global renWin
  for {set currVal $lowVal} {$currVal <= $highVal} {set currVal [expr $currVal + $stepVal]} {
    contour SetValue 0 $currVal
    $renWin Render
    update
  }
}


