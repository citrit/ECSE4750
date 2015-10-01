#
# Advanced Computer Graphics and Data Visualization
# Homework 3
# Exercise 6.3(a)
# Jesse Booth
# April 15, 1997

# load vtktcl;
# user interface command widget
source vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# quadric function
vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;

vtkSampleFunction sample;
# reduced to 20 20 20 from 50 50 50 to speed things up
  sample SetSampleDimensions 20 20 20;
  sample SetImplicitFunction quad;

vtkContourFilter contours;
  contours SetInput [sample GetOutput];
# display an initial contour
  contours GenerateValues 1 0.0 0.1;

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

# prevent the tk window from showing up then start the event loop
wm withdraw .

# animate an isosurface
proc animate arg {                                               
  for {set i 0} {$i < 19} {incr i} {                             
    contours GenerateValues 1 [expr $i / 15.0] [expr $i / 15.0];
    $arg Render;                                                 
  }                                                              
}                                                                
                                                                 
