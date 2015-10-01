# load vtktcl;
# user interface command widget
source vtkInt.tcl

set continue 1;
set iteration 0;
set run 0;
set min 0;
set max 1.2;
set delta 0.2;
set current $max;

source control.tcl

proc generate_contour { value } {
  
  contours SetValue 0 $value;

}

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 40 40 40;
  sample SetImplicitFunction quad;

vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours SetValue 0 $max;

vtkPolyNormals norm
  norm SetInput [contours GetOutput];

vtkDecimate decimate
  decimate SetInput [norm GetOutput];
  decimate SetTargetReduction 0.6;
  decimate SetMaximumIterations 6;
  decimate PreserveEdgesOn

vtkPolyMapper contMapper;
  contMapper SetInput [decimate GetOutput];
  contMapper SetScalarRange 0.0 1.2;

vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

$ren1 SetEndRenderMethod allow_continue;

# enable user interface interactor
$iren SetUserMethod {  wm deiconify .vtkInteract };
$iren Initialize


# prevent the tk window from showing up then start the event loop
#wm withdraw .


proc deci_on { } {
  global renWin run
  contMapper SetInput [decimate GetOutput];
  if { $run ==0 } {
    $renWin Render;
  }   
}

proc deci_off { } {
  global renWin run
  contMapper SetInput [contours GetOutput];
  if { $run ==0 } {
    $renWin Render;
  }   
}


control_panel;



iterate;

















