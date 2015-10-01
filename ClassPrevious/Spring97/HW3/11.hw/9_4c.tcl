#Homework No 3: Advanced Computer Graphics & Data Visualization
#Kanwaljit Anand - ID#: 185-74-8536
#Problem 9.4(c): Implementing the Probe and Warp filter.

source vtkInt.tcl

vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# create pipeline
vtkPLOT3DReader pl3d;
    pl3d SetXYZFilename "combxyz.bin"
    pl3d SetQFilename "combq.bin"
    pl3d SetScalarFunctionNumber 100;
    pl3d SetVectorFunctionNumber 202;
    pl3d Update;

vtkPlaneSource plane;
    plane SetResolution 25 25;

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
    

vtkProbeFilter probe1;
    probe1 SetInput [tpd1 GetOutput];
    probe1 SetSource [pl3d GetOutput];

vtkConnectivityFilter contF1;
#This part converts the DataSet to Unstructured grid
	contF1 SetInput [probe1 GetOutput];
	contF1 ColorRegionsOn;
	contF1 ExtractLargestRegion;

vtkProbeFilter probe2;
    probe2 SetInput [tpd2 GetOutput];
    probe2 SetSource [pl3d GetOutput];

vtkConnectivityFilter contF2;
#This part converts the DataSet to Unstructured grid
	contF2 SetInput [probe2 GetOutput];
	contF2 ColorRegionsOn;
	contF2 ExtractLargestRegion;

vtkProbeFilter probe3;
    probe3 SetInput [tpd3 GetOutput];
    probe3 SetSource [pl3d GetOutput];

vtkConnectivityFilter contF3;
#This part converts the DataSet to Unstructured grid
	contF3 SetInput [probe3 GetOutput];
	contF3 ColorRegionsOn;
	contF3 ExtractLargestRegion;

vtkAppendFilter appendPF;
    appendPF AddInput [contF1 GetOutput];
    appendPF AddInput [contF2 GetOutput];
    appendPF AddInput [contF3 GetOutput];

vtkWarpVector warp;
#warp requires an input of PointSet & returns PointSet
    warp SetInput [appendPF GetOutput];
    warp SetScaleFactor .005;

vtkDataSetMapper warpMapper;
    warpMapper SetInput [warp GetOutput];
    eval warpMapper SetScalarRange [[pl3d GetOutput] GetScalarRange];
    
vtkActor warpActor;
    warpActor SetMapper warpMapper;
	 
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
    
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors warpActor;
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

$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;
wm withdraw .
