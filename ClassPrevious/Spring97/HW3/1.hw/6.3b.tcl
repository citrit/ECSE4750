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

set value 225.0;

vtkMarchingCubes contour;
  contour SetInput [vol GetOutput];
  contour SetValue 0 $value;

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

set min 0.0;
set max 250;
set numsteps 25;
set status "Ready"

# animate function
proc animate {} {
    global min max numsteps status renWin;
    for {set i 0} {$i < $numsteps} {incr i} {
	set value [expr $min+$i*($max-$min)/$numsteps];
	set status "Rendering with isovalue = $value";
	update
	contour SetValue 0 $value;
	$renWin Render;
    }

    set status "Ready"
}

# nice UI stuff
button .b1 -command { animate } -text {Animate}
frame .f1
label .l1 -text "Min:"
entry .e1 -textvariable min
frame .f2
label .l2 -text "Max:"
entry .e2 -textvariable max
frame .f3
label .l3 -text "Num steps:"
entry .e3 -textvariable numsteps
label .l4 -textvariable status
pack .b1
pack .l1 .e1 -side left -in .f1
pack .l2 .e2 -side left -in .f2
pack .l3 .e3 -side left -in .f3
pack .f1 .f2 .f3 .l4

# print a usage message
puts "Rotate the view to an appropriate position and then press the";
puts "Animate button to animate the isosurfaces.";
puts "You can also change the min and max isosurface values as well as";
puts "the number of steps that are made during the animation.";
