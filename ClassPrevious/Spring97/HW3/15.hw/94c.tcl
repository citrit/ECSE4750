# : Gregory C. Tulumbas  -  ACG Homework #3, Part 9.4(c)
# :

# Uncomment the following line if using Window95/NT
load vtktcl;
# get the interactor ui
source vtkInt.tcl

vtkRenderMaster rm;

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

vtkStructuredGridGeometryFilter plane;
    plane SetInput [pl3d GetOutput];
    plane SetExtent 10 10 1 100 1 100;
vtkStructuredGridGeometryFilter plane2;
    plane2 SetInput [pl3d GetOutput];
    plane2 SetExtent 30 30 1 100 1 100;
vtkStructuredGridGeometryFilter plane3;
    plane3 SetInput [pl3d GetOutput];
    plane3 SetExtent 45 45 1 100 1 100;

vtkOutlineFilter outTpd;
    outTpd SetInput [plane GetOutput];
vtkPolyMapper mapTpd;
    mapTpd SetInput [outTpd GetOutput];
vtkActor tpdActor;
    tpdActor SetMapper mapTpd;
    [tpdActor GetProperty] SetColor 0 0 0;

vtkOutlineFilter outTpd2;
    outTpd2 SetInput [plane2 GetOutput];
vtkPolyMapper mapTpd2;
    mapTpd2 SetInput [outTpd2 GetOutput];
vtkActor tpd2Actor;
    tpd2Actor SetMapper mapTpd2;
    [tpd2Actor GetProperty] SetColor 0 0 0;

vtkOutlineFilter outTpd3;
    outTpd3 SetInput [plane3 GetOutput];
vtkPolyMapper mapTpd3;
    mapTpd3 SetInput [outTpd3 GetOutput];
vtkActor tpd3Actor;
    tpd3Actor SetMapper mapTpd3;
    [tpd3Actor GetProperty] SetColor 0 0 0;


vtkAppendPolyData appendF;
    appendF AddInput [plane GetOutput];
    appendF AddInput [plane2 GetOutput];
    appendF AddInput [plane3 GetOutput];

vtkWarpVector warp;
    warp SetInput [appendF GetOutput];
    warp SetScaleFactor 0.004;
vtkGeometryFilter ds2poly;
    ds2poly SetInput [warp GetOutput];
vtkCleanPolyData clean;
    clean SetInput [ds2poly GetOutput];
vtkDataSetMapper planeMapper;
    planeMapper SetInput [clean GetOutput];
vtkActor planeActor;
    planeActor SetMapper planeMapper;


vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors planeActor;
$ren1 AddActors tpdActor;
$ren1 AddActors tpd2Actor;
$ren1 AddActors tpd3Actor;
$ren1 SetBackground 1 1 1;
$renWin SetSize 500 500;

set cam1 [$ren1 GetActiveCamera];
$cam1 SetClippingRange 3.95297 50;
$cam1 SetFocalPoint 8.88908 0.595038 29.3342;
$cam1 SetPosition -12.3332 31.7479 41.2387;
$cam1 CalcViewPlaneNormal;
$cam1 SetViewUp 0.060772 -0.319905 0.945498;


# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .



