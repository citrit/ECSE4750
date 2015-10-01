# Brutus Youn
# Graphics and Visualization
# HW#3
# Streamline Filter

load vtktcl;
# get the interactor ui
source vtkInt.tcl

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
    pl3d SetXYZFilename "combxyz.bin"
    pl3d SetQFilename "combq.bin"
    pl3d SetScalarFunctionNumber 100;
    pl3d SetVectorFunctionNumber 202;
    pl3d Update;

vtkPointSource seeds;
    seeds SetRadius 0.075; 
    eval seeds SetCenter 2.5 0.0 30.0;
    seeds SetNumberOfPoints 25;          

#vtkProbeFilter probe;
#    probe SetInput [seeds GetOutput];
#    probe SetSource [pl3d GetOutput];

#vtkStreamLine contour;
#    contour SetInput [pl3d GetOutput];
#    contour SetSource [seeds GetOutput];
#    contour SetMaximumPropagationTime 500;
#    contour SetStepLength 0.5;

#vtkPolyMapper contourMapper;
#    contourMapper SetInput [contour GetOutput];
#    eval contourMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];

#vtkActor planeActor;
#    planeActor SetMapper contourMapper;


vtkStreamLine streamers;
    streamers SetInput [pl3d GetOutput];
    streamers SetSource [seeds GetOutput];
    streamers SetMaximumPropagationTime 500;
    streamers SetStepLength 0.1;
    streamers Update;
vtkPolyMapper mapStreamers;
    mapStreamers SetInput [streamers GetOutput];
    eval mapStreamers SetScalarRange \
       [[[[pl3d GetOutput] GetPointData] GetScalars] GetRange];
vtkActor streamersActor;
    streamersActor SetMapper mapStreamers;

vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors streamersActor;
#$ren1 AddActors planeActor;
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

# prevent the tk window from showing up then start the event loop
wm withdraw .



