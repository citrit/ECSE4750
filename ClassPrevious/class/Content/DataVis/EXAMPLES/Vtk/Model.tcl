# load vtktcl;
# user interface command widget
source vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
# Make first Render Window and included renderer
set renWin [rm MakeRenderWindow];
set renA [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# Make second renderer
set renB [$renWin MakeRenderer];

# assign the renderers a region of the render window
# in xmin, ymin, xmax, ymax
$renA SetViewport 0.0 0.0 0.5 1.0;
$renB SetViewport 0.5 0.0 1.0 1.0;
$renA SetBackground 0.75 0.75 0.0;
$renB SetBackground 1.0 1.0 1.0;

# create an actor and give it cone geometry
vtkConeSource cone;
  cone SetResolution 8;
vtkPolyMapper coneMapper;
  coneMapper SetInput [cone GetOutput];
vtkActor coneActor;
  coneActor SetMapper coneMapper;

# assign our actor to the renderer
$renA AddActors coneActor;
$renB AddActors coneActor;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

