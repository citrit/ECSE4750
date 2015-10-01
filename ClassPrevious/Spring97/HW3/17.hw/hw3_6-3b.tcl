#
# Advanced Computer Graphics and Data Visualization
# Homework 3
# Exercise 6.3(b)
# Jesse Booth
# April 15, 1997
#

# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

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
    vol DebugOn;

vtkMarchingCubes contour;
  contour SetInput [vol GetOutput];
#  contour SetValue 0 225.0;
  contour GenerateValues 1 200 200;

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

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

# animate the isosurfaces
proc animate arg {
  for {set i 200} {$i < 2200} {incr i 100} {
  	contour GenerateValues 1 $i $i;
        $arg Render;        
  }
}

