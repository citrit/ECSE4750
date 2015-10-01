
proc control_panel { } {
  global run min max delta current;
  button .start_b -text "Start" -command { set run 1 };
  button .stop_b -text "Stop" -command { set run 0 };

  pack .start_b .stop_b -fill x -side top;


  frame .min
  frame .max
  frame .delta
  frame .cur

  label .min.l -text "Min"
  label .max.l -text "Max"
  label .delta.l -text "Delta"
  label .cur.l -text "Cur"
  entry .min.e -width 20 -relief sunken -textvariable min
  entry .max.e -width 20 -relief sunken -textvariable max
  entry .delta.e -width 20 -relief sunken -textvariable delta
  entry .cur.e -width 20 -relief sunken -textvariable current
 
  bind .cur.e <Return> set_new_values

  pack .min.l -side left -fill x
  pack .min.e -side right 

  pack .max.l -side left -fill x
  pack .max.e -side right 

  pack .delta.l -side left -fill x
  pack .delta.e -side right 

  pack .cur.l -side left -fill x
  pack .cur.e -side right 
 
  pack .min .max .delta .cur -side top -fill x

  button .dec_on -text "Decimation On" -command { deci_on }
  button .dec_off -text "Decimation Off" -command { deci_off }

  pack .dec_on .dec_off -side top -fill x

  button .quit -text "Quit" -command { exit }
  pack .quit -side bottom -fill x

}

proc set_new_values { } {
  global renWin current
  generate_contour $current;
  $renWin Render;
  update;
}

proc iterate { } {
    global continue iteration run min max delta current renWin;
    set j 1;
    while {$j==1 } {
	tkwait variable run;
	while { $run ==1 } {
	    set current [expr $current-$delta];
	    generate_contour $current;
	    incr iteration;
	    if {$current <= $min} {set delta [expr -$delta]}
	    if {$current >= $max} {set delta [expr -$delta]}
	    $renWin Render;
	    update;
	}
    }
}
