# Yuping Chen
# HW3#3 Mhw3.tcl - MRIhead w/ decimation
# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

# get the interactor ui
source /campus/visualization/vtk/1.3/common/examplesTcl/vtkInt.tcl

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

vtkDecimate deci; 
    deci SetInput [contour GetOutput];
    deci SetTargetReduction 0.9;

#vtkConnectivityFilter connect;
#    connect SetInput [deci GetOutput];
#    connect ExtractLargestRegion;
    
#vtkPolyNormals normals;
#    normals SetInput [connect GetOutput];

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
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

set isovalue 225.0;
# loop over iso-surfaces
for {set nloops 0} {$nloops < 20} {incr nloops} {
      set value [expr $nloops*($isovalue/20)];
#      set value [expr $isovalue-10];
      contour SetInput [vol GetOutput];
      contour GenerateValues 0 0.0 $value;
      $renWin Render;
}
# prevent the tk window from showing up then start the event loop
wm withdraw .

