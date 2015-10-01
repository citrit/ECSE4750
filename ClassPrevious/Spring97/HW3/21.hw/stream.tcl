# get the interactor ui
load vtktcl
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

vtkCubeSource cube;
    cube SetXLength 0.1;
    cube SetYLength 0.1;
    cube SetZLength 0.1;
vtkTransform transC1;
    transC1 Translate 3.7 0.0 28.37;
    transC1 Scale 5 5 5;
    transC1 RotateY 90;
vtkTransformPolyFilter tcube1
    tcube1 SetInput [cube GetOutput];
    tcube1 SetTransform transC1;
vtkOutlineFilter outTcube1;
    outTcube1 SetInput [tcube1 GetOutput];
vtkPolyMapper mapTcube1;
    mapTcube1 SetInput [outTcube1 GetOutput];
vtkActor tpd1Actor;
    tpd1Actor SetMapper mapTcube1;
    [tpd1Actor GetProperty] SetColor 0 0 0;

vtkProbeFilter probe;
    probe SetInput [tcube1 GetOutput];
    probe SetSource [pl3d GetOutput];

vtkStreamLine streamer;
    streamer SetInput [pl3d GetOutput];
    streamer SetSource [tcube1 GetOutput];
    streamer SetMaximumPropagationTime 500000;
    streamer SetStepLength 0.1;
    streamer Update;
vtkPolyMapper mapStream;
    mapStream SetInput [streamer GetOutput];
    eval mapStream SetScalarRange \
    [[[[pl3d GetOutput] GetPointData] GetScalars] GetRange];
vtkActor streamActor;
    streamActor SetMapper mapStream;


vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors streamActor;
$ren1 AddActors outlineActor;
#$ren1 AddActors planeActor;
$ren1 AddActors tpd1Actor;
#$ren1 AddActors tpd2Actor;
#$ren1 AddActors tpd3Actor;
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



