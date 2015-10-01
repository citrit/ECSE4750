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
  sample SetSampleDimensions 20 20 20;
  sample SetImplicitFunction quad;
vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours GenerateValues 5 0.0 1.2;

vtkDecimate deci;
  deci SetInput [contours GetOutput];
  deci SetTargetReduction 0.9;
  deci SetAspectRatio 20;
  deci SetInitialError 0.0005;
  deci SetErrorIncrement 0.001;
  deci SetMaximumIterations 6;
  deci SetInitialFeatureAngle 30;

vtkConnectivityFilter connect;
  connect SetInput [deci GetOutput];
  connect ExtractLargestRegion;

vtkDataSetMapper contMapper;
  contMapper SetInput [connect GetOutput];
  contMapper ScalarsVisibleOn;

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

source ./prob1.tcl
