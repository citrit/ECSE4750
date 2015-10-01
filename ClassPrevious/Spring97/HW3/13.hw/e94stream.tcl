# Uncomment this line if using Vtk 1.3 and Win95
# load vtktcl;

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

vtkPointSource ptsrc1;
	ptsrc1 SetNumberOfPoints 25;
	eval ptsrc1 SetCenter 3.7 0.0 28.37;
	ptsrc1 SetRadius 0.5;
vtkStreamLine strLine1; 
	strLine1 SetInput [pl3d GetOutput];
	strLine1 SetSource [ptsrc1 GetOutput];
	strLine1 SetStepLength 0.1;
	strLine1 SetMaximumPropagationTime 500;
	strLine1 Update;
vtkPolyMapper streamMapper1;
    streamMapper1 SetInput [strLine1 GetOutput];
    eval streamMapper1 SetScalarRange [[pl3d GetOutput] GetScalarRange];    
vtkActor streamActor1;
    streamActor1 SetMapper streamMapper1;


vtkPointSource ptsrc2;
	ptsrc2 SetNumberOfPoints 25;
	eval ptsrc2 SetCenter 9.2 0.0 31.20;
	ptsrc2 SetRadius 0.5;
vtkStreamLine strLine2; # DataSetToPoly
	strLine2 SetInput [pl3d GetOutput];
	strLine2 SetSource [ptsrc2 GetOutput];
	strLine2 SetStepLength 0.1;
	strLine2 SetMaximumPropagationTime 500;
	strLine2 Update;
vtkPolyMapper streamMapper2;
    streamMapper2 SetInput [strLine2 GetOutput];
    eval streamMapper2 SetScalarRange [[pl3d GetOutput] GetScalarRange];    
vtkActor streamActor2;
    streamActor2 SetMapper streamMapper2;


vtkPointSource ptsrc3;
	ptsrc3 SetNumberOfPoints 25;
	eval ptsrc3 SetCenter 13.27 0.0 33.30;
	ptsrc3 SetRadius 0.5;
vtkStreamLine strLine3;
	strLine3 SetInput [pl3d GetOutput];
	strLine3 SetSource [ptsrc3 GetOutput];
	strLine3 SetStepLength 0.1;
	strLine3 SetMaximumPropagationTime 500;
	strLine3 Update;
vtkPolyMapper streamMapper3;
    streamMapper3 SetInput [strLine3 GetOutput];
    eval streamMapper3 SetScalarRange [[pl3d GetOutput] GetScalarRange];    
vtkActor streamActor3;
    streamActor3 SetMapper streamMapper3;


vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors streamActor1;
$ren1 AddActors streamActor2;
$ren1 AddActors streamActor3;

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

