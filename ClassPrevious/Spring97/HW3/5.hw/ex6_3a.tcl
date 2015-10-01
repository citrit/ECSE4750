# Author: Elaine D'Souza
# HW#3	Date: Apr14,'97
# Using vtkContourFilter to generate isosurfaces
# for the Quadric function
# By assigning an Actor for each surface, and 
# turning these ON/OFF, creating animation 
# sequence
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
  sample SetSampleDimensions 25 25 25;
  sample SetImplicitFunction quad;

vtkContourFilter cont1;
	cont1 SetInput [sample GetOutput];
	cont1 GenerateValues 3 0.0 0.4;  
vtkPolyMapper contMapper1;
	contMapper1 SetInput [cont1 GetOutput];
	contMapper1 SetScalarRange 0.0 3.0;
vtkActor contActor1;  
	contActor1 SetMapper contMapper1;

vtkContourFilter cont2;
	cont2 SetInput [sample GetOutput];
	cont2 GenerateValues 3 0.2 0.6;  
vtkPolyMapper contMapper2;
	contMapper2 SetInput [cont2 GetOutput];
	contMapper2 SetScalarRange 0.0 3.0;
vtkActor contActor2;  
	contActor2 SetMapper contMapper2;

vtkContourFilter cont3;
	cont3 SetInput [sample GetOutput];
	cont3 GenerateValues 3 0.4 0.8;  
vtkPolyMapper contMapper3;
	contMapper3 SetInput [cont3 GetOutput];
	contMapper3 SetScalarRange 0.0 3.0;
vtkActor contActor3;  
	contActor3 SetMapper contMapper3;

$ren1 AddActors contActor1;
$ren1 AddActors contActor2;
$ren1 AddActors contActor3;

#contActor1 VisibilityOff;    
#contActor2 VisibilityOff;    
#contActor3 VisibilityOff;    

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

for {set count 1} {$count < 10} {} {
	incr count;

	puts "Displaying Innermost"
	contActor1 VisibilityOn;    
	contActor2 VisibilityOff;    
	contActor3 VisibilityOff;    
  $renWin Render

	puts "Displaying Middle"
	contActor1 VisibilityOff;    
	contActor2 VisibilityOn;    
	contActor3 VisibilityOff;    
  $renWin Render  
  
	puts "Displaying Outermost"
	contActor1 VisibilityOff;    
	contActor2 VisibilityOff;    
	contActor3 VisibilityOn;    
  $renWin Render

	puts "Displaying Middle"
	contActor1 VisibilityOff;    
	contActor2 VisibilityOn;    
	contActor3 VisibilityOff;    
  $renWin Render  
  
}

puts "YEAH! success"  

	
# prevent the tk window from showing up then start the event loop
wm withdraw .

