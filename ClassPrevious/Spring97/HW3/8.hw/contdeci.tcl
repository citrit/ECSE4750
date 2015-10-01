# Yuping Chen
# HW#3 contour.tcl
#
#load vtktcl;
# user interface command widget
source /campus/visualization/vtk/1.3/common/examplesTcl/vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients  0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 50 50 50;
  sample SetImplicitFunction quad;
#  sample SetScalarFunctionNumber 100;
#  sample SetVectorFunc tionNumber 202;
  sample Update;
  
vtkContourFilter cf;
   cf SetInput [sample GetOutput];
   cf GenerateValues 5 0.0 1.2;
  	
vtkDecimate deci; 
    deci SetInput [cf GetOutput];
    deci SetTargetReduction 0.5;
    deci SetAspectRatio 20;
    deci SetInitialError 0.0002;
    deci SetErrorIncrement 0.0005;
    deci SetMaximumIterations 6;
    deci SetInitialFeatureAngle 45;

vtkPolyNormals normals;
    normals SetInput [deci GetOutput];

vtkPolyMapper contMapper;
  contMapper SetInput [normals GetOutput];
#  eval cutMapper SetScalarRange [sample GetOutput];

vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;


# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;
set min 0.0;
set max 1.2;

# loop over iso-surfaces
for {set nloops 0} {$nloops < 5} {incr nloops} {
      set value [expr $min+ $nloops*($max/5)];
      cf SetInput [sample GetOutput];
      cf GenerateValues $nloops $min $value;
      $renWin Render;
}

# prevent the tk window from showing up then start the event loop
wm withdraw .

