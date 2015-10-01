# uncomment this line if you are using Windows95/NT
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

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

button .phred -text stepAnimate -command stepAnimate
pack .phred

proc stepAnimate {} {
    global renWin;

    for { set i 0.0 } { $i <= 1.2 } { set i [expr $i + 0.2] } {
	contours GenerateValues 1 $i $i;
	$renWin Render;
    }
}

# prevent the tk window from showing up then start the event loop
#wm withdraw .
