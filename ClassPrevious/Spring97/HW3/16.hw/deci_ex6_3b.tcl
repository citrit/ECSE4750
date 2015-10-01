# Homework 3 - Advanced Computer Graphics and Data Visualization
# Helio Pedrini
#
# Animation sequence for the MRIhead example including Decimation filter

# get the interactor ui
source /campus/visualization/vtk/1.1/common/examples/tcl/vtkInt.tcl

# create the render master
vtkRenderMaster rm;

# create the RenderWindow and Renderer
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

# load up the data
vtkStructuredPointsReader vol;
    vol SetFilename "/home/62/citrit/public/Spring97/examples/Vtk/MRIdata.vtk"
    vol DebugOn;

vtkMarchingCubes contour;
  contour SetInput [vol GetOutput];
  contour SetValue 0 225.0;

vtkDecimate decimation;
  decimation SetInput [contour GetOutput];
  decimation SetTargetReduction 0.9;
  
vtkPolyMapper volMapper;
  volMapper SetInput [decimation GetOutput];
  volMapper ScalarsVisibleOff;

vtkActor volActor;
  volActor SetMapper volMapper;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

# add the actors to the renderer, set the background and size
$ren1 AddActors outActor;
$ren1 AddActors volActor;
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;

# render the sequence of images, creating an animation
set value 225.0;
for {set iteration 0} {$iteration <= 4} {incr iteration 1} {
   contour GenerateValues $iteration 0.0 $value;
   set value [expr $value-10];
   $iren SetUserMethod {wm deiconify .vtkInteract};
   $renWin Render;
}

# prevent the tk window from showing up then start the event loop
wm withdraw .
