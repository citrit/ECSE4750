#================================================
# Author: Elaine D'Souza
# HW#3	Date: Apr14,'97
# Using vtkDecimate on the Quadric function
# Animation sequence
#================================================

source vtkint.tcl

vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

$ren1 SetBackground 1 1 1;

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 30 30 30;
  sample SetImplicitFunction quad;

vtkContourFilter cont1;
	cont1 SetInput [sample GetOutput];
	cont1 GenerateValues 3 0.0 0.4;  
vtkDecimate deci1;
	deci1 SetInput [cont1 GetOutput];
	deci1 SetMaximumIterations 10
	deci1 SetTargetReduction 0.6
	deci1 SetMaximumFeatureAngle 30
	deci1 SetInitialError 0.0
	deci1 SetErrorIncrement 0.01
	deci1 SetMaximumError 0.3
vtkPolyMapper dMapper1;
	dMapper1 SetInput [deci1 GetOutput];
	dMapper1 SetScalarRange 0.0 3.0;
vtkActor dActor1;  
	dActor1 SetMapper dMapper1;

vtkContourFilter cont2;
	cont2 SetInput [sample GetOutput];
	cont2 GenerateValues 3 0.2 0.6;  
vtkDecimate deci2;
	deci2 SetInput [cont2 GetOutput];
	deci2 SetMaximumIterations 10
	deci2 SetTargetReduction 0.6
	deci2 SetMaximumFeatureAngle 30
	deci2 SetInitialError 0.0
	deci2 SetErrorIncrement 0.01
	deci2 SetMaximumError 0.3
vtkPolyMapper dMapper2;
	dMapper2 SetInput [deci2 GetOutput];
	dMapper2 SetScalarRange 0.0 3.0;
vtkActor dActor2;  
	dActor2 SetMapper dMapper2;

vtkContourFilter cont3;
	cont3 SetInput [sample GetOutput];
	cont3 GenerateValues 3 0.4 0.8;  
vtkDecimate deci;
	deci SetInput [cont3 GetOutput];
	deci SetMaximumIterations 10
	deci SetTargetReduction 0.6
	deci SetMaximumFeatureAngle 30
	deci SetInitialError 0.0
	deci SetErrorIncrement 0.01
	deci SetMaximumError 0.3
vtkPolyMapper dMapper3;
	dMapper3 SetInput [deci GetOutput];
	dMapper3 SetScalarRange 0.0 3.0;
vtkActor dActor3;  
	dActor3 SetMapper dMapper3;
	
$ren1 AddActors dActor1;
$ren1 AddActors dActor2;
$ren1 AddActors dActor3;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

for {set count 1} {$count < 4} {} {
	incr count;

	puts "Displaying Middle"
	dActor1 VisibilityOff;    
	dActor2 VisibilityOn;    
	dActor3 VisibilityOff;    
  $renWin Render  

	puts "Displaying Innermost"
	dActor1 VisibilityOn;    
	dActor2 VisibilityOff;    
	dActor3 VisibilityOff;    
  $renWin Render

	puts "Displaying Middle"
	dActor1 VisibilityOff;    
	dActor2 VisibilityOn;    
	dActor3 VisibilityOff;    
  $renWin Render  
  
	puts "Displaying Outermost"
	dActor1 VisibilityOff;    
	dActor2 VisibilityOff;    
	dActor3 VisibilityOn;    
  $renWin Render

  
}
    puts "YEAH! success"  

	
# prevent the tk window from showing up then start the event loop
wm withdraw .

