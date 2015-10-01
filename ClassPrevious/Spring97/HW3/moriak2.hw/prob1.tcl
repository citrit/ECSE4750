  for {set i 1} { $i < 7 } { incr i } {
    contours GenerateValues 5 0.$i 1.2;
    $iren Initialize;
    }
for {set j 2} { $j < 6 } { incr j } {
    contours GenerateValues 5 0.7 1.$j;
    $iren Initialize;
    }
for {set k 5} { $k > 0 } { incr k -1 } {
    contours GenerateValues $k 0.7 1.6;
    $iren Initialize;
    }
