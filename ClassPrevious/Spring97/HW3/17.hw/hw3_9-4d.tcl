#
# Advanced Computer Graphics and Data Visualization
# Homework 3 - VectorNorm: 9.4(d)
# Jesse Booth
# April 15, 1997

# uncomment the line below if you are on Windows95
# load vtktcl;

# get the interactor ui
source vtkInt.tcl

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
    pl3d SetScalarFunctionNumber 153;  # velocity magnitude
    pl3d SetVectorFunctionNumber 200;  # velocity
    pl3d Update;

# extract data planes to use to make smashed vector planes
vtkStructuredGridGeometryFilter tpd1;
    tpd1 SetInput [pl3d GetOutput];
    tpd1 SetExtent 5 5 1 100 1 100;
vtkStructuredGridGeometryFilter tpd2;
    tpd2 SetInput [pl3d GetOutput];
    tpd2 SetExtent 30 30 1 100 1 100;
vtkStructuredGridGeometryFilter tpd3;
    tpd3 SetInput [pl3d GetOutput];
    tpd3 SetExtent 50 50 1 100 1 100;
vtkAppendPolyData appendF;
    appendF AddInput [tpd1 GetOutput];
    appendF AddInput [tpd2 GetOutput];
    appendF AddInput [tpd3 GetOutput];

#
# probe the three planes for data points
#
vtkProbeFilter probe;
    probe SetInput [appendF GetOutput];
    probe SetSource [pl3d GetOutput];

#
# create VectorNorm filter, create scalar planes from vectors
#
vtkVectorNorm vn;
    vn SetInput [probe GetOutput];
vtkDataSetMapper vnMapper;
    vnMapper SetInput [vn GetOutput];
    eval vnMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];
vtkActor vnActor;
    vnActor SetMapper vnMapper;

#
# construct outline of combustor
#
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 1 1 1;

$ren1 AddActors outlineActor;
$ren1 AddActors vnActor;
$ren1 SetBackground 0.1 0.1 0.1;
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
