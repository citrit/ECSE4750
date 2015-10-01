  for {set i 225 } { $i > 50 } { incr i -15 } {
    contour SetValue 0 $i.0;
    $iren Initialize;
    }
