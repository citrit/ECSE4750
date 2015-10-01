#load vtktcl;
# user interface command widget
source vtkInt.tcl

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

set value 1.2

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 50 50 50;
  sample SetImplicitFunction quad;
vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours SetValue 0 $value
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

set min 0.0;
set max 1.2;
set numsteps 25;
set status "Ready"

# animate function
proc animate {} {
    global min max numsteps status renWin;
    for {set i 0} {$i < $numsteps} {incr i} {
	set value [expr $min+$i*($max-$min)/$numsteps];
	set status "Rendering with isovalue = $value";
	update
	contours SetValue 0 $value;
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
