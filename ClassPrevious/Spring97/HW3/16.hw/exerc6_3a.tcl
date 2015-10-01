# Homework 3 - Advanced Computer Graphics and Data Visualization
# Helio Pedrini
#
# Animation sequence for the quadric example

# get the interactor ui
source /campus/visualization/vtk/1.1/common/examples/tcl/vtkInt.tcl

# create the render master
vtkRenderMaster rm;

# create a RenderWindow and Renderer
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
  
vtkSampleFunction sample;
  sample SetSampleDimensions 50 50 50;
  sample SetImplicitFunction quad;
  
vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours GenerateValues 5 0.0 1.2;
  
vtkPolyMapper contMapper;
  contMapper SetInput [contours GetOutput];
  contMapper SetScalarRange 0.0 1.2;
  
vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

# render the sequence of images, creating an animation.
for {set iteration 0} {$iteration <= 4} {incr iteration 1} {
   contours GenerateValues $iteration 0.0 1.2;
   $iren SetUserMethod {wm deiconify .vtkInteract};
   $iren Initialize;
}

# prevent the tk window from showing up then start the event loop
wm withdraw .

