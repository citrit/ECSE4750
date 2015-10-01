#Homework No 3: Advanced Computer Graphics & Data Visualization
#Kanwaljit Anand - ID#: 185-74-8536
#Problem 6.4(a): Decimating the Quad function

source vtkInt.tcl

vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;

vtkSampleFunction sample;
  sample SetSampleDimensions 25 25 25;
  sample SetImplicitFunction quad;

vtkActor contActor1;  
vtkActor contActor2;  
vtkActor contActor3;  
vtkActor contActor4;  
  
vtkPolyMapper contMapper1;
vtkPolyMapper contMapper2;
vtkPolyMapper contMapper3;
vtkPolyMapper contMapper4;

vtkContourFilter contours1;
vtkContourFilter contours2;
vtkContourFilter contours3;
vtkContourFilter contours4;

vtkDecimate Decimate1;
vtkDecimate Decimate2;
vtkDecimate Decimate3;
vtkDecimate Decimate4;

$ren1 SetBackground 1 1 1;

contours1 SetInput [sample GetOutput];
contours1 GenerateValues 3 0.0 0.5;
Decimate1 SetInput [contours1 GetOutput];
Decimate1 SetMaximumIterations 10;
Decimate1 SetTargetReduction 0.5;
Decimate1 SetMaximumFeatureAngle 30;
Decimate1 SetInitialError 0.0;
Decimate1 SetErrorIncrement 0.001;
Decimate1 SetMaximumError 0.1;

contMapper1 SetInput [Decimate1 GetOutput];
contMapper1 SetScalarRange 0.0 3.0;
contActor1 SetMapper contMapper1;

contours2 SetInput [sample GetOutput];
contours2 GenerateValues 5 0.0 0.7;  
Decimate2 SetInput [contours2 GetOutput];
Decimate2 SetMaximumIterations 10;
Decimate2 SetTargetReduction 0.5;
Decimate2 SetMaximumFeatureAngle 30;
Decimate2 SetInitialError 0.0;
Decimate2 SetErrorIncrement 0.001;
Decimate2 SetMaximumError 0.1;

contMapper2 SetInput [Decimate2 GetOutput];
contMapper2 SetScalarRange 0.0 3.0;
contActor2 SetMapper contMapper2;

contours3 SetInput [sample GetOutput];
contours3 GenerateValues 7 0.0 0.9;  
Decimate3 SetInput [contours3 GetOutput];
Decimate3 SetMaximumIterations 10;
Decimate3 SetTargetReduction 0.5;
Decimate3 SetMaximumFeatureAngle 30;
Decimate3 SetInitialError 0.0;
Decimate3 SetErrorIncrement 0.001;
Decimate3 SetMaximumError 0.1;

contMapper3 SetInput [Decimate3 GetOutput];
contMapper3 SetScalarRange 0.0 3.0;
contActor3 SetMapper contMapper3;

contours4 SetInput [sample GetOutput];
contours4 GenerateValues 9 0.0 1.1;  
Decimate4 SetInput [contours4 GetOutput];
Decimate4 SetMaximumIterations 10;
Decimate4 SetTargetReduction 0.5;
Decimate4 SetMaximumFeatureAngle 30;
Decimate4 SetInitialError 0.0;
Decimate4 SetErrorIncrement 0.001;
Decimate4 SetMaximumError 0.1;

contMapper4 SetInput [Decimate4 GetOutput];
contMapper4 SetScalarRange 0.0 3.0;
contActor4 SetMapper contMapper4;

$ren1 AddActors contActor1;
$ren1 AddActors contActor2;
$ren1 AddActors contActor3;
$ren1 AddActors contActor4;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

#	puts "All actors are off to begin with"
	contActor1 VisibilityOff;	
	contActor2 VisibilityOff;
	contActor3 VisibilityOff;
	contActor4 VisibilityOff;
	contActor1 VisibilityOn;	
	$renWin Render;

	puts "\n\tStarting Rendering Loop ... use Control-C to exit\n"

for {set counter 1} {$counter > 0} {incr counter} {

	puts "Turning actor #1 on now"	
	
	contActor2 VisibilityOff;
	contActor1 VisibilityOn;
	$renWin Render;
	
	puts "Turning actor #2 on now"	

	contActor1 VisibilityOff;	
	contActor2 VisibilityOn;
	$renWin Render;
	
	puts "Turning actor #3 on now"	

	contActor2 VisibilityOff;
	contActor3 VisibilityOn;
	$renWin Render;
	
	puts "Turning actor #4 on now"	

	contActor3 VisibilityOff;
	contActor4 VisibilityOn;
	$renWin Render;
	
	puts "Turning actor #3 on now"	

	contActor4 VisibilityOff;
	contActor3 VisibilityOn;
	$renWin Render;
	
	puts "Turning actor #2 on now"	

	contActor3 VisibilityOff;
	contActor2 VisibilityOn;
	$renWin Render;	
}	

# prevent the tk window from showing up then start the event loop
wm withdraw .

