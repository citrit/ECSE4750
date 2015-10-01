#================================================
# Author: Elaine D'Souza
# HW#3	Date: Apr14,'97
# Using vtkDecimate on the Quadric function
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
	cont1 GenerateValues 5 0.0 0.4;  
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

$ren1 AddActors dActor1;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;
	
# prevent the tk window from showing up then start the event loop
wm withdraw .

