#Homework No 3: Advanced Computer Graphics & Data Visualization
#Kanwaljit Anand - ID#: 185-74-8536
#Problem 6.4(b): Decimated-Animation the MRIHead.vtk dataset

source vtkInt.tcl

vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

# Load the dataset
vtkStructuredPointsReader vol;
    vol SetFilename "MRIdata.vtk"

vtkTransform trans;
    trans RotateX 180;
 
vtkMarchingCubes contour1;
  contour1 SetInput [vol GetOutput];
  contour1 SetValue 0 200.0;

vtkTransformPolyFilter tpf1
    tpf1 SetInput [contour1 GetOutput];
    tpf1 SetTransform trans;
    
    puts "\n\tComputing Decimating"

vtkDecimate Decimate1
	Decimate1 SetInput [tpf1 GetOutput];
	Decimate1 SetMaximumIterations 10;
	Decimate1 SetTargetReduction 0.5;
	Decimate1 SetMaximumFeatureAngle 30;
	Decimate1 SetInitialError 0.0;
	Decimate1 SetErrorIncrement 0.001;
	Decimate1 SetMaximumError 0.5;

vtkMarchingCubes contour2;
  contour2 SetInput [vol GetOutput];
  contour2 SetValue 0 400.0;

vtkTransformPolyFilter tpf2
    tpf2 SetInput [contour2 GetOutput];
    tpf2 SetTransform trans;

vtkDecimate Decimate2
	Decimate2 SetInput [tpf2 GetOutput];
	Decimate2 SetMaximumIterations 10;
	Decimate2 SetTargetReduction 0.5;
	Decimate2 SetMaximumFeatureAngle 30;
	Decimate2 SetInitialError 0.0;
	Decimate2 SetErrorIncrement 0.001;
	Decimate2 SetMaximumError 0.5;

vtkMarchingCubes contour3;
  contour3 SetInput [vol GetOutput];
  contour3 SetValue 0 600.0;

vtkTransformPolyFilter tpf3
    tpf3 SetInput [contour3 GetOutput];
    tpf3 SetTransform trans;

vtkDecimate Decimate3
	Decimate3 SetInput [tpf3 GetOutput];
	Decimate3 SetMaximumIterations 10;
	Decimate3 SetTargetReduction 0.5;
	Decimate3 SetMaximumFeatureAngle 30;
	Decimate3 SetInitialError 0.0;
	Decimate3 SetErrorIncrement 0.001;
	Decimate3 SetMaximumError 0.5;

vtkMarchingCubes contour4;
  contour4 SetInput [vol GetOutput];
  contour4 SetValue 0 800.0;

vtkTransformPolyFilter tpf4
    tpf4 SetInput [contour4 GetOutput];
    tpf4 SetTransform trans;

vtkDecimate Decimate4
	Decimate4 SetInput [tpf1 GetOutput];
	Decimate4 SetMaximumIterations 10;
	Decimate4 SetTargetReduction 0.5;
	Decimate4 SetMaximumFeatureAngle 30;
	Decimate4 SetInitialError 0.0;
	Decimate4 SetErrorIncrement 0.001;
	Decimate4 SetMaximumError 0.5;
	
	puts "\tSending contents to mapper\n"

vtkPolyMapper volMapper1;
  volMapper1 SetInput [Decimate1 GetOutput];
  volMapper1 ScalarsVisibleOff;

vtkPolyMapper volMapper2;
  volMapper2 SetInput [Decimate2 GetOutput];
  volMapper2 ScalarsVisibleOff;

vtkPolyMapper volMapper3;
  volMapper3 SetInput [Decimate3 GetOutput];
  volMapper3 ScalarsVisibleOff;

vtkPolyMapper volMapper4;
  volMapper4 SetInput [Decimate4 GetOutput];
  volMapper4 ScalarsVisibleOff;

vtkActor volActor1;
  volActor1 SetMapper volMapper1;

vtkActor volActor2;
  volActor2 SetMapper volMapper2;

vtkActor volActor3;
  volActor3 SetMapper volMapper3;

vtkActor volActor4;
  volActor4 SetMapper volMapper4;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkTransformPolyFilter out
    out SetInput [outLine GetOutput];
    out SetTransform trans;

vtkPolyMapper outMapper;
  outMapper SetInput [out GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

$ren1 AddActors outActor;
$ren1 AddActors volActor1;
$ren1 AddActors volActor2;
$ren1 AddActors volActor3;
$ren1 AddActors volActor4;

	puts "\tInitializing Renderer"

$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

#all are off to begin with:
volActor1 VisibilityOff;	
volActor2 VisibilityOff;
volActor3 VisibilityOff;
volActor4 VisibilityOff;
volActor1 VisibilityOn;	
$renWin Render;

	puts "\n\tEntering Rendering loop ... use Control-C to quit\n"

for {set counter 1} {$counter > 0} {incr counter} {

	puts "Displaying iso-surface #1 now ..."	
	
	volActor2 VisibilityOff;	
	volActor1 VisibilityOn;
	$renWin Render;
	
	puts "Displaying iso-surface #2 now ..."	
	volActor1 VisibilityOff;	
	volActor2 VisibilityOn;
	$renWin Render;
	
	puts "Displaying iso-surface #3 now ..."	

	volActor2 VisibilityOff;
	volActor3 VisibilityOn;
	$renWin Render;
	
	puts "Displaying iso-surface #4 now ..."	

	volActor3 VisibilityOff;
	volActor4 VisibilityOn;
	$renWin Render;
	
	puts "Displaying iso-surface #3 now ..."	

	volActor4 VisibilityOff;
	volActor3 VisibilityOn;
	$renWin Render;	

	puts "Displaying iso-surface #2 now ..."	

	volActor3 VisibilityOff;	
	volActor2 VisibilityOn;
	$renWin Render;	
}	

# render the image
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;
wm withdraw .

