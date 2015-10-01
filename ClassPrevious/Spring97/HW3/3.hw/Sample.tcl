load vtktcl;
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
  sample SetSampleDimensions 5 5 5;
  sample SetImplicitFunction quad;
vtkContourFilter contours;
contours SetInput [sample GetOutput];
contours GenerateValues 5 0 1.2;

vtkDecimate deci;
    deci SetInput [contours GetOutput];
    deci SetTargetReduction 0.90;
    deci SetAspectRatio 20;
    deci SetInitialError 0.002;
    deci SetErrorIncrement 0.005;
    deci SetMaximumIterations 6;
    deci SetInitialFeatureAngle 45;

vtkPolyMapper contMapper;
  contMapper SetInput [deci GetOutput];
  contMapper SetScalarRange 0.0 1.2;
vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

wm withdraw .

set cam1 [$ren1 GetActiveCamera];
for {set i 12} {$i>-1} {incr i -1} {
for {set j 0} {$j<5} {incr j 1} {
eval contours SetValue $j [expr $i/10.0];
}
$cam1 Azimuth 5;
$cam1 Elevation 5;
$renWin Render;
}

for {set i 0} {$i<12} {incr i 1} {
for {set j 0} {$j<5} {incr j 1} {
eval contours SetValue $j [expr $i/10.0];
}
$cam1 Elevation -5;
$cam1 Azimuth -5;

$renWin Render;
}