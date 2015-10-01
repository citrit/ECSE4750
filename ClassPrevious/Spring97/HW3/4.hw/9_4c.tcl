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
    pl3d SetScalarFunctionNumber 153;
    pl3d SetVectorFunctionNumber 200;
    pl3d Update;


#create three probe planes and collect them
#
vtkStructuredGridGeometryFilter plane1;
    plane1 SetInput [pl3d GetOutput];
    plane1 SetExtent 5 5 1 100 1 100;
vtkStructuredGridGeometryFilter plane2;
    plane2 SetInput [pl3d GetOutput];
    plane2 SetExtent 30 30 1 100 1 100;
vtkStructuredGridGeometryFilter plane3;
    plane3 SetInput [pl3d GetOutput];
    plane3 SetExtent 55 55 1 100 1 100;
vtkAppendPolyData appendF;
    appendF AddInput [plane1 GetOutput];
    appendF AddInput [plane2 GetOutput];
    appendF AddInput [plane3 GetOutput];


# now warp the three planes using velocity vector
# information
vtkWarpVector warp;
  warp SetInput [appendF GetOutput];
  warp SetScaleFactor 0.001;
vtkGeometryFilter dataSet2Poly;
  dataSet2Poly SetInput [warp GetOutput];
vtkCleanPolyData cleaner;
  cleaner SetInput [dataSet2Poly GetOutput];
vtkPolyNormals normals;
  normals SetInput [cleaner GetOutput];
  normals SetFeatureAngle 60;
vtkDataSetMapper planeMapper;
  planeMapper SetInput [normals GetOutput];
  eval planeMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];
vtkActor planeActor;
  planeActor SetMapper planeMapper;

# create the wireframe outline of the combustor
#
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 1 1 1;

$ren1 AddActors outlineActor;
$ren1 AddActors planeActor;
$ren1 SetBackground 0 0 0.5;
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



