# Homework 3 - Advanced Computer Graphics and Data Visualization
# Helio Pedrini
#
# Visualization of velocity flow in the combustor by using "vtkProbeFilter" and "vtkWarpVector"

# get the interactor ui
source /campus/visualization/vtk/1.1/common/examples/tcl/vtkInt.tcl

# create the render master
vtkRenderMaster rm;

# create the RenderWindow, Renderer and both Actors
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# create pipeline
vtkPLOT3DReader pl3d;
    pl3d SetXYZFilename "/home/62/citrit/public/Spring97/examples/Vtk/COMBXYZ.BIN"
    pl3d SetQFilename "/home/62/citrit/public/Spring97/examples/Vtk/COMBQ.BIN"
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

vtkPolyMapper mapTpd3;
    mapTpd3 SetInput [outTpd3 GetOutput];

vtkActor tpd3Actor;
    tpd3Actor SetMapper mapTpd3;
    [tpd3Actor GetProperty] SetColor 0 0 0;

vtkAppendPolyData appendF;
    appendF AddInput [tpd1 GetOutput];
    appendF AddInput [tpd2 GetOutput];
    appendF AddInput [tpd3 GetOutput];
       
vtkProbeFilter probe;
    probe SetInput [appendF GetOutput];
    probe SetSource [pl3d GetOutput];

# vtkGeometryFilter: extract geometry from data
vtkGeometryFilter ds2poly;
    ds2poly SetInput [probe GetOutput];

# vtkWarpVector: deform geometry with vector data
vtkWarpVector warpvector;
    warpvector SetInput [ds2poly GetOutput];
    warpvector SetScaleFactor 0.004;
     
# WarpVector Mapper
vtkDataSetMapper warpvectorMapper;
    warpvectorMapper SetInput [warpvector GetOutput];
    eval warpvectorMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];

# WarpVector Actor
vtkActor warpvectorActor;
    warpvectorActor SetMapper warpvectorMapper;
	 
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];

vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];

vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors warpvectorActor;
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
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .
