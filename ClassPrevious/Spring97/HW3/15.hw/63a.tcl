# : Gregory C. Tulumbas  -  ACG Homework #3, Part 6.3(a)
# :
# : Usage: Type "Cycle" to see the animation

# If you are using Window95 and vtk 1.3 uncomment the line below
load vtktcl;

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

vtkContourFilter contour1;
  contour1 SetInput [sample GetOutput];
  contour1 SetValue 0 0.0;
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

# prevent the tk window from showing up then start the event loop
wm withdraw .

proc Cycle {} {
	global contMapper;
 	global contour1;
 	global contours;
	global renWin;
	
	contMapper SetInput [contour1 GetOutput];
	  
        contour1 SetValue 0 0.0; $renWin Render;
        contour1 SetValue 0 0.1; $renWin Render;
        contour1 SetValue 0 0.2; $renWin Render;
        contour1 SetValue 0 0.3; $renWin Render;
        contour1 SetValue 0 0.4; $renWin Render;
        contour1 SetValue 0 0.5; $renWin Render;
        contour1 SetValue 0 0.6; $renWin Render;
        contour1 SetValue 0 0.7; $renWin Render;
        contour1 SetValue 0 0.8; $renWin Render;
        contour1 SetValue 0 0.9; $renWin Render;
        contour1 SetValue 0 1.0; $renWin Render;
        contour1 SetValue 0 1.1; $renWin Render;
        contour1 SetValue 0 1.2; $renWin Render;
        
	contMapper SetInput [contours GetOutput];
        $renWin Render;

}
