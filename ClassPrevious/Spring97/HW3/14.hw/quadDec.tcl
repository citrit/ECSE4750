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
  sample SetSampleDimensions 25 25 25;
  sample SetImplicitFunction quad;
  sample Update;

vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours SetValue 0 0.5;

vtkTriangleFilter tri;
    tri SetInput [contours GetOutput];
vtkDecimate deci;
    deci SetInput [tri GetOutput];
    deci SetTargetReduction 0.9;
    deci SetAspectRatio 20;
    deci SetInitialError 0.0005;
    deci SetErrorIncrement 0.001;
    deci SetMaximumIterations 2;
    deci SetInitialFeatureAngle 30;

vtkPolyMapper contMapper;
  contMapper SetInput [deci GetOutput];
  contMapper SetScalarRange 0.0 1.2;

vtkActor contActor;
  contActor SetMapper contMapper;

 
#outline
vtkOutlineFilter outline;
   outline SetInput [sample GetOutput];
vtkPolyMapper outlineMapper;
   outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
   outlineActor SetMapper outlineMapper;
   eval [outlineActor GetProperty] SetColor 0 0 0;

$ren1 SetBackground 1.0 1.0 1.0;
$ren1 AddActors contActor;
$ren1 AddActors outlineActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

for {set k 0} {$k < 3} {incr k} {
 for {set j 0} {$j < 20} {incr j} {
  set value [expr 0.0 + ($j)*(12.0/190.0)];
  contours SetValue 0 $value;
  $renWin Render;
 }
}

# prevent the tk window from showing up then start the event loop
wm withdraw .
