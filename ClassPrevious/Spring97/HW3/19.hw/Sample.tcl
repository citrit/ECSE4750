#load vtktcl;
# user interface command widget
source vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 20 20 20;
  sample SetImplicitFunction quad;
vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours SetValue 0 1.2;
vtkPolyMapper contMapper;
  contMapper SetInput [contours GetOutput];
  contMapper SetScalarRange 0.0 1.2;
vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# Setup the Control Panel
button .start -text "Start Animation" -command {animate 0 1.2 0.05}
pack .start
button .quit -text "Quit" -command exit
pack .quit -fill x

proc animate {lowVal highVal stepVal} {
  global renWin
  for {set currVal $lowVal} {$currVal <= $highVal} {set currVal [expr $currVal + $stepVal]} {
    contours SetValue 0 $currVal
    $renWin Render
    update
  }
}
