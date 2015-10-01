# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

# get the interactor ui
source vtkInt.tcl

set iteration 0;
set run 0;
set min 700;
set max 1300;
set delta 100;
set current $max;

source control.tcl

proc generate_contour { value } {

  contour SetValue 0 $value;

}


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
  contour SetValue 0 $max;

vtkPolyNormals norm
  norm SetInput [contour GetOutput];

vtkDecimate decimate
  decimate SetInput [norm GetOutput];
  decimate SetTargetReduction 0.7;
  decimate SetMaximumIterations 6;
#  decimate PreserveEdgesOn

vtkPolyMapper volMapper;
  volMapper SetInput [decimate GetOutput];
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

proc deci_on { } {
  global renWin run
  volMapper SetInput [decimate GetOutput];
  if { $run ==0 } {
    $renWin Render;
  }
}

proc deci_off { } {
  global renWin run 
  volMapper SetInput [contour GetOutput];
  if { $run ==0 } {
    $renWin Render;
  }
}

control_panel;

iterate;


