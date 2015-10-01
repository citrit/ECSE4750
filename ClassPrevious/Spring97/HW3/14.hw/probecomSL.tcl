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
    pl3d SetXYZFilename "/home/62/citrit/public/Spring97/examples/Vtk/COMBXYZ.BIN"
    pl3d SetQFilename "/home/62/citrit/public/Spring97/examples/Vtk/COMBQ.BIN"
    pl3d SetScalarFunctionNumber 100;
    pl3d SetVectorFunctionNumber 202;
    pl3d Update;

# Create source for streamlines
vtkPointSource seeds;
    seeds SetRadius 4.75;
    eval seeds SetCenter 6 6 6;
    seeds SetNumberOfPoints 45;
vtkStreamLine streamers;
    streamers SetInput [pl3d GetOutput];
    streamers SetSource [seeds GetOutput];
    streamers SetStartPosition 6 6 6;
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



