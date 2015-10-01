#Yuping Chen
#HW#3 pStr.tcl 9.4b
#
#unommet the line below if you are on Windows95
# load vtktcl;
# get the interactor ui
source /campus/visualization/vtk/1.3/common/examplesTcl/vtkInt.tcl

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
    
vtkPlaneSource plane;
    plane SetResolution 50 50;
vtkTransform transP1;
    transP1 Translate 3.7 0.0 28.37;
    transP1 Scale 5 5 5;
    transP1 RotateY 90;
vtkTransformPolyFilter tpd1
    tpd1 SetInput [plane GetOutput];
    tpd1 SetTransform transP1;
vtkOutlineFilter outTpd1;
    outTpd1 SetInput [tpd1 GetOutput];
vtkPolyMapper mapTpd1;
    mapTpd1 SetInput [outTpd1 GetOutput];
vtkActor tpd1Actor;
    tpd1Actor SetMapper mapTpd1;
    [tpd1Actor GetProperty] SetColor 0 0 0;

vtkTransform transP2;
    transP2 Translate 9.2 0.0 31.20;
    transP2 Scale 5 5 5;
    transP2 RotateY 90;
vtkTransformPolyFilter tpd2
    tpd2 SetInput [plane GetOutput];
    tpd2 SetTransform transP2;
vtkOutlineFilter outTpd2;
    outTpd2 SetInput [tpd2 GetOutput];
vtkPolyMapper mapTpd2;
    mapTpd2 SetInput [outTpd2 GetOutput];
vtkActor tpd2Actor;
    tpd2Actor SetMapper mapTpd2;
    [tpd2Actor GetProperty] SetColor 0 0 0;

vtkTransform transP3;
    transP3 Translate 13.27 0.0 33.30;
    transP3 Scale 5 5 5;
    transP3 RotateY 90;
vtkTransformPolyFilter tpd3
    tpd3 SetInput [plane GetOutput];
    tpd3 SetTransform transP3;
vtkOutlineFilter outTpd3;
    outTpd3 SetInput [tpd3 GetOutput];
vtkDataSetMapper mapTpd3;
    mapTpd3 SetInput [outTpd3 GetOutput];
vtkActor tpd3Actor;
    tpd3Actor SetMapper mapTpd3;
    [tpd3Actor GetProperty] SetColor 0 0 0;

vtkAppendPolyData appendF;
    appendF AddInput [tpd1 GetOutput];
    appendF AddInput [tpd2 GetOutput];
    appendF AddInput [tpd3 GetOutput];

#vtkProbeFilter probe;
#    probe SetInput [appendF GetOutput];
#    probe SetSource [pl3d GetOutput];
#vtkContourFilter contour;
#    contour SetInput [probe GetOutput];
#    eval contour GenerateValues 50 [[pl3d GetOutput] GetScalarRange];
##### HW#3 9.4 StreamLine

# Create source for streamtubes
vtkPointSource seeds1;
    eval seeds1 SetCenter 3.7 0.0 28.37;
    seeds1 SetRadius 0.6;    
    seeds1 SetNumberOfPoints 300;

vtkPointSource seeds2;
    eval seeds2 SetCenter 9.2 0.0 31.20;
    seeds2 SetRadius 0.9;    
    seeds2 SetNumberOfPoints 500;

vtkPointSource seeds3;
    eval seeds3 SetCenter 13.27 0.0 33.3;
    seeds3 SetRadius 0.8;    
    seeds3 SetNumberOfPoints 400;

vtkAppendFilter seeds;
    seeds AddInput [seeds1 GetOutput];
    seeds AddInput [seeds2 GetOutput];
    seeds AddInput [seeds3 GetOutput];

vtkStreamLine streamers;
    streamers SetInput [pl3d GetOutput];
    streamers SetSource [seeds GetOutput];
    streamers SetMaximumPropagationTime 100;
    streamers SetStepLength 0.1;
    streamers Update;

vtkPolyMapper streamMapper;
    streamMapper SetInput [streamers GetOutput];
    streamMapper SetScalarRange 2 10;
    eval streamMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];

vtkActor planeActor;
    planeActor SetMapper streamMapper;

vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors planeActor;
$ren1 AddActors tpd1Actor;
$ren1 AddActors tpd2Actor;
$ren1 AddActors tpd3Actor;
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



