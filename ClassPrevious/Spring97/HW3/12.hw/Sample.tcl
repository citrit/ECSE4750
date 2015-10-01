load vtktcl;
# user interface command widget
source VTKINT.TCL
set x 5;
set y 0.0;
set z 1.2;

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
 quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 15 15 15;
  sample SetImplicitFunction quad;

vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours GenerateValues $x $y $z;

vtkTriangleFilter tri;
  tri SetInput [contours GetOutput];

vtkDecimate deci;
  deci SetInput [tri GetOutput];

vtkPolyMapper contMapper;
  contMapper SetInput [deci GetOutput];
  contMapper SetScalarRange 0.0 1.2;
vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

#set x 5;
#set y 0.0;
#set z 9.2;
#contours GenerateValues $x $y $z;
#$renWin Render;

#proc anim {$renWin $x $y $z} {
    for {set i 0} {$i < 50} {incr i; incr i} {
	#$renWin Render;
	#set $x [expr [llength $x] + $i];
	set y [expr $y + .1];
	set z [expr $z + .1];
	contours GenerateValues $x $y $z;
	#$contMapper SetScalarRange $y $z;
	$renWin Render;
    }
#}
# prevent the tk window from showing up then start the event loop
wm withdraw .
