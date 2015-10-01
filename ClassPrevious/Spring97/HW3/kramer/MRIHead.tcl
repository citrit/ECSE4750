# If you are using Window95 and vtk 1.3 uncomment the line below
# load vtktcl;

# get the interactor ui
source vtkInt.tcl

# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer
#
global renWin actors
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

#
# Load up the data
vtkStructuredPointsReader vol;
    vol SetFilename "MRIdata.vtk"
    vol DebugOn;

# create a bunch of coutours
foreach i {  200 400 600 800 1000 1100 1200 1300 1400 1500 1600 1700 1800 1900 2000 } {
    # create a contour
    vtkMarchingCubes contour$i;
    contour$i SetInput [vol GetOutput];
    contour$i SetValue 0 $i;

    # map the polygons
    vtkPolyMapper volMapper$i;
    volMapper$i SetInput [contour$i GetOutput];
    volMapper$i ScalarsVisibleOff;

    # create an actor for them
    vtkActor volActor$i;
    volActor$i SetMapper volMapper$i;
    volActor$i VisibilityOff

    # add the actor to our list
    $ren1 AddActors volActor$i;
    lappend actors volActor$i
}

# now add them in reverse so we get bounce instead of cycle
set length [llength $actors]
for { set i [expr $length - 2] } { $i > 0 } { incr i -1 } {
    lappend actors [lindex $actors $i]
}

# turn on the first actor
[lindex $actors 0] VisibilityOn

# create an outline of the volume
vtkOutlineFilter outLine;
outLine SetInput [vol GetOutput];

# map the outline
vtkPolyMapper outMapper;
outMapper SetInput [outLine GetOutput];

# draw it
vtkActor outActor;
outActor SetMapper outMapper;
$ren1 AddActors outActor;

# setup the background
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;


# render the image
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

# note that our animation routine is exactly the same as before
# define the animation procedure
proc animate {} {
    global renWin actors

    # turn off the first guy in the loop
    set head [lindex $actors 0]
    $head VisibilityOff

    # put him at the end
    set actors "[lrange $actors 1 end] $head"
    puts $actors

    # pull the next guy off and turn him on
    [lindex $actors 0] VisibilityOn

    # render it
    $renWin Render

    # set us up to change again
    after 300 animate
}

# start us off!!
animate

