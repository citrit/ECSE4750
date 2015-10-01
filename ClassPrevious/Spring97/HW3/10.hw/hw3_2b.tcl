#  Laura Conway
#  Homework 3 Part 2
#  Exercise 9.4 b - Using vtkProbeFilter and vtkStreamLine
#
#uncomment the line below if you are on Windows95
# load vtktcl;
# get the interactor ui
source vtkInt.tcl
puts " "
puts "NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE"
puts " "
puts "I've had trouble on some systems running out of memory when I run this."
puts "It does work well on hawk.vlsc, but... "
puts "If this happens, remove the '#' before 'compPlane SetExtent 0 100 10 20 4 4' on line 40"
puts " "
puts " "


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
    pl3d SetXYZFilename "combxyz.bin"
    pl3d SetQFilename "combq.bin"
    pl3d SetScalarFunctionNumber 100;
    pl3d SetVectorFunctionNumber 202;
    pl3d Update;

vtkStructuredGridGeometryFilter compPlane;
	compPlane SetInput [pl3d GetOutput];
	compPlane SetExtent 0 100 0 100 4 4;
#	compPlane SetExtent 0 100 10 20 4 4;

vtkProbeFilter probe;
	probe SetInput [compPlane GetOutput];
	probe SetSource [pl3d GetOutput];
	probe DebugOn;

vtkStreamLine sl;
 	sl SetInput [probe GetOutput];
    sl SetSource [probe GetOutput];
    sl SetStepLength .5;
	sl SetMaximumPropagationTime 500;
	sl DebugOn;
	sl Update;

vtkPolyMapper slMapper;
    slMapper SetInput [sl GetOutput];
 	eval slMapper SetScalarRange [[[[pl3d GetOutput] GetPointData] GetScalars] GetRange];

vtkActor streamActor;
	streamActor SetMapper slMapper;

# Outline Box 
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors streamActor;
$ren1 SetBackground 1 1 1;
$renWin SetSize 500 500;
$iren Initialize;

set cam1 [$ren1 GetActiveCamera];
$cam1 SetClippingRange 3.95297 50;
$cam1 SetFocalPoint 8.88908 0.595038 29.3342;
$cam1 SetPosition -12.3332 31.7479 41.2387;
$cam1 CalcViewPlaneNormal;
$cam1 SetViewUp 0.060772 -0.319905 0.945498;
puts ""
puts ""
puts " This is exercise 9.4 b - Using vtkProbeFilter and vtkStreamLine  " 
puts ""

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

