
proc animate {} {
    global renWin stopvar max min delta current

    .status configure -text "Animating isovalues..";
    
    # now render it with isovalues from min to max (delta increments)
    # (start at the largest to the scale is correct :-)
    set stopvar 0 
    for {set current $max} {$stopvar == 0} \
	    {set current [expr $current + $delta]} {
	if { $current <= $min }  { 
	    set delta [expr -1 * $delta];
	    set current $min; 
	}
	if { $current >= $max }  { 
	    set delta [expr -1 * $delta]; 
	    set current $max; 
	}
	render_frame $current
	update
    }
}

proc render_frame { val } {
    global renWin
    .status configure -text "Rendering $val..."
    update
    isosurface SetValue 0 $val
    $renWin Render
    .status configure -text "Ready"
}

proc make_gui {} {
    # Make controls
    button .b1 -command { set stopvar 1 } -text {Stop Animation}
    button .b2 -command { animate }       -text {Start Animation}
    button .b3 -command { exit }          -text {QUIT}
    frame .f1
    label .l1 -text "MAX:  "
    entry .e1 -textvariable max 
    frame .f2
    label .l2 -text "MIN:  "
    entry .e2 -textvariable min 
    frame .f3
    label .l3 -text "DELTA:"
    entry .e3 -textvariable delta
    frame .f4
    label .l4 -text "CUR:  "
    entry .e4 -textvariable current
    label .status -text "Ready"
    
    pack .l1 .e1 -side left -in .f1
    pack .l2 .e2 -side left -in .f2
    pack .l3 .e3 -side left -in .f3
    pack .l4 .e4 -side left -in .f4
    pack .b2 .b1 .f1 .f2 .f3 .f4 .status .b3 -fill x
    
    bind .e4 <Return> {render_frame $current}
}

