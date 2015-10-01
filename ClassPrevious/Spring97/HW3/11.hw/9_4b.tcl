#Homework No 3: Advanced Computer Graphics & Data Visualization
#Kanwaljit Anand - ID#: 185-74-8536
#Problem 9.4(b): Implementing the Probe and StreamLine filter.

puts "Implementing StreamLine Filter"
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
    plane SetResolution 10 10;

puts "\tGenerating vtkPointSource ... "

#using vtkPointSource to generate points:
vtkPointSource psource;
   psource SetRadius 0.80;
   eval psource SetCenter 3.5 2.0 26.0
   psource SetNumberOfPoints 100;
		
	puts "\tGenerating StreamLine"
vtkStreamLine streamL;
    streamL SetInput [pl3d GetOutput];
	 streamL SetSource [psource GetOutput];
	 streamL SetMaximumPropagationTime 100;
	 streamL SpeedScalarsOn;
	 streamL SetIntegrationStepLength 0.02;
	 streamL SetStepLength 0.02;
	 streamL Update;

	puts "\tGenerating StreamLine Mapper"

vtkPolyMapper slMapper;
    slMapper SetInput [streamL GetOutput];
    eval slMapper SetScalarRange [[psource GetOutput] GetScalarRange];

	puts "\tGenerating Actors"

vtkActor slActor;
    slActor SetMapper slMapper;
	 
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];

vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;
    
    puts "\tInitializing Interactive Window"

$ren1 AddActors outlineActor;
$ren1 AddActors slActor;
$ren1 SetBackground 1 1 1;
$renWin SetSize 500 500;
$iren Initialize;

puts "\tSetting up Camera"

set cam1 [$ren1 GetActiveCamera];
$cam1 SetClippingRange 3.95297 50;
$cam1 SetFocalPoint 8.88908 0.595038 29.3342;
$cam1 SetPosition -12.3332 31.7479 41.2387;
$cam1 CalcViewPlaneNormal;
$cam1 SetViewUp 0.060772 -0.319905 0.945498;

$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

puts "All complete\n"
wm withdraw .
