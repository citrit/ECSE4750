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

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

$ren1 AddActors outActor;

vtkMarchingCubes contour;
vtkPolyMapper volMapper;
vtkActor volActor;
  contour SetInput [vol GetOutput];
for {set count 100} {$count < 500} {incr count 100} {
  contour SetValue 0 $count;
  volMapper SetInput [contour GetOutput];
  volMapper ScalarsVisibleOff;
  volActor SetMapper volMapper;


# Add the actors to the renderer, set the background and size
#

$ren1 AddActors volActor;
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;
}
# prevent the tk window from showing up then start the event loop
wm withdraw .

