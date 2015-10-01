# Spit out a helpfull screen.

puts "Homework 3 TCL script
Use:
       vtk hw3.tcl 1       - For Wireframe
       vtk hw3.tcl 2       - For plane
       vtk hw3.tcl 3       - For Contours
"

# get the interactor ui
source /locker/44/000644/vtk1.0/examples/tcl/vtkInt.tcl

# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren   [$renWin MakeRenderWindowInteractor];

if [catch {open example.grid r} inf] {
    puts stderr "error: cannot open example.grid for read";
    exit 1;
} 
set line [gets $inf];
close $inf;
set dimx [lindex $line 0];
set dimy [lindex $line 1];
set numPts [expr $dimx * $dimy];
#
# Load up the data
#

vtkStructuredPoints volume;
   volume SetDimensions $dimx $dimy 1;

vtkFloatScalars scalars;
for {set i 2} {$i < [expr $numPts + 2]} {incr i 1} {
    scalars InsertNextScalar [lindex $line $i];
}
[volume GetPointData] SetScalars scalars;

vtkContourFilter cf;
   cf SetInput volume;
   cf GenerateValues 20 -7.0 8.0;

vtkStructuredPointsGeometryFilter sptFilt;
   sptFilt SetInput volume;

vtkPolyMapper mapper;
if {[lindex $argv 0] == 1} {
    mapper SetInput [sptFilt GetOutput];
} elseif {[lindex $argv 0] == 2} {
    mapper SetInput [sptFilt GetOutput];
} else {
    mapper SetInput [cf GetOutput];
}

vtkLODActor actor1;
   actor1 SetMapper mapper;
   if {[lindex $argv 0] == 1} {
       [actor1 GetProperty] SetWireframe;
   }

# Add the actors to the renderer, set the background and size
#
$ren1 AddActors actor1;
$ren1 SetBackground 0 0 0;
$renWin SetSize 500 400;
$iren Initialize;

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};

$renWin Render;

# prevent the tk window from showing up then start the event loop
wm withdraw .

