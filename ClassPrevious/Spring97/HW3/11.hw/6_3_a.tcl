#Homework No 3: Advanced Computer Graphics & Data Visualization
#Kanwaljit Anand - ID#: 185-74-8536
#Problem 6.4(a): Animating the Quad function

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

$ren1 SetBackground 1 1 1;

contours1 SetInput [sample GetOutput];
contours1 GenerateValues 3 0.0 0.5;
contMapper1 SetInput [contours1 GetOutput];
contMapper1 SetScalarRange 0.0 3.0;
contActor1 SetMapper contMapper1;

contours2 SetInput [sample GetOutput];
contours2 GenerateValues 5 0.0 0.7;  
contMapper2 SetInput [contours2 GetOutput];
contMapper2 SetScalarRange 0.0 3.0;
contActor2 SetMapper contMapper2;

contours3 SetInput [sample GetOutput];
contours3 GenerateValues 7 0.0 0.9;  
contMapper3 SetInput [contours3 GetOutput];
contMapper3 SetScalarRange 0.0 3.0;
contActor3 SetMapper contMapper3;

contours4 SetInput [sample GetOutput];
contours4 GenerateValues 9 0.0 1.1;  
contMapper4 SetInput [contours4 GetOutput];
contMapper4 SetScalarRange 0.0 3.0;
contActor4 SetMapper contMapper4;

$ren1 AddActors contActor1;
$ren1 AddActors contActor2;
$ren1 AddActors contActor3;
$ren1 AddActors contActor4;

# enable user interface interactor
	puts "Initializing renderer\n"
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

	puts "Displaying Iso-surface #1 now"	
	
	contActor2 VisibilityOff;
	contActor1 VisibilityOn;
	$renWin Render;
	
	puts "Displaying Iso-surface #2 now"	

	contActor1 VisibilityOff;	
	contActor2 VisibilityOn;
	$renWin Render;
	
	puts "Displaying Iso-surface #3 now"	

	contActor2 VisibilityOff;
	contActor3 VisibilityOn;
	$renWin Render;
	
	puts "Displaying Iso-surface #4 now"	

	contActor3 VisibilityOff;
	contActor4 VisibilityOn;
	$renWin Render;
	
	puts "Displaying Iso-surface #3 now"	

	contActor4 VisibilityOff;
	contActor3 VisibilityOn;
	$renWin Render;
	
	puts "Displaying Iso-surface #2 now"	

	contActor3 VisibilityOff;
	contActor2 VisibilityOn;
	$renWin Render;	
}	

# prevent the tk window from showing up then start the event loop
wm withdraw .

