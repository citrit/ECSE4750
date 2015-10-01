#  Laura Conway
#  Homework 3 Part 1 a
#  Exercise 6.3 
#
# user interface command widget
source vtkInt.tcl
#
# Call Animate from the Interactor window to animate scalar values
#
proc Animate {} {

global renWin min max num_increments decimation;

    contours GenerateValues 0 10 10;
	$renWin Render;

	for {set i 0} { $i < $num_increments} {incr i} {
		set val [expr $max - ($min + ($i/($num_increments-1.0))*($max - $min))]
		contours SetValue 0 $val;
		$renWin Render;
		puts "SetValue 0 $val"
#
# Since decimation is pretty slow, don't bother with a delay if it's enabled
		if { $decimation == 0 } {
		   after 1000
        }
	}
    contours GenerateValues 0 10 10;
	$renWin Render;
	for {set i 0} { $i < $num_increments} {incr i} {
		set val [expr $min + ($i/($num_increments-1.0))*($max - $min)]
		contours SetValue $i $val;
		$renWin Render;
		puts "SetValue $i $val" 
		if { $decimation == 0 } {
		   after 500
        }
	}
#
# Reset back to original image
#
	contours GenerateValues $num_increments $min $max;
	$renWin Render;

    PrintInfo;
}

proc ToggleDecimation {} {

  global decimation connectivity; 

  if {$decimation == 0} {
	set decimation 1
	puts "Turning on Decimation"
	if {$connectivity == 1} {
	   connect SetInput [deci GetOutput];
	} else {
       contMapper SetInput [deci GetOutput];
    }
  } else {
	puts "Turning off decimation"
	set decimation 0
	if {$connectivity == 1} {
	   connect SetInput [ contours GetOutput ];
	} else {
	   contMapper SetInput [contours GetOutput];
	}
  }
}
proc ToggleConnectivity {} {

  global decimation connectivity;

  if {$connectivity == 0} {
	set connectivity 1
	puts "Turning on Connectivity (ExtractLargestRegion)"
	if {$decimation == 1} {
	   connect SetInput [deci GetOutput];
	} else {
       connect SetInput [contours GetOutput];
	}
    contMapper SetInput [connect GetOutput];
  } else {
	puts "Turning off Connectivity"
	set connectivity 0
	if {$decimation == 1} {
       contMapper SetInput [deci GetOutput];
    } else {
       contMapper SetInput [contours GetOutput];
	}
  }
}
proc PrintInfo {} {

  global decimation connectivity; 

   puts ""
   puts ""
   puts " "
   puts " "
   puts " "
   puts " "
   puts "Use the Interactor to call the function 'Animate' to animate;"
   puts "and 'ToggleDecimation' and 'ToggleConnectivity' to add/remove filters."
   puts ""
   puts "The pipeline is currently:"
   puts "   vtkSampleFunction -> "
   puts "   vtkContourFilter -> "
   if { $decimation == 1 } {
      puts "   vtkDecimate -> "
   } 
   if { $connectivity == 1 } {
      puts "   vtkConnectivityFilter -> "
   }
   puts "   vtkDataSetMapper"
   puts " "
   puts " "
   puts " "
}
# create a rendering window and renderer
vtkRenderMaster rm;
global min max num_increments decimation connectivity;
set num_increments 5;
set min 0.0
set max 1.2
set decimation 0
set connectivity 0

set renWin [rm MakeRenderWindow];
set ren1 [$renWin MakeRenderer];
set iren [$renWin MakeRenderWindowInteractor];

vtkQuadric quad;
  quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
vtkSampleFunction sample;
  sample SetSampleDimensions 30 30 30;
  sample SetImplicitFunction quad;
vtkContourFilter contours;
  contours SetInput [sample GetOutput];
  contours GenerateValues $num_increments $min $max;
#
#  Decimation and Connectivity filters may be toggled via 
#  ToggleDecimation and ToggleConnectivity to join in the pipeline
#
vtkDecimate deci;
  deci SetInput [ contours GetOutput ];
  deci SetTargetReduction 0.9;
  deci SetMaximumIterations 5;
# deci DebugOn;
vtkConnectivityFilter connect;
  connect SetInput [contours GetOutput];
  connect ExtractLargestRegion;
# connect DebugOn;

vtkDataSetMapper contMapper;
#  Initially we'll skip the Decimation and Connectivity Filters
  contMapper SetInput [contours GetOutput]; 
  contMapper SetScalarRange $min $max;
vtkActor contActor;
  contActor SetMapper contMapper;

# assign our actor to the renderer
$ren1 SetBackground 1 1 1;
$ren1 AddActors contActor;

PrintInfo;

# enable user interface interactor
$iren SetUserMethod {wm deiconify .vtkInteract};
$iren Initialize;

# prevent the tk window from showing up then start the event loop
wm withdraw .

