#================================================
# Author: Elaine D'Souza
# HW#3	Date: Apr14,'97
# Using vtkProbeFilter and vtkStreamLine
#================================================

# uncommet the line below if you are on Windows95
# load vtktcl;
# get the interactor ui
source vtkint.tcl

# create planes
# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer and both Actors
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# create pipeline
#
vtkPLOT3DReader pl3d;
    pl3d SetXYZFilename "COMBXYZ.BIN"
    pl3d SetQFilename "COMBQ.BIN"
    pl3d SetScalarFunctionNumber 100;
    pl3d SetVectorFunctionNumber 202;
    pl3d Update;

vtkPlaneSource plane;
    plane SetResolution 10 10;

# Create source for streamtubes
vtkPointSource seeds;
    seeds SetRadius 0.75;
    eval seeds SetCenter 3.7 0.0 28.37
    seeds SetNumberOfPoints 25;

vtkStreamLine streamer;
   streamer SetInput [pl3d GetOutput];
	 streamer SetSource [seeds GetOutput];
	 streamer SetMaximumPropagationTime 100;
	 streamer SpeedScalarsOn;
	 streamer SetIntegrationStepLength 0.005;
	 streamer SetStepLength 0.175;
	 streamer Update;
vtkPolyMapper slMapper;
    slMapper SetInput [streamer GetOutput];
    eval slMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];
vtkActor slActor;
    slActor SetMapper slMapper;
	 
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];

vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;
    
puts " adding actors to ren1"

$ren1 AddActors outlineActor;
$ren1 AddActors slActor;
$ren1 SetBackground 1 1 1;
$renWin SetSize 500 500;
$iren Initialize;

set cam1 [$ren1 GetActiveCamera];
$cam1 SetClippingRange 3.95297 50;
$cam1 SetFocalPoint 8.88908 0.595038 29.3342;
$cam1 SetPosition -12.3332 31.7479 41.2387;
$cam1 CalcViewPlaneNormal;
$cam1 SetViewUp 0.060772 -0.319905 0.945498;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

puts "YEAH! success"

# prevent the tk window from showing up then start the event loop
wm withdraw .
