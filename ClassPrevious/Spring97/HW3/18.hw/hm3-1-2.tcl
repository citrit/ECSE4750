source VTKINT.TCL
set max 225;
set min 10;


# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

#
# Load up the data
vtkStructuredPointsReader vol;
    vol SetFilename "/home/62/citrit/public/Spring97/examples/Vtk/MRIdata.vtk"
    vol DebugOn;

vtkMarchingCubes contour;
  contour SetInput [vol GetOutput];
  contour SetValue 10 225.0;

vtkPolyMapper volMapper;
  volMapper SetInput [contour GetOutput];
  volMapper ScalarsVisibleOff;

vtkActor volActor;
  volActor SetMapper volMapper;

vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];

vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];

vtkActor outActor;
  outActor SetMapper outMapper;

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors outActor;
$ren1 AddActors volActor;
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

for {set i 0} {$i <10 } {incr i} {
	set max [expr $max + 20]
        puts "Contour Number $i";
        contour SetValue $min $max;
        $renWin Render;
        }


# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};

#$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .
