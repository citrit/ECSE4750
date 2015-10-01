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
    vol SetFilename "/home/62/citrit/public/Spring97/examples/Vtk/MRIdata.vtk"
#   vol DebugOn;

vtkMarchingCubes contour;
  contour SetInput [vol GetOutput];
  contour SetValue 0 250.0;

vtkConnectivityFilter connect;
    connect SetInput [contour GetOutput];
    connect ExtractLargestRegion;
vtkDataSetMapper volMapper;
    volMapper SetInput [connect GetOutput];
    volMapper ScalarsVisibleOff;

#vtkPolyMapper volMapper;
# volMapper SetInput [contour GetOutput];
# volMapper ScalarsVisibleOff;

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

for {set k 0} {$k < 2} {incr k} {
 for {set j 0} {$j < 20} {incr j} {
  set value [expr 35.0 + ($j)*(220.0/19.0)];
  contour SetValue 0 $value;
  $renWin Render;
 }
}


# prevent the tk window from showing up then start the event loop
wm withdraw .

