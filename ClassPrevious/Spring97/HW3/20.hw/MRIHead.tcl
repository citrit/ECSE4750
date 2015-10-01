# If you are using Window95 and vtk 1.3 uncomment the
# line below
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

vtkCleanPolyData clean;
  clean SetInput [contour GetOutput];

vtkDecimate deci;
  deci SetInput [clean GetOutput];
  deci SetTargetReduction 0.25;
  deci SetInitialFeatureAngle 30;

vtkPolyNormals norms;
  norms SetInput [deci GetOutput];

vtkConnectivityFilter connect;
  connect SetInput [norms GetOutput];
  connect ExtractLargestRegion;

vtkDataSetMapper volMapper;
  volMapper SetInput [connect GetOutput];
  volMapper ScalarsVisibleOff;

vtkActor volActor;
  volActor SetMapper volMapper;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

vtkCamera cam1;
  cam1 SetPosition 160 -31.5 -35;
  cam1 SetFocalPoint 19.5 31.5 31.5;
  cam1 CalcViewPlaneNormal;
  cam1 SetViewUp 0 -1 0;

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors outActor;
$ren1 AddActors volActor;
$ren1 SetActiveCamera cam1;
$ren1 SetBackground .5 .5 .7;
$renWin SetSize 400 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
for {set i 0} {$i < 8} {incr i} {
  contour SetValue 0 [expr 450.0 + 225.0 * $i];
  $renWin Render; }

# prevent the tk window from showing up then start the event loop
wm withdraw .

