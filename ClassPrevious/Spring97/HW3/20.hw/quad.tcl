# load vtktcl;
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
  sample SetSampleDimensions 30 30 30;
  sample SetImplicitFunction quad;
vtkContourFilter contours;
  contours SetInput [sample GetOutput];
vtkCleanPolyData clean;
  clean SetInput [contours GetOutput];
vtkDecimate deci;
  deci SetInput [clean GetOutput];
  deci SetTargetReduction 0.5;
vtkPolyNormals norms;
  norms SetInput [deci GetOutput];
vtkPolyMapper contMapper;
  contMapper SetInput [norms GetOutput];
  contMapper SetScalarRange 0.0 1.3;
vtkActor contActor;
  contActor SetMapper contMapper; 

vtkOutlineFilter outlin;
  outlin SetInput [sample GetOutput];
vtkPolyMapper mapoutline;
  mapoutline SetInput [outlin GetOutput];
vtkActor outline;
  outline SetMapper mapoutline;

# assign our actor to the renderer
$ren1 SetBackground .5 .5 .7;
$ren1 AddActors contActor;
$ren1 AddActors outline;
$renWin SetSize 350 350;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

set cam1 [$ren1 GetActiveCamera];
  $cam1 SetClippingRange 0.697 34.56;
  $cam1 SetPosition 4.36 3.5 4.21;
  $cam1 CalcViewPlaneNormal;
  $cam1 SetViewUp 0 1 0;

vtkLight lite1;
  lite1 SetPosition 4.29 4.95 -2.47;
$ren1 AddLights lite1;

for {set i 0} {$i <= 100} {incr i} {
  contours GenerateValues 0 [expr 1.4*$i/100.0] [expr 1.4*$i/100.0];
  $renWin Render; }

# prevent the tk window from showing up then start the event loop
wm withdraw .

