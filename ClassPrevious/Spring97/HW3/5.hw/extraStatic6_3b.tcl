#================================================
# Author: Elaine D'Souza
# HW#3	Date: Apr14,'97
# Using vtkDecimate on the MRIHead
#================================================

# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

# get the interactor ui
source vtkint.tcl

# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

# Load up the data
vtkStructuredPointsReader vol;
    vol SetFilename "MRIdata.vtk"

vtkTransform trans;
    trans RotateX 180;
 
vtkMarchingCubes cont1;
  cont1 SetInput [vol GetOutput];
  cont1 SetValue 0 500.0;
vtkTransformPolyFilter tpc1
    tpc1 SetInput [cont1 GetOutput];
    tpc1 SetTransform trans;
vtkDecimate deci1;
	deci1 SetInput [tpc1 GetOutput];
	deci1 SetMaximumIterations 10
	deci1 SetTargetReduction 0.9
	deci1 SetMaximumFeatureAngle 30
	deci1 SetInitialError 0.0
	deci1 SetErrorIncrement 0.01
	deci1 SetMaximumError 0.1
vtkPolyMapper volMapper1;
  volMapper1 SetInput [deci1 GetOutput];
  volMapper1 ScalarsVisibleOff;
vtkActor volActor1;
  volActor1 SetMapper volMapper1;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];
vtkTransformPolyFilter tpout
    tpout SetInput [outLine GetOutput];
    tpout SetTransform trans;
vtkPolyMapper outMapper;
  outMapper SetInput [tpout GetOutput];
vtkActor outActor;
  outActor SetMapper outMapper;

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors outActor;
$ren1 AddActors volActor1;

$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

