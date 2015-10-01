load vtktcl;
# user interface command widget
source /vtk13/examples/vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# create an actor and give it cone geometry
vtkConeSource cone;
  cone SetResolution 8;
vtkPolyMapper coneMapper;
  coneMapper SetInput [cone GetOutput];
vtkActor coneActor;
  coneActor SetMapper coneMapper;

# assign our actor to the renderer
$ren1 AddActors coneActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

