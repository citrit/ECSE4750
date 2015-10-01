load vtktcl;
# user interface command widget
source vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

#vtkContourFilter contours(0);
#vtkContourFilter contours(1);
vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 20 20 20;
  sample SetImplicitFunction quad;

vtkContourFilter contour;
   contour SetInput [sample GetOutput];
vtkPolyMapper contMapper;
vtkActor contActor;
for {set count 2} {$count < 12} { incr count 2} {
   contour SetValue 0 [ expr { $count/10.0 }]
   contMapper SetInput [contour GetOutput];
   contMapper SetScalarRange 0.0 1.4;
   contActor SetMapper contMapper;
# assign our actor to the renderer
   $ren1 SetBackground 1 1 1;
   $ren1 AddActors contActor;
# enable user interface interactor
   $iren SetUserMethod {wm deiconify .vtkInteract};
   $iren Initialize;
# prevent the tk window from showing up then start the event loop
   wm withdraw .

 }

