#Stephen Young ... Homework 3
#Modified Sample.tcl code

#load vtktcl;
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

vtkContourFilter contours;
contours SetInput [sample GetOutput];
contours GenerateValues 5 0.0 1.2;

vtkTriangleFilter a_fil;
a_fil SetInput [contours GetOutput];
  
vtkDecimate killer;
killer SetInput [a_fil GetOutput];

vtkPolyMapper contMapper;
  contMapper SetInput [killer GetOutput];
  contMapper SetScalarRange 0.0 1.2;


vtkActor contActor;
  contActor SetMapper contMapper;


vtkOutlineFilter outline;
  outline SetInput [sample GetOutput];

vtkPolyMapper outlineMapper;
  outlineMapper SetInput [outline GetOutput];

vtkActor outlineActor;
  outlineActor SetMapper outlineMapper;
  eval [outlineActor GetProperty] SetColor 0 0 0;


# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;
$ren1 AddActors outlineActor;

$renWin Render


#Throw in a for loop here
#for {set num 0.2} {$num <= 6.0} {set num [expr $num+0.2]} {
#contours GenerateValues 5 0.0 $num;
#$renWin Render;
#}

# enable user interface interactor
#$iren SetUserMethod {wm deiconify .vtkInteract};
#$iren Initialize;
$iren Start;

# prevent the tk window from showing up then start the event loop
#wm withdraw .

