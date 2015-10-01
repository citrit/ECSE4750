# Brutus Youn
# Graphics and Visualization
# HW#3
# Quadratic Function Animation

load vtktcl;

# get the interactor ui
source vtkInt.tcl

#First create the rendermaster
vtkRenderMaster rm;

# create a window to render into
set renWin [rm MakeRenderWindow];

# create a renderer
set ren1 [$renWin MakeRenderer];

# interactiver renderer catches mouse events (optional)
set iren [$renWin MakeRenderWindowInteractor];

# Quadric definition
  vtkQuadric quadric;
    quadric SetCoefficients .5 1 .2 0 .1 0 0 .2 0 0;

  vtkSampleFunction sample;
    sample SetSampleDimensions 30 30 30;
    sample SetImplicitFunction quadric;
    
# Create surface F(x,y,z) 
  vtkContourFilter contours;
    contours SetInput [sample GetOutput];
    contours GenerateValues 1 0.0 0.1;

  vtkPolyMapper contMapper;
    contMapper SetInput [contours GetOutput];
    contMapper SetScalarRange 0.0 1.0;

  vtkActor contActor;
    contActor SetMapper contMapper;

# Create outline
  vtkOutlineFilter outline;
    outline SetInput [sample GetOutput];

  vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];

  vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    eval [outlineActor GetProperty] SetColor 0 0 0;


  $ren1 SetBackground 1 1 1;
  $ren1 AddActors contActor;
  $ren1 AddActors outlineActor;

$iren Initialize;

contours GenerateValues 1 0.0 0.2;
$renWin Render;
contours GenerateValues 1 0.0 0.3;
$renWin Render;
contours GenerateValues 1 0.0 0.4;
$renWin Render;
contours GenerateValues 1 0.0 0.5;
$renWin Render;
contours GenerateValues 1 0.0 0.6;
$renWin Render;
contours GenerateValues 1 0.0 0.7;
$renWin Render;
contours GenerateValues 1 0.0 0.8;
$renWin Render;
contours GenerateValues 1 0.0 0.9;
$renWin Render;
contours GenerateValues 1 0.0 1.0;
$renWin Render;
contours GenerateValues 1 0.0 1.1;
$renWin Render;
contours GenerateValues 1 0.0 1.2;
$renWin Render;


wm withdraw .;
  