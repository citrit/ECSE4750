# Graphics & Visualization
# Spring 1997
#
# Homework 3
#
# Alexander Holmansky
#


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

# Construct 9 filters
vtkContourFilter contour1;
  contour1 SetInput [sample GetOutput];
  contour1 GenerateValues 1 0.0 0.12;
vtkContourFilter contour2;
  contour2 SetInput [sample GetOutput];
  contour2 GenerateValues 1 0.12 0.24;
vtkContourFilter contour3;
  contour3 SetInput [sample GetOutput];
  contour3 GenerateValues 1 0.24 0.36;
vtkContourFilter contour4;
  contour4 SetInput [sample GetOutput];
  contour4 GenerateValues 1 0.36 0.48;  
vtkContourFilter contour5;
  contour5 SetInput [sample GetOutput];
  contour5 GenerateValues 1 0.48 0.60;
vtkContourFilter contour6;
  contour6 SetInput [sample GetOutput];
  contour6 GenerateValues 1 0.60 0.72;
vtkContourFilter contour7;
  contour7 SetInput [sample GetOutput];
  contour7 GenerateValues 1 0.72 0.84;
vtkContourFilter contour8;
  contour8 SetInput [sample GetOutput];
  contour8 GenerateValues 1 0.84 0.96;
vtkContourFilter contour9;
  contour9 SetInput [sample GetOutput];
  contour9 GenerateValues 1 0.96 1.2;

# create mappers
vtkPolyMapper contMapper1;
  contMapper1 SetInput [contour1 GetOutput];
  contMapper1 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper2;
  contMapper2 SetInput [contour2 GetOutput];
  contMapper2 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper3;
  contMapper3 SetInput [contour3 GetOutput];
  contMapper3 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper4;
  contMapper4 SetInput [contour4 GetOutput];
  contMapper4 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper5;
  contMapper5 SetInput [contour5 GetOutput];
  contMapper5 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper6;
  contMapper6 SetInput [contour6 GetOutput];
  contMapper6 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper7;
  contMapper7 SetInput [contour7 GetOutput];
  contMapper7 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper8;
  contMapper8 SetInput [contour8 GetOutput];
  contMapper8 SetScalarRange 0.0 1.2;
vtkPolyMapper contMapper9;
  contMapper9 SetInput [contour9 GetOutput];
  contMapper9 SetScalarRange 0.0 1.2;

# create actors
vtkActor contActor1;
  contActor1 SetMapper contMapper1;

vtkActor contActor2;
  contActor2 SetMapper contMapper2;
  contActor2 VisibilityOff;
vtkActor contActor3;
  contActor3 SetMapper contMapper3;
  contActor3 VisibilityOff;
vtkActor contActor4;
  contActor4 SetMapper contMapper4;
  contActor4 VisibilityOff;
vtkActor contActor5;
  contActor5 SetMapper contMapper5;
  contActor5 VisibilityOff;
vtkActor contActor6;
  contActor6 SetMapper contMapper6;
  contActor6 VisibilityOff;
vtkActor contActor7;
  contActor7 SetMapper contMapper7;
  contActor7 VisibilityOff;
vtkActor contActor8;
  contActor8 SetMapper contMapper8;
  contActor8 VisibilityOff;
vtkActor contActor9;
  contActor9 SetMapper contMapper9;
  contActor9 VisibilityOff;

# assign our actors to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor1;
$ren1 AddActors contActor2;
$ren1 AddActors contActor3;
$ren1 AddActors contActor4;
$ren1 AddActors contActor5;
$ren1 AddActors contActor6;
$ren1 AddActors contActor7;
$ren1 AddActors contActor8;
$ren1 AddActors contActor9;


# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

contActor1 VisibilityOff;
contActor2 VisibilityOn;
$renWin Render;
contActor2 VisibilityOff;
contActor3 VisibilityOn;
$renWin Render;
contActor3 VisibilityOff;
contActor4 VisibilityOn;
$renWin Render;
contActor4 VisibilityOff;
contActor5 VisibilityOn;
$renWin Render;
contActor5 VisibilityOff;
contActor6 VisibilityOn;
$renWin Render;
contActor6 VisibilityOff;
contActor7 VisibilityOn;
$renWin Render;
contActor7 VisibilityOff;
contActor8 VisibilityOn;
$renWin Render;
contActor8 VisibilityOff;
contActor9 VisibilityOn;
$renWin Render;



