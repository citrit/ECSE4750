# user interface command widget
source vtkInt.tcl

# make some stuff global so we can access it in our function
global renWin actors

# create a rendering window and renderer
vtkRenderMaster rm;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# create the input source
vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 50 50 50;
  sample SetImplicitFunction quad;

# now create a fleet of actors
set steps 6
for { set i 1.2 } { $i >= 0 } { set i [expr $i - 1.2 / $steps] } {
    # create one contour
    vtkContourFilter contours_$i;
    contours_$i SetInput [sample GetOutput];
    contours_$i GenerateValues 0 $i $i

    # map it to polygons
    vtkPolyMapper contMapper_$i;
    contMapper_$i SetInput [contours_$i GetOutput];
    contMapper_$i SetScalarRange 0.0 1.2;

    # create an actor for it
    vtkActor contActor_$i;
    contActor_$i SetMapper contMapper_$i;
    contActor_$i VisibilityOff

    # add the actor to our list of actors
    $ren1 AddActors contActor_$i;
    lappend actors contActor_$i
}

# add a reversed copy of the list so that it will bounce instead of cycle
set length [llength $actors]
for { set i [expr $length - 2] } { $i > 0 } { incr i -1 } {
    lappend actors [lindex $actors $i]
}

# turn on the first actor
contActor_1.2 VisibilityOn

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

# define the animation procedure
proc animate {} {
    global renWin actors

    # turn off the first guy in the loop
    set head [lindex $actors 0]
    $head VisibilityOff

    # put him at the end
    set actors "[lrange $actors 1 end] $head"

    # pull the next guy off and turn him on
    [lindex $actors 0] VisibilityOn

    # render it
    $renWin Render

    # set us up to change again
    after 300 animate
}

# start us off!
animate


