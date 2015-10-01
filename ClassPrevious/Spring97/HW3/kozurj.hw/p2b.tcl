# tcl file for Graphics Project 3, problem 9.4b
# John P Kozura

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

#vtkPlaneSource plane;
#    plane SetResolution 50 50;
# Here we generate the point source representation

vtkPointSource ps1;
	ps1 SetNumberOfPoints 50;
	ps1 SetRadius 0.5;
	eval ps1 SetCenter 3.7 0.0 28.37;
vtkStreamLine sline1; 
	sline1 SetInput [pl3d GetOutput];
	sline1 SetSource [ps1 GetOutput];
	sline1 SetStepLength 0.1;
	sline1 Update;
vtkPolyMapper pmapper1;
    pmapper1 SetInput [sline1 GetOutput];
    eval pmapper1 SetScalarRange [[pl3d GetOutput] GetScalarRange];    
vtkActor stream1;
    stream1 SetMapper pmapper1;
$ren1 AddActors stream1;


vtkPointSource ps2;
	ps2 SetNumberOfPoints 50;
	ps2 SetRadius 0.5;
	eval ps2 SetCenter 9.2 0.0 31.20;
vtkStreamLine sline2; # DataSetToPoly
	sline2 SetInput [pl3d GetOutput];
	sline2 SetSource [ps2 GetOutput];
	sline2 SetStepLength 0.1;
	sline2 Update;
vtkPolyMapper pmapper2;
    pmapper2 SetInput [sline2 GetOutput];
    eval pmapper2 SetScalarRange [[pl3d GetOutput] GetScalarRange];    
vtkActor stream2;
    stream2 SetMapper pmapper2;
$ren1 AddActors stream2;

vtkPointSource ps3;
	ps3 SetNumberOfPoints 50;
	ps3 SetRadius 0.5;
	eval ps3 SetCenter 13.27 0.0 33.30;
vtkStreamLine sline3;
	sline3 SetInput [pl3d GetOutput];
	sline3 SetSource [ps3 GetOutput];
	sline3 SetStepLength 0.1;
	sline3 Update;
vtkPolyMapper pmapper3;
    pmapper3 SetInput [sline3 GetOutput];
    eval pmapper3 SetScalarRange [[pl3d GetOutput] GetScalarRange];    
vtkActor stream3;
    stream3 SetMapper pmapper3;
$ren1 AddActors stream3;

# no more of this plane old contour filter garbage :)

vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
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



