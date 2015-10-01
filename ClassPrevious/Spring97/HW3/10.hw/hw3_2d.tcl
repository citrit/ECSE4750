#  Laura Conway
#  Homework 3 Part 2
#  Exercise 9.4 d - Using vtkProbeFilter and vtkVectorNorm
#
#uncomment the line below if you are on Windows95
# load vtktcl;
# get the interactor ui
source vtkInt.tcl

# First create the render master
vtkRenderMaster rm;

# Now create the RenderWindow, Renderer and both Actors
#
set renWin [rm MakeRenderWindow];
set ren1   [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

# create pipeline
#
vtkPLOT3DReader pl3d;
    pl3d SetXYZFilename "combxyz.bin"
    pl3d SetQFilename "combq.bin"
    pl3d SetScalarFunctionNumber 100;
    pl3d SetVectorFunctionNumber 202;
    pl3d Update;

vtkStructuredGridGeometryFilter compPlane;
	compPlane SetInput [pl3d GetOutput];
	compPlane SetExtent 0 100 0 100 9 9;

vtkProbeFilter probe;
    probe SetInput [compPlane GetOutput];
    probe SetSource [pl3d GetOutput];
	probe DebugOn;

vtkVectorNorm color;
  color SetInput [ probe GetOutput ];
  color NormalizeOn;
  color DebugOn;

# Initialize lut to red->blue
vtkLookupTable lut;
   lut SetHueRange  0.0 0.667;

vtkDataSetMapper colorMapper;
  colorMapper SetInput [color GetOutput];
  colorMapper SetLookupTable lut;
  eval colorMapper SetScalarRange -1 1;

vtkActor colorActor;
	colorActor SetMapper colorMapper;

# Outline Box 
vtkStructuredGridOutlineFilter outline;
    outline SetInput [pl3d GetOutput];
vtkPolyMapper outlineMapper;
    outlineMapper SetInput [outline GetOutput];
vtkActor outlineActor;
    outlineActor SetMapper outlineMapper;
    [outlineActor GetProperty] SetColor 0 0 0;

$ren1 AddActors outlineActor;
$ren1 AddActors colorActor;
$ren1 SetBackground 1 1 1;
$renWin SetSize 500 500;
$iren Initialize;

set cam1 [$ren1 GetActiveCamera];
$cam1 SetClippingRange 3.95297 50;
$cam1 SetFocalPoint 8.88908 0.595038 29.3342;
$cam1 SetPosition -12.3332 31.7479 41.2387;
$cam1 CalcViewPlaneNormal;
$cam1 SetViewUp 0.060772 -0.319905 0.945498;

puts ""
puts ""
puts " This is exercise 9.4 d - Using vtkProbeFilter and vtkVectorNorm  " 
puts ""
puts " Lookup table changes from (red->blue) to (blue->red) after 5 seconds..."
puts ""
puts " SetHueRange  0.0 0.667 (red->blue)"

# render the image
#
$iren SetUserMethod {wm deiconify .vtkInteract};
$renWin Render;

#
# Change lut to blue->red
puts " Waiting 5 seconds..."
after 5000
lut SetHueRange  0.667 0.0;
$renWin Render;
puts " SetHueRange 0.667 0.0 (blue->red)"

# prevent the tk window from showing up then start the event loop
wm withdraw .

