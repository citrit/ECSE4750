# tcl file for Graphics Project 3, problem 6.3a
# John P Kozura

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
  sample SetSampleDimensions 50 50 50;
  sample SetImplicitFunction quad;


# here is my code, eventually generates 5 actors
# I think this would be better done in a loop

vtkContourFilter cont1;
  cont1 SetInput [sample GetOutput];
  cont1 GenerateValues 1 0.0 0.2;
vtkPolyMapper contMapper1;
  contMapper1 SetInput [cont1 GetOutput];
  contMapper1 SetScalarRange 0.0 2.0;
vtkActor contActor1;
  contActor1 SetMapper contMapper1;
$ren1 AddActors contActor1;
  contActor1 VisibilityOn;

vtkContourFilter cont2;
  cont2 SetInput [sample GetOutput];
  cont2 GenerateValues 1 0.0 0.4;
vtkPolyMapper contMapper2;
  contMapper2 SetInput [cont2 GetOutput];
  contMapper2 SetScalarRange 0.0 2.0;
vtkActor contActor2;
  contActor2 SetMapper contMapper2;
$ren1 AddActors contActor2;
  contActor2 VisibilityOff;

vtkContourFilter cont3;
  cont3 SetInput [sample GetOutput];
  cont3 GenerateValues 1 0.0 0.6;
vtkPolyMapper contMapper3;
  contMapper3 SetInput [cont3 GetOutput];
  contMapper3 SetScalarRange 0.0 2.0;
vtkActor contActor3;
  contActor3 SetMapper contMapper3;
$ren1 AddActors contActor3;
  contActor3 VisibilityOff;

vtkContourFilter cont4;
  cont4 SetInput [sample GetOutput];
  cont4 GenerateValues 1 0.0 0.8;
vtkPolyMapper contMapper4;
  contMapper4 SetInput [cont4 GetOutput];
  contMapper4 SetScalarRange 0.0 2.0;
vtkActor contActor4;
  contActor4 SetMapper contMapper4;
$ren1 AddActors contActor4;
  contActor4 VisibilityOff;

vtkContourFilter cont5;
  cont5 SetInput [sample GetOutput];
  cont5 GenerateValues 1 0.0 1.0;
vtkPolyMapper contMapper5;
  contMapper5 SetInput [cont5 GetOutput];
  contMapper5 SetScalarRange 0.0 2.0;
vtkActor contActor5;
  contActor5 SetMapper contMapper5;
$ren1 AddActors contActor5;
  contActor5 VisibilityOff;


# now for some more code pirated shamelessly from sample
$ren1 SetBackground 1 1 1;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

# initial render to turn everything off
$renWin Render;

# now cycle through all 5

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
