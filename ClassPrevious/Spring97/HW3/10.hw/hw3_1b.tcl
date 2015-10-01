#  Laura Conway
#  Homework 3 Part 1a
#  Exercise 6.3 b
#
# user interface command widget
source vtkInt.tcl

#
# Call Animate from the Interactor window to animate scalar values
#
proc Animate {} {

global renWin min max increment current marchingcubes;

	if { $marchingcubes == 1} {
		for {set i 0} { $i < 11} {incr i} {
			contours SetValue 0 [expr $min + ($i * $increment)]
			puts "contours SetValue 0 [expr $min + ($i * $increment)]"
			$renWin Render;
		}
	} else {
		for {set i 0} { $i < 11} {incr i} {
			iso SetValue 0 [expr $min + ($i * $increment)]
			puts "iso SetValue 0 [expr $min + ($i * $increment)]"
			$renWin Render;
		}
	}
	set current $max;
	PrintInfo;
}

proc TogglePipeLine {} {

  global marchingcubes;

  puts "Switching Pipeline";
  if {$marchingcubes == 0} {
	set marchingcubes 1
    clean SetInput [contours GetOutput];
  } else {
	set marchingcubes 0
    clean SetInput [iso GetOutput];
  }
  PrintPipeLine;
}
proc ToggleConnectivity {} {

  global connectivity;

	if {$connectivity == 0} {
		set connectivity 1
		puts "Turning on Connectivity (ExtractLargestRegion)"
		contMapper SetInput [connect GetOutput];
	} else {
		puts "Turning off Connectivity"
		set connectivity 0
		contMapper SetInput [deci GetOutput];
	}
	PrintPipeLine;
}

proc PrintInfo {} {

   global marchingcubes;

	puts ""
	puts ""
	puts " "
	puts " "
	puts " "
	puts " "
	puts "Use the Interactor to call these functions:" 
	puts "   'Animate' to animate"
	puts "   'Next' to go to the next iso surface"
	puts "   'Redraw' to reset to first iso surface";
	puts "   'TogglePipeLine' to switch between MarchingCubes and ContourFilter";
	puts "   'ToggleConnectivity' to enable/disable the connectivity filter "; 	puts ""
	PrintPipeLine; 
	puts " "
	puts " "
	puts " "
}
proc PrintPipeLine {} {

    global marchingcubes connectivity;

	puts "The pipeline is currently:"
	if { $marchingcubes == 1 } {
		puts "   vtkMarchingCubes -> "
	} else {
		puts "   vtkContourFilter -> "
	}
	puts "   vtkCleanPolyData ->"
	puts "   vtkDecimate -> "
	if { $connectivity == 1 } {
	  puts "   vtkConnectivityFilter -> "
	}
	puts "   vtkDataSetMapper"
}
proc Next {} {

global renWin min max current increment marchingcubes;

	set current [expr $current + $increment];
	if {$current > $max} {
	  set current $min;
	}
	puts "Setting value to $current"
	if { $marchingcubes == 1} {
		contours SetValue 0 $current;
	} else {
		iso SetValue 0 $current;
	}
	$renWin Render;
}


proc Redraw {} {

global renWin min marchingcubes;

	if { $marchingcubes == 1} {
		contours SetValue 0 $min;
	} else {
		iso SetValue 0 $min;
	}
	$renWin Render;

	PrintInfo;
}


# create a rendering window and renderer
vtkRenderMaster rm;
global renWin min max current connectivity marchingcubes;
set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];
set min 200;
set max 2200.0;
set connectivity 1
set marchingcubes 1
set current 200
set increment [expr ($max - $min)/10.0];

vtkStructuredPointsReader reader;
  reader SetFilename "MRIdata.vtk"
#  reader DebugOn;

#
#  Doing either MarchingCubes or ContourFilter based on user input
#
vtkMarchingCubes contours;
  contours SetInput [reader GetOutput];
  contours SetValue 0 $min;

vtkContourFilter iso;
  iso SetInput [reader GetOutput];
  iso SetValue 0 $min;

vtkCleanPolyData clean;
	clean SetInput [contours GetOutput];

vtkDecimate deci;
  deci SetInput [ clean GetOutput ];
  deci SetTargetReduction 0.9;
  deci SetMaximumIterations 3;
#  deci DebugOn;

vtkConnectivityFilter connect;
  connect SetInput [deci GetOutput];
  connect ExtractLargestRegion;
# connect DebugOn;

vtkDataSetMapper contMapper;
  contMapper SetInput [connect GetOutput];
  contMapper SetScalarRange $min $max;

vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

# Put this guy in an upright postition
set camera [$ren1 GetActiveCamera ];
$camera SetPosition -105.0 -13.0 -63.0;
$camera SetViewUp 0.0 -1.0 0.5;
$camera CalcViewPlaneNormal;

PrintInfo;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

