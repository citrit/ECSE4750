#================================================
# Author: Elaine D'Souza
# HW#3	Date: Apr14,'97
# Using vtkDecimate on the MRIHead
# Animation sequence
#================================================

# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

# get the interactor ui
source vtkint.tcl

# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

# Load up the data
vtkStructuredPointsReader vol;
    vol SetFilename "MRIdata.vtk"

vtkTransform trans;
    trans RotateX 180;
 
vtkMarchingCubes cont1;
  cont1 SetInput [vol GetOutput];
  cont1 SetValue 0 300.0;
vtkTransformPolyFilter tpc1
    tpc1 SetInput [cont1 GetOutput];
    tpc1 SetTransform trans;
vtkDecimate deci1;
	deci1 SetInput [tpc1 GetOutput];
	deci1 SetMaximumIterations 10
	deci1 SetTargetReduction 0.9
	deci1 SetMaximumFeatureAngle 30
	deci1 SetInitialError 0.0
	deci1 SetErrorIncrement 0.01
	deci1 SetMaximumError 0.1
vtkPolyMapper volMapper1;
  volMapper1 SetInput [deci1 GetOutput];
  volMapper1 ScalarsVisibleOff;
vtkActor volActor1;
  volActor1 SetMapper volMapper1;

vtkMarchingCubes cont2;
  cont2 SetInput [vol GetOutput];
  cont2 SetValue 0 400.0;
vtkTransformPolyFilter tpc2
    tpc2 SetInput [cont2 GetOutput];
    tpc2 SetTransform trans;
vtkDecimate deci2;
	deci2 SetInput [tpc2 GetOutput];
	deci2 SetMaximumIterations 10
	deci2 SetTargetReduction 1.0
	deci2 SetMaximumFeatureAngle 30
	deci2 SetInitialError 0.0
	deci2 SetErrorIncrement 0.01
	deci2 SetMaximumError 0.75
vtkPolyMapper volMapper2;
  volMapper2 SetInput [deci2 GetOutput];
  volMapper2 ScalarsVisibleOff;
vtkActor volActor2;
  volActor2 SetMapper volMapper2;

vtkMarchingCubes cont3;
  cont3 SetInput [vol GetOutput];
  cont3 SetValue 0 500.0;
vtkTransformPolyFilter tpc3
    tpc3 SetInput [cont3 GetOutput];
    tpc3 SetTransform trans;
vtkDecimate deci3;
	deci3 SetInput [tpc3 GetOutput];
	deci3 SetMaximumIterations 10
	deci3 SetTargetReduction 1.0
	deci3 SetMaximumFeatureAngle 30
	deci3 SetInitialError 0.0
	deci3 SetErrorIncrement 0.01
	deci3 SetMaximumError 0.75
vtkPolyMapper volMapper3;
  volMapper3 SetInput [deci3 GetOutput];
  volMapper3 ScalarsVisibleOff;
vtkActor volActor3;
  volActor3 SetMapper volMapper3;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];
vtkTransformPolyFilter tpout
    tpout SetInput [outLine GetOutput];
    tpout SetTransform trans;
vtkPolyMapper outMapper;
  outMapper SetInput [tpout GetOutput];
vtkActor outActor;
  outActor SetMapper outMapper;

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors outActor;
$ren1 AddActors volActor1;
$ren1 AddActors volActor2;
$ren1 AddActors volActor3;

$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

for {set count 1} {$count < 4} {} {
	incr count;

	puts "Displaying Innermost"
	volActor1 VisibilityOn;    
	volActor2 VisibilityOff;    
	volActor3 VisibilityOff;    
  $renWin Render

	puts "Displaying Middle"
	volActor1 VisibilityOff;    
	volActor2 VisibilityOn;    
	volActor3 VisibilityOff;    
  $renWin Render  
  
	puts "Displaying Outermost"
	volActor1 VisibilityOff;    
	volActor2 VisibilityOff;    
	volActor3 VisibilityOn;    
  $renWin Render

	puts "Displaying Middle"
	volActor1 VisibilityOff;    
	volActor2 VisibilityOn;    
	volActor3 VisibilityOff;    
  $renWin Render  
  
}
	puts "YEAH!"

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

