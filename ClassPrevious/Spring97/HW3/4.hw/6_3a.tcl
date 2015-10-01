
####################################################
#
# Advanced Graphics and Visualization Homework 3
#
# Assigmnent 6.3 part a from the VTK text
#
####################################################


#load vtktcl;
# user interface command widget
source ./vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;

vtkSampleFunction sample;
  sample SetSampleDimensions 10 10 10;
  sample SetImplicitFunction quad;

vtkContourFilter contours;
  contours SetInput [sample GetOutput];
#  contours GenerateValues 5 0.0 1.2;
  contours GenerateValues 1 0.0 0.1;

vtkPolyMapper contMapper;
  contMapper SetInput [contours GetOutput];
#  contMapper SetScalarRange 0.0 1.2;
  contMapper SetScalarRange 0.0 1.2;

vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

# this animation procedure will display an isosurface which
# appears to grow over time from 0.0 to 1.2
proc Animate arg {
  for {set i 0} {$i < 12} {incr i} {
    contours GenerateValues 1 [expr $i / 10.0] [expr $i / 10.0 ];
    $arg Render; 
  }
}  