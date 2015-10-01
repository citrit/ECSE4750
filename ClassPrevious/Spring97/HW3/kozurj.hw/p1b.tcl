# tcl file for Graphics Project 3, problem 6.3b
# John P Kozura

# get the interactor ui
source vtkInt.tcl

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
    vol SetFilename "MRIdata.vtk"
#    vol DebugOn;

# here is my code, same as the first one, eventually generates 5 actors
# I'd use a loop, but cut and paste is just so much fun


vtkMarchingCubes cont1;
  cont1 SetInput [vol GetOutput];
  cont1 SetValue 0 25.0;
vtkPolyMapper volMapper1;
  volMapper1 SetInput [cont1 GetOutput];
  volMapper1 ScalarsVisibleOff;
vtkActor volActor1;
  volActor1 SetMapper volMapper1;
  volActor1 VisibilityOn;
$ren1 AddActors volActor1;


vtkMarchingCubes cont2;
  cont2 SetInput [vol GetOutput];
  cont2 SetValue 0 50.0;
vtkPolyMapper volMapper2;
  volMapper2 SetInput [cont2 GetOutput];
  volMapper2 ScalarsVisibleOff;
vtkActor volActor2;
  volActor2 SetMapper volMapper2;
  volActor2 VisibilityOff;
$ren1 AddActors volActor2;

vtkMarchingCubes cont3;
  cont3 SetInput [vol GetOutput];
  cont3 SetValue 0 75.0;
vtkPolyMapper volMapper3;
  volMapper3 SetInput [cont3 GetOutput];
  volMapper3 ScalarsVisibleOff;
vtkActor volActor3;
  volActor3 SetMapper volMapper3;
  volActor3 VisibilityOff;
$ren1 AddActors volActor3;


vtkMarchingCubes cont4;
  cont4 SetInput [vol GetOutput];
  cont4 SetValue 0 100.0;
vtkPolyMapper volMapper4;
  volMapper4 SetInput [cont4 GetOutput];
  volMapper4 ScalarsVisibleOff;
vtkActor volActor4;
  volActor4 SetMapper volMapper4;
  volActor4 VisibilityOff;
$ren1 AddActors volActor4;


vtkMarchingCubes cont5;
  cont5 SetInput [vol GetOutput];
  cont5 SetValue 0 125.0;
vtkPolyMapper volMapper5;
  volMapper5 SetInput [cont5 GetOutput];
  volMapper5 ScalarsVisibleOff;
vtkActor volActor5;
  volActor5 SetMapper volMapper5;
  volActor5 VisibilityOff;
$ren1 AddActors volActor5;


# heres some of your code to generate a gridline
vtkOutlineFilter outLine;
  outLine SetInput [vol GetOutput];
vtkPolyMapper outMapper;
  outMapper SetInput [outLine GetOutput];
vtkActor outActor;
  outActor SetMapper outMapper;
$ren1 AddActors outActor;

$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

# now cycle through all 5

volActor1 VisibilityOff;
volActor2 VisibilityOn;
$renWin Render;

volActor2 VisibilityOff;
volActor3 VisibilityOn;
$renWin Render;

volActor3 VisibilityOff;
volActor4 VisibilityOn;
$renWin Render;

volActor4 VisibilityOff;
volActor5 VisibilityOn;


